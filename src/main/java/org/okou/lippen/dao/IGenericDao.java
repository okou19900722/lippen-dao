package org.okou.lippen.dao;

import java.util.List;

public interface IGenericDao<T>
{
	/**
	 * find by pojo
	 */
	public List<T> find(T query);

	/**
	 * find by pojo with paged
	 */
	public List<T> find(T query, Integer firstResult, Integer maxResults);

	/**
	 * get pojo
	 */
	public T get(T query);

	/**
	 * save pojo
	 */
	public void save(T po);

	/**
	 * update pojo
	 */
	public int update(T po);

	/**
	 * remove
	 */
	public int delete(T po);

	/**
	 * save pojo batch
	 */
//	public void save(final Collection<T> pos);
}
