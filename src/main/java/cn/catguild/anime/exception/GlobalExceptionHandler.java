package cn.catguild.anime.exception;

import cn.catguild.anime.common.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author xiyan
 * @date 2024/7/7 19:03
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

	/**
	 * 处理自定义的业务异常
	 *
	 * @param req
	 * @param e
	 * @return
	 */
	@ExceptionHandler(value = BizException.class)
	@ResponseBody
	public ApiResponse<Void> bizExceptionHandler(HttpServletRequest req, BizException e) {
		log.error("发生业务异常！原因是：{}", e.getMsg());
		return ApiResponse.fail(e.getCode(), e.getMsg());
	}

	/**
	 * 处理空指针的异常
	 *
	 * @param req
	 * @param e
	 * @return
	 */
	@ExceptionHandler(value = NullPointerException.class)
	@ResponseBody
	public ApiResponse<Void> exceptionHandler(HttpServletRequest req, NullPointerException e) {
		log.error("发生空指针异常！原因是:", e);
		return ApiResponse.failMessage("发生空指针异常！原因是:");
	}

	/**
	 * 处理其他异常
	 *
	 * @param req
	 * @param e
	 * @return
	 */
	@ExceptionHandler(value = Exception.class)
	@ResponseBody
	public ApiResponse<Void> exceptionHandler(HttpServletRequest req, Exception e) {
		log.error("未知异常！原因是:", e);
		return ApiResponse.failMessage("未知异常");
	}

}

