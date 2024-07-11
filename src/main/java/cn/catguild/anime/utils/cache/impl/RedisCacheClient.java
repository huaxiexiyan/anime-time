package cn.catguild.anime.utils.cache.impl;


import cn.catguild.anime.utils.cache.CacheClient;
import cn.catguild.anime.utils.JSONUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
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
		try (Cursor<String> scan = redisTemplate.scan(ScanOptions.scanOptions().match(pattern).build())) {
			scan.stream().forEach(keys::add);
		}
		return keys;
	}

	@Override
	public void setValue(String key,Object value) {
		redisTemplate.opsForValue().set(key, value);
	}

	@Override
	public String getValue(String key) {
	    Object obj = redisTemplate.opsForValue().get(key);
	    return obj == null ? "{}" : JSONUtils.toJsonStr(obj);
	}


}
