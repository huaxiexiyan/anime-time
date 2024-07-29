package cn.catguild.anime.job.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author xiyan
 * @date 2024/7/10 21:18
 */
@NoArgsConstructor
@Data
public class BiliBiliSeason {

	private Integer season_id;

	private Integer media_id;

	private String title;

	private String subtitle;

	private String cover;

	private String link;

	private String horizontal_picture;

	/**
	 * 地区id
	 */
	private List<BiliBiliSeasonArea> areas;

	/**
	 * 风格标签
	 */
	private String[] styles;

	/**
	 * 状态
	 */
	private Integer status;

	/**
	 * 剧集类型
	 */
	private Integer type;

	// 评价内容，或简介
	private String evaluate;

	/**
	 * 1、发行时间
	 */
	private BiliBiliSeasonPublishInfo publish;

	@Data
	public static class BiliBiliSeasonArea {

		private Integer id;

		private String name;

	}

	@Data
	public static class BiliBiliSeasonPublishInfo {
		private Integer is_finish;

		private Integer is_started;

		private String pub_time;

		private String pub_time_show;

		private Integer unknow_pub_date;

		private Integer weekday;
	}

	@Data
	class BiliBiliSeasonStyle {

		private String name;

	}

}
