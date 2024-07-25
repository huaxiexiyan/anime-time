package cn.catguild.anime.domain.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author xiyan
 * @date 2024/7/25 17:37
 */
@AllArgsConstructor
@Getter
public enum AnimeType {
	// 	1：番剧 2：电影 3：纪录片4：国创 5：电视剧 7：综艺
	DRAMA_SERIES(1),
	FILM(2),
	DOCUMENTARY(3),
	DOMESTIC_ANIMATION(4),
	TV_PLAY(5),
	VARIETY(7);

	private final int code;

	public static AnimeType parse(Integer type) {
		for (AnimeType value : AnimeType.values()) {
			if (value.code == type) {
				return value;
			}
		}
		return null;
	}
}
