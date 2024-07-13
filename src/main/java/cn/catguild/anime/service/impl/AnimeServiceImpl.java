package cn.catguild.anime.service.impl;

import cn.catguild.anime.common.ApiPage;
import cn.catguild.anime.domain.Anime;
import cn.catguild.anime.domain.AnimeCondition;
import cn.catguild.anime.domain.query.AnimePageQuery;
import cn.catguild.anime.repository.mapper.AnimeFilterMapper;
import cn.catguild.anime.repository.mapper.AnimeMapper;
import cn.catguild.anime.service.AnimeService;
import cn.catguild.anime.utils.ApiPageUtils;
import cn.catguild.anime.utils.id.IdGenerator;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
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

	@Override
	public ApiPage<Anime> page(AnimePageQuery animePageQuery) {
		IPage<Anime> animeIpage = baseMapper.selectPage(animePageQuery.getIpage(), Wrappers.emptyWrapper());
		return ApiPageUtils.toApiPage(animeIpage, IPage::getRecords);
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public void add(Anime anime) {
		anime.setId(idGenerator.nextId());
		baseMapper.insert(anime);
	}

	@Override
	public List<AnimeCondition> condition() {
		// 筛选项name,筛选值,筛选字段,筛选组
		List<AnimeCondition> animeFiltersList = animeFilterMapper.selectList(Wrappers.emptyWrapper());
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

	@Override
	public void addCondition(AnimeCondition animeFilter) {
		animeFilterMapper.insert(animeFilter);
	}

}
