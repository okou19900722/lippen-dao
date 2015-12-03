package org.okou.lippen.common.utils;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.DynaProperty;
import org.apache.commons.beanutils.PropertyUtilsBean;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.springframework.util.Assert;
import org.springframework.util.ReflectionUtils;

/**
 * 基于jakarta commons BeanUtils
 * 
 * @author EXvision
 */
public class BeanUtils extends org.apache.commons.beanutils.BeanUtils
{
	private static Logger logger = Logger.getLogger(BeanUtils.class);

	private static PropertyUtilsBean propertyUtilsBean;

	/**
	 * 复制实例的字段 忽略部分设定的字段
	 * 
	 * @param dest
	 *            目标实例
	 * @param orig
	 *            来源实例
	 * @param ignoreProperties
	 *            所需要忽略的字段
	 */
	public static void copyProperties(Object dest, Object orig,
			String... ignoreProperties)
	{
		org.springframework.beans.BeanUtils.copyProperties(orig, dest,
				ignoreProperties);
	}

	/**
	 * 复制实例的字段 <b>忽略null字段</b>
	 * 
	 * @param dest
	 *            目标实例
	 * @param orig
	 *            来源实例
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	@SuppressWarnings("rawtypes")
	public static void copyPropertiesIgnoreNull(Object dest, Object orig)
			throws IllegalAccessException, InvocationTargetException
	{
		// Validate existence of the specified beans
		if (dest == null)
		{
			throw new IllegalArgumentException("No destination bean specified");
		}
		if (orig == null)
		{
			throw new IllegalArgumentException("No origin bean specified");
		}
		debug("BeanUtils.copyProperties(" + dest + ", " + orig + ")");

		// Copy the properties, converting as necessary
		if (orig instanceof DynaBean)
		{
			DynaProperty[] origDescriptors = ((DynaBean) orig).getDynaClass()
					.getDynaProperties();
			for (int i = 0; i < origDescriptors.length; i++)
			{
				String name = origDescriptors[i].getName();
				// Need to check isReadable() for WrapDynaBean
				// (see Jira issue# BEANUTILS-61)
				if (getPropertyUtils().isReadable(orig, name)
						&& getPropertyUtils().isWriteable(dest, name))
				{
					Object value = ((DynaBean) orig).get(name);
					if (value != null)
					{
						copyProperty(dest, name, value);
					}
				}
			}
		}
		else
			if (orig instanceof Map)
			{
				Iterator names = ((Map) orig).keySet().iterator();
				while (names.hasNext())
				{
					String name = (String) names.next();
					if (getPropertyUtils().isWriteable(dest, name))
					{
						Object value = ((Map) orig).get(name);
						if (value != null)
						{
							copyProperty(dest, name, value);
						}
					}
				}
			}
			else
			/* if (orig is a standard JavaBean) */{
				PropertyDescriptor[] origDescriptors = getPropertyUtils()
						.getPropertyDescriptors(orig);
				for (int i = 0; i < origDescriptors.length; i++)
				{
					String name = origDescriptors[i].getName();
					if ("class".equals(name))
					{
						continue; // No point in trying to set an object's
						// class
					}
					if (getPropertyUtils().isReadable(orig, name)
							&& getPropertyUtils().isWriteable(dest, name))
					{
						try
						{
							Object value = getPropertyUtils()
									.getSimpleProperty(orig, name);
							if (value != null)
							{
								copyProperty(dest, name, value);
							}
						}
						catch (NoSuchMethodException e)
						{
							// Should not happen
						}
					}
				}
			}

	}

	/**
	 * Bean转换到Map的方法<br>
	 * <ul>
	 * <li><b>value空值</b>将会被忽略</li>
	 * <li><b>key=class</b>的项，将会被忽略</li>
	 * </ul>
	 * 具体请看源代码
	 * 
	 * @param bean
	 *            待转换的pojo
	 * @return 转换后的Map
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Map describe(Object bean)
	{
		try
		{
			Map<?, ?> map = org.apache.commons.beanutils.BeanUtils
					.describe(bean);
			Map<Object, Object> reMap = new HashMap<Object, Object>();
			map.remove("class");
			Iterator<?> it = map.entrySet().iterator();
			while (it.hasNext())
			{
				Map.Entry<?, ?> entry = (Entry<?, ?>) it.next();
				if (entry.getValue() != null
						&& StringUtils.isNotBlank(String.valueOf(entry
								.getValue())))
				{
					reMap.put(entry.getKey(), entry.getValue());
				}
			}
			return reMap;
		}
		catch (IllegalAccessException e)
		{
			throw new RuntimeException(e);
		}
		catch (InvocationTargetException e)
		{
			throw new RuntimeException(e);
		}
		catch (NoSuchMethodException e)
		{
			throw new RuntimeException(e);
		}
	}

	/**
	 * 通过反射获取变量 异常不抛出
	 * 
	 * @param bean
	 * @param name
	 * @return 值或Null
	 */
	public static String getPropertyQuietly(Object bean, String name)
	{
		try
		{
			return BeanUtils.getProperty(bean, name);
		}
		catch (IllegalAccessException e)
		{
			debug(e);
		}
		catch (InvocationTargetException e)
		{
			debug(e);
		}
		catch (NoSuchMethodException e)
		{
			debug(e);
		}
		return null;
	}

	/**
	 * 循环向上转型,获取对象的DeclaredField.
	 * 
	 * @throws NoSuchFieldException
	 *             如果没有该Field时抛出.
	 */
	public static Field getDeclaredField(Object object, String propertyName)
			throws NoSuchFieldException
	{
		Assert.notNull(object);
		Assert.hasText(propertyName);
		return getDeclaredField(object.getClass(), propertyName);
	}

	/**
	 * 循环向上转型,获取对象的DeclaredField.
	 * 
	 * @throws NoSuchFieldException
	 *             如果没有该Field时抛出.
	 */
	public static Field getDeclaredField(Class<?> clazz, String propertyName)
			throws NoSuchFieldException
	{
		Assert.notNull(clazz);
		Assert.hasText(propertyName);
		for (Class<?> superClass = clazz; superClass != Object.class; superClass = superClass
				.getSuperclass())
		{
			try
			{
				return superClass.getDeclaredField(propertyName);
			}
			catch (NoSuchFieldException e)
			{
				// Field不在当前类定义,继续向上转型
			}
		}
		throw new NoSuchFieldException("No such field: " + clazz.getName()
				+ '.' + propertyName);
	}

	/**
	 * 暴力获取对象变量值,忽略private,protected修饰符的限制.
	 * 
	 * @throws NoSuchFieldException
	 *             如果没有该Field时抛出.
	 */
	public static Object forceGetProperty(Object object, String propertyName)
			throws NoSuchFieldException
	{
		Assert.notNull(object);
		Assert.hasText(propertyName);

		Field field = getDeclaredField(object, propertyName);

		boolean accessible = field.isAccessible();
		field.setAccessible(true);

		Object result = null;
		try
		{
			result = field.get(object);
		}
		catch (IllegalAccessException e)
		{
			logger.info("error wont' happen");
		}
		field.setAccessible(accessible);
		return result;
	}

	/**
	 * 暴力设置对象变量值,忽略private,protected修饰符的限制.
	 * 
	 * @throws NoSuchFieldException
	 *             如果没有该Field时抛出.
	 */
	public static void forceSetProperty(Object object, String propertyName,
			Object newValue) throws NoSuchFieldException
	{
		Assert.notNull(object);
		Assert.hasText(propertyName);

		Field field = getDeclaredField(object, propertyName);
		boolean accessible = field.isAccessible();
		field.setAccessible(true);
		try
		{
			field.set(object, newValue);
		}
		catch (IllegalAccessException e)
		{
			logger.info("Error won't happen");
		}
		field.setAccessible(accessible);
	}

	/**
	 * 暴力调用对象函数,忽略private,protected修饰符的限制.
	 * 
	 * @throws NoSuchMethodException
	 *             如果没有该Method时抛出.
	 */
	public static Object invokePrivateMethod(Object object, String methodName,
			Object... params) throws NoSuchMethodException
	{
		Assert.notNull(object);
		Assert.hasText(methodName);
		Class<?>[] types = new Class[params.length];
		for (int i = 0; i < params.length; i++)
		{
			types[i] = params[i].getClass();
		}

		Class<?> clazz = object.getClass();
		Method method = null;
		for (Class<?> superClass = clazz; superClass != Object.class; superClass = superClass
				.getSuperclass())
		{
			try
			{
				method = superClass.getDeclaredMethod(methodName, types);
				break;
			}
			catch (NoSuchMethodException e)
			{
				// 方法不在当前类定义,继续向上转型
			}
		}

		if (method == null)
			throw new NoSuchMethodException("No Such Method:"
					+ clazz.getSimpleName() + methodName);

		boolean accessible = method.isAccessible();
		method.setAccessible(true);
		Object result = null;
		try
		{
			result = method.invoke(object, params);
		}
		catch (Exception e)
		{
			ReflectionUtils.handleReflectionException(e);
		}
		method.setAccessible(accessible);
		return result;
	}

	/**
	 * 按Filed的类型取得Field列表.
	 */
	public static List<Field> getFieldsByType(Object object, Class<?> type)
	{
		List<Field> list = new ArrayList<Field>();
		Field[] fields = object.getClass().getDeclaredFields();
		for (Field field : fields)
		{
			if (field.getType().isAssignableFrom(type))
			{
				list.add(field);
			}
		}
		return list;
	}

	/**
	 * 按FiledName获得Field的类型.
	 */
	public static Class<?> getPropertyType(Class<?> type, String name)
			throws NoSuchFieldException
	{
		return getDeclaredField(type, name).getType();
	}

	/**
	 * 获得field的getter函数名称.
	 */
	public static String getGetterName(Class<?> type, String fieldName)
	{
		Assert.notNull(type, "Type required");
		Assert.hasText(fieldName, "FieldName required");

		if (type.getName().equals("boolean"))
		{
			return "is" + StringUtils.capitalize(fieldName);
		}
		else
		{
			return "get" + StringUtils.capitalize(fieldName);
		}
	}

	/**
	 * 获得field的getter函数,如果找不到该方法,返回null.
	 */
	public static Method getGetterMethod(Class<?> type, String fieldName)
	{
		try
		{
			return type.getMethod(getGetterName(type, fieldName));
		}
		catch (NoSuchMethodException e)
		{
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	/**
	 * Gets the <code>PropertyUtilsBean</code> instance used to access
	 * properties.
	 * 
	 * @return The ConvertUtils bean instance
	 */
	public static PropertyUtilsBean getPropertyUtils()
	{
		if (propertyUtilsBean == null)
		{
			propertyUtilsBean = new PropertyUtilsBean();
		}
		return propertyUtilsBean;
	}

	private static void debug(Object message)
	{
		if (logger.isDebugEnabled())
		{
			logger.debug(message);
		}
	}

}
