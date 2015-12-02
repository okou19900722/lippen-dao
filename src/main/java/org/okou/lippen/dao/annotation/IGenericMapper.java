package org.okou.lippen.dao.annotation;

import java.util.List;
import java.util.Map;

import org.okou.lippen.dao.annotation.mybatis.LippenParam;

public interface IGenericMapper
{
	public <T> List<T> find(T query);
	
	public <T> List<Map<String, Object>> find(LippenParam<T> query);
	public <T> Map<String, Object> get(LippenParam<T> query);

	public <T> void save(LippenParam<T> query);

	public <T> Integer update(LippenParam<T> query);

	public <T> Integer delete(LippenParam<T> query);
	
}
