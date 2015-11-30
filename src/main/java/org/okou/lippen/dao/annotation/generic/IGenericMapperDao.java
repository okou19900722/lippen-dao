package org.okou.lippen.dao.annotation.generic;

import org.okou.lippen.dao.IGenericDao;
import org.okou.lippen.dao.annotation.IMapperEntity;

public interface IGenericMapperDao<T extends IMapperEntity> extends IGenericDao<T>
{
}
