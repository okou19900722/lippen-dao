package org.okou.lippen.dao.annotation.generic;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;
import org.okou.lippen.dao.annotation.IGenericMapper;
import org.okou.lippen.dao.annotation.mybatis.LippenParam;
import org.okou.lippen.dao.annotation.mybatis.provider.LippenGenericSqlProvider;

/**
 * 通用mapper接口类，通过{@link LippenGenericSqlProvider} 类动态生成sql语句
 * 使用本通用类有限制，请根据实际情况使用本类
 * @author okou
 *
 * @date 2015年12月3日 下午5:27:41
 * @see LippenGenericSqlProvider
 */
public interface GenericMapper extends IGenericMapper
{
	/**
	 * {@link LippenGenericSqlProvider} 类动态生成的sql语句将model对象中所有非空的属性全部加入查询条件
	 * @see LippenGenericSqlProvider#findAll(LippenParam)
	 * @author okou
	 *
	 * @param query
	 * @return
	 * @date 2015年12月3日
	 */
	@SelectProvider
	(type=LippenGenericSqlProvider.class, method="findAll")
	public <T> 
	List<Map<String, Object>> 
	find(LippenParam<T> query);
	
	/**
	 * {@link LippenGenericSqlProvider} 类动态生成的sql语句将model对象中所有非空的属性全部加入查询条件
	 * @see LippenGenericSqlProvider#get(LippenParam)
	 *
	 * @author okou
	 *
	 * @param query
	 * @return
	 * @date 2015年12月3日
	 */
	@SelectProvider(type=LippenGenericSqlProvider.class, method="get")
	public <T> 
	Map<String, Object> 
	get(LippenParam<T> query);
	
	/**
	 * {@link LippenGenericSqlProvider} 类动态生成的sql语句将model对象中所有非空的属性全部保存，如果数据库字段不为空，但未赋值，则抛出异常
	 * @see LippenGenericSqlProvider#save(LippenParam)
	 *
	 * @author okou
	 *
	 * @param query
	 * @date 2015年12月3日
	 */
	@InsertProvider(type=LippenGenericSqlProvider.class, method="save")
	@Options(useGeneratedKeys=true)
	public <T> 
	void 
	save(LippenParam<T> query);
	
	/**
	 * {@link LippenGenericSqlProvider} 类动态生成的sql语句将model对象中所有非空的属性全部更新，条件为id
	 * @see LippenGenericSqlProvider#findAll(LippenParam)
	 *
	 * @author okou
	 *
	 * @param query
	 * @return
	 * @date 2015年12月3日
	 */
	@UpdateProvider(type=LippenGenericSqlProvider.class, method="update")
	public <T> 
	Integer 
	update(LippenParam<T> query);
	
	/**
	 * {@link LippenGenericSqlProvider} 类动态生成的sql语句根据id为条件删除
	 * @see LippenGenericSqlProvider#findAll(LippenParam)
	 *
	 * @author okou
	 *
	 * @param query
	 * @return
	 * @date 2015年12月3日
	 */
	@UpdateProvider(type=LippenGenericSqlProvider.class, method="delete")
	public <T> 
	Integer 
	delete(LippenParam<T> query);
	
}
