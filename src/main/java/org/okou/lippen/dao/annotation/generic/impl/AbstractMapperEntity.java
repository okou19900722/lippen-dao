package org.okou.lippen.dao.annotation.generic.impl;

import org.okou.lippen.dao.annotation.IGenericMapper;
import org.okou.lippen.dao.annotation.IMapperEntity;
import org.okou.lippen.dao.annotation.generic.GenericMapper;

public abstract class AbstractMapperEntity implements IMapperEntity
{
	@Override
	public Class<? extends IGenericMapper> getMapperClass()
	{
		return GenericMapper.class;
	}
}
