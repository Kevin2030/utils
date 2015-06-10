/*
 * Copyright (C) xxxx-2015 Your Company Inc.All Rights Reserved.
 * 
 * FileName：DBUtil.java
 * 
 * Description：JDBC封装工具类
 * 
 * History：
 * 1.0 Kai.Zhao 2015年6月10日 Create
 * 1.1 Kai.Zhao 2015年6月10日 实现增删改查
 */
package com.xxx.utils;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/** 
 * JDBC工具类
 * 
 * @author Kai.Zhao
 * @version 1.0
 * @see
 */

public class DBUtil {
	/*
	 * 默认值：没有查找到数据
	 */
	protected static final int DEFAULTVALUE = -1;

	/**
	 * 执行SQL Data Manipulation Language (DML) 脚本, 例如 <code>INSERT</code>, <code>UPDATE</code> 或
	 * <code>DELETE</code>.
	 * 
	 * @param conn
	 * @param sql
	 * @param params
	 * @return
	 * @throws SQLException
	 * @see
	 */
	public static int execute(Connection conn, String sql, Object... params) throws SQLException {
		PreparedStatement ps = null;
		try {
			if (conn == null)
				throw new SQLException("connection is null");
			conn.setAutoCommit(false);
			ps = conn.prepareStatement(sql);
			setPrepareStatementParams(ps, params);
			int execute = ps.executeUpdate();
			conn.commit();
			return execute;
		} catch (SQLException e) {
			if (conn != null)
				conn.rollback();
			throw e;
		} finally {
			if (ps != null)
				ps.close();
			if (conn != null)
				conn.close();
		}
	}

	/**
	 * 查找集合
	 * 
	 * @param conn
	 * @param handle
	 * @param sql
	 * @param params
	 * @return
	 * @throws SQLException
	 * @see
	 */
	public static <T> List<T> queryList(Connection conn, RowHandle<T> handle, String sql, Object... params)
			throws SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			if (conn == null)
				throw new SQLException("connection is null");
			ps = conn.prepareStatement(sql);
			setPrepareStatementParams(ps, params);
			rs = ps.executeQuery();
			List<T> list = new ArrayList<T>();
			while (rs.next())
				list.add(handle.execute(rs));
			return list;
		} finally {
			if (rs != null)
				rs.close();
			if (ps != null)
				ps.close();
			if (conn != null)
				conn.close();
		}
	}

	/**
	 * 查找对象
	 * 
	 * @param conn
	 * @param handle
	 * @param sql
	 * @param params
	 * @return
	 * @throws SQLException
	 * @see
	 */
	public static <T> T queryObject(Connection conn, RowHandle<T> handle, String sql, Object... params)
			throws SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			if (conn == null)
				throw new SQLException("connection is null");
			ps = conn.prepareStatement(sql);
			setPrepareStatementParams(ps, params);
			rs = ps.executeQuery();
			T clazz = null;
			while (rs.next())
				clazz = handle.execute(rs);
			return clazz;
		} finally {
			if (rs != null)
				rs.close();
			if (ps != null)
				ps.close();
			if (conn != null)
				conn.close();
		}
	}

	/**
	 * 查询Float
	 * 
	 * @param conn
	 * @param sql
	 * @param params
	 * @return
	 * @throws SQLException
	 * @see
	 */
	public static float queryFloat(Connection conn, String sql, Object... params) throws SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			if (conn == null)
				throw new SQLException("connection is null");
			ps = conn.prepareStatement(sql);
			setPrepareStatementParams(ps, params);
			rs = ps.executeQuery();
			float result = DEFAULTVALUE;
			while (rs.next())
				result = rs.getFloat(1);
			return result;
		} finally {
			if (rs != null)
				rs.close();
			if (ps != null)
				ps.close();
			if (conn != null)
				conn.close();
		}
	}

	/**
	 * 查询Integer
	 * 
	 * @param conn
	 * @param sql
	 * @param params
	 * @return
	 * @throws SQLException
	 * @see
	 */
	public static int queryInteger(Connection conn, String sql, Object... params) throws SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			if (conn == null)
				throw new SQLException("connection is null");
			ps = conn.prepareStatement(sql);
			setPrepareStatementParams(ps, params);
			rs = ps.executeQuery();
			int result = DEFAULTVALUE;
			while (rs.next())
				result = rs.getInt(1);
			return result;
		} finally {
			if (rs != null)
				rs.close();
			if (ps != null)
				ps.close();
			if (conn != null)
				conn.close();
		}
	}

	/**
	 * 查询String
	 * 
	 * @param conn
	 * @param sql
	 * @param params
	 * @return
	 * @throws SQLException
	 * @see
	 */
	public static String queryString(Connection conn, String sql, Object... params) throws SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			if (conn == null)
				throw new SQLException("connection is null");
			ps = conn.prepareStatement(sql);
			setPrepareStatementParams(ps, params);
			rs = ps.executeQuery();
			String result = "";
			while (rs.next())
				result = rs.getString(1);
			return result;
		} finally {
			if (rs != null)
				rs.close();
			if (ps != null)
				ps.close();
			if (conn != null)
				conn.close();
		}
	}

	public static interface RowHandle<T> {
		public T execute(ResultSet rs) throws SQLException;
	}

	private static void setPrepareStatementParams(PreparedStatement ps, Object... params) throws SQLException {
		if (ps != null) {
			int times = 1;
			for (Object val : params) {
				if (val instanceof Integer) {
					ps.setInt(times, (Integer) val);
				}
				if (val instanceof Short) {
					ps.setShort(times, (Short) val);
				}
				if (val instanceof Byte) {
					ps.setByte(times, (Byte) val);
				}
				if (val instanceof Float) {
					ps.setFloat(times, (Float) val);
				}
				if (val instanceof Long) {
					ps.setLong(times, (Long) val);
				}
				if (val instanceof Double) {
					ps.setDouble(times, (Double) val);
				}
				if (val instanceof String) {
					ps.setString(times, (String) val);
				}
				if (val instanceof Timestamp) {
					ps.setTimestamp(times, (Timestamp) val);
				}
				if (val instanceof Date) {
					ps.setDate(times, (Date) val);
				}
				if (val instanceof Integer) {
					ps.setInt(times, (Integer) val);
				}
				if (val instanceof byte[]) {
					ps.setBytes(times, (byte[]) val);
				}
				times++;
			}
		}
	}
}
