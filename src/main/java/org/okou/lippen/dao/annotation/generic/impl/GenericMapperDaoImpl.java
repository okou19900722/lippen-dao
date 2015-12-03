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
/**
 * 通用annotation 版dao实现类，通过model对象获得mapper接口类，来调用对应方法 
 * @author okou
 *
 * @date 2015年12月3日 下午5:37:57
 */
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
				T po = parseToObject(clazz, map);
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
			Map<String, Object> map = session.getMapper(query.getMapperClass()).get(new LippenParam<T>(query));
			Class<T> clazz = (Class<T>) query.getClass();
			T po = parseToObject(clazz, map);
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
		session.getMapper(po.getMapperClass()).save(new LippenParam<T>(po));
	}

	@Override
	public int update(T po)
	{
		return session.getMapper(po.getMapperClass()).update(new LippenParam<T>(po));
	}

	@Override
	public int delete(T po)
	{
		Integer i = session.getMapper(po.getMapperClass()).delete(new LippenParam<T>(po));
		System.err.println(i);
		return i;
	}

	public void setSession(SqlSession session)
	{
		this.session = session;
	}
	
	private T parseToObject(Class<T> clazz, Map<String, Object> map) throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException, InstantiationException
	{
		if(map == null)return null;
		T po = clazz.newInstance();
		for(Entry<String, Object> e : map.entrySet())
		{
			Field f = clazz.getDeclaredField(e.getKey());
			f.setAccessible(true);
			f.set(po, e.getValue());
		}
		return po;
	}
}
