package org.okou.lippen.dao.annotation.mybatis.handler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

public class LippenTypeHandler 
{

	public void setParameter(PreparedStatement ps, int i, Object parameter,
			JdbcType jdbcType) throws SQLException
	{
		System.out.println("1111111");
		
	}

	public Object getResult(ResultSet rs, String columnName)
			throws SQLException
	{
		System.out.println("2222222");
		return null;
	}

	public Object getResult(ResultSet rs, int columnIndex) throws SQLException
	{
		System.out.println("3333333");
		return null;
	}

	public Object getResult(CallableStatement cs, int columnIndex)
			throws SQLException
	{
		System.out.println("4444444");
		return null;
	}}
