package cn.catguild.anime.config;

import cn.catguild.anime.utils.id.IdGenerator;
import cn.catguild.anime.utils.id.Snowflake;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author xiyan
 * @date 2024/7/7 18:51
 */
@Configuration
public class IdConfig {

	@Bean
	public IdGenerator idGenerator(){
		return new Snowflake(1,1,true);
	}

}
