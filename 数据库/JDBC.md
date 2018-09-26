## JDBC

### 1. 定义及用途

* 英文：Java data base connectivity - java 数据库连接

* 是一个Java API ，是用于在Java语言编程中与数据库连接的API，可用于访问任何类型的数据库
* 主要功能：
  * 连接到数据库
  * 创建SQL语句
  * 执行SQL数据库查询
  * 查看和修改查询返回结果记录

### 2. 相对同类产品优势

* 

### 3. 核心知识点

#### 3.1. 常见组件

##### 3.1.1. 

---

#### 3.2. 构造一个使用JDBC的程序

* 导入包
  * `java.sql.*`
* 注册JDBC驱动程序
  * `Class.forName("com.mysql.jdbc.Driver");`
* 打开连接
  * `DriverManager.getConnection(数据库服务器地址,用户名,密码);`
* 执行查询
  * 获取Statement：`连接.createStatement()`
  * 构造sql语句：`sql = sql语句`
  * 执行查询：`statement.executeQuery(sql)` ;返回结果集ResultSet
* 从结果集中提取数据
  * `类型 变量名 = resultSet.get类型(列名);`
  * 举例：`int id  = resultSet.getInt("id");`
* 清理环境资源
  * 关闭结果集：`resultSet.close();`
  * 关闭Statement：`statement.close();`
  * 关闭连接：`连接.close();`

---

#### 3.3. JDBC驱动程序

* 作用：用于与数据库服务器进行交互
* 分类：
  * 类型1：JDBC-ODBC桥接驱动程序
    * 出现是因为早期大多数数据库仅支持ODBC访问，实质是用的ODBC驱动
  * 类型2：JDBC本地API
    * JDBC API调用将转换为本地C/C++ API调用，驱动程序通常由数据库供应商提供
  * 类型3：JDBC-Net纯Java
    * JDBC客户端使用标准网络套接字与中间件应用程序服务器进行通信。 套接字信息随后由中间件应用服务器转换成DBMS所需的调用格式，并转发到数据库服务器
    * 优势：一个驱动程序实际上可以提供多个数据库的访问
    * 适用：同时访问多种类型的数据库
  * 类型4：100%纯Java
    * 基于纯Java的驱动程序通过套接字连接与供应商的数据库直接通信。 这是数据库可用的最高性能驱动程序，通常由供应商自己提供
    * 适用：只访问一种类型的数据库

---

#### 3.4. JDBC数据库连接

* 创建步骤：
  * 注册JDBC驱动程序
    * 只需在程序中一次注册
    * 注册方法：
      * `Class.forName("com.mysql.jdbc.Driver")`方法
      * `DriverManager.registerDriver(Driver d)`方法
  * 数据库URL等配置
    * 各个RDBMS的url配置略有差异
    * MySQL：`jdbc:mysql://127.0.0.1:3307/数据库名?其他参数`
  * 创建连接对象
    * `DriverManager.getConnection(URL, Properties)`
    * `DriverManager.getConnection(URL, user, password)`

---

#### 3.5. XXXStatement

* 作用：
  * 用于发送SQL命令，并从数据库接收数据的方法和属性
  * 协助Java和SQL数据类型的数据类型差异转换

##### 3.5.1. Statement

* 特点：不能接受参数
* 适用：使用静态SQL语句时
* 创建：`connection.createStatement()`
* 执行：
  * `boolean execute (String SQL)`
    * 适用于DDL语句
    * 检测到结果集返回true
  * `int executeUpdate (String SQL)`
    * 适用于 增 删 改
  * `ResultSet executeQuery(String SQL)`
    * 适用于 查
* 清理：`statement.close()`

---

##### 3.5.2. PreparedStatement

* 特点：运行时接受输入参数
* 适用：计划要多次使用SQL语句时
* 参数标记:
  * 定义：JDBC中的所有参数都由 `?` 符号作为占位符,这称为参数标记
  * 作用：占位；
  * 参数标记索引：每个参数标记的唯一性由其定义顺序决定，可理解为从1开始的索引
* 创建：`cennection.prepareStatement(SQL)`
* 替换参数标记
  * 使用`setXXX(参数标记索引, 实参)`方法将值绑定到参数 替换参数标记

---

##### 3.5.3. CallableStatement

