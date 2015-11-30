package org.okou.lippen.dao.annotation.generic.impl;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.apache.ibatis.jdbc.SQL;
import org.okou.lippen.commons.annotation.util.AnnotationUtils;
import org.okou.lippen.dao.annotation.mybatis.LippenParam;
import org.okou.lippen.dao.annotation.mybatis.annotation.Column;
import org.okou.lippen.dao.annotation.mybatis.annotation.Id;
import org.okou.lippen.dao.annotation.mybatis.annotation.util.AnnotationValueUtil;

public class LippenSQL<T> extends SQL
{
	public static final String AND = ") \nAND (";
	public static final String OR = ") \nOR (";
	public static final String x = "'";
	
	protected LippenParam<T> param;

	public LippenSQL()
	{
	}
	public LippenSQL(LippenParam<T> p)
	{
		this.param = p;
	}

	protected String getColumns(List<Field> fields, boolean skipId) throws SecurityException, IllegalArgumentException, NoSuchMethodException, IllegalAccessException, InvocationTargetException
	{
		if (fields == null || fields.size() == 0)
			return "*";
		StringBuffer sb = new StringBuffer();
		for (Field f : fields)
		{
			if(AnnotationUtils.hasAnnotation(f, Id.class))continue;
			if (sb.length() > 0)
				sb.append(",");
			String columnName = AnnotationValueUtil.getValue(f, Column.class, f.getName(), false);
			sb.append(columnName);
		}
		return sb.toString();
	}
	protected String getColumns(List<Field> fields) throws SecurityException, IllegalArgumentException, NoSuchMethodException, IllegalAccessException, InvocationTargetException
	{
		return getColumns(fields, false);
	}

	protected String getWhereSQL(List<Field> fields, String prepend)
			throws IllegalArgumentException, IllegalAccessException, SecurityException, NoSuchMethodException, InvocationTargetException
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
				if (sb.length() > 0)
					sb.append(prepend);
				String columnName = AnnotationValueUtil.getValue(f, Column.class, f.getName(), false);
				sb.append(columnName).append("=").append(o);
			}
		}
		return sb.toString();
	}
	
	protected String getValuesSQL(List<Field> fields, String prepend) throws IllegalArgumentException, IllegalAccessException
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
				if (sb.length() > 0)
					sb.append(prepend);
				sb.append(x).append(o).append(x);
			}
		}
		return sb.toString();
	}
}
