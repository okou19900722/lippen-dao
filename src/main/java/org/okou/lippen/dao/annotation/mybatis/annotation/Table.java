package org.okou.lippen.dao.annotation.mybatis.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 当model类名与表名不一致时，可以添加本注解，并通过设置value值来设置对应的表名
 * @author okou
 *
 * @date 2015年12月3日 下午5:23:36
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Table
{
	public String value() default "";
}
