package org.okou.lippen.dao.annotation.mybatis.provider;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import org.okou.lippen.dao.annotation.generic.impl.LippenSQL;
import org.okou.lippen.dao.annotation.mybatis.LippenParam;
import org.okou.lippen.dao.annotation.mybatis.annotation.Table;
import org.okou.lippen.dao.annotation.mybatis.annotation.util.AnnotationValueUtil;

public class LippenGenericSqlProvider
{
	public <T> String findAll(LippenParam<T> p) throws SecurityException, IllegalArgumentException, NoSuchMethodException, IllegalAccessException, InvocationTargetException
	{
		Class<?> c = p.getClass();
		final String tableName = AnnotationValueUtil.getValue(c, Table.class, c.getSimpleName());
		return new LippenSQL<T>(){
			{
				SELECT("*");
				FROM(tableName);
			}
		}.toString();
	}
	public <T> String get(T p) throws IllegalAccessException, SecurityException, IllegalArgumentException, NoSuchMethodException, InvocationTargetException
	{
		Class<?> c = p.getClass();
		final String tableName = AnnotationValueUtil.getValue(c, Table.class, c.getSimpleName());
		Field[] fields = p.getClass().getDeclaredFields();
		final List<Field> fieldList = new ArrayList<Field>();
		for (Field field : fields)
		{
			if(!Modifier.isStatic(field.getModifiers()))
			{
				fieldList.add(field);
			}
		}
		return new LippenSQL<T>(new LippenParam<T>(p))
		{
			{
				SELECT(getColumns(fieldList));
				FROM(tableName);
				WHERE(getWhereSQL(fieldList, AND));
			}
		}.toString();
	}
	public <T> String save(T p) throws IllegalAccessException, SecurityException, IllegalArgumentException, NoSuchMethodException, InvocationTargetException
	{
		Class<?> c = p.getClass();
		final String tableName = AnnotationValueUtil.getValue(c, Table.class, c.getSimpleName());
		Field[] fields = p.getClass().getDeclaredFields();
		final List<Field> fieldList = new ArrayList<Field>();
		for (Field field : fields)
		{
			if(!Modifier.isStatic(field.getModifiers()))
			{
				fieldList.add(field);
			}
		}
		String s =  new LippenSQL<T>(new LippenParam<T>(p))
		{
			{
				INSERT_INTO(tableName);
				VALUES(getColumns(fieldList, true), getValuesSQL(fieldList, ","));
			}
		}.toString();
		return s;
	}
	//TODO 添加update 和 delete 的sql语句，然后返回
	public String update()
	{
		return null;
	}
	//TODO 通过@link{TestTeacherMapper}进行单元测试
	public String delete()
	{
		return null;
	}
	/**
	 * 默认非静态的变量都是column
	 * @param c
	 * @return
	 *
	 *@author 严尚君
	 *
	 *@date 2015-11-30
	 */
	private List<Field> getColumnField(Class<?> c)
	{
		Field[] fields = c.getDeclaredFields();
		List<Field> fieldList = new ArrayList<Field>();
		for (Field field : fields)
		{
			if(!Modifier.isStatic(field.getModifiers()))
			{
				fieldList.add(field);
			}
		}
		return fieldList;
	}
	
}
