package cn.catguild.anime.service;

import cn.catguild.anime.common.ApiPage;
import cn.catguild.anime.domain.Anime;
import cn.catguild.anime.domain.AnimeCondition;
import cn.catguild.anime.domain.query.AnimePageQuery;

import java.util.List;

/**
 * @author xiyan
 * @date 2024/7/7 20:47
 */
public interface AnimeService {

	ApiPage<Anime> page(AnimePageQuery animePageQuery);

	void add(Anime anime);

	/**
	 * 查询出筛选栏选项
	 */
	List<AnimeCondition> condition();

	void addCondition(AnimeCondition animeFilter);

	void updateByHashId(String hashId, Anime anime);

	boolean isExistByHashId(String hashId);

}
