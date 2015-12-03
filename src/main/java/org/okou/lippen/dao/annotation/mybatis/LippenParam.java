package org.okou.lippen.dao.annotation.mybatis;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import org.okou.lippen.dao.annotation.mybatis.annotation.Id;
import org.springframework.core.annotation.AnnotationUtils;

/**
 * 通用annotation 版查询参数
 * @author okou
 *
 * @date 2015年12月3日 下午4:52:25
 */
public class LippenParam<T>
{
	/**
	 * model对象
	 * @author okou
	 *
	 * @date 2015年12月3日
	 */
	private T po;
	
	/**
	 * 分页查询开始记录
	 * @author okou
	 *
	 * @date 2015年12月3日
	 */
	private Integer firstResult;
	/**
	 * 分页查询的记录数
	 * @author okou
	 *
	 * @date 2015年12月3日
	 */
	private Integer maxResults;
	
	public LippenParam(T po)
	{
		this(po, null, null);
	}
	public LippenParam(T po, Integer maxResults)
	{
		//limit num 表示从0开始取num个，所以此处firstResult为0
		this(po, 0, maxResults);
	}
	public LippenParam(T po, Integer firstResult, Integer maxResults)
	{
		super();
		this.po = po;
		this.firstResult = firstResult;
		this.maxResults = maxResults;
	}
	public T getPo()
	{
		return po;
	}
	public Integer getFirstResult()
	{
		return firstResult;
	}
	public Integer getMaxResults()
	{
		return maxResults;
	}
	
	
	/**
	 * 设置主键(只设置以{@link Id} 注解的第一个变量)，
	 * @param id
	 *
	 *@author okou
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 *
	 *@date 2015年12月3日
	 */
	public void setId(Object id) throws IllegalArgumentException, IllegalAccessException
	{
		if(id == null)return;
		Field[] fields = po.getClass().getDeclaredFields();
		for (Field field : fields)
		{
			Annotation annotation = AnnotationUtils.getAnnotation(field, Id.class);
			if(annotation != null)
			{
				field.setAccessible(true);
				Object ID = id;
				if(!field.getType().isInstance(po))
				{
					long l = (Long) id;
					ID = (int)l;
				}
				field.set(po, ID);
				return;
			}
		}
	}
}
