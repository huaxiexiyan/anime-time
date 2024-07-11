package cn.catguild.anime.common;

import lombok.Data;

import java.util.List;

/**
 * @author xiyan
 * @date 2022-04-03 13:55
 */
@Data
public class ApiPage<T> {

	private int hasNext = 1;

	private List<T> list;

	private int num = 1;

	private int size = 10;

	private int total = 0;

	public ApiPage() {

	}

}
