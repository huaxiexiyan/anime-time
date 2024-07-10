package cn.catguild.anime.common;

import lombok.Data;

import java.util.List;

/**
 * @author xiyan
 * @date 2022-04-03 13:55
 */
@Data
public class ApiPage<T> {

	private List<T> records;

	private long current = 1;

	private long size = 10;

	private long total = 0;

	public ApiPage() {

	}

}
