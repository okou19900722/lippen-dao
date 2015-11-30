package org.okou.lippen.dao.annotation;

import java.util.List;
import java.util.Map;

import org.okou.lippen.dao.annotation.mybatis.LippenParam;

public interface IGenericMapper
{
	public <T> List<T> find(T query);
	
	public <T> List<Map<String, Object>> find(LippenParam<T> query);
	public <T> Map<String, Object> get(T query);

	public <T> void save(T po);

	public <T> int update(T po);

	public <T> int delete(T po);
	
}
