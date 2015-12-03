package org.okou.lippen.dao.annotation;

import java.io.Serializable;

import org.okou.lippen.dao.annotation.generic.impl.AbstractMapperEntity;

/**
 * mybatis annotation版通用dao的model类必须实现接口
 * 如果希望使用通用处理类，建议model类继承自{@link AbstractMapperEntity} 
 * @author okou
 *
 * @date 2015年12月3日 下午4:47:46
 * @see AbstractMapperEntity
 */
public interface IMapperEntity extends Serializable
{
	/**
	 * 获取mapper接口，自定义的mapper接口必须继承自{@link IGenericMapper}接口 
	 * @return
	 *
	 *@author okou
	 *
	 *@date 2015年12月3日
	 */
	public Class<? extends IGenericMapper> getMapperClass();
	
	
}
