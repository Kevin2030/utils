/*
 * Copyright (C) xxxx-2015 Your Company Inc.All Rights Reserved.
 * 
 * FileName：ServletUtil.java
 * 
 * Description：Servlet工具类
 * 
 * History：
 * 1.0 Kai.Zhao 2015年6月10日 Create
 * 1.1 Kai.Zhao 2015年6月10日 Servlet容器基本操作
 */
package com.xxx.utils;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.Date;
import java.util.Enumeration;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;

/**
 * Servlet操作工具类
 * 
 * @author Kai.Zhao
 * @version 1.0
 * @see
 */

public class ServletUtil {
	/**
	 * 获取String
	 * 
	 * @param request
	 * @param paramName 关键字
	 * @param defaultValue 默认值
	 * @return 
	 * @see
	 */
	public static String getString(HttpServletRequest request, String paramName, String defaultValue) {
		String result = request.getParameter(paramName);
		if (result == null || "".equals(result.trim()))
			result = defaultValue;
		result = escape(result);
		return result;
	}

	/**
	 * 获取 Session String
	 * 
	 * @param request
	 * @param paramName
	 * @param defaultValue
	 * @return 
	 * @see
	 */
	public static String getSessionString(HttpServletRequest request, String paramName, String defaultValue) {
		Object param = request.getSession().getAttribute(paramName);
		if (param == null) {
			return defaultValue;
		}
		String result = param.toString();
		if (StringUtils.isBlank(result)) {
			result = defaultValue;
		}
		result = escape(result);
		return result;
	}

	/**
	 * 删除 Session Attribute
	 * 
	 * @param request
	 * @param paramName 
	 * @see
	 */
	public static void removeSessionAttribute(HttpServletRequest request, String paramName) {
		request.getSession().removeAttribute(paramName);
	}

	/**
	 * 获取 Session Long
	 * 
	 * @param request
	 * @param paramName
	 * @param defaultValue
	 * @return 
	 * @see
	 */
	public static long getSessionLong(HttpServletRequest request, String paramName, long defaultValue) {
		Object param = request.getSession().getAttribute(paramName);
		if (param == null) {
			return defaultValue;
		}
		try {
			long result = Long.valueOf(param.toString());
			return result;
		} catch (Exception e) {
			return defaultValue;
		}
	}

	/**
	 * 验证X-Token
	 * 
	 * @param request
	 * @return 
	 * @see
	 */
	public static boolean verifyToken(HttpServletRequest request) {
		// 经过两次加密
		String sessionToken = new String(DigestUtils.md5(DigestUtils.md5(ServletUtil.getSessionString(request,
				"X-Token", ""))));
		if (StringUtils.isBlank(sessionToken)) {
			return false;
		}
		// 头部
		String headToken = request.getHeader("X-Token");
		if (StringUtils.isBlank(headToken)) {
			return false;
		}
		return sessionToken.equals(headToken);
	}

	/**
	 * 设置 Session
	 * 
	 * @param request
	 * @param attributeName
	 * @param attributeValue
	 * @return 
	 * @see
	 */
	public static void setSessionAttribute(HttpServletRequest request, String attributeName, Object attributeValue) {
		if (StringUtils.isBlank(attributeName) || attributeValue == null
				|| StringUtils.isBlank(attributeValue.toString())) {
			return;
		}
		request.getSession().setAttribute(attributeName, attributeValue);
	}

	/**
	 * 获取DateString
	 * 
	 * @param request
	 * @param paramName 关键字
	 * @return 
	 * @see
	 */
	public static String getDateString(HttpServletRequest request, String paramName) {
		String result = request.getParameter(paramName);
		if (result == null || "".equals(result.trim())) {
			result = "";
		} else {
			result = result.replaceAll("-", "");
			result = result.replaceAll("/", "");
		}
		return result;
	}

	/**
	 * 获取Date
	 * 
	 * @param request
	 * @param paramName 关键字
	 * @param defaultValue 默认值
	 * @param format 日期格式
	 * @return 
	 * @see
	 */
	public static Date getDate(HttpServletRequest request, String paramName, Date defaultValue, String format) {
		String ori = request.getParameter(paramName);
		if (StringUtils.isBlank(ori))
			return defaultValue;
		try {
			return DateUtils.parseDate(ori, format);
		} catch (Exception e) {
			return defaultValue;
		}
	}

