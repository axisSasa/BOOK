## MySQL

### 1. 定义及用途

#### 1.1. 关系型数据库：

* 是建立在关系模型基础上的数据库；关系数据库将数据保存在不同的表中

##### 1.1.1. 关系模型：

* 定义：指二维表格模型，因此一个关系型数据库就是由二维表及其之间的联系组成的一个数据组织

##### 1.1.2. 数据库：

* 定义：长期储存在计算机内、有组织的、可共享的数据集合；
* 效果：该数据集合尽量不重复，且对数据的增、删、改、查能由统一软件进行管理和控制
* 基本结构：物理数据层、概念数据层、用户数据层

##### 1.1.3. MySQL

* 简介：
  * 是关系型数据库；
  * 其关系模型是二维表格模型
  * 其二位表格模型包括：表头、行、列、键、值
* 特性：
  * 支持多线程
  * 提供多语言支持
  * 提供 [TCP/IP](https://baike.baidu.com/item/TCP%2FIP)、ODBC 和 [JDBC](https://baike.baidu.com/item/JDBC)等多种[数据库](https://baike.baidu.com/item/%E6%95%B0%E6%8D%AE%E5%BA%93)连接途径
  * 提供用于管理、检查、优化数据库操作的管理工具
  * 支持多种存储引擎

### 2. 相对同类产品优势

* 与Oracle数据库做对比

### 3. 核心知识点

#### 3.1. 存储引擎

##### 3.1.1. 常见的存储引擎

- MyISAM
  - MySQL 5.0 之前的默认数据库引擎，高的插入，查询速度，不支持事务
  - 支持 B-tree、Full-text 等索引，不支持 Hash 索引；
- BDB, 支持Commit 和Rollback 等事务特性
- CSV, 逻辑上由逗号分割数据的存储引擎,不支持索引
- InnoDB
  - 支持 B-tree、Full-text 等索引，不支持 Hash 索引；

---

##### 3.1.2. InnoDB

* MySQL 5.5 起成为默认数据库引擎, 支持ACID事务，支持行级锁定

* ACID事务
  * ACID: 指数据库事务正确执行的四个基本要素的缩写
  * Atomicity-原子性：整个事务中的所有操作，要么全部完成，要么全部不完成
  * Consistency-一致性: 事务开始以前，被操作的数据的完整性处于一致性的状态，事务结束后，被操作的数据的完整性也必须处于一致性状态
  * Isolation-隔离性：串行化或序列化请求，使得在同一时间仅有一个请求用于同一数据
  * Durability-持久性：事务一旦完成，事务对数据库的更改不会再回滚
* 行级锁
  * 定义：顾名思义，能加在数据库表格行级别的数据的锁
  * 实现原理：对索引加锁
  * 特点：
    * 只有通过索引条件检索数据时，InnoDB才会使用行级锁，否则会使用表级锁
    * 即使是访问不同行的记录，如果使用的是相同的索引键，会发生锁冲突
    * 如果数据表建有多个索引时，可以通过不同的索引锁定不同的行

---

#### 3.2. Mysql的锁

##### 3.2.1. MyISAM的锁

* 支持锁的：表级锁-table-level locking
  * 支持锁模式：
    * 表共享读锁-Table Read Lock
    * 表独占写锁-Table Write Lock
* 表级锁特点：开销小，加锁快；不会出现死锁，锁定粒度大，发生锁冲突的概率高,并发度低
* 适用情况：适合并发性不高，以查询为主，少量更新的应用
* 如何加锁：
  * 默认加锁：
    * 执行查询语句时，会自动给涉及到表加读锁
    * 在执行更新操作时，会加写锁
  * 显示加锁：
    * 使用LOCK TABLE
    * 显示加锁后只能访问加锁的表，不能访问其他表
* 加锁注意事项：
  * 对同一个表来说，读锁不会阻塞读锁，但会阻塞写锁
  * 对同一个表来说，写锁会阻塞读锁
  * 对同一个表来说，读锁和写锁同时申请锁，优先分配给写锁
  * 对同一个表来说，长时间的查询可能会导致写锁发生饥饿

---

##### 3.2.2. InnoDB的锁

* 支持的锁：支持表级锁&行级锁-row-level locking
  * 支持锁模式：
    * 共享锁
    * 排他锁
* 如何加锁：
  * 默认加锁：
    * 对于 UPDATE、 DELETE 和 INSERT 语句，InnoDB会自动给涉及数据集加排他锁（X)
    * 对于普通 SELECT 语句，InnoDB不会加任何锁
  * 显示加锁：
    * lock in share mode 显式的加共享锁
    * for update 显式的加排他锁
* 加锁注意事项：
  * 对同一个表，线程A加了共享锁后，线程B加了共享锁，那么两个线程需要进行更新操作时会产生死锁
* 死锁检测：
  * InnoDB发生死锁后一般能自动检测到，并使一个事务释放锁并回退，另一个事务获得锁，继续完成事务
  * 死锁检测也是要耗费资源的，应当尽量避免死锁，怎么避免？
    * 不同的程序会并发的存取多个表，应尽量约定以相同的顺序来访问表-思路与并发顺序获取锁思路一致

---

#### 3.3. MySQL基本语句

