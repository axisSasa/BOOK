## MyBatis-Spring

### 1. 定义及用途

* 作用：将 MyBatis 代码无缝地整合到 Spring 中

### 2. 相对同类产品优势

* 

### 3. 核心知识点

#### 3.1. 使用

* 前提

  * 引入mybatis-spring-x.x.x.jar 
  * 定义一个 SqlSessionFactory 
  * 定义至少一个数据映射器类

* 配置SqlSessionFactory

  * SqlSessionFactory 需要配置一个 DataSource

  * 示例：

    ```
    <bean id="sqlSessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean">
      <property name="dataSource" ref="dataSource" />
    </bean>
    ```

* 配置mapper：

  * 在配置SqlSessionFactory时，增加`<property name="mapperLocations" value="classpath:mapper/*.xml"/>`
  * 作用：能自动扫描锁配置的mapper文件

* 配置配置文件：

  * 在配置SqlSessionFactory时，增加`<property name="configLocation" value="classpath:mybatis-config.xml" />`

* mapper的使用方式：

  - 直接注入映射器到你的 business/service 对象中
  - 一般是：Dao返回的类型就是mapper的resultType，服务层调用dao对象获取该resultType类型的返回值

---

#### 3.2. SqlSessionFactoryBean

* 作用：类似mybatis中的SqlSessionFactoryBuilder，用于创建SqlSessionFactory

* 使用方式：

  * XML

    ```
    <bean id="sqlSessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean">
      <property name="dataSource" ref="dataSource" />
    </bean>
    ```

  * Java

    ```
    SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
    SqlSessionFactory sessionFactory = factoryBean.getObject();
    ```

* 使用：

  * 不需要显示使用SqlSessionFactoryBean 或 SqlSessionFactory
  * SqlSessionFactory自动被注入到MapperFactoryBean 或其它扩 展了 SqlSessionDaoSupport 的 DAO

---

#### 3.3. 事务

* 实质：事务处理期间,一个单独的 SqlSession 对象将会被创建 和使用。当事务完成时,这个 session 会以合适的方式提交或回滚

* 配置

  * 目标：开启spring的事务处理

  * 示例：

    ```
    <bean id="transactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
      <property name="dataSource" ref="dataSource" />
    </bean>
    ```

  * 注意： 这里的DataSource 必须和用来创建 SqlSessionFactoryBean 的 是同一个数据源

* 编程式事务管理

  * 前提
    * 不能在Spring 管 理 的 SqlSession 上 调 用 SqlSession.commit() , SqlSession.rollback() 或 SqlSession.close() 方法
    * 无论 JDBC 连接是否设置为自动提交, SqlSession 数据方法的执行或在 Spring 事务之外 任意调用映射器方法都将会自动被提交
  * 手动编程控制事务
    * 使用PlatformTransactionManager

---

#### 3.4. SqlSession

##### 3.4.1. SqlSessionTemplate

* 作用：管理 MyBatis 的 SqlSession, 调用 MyBatis 的 SQL 方法, 翻译异常

* 特点：是线程安全的, 可以被多个 DAO 所共享使用

* 配置：

  ```
  <bean id="sqlSession" class="org.mybatis.spring.SqlSessionTemplate">
    <constructor-arg index="0" ref="sqlSessionFactory" />
  </bean>
  ```

---

#### 3.5. 注入映射器

作用：代替手工使用 SqlSessionDaoSupport 或 SqlSessionTemplate 编写数据访问对象 (DAO)的代码

##### 3.5.1. MapperFactoryBean

* 作用：直接注入数据映射器接口到你的 service 层 bean 中

* 好处：不需要编写任何 DAO 实现的代码,因为 MyBatis-Spring 将会为你创建代理

* 配置示例：

  ```
  <bean id="userMapper" class="org.mybatis.spring.mapper.MapperFactoryBean">
    <property name="mapperInterface" value="org.mybatis.spring.sample.mapper.UserMapper" />
    <property name="sqlSessionFactory" ref="sqlSessionFactory" />
  </bean>
  等价于：在SqlSessionFactoryBean 的 configLocation 属性该mapper
  ```

---

##### 3.5.2. MapperScannerConfigurer

* 作用：查找类路径下的映射器并自动将它们创建为 MapperFactoryBean

* 配置：

  ```
  <bean class="org.mybatis.spring.mapper.MapperScannerConfigurer">
    <property name="basePackage" value="org.mybatis.spring.sample.mapper" />
  </bean>
  ```

* 配置解析：

  * basePackage 属性：为映射器接口文件设置基本的包路径；可以使用分号或逗号 作为分隔符设置多于一个的包路径

* 映射器命名：被发现的映射器将会使用 Spring 对自动侦测组件默认的命名策略来命名(非大写的非完全限定类名)

### 4. 常见面试题

### 5. 综述

### 6. 参考资料

* [mybatis-spring官方文档](http://www.mybatis.org/spring/zh/index.html)
* 