	/**
	 * 遍历Request
	 * 
	 * @param request
	 * @param paramName 关键字
	 * @return 
	 * @see
	 */
	public static String getParameterIgnoreCase(HttpServletRequest request, String paramName) {
		for (Enumeration<String> em = request.getParameterNames(); em.hasMoreElements();) {
			String name = (String) em.nextElement();
			if (name.equalsIgnoreCase(paramName))
				return request.getParameter(name);
		}
		return null;
	}

	/**
	 * 获取String
	 * 
	 * @param request
	 * @param paramName 关键字
	 * @return 
	 * @see
	 */
	public static String getString(HttpServletRequest request, String paramName) {
		return getString(request, paramName, "");
	}

	/**
	 * 获取数组
	 * 
	 * @param request
	 * @param paramName 关键字
	 * @return 
	 * @see
	 */
	public static String[] getStrings(HttpServletRequest request, String paramName) {
		return request.getParameterValues(paramName);
	}

	/**
	 * 获取Intger
	 * 
	 * @param request
	 * @param paramName 关键字
	 * @param defaultValue 默认值
	 * @return 
	 * @see
	 */
	public static Integer getInteger(HttpServletRequest request, String paramName, Integer defaultValue) {
		Integer result = null;
		if (paramName == null || paramName.trim().length() == 0)
			return defaultValue;
		String paramValue = request.getParameter(paramName);
		if (StringUtils.isBlank(paramValue))
			return defaultValue;
		try {
			result = Integer.valueOf(paramValue);
		} catch (NumberFormatException e) {
			e.printStackTrace();
			result = defaultValue;
		}
		return result;
	}

	/**
	 * 获取int
	 * 
	 * @param request
	 * @param paramName 关键字
	 * @param defaultValue 默认值
	 * @return 
	 * @see
	 */
	public static int getInt(HttpServletRequest request, String paramName, int defaultValue) {
		if (paramName == null || paramName.trim().length() == 0)
			return defaultValue;
		String paramValue = request.getParameter(paramName);
		if (StringUtils.isBlank(paramValue))
			return defaultValue;
		try {
			return Integer.parseInt(request.getParameter(paramName));
		} catch (NumberFormatException e) {
			return defaultValue;
		}
	}

	/**
	 * 获取float
	 * 
	 * @param request
	 * @param paramName 关键字
	 * @param defaultValue 默认值
	 * @return 
	 * @see
	 */
	public static float getFloat(HttpServletRequest request, String paramName, int defaultValue) {
		if (paramName == null || paramName.trim().length() == 0)
			return defaultValue;
		String paramValue = request.getParameter(paramName);
		if (StringUtils.isBlank(paramValue))
			return defaultValue;
		return Float.parseFloat(request.getParameter(paramName));
	}

	/**
	 * 获取long
	 * 
	 * @param request
	 * @param paramName 关键字
	 * @param defaultValue 默认值
	 * @return 
	 * @see
	 */
	public static long getLong(HttpServletRequest request, String paramName, long defaultValue) {
		try {
			if (paramName == null || paramName.trim().length() == 0)
				return defaultValue;
			return Long.parseLong(request.getParameter(paramName));
		} catch (NumberFormatException e) {
			return defaultValue;
		}
	}

	/**
	 * 获取Integer数组
	 * 
	 * @param request
	 * @param paramName 关键字
	 * @param defaultValue 默认值
	 * @return 
	 * @see
	 */
	public static Integer[] getIntegers(HttpServletRequest request, String paramName, Integer defaultValue) {
		String[] temp = request.getParameterValues(paramName);
		if (temp != null) {
			Integer[] result = new Integer[temp.length];
			for (int i = 0; i < result.length; i++)
				try {
					result[i] = Integer.valueOf(temp[i]);
				} catch (NumberFormatException e) {
					result[i] = defaultValue;
				}
			return result;
		} else
			return new Integer[0];
	}

	/**
	 * 获取long数组
	 * 
	 * @param request
	 * @param paramName 关键字
	 * @param defaultValue 默认值
	 * @return 
	 * @see
	 */
	public static Long[] getLongs(HttpServletRequest request, String paramName, Integer defaultValue) {
		String[] temp = request.getParameterValues(paramName);
		if (temp != null) {
			Long[] result = new Long[temp.length];
			for (int i = 0; i < result.length; i++)
				try {
					result[i] = Long.valueOf(temp[i]);
				} catch (NumberFormatException e) {
					String de = String.valueOf(defaultValue);
					long dd = Long.valueOf(de);
					result[i] = dd;
				}
			return result;
		} else
			return new Long[0];
	}

