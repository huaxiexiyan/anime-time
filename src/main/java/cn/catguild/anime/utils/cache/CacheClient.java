package cn.catguild.anime.utils.cache;

import java.util.List;

/**
 * @author xiyan
 * @date 2023/9/26 11:20
 */
public interface CacheClient {

	/**
	 * 查询 缓存键是否存在
	 *
	 * @param key 缓存键
	 */
	boolean exists(String key);

	/**
	 * 模糊匹配缓存键
	 *
	 * @param pattern 模糊匹配
	 * @return
	 */
	List<String> getKeys(String pattern);

	/**
	 * 存储 string 类型，已经设置了默认的
	 * ps：如果已经设置了序列化方式，则不要对key于value再序列化，直接调用即可
	 * @param key
	 * @param value string值，若配置了json序列化，则不要转json再传
	 * @return
	 */
	void setValue(String key,Object value);

	/**
	 * 获取json字符串
	 *
	 * @param key
	 * @return
	 */
	String getValue(String key);


}
