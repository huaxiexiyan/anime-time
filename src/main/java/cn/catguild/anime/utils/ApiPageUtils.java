package cn.catguild.anime.utils;

import cn.catguild.anime.common.ApiPage;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.function.Function;

/**
 * @author xiyan
 * @date 2024/7/7 18:45
 */
@UtilityClass
public class ApiPageUtils {

	public static <R, T> ApiPage<T> toApiPage(IPage<R> page, Function<IPage<R>, List<T>> conversionFunction) {
		ApiPage<T> apiPage = new ApiPage<>();
		apiPage.setCurrent(page.getCurrent());
		apiPage.setSize(page.getSize());
		apiPage.setTotal(page.getTotal());
		// 接受一个函数，进行 records 转换
		apiPage.setRecords(conversionFunction.apply(page));
		return apiPage;
	}

}
