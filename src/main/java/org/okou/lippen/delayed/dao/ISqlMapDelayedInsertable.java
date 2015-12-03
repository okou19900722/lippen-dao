package org.okou.lippen.delayed.dao;

import java.io.Serializable;

public interface ISqlMapDelayedInsertable extends Serializable{
	/**
	 * 获取sql map里面的insert节点的id 便于延迟更新器进行批量insert
	 * 
	 * @return sql map里面的insert节点的id
	 * @author okou
	 * @since 2012-11-28
	 */
	public String insertId();
}
