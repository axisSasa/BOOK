HTTP协议 及其 扩展知识

* 了解HTTP协议：[HTTP协议详解](http://blog.51cto.com/13570193/2108347)
* Servlet运行过程：[javaweb学习总结(五)——Servlet开发(一)](https://www.cnblogs.com/xdp-gacl/p/3760336.html)
  * 涉及HTTP请求、web容器、Servlet
  * 了解 http和servlet的联系
  * 了解web容器和servlet的来联系
* Servlet的定义？
  * Servlet是一套规范，包含servlet 和 filter 这两项技术
    * javax.servlet.Filter;
  * javax.servlet.http是javax.servlet 基于HTTP协议的扩展
  * javax.servlet.http中HttpServletRequest、HttpServletResponse两个接口分别对应HTTP协议中的请求和响应
    * 这两个接口是由Web容器进行实例化的
  * 由上可知Servlet可看作是对HTTP协议做的面向对象的封装
  * 代码上来说一个继承HttpServlet的Java类就可以看作一个Servlet
* Web容器的定义？[Web容器、服务器、容器的理解总结](https://www.cnblogs.com/leiqiannian/p/7797188.html)
  * Servlet没有main()方法。Servlet受控于另一个Java应用，这个Java应用称为容器。
  * web服务器应用得到一个指向servlet的请求，此时服务器不是把这个请求交给servlet本身，而是交给部署该servlet的容器，要由容器调用servlet的方法(如 get请求 调用 doGet方法)
  * 容器的好处：让servle与Web服务器通信；对Servlet生命周期进行管理；提供多线程支持等
* springmvc 和Servlet的关系
  * springmvc 也是基于Servlet的封装
  * Spring MVC的入口DispatcherServlet是一个Servlet,且该Servlet拦截了所有的请求
  * 然后DispatcherServlet再对URL进行处理，这样springmvc就接管了Servlet的工作

