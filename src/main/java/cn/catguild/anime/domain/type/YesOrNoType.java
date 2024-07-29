package cn.catguild.anime.domain.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author xiyan
 * @date 2024/7/26 15:29
 */
@AllArgsConstructor
@Getter
public enum YesOrNoType {
	YES(1),
	NO(0);

	private final int code;

	public static YesOrNoType parse(Integer type) {
		for (YesOrNoType value : YesOrNoType.values()) {
			if (value.code == type) {
				return value;
			}
		}
		return null;
	}
}
