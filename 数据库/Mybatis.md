## MyBatis

### 1. 定义及用途

* 是一个持久层框架，它支持定制化 SQL、存储过程以及高级映射
* 

### 2. 相对同类产品优势

* 消除了几乎所有的JDBC代码和参数的手工设置以及对结果集的检索封装

### 3. 核心知识点

#### 3.1. Java API

##### 3.1.1. SqlSessionFactoryBuilder

* 作用：其 build() 方法，允许从不同的资源中创建一个 SqlSessionFactory 实例
* 最佳作用域：方法作用域
* 生命周期：创建了SqlSessionFactory就可以死了

---

##### 3.1.2. SqlSessionFactory

* 作用：创建 SqlSession 实例
* 最佳作用域：整个应用【可以使用单例模式实现】
* 生命周期：在应用的运行期间一直存在
* 获取方式：
  * java 配置：`new SqlSessionFactoryBuilder().build(Configuration);`
  * XML配置：`new SqlSessionFactoryBuilder().build(InputStream);`

---

##### 3.1.3. SqlSession

* 作用：执行命令，获取映射器和管理事务
* 线程安全：SqlSession 的实例不是线程安全的，因此是不能被共享
* 最佳作用域：请求或方法作用域
* 生命周期：随请求建立，随响应关闭
* 获取方式：`sqlSessionFactory.openSession();`
  * 无参数时，创建的sqlSession具有如下特性
    * 开启一个事务/关闭自动提交
    * 从由当前环境配置的 DataSource 实例中获取 Connection 对象
    * 事务隔离级别将会使用驱动或数据源的默认设置
    * 预处理语句不会被复用，也不会批量处理更新
* 主要方法介绍：
  * 使用映射器
    * `sqlsession.getMapper(XXXXMapper.class);`
    * 作用：创建来绑定你映射的语句的接口
  * 执行语句方法
    * `<T> T selectOne(String statement, Object parameter)` 等
    * 作用：执行定义在 SQL 映射的 XML 文件中的 SELECT、INSERT、UPDATE 和 DELETE 语句
  * 事务控制方法
    * 默认情况下 MyBatis 不会自动提交事务，除非它侦测到有插入、更新或删除操作改变了数据库
    * `void commit(boolean force)` `void rollback(boolean force)`

---

#### 3.2. XML映射文件

##### 3.2.1. 预处理语句/参数设置

* 普通情况使用：#{id}，功能类似参数标记
* 传入参数时复杂对象：
  * #{id}, #{username}, #{password}，mybatis会进行参数映射，把对象的属性映射到相应的参数标记
  * #{property,javaType=int,jdbcType=NUMERIC} ，给参数指定特殊类型
    * 如果一个列允许 null 值，并且会传递值 null 的参数，就必须要指定 JDBC Type

---

##### 3.2.2. 字符串替换

* 直接在 SQL 语句中插入一个不转义的字符串可以使用
* 示例：ORDER BY ${columnName}

---

##### 3.2.3. resultMap

* 作用：映射查询结果集

* 实现原理：简单的语句不需要明确的结果自动映射，复杂的语句需要描述它们的关系

  * 自动映射：自动映射查询结果时，MyBatis会获取sql返回的列名并在java类中查找相同名字的属性（忽略大小写）

    * 自动映射等级
      * `NONE` - 禁用自动映射。仅设置手动映射属性
      * `PARTIAL` - 默认值，将自动映射结果除了那些有内部定义内嵌结果映射的(joins)
      * `FULL` - 自动映射所有

  * 复杂返回类型示例

    ```
    <resultMap id="userResultMap" type="User">
      <id property="id" column="user_id" />
      <result property="username" column="user_name"/>
      <result property="password" column="hashed_password"/>
    </resultMap>
    使用：<select id="selectUsers" resultMap="userResultMap">
    ```

* 主要子元素：

  * `constructor` - 用于在实例化类时，注入结果到构造方法中
  * `association` – 一个复杂类型的关联;可以指定为一个 `resultMap` 元素，或者引用一个
  * `collection` – 一个复杂类型的集合；可以指定为一个 `resultMap` 元素，或者引用一个
  * `id` – 一个 ID 结果;标记出作为 ID 的结果可以帮助提高整体性能
  * `result` – 注入到字段或 JavaBean 属性的普通结果
  * 鉴别器discriminator：类似switch 语句

