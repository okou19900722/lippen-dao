package org.okou.lippen.dao.sqlmap.generic.impl;

import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

import org.okou.lippen.dao.sqlmap.ISqlMapEntity;
import org.okou.lippen.dao.sqlmap.generic.IGenericSqlMapDao;
import org.springframework.orm.ibatis.SqlMapClientCallback;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.ibatis.sqlmap.client.SqlMapExecutor;

/**
 * ibatis 实现的通用dao
 * @author okou
 *
 * @date 2015年12月3日 下午4:39:24
 */
@SuppressWarnings("deprecation")
public class IBatisGenericSqlMapDaoImpl<T extends ISqlMapEntity> extends
		SqlMapClientDaoSupport implements IGenericSqlMapDao<T>
{
	@SuppressWarnings("unchecked")
	@Override
	public List<T> find(T query)
	{
		return getSqlMapClientTemplate().queryForList(query.selectId(), query);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> find(T query, Integer firstResult, Integer maxResults)
	{
		return getSqlMapClientTemplate().queryForList(query.selectId(), query,
				firstResult, maxResults);
	}

	@SuppressWarnings("unchecked")
	@Override
	public T get(T query)
	{
		return (T) getSqlMapClientTemplate().queryForObject(query.selectId(),
				query);
	}

	@Override
	public void save(T po)
	{
		getSqlMapClientTemplate().insert(po.insertId(), po);
	}

	@Override
	public int update(T po)
	{
		return getSqlMapClientTemplate().update(po.updateId(), po);
	}

	@Override
	public int delete(T po)
	{
		return getSqlMapClientTemplate().delete(po.deleteId(), po);
	}

//	@Override
//	@Transactional
	public void save(final Collection<T> pos)
	{
		getSqlMapClientTemplate().execute(new SqlMapClientCallback<Object>()
		{
			@Override
			public Object doInSqlMapClient(SqlMapExecutor executor)
					throws SQLException
			{
				executor.startBatch();

				for (T po : pos)
				{
					executor.insert(po.insertId(), po);
				}

				executor.executeBatch();

				return null;
			}
		});
	}
}