##### 3.3.1. DML-CRUD

* 定义：data manipulation language-数据操纵语言
* 作用：用来对数据库的数据进行一些操作
* 包括语句：SELECT、UPDATE、INSERT、DELETE
  * `SELECT 列名称 FROM 表名称`
  * `UPDATE 表名称 SET 列名称 = 新值 WHERE 列名称 = 某值`
  * `INSERT INTO table_name (列1, 列2,...) VALUES (值1, 值2,....)`
  * `DELETE FROM 表名称 WHERE 列名称 = 值`

---

##### 3.3.2. DDL

* 定义：data definition language-数据库定义语言
* 作用：定义或改变表的结构，数据类型，表之间的链接和约束等工作
* 包括语句：CREATE TABLE，ALTER TABLE，ALTER COLUMN，DROP TABLE，DROP DATABASE
  * 新建表：`CREATE TABLE 表名 (列名称1 数据类型, 列名称1 数据类型,...)`

  * 删除表：`DROP TABLE 表名;`

  * 重命名表：`ALTER TABLE 表名 rename 新表名;`

  * 添加列：`ALTER TABLE 表名 add 列名 列数据类型 [after 插入位置];`

  * 修改列：`ALTER TABLE 表名 change 列名称 列新名称 新数据类型;`

  * 删除列：`ALTER TABLE 表名 drop 列名称 `

  * 删除数据库：`DROP DATABASE 数据库名`

  * 删除索引-增加索引：

    ```
    ALTER TABLE 表名
    DROP INDEX 要删除的索引,
    ADD INDEX 新增的索引(作为索引的列名，可多个);
    ```

---

##### 3.3.3. DCL

* 定义：Data Control Language-数据库控制语言
* 作用：设置或更改数据库用户或角色权限的语句
* 包括语句：grant,deny,revoke
  * grant授权
  * revoke删除权限

---

##### 3.3.4. 其他

- 登陆：`mysql -h 主机名 -u 用户名 -p`
  - **-h :** 该命令用于指定客户端所要登录的MySQL主机名，登录当前机器该参数可以省略
  - **-u :** 所要登录的用户名;
  - **-p :** 告诉服务器将会使用一个密码来登录
- 进入数据库：`use 数据库名`
- 展示数据库中的表：`show tables;`
- 展示一个表的创建语句: `show create table 表名;`
- 展示表中的字段：`show columns from 表名;`
- 导出数据：`SELECT XXX -> INTO OUTFILE '/XXX.txt'`
- 导出SQL格式数据：`mysqldump  -uroot -p --all-databases`
- 导入数据：`mysql -u用户名    -p密码    <  要导入的数据库数据`

---

#### 3.4. MySQL的数据类型

* 定义：数据类型是指列、存储过程参数、表达式和局部变量的数据特征，它决定了数据的存储格式，代表了不同的信息类型

##### 3.4.1. 数据类型简单分类

  * 整数类型：BIT、BOOL、TINY INT、SMALL INT、MEDIUM INT、 INT、 BIG INT
  * 浮点数类型：FLOAT、DOUBLE、DECIMAL
  * 字符串类型：CHAR、VARCHAR、TINY TEXT、TEXT、MEDIUM TEXT、LONGTEXT、TINY BLOB、BLOB、MEDIUM BLOB、LONG BLOB
  * 日期类型：Date、DateTime、TimeStamp、Time、Year
  * 其他：BINARY、VARBINARY、ENUM、SET、Geometry、Point、MultiPoint、LineString..

---
##### 3.4.2.整数类型-和Java类型比较
* tinyint(m) - 1个字节 - byte
* smallint(m) - 2个字节 - short
* smallint unsigned(m) - 2个字节 - char
* mediumint(m) - 3个字节 - 无对应类型
* int(m) - 4个字节 - int
* bigint(m) -8个字节 - long

---
##### 3.4.3. 浮点型：
* float(m,d) m总个数，d小数位 - 4字节 - float
* double(m,d) m总个数，d小数位 -8字节 - double

---
##### 3.4.4. 字符串：string
* char(n)- 固定长度，最多255个字符，小于定义长度则补空格，查询之时再将空格去掉。所以char类型存储的字符串末尾不能有空格，不管是存入几个字符，都将占用4个字节
* varchar(n)- 固定长度，最多65535个字符，实际占用空间为：存入的实际字符数+1个字节
  * mysql 5.5.版本之前 varchar(32) 代表32各字节,utf8每个汉字3各字节，能容纳10个汉字
  * mysql 5.5.版本之后 varchar(32) 代表32个字符，一个中文一个字符，能容纳32个汉字
* nchar：定长的，同char，小于定义长度则补空格，不同的是，比char多了个“N”，表示存储的是Unicode数据类型的字符
* nvarchar：同char和nchar的区别，中文字符在varchar中占2个长度，在nvarchar中占1个长度
* tinytext - 可变长度，最多255个字符
* text - 可变长度，最多65535个字符,不能有默认值，查找速度比varchar慢，实际占用空间为：实际字符数+2个字节
* mediumtext - 可变长度，最多2的24次方-1个字符
* longtext - 可变长度，最多2的32次方-1个字符

