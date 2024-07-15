package cn.catguild.anime.domain.query;

import cn.catguild.anime.common.ApiPageQuery;
import cn.catguild.anime.domain.Anime;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author xiyan
 * @date 2024/7/7 20:48
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class AnimePageQuery extends ApiPageQuery<Anime> {

	/**
	 * 地区
	 **/
	private String area;

	/**
	 * 风格
	 **/

	private String styleId;

	/**
	 * 类型
	 **/
	private String seasonVersion;

	/**
	 * 付费
	 **/
	private String seasonStatus;

	/**
	 * 配音
	 **/
	private String spokenLanguageType;

	/**
	 * 版权
	 **/
	private String copyright;

	/**
	 * 状态
	 **/
	private String isFinish;

	/**
	 * 年份
	 **/
	private String year;

	/**
	 * 季度
	 **/
	private String seasonMonth;

}
