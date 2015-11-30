package org.okou.lippen.dao.sqlmap.spring.orm.ibatis;

public abstract class Dialect
{

	public abstract boolean supportsLimit();

	public abstract boolean supportsLimitOffset();

	public String getLimitString(String sql, int offset, int limit)
	{
		return getLimitString(sql, offset, String.valueOf(offset), limit,
				String.valueOf(limit));
	}

	public abstract String getLimitString(String sql, int offset,
			String offsetPlaceholder, int limit, String limitPlaceholder);

}
