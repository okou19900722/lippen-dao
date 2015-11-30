package org.okou.lippen.dao.annotation.mybatis.provider;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import org.okou.lippen.commons.annotation.util.AnnotationUtils;
import org.okou.lippen.commons.util.string.StringUtils;
import org.okou.lippen.dao.annotation.generic.impl.LippenSQL;
import org.okou.lippen.dao.annotation.mybatis.LippenParam;
import org.okou.lippen.dao.annotation.mybatis.annotation.Table;

public class LippenGenericSqlProvider
{
	public <T> String findAll(LippenParam<T> p)
	{
		final String tableName = getTableName(p.getPo());
		return new LippenSQL<T>(){
			{
				SELECT("*");
				FROM(tableName);
			}
		}.toString();
	}
	public <T> String get(T p) throws IllegalAccessException
	{
		final String tableName = getTableName(p);
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
	public <T> String save(T p) throws IllegalAccessException
	{
		final String tableName = getTableName(p);
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
		System.err.println(s);
		return s;
	}
	//TODO 添加update 和 delete 的sql语句，然后返回
	//TODO 通过@link{TestTeacherMapper}进行单元测试
	
	
	private <T> String getTableName(T t)
	{
		if(!AnnotationUtils.hasAnnotation(t.getClass(), Table.class))
		{
			throw new RuntimeException("type " + t.getClass() + " need a annotation with org.okou.lippen.dao.annotation.mybatis.annotation.Table");
		}
		String table = t.getClass().getAnnotation(Table.class).value();
		table = "".equals(table) ? StringUtils.lowerCaseFirstWord(t.getClass().getSimpleName()) : table;
		return table;
	}
}
