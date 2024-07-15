package cn.catguild.anime.config;

import cn.catguild.anime.filter.LogInterceptor;
import cn.catguild.anime.paramsresolver.UnderlineToHumpParamsResolver;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * @author xiyan
 * @date 2023/8/17 15:01
 */
@AllArgsConstructor
@Configuration
public class WebConfig implements WebMvcConfigurer {

	private final LogInterceptor logInterceptor;

	private final UnderlineToHumpParamsResolver underlineToHumpParamsResolver;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(logInterceptor);
	}

	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
		resolvers.add(underlineToHumpParamsResolver);
	}

}
