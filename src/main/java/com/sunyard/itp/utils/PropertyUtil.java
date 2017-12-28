package com.sunyard.itp.utils;

import java.io.IOException;
import java.net.URL;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;


/**
 * 
 * @author daox.nie
 * @created 2016年7月25日 下午1:52:36
 * @description property工具类
 */
public class PropertyUtil
{
	public static Properties getProperties(String filename)
	{
		if (StringUtils.isEmpty(filename))
		{
			throw new IllegalArgumentException("filename can not be null or empty");
		}

		filename = filename.trim();
		Properties p = new Properties();
		URL url = Thread.currentThread().getContextClassLoader().getResource(filename);
		if (url == null)
		{
			throw new IllegalStateException(filename + "不存在");
		}
		try
		{
			p.load(url.openStream());
		}
		catch (IOException e)
		{
			throw new RuntimeException("不能加载 " + filename, e);
		}
		return p;
	}
	
	/**
	 * 
	 * @param filename
	 * @param key 
	 * @return value
	 */
	public static String getValue(String key,String filename)
	{
		if (StringUtils.isEmpty(key) || StringUtils.isEmpty(filename))
		{
			throw new IllegalArgumentException("filename and key can not be null or empty");
		}

		key = key.trim();
		filename = filename.trim();
		Properties p = getProperties(filename);
		String value = p.getProperty(key);
		if (StringUtils.isNotEmpty(value))
		{
			value = value.trim();
		}
		return value;
	}

}
