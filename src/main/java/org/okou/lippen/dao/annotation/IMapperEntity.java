package org.okou.lippen.dao.annotation;

import java.io.Serializable;

public interface IMapperEntity extends Serializable
{

	public Class<? extends IGenericMapper> getMapperClass();
	
	
}
