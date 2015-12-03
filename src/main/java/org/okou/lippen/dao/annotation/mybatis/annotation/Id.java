package org.okou.lippen.dao.annotation.mybatis.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 用来设置自增长的主键
 * @author okou
 *
 * @date 2015年12月3日 下午5:25:42
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Id
{
	public String value() default "";
}
