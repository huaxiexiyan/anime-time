package cn.catguild.anime.service.impl;

import cn.catguild.anime.common.ApiPage;
import cn.catguild.anime.domain.Anime;
import cn.catguild.anime.domain.AnimeCondition;
import cn.catguild.anime.domain.query.AnimePageQuery;
import cn.catguild.anime.domain.type.AnimeStyle;
import cn.catguild.anime.domain.type.Kv;
import cn.catguild.anime.domain.type.YesOrNoType;
import cn.catguild.anime.repository.mapper.AnimeFilterMapper;
import cn.catguild.anime.repository.mapper.AnimeMapper;
import cn.catguild.anime.repository.mapper.AnimeStyleMapper;
import cn.catguild.anime.service.AnimeService;
import cn.catguild.anime.utils.ApiPageUtils;
import cn.catguild.anime.utils.CommonUtil;
import cn.catguild.anime.utils.id.IdGenerator;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.google.common.base.Splitter;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author xiyan
 * @date 2024/7/7 20:47
 */
@AllArgsConstructor
@Service
public class AnimeServiceImpl implements AnimeService {

	private final AnimeMapper baseMapper;

	private final IdGenerator idGenerator;

	private final AnimeFilterMapper animeFilterMapper;

	private final AnimeStyleMapper animeStyleMapper;

	private final Cache<String, String> cacheAnimeCondition;

	@Override
	public ApiPage<Anime> page(AnimePageQuery animePageQuery) {
		if (StringUtils.hasText(animePageQuery.getArea()) && !"-1".equals(animePageQuery.getArea())) {
			animePageQuery.setAreas(CommonUtil.strToIntListAcceptEmpty(animePageQuery.getArea()));
		}
		// 解析年份
		if (StringUtils.hasText(animePageQuery.getYear()) && !"-1".equals(animePageQuery.getYear())) {
			Kv<Integer, Integer> kv = CommonUtil.parseIntRange(animePageQuery.getYear());
			animePageQuery.setStartYear(kv.getK());
			animePageQuery.setEndYear(kv.getV());
		}
		IPage<Anime> animeIpage = baseMapper.selectCustomPage(animePageQuery.getIpage(), animePageQuery);
		return ApiPageUtils.toApiPage(animeIpage, IPage::getRecords);
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public void add(Anime anime) {
		anime.setId(idGenerator.nextId());
		baseMapper.insert(anime);

		// 新增风格关系
		if (CollectionUtils.isEmpty(anime.getStyles())) {
			return;
		}
		List<AnimeStyle> styles = anime.getStyles();
		styles.forEach(style -> {
			style.setId(idGenerator.nextId());
			animeStyleMapper.insert(style);
		});
	}

	@Override
	public List<AnimeCondition> condition() {
		// 筛选项name,筛选值,筛选字段,筛选组
		List<AnimeCondition> animeFiltersList = animeFilterMapper.selectList(Wrappers.<AnimeCondition>lambdaQuery()
			.eq(AnimeCondition::getIsShow, YesOrNoType.YES.getCode()));
		List<AnimeCondition> result = new ArrayList<>();
		animeFiltersList.forEach(animeFilter -> {
			if (animeFilter.getParentId() == 0) {
				result.add(animeFilter);
			}
		});
		result.sort(Comparator.comparingInt(AnimeCondition::getSort));
		Map<Long, List<AnimeCondition>> collectMap = animeFiltersList.stream().collect(Collectors.groupingBy(AnimeCondition::getParentId));
		result.forEach(animeFilter -> {
			List<AnimeCondition> animeFilters = collectMap.getOrDefault(animeFilter.getId(), new ArrayList<>());
			animeFilters.sort(Comparator.comparingInt(AnimeCondition::getSort));
			animeFilter.setValues(animeFilters);
		});
		return result;
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public void updateByHashId(String hashId, Anime anime) {
		baseMapper.updateByHashId(hashId, anime);

		// 新增风格关系
		animeStyleMapper.delete(Wrappers.<AnimeStyle>lambdaQuery().eq(AnimeStyle::getAnimeHashId, hashId));
		if (CollectionUtils.isEmpty(anime.getStyles())) {
			return;
		}
		List<AnimeStyle> styles = anime.getStyles();
		styles.forEach(style -> {
			style.setId(idGenerator.nextId());
			animeStyleMapper.insert(style);
		});
	}

	@Override
	public boolean isExistByHashId(String hashId) {
		return baseMapper.exists(Wrappers.<Anime>lambdaQuery().eq(Anime::getHashId, hashId));
	}

	@Override
	public String getStyleKeywordByName(String style) {
		cacheAnimeCondition.getIfPresent(style);
		return cacheAnimeCondition.get(style, k -> {
			AnimeCondition animeCondition = animeFilterMapper.selectOne(Wrappers.<AnimeCondition>lambdaQuery()
				.eq(AnimeCondition::getName, style));
			if (animeCondition == null) {
				return null;
			}
			return animeCondition.getKeyword();
		});
	}

	@Override
	public void addCondition(AnimeCondition animeFilter) {
		animeFilterMapper.insert(animeFilter);
	}

}
