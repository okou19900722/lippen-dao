# 1、基于mybatis注解实现的通用dao实现

## 1.1、快速开始

### 1.1.1、建表，建model对象
首先，创建一张表user以及其实体类User    
使用通用dao，其model对象`必须实现IMapperEntity接口`，建议`继承自AbstractMapperEntity类`
```java
package org.okou.mybatis.annotation.model;

import org.okou.lippen.dao.annotation.IMapperEntity;
import org.okou.lippen.dao.annotation.generic.impl.AbstractMapperEntity;
import org.okou.lippen.dao.annotation.mybatis.annotation.Id;
import org.okou.lippen.dao.annotation.mybatis.annotation.Table;
@Table("user")
public class User extends AbstractMapperEntity implements IMapperEntity{
    @Id
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
}
```

model对象类可以添加`@Table`注解也可不添加，如果表名和model类名不一致，则必须通过`@Table`注解设置value值，否则默认使用首字母小写的类名为表名。  
字段名不一致可使用`@Column`注解配置value值，不设置默认使用属性名为字段名。


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

然后是通用dao的bean配置
```xml
<bean
	class="org.okou.lippen.dao.annotation.generic.impl.GenericMapperDaoImpl">
</bean>
```

### 1.1.3、单元测试
配置好了之后，通过单元测试看是否配置成功
```java
import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.okou.lippen.dao.IGenericDao;
import org.okou.mybatis.annotation.model.User;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:mybatis-annotation-property.xml", "classpath:lippen-mybatis-annotation.xml", "classpath:mybatis-annotation-test.xml"})
public class TestMyBatisAnnotationGenericDao
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
![](https://github.com/okou19900722/lippen-dao/raw/master/image/mybatis-annotation-result.png)
