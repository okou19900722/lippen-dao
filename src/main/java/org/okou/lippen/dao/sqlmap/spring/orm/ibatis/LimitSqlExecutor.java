package org.okou.lippen.dao.sqlmap.spring.orm.ibatis;


import java.sql.Connection;
import java.sql.SQLException;

import com.ibatis.sqlmap.engine.execution.SqlExecutor;
import com.ibatis.sqlmap.engine.mapping.statement.RowHandlerCallback;
import com.ibatis.sqlmap.engine.scope.StatementScope;

public class LimitSqlExecutor extends SqlExecutor
{
	Dialect dialect;

	@Override
	public void executeQuery(StatementScope statementScope, Connection conn,
			String sql, Object[] parameters, int skipResults, int maxResults,
			RowHandlerCallback callback) throws SQLException
	{
		if (dialect.supportsLimit()
				&& (skipResults != NO_SKIPPED_RESULTS || maxResults != NO_MAXIMUM_RESULTS))
		{
			sql = sql.trim();
			if (dialect.supportsLimitOffset())
			{
				sql = dialect.getLimitString(sql, skipResults, maxResults);
				skipResults = NO_SKIPPED_RESULTS;
			}
			else
			{
				sql = dialect.getLimitString(sql, 0, maxResults);
			}
			maxResults = NO_MAXIMUM_RESULTS;
		}
		super.executeQuery(statementScope, conn, sql, parameters, skipResults,
				maxResults, callback);
	}

	/**
	 * @param dialect
	 *            the dialect to set
	 */
	public void setDialect(Dialect dialect)
	{
		this.dialect = dialect;
	}

}
