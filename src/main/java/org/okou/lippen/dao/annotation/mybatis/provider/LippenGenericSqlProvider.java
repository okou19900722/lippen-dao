package org.okou.lippen.dao.annotation.mybatis.provider;

import java.lang.reflect.InvocationTargetException;

import org.okou.lippen.dao.annotation.generic.impl.LippenSQL;
import org.okou.lippen.dao.annotation.mybatis.LippenParam;

/**
 * 通用mapper动态sql处理类
 * @author okou
 *
 * @date 2015年12月3日 下午5:22:52
 */
public class LippenGenericSqlProvider
{
	public <T> String findAll(LippenParam<T> p) throws SecurityException, IllegalArgumentException, NoSuchMethodException, IllegalAccessException, InvocationTargetException
	{
		String s =  new LippenSQL<T>(p){
			{
				SELECT("*");
				FROM(tableName);
				WHERE(getValueNotNullExpressions(fieldList, AND));
			}
		}.toString();
		System.err.println(s);
		return s;
	}
	public <T> String get(LippenParam<T> p) throws IllegalAccessException, SecurityException, IllegalArgumentException, NoSuchMethodException, InvocationTargetException
	{
		return new LippenSQL<T>(p){
			{
				SELECT("*");
				FROM(tableName);
				WHERE(getValueNotNullExpressions(fieldList, AND));
			}
		}.toString();
	}
	public <T> String save(LippenParam<T> p) throws IllegalAccessException, SecurityException, IllegalArgumentException, NoSuchMethodException, InvocationTargetException
	{
		return new LippenSQL<T>(p)
		{
			{
				INSERT_INTO(tableName);
				VALUES(getInsertColumnsExpressions(fieldList), getInsertValueExpressions(fieldList));
			}
		}.toString();
	}
	public <T> String update(LippenParam<T> p) throws SecurityException, IllegalArgumentException, NoSuchMethodException, IllegalAccessException, InvocationTargetException
	{
		
		return new LippenSQL<T>(p)
		{
			{
				UPDATE(tableName);
				SET(getValueNotNullExpressions(fieldList, ","));
				WHERE("id=#{po.id}");
			}
		}.toString();
	}
	public <T> String delete(LippenParam<T> p) throws SecurityException, IllegalArgumentException, NoSuchMethodException, IllegalAccessException, InvocationTargetException
	{
		return new LippenSQL<T>(p)
		{
			{
				DELETE_FROM(tableName);
				WHERE("id=#{po.id}");
			}
		}.toString();
	}
	
}
