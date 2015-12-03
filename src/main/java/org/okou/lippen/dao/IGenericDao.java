package org.okou.lippen.dao;

import java.util.List;

/**
 * 通用dao接口
 * @author okou
 *
 * @date 2015年12月3日 下午4:15:58
 */
public interface IGenericDao<T>
{
	/**
	 * 通过pojo对象查找列表
	 */
	public List<T> find(T query);

	/**
	 * 通过pojo对象分页查找列表
	 */
	public List<T> find(T query, Integer firstResult, Integer maxResults);

	/**
	 * 通过pojo对象分页查找对象
	 */
	public T get(T query);

	/**
	 * 保存pojo对象
	 */
	public void save(T po);

	/**
	 * 更新pojo对象
	 */
	public int update(T po);

	/**
	 * 移除对象
	 */
	public int delete(T po);

	/**
	 * save pojo batch
	 */
//	public void save(final Collection<T> pos);
}