* 主要属性：

  * id: 标志一个resultmap
  * type: 类的完全限定名 or 别名
  * autoMapping：开启或者关闭自动映射

---

##### 3.2.4. 缓存

###### 3.2.4.1. 一级缓存/本地缓存

* 定义：
  * 是sqlSession级别的缓存
  * 当在同一个sqlSession中执行两次相同的sql语句时，第一次执行完毕会将数据库中查询的数据写到缓存（内存），第二次查询时会从缓存中获取数据，不再去底层数据库查询
* 实质：创建一个新的SqlSession对象时，SqlSession对象中会有一个新的Executor对象。Executor对象中持有一个新的PerpetualCache对象，该对象保存一级缓存数据
* 生命周期：
  * SqlSession执行了DML操作（增删改），并且提交到数据库，MyBatis则会清空SqlSession中的一级缓存，即PerpetualCache对象，但该对象仍可用，只是数据没了
  * SqlSession显示执行clearCache()方法，效果同上DML操作
  * SqlSession显示执行close()方法，则该SqlSession一级缓存不可用
* 开启方式：默认开启

---

###### 3.2.4.2. 二级缓存/全局缓存

* 定义：
  * 是mapper级别的缓存
  * 不同的sqlSession两次执行相同的namespace下的sql语句，且向sql中传递的参数也相同，即最终执行相同的sql语句，则第一次执行完毕会将数据库中查询的数据写到缓存，第二次查询时会从缓存中获取数据，不再去底层数据库查询
* 开启方式：映射XML文件中配置`<cache/>`
  * 特殊要求：MyBatis要求返回的POJO必须是可序列化的
  * 默认效果：
    * 映射语句文件中的所有select语句将会被缓存。
    * 映射语句文件中的所欲insert、update和delete语句会刷新缓存。
    * 缓存会使用默认的Least Recently Used（LRU，最近最少使用的）算法来收回
      * 其他策略：FIFO；SOFT；WEAK
* 好处：MyBatis通常和Spring进行整合开发。对于每一个service中的sqlsession是不同的，如果没使用事务，此时一级缓存没有意义，二级缓存(是跨SqlSession的)就可以发挥作用啦
* 特点：优先级高于一级缓存
* 参照缓存：
  * 作用：命名空间中共享相同的缓存配置和实例
  * `<cache-ref namespace="mapper全限定名">`

---

###### 3.2.4.3. 自定义缓存/第三方缓存

* 属于二级缓存

* 开启方式：`<cache type="自定义缓存【实现 org.mybatis.cache.Cache 接口】"/>`

---

#### 3.3. 动态SQL

* 作用：避免手动拼接SQL语句

* if

  * 作用：只留下符合添加的语句

  * 适用：根据条件包含 where 子句的一部分

  * 示例：

    ```
    WHERE state = ‘ACTIVE’ 
      <if test="title != null">
        AND title like #{title}
      </if>
    ```

* choose (when, otherwise)

  * 作用：只使用条件语句中的一项，类似于java的switch

  * 示例：

    ```
    <choose>
        <when test="title != null">
          AND title like #{title}
        </when>
        。。。
        <otherwise>
          AND featured = 1
        </otherwise>
      </choose>
    ```

* trim (where, set)

  * 作用：
    * 避免where子句异常(多了AND 或where后无内容等)
    * 避免set子句异常(避免多了set 或 多了逗号)
  * 实质：
    * `<set>` 等价于 `<trim prefix="SET" suffixOverrides=",">`
    * `<where>` 等价于 `<trim prefix="WHERE" prefixOverrides="AND |OR ">`
  * trim: 就是为了过滤，根据prefix， suffixOverrides等属性过滤

* foreach

  * 作用：对一个集合进行遍历，通常结合in使用

  * 示例：

    ```
    WHERE ID in
      <foreach item="item" index="index" collection="list"
          open="(" separator="," close=")">
            #{item}
      </foreach>
    ```

### 4. 常见面试题

### 5. 综述

### 6. 参考资料

* [mybatis官方文档](http://www.mybatis.org/mybatis-3/zh/index.html)

* [【MyBatis源码解析】MyBatis一二级缓存](https://www.cnblogs.com/xrq730/p/6991655.html)

* [Mybatis的一级缓存和二级缓存的理解和区别](https://blog.csdn.net/llziseweiqiu/article/details/79413130)

* [mybatis一级缓存二级缓存](https://www.cnblogs.com/happyflyingpig/p/7739749.html)

