package org.okou.lippen.dao.sqlmap;

import java.io.Serializable;

import org.okou.lippen.delayed.dao.ISqlMapDelayedInsertable;
import org.okou.lippen.delayed.dao.ISqlMapDelayedUpdatable;

public interface ISqlMapEntity extends ISqlMapDelayedInsertable,
		ISqlMapDelayedUpdatable, Serializable
{
	/**
	 * 获取sql map里面的select节点的id
	 * 
	 * @return sql map里面的select节点的id
	 * @author EXvision
	 * @since 2013-2-22
	 */
	public String selectId();

	/**
	 * 获取sql map里面的insert节点的id
	 * 
	 * @return sql map里面的insert节点的id
	 * @author EXvision
	 * @since 2013-2-22
	 */
	public String insertId();

	/**
	 * 获取sql map里面的update节点的id
	 * 
	 * @return sql map里面的update节点的id
	 * @author EXvision
	 * @since 2012-11-28
	 */
	public String updateId();

	/**
	 * 获取sql map里面的delete节点的id
	 * 
	 * @return sql map里面的delete节点的id
	 * @author EXvision
	 * @since 2013-2-22
	 */
	public String deleteId();
}
