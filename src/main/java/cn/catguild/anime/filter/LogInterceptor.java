package cn.catguild.anime.filter;

import cn.catguild.anime.constant.LogConstant;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.MDC;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.util.UUID;

/**
 * @author xiyan
 * @date 2023/8/17 14:55
 */
@Component
public class LogInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(@Nullable HttpServletRequest request,@Nullable HttpServletResponse response,@Nullable Object handler) throws Exception {
        MDC.put(LogConstant.TRACE_ID, generateTraceId());
        return true;
    }

    @Override
    public void postHandle(@Nullable HttpServletRequest request,@Nullable HttpServletResponse response,@Nullable Object handler,@Nullable ModelAndView modelAndView) throws Exception {
        // 清除 Trace ID，以避免内存泄漏
        MDC.remove(LogConstant.TRACE_ID);
    }

	private String generateTraceId() {
		return UUID.randomUUID().toString().replace("-", "");
	}

}
