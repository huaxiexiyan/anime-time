package cn.catguild.anime.domain;

import cn.catguild.anime.common.AbstractEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 标签
 *
 * @author xiyan
 * @date 2024/7/7 16:40
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class AnimeCondition extends AbstractEntity {


	private Long parentId;

	/**
	 * 筛选名称
	 */
	private String name;

	/**
	 * 数据库字段名
	 */
	private String field;

	/**
	 * 数据库字段值
	 */
	private String keyword;

	/**
	 * 排序字段，值越大排序越靠后
	 */
	private Integer sort;

	private Integer isShow;

	@TableField(exist = false)
	private List<AnimeCondition> values;

}
