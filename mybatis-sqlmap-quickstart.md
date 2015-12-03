# 0、本文档与ibatis基本一致
# 1、基于mybatis的通用dao实现

## 1.1、快速开始

### 1.1.1、建表，配置mybatis sqlmap配置文件
首先，创建一张表user以及其实体类User    
使用通用dao，其model对象`必须实现ISqlMapEntity接口`
```java
package org.okou.mybatis.sqlmap.model;

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
		return "user.select";//mybatis 的statement id
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

然后是mybatis的sqlmap配置文件
```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" 
"http://mybatis.org/dtd/mybatis-3-mapper.dtd">

<mapper namespace="user">
    <resultMap type="org.okou.mybatis.sqlmap.model.User" id="User" />
    <select id="select" parameterType="int" resultMap="User">
        select * from `user` where id = #{id}
    </select>
    <update id="update">
		<!-- update sql -->
	</update>
	<insert id="insert">
		<!-- insert sql -->
	</insert>
	<delete id="delete">
		<!-- delete sql -->
	</delete>
</mapper>
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
				<value>classpath:config/mybatis.properties</value>
			</list>
		</property>
		<property name="ignoreResourceNotFound" value="true" />
		<property name="fileEncoding" value="utf-8" />
	</bean>
</beans>
```
与之对应的就是dataSource.properties配置文件  
data.source为dataSource配置，按自己的项目配置相应参数
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
```

mybatis.properties配置文件是mybatis的主配置文件和model类的sqlmap配置文件
```java
#mybatis mappers, path with classpath and '/'
mybatis.mapper.locations=classpath\:org/okou/mybatis/sqlmap/*_mapper.xml
#mybatis config , path with classpath and '/'
mybatis.config.locations=classpath\:mybatis-config.xml
```

然后是通用dao的bean配置
```xml
<bean id="mybatisGenericSqlMapDao"
	class="org.okou.lippen.dao.sqlmap.generic.impl.MybatisGenericSqlMapDaoImpl">
	<property name="sqlSessionTemplate" ref="sqlSessionTemplate" />
</bean>
```

### 1.1.3、单元测试
配置好了之后，通过单元测试看是否配置成功
```java
import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.okou.lippen.dao.IGenericDao;
import org.okou.mybatis.sqlmap.model.User;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:mybatis-sqlmap-property.xml", "classpath:lippen-mybatis.xml", "classpath:mybatis-test.xml"})
public class TestMyBatisSqlMapGenericDao
{
    @Resource
	IGenericDao<User> mybatisGenericSqlMapDao;
	
	@Test
	public void test()
	{
		User u = new User();
		u.setId(12);
		System.out.println(mybatisGenericSqlMapDao.get(u));
	}
}
```
运行结果如图:
![](https://github.com/okou19900722/lippen-dao/raw/master/image/mybatis-result.png)
