package org.okou.lippen.dao.sqlmap.spring.orm.ibatis;

import org.apache.log4j.Logger;
import org.okou.lippen.common.utils.BeanUtils;
import org.springframework.orm.ibatis.SqlMapClientFactoryBean;

import com.ibatis.common.logging.LogFactory;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.engine.execution.SqlExecutor;
import com.ibatis.sqlmap.engine.impl.SqlMapClientImpl;
import com.ibatis.sqlmap.engine.impl.SqlMapExecutorDelegate;

@SuppressWarnings("deprecation")
public class LippenSqlMapClientFactoryBean extends SqlMapClientFactoryBean
{
	private Logger logger = Logger.getLogger(getClass());

	private SqlExecutor sqlExecutor;

	public SqlExecutor getSqlExecutor()
	{
		return sqlExecutor;
	}

	public void setSqlExecutor(SqlExecutor sqlExecutor)
	{
		this.sqlExecutor = sqlExecutor;
	}

	@Override
	public void afterPropertiesSet() throws Exception
	{
		super.afterPropertiesSet();
		SqlMapClient c = (SqlMapClient) getObject();
		if (sqlExecutor != null && c instanceof SqlMapClientImpl)
		{
			LogFactory.selectLog4JLogging();

			SqlMapClientImpl client = (SqlMapClientImpl) c;
			SqlMapExecutorDelegate delegate = client.getDelegate();
			try
			{
				BeanUtils
						.forceSetProperty(delegate, "sqlExecutor", sqlExecutor);
				logger.info("[iBATIS] success set ibatis SqlMapClient.sqlExecutor = "
						+ sqlExecutor.getClass().getName());
			}
			catch (Exception e)
			{
				logger.error(
						"[iBATIS] error,cannot set ibatis SqlMapClient.sqlExecutor = "
								+ sqlExecutor.getClass().getName(), e);
			}
		}
	}
}
