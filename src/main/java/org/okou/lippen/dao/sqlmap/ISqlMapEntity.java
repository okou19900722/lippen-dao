package org.okou.lippen.dao.sqlmap;

import java.io.Serializable;

import org.okou.lippen.delayed.dao.ISqlMapDelayedInsertable;
import org.okou.lippen.delayed.dao.ISqlMapDelayedUpdatable;

/**
 * 通用dao的xml配置的model对象必须实现的接口，
 * 不管是ibatis还是mybatis，只要是通过xml配置sql语句，都需要实现本接口
 * @author okou
 *
 * @date 2015年12月3日 下午4:22:56
 */
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
