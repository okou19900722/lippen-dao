package org.okou.lippen.dao.annotation.mybatis.annotation.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.okou.lippen.commons.annotation.util.AnnotationUtils;
import org.okou.lippen.commons.util.string.StringUtils;

public class AnnotationValueUtil
{
	public static <T extends Annotation> String getValue(AnnotatedElement element, Class<T> annotation, String defaultName) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException
	{
		return getValue(element, annotation, defaultName, true);
	}
	/**
	 * @param <T>
	 * @param element 注解的对象
	 * @param annotation 注解的类
	 * @param defaultName 默认值，如果没有注解或者注解没有定义值，返回默认值
	 * @param require 检测是否必须包含注解，当该值为true时，未检测到注解未抛出异常
	 * @return
	 * @throws SecurityException 
	 * @throws NoSuchMethodException 如果注解没有value方法会抛出该异常
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 *
	 *@author 严尚君
	 *
	 *@date 2015-11-30
	 */
	public static <T extends Annotation> String getValue(AnnotatedElement element, Class<T> annotation, String defaultName, boolean require) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException
	{
		if(require && !AnnotationUtils.hasAnnotation(element, annotation))
		{
			throw new RuntimeException("type " + element + " need a annotation with org.okou.lippen.dao.annotation.mybatis.annotation.Table");
		}
		T t = element.getAnnotation(annotation);
		String value = t == null ? StringUtils.lowerCaseFirstWord(defaultName) : null;
		if(t != null)
		{
			Method m = t.getClass().getMethod("value");
			String annotationValue = (String) m.invoke(t);
			value = "".equals(annotationValue) ? StringUtils.lowerCaseFirstWord(defaultName) : annotationValue;
		}
		return value;
	}
}
