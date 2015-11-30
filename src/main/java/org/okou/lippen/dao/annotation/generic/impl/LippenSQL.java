package org.okou.lippen.dao.annotation.generic.impl;

import java.lang.reflect.Field;
import java.util.List;

import org.apache.ibatis.jdbc.SQL;
import org.okou.lippen.dao.annotation.mybatis.LippenParam;

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

	protected String getColumns(List<Field> fields, boolean skipId)
	{
		if (fields == null || fields.size() == 0)
			return "*";
		StringBuffer sb = new StringBuffer();
		for (Field f : fields)
		{
			//TODO 通过ID注解来排队，不能通过名字
			if(skipId && f.getName().equals("id"))continue;
			if (sb.length() > 0)
				sb.append(",");
			//TODO 不能直接用字段名，要用column注解来取值
			sb.append(f.getName());
		}
		return sb.toString();
	}
	protected String getColumns(List<Field> fields)
	{
		return getColumns(fields, false);
	}

	protected String getWhereSQL(List<Field> fields, String prepend)
			throws IllegalArgumentException, IllegalAccessException
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
				//TODO 不能直接用字段名，要用column注解来取值
				sb.append(f.getName()).append("=").append(o);
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
