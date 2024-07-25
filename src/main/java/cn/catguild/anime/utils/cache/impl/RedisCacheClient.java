package cn.catguild.anime.utils.cache.impl;


import cn.catguild.anime.utils.cache.CacheClient;
import cn.catguild.anime.utils.JSONUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xiyan
 * @date 2023/9/26 11:28
 */
@Slf4j
@Component
@AllArgsConstructor
public class RedisCacheClient implements CacheClient {

	private RedisTemplate<String, Object> redisTemplate;

	@Override
	public boolean exists(String key) {
		Boolean isExist = redisTemplate.hasKey(key);
		return isExist != null && isExist;
	}

	@Override
	public List<String> getKeys(String pattern) {
		List<String> keys = new ArrayList<>();

		ScanOptions options = ScanOptions.scanOptions().match(pattern).count(1000).build();
		try (Cursor<?> cursor = redisTemplate.executeWithStickyConnection(redisConnection ->
			new ConvertingCursor<>(redisConnection.keyCommands().scan(options), redisTemplate.getKeySerializer()::deserialize)
		)) {
			while (cursor != null && cursor.hasNext()) {
				Object key = cursor.next();
				if (key != null) {
					keys.add(key.toString());
				}
			}
		} catch (Exception e) {
			// 处理异常，例如日志记录
			throw new RuntimeException("redis 获取 keys 出现错误！");
		}

		return keys;
	}

	@Override
	public void setValue(String key, Object value) {
		redisTemplate.opsForValue().set(key, value);
	}

	@Override
	public String getValue(String key) {
		Object obj = redisTemplate.opsForValue().get(key);
		return obj == null ? "{}" : JSONUtils.toJsonStr(obj);
	}


}
