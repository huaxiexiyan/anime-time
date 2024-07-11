package cn.catguild.anime.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.slf4j.MDC;

/**
 * @author xiyan
 * @date 2022-03-12 17:49
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class ApiResponse<T> {

	/**
	 * 如果请求 0:成功,其他:失败
	 */
	private Integer code;

	/**
	 * 响应数据
	 */
	private T data;

	/**
	 * 向用户显示消息
	 */
	private String message;

	/**
	 * 方便后端排查：唯一的请求ID
	 */
	private String traceId;

	/**
	 * 方便后端排查：当前接入服务器的主机
	 */
	private String host;

	private ApiResponse() {}

	public ApiResponse(Integer code, String message, T data) {
		this.code = code;
		this.message = message;
		this.data = data;
		if (!isSuccess()) {
			this.traceId = MDC.get("traceId");
		}
	}

	public static <T> ApiResponse<T> ok() {
		return new ApiResponse<>(0, "success", null);
	}

	public static <T> ApiResponse<T> ok(T data) {
		return new ApiResponse<>(0, "success", data);
	}

	public static <T> ApiResponse<T> fail() {
		return new ApiResponse<>(-1, "一般错误", null);
	}

	public static <T> ApiResponse<T> fail(Integer code) {
		return new ApiResponse<>(code, "fail", null);
	}

	public static <T> ApiResponse<T> fail(String message) {
		return new ApiResponse<>(-1, message, null);
	}

	public static <T> ApiResponse<T> fail(Integer code, String message) {
		return new ApiResponse<>(code, message, null);
	}

	/**
	 * 判断当前请求是否成功
	 *
	 * @return 成功：true
	 */
	public boolean isSuccess() {
		return this.code != null && this.code == 0;
	}

}
