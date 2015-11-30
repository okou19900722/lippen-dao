package org.okou.lippen.dao.annotation.generic.impl;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSession;
import org.okou.lippen.dao.annotation.IMapperEntity;
import org.okou.lippen.dao.annotation.generic.IGenericMapperDao;
import org.okou.lippen.dao.annotation.mybatis.LippenParam;
@SuppressWarnings("unchecked")
public class GenericMapperDaoImpl<T extends IMapperEntity> implements IGenericMapperDao<T>
{
	@Resource
	private SqlSession session;

	@Override
	public List<T> find(T query)
	{
		return find(query, null, null);
	}

	@Override
	public List<T> find(T query, Integer firstResult, Integer maxResults)
	{
		try{
			List<Map<String, Object>> list = session.getMapper(query.getMapperClass()).find(new LippenParam<T>(query, firstResult, maxResults));
			Class<T> clazz = (Class<T>) query.getClass();
			List<T> result = new ArrayList<T>();
			for (Map<String, Object> map : list)
			{
				T po = clazz.newInstance();
				parseToObject(po, clazz, map);
				result.add(po);
			}
			return result;
		}
		catch(Exception e)
		{
			throw new RuntimeException(e);
		}
	}

	@Override
	public T get(T query)
	{
		try{
			Map<String, Object> map = session.getMapper(query.getMapperClass()).get(query);
			Class<T> clazz = (Class<T>) query.getClass();
			T po = clazz.newInstance();
			parseToObject(po, clazz, map);
			return po;
		}
		catch(Exception e)
		{
			throw new RuntimeException(e);
		}
	}

	@Override
	public void save(T po)
	{
		session.getMapper(po.getMapperClass()).save(po);
	}

	@Override
	public int update(T po)
	{
		return session.getMapper(po.getMapperClass()).update(po);
	}

	@Override
	public int delete(T po)
	{
		return session.getMapper(po.getMapperClass()).delete(po);
	}

	public void setSession(SqlSession session)
	{
		this.session = session;
	}

	
	
	private void parseToObject(T po, Class<T> clazz, Map<String, Object> map) throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException
	{
		for(Entry<String, Object> e : map.entrySet())
		{
			Field f = clazz.getDeclaredField(e.getKey());
			f.setAccessible(true);
			f.set(po, e.getValue());
		}
		
	}
}
