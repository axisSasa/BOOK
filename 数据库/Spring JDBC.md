## Spring JDBC

### 1. 定义及用途

* 一个持久层框架，对jdbc进行了抽象和封装，消除了重复冗余的jdbc重复性的代码，使操作数据库变的更简单

### 2. 相对同类产品优势

* 封装了传统JDBC使用中的异常处理、连接数据库、等所有的模板话操作
* 代码更简洁
* 提高开发效率

### 3. 核心知识点

#### 3.1. JdbcTemplate类

* 作用：

  * 执行SQL查询，更新语句和存储过程调用
  * 在ResultSet上执行迭代并提取返回的参数值
  * 捕获JDBC异常并转换

* 使用示例

  ```
  public class StudentJDBCTemplate implements StudentDAO {
      private DataSource dataSource;
      private JdbcTemplate jdbcTemplateObject;
  
      public void setDataSource(DataSource dataSource) {
          this.dataSource = dataSource;
          this.jdbcTemplateObject = new JdbcTemplate(dataSource);
      }
      ...
  ```

* 主要方法

  * execute方法：可以用于执行任何SQL语句，一般用于执行DDL语句；
  * update方法及batchUpdate方法：update方法用于执行新增、修改、删除等语句；batchUpdate方法用于执行批处理相关语句；
  * query方法及queryForXXX方法：用于执行查询相关语句；
  * call方法：用于执行存储过程、函数相关语句。

---

#### 3.2. Mapper映射

* 作用：把resultSet对象中的数据映射为java对象

* 使用示例

  ```
  public class StudentMapper implements RowMapper<Student> {
      public Student mapRow(ResultSet rs, int rowNum) throws SQLException {
          Student student = new Student();
          student.setId(rs.getInt("id"));
          student.setName(rs.getString("name"));
          student.setAge(rs.getInt("age"));
          return student;
      }
  }
  ```

* 示例解析：

  * 实现RowMapper接口，实现mapRow方法
  * mapRow方法：接受ResultSet结果集 和行数做参数，返回Java对象

### 4. 常见面试题

### 5. 综述

### 6. 参考资料

* [Spring JDBC教程](https://www.yiibai.com/springjdbc/)
* [Spring JdbcTemplate使用详解](https://blog.csdn.net/u012843873/article/details/80049392)
* 