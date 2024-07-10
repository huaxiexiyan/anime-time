package cn.catguild.anime.domain.query;

import cn.catguild.anime.common.ApiPageQuery;
import cn.catguild.anime.domain.Anime;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author xiyan
 * @date 2024/7/7 20:48
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class AnimePageQuery extends ApiPageQuery<Anime> {

	private String title;

}
