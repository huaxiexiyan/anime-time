package cn.catguild.anime.config;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;


/**
 * @author xiyan
 * @date 2024/7/26 22:05
 */
@Configuration
public class CacheConfig {


	@Bean
	public Cache<String, String> cacheAnimeCondition() {
		return Caffeine.newBuilder()
			.initialCapacity(100)
			.maximumSize(500)
			.expireAfterAccess(10, TimeUnit.MINUTES)
			.build();
	}

}

