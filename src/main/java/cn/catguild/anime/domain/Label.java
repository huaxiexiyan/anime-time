package cn.catguild.anime.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * 标签
 *
 * @author xiyan
 * @date 2024/7/7 16:40
 */
@Data
public class Label {

	@TableId
	private Long id;

	private Long parentId;

	/**
	 * 标签名
	 */
	private String name;

}
