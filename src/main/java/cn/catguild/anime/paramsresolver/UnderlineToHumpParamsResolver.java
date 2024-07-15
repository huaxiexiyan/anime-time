package cn.catguild.anime.paramsresolver;

import com.google.common.base.CaseFormat;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeansException;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.Iterator;

/**
 * 仅临时使用，需谨慎些
 *
 * @author xiyan
 * @date 2024/7/15 16:39
 */
@Component
public class UnderlineToHumpParamsResolver implements HandlerMethodArgumentResolver {

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return parameter.hasParameterAnnotation(ParameterConvertUnderlineToHump.class);
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
		Object obj = parameter.getParameterType().getDeclaredConstructor().newInstance();
		BeanWrapper wrapper = PropertyAccessorFactory.forBeanPropertyAccess(obj);
		Iterator<String> paramNames = webRequest.getParameterNames();
		while (paramNames.hasNext()) {
			String paramName = paramNames.next();
			Object o = webRequest.getParameter(paramName);
			try {
				if (StringUtils.hasText(paramName) && paramName.contains("_")) {
					wrapper.setPropertyValue(CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, paramName), o);
				} else {
					wrapper.setPropertyValue(paramName, o);

				}
			} catch (BeansException beansException) {

			}
		}
		return obj;
	}
}
