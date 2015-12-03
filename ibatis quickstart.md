# 1、基于ibatis的通用dao实现

## 1.1、快速开始

### 1.1.1、建表，配置ibatis sqlmap配置文件
首先，创建一张表user以及其实体类User    
使用通用dao，其model对象`必须实现ISqlMapEntity接口`
```java
package org.okou.ibatis.model;

import org.okou.lippen.dao.sqlmap.ISqlMapEntity;

public class User implements ISqlMapEntity{
    private static final long serialVersionUID = 1L;
	private Integer id;
	private String userName;
	private Integer userAge;
	private String userAddress;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Integer getUserAge() {
		return userAge;
	}
	public void setUserAge(Integer userAge) {
		this.userAge = userAge;
	}
	public String getUserAddress() {
		return userAddress;
	}
	public void setUserAddress(String userAddress) {
		this.userAddress = userAddress;
	}
	
	public String toString()
	{
		return "{id:" + id 
		+ ",userName:" + userName
		+ ",userAge:" + userAge
		+ ",userAddress:" + userAddress
		+ "}";
	}
	
	@Override
	public String selectId()
	{
		return "user.select";//ibatis 的statement id
	}
	@Override
	public String insertId()
	{
		return "user.insert";
	}
	@Override
	public String updateId()
	{
		return "user.update";
	}
	@Override
	public String deleteId()
	{
		return "user.delete";
	}
}

```

然后是ibatis的sqlmap配置文件
```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE sqlMap PUBLIC "-//ibatis.apache.org//DTD SQL Map 2.0//EN" "http://ibatis.apache.org/dtd/sql-map-2.dtd" >
<sqlMap namespace="user">
    <typeAlias alias="User" type="org.okou.ibatis.model.User"/>
	<resultMap class="User" id="User">
	</resultMap>
	
	<select id="select" resultClass="User">
		select * from user 
		<dynamic prepend="where">
			<isNotNull property="id" prepend="and">
				id=#id#
			</isNotNull>
		</dynamic>
	</select>
	<update id="update">
	...update sql
	</update>
	<insert id="insert">
	...insert sql
	</insert>
	<delete id="delete">
	...delete sql
	</delete>
</sqlMap>

```

### 1.1.2、spring配置文件
管理properties配置的properties.xml
```xml
<beans xmlns="http://www.springframework.org/schema/beans"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:aop="http://www.springframework.org/schema/aop"
	xmlns:tx="http://www.springframework.org/schema/tx" xmlns:context="http://www.springframework.org/schema/context"
	xmlns:lang="http://www.springframework.org/schema/lang" xmlns:util="http://www.springframework.org/schema/util"
	xsi:schemaLocation="http://www.springframework.org/schema/beans
		http://www.springframework.org/schema/beans/spring-beans-3.0.xsd
		http://www.springframework.org/schema/aop
		http://www.springframework.org/schema/aop/spring-aop-3.0.xsd
		http://www.springframework.org/schema/tx
		http://www.springframework.org/schema/tx/spring-tx-3.0.xsd
		http://www.springframework.org/schema/context
		http://www.springframework.org/schema/context/spring-context-3.0.xsd
		http://www.springframework.org/schema/lang
		http://www.springframework.org/schema/lang/spring-lang-3.0.xsd
		http://www.springframework.org/schema/util
		http://www.springframework.org/schema/util/spring-util-3.0.xsd">

	<!-- 支持多properties文件 -->
	<bean id="propertyPlaceholderConfigurer"
		class="org.springframework.beans.factory.config.PropertyPlaceholderConfigurer">
		<property name="locations">
			<list>
				<value>classpath:config/dataSource.properties</value>
			</list>
		</property>
		<property name="ignoreResourceNotFound" value="true" />
		<property name="fileEncoding" value="utf-8" />
	</bean>
</beans>
```
与之对应的就是dataSource.properties配置文件  
data.source开头的配置为dataSource配置，而最后两个ibatis开头的配置是ibatis的主配置文件和model类的sqlmap配置文件
```java
data.source.driver=com.mysql.jdbc.Driver
data.source.driverUrl=jdbc:mysql://localhost:3306/t
data.source.user=root
data.source.password=

data.source.alias=spring
data.source.houseKeepingSleepTime=1800000
data.source.simultaneousBuildThrottle=10
data.source.prototypeCount=10
data.source.maximumConnectionCount=100
data.source.minimumConnectionCount=10
data.source.maximumActiveTime=9223372036854775807
data.source.trace=false
data.source.verbose=false


#ibatis config , path with classpath and '/'
ibatis.config.location=classpath:ibatis-config.xml
#ibatis mappers, path with classpath and '/'
ibatis.mapping.locations=classpath\:org/okou/ibatis/sqlmap/*_SqlMap.xml
```

然后是通用dao的bean配置
```xml
<bean id="ibatisGenericSqlMapDao" class="org.okou.lippen.dao.sqlmap.generic.impl.IBatisGenericSqlMapDaoImpl" >
	<property name="sqlMapClient" ref="sqlMapClient"/>
	<property name="sqlMapClientTemplate" ref="sqlMapClientTemplate"></property>
</bean>
```

### 1.1.3、单元测试
配置好了之后，通过单元测试看是否配置成功
```java
import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.okou.ibatis.model.User;
import org.okou.lippen.dao.sqlmap.generic.IGenericSqlMapDao;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:ibatis-sqlmap-property.xml", "classpath:lippen-ibatis.xml", "classpath:ibatis-test.xml"})
public class TestIbatisGenericDao
{
    @Resource
	IGenericSqlMapDao<User> ibatisGenericSqlMapDao;
	
	@Test
	public void test()
	{
		User u = new User();
		u.setId(1);
		System.out.println(ibatisGenericSqlMapDao.get(u));
	}
}
```
运行结果如图:
![](https://github.com/okou19900722/lippen-dao/raw/master/image/运行结果.png)
