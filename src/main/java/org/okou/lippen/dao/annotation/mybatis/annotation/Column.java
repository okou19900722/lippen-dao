package org.okou.lippen.dao.annotation.mybatis.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 当model类中属性名与字段名不一致时，可以添加本注解，并通过设置value值来设置对应的字段名
 * @author okou
 *
 * @date 2015年12月3日 下午5:26:09
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Column
{
	public String value() default "";
}
