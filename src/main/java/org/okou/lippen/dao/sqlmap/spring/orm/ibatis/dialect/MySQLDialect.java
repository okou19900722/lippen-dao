package org.okou.lippen.dao.sqlmap.spring.orm.ibatis.dialect;

import org.okou.lippen.dao.sqlmap.spring.orm.ibatis.Dialect;


public class MySQLDialect extends Dialect
{

	public boolean supportsLimitOffset()
	{
		return true;
	}

	public boolean supportsLimit()
	{
		return true;
	}

	public String getLimitString(String sql, int offset,
			String offsetPlaceholder, int limit, String limitPlaceholder)
	{
		if (offset > 0)
		{
			return sql + " limit " + offsetPlaceholder + "," + limitPlaceholder;
		}
		else
		{
			return sql + " limit " + limitPlaceholder;
		}
	}

}
