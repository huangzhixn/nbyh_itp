package com.sunyard.itp.cache;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sunyard.itp.utils.PropertyUtil;



/**
 * 
 * @Title: ConfCache.java
 * @Package com.sunyard.itp.cache
 * @Description: 配置文件内容缓存
 * @author 黄志鑫
 * @date 2017年8月2日 下午4:58:36
 * @version 1.0
 */
public class ConfCache
{
	private static volatile HashMap<String, String> CONF_CACHE;
	
	public static final String CONF_FILE_NAME = "conf.properties";
	
	private static final Logger logger = LoggerFactory.getLogger(ConfCache.class);
	
	private synchronized static void init()
	{
		if(CONF_CACHE == null || CONF_CACHE.isEmpty())
		{
			CONF_CACHE = new HashMap<>();
			logger.info("ConfCache start to load "+CONF_FILE_NAME);
			Properties p = PropertyUtil.getProperties(CONF_FILE_NAME);
			Enumeration e = p.keys();
			while(e.hasMoreElements())
			{
				String key =e.nextElement().toString();
				String value = p.get(key).toString();
				logger.debug("key:"+key+",value:"+value+"\n");
				CONF_CACHE.put(key,value);
			}
		}
	}
	
	public static String getValue(String key)
	{
		return getValue(key,null);
	}
	
	public static String getValue(String key,String defaultVaule)
	{
		if(CONF_CACHE == null || CONF_CACHE.isEmpty())
		{
			init();
		}
		String value = CONF_CACHE.get(key.trim());
		return value == null ? defaultVaule:value.trim();
	}	
}
