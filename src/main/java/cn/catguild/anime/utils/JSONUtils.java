package cn.catguild.anime.utils;

import cn.catguild.anime.utils.transplant.CustomJavaTimeModule;
import cn.catguild.anime.utils.transplant.DateUtil;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.json.JsonReadFeature;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.type.TypeFactory;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.*;

/**
 * @author xiyan
 * @date 2022-04-25 15:29
 */
@Slf4j
@UtilityClass
public class JSONUtils {

	public static ObjectMapper getInstance() {
		return JacksonHolder.INSTANCE;
	}

	/**
	 *
	 * @param jsonStr
	 * @param valueTypeRef
	 * @return
	 * @param <T>
	 */
	public static <T> List<T> toList(String jsonStr, Class<T> valueTypeRef) {
		try {
			List<Map<String, Object>> list = getInstance().readValue(jsonStr,
				TypeFactory.defaultInstance().constructCollectionType(List.class, Map.class));
			List<T> result = new ArrayList<>();

			for (Map<String, Object> map : list) {
				result.add(toPojo(map, valueTypeRef));
			}
			return result;
		} catch (JsonProcessingException e) {
			log.error("jackson 序列化失败，输入【{}】 目标类型【{}】", jsonStr, valueTypeRef, e);
			throw new RuntimeException(e);
		}
	}


	/**
	 *
	 * @param obj
	 * @param valueTypeRef
	 * @return
	 * @param <T>
	 */
	public static <T> T toPojo(Object obj, Class<T> valueTypeRef) {
		// 如果 obj 是 JSON 字符串，使用 readValue 方法
		if (obj instanceof String) {
			try {
				return getInstance().readValue((String) obj, valueTypeRef);
			} catch (IOException e) {
				log.error("jackson 序列化失败，输入【{}】 目标类型【{}】", obj, valueTypeRef, e);
				throw new RuntimeException(e);
			}
		}
		// 否则，使用 convertValue 方法
		return getInstance().convertValue(obj, valueTypeRef);
	}

	/**
	 *  序列化对象为JSON字符串
	 * @param obj
	 * @return
	 */
	public static String toJsonStr(Object obj) {
		try {
			return getInstance().writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			log.error("jackson 序列化失败", e);
			throw new RuntimeException(e);
		}
	}

	/**
	 * 将json对象读出来
	 *
	 * @param jsonValue
	 * @return
	 */
	public static JsonNode toJsonTree(String jsonValue) {
		try {
			return getInstance().readTree(jsonValue);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
	}

	private static class JacksonHolder {
		private static final ObjectMapper INSTANCE = new JacksonObjectMapper();
	}

	public static class JacksonObjectMapper extends ObjectMapper {
		private static final long serialVersionUID = 4288193147502386170L;

		private static final Locale CHINA = Locale.CHINA;

		public JacksonObjectMapper() {
			super();
			// 设置地点为中国
			super.setLocale(CHINA);
			// 去掉默认的时间戳格式
			super.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
			// 设置为中国上海时区
			super.setTimeZone(TimeZone.getTimeZone(ZoneId.systemDefault()));
			// 序列化时，日期的统一格式
			super.setDateFormat(new SimpleDateFormat(DateUtil.PATTERN_DATETIME, Locale.CHINA));
			// 序列化处理
			super.configure(JsonReadFeature.ALLOW_UNESCAPED_CONTROL_CHARS.mappedFeature(), true);
			super.configure(JsonReadFeature.ALLOW_BACKSLASH_ESCAPING_ANY_CHARACTER.mappedFeature(), true);
			super.findAndRegisterModules();
			// 失败处理(允许字段不一致)
			super.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
			super.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			// 单引号处理
			super.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
			// 反序列化时，属性不存在的兼容处理s
			super.getDeserializationConfig().withoutFeatures(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
			// 日期格式化
			super.registerModule(new CustomJavaTimeModule());
			super.findAndRegisterModules();
		}

		@Override
		public ObjectMapper copy() {
			return super.copy();
		}
	}


}
