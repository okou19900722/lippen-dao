package org.okou.lippen.dao.sqlmap.generic.impl;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.okou.lippen.dao.sqlmap.ISqlMapEntity;
import org.okou.lippen.dao.sqlmap.generic.IGenericSqlMapDao;

/**
 * mybatis实现的通用dao
 * @author okou
 *
 * @date 2015年12月3日 下午4:38:23
 */
public class MybatisGenericSqlMapDaoImpl<T extends ISqlMapEntity> extends
		SqlSessionDaoSupport implements IGenericSqlMapDao<T>
{

	@Override
	public List<T> find(T query)
	{
		return getSqlSession().selectList(query.selectId(), query);
	}

	@Override
	public List<T> find(T query, Integer firstResult, Integer maxResults)
	{
		return getSqlSession().selectList(query.selectId(), query, new RowBounds(firstResult, maxResults));
	}

	@Override
	public T get(T query)
	{
		return getSqlSession().selectOne(query.selectId(), query);
	}

	@Override
	public void save(T po)
	{
		getSqlSession().insert(po.insertId(), po);
	}

	@Override
	public int update(T po)
	{
		return getSqlSession().update(po.updateId(), po);
	}

	@Override
	public int delete(T po)
	{
		return getSqlSession().delete(po.deleteId(), po);
	}
}
