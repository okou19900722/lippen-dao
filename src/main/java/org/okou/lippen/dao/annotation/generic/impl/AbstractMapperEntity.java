package org.okou.lippen.dao.annotation.generic.impl;

import org.okou.lippen.dao.annotation.IGenericMapper;
import org.okou.lippen.dao.annotation.IMapperEntity;
import org.okou.lippen.dao.annotation.generic.GenericMapper;

public abstract class AbstractMapperEntity implements IMapperEntity
{
	private static final long serialVersionUID = -8835127530266011504L;

	@Override
	public Class<? extends IGenericMapper> getMapperClass()
	{
		return GenericMapper.class;
	}
}