---
##### 3.4.5. 日期时间类型
* date - 日期 '2008-12-2'
* time - 时间 '12:25:36'
* datetime 
  * 日期时间 '2008-12-2 22:06:44' 
      * 占用空间：8字节
      * 存储的时间范围:1000-01-01 - 9999-12-31
      * 特点：允许为空值，可以自定义值，系统不会自动修改其值
* timestamp 
  * 自1970-01-01 到现在经过的秒数
  * 占用空间：4字节
  * 存储的时间范围：1970-01-01 2038-01-19
  * 特点：允许为空值，但是不可以自定义值，默认情况下以后任何时间修改表中的记录时，对应记录的timestamp值会自动被更新为当前的系统时间

---

##### 3.4.6. 数据类型的属性

* NULL: 数据列可为NULL

* NOT NULL: 数据列不能为NULL
* DEFAULT：设置默认值
* PRIMARY KEY：设置数据列为主键
* AUTO_INCREMENT：设置某列自增
* UNSIGNED：无符号
* CHARACTER SET name：指定字符集

---

##### 3.4.7. 如何选取数据类型

* 指定数据类型的时候一般是采用从小原则，减少占用空间，提升运行效率
* 根据选定的存储引擎，确定如何选择合适的数据类型
  * InnoDB ：对于InnoDB数据表，内部的行存储格式没有区分固定长度和可变长度列（所有数据行都使用指向数据列值的头指针），对mysql来说区别不大，主要的性能因素是数据行使用的存储总量。由于CHAR平均占用的空间多于VARCHAR，因 此使用VARCHAR来最小化需要处理的数据行的存储总量和磁盘I/O是比较好的。
  * MyISAM：用固定长度(CHAR)的数据列代替可变长度(VARCHAR)的数据列

---

#### 3.5. MySQL高级语句

##### 3.5.1. 查询的五种子句

* where(条件查询)
  * 针对表中的列发挥作用，查询数据
  * 使用
    * `where 列名=值`
    * `where 值 IN 表的一列数据` IN 运算符用于 WHERE 表达式中，以列表项的形式支持多个选择
    * `where 值 NOT IN表的一列数据`NOT IN：表示与 IN 相反的意思，即不在这些列表项内选择
* having（筛选）
  * 对查询结果中的列发挥作用，筛选数据
  * having子句中可以使用字段别名，而where不能使用
  * having子句能够使用统计函数，但where不能使用
* group by（分组）
  * 可以简单理解为合并一个表中的几行数据生成一行新表的新数据
  * 需与统计函数（聚合函数）一起使用才有意义
  * 统计函数：
    * max：求最大值 
    * min：求最小值 
    * sum：求总数和 
    * avg：求平均值 
    * count：求总行数
* order by（排序）
  * 单个字段降序排序	`order by 列名 desc;`
  * 单个字段升序排序 `order by 列名 asc;`
  * 多个字段排序`order by 列名一 desc,列名二 desc;`
* limit（限制结果数）
  * 1个参数：`limit 5` 返回前5行
  * 2个参数：`limit 5,10` 返回6-15行

---

##### 3.5.2. 模糊查询

* %: 表示任意个或多个字符。可匹配任意类型和长度的字符
* _: 表示任意单个字符。匹配单个任意字符，它常用来限制表达式的字符长度语句
* escape: 使得转义字符后面的%等就不作为通配符使用了（用于确实需要匹配%和_的情况）
* REGEXP:,NOT REGEXP: 使用正则
* 示例：
  * 匹配以“上”开头的字符串
    * `where 字段名 like '上%'`
    * `where 字段名 REGEXP '^上'`
  * 匹配以“%OK”结尾的字符串
    * `where 字段名 like '%' escape'%OK'`

---

##### 3.5.3. 连接查询

* 外连接: 
  * left/right join
  * left join以左表为主；right join 以右表为主
  * 以某张表为主,取出里面的所有记录, 然后每条记录与另外一张表进行连接，
  * 不管能不能匹配上条件,最终都会保留，
  * 能匹配,正确保留; 不能匹配,其他表的字段都置空为NULL
* 内连接：
  * inner join
  * 从左表中取出每一条记录,去右表中与所有的记录进行匹配
  * 匹配必须是某个条件在左表中与右表中相同最终才会保留结果,否则不保留
* 交叉连接：
  * 左表 cross join 右表 / from 左表,右表
  * 效果：形成两表的笛卡儿积

---

##### 3.5.4. 联合查询

