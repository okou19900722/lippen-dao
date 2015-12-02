package org.okou.lippen.dao.annotation.generic.impl;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.jdbc.SQL;
import org.okou.lippen.dao.annotation.mybatis.LippenParam;
import org.okou.lippen.dao.annotation.mybatis.annotation.Column;
import org.okou.lippen.dao.annotation.mybatis.annotation.Table;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.StringUtils;

public class LippenSQL<T> extends SQL
{
	protected static final String AND = ") \nAND (";
	protected static final String OR = ") \nOR (";
	protected static final String FRONT = "#{po.";
	protected static final String BACK = "}";
	protected static final String SEPARATOR = "`";
	
	protected LippenParam<T> param;
	protected String tableName;
	protected T t;
	protected List<Field> fieldList;

	public LippenSQL(LippenParam<T> p) throws SecurityException, IllegalArgumentException, NoSuchMethodException, IllegalAccessException, InvocationTargetException
	{
		this.param = p;
		t = p.getPo();
		Class<?> c = t.getClass();
		Annotation annotation = AnnotationUtils.getAnnotation(c, Table.class);
		tableName = getValue(annotation, c);
		fieldList = getColumnFields(c);
	}

	protected String getInsertColumnsExpressions(List<Field> fields) throws IllegalArgumentException, IllegalAccessException
	{
		if (fields == null || fields.size() == 0)
			return "";
		StringBuffer sb = new StringBuffer();
		T t = param.getPo();
		for (Field f : fields)
		{
			f.setAccessible(true);
			Object o = f.get(t);
			if (o != null)
			{
				String columnName = getValue(f.getAnnotation(Column.class), f);
				if (sb.length() > 0)
					sb.append(",");
				sb.append(SEPARATOR).append(columnName).append(SEPARATOR);
			}
		}
		return sb.toString();
	}
	protected String getInsertValueExpressions(List<Field> fields) throws IllegalArgumentException, IllegalAccessException
	{
		if (fields == null || fields.size() == 0)
			return "";
		StringBuffer sb = new StringBuffer();
		T t = param.getPo();
		for (Field f : fields)
		{
			f.setAccessible(true);
			Object o = f.get(t);
			if (o != null)
			{
				String columnName = getValue(f.getAnnotation(Column.class), f);
				if (sb.length() > 0)
					sb.append(",");
				sb.append(FRONT).append(columnName).append(BACK);
			}
		}
		return sb.toString();
	}
	
	protected String getValueNotNullExpressions(List<Field> fields, String prepend) throws IllegalArgumentException, IllegalAccessException
	{
		if (fields == null || fields.size() == 0)
			return "";
		StringBuffer sb = new StringBuffer();
		T t = param.getPo();
		for (Field f : fields)
		{
			f.setAccessible(true);
			Object o = f.get(t);
			if (o != null)
			{
				String columnName = getValue(f.getAnnotation(Column.class), f);
				if (sb.length() > 0)
					sb.append(prepend);
				sb.append(columnName).append("=").append(FRONT).append(columnName).append(BACK);
			}
		}
		return sb.toString();
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
	protected List<Field> getColumnFields(Class<?> c)
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
	protected String getValue(Annotation annotation, Field field)
	{
		return getValue(annotation, StringUtils.uncapitalize(field.getName()));
	}
	protected String getValue(Annotation annotation, Class<?> type)
	{
		return getValue(annotation, StringUtils.uncapitalize(type.getSimpleName()));
	}
	protected String getValue(Annotation annotation, String defaultValue)
	{
		if(annotation == null)return defaultValue;
		String str = (String) AnnotationUtils.getValue(annotation);
		return str == null || "".equals(str) ? defaultValue : str;
	}
}
