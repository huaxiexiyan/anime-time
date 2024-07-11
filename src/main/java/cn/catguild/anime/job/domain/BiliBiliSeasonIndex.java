package cn.catguild.anime.job.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author xiyan
 * @date 2024/7/10 21:18
 */
@NoArgsConstructor
@Data
public class BiliBiliSeasonIndex {

	private String cover;
	private String indexShow;
	private String title;
	private String subTitle;
	private String link;
	private Integer media_id;
	private Integer season_id;

}
