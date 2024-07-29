package cn.catguild.anime.domain.type;

import cn.catguild.anime.common.AbstractEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author xiyan
 * @date 2024/7/26 21:32
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class AnimeStyle extends AbstractEntity {

	private String animeHashId;

	private String keyword;

	@TableField(exist = false)
	private String name;

}