* 效果：将多次查询(多条select语句), 在记录上进行拼接(字段不会增加）
* 语法：多条select语句构成: 每一条select语句获取的字段数必须严格一致(但是字段类型无关)
* 示例：`Select 语句1 Union [union选项] Select 语句2；`
* union选项：
  * All: 保留所有，不管重复
  * Distinct：会去重；不给union选项，则默认为Distinct
* 联合查询中使用 order by
  * select查询语句必须被()括起来
  * order by 必须配合limit使用

---

##### 3.5.5. 子查询

* 查询是在某个查询结果之上进行的
* 分类
  * 按位置分：
    * from 子查询
    * where 子查询
    * exists 子查询
      * exists的条件就像一个bool条件，当能返回结果集则为true，不能返回结果集则为 false
      * exists左边的查询每进行一次都需要 先执行exists且返回true
  * 按查询结果分：
    * 标量子查询：子查询结果为1行1列
    * 列子查询：1列多行
    * 行子查询：1行多列
    * 表子查询：多行多列

---

#### 3.6. 函数

##### 3.6.1.  数字函数

* ABS(X):返回X的绝对值 
* MOD(N,M)或%:返回N被M除的余数
* FLOOR(X):返回不大于X的最大整数值
* CEILING(X):返回不小于X的最小整数值
* ROUND(X) :返回参数X的四舍五入的一个整数

---

##### 3.6.2. 字符串函数

* ASCII(str):返回字符串str的最左面字符的ASCII代码值。如果str是空字符串，返回0。如果str是NULL，返回NULL
* CONCAT(str1,str2,...):返回来自于参数连结的字符串。如果任何参数是NULL，返回NULL
* LENGTH(str):返回字符串str的长度
* LOCATE(substr,str):返回子串substr在字符串str第一个出现的位置，如果substr不是在str里面，返回0
* LEFT(str,len):返回字符串str的最左面len个字符
* SUBSTRING(str,pos):从字符串str的起始位置pos返回一个子串
* TRIM(str):返回字符串str，删除指定的前缀或后缀，默认删除空格`TRIM(BOTH 'x' FROM 'xxxbarxxx'); `
* LTRIM(str):返回删除了其前置空格字符的字符串str
* REPLACE(str,from_str,to_str):返回字符串str，其字符串from_str的所有出现由字符串to_str代替
* REVERSE(str):返回颠倒字符顺序的字符串str
* INSERT(str,pos,len,newstr):返回字符串str，在位置pos起始的子串且len个字符长的子串由字符串newstr代替

---

##### 3.6.3. 日期函数

* DAYOFWEEK(date):返回日期date的星期索引(1=星期天，2=星期一, …7=星期六)
* WEEKDAY(date):返回date的星期索引(0=星期一，1=星期二, ……6= 星期天)
* DAYOFMONTH(date):返回date的月份中的日期，在1到31范围内
* DAYNAME(date):返回date的星期名字
* DATE_ADD(date,INTERVAL expr type) ,进行日期增加的操作，可以精确到秒
* NOW():以‘YYYY-MM-DD HH:MM:SS’或YYYYMMDDHHMMSS格式返回当前的日期和时间

---

#### 3.7. 存储过程

* 定义：存储过程Procedure是一组为了完成特定功能的SQL语句集合，经编译后存储在数据库中，用户通过指定存储过程的名称并给出参数来执行
  * 包含逻辑控制语句和数据操纵语句
  * 可以接受参数、输出参数、返回单个或多个结果集以及返回值
* 优点：
  * 能够实现较快的执行速度
    * 存储过程是预编译的，在首次运行一个存储过程时，查询优化器对其进行分析、优化，并给出最终被存在系统表中的存储计划
  * 减轻网络流量/减少查询次数
* 劣势：
  * 每个连接的内存使用量将会增加
  * 相对复杂、难以调试、维护
* DELIMITER
  * 作用：修改分隔符
  * 效果：`DELIMITER //` ：将分隔符;修改为//
  * 存储过程会使用到;号，为了保证存储过程是按整体解释执行的，将分割符修改为存储过程不使用的符号
* 创建存储过程:CREATE PROCEDURE XXX() BEGIN DML EBD
  * CREATE PROCEDURE 存储过程的名称()
  * `BEGIN`和`END`之间的部分称为存储过程的主体
* 调用存储过程：
  * CALL 存储过程的名称()；
* 删除存储过程：
  * DROP PROCEDURE IF EXISTS 存储过程的名称;
* 展示存储过程创建语句：
  * show create procedure seckill.execute_seckill\G
* 带参数的存储过程：
  * CREATE PROCEDURE XXX(in param1bigint, in param2 timestamp, out r_result int)
  * in 说明为输入参数
  * out 说明为返回值
  * inout 既将值得副本传入，也可修改该值返回
* 变量
  * 声明变量
    * DECLARE insert_count int DEFAULT 0;
    * 声明变量insert_count类型为int 默认值为0
  * 分配变量值
    * SET insert_count = 10;
  * 变量范围
    * 在`BEGIN END`块内声明一个变量，超出END无效
    * 以`@`符号开头的变量是会话变量。直到会话结束前它可用和可访问
* 提交&事务：
  * START TRANSACTION 后，只有当commit数据才会生效，ROLLBACK后就会回滚
  * autocommit 为 0：只有当commit数据才会生效，ROLLBACK后就会回滚
  * autocommit 为1 & 没有START TRANSACTION：调用ROLLBACK是没有用的。即便设置了SAVEPOINT
    * SAVEPOINT：保存点，方便回退到指定地方

---

#### 3.8. 视图

##### 3.8.1. 数据库视图

* 定义：从一个或多个表导出的表；数据库中只存储视图的定义：有带连接得select查询语句组成
* 特点：数据库视图是动态的，因为它与物理模式无关，只与定义的语句有关，当真实的表的数据发生变化时，视图也反映了这些数据的变化
* 优点：
  * 简化复杂查询，使复杂的查询易于理解和使用
  * 提供额外的安全层，允许您创建只读视图，以将只读数据公开给特定用户
  * 可以提供计算列
* 缺点：
  * 性能差：sql server必须把视图查询转化成对基本表的查询
  * 对表的依赖：每当更改与其相关联的表的结构时，都必须更改视图

---

##### 3.8.2. MySQL视图

* 实现算法

  - 临时表算法：根据视图定义创建临时表，针对临时表执行查询操作
    - 视图中包含无法再原表和视图记录建立一一映射的情况，使用临时表算法
    - 无法被更新
  - 合并算法：根据视图定义，将视图查询定义为一个组合查询

* 存储形式

  * 视图的版本：视图被更改，视图的副本会被复制到arc文件夹下，名字改为`视图名.from-0000X`

* MySQL视图限制：

  * 不能在视图上创建索引
  *  5.7.7之前版本，不能再select语句的from子句中使用子查询来定义视图
  * 删除 or 重命名 base table会导致视图无效，可使用check table检查视图是否有效

---

##### 3.8.3. 创建视图

  * 代码：

    ```sql
    CREATE 
       [ALGORITHM = {MERGE  | TEMPTABLE | UNDEFINED}]
    VIEW [database_name].[view_name] 
    AS
    [SELECT  statement]
    ```

  * ALGORITHM参数解析

    * 使用MERGE算法
      * 效果：将输入的查询与的视图定义的查询语句组合成一个查询
      * 限制：
        * 如果SELECT语句包含集合函数 或 group by ,having,子查询，limit,union等，不能使用MERGE
        * 如果select语句没有引用表，不能使用MERGE
        * 如果不允许使用MERGE算法，则MySQL会更改算法为UNDEFINED
    * 使用TEMPTABLE算法
      * 效果：MySQL会根据视图定义创建一个临时表，查询基于临时表进行
      * 限制：
        * 效率相对较低，因为必须创建临时表来存储结果集并将数据从基表移动到临时表
        * 视图不可更新
    * UNDEFINED算法
      * 效果：创建视图没有显示指定算法时，UNDEFINED是默认算法，UNDEFINED会在MERGE和TEMPTABLE中选一个，优先选择MERGE

* 视图名解析

  * 视图和表共享相同的命名空间，因此视图和表不能具有相同的名称，使用`show tables` or `SHOW FULL TABLES` 可查看到视图
  * 视图的名称必须遵循表的命名规则

* select语句解析

  * select语句可以从数据库中存在的任何表或视图查询数据
  * 限制：
    * 可以在where语句中包含子查询，不能再from子句中包含子查询
    * 不能引用任何变量

---

##### 3.8.4. 视图更新

* 检查特定数据库中可更新视图

  ```sql
  SELECT 
      table_name, is_updatable
  FROM
      information_schema.views
  WHERE
      table_schema = '数据库名';
  ```

* 具体更新语句同普通表更新无异

* 视图更新，数据存入视图中【但实际视图可能不应该包含该数据】导致视图不一致问题

  * 解决视图不一致问题：在创建或修改视图时使用`WITH CHECK OPTION`，这样在导致数据不一致的修改时，MySQL会报错
  * `WITH CHECK OPTION`：会检查正在更改的每个行，使得符合视图的定义
    * 检擦范围：默认使用`CASCADED`
    * `WITH CASCADED CHECK OPTION`：MySQL会循环检查视图的规则以及底层视图的规则
    * `WITH LOCAL CHECK OPTION`: 不会检查底层视图的规则

---

##### 3.8.5. 管理视图

* 显示视图定义：
  * 语法：`SHOW CREATE VIEW [database_name].[view_ name];`
* 修改视图
  * `ALTER VIEW` or `CREATE OR REPLACE VIEW`
  * 具体语法与创建视图一致，将 CREATE VIEW 替换为对应语句即可
* 删除视图
  * 语法：`DROP VIEW [IF EXISTS][database_name].[view_name]`

---

#### 3.9. 触发器

##### 3.9.1. SQL触发器

* 定义：存储在数据库目录中的一组SQL语句。每当与表相关联的事件发生时，就会执行或触发SQL触发器
* 实质：是一种特殊的存储过程
* 与存储过程不同之处：在于触发器时被动自动调用，存储过程必须显示调用
* 优点：提供了检查数据完整性的替代方法，可以捕获一定错误
* 缺点：只能提供扩展验证，并且无法替换所有验证；增加数据库服务器的开销

---

##### 3.9.2. MySQL触发器

* 定义：触发器是一组SQL语句，当对相关联的表上的数据进行更改时，会自动调用该语句
  * 可在 insert delete update语句之前or之后调用
  * 某些后台使用了这三种语句的语句也可触发触发器：replace语句后台调用insert
* 命名约定：
  * 形式：(BEFORE | AFTER)_tableName_(INSERT| UPDATE | DELETE)
  * 同表不能重名
* 存储：在数据目录中存储：/data/数据库名/,文件命名为tablename.TRG triggername.TRN
  * triggername.TRN：包含触发器定义
  * tablename.TRG：包含将触发器映射到相应的表
* 限制：
  * 不能使用准备语句：prapare,execute等
  * 不能使用提交或回滚的语句：commit,rename,alter,create等

---

##### 3.9.3. 创建触发器

* 语法：

  ```sql
  CREATE TRIGGER trigger_name trigger_time trigger_event
   ON table_name
   FOR EACH ROW
   BEGIN
   ...
   OLD.列名
   END;
  ```

* 语法解析：

  * trigger_name 触发器名
  * trigger_time 触发时机：after or before
  * trigger_event 造成触发的事件：insert or update or delete
  * table_name 触发器关联的表
  * 触发器的主体中，使用`OLD`关键字来访问受触发器影响的行

---

##### 3.9.4. 管理触发器

* 查看触发器：
  * 查看所有触发器：`SHOW TRIGGERS;`
  * 查看特定数据库的触发器: `SHOW TRIGGERS FROM 数据库名;`
  * 查看特定表的触发器：SHOW TRIGGERS FROM 数据库名 WHERE `table` = '表名';
* 删除触发器：
  * 删除特定触发器：`DROP TRIGGER table_name.trigger_name;`
* 修改触发器: 必须首先删除它并使用新的代码重新创建

---

#### 3.10. 事件

* 定义：一个包含SQL语句的命名对象
* 实质：基于预定义的时间表运行的任务，类似于UNIX中的cron作业或Windows中的任务调度程序

---

##### 3.10.1. 事件调度程序线程

* 作用：执行所有调度的事件
* 开启：`SET GLOBAL event_scheduler = ON;`
* 禁用并停止： `SET GLOBAL event_scheduler = OFF;`

---

##### 3.10.2. 创建事件

* 语法

  ```sql
  CREATE EVENT [IF NOT EXIST]  event_name
  ON SCHEDULE schedule
  DO
  event_body
  ```

* 语法解析：

  * event_name：事件名，在数据库模中必须是唯一的
  * schedule：
    * 事件是一次性事件，则使用语法：`AT timestamp [+ INTERVAL]`
    * 事件是循环事件，则使用`EVERY`子句：`EVERY interval STARTS timestamp [+INTERVAL] ENDS timestamp [+INTERVAL]`
  * event_body: 若有符合语句依然要放在BEGIN END中

---

##### 3.10.3. 事件管理

* 查看事件：`SHOW EVENTS FROM 数据库名;`

* 修改事件：

  * 语法：

    ```sql
    ALTER EVENT event_name
    ON SCHEDULE schedule
    ON COMPLETION [NOT] PRESERVE
    RENAME TO new_event_name
    ENABLE | DISABLE
    DO
      event_body
    ```

  * 语法解析：

    * on completion preserve 的时候,当event到期了,event会被disable,但是该event还是会存在
    * on completion not preserve的时候,当event到期的时候,该event会被自动删除掉

* 禁用事件：`alter event 事件名 disable;`

* 启用事件：`alter event 事件名 enable;`

* 重命名事件：`alter event 事件名 RENAME TO 新事件名`

* 将事件移动到其他数据库：`alter event 原数据库.事件名 RENAME TO 新数据库.事件名`

---

#### 3.11. 索引

* 作用：能够为数据的查询提供快捷的存取路径，减少磁盘I/O，提高检索效率
  * 使用索引：
    * 读入索引表，通过索引表【B+树，查询事时间复杂度为O(logn)】直接找到所需数据的物理地址，并把数据读入数据缓冲区
    * 能一次定位到特定值的行数，减少了遍历的行数
  * 不使用索引：
    * 直接去读表数据存放的磁盘块，读到数据缓冲区中再查找需要的数据
    * 且MySQL必须从第一条记录开始读完整个表，直到找出相关的行，表越大，查询数据所花费的时间就越多
* 缺点：降低对表的更新速度，占用磁盘空间：因为更新表时，还要额外保存索引文件
* 索引组成：索引值及记录相应物理地址的ROWID两个部分构成，并按照索引值有序排列，ROWID可以快速定位到数据库表符合条件的记录
* 实质：是一种数据结构
* 使用原则：
  * 适用数据较多，更新相对查询不频繁的情况；
  * 符合最左前缀匹配原则
  * 使用索引可以乱序
  * 索引列不能参与计算，参加计算会增加查询成本
  * 索引选择性应较高

---

##### 3.11.1. 分类

* 单列索引 &  组合索引
  * 单列索引：一个索引只包含单个列；一个表可以有多个单列索引
  * 组合索引/复合索引/联合索引：一个索引包含多个列
    * 最左前缀匹配原则：必须按照从左到右的顺序匹配
      * 一直向右匹配直到遇到范围查询(>、<、between、like)就停止匹配
      * where子句里面 顺序可以任意调整
    * `alter table 表名 add key abc(a,b,c);`建立组合索引为例说明
      * `select语句 where a = 1 and c = 1; ` key_len只会算a的字节数
      * `select语句 where a = 1 and b = 1; ` key_len会算ab
      * `select语句 where a = 1 and b = 1 and c = 1; ` key_len会算abc
      * `select语句 where b = 1  ` key_len会算abc，未使用索引
      * `select语句 where c = 1; ` key_len会算abc
      * 注意：若字段为null，需要1个字节的额外空间
* 唯一索引
  * 唯一索引是相对正常建立的普通索引来说的
  * 特点：
    * 索引列的值必须唯一，但允许有空值
    * 如果是组合索引，则列值的组合必须唯一
* 主键索引
  * 是一种唯一性索引，必须指定为“PRIMARY KEY”，不允许有空值，且每个表只能有一个主键
* 全文索引
  * 用于搜索很长一篇文章的时候
* 空间索引

---

##### 3.11.2. 创建索引

- 直接创建：
  - 普通索引：`CREATE INDEX 索引名 ON 表名(列名(length)); `
  - 唯一索引：`CREATE UNIQUE INDEX 索引名 ON 表名(列名(length)); `
    - length：如果列类型是BLOB和TEXT类型，必须指定 length
- 修改表结构添加索引：
  - 普通索引：`ALTER table 表名 ADD INDEX 索引名(列名)`
  - 唯一索引：`ALTER table 表名 ADD UNIQUE [index] 索引名(列名)`
  - 主键索引：`ALTER TABLE 表名 ADD PRIMARY KEY (列名);`
- 创建表时创建索引：
  - 普通索引：`CREATE TABLE 表名( XXX  INDEX [索引名] (列名(length))  );`
  - 唯一索引：`CREATE TABLE 表名( XXX  UNIQUE INDEX [索引名] (列名(length))  );` 
  - 主键索引：`CREATE TABLE 表名( XXX  PRIMARY KEY (列名),）`
- 创建前缀索引
  - 选择性高但索引太长，则可用前缀索引平衡选择性和索引大小
  - 查看选择性：
    - `SELECT count(DISTINCT(concat(列名1, 列名2)))/count(*) AS Selectivity FROM 数据库名.表名`
  - 创建基于列名1 和列名2前4个字符的前缀索引：
    - ALTER TABLE 数据库名.表名 ADD INDEX `索引名` (列名1, 列名2(4));

---

##### 3.11.3. 管理索引

* 查看索引：`SHOW INDEX FROM 表名; 
* 删除索引：
  * 删除主键：` ALTER TABLE 表名 DROP PRIMARY KEY;`
  * 删除一般索引：`ALTER TABLE testalter_tbl DROP INDEX 索引名;`
* 查看一句select语句是否使用了索引：`EXPLAIN select语句;`
  * 该操作返回一个表
  * 表的key列：表明使用的索引
  * 表的possible_keys列：MySQL在搜索数据记录时可以选用的各个索引
  * 表的key_len列：表明使用的索引的长度【字节数】
* 索引合并：
  * 能命中比联合索引更多的可能

---

##### 3.11.4. 索引的存储类型

* BTREE
  * b+树，层数越多，数据量指数级增长(InnoDB默认使用)
* HASH
  * 查询单条快，范围查询慢
* 实现：不同存储引擎对索引的实现方式是不同的
  * MyISAM引擎
    * 储存原理：使用带顺序访问指针的B+Tree作为索引结构，叶节点的data域存放的是数据记录的地址
    * 查找原理：MyISAM中索引检索的算法为首先按照B+Tree搜索算法搜索索引，如果指定的Key存在，则取出其data域的值，然后以data域的值为地址，读取相应数据记录
    * 归类：非聚集索引
    * 存储形式：MyISAM索引文件和数据文件是分离的，索引文件仅保存数据记录的地址
  * InnoDB
    * 存储原理：表数据文件本身就是按B+Tree组织的一个索引结构，这棵树的叶节点data域保存了完整的数据记录；且这个索引的key是数据表的**主键**，因此InnoDB表数据文件本身就是主索引
    * 查找原理：
    * 归类：聚集索引
      * 按主键的搜索十分高效，但是辅助索引搜索需要检索两遍索引
    * 存储形式：数据文件本身就是索引文件
    * 特点：
      * 因为InnoDB的数据文件本身要按主键聚集，所以InnoDB要求表必须有主键（MyISAM可以没有），如果没有显式指定，则MySQL系统会自动选择一个可以唯一标识数据记录的列作为主键，如果不存在这种列，则MySQL自动为InnoDB表生成一个隐含字段作为主键，这个字段长度为6个字节，类型为长整形
      * InnoDB的辅助索引data域存储相应记录主键的值而不是地址,即：以主键作为data域
    * 解释优化：
      * 不建议使用过长的字段作为主键，因为所有辅助索引都引用主索引，过长的主索引会令辅助索引变得过大
      * 非单调的字段作为主键在InnoDB中不是个好主意，因为InnoDB数据文件本身是一颗B+Tree，非单调的主键会造成在插入新记录时数据文件为了维持B+Tree的特性而频繁的分裂调整，十分低效，而使用自增字段作为主键则是一个很好的选择

---

#### 3.12. 应用架构

- 单点（Single），适合小规模应用
- 复制（Replication），适合中小规模应用 
- 集群（Cluster），适合大规模应用 
  - 集群常用高可用架构方案
    - Mysql主从架构
    - Mysql+DRDB架构
    - Mysql+MHA架构
    - Mysql+MMM架构

---

#### 3.13.  事务

* 定义：
  * 一个最小的不可再分的工作单元；通常一个事务对应一个完整的业务
  * 事务只和DML语句有关，或者说DML语句才有事务
* 事务并发的问题：
  * 脏读：事务A的数据未提交，事务B能读取A未提交的数据，事务A是可能回滚的，所以称事务B读到的数据就是脏数据
  * 不可重复读：事务A多次读取同一数据，事务B在事务A多次读取过程中对数据坐了更新并提交，导致事务A多次读取的**数据不一致**
  * 幻读：一个事务内，多次查询中数据**条数不一致 **
    * 解决方式：读写数据都锁表
* 四大特征：ACID
  * I - isolation 隔离性 的4个级别【由低到高】
    * 读未提交：read uncommitted
      * 涉及并发问题： 脏读；不可重复读；幻读
    * 读已提交：read committed
      * 涉及并发问题：不可重复读；幻读
      * 实践：是Oracle默认事务隔离级别
    * 可重复读：repeatable read
      * 涉及并发问题：幻读
      * 实践：是mysql默认事务隔离级别；
      * PS: Mysql InnoDB的可重复读并不保证避免幻读，需要应用使用加锁读来保证
    * 串行化：serializable
      * 涉及并发问题：事务串行执行，无并发问题
  * 隔离级别的作用范围
    * GLOBAL：对所有的会话有效 
    * SESSION：对当前的会话有效 
  * 管理隔离级别
    * 注意：8.0MySQL移除了tx_isolation,所以必须使用transaction_isolation
    * 设置 `SET XXXtransaction_isolation = '隔离级别';`
    * 查看：` select @@transaction_isolation;`

---

#### 3.14. 预处理

* 定义：先提交sql语句到mysql服务端，执行预编译，客户端执行sql语句时，再上传输入参数
* 工作原理：
  * 创建 SQL 语句模板并发送到数据库，预留的值使用参数 "?" 标记 
  * 数据库解析，编译，对SQL语句模板执行查询优化，并保存起来
  * 将应用绑定的值传递给参数（"?" 标记），数据库执行语句
* 优点：
  * 执行效率相对于一般的sql执行操作高,因为第二次执行只需要发送查询的参数，而不是整个语句
  * 防止sql注入，因为预处理将sql语句与数据分开发送
* 语法：
  * 创建：`prepare 预处理名称 from 'sql语句';`
  * 执行：`execute 预处理名称 [ using @变量名 [, @变量名1 ] ...];`

### 4. 常见面试题

### 5. 综述

### 6. 参考资料

* [关系型数据库-百度百科](https://baike.baidu.com/item/%E5%85%B3%E7%B3%BB%E5%9E%8B%E6%95%B0%E6%8D%AE%E5%BA%93/8999831?fr=aladdin)
* [数据库-百度百科](https://baike.baidu.com/item/%E6%95%B0%E6%8D%AE%E5%BA%93/103728)
* [Mysql学习总结（54）——MySQL 集群常用的几种高可用架构方案](https://blog.csdn.net/u012562943/article/details/79031902)
* [acid-百度百科](https://baike.baidu.com/item/acid/10738?fr=aladdin)
* [MySQL学习笔记(五)：MySQL表级锁和行级锁](https://www.cnblogs.com/zhanht/p/5431273.html)
* [浅谈 DML、DDL、DCL的区别](https://www.cnblogs.com/dato/p/7049343.html)
* [21分钟 MySQL 入门教程](https://www.cnblogs.com/webnote/p/5753996.html)
* [Mysql中timestamp用法详解](https://www.cnblogs.com/givemelove/p/8251185.html)
* [MYSQL中数据类型介绍](https://www.cnblogs.com/-xlp/p/8617760.html)
* [mysql having和where的区别](https://www.cnblogs.com/lixiuyuan999/p/6370454.html)
* [MySQL数据高级查询之连接查询、联合查询、子查询](https://blog.csdn.net/u011277123/article/details/54863371/)
* [MySql常用函数大全讲解](https://blog.csdn.net/sinat_38899493/article/details/78710482)
* [存储过程详解](https://www.cnblogs.com/xiangzhong/p/5038338.html)
* [create event时 on completion preserve 什么意思](https://blog.csdn.net/sanxian_li/article/details/39230899)
* [MySQL教程](https://www.yiibai.com/mysql/)
* [MySQL 索引-菜鸟教程](https://www.runoob.com/mysql/mysql-index.html)
* [MySQL(五) MySQL中的索引详讲](https://www.cnblogs.com/whgk/p/6179612.html)
* [一道关于索引的使用和key_len的计算的面试题](https://blog.csdn.net/Oops_Qu/article/details/78241447?locationNum=7&fps=1)
* [MySQL索引原理以及查询优化](https://www.cnblogs.com/bypp/p/7755307.html)
* [MySQL索引背后的数据结构及算法原理](http://blog.codinglabs.org/articles/theory-of-mysql-index.html)
* [MySQL——事务(Transaction)详解](https://blog.csdn.net/w_linux/article/details/79666086)
* [Mysql的四种事务隔离级别](https://www.cnblogs.com/huanongying/p/7021555.html)
* [轻松理解MYSQL MVCC 实现机制](https://blog.csdn.net/whoamiyang/article/details/51901888)
* [Mysql预处理和事务](https://www.cnblogs.com/onlyzc/p/8417032.html)