	/**
	 * 获取long数组
	 * 
	 * @param request
	 * @param paramName 关键字
	 * @param split 分割标识
	 * @return 
	 * @see
	 */
	public static Long[] getLongs(HttpServletRequest request, String paramName, String split) {
		String temp = request.getParameter(paramName);
		if (temp != null) {
			String[] a = temp.split(split);
			if (a == null || a.length == 0)
				return new Long[0];
			Long[] res = new Long[a.length];
			for (int i = 0; i < a.length; i++) {
				res[i] = Long.parseLong(a[i]);
			}
			return res;
		} else
			return new Long[0];
	}

	/**
	 * 获取BigDecimal
	 * 
	 * @param request
	 * @param paramName 关键字
	 * @param defaultValue 默认值
	 * @return 
	 * @see
	 */
	public static BigDecimal getBigDecimal(HttpServletRequest request, String paramName, BigDecimal defaultValue) {
		BigDecimal result = null;
		try {
			result = new BigDecimal(request.getParameter(paramName));
		} catch (RuntimeException e) {
			result = defaultValue;
		}
		return result;
	}

	/**
	 * 获取Boolean
	 * 
	 * @param request
	 * @param paramName 关键字
	 * @param defaultValue 默认值
	 * @return 
	 * @see
	 */
	public static Boolean getBoolean(HttpServletRequest request, String paramName, Boolean defaultValue) {
		Boolean result = null;
		try {
			result = Boolean.valueOf(request.getParameter(paramName));
		} catch (NumberFormatException e) {
			result = defaultValue;
		}
		return result;
	}

	/**
	 * 获取Cookie
	 * 
	 * @param request
	 * @param name 关键字
	 * @return 
	 * @see
	 */
	public static String getCookieValue(HttpServletRequest request, String name) {
		Cookie[] cookies = request.getCookies();
		if (cookies != null)
			for (Cookie cookie : cookies)
				if (cookie.getName().equals(name))
					return cookie.getValue();
		return null;
	}

	/**
	 * 设置Cookie
	 * 
	 * @param request
	 * @param name 关键字
	 * @param value 值 
	 * @see
	 */
	public static void setCookieValue(HttpServletRequest request, String name, String value) {
		Cookie[] cookies = request.getCookies();
		if (cookies != null)
			for (Cookie cookie : cookies)
				if (cookie.getName().equals(name))
					cookie.setValue(value);
	}

	/**
	 * 获取String数组
	 * 
	 * @param request
	 * @param param 关键字
	 * @param split 分割标识
	 * @return 
	 * @see
	 */
	public static String[] getStrings(HttpServletRequest request, String param, String split) {
		String raw = request.getParameter(param);
		if (raw != null) {
			String[] strs = raw.split(split);
			if (strs == null)
				return new String[0];
			return strs;
		} else
			return new String[0];
	}

	public static String escape(String html) {
		if (html == null)
			return null;
		String result = html.replace("<", "");
		result = result.replace(">", "");
		result = result.replace("&", "");
		result = result.replace("\"", "");
		result = result.replace("'", "");
		return result;
	}

	/**
	 * 组织URL
	 * 
	 * @param request
	 * @param sourceUrl
	 * @param fromUrl
	 * @return
	 * @throws UnsupportedEncodingException 
	 * @see
	 */
	public static String buildAwareUrl(HttpServletRequest request, String sourceUrl, String fromUrl)
			throws UnsupportedEncodingException {
		String queryString = request.getQueryString();

		String url = null;
		if (StringUtils.isBlank(fromUrl)) {
			url = sourceUrl;
		} else {
			String encodeUrl = null;

			if (StringUtils.isBlank(queryString)) {
				encodeUrl = URLEncoder.encode(fromUrl, "utf-8");
			} else {
				encodeUrl = URLEncoder.encode(fromUrl + "?" + queryString, "utf-8");
			}

			url = sourceUrl + "?url=" + encodeUrl;
		}

		return url;
	}

	/**
	 * 获取double
	 * 
	 * @param request
	 * @param paramName 关键字
	 * @param defaultValue 默认值
	 * @return 
	 * @see
	 */
	public static double getDouble(HttpServletRequest request, String paramName, double defaultValue) {
		Double result = null;
		if (paramName == null || paramName.trim().length() == 0)
			return defaultValue;

		String paramValue = request.getParameter(paramName);
		if (StringUtils.isBlank(paramValue))
			return defaultValue;
		try {
			result = Double.parseDouble(paramValue);
		} catch (NumberFormatException e) {
			result = defaultValue;
		}
		return result;
	}
}
