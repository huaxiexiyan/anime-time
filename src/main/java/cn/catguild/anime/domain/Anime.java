package cn.catguild.anime.domain;

import cn.catguild.anime.common.AbstractEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 动漫
 *
 * @author xiyan
 * @date 2024/7/7 16:49
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class Anime extends AbstractEntity {

	/**
	 * 唯一id
	 */
	private Integer seasonId;

	/**
	 * 标题，原名
	 */
	private String title;

	/**
	 * 副标题
	 */
	private String subTitle;

	/**
	 * 链接处
	 */
	private String link;

	/**
	 * 封面图地址
	 */
	private String cover;

	/**
	 * 水平方向图
	 */
	private String horizontalPicture;


	/**
	 * 1、发行时间
	 */
	private LocalDateTime releaseTime;

	/**
	 * 简介
	 */
	private String summary;

	/**
	 * 风格类型：标签
	 */
	// private List<String> styleType;

	// 制作信息

	// 角色声优

}
