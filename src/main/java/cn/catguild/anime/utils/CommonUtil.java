package cn.catguild.anime.utils;

import cn.catguild.anime.domain.type.Kv;
import com.google.common.base.CharMatcher;
import com.google.common.base.Splitter;
import lombok.experimental.UtilityClass;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author xiyan
 * @date 2024/7/29 15:20
 */
@UtilityClass
public class CommonUtil {

	public static Kv<Integer, Integer> parseIntRange(String range) {
		return parseRange(range, Integer::parseInt, Integer::parseInt);
	}

	public static Kv<String, String> parseStringRange(String range) {
		return parseRange(range, null, null);
	}

	/**
	 * 解析格式 [k,v)
	 *
	 * @param range [k,v)
	 * @return kv
	 */
	@SuppressWarnings("unchecked")
	public static <K, V> Kv<K, V> parseRange(String range, Function<String, K> kMap, Function<String, V> vMap) {
		// 去掉括号和空格
		String trimmed = CharMatcher.anyOf("[]() ").removeFrom(range);
		// 使用逗号分割
		List<String> ranges = Splitter.on(',').omitEmptyStrings().trimResults().splitToList(trimmed);

		if (ranges.size() == 2) {
			try {
				K k;
				V v;
				if (kMap == null) {
					k = (K) ranges.get(0);
				} else {
					k = kMap.apply(ranges.get(0));
				}
				if (vMap == null) {
					v = (V) ranges.get(1);
				} else {
					v = vMap.apply(ranges.get(1));
				}
				return new Kv<>(k, v);
			} catch (NumberFormatException e) {
				// 处理解析错误
				throw new IllegalArgumentException("Invalid range format: " + range, e);
			}
		} else {
			throw new IllegalArgumentException("Invalid range format: " + range);
		}
	}


	public static List<Integer> strToIntListAcceptEmpty(String str) {
		return strToList(str, ",", Integer::parseInt, true);
	}

	/**
	 * 带 分隔符 的字符串转 list
	 *
	 * @param str         带分隔符的字符串
	 * @param separator   分隔符
	 * @param map         转换对象的函数
	 * @param acceptEmpty 是否结束字符串为空
	 * @param <T>
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> List<T> strToList(String str, String separator, Function<String, T> map, boolean acceptEmpty) {
		if (!StringUtils.hasText(separator)) {
			throw new RuntimeException("字符串转数组失败，分隔符不能为空");
		}

		if (!StringUtils.hasText(str)) {
			// 其中有空
			if (acceptEmpty) {
				return new ArrayList<>();
			} else {
				throw new RuntimeException("字符串转数组失败，输入的字符串不能为空");
			}
		}
		List<String> result = Splitter.on(separator)
			.trimResults() // 去掉可能存在的空格
			.omitEmptyStrings() // 忽略空字符串
			.splitToList(str);
		if (map != null) {
			return result.stream()
				.map(map)
				.toList();
		}

		return (List<T>) result;
	}

	// public static void main(String[] args) {
	// 	System.out.println(CommonUtil.strToList("1,2,3",",",Integer::parseInt,true));//[1, 2, 3]
	// 	System.out.println(CommonUtil.strToList(",2,3",",",Integer::parseInt,true));//[2, 3]
	// 	System.out.println(CommonUtil.strToList("1,2,",",",Integer::parseInt,true));//[1, 2]
	// 	System.out.println(CommonUtil.strToList(",2,",",",Integer::parseInt,true));//[2]
	// 	System.out.println(CommonUtil.strToList(",2,",",",null,true));//[2]
	// }

	// public static void main(String[] args) {
	// 	String yearRange = "[2020,2023)";
	// 	Kv<Integer, Integer> kv = CommonUtil.parseRange(yearRange, Integer::parseInt, Integer::parseInt);
	// 	System.out.println("Start : " + kv.getK());
	// 	System.out.println("End : " + kv.getV());
	// }

}
