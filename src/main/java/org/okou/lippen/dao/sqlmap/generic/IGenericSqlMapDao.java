package org.okou.lippen.dao.sqlmap.generic;

import org.okou.lippen.dao.IGenericDao;
import org.okou.lippen.dao.sqlmap.ISqlMapEntity;

/**
 * xml 配置的通用dao，
 * 使用本dao需要model对象实现{@link ISqlMapEntity} 接口
 * @author okou
 *
 * @date 2015年12月3日 下午4:34:32
 */
public interface IGenericSqlMapDao<T extends ISqlMapEntity> extends IGenericDao<T>
{

}
