package org.okou.lippen.dao.annotation.generic.impl;

import org.okou.lippen.dao.annotation.IGenericMapper;
import org.okou.lippen.dao.annotation.IMapperEntity;
import org.okou.lippen.dao.annotation.generic.GenericMapper;

/**
 * 抽象的model对象，annotation版model类如果希望使用通用provider来动态生成sql语句，继承本类
 * @author okou
 *
 * @date 2015年12月3日 下午5:36:03
 */
public abstract class AbstractMapperEntity implements IMapperEntity
{
	private static final long serialVersionUID = -8835127530266011504L;

	@Override
	public Class<? extends IGenericMapper> getMapperClass()
	{
		return GenericMapper.class;
	}
}
