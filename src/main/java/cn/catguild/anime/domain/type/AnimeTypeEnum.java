package cn.catguild.anime.domain.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author xiyan
 * @date 2024/7/25 17:37
 */
@AllArgsConstructor
@Getter
public enum AnimeTypeEnum {
	// 	1：番剧 2：电影 3：纪录片4：国创 5：电视剧 7：综艺
	DRAMA_SERIES(1, "番剧"),
	FILM(2, "电影"),
	DOCUMENTARY(3, "纪录片4"),
	DOMESTIC_ANIMATION(4, "国创"),
	TV_PLAY(5, "电视剧"),
	VARIETY(7, "综艺");

	private final int code;
	private final String desc;

	public static AnimeTypeEnum parse(Integer code) {
		for (AnimeTypeEnum value : AnimeTypeEnum.values()) {
			if (value.code == code) {
				return value;
			}
		}
		throw new RuntimeException("地区枚举错误");
	}
}
