package org.okou.lippen.delayed.dao;

import java.io.Serializable;

public interface ISqlMapDelayedUpdatable extends Serializable
{
	/**
	 * 获取sql map里面的update节点的id 便于延迟更新器进行批量update
	 * @returnsql map里面的update节点的id
	 *
	 *@author okou
	 *
	 *@date 2015年12月3日
	 */
	public String updateId();
}