* 特点：运行时接受输入参数
* 适用：要访问数据库存储过程时
* 创建：`connection.prepareCall(调用存储过程的SQL)`
* 替换参数标记
* 处理存储过程中的返回值：
  * `registerOutParameter(参数标记索引，参数数据库中类型)`方法将JDBC数据类型绑定到存储过程并返回预期数据类型
* 获取存储过程中的返回值
  * `getJava类型(参数标记索引)`

---

#### 3.6. 处理结果集

* 结果集: 指包含在`ResultSet`对象中的行和列数据

  * `ResultSet`对象: 维护指向结果集中当前行的游标

##### 3.6.1. 结果集的设置

  * 设置时机：在创建statement时设置
  * 使用示例：`createStatement(int RSType, int RSConcurrency);`
  * 示例解析：
    * RSType: `ResultSet`的类型 
      * 作用：设置限制光标移动，设置同步数据库更新
      * 光标只能在结果集中向前移动：ResultSet.TYPE_FORWARD_ONLY
      * 光标可以在结果集中向前、向后移动
        * 数据库更新，结果集跟着同步：ResultSet.TYPE_SCROLL_SENSITIVE
        * 数据库更新，结果集不同步：ResultSet.TYPE_SCROLL_INSENSITIVE
    * RSConcurrency：一个常量
      * 作用：指定结果集是只读还是可更新
      * ResultSet.CONCUR_READ_ONLY,默认值，设置结果集只读
      * ResultSet.CONCUR_UPDATABLE，设置结果集可更新


---

##### 3.6.2. 浏览结果集

* 作用：移动光标，定位到结果集中特定行
* 常用方法：
  * beforeFirst(): 将光标移动到第一行之前
  * afterLast(): 将光标移动到最后一行之后
  * first(): 光标移动到第一行
  * last(): 光标移动到最后一行
  * absolute(int row) : 光标移动到指定行
  * relative(int row) : 光标从当前位置向前或向后移动指定行
  * previous(): 上一行
  * next(): 下一行
  * moveToInsertRow():插入行

---

##### 3.6.3. 查看结果集数据

* 作用：查看结果集当前行的数据

* 方式：
  * 通过列名称的方法获取
    * get类型(列名)
  * 通过列索引的方法获取
    * get类型(列索引) - 列索引从1开始计数

---

##### 3.6.4. 更新结果集数据

* 作用：更新结果集当前行的数据;不会更改底层数据库中的值

* 方式：
  - 通过列名称的方法更新
    - update类型(列名)
  - 通过列索引的方法更新
    - update类型(列索引) - 列索引从1开始计数

* 更新结果集的同时更改底层数据库中的值 的方法：
  * updateRow() 更新数据库当前行
  * deleteRow() 删除数据库当前行
  * refreshRow() 结果集同步数据库的更改
  * cancelRowUpdates()取消对当前行做的更改
  * insertRow() 在数据库插入一行

---

#### 3.7. 事务

* 作用：事务能够控制何时更改提交并应用于数据库，可将一组SQL操作视为一个逻辑单元
* 开启事务：
  * 方法：关闭自动提交，改用手动提交
  * 代码：
    * 关闭自动提交：`连接.setAutoCommit(false);`
    * 设置手动提交：`连接.commit();`
* 处理事务执行异常：
  * 方法：失败回滚
  * 代码：`连接.rollback();`
* 设置保存点
  * 作用：如果通过保存点(`Savepoint`)后发生错误时，不必回滚整个事务，可回滚到保存点
  * 管理保存点
    * 设置保存点：setSavepoint(String 保存点名),返回一个Savepoint对象
    * 删除保存点：releaseSavepoint(Savepoint)
  * 使用保存点回滚：
    * `连接.rollback(Savepoint);`

---

#### 3.8. 批处理

* 作用：一次向数据库发送多个SQL语句，可以减少通信开销，从而提高性能
* 检测是否支持批处理：`DatabaseMetaData.supportsBatchUpdates()`
* 管理 批处理语句：
  * 添加：XXXStatement.addBatch()
  * 删除：XXXStatement.clearBatch()
* 执行批处理
  * XXXStatement.executeBatch()
  * 返回一个整数数组，数组的每个元素表示相应更新语句的更新计数

---

### 4. 常见面试题

### 5. 综述

### 6. 参考资料

* [JDBC教程](https://www.yiibai.com/jdbc/)
* 