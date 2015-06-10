/*
 * Copyright (C) xxxx-2015 Your Company Inc.All Rights Reserved.
 * 
 * FileName：URLConnectionUtil.java
 * 
 * Description：网络请求
 * 
 * History：
 * 1.0 Kai.Zhao 2015年6月10日 Create
 * 1.1 Kai.Zhao 2015年6月10日 请求
 */
package com.xxx.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 网络请求工具
 * 
 * @author Kai.Zhao
 * @version 1.0
 * @see
 */

public class URLConnectionUtil {
	private static final Logger logger = LoggerFactory.getLogger(URLConnectionUtil.class);

	/**
	 * 连接超时时间
	 */
	private static final int CONNECTION_TIME_OUT = 50000;
	/**
	 * 读取流超时时间
	 */
	private static final int READ_TIME_OUT = 300000;

	/**
	 * URL请求
	 * 
	 * @param requestUrl URL地址
	 * @param method 请求方式 GET/POST...
	 * @param jsonString JSON字符串
	 * @return
	 * @throws Exception 
	 * @see
	 */
	public static String service(String requestUrl, String method, String jsonString) throws Exception {
		OutputStream os = null;
		BufferedReader br = null;
		try {
			URL url = new URL(requestUrl);
			HttpURLConnection.setFollowRedirects(false);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod(method);
			// conn.setRequestProperty("Content-Type", "text/html; charset=UTF-8");
			// conn.setRequestProperty("content_type", "application/x-www-form-urlencoded");
			logger.debug("URL Request :{}", jsonString);
			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.setConnectTimeout(CONNECTION_TIME_OUT);
			conn.setReadTimeout(READ_TIME_OUT);
			conn.connect();
			os = conn.getOutputStream();
			os.write(jsonString.getBytes("utf-8"));

			br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
			String line;
			StringBuffer result = new StringBuffer();
			while ((line = br.readLine()) != null) {
				result.append("\n").append(line);
			}
			logger.debug("URL Response :{}", result);
			return result.toString();
		} catch (Exception e) {
			return null;
		} finally {
			if (os != null)
				os.close();
			if (br != null)
				br.close();
		}
	}
}
