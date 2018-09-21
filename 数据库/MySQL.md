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

- MyISAM，MySQL 5.0 之前的默认数据库引擎，高的插入，查询速度，不支持事务
- BDB, 支持Commit 和Rollback 等事务特性
- CSV, 逻辑上由逗号分割数据的存储引擎,不支持索引
-  InnoDB

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

#### 3.7. 应用架构

- 单点（Single），适合小规模应用
- 复制（Replication），适合中小规：模应用 
- 集群（Cluster），适合大规模应用 
  - 集群常用高可用架构方案
    - Mysql主从架构
    - Mysql+DRDB架构
    - Mysql+MHA架构
    - Mysql+MMM架构

---

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