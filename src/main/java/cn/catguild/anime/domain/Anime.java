package cn.catguild.anime.domain;

import cn.catguild.anime.common.AbstractEntity;
import cn.catguild.anime.domain.type.AnimeType;
import cn.catguild.anime.domain.type.PublishInfo;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

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
	 * 唯一id 对名称的哈希值
	 */
	private String hashId;

	/**
	 * 平台-番剧唯一id
	 */
	private Integer seasonId;

	/**
	 * 标题，原名
	 */
	private String title;

	/**
	 * 副标题
	 */
	private String subtitle;

	/**
	 * 链接处
	 */
	private String link;

	/**
	 * 封面图地址
	 */
	private String cover;

	/**
	 * 横板封面图片url
	 */
	private String horizontalPicture;

	/**
	 * 地区id
	 */
	@TableField(exist = false)
	private List<Area> areas;
	/**
	 * 风格标签
	 */
	@TableField(exist = false)
	private List<String> styles;
	/**
	 * 状态
	 */
	private Integer status;
	/**
	 * 剧集类型
	 */
	private AnimeType type;

	// 评价内容，或简介
	private String evaluate;

	/**
	 * 1、发行时间
	 */
	// @TableField(exist = false)
	private PublishInfo publish;

	// 角色声优
	// /**
	//  * 声优列表
	//  */
	// @TableField(exist = false)
	// private List<Professional> actorList;

	// private String getActors(){
	// 	if (CollectionUtils.isEmpty(this.actorList)){
	// 		return "";
	// 	}
	// }

	// /**
	//  * 制作团队列表
	//  */
	// @TableField(exist = false)
	// private List<Professional> staffList;

	// private String getStaff(){
	//
	// }

}
