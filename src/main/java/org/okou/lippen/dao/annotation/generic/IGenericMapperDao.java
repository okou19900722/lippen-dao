package org.okou.lippen.dao.annotation.generic;

import org.okou.lippen.dao.IGenericDao;
import org.okou.lippen.dao.annotation.IMapperEntity;

/**
 * annotation 版通用dao实现的接口
 * @author okou
 *
 * @date 2015年12月3日 下午5:26:59
 */
public interface IGenericMapperDao<T extends IMapperEntity> extends IGenericDao<T>
{
}
