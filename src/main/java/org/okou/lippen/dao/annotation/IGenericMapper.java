package org.okou.lippen.dao.annotation;

import java.util.List;
import java.util.Map;

import org.okou.lippen.dao.annotation.mybatis.LippenParam;

/**
 * mybatis annotation 版model对象返回的mapper接口必须继承自本接口
 * @author okou
 *
 * @date 2015年12月3日 下午4:51:34
 */
public interface IGenericMapper
{
	public <T> List<T> find(T query);
	
	public <T> List<Map<String, Object>> find(LippenParam<T> query);
	
	public <T> Map<String, Object> get(LippenParam<T> query);

	public <T> void save(LippenParam<T> param);

	public <T> Integer update(LippenParam<T> param);

	public <T> Integer delete(LippenParam<T> param);
	
}
