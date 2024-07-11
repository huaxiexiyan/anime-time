package cn.catguild.anime.service.impl;

import cn.catguild.anime.common.ApiPage;
import cn.catguild.anime.domain.Anime;
import cn.catguild.anime.domain.query.AnimePageQuery;
import cn.catguild.anime.repository.mapper.AnimeMapper;
import cn.catguild.anime.service.AnimeService;
import cn.catguild.anime.utils.ApiPageUtils;
import cn.catguild.anime.utils.id.IdGenerator;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author xiyan
 * @date 2024/7/7 20:47
 */
@AllArgsConstructor
@Service
public class AnimeServiceImpl implements AnimeService {

	private final AnimeMapper baseMapper;

	private final IdGenerator idGenerator;

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

}
