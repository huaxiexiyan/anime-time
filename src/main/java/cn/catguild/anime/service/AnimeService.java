package cn.catguild.anime.service;

import cn.catguild.anime.common.ApiPage;
import cn.catguild.anime.domain.Anime;
import cn.catguild.anime.domain.query.AnimePageQuery;

/**
 * @author xiyan
 * @date 2024/7/7 20:47
 */
public interface AnimeService {

	ApiPage<Anime> page(AnimePageQuery animePageQuery);

	void add(Anime anime);

}
