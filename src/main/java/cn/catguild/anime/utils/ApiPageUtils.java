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
		apiPage.setNum((int) page.getCurrent());
		apiPage.setSize((int) page.getSize());
		apiPage.setTotal((int) page.getTotal());
		// 计算是否有下一页
		apiPage.setHasNext(apiPage.getNum() * apiPage.getSize() > apiPage.getTotal() ? 1 : 0);
		// 接受一个函数，进行 records 转换
		apiPage.setList(conversionFunction.apply(page));
		return apiPage;
	}

}
