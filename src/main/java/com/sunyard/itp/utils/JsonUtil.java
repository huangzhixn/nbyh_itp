package com.sunyard.itp.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

public class JsonUtil {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = LoggerFactory.getLogger(JsonUtil.class);

	private JsonUtil() {
	}

	/**
	 * object to String
	 * 
	 * @param obj
	 * @return String
	 */
	public static String ObjectToString(Object obj) {
		try {
			return JSON.toJSONString(obj, SerializerFeature.WriteMapNullValue);
		} catch (Exception e) {
			logger.error("object to json error", e);
		}
		return null;
	}

	/**
	 * 将json string反序列化成对象
	 *
	 * @param json
	 * @param valueType
	 * @return
	 */
	public static <T> T StringJsonToObject(String json, Class<T> valueType) {
		try {
			return JSON.parseObject(json, valueType);
		} catch (Exception e) {
			logger.error("json to object error", e);
		}
		return null;
	}
}
