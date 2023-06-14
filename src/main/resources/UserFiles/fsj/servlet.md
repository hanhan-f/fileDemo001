# ** Servlet** 

> server applet: 概念：运行在服务器的小程序

Servlet就是一个接口，定义了Java类被浏览器访问到（tomcat识别）的规则

#### 实现servlet

```java
//1.创建普通Java类
//2.实现Servlet的规范，继承HttpServlet类
//3.重写service方法，用于处理请求
//4.设置注解，指定访问的路径,使用时只采用一种写法，value,urlPatterns均为访问地址
@WebServlet("/ser01")
@WebServlet(name="servlet",value = "/ser01")
@WebServlet(name="servlet",value = {"/ser01","001"})
@WebServlet(name="servlet",urlPatterns = "/ser01")
@WebServlet(name="servlet",urlPatterns = {"/ser01","001"})
public class servlet01 extends HttpServlet{
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("001");
        resp.getWriter().write("hello");
    }
}  

//法2
//继承HttpServlet父类GenericServlet
//重写方法
//设置注解
@WebServlet("/ser02")
public class servlet02 extends GenericServlet {
    @Override
    public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {
        System.out.println("继承GenericServlet");
    }
}



//法3
//实现Servlet接口
//重写方法
//设置注解
@WebServlet("/ser03")
public class servlet03 implements Servlet {
    @Override
    public void init(ServletConfig servletConfig) throws ServletException {

    }

    @Override
    public ServletConfig getServletConfig() {
        return null;
    }

    @Override
    public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {
        System.out.println("继承Servlet");
    }

    @Override
    public String getServletInfo() {
        return null;
    }

    @Override
    public void destroy() {

    }
}
```

### Servlet生命周期

init方法，在Servlet实例创建后指向，证明该Servlet有实例创建了

```java
//初始化方法，
//系统方法，服务器自动调用
//当请求到达Servlet容器时，Servlet容器会判断该Servlet对象是否存在，如果不存在，创建实例并初始化
//方法只执行一次
public void init(){
    System.out.println("Servlet实例被创建了");
}
```

service方法，每次有请求到达某个Servlet方法时执行，用来处理请求

```java
//就绪服务方法（处理请求数据）
//系统方法，服务器自动调用
//当有请求到达Servlet时，就会调用service方法
//方法可被多次调用
protected void service(){
    System.out.println("service被调用了")
}
```

destroy方法，Servlet实例销毁时执行，证明该Servlet的实例被销毁了

```java
//销毁方法
//系统方法，服务器自动调用
//当服务器关闭或应用程序停止时，调用该方法
//方法只执行一次
public void destroy(){
    System.out.println("Servlet实例被销毁了");
}
```

Servlet生命周期：servlet类加载->实例化->服务->销毁

## HttpServletRequest对象
### 接收请求

#### 1.1常用方法

```java
//获取请求时完整路径（从http开始到“?”结束）
        String url=requset.getRequestURL()+"";
        System.out.println("获取请求时完整路径"+url);
        //获取请求时的部分路径（从项目的站点名开始，到"?"结束）
        String uri=requset.getRequestURI();
        System.out.println("获取请求时的部分路径"+uri);
        //获取请求时的参数字符串（从“?”后面开始，到最后的字符串）
        String queryString=requset.getQueryString();
        System.out.println("获取请求时的参数字符串"+queryString);
        //获取请求方式（GET和POST）
        String method=requset.getMethod();
        System.out.println("获取请求方式"+method);
        //获取当前协议版本,一般（HTTP/1.1）
        String protocol=requset.getProtocol();
        System.out.println("获取当前协议版本"+protocol);
        //获取项目的站点名（项目对外访问路径）
        String webApp=requset.getContextPath();//上下文路径（项目名）
        System.out.println("获取项目的站点名"+webApp);
```

#### 1.2获取请求参数

```java
//获取指定名称的参数值
        String uname=requset.getParameter("uname");
        String pwd=requset.getParameter("upwd");
        System.out.println("uname:"+uname+"upwd"+pwd);

        //获取指定名称的参数的所有参数值,返回字符串数组（用于复选框传值）
        String[] hobbys=requset.getParameterValues("hobbys");
        if(hobbys!=null&&hobbys.length>0){
            for(String hobby:hobbys){
                System.out.println("爱好"+hobby);
            }
        }
```

### 请求乱码问题

```java
/**
         * 乱码原因
         *             tomcat8以上版本			tomcat7及以下版本
         *  GET请求       不会出错				会出现乱码
         *new String(requset.getParameter("uname").getBytes("ISO-8859-1"),"UTF-8")，针对任意请求方式，但是数据本身没有乱码，仍使用会导致新的乱码问题
         *  post请求      会乱码，可通过设置服务器解析编码格式解决
         *                request.setCharacterEncoding("UTF-8")
         */
```

### 请求转发

> 一种服务器行为，当客户端请求到达后，服务器进行转发，此时会将请求对象进行保存，地址栏中的URL地址不会改变，得到响应后，服务器端再将响应发送给客户端，从始至终只有一个请求发出

```java
        String name1=requset.getParameter("uname");
        //请求转发跳转到ser02
        //requset.getRequestDispatcher("ser02").forward(requset,response);
        //请求转发跳转到jsp页面
        //requset.getRequestDispatcher("index.jsp").forward(requset,response);
        //请求条转html页面
         requset.getRequestDispatcher("index.html").forward(requset,response);

```

#### request作用域

通过该对象可以再 一个请求中传递数据，作用范围：再一次请求中有效，及服务器跳转有效

```java
//设置域对象内容
        requset.setAttribute(String name,Object value);

        //获取域对象内容
        requset.getAttribute(String name);
        
        //删除域对象内容
        requset.removeAttribute(String name);
```

request域对象中的数据只在一次请求中有效，经过请求转发，request域中的数据依然存在，即在请求转发的过程中可以通过request来传输/共享数据

## HttpServletResponse对象

> Web服务器收到客户端的http请求，会针对每一次请求，分别创建一个用于代表请求的request对象和代表响应的response对象
>
> 获取客户端数据：request对象；向客户端输出数据response对象

### 1响应数据



两种形式

getWriter()：获取字符流，只适用字符串

getOutputStream()：获取字节流，适用于一切数据

 ### 2响应乱码问题

```java
//        响应乱码问题
//        getWriter()的乱码问题:默认编码格式（ISO-8859-1）中文必定乱码,解决方式在服务端告知服务器使用一种支持中文的编码格式
//        getOutputStream()的乱码问题,由于本身传递就是字节形式,可能出现乱码也可能不会
//        指定服务端编码
        response.setCharacterEncoding("UTF-8");

//        指定客户端的解码方式
        response.setHeader("content-type","text/html;charset=UTF-8");

//        同时设置客户端和服务端的编码格式
        response.setContentType("text/html;charset=UTF-8");
//		获取输出流
        PrintWriter writer=response.getWriter();
        writer.write("你好");
```

### 3重定向

> 服务端指导的客户端行为，跳转到某个页面（地址栏同时发生变化）
>
> 发生两次请求（原页面->待跳转页面）
>
> 两次request对象不相同，数据不共享

```java
/**
         * 重定向与请求转发的区别
         *      1.请求转发地址栏不会改变,重定向的地址栏会发生改变
         *      2.请求转发只有一次请求,重定向有两次
         *      3.请求转发是request对象可共享,重定向是request对象不共享
         *      4.请求转发是服务端行为,重定向是客户端行为
         *      5.请求转发的地址只能是当前站点(当前项目)的资源,重定向时地址可以时任何地址
         */
response.sendRedirect(url);
```

## Cookie对象

> 将一些只须保存在客户端，或者在客户端进行处理的数据，放在本地计算机上，不需要通过网络传输，提高网页处理的效率
>
> javax.servlet.http.Cookie。随着服务器端的响应发送给客户端，保存在浏览器，当下次访问时把Cookie再带回服务器
>
> Cookie格式：键值对用”=“连接，多个键值对间用”;“隔开

### 1.Cookie的创建

```java
//        Cookie的创建
        Cookie c=new Cookie("Cookie","cookie01");
//        发送(响应)Cookie数据
        response.addCookie(c);
//        获取Cookie数据(Cookie只能获取所有Cookie数据，不能获取单独某个，故返回数组)
        Cookie[] cookies = requset.getCookies();
//        判断是否为空
        if(cookies!=null&&cookies.length>0){
            for(Cookie cookie:cookies){
                String name=cookie.getName();
                String value=cookie.getValue();
                System.out.println("name:"+name+",value:"+value);
            }
        }
```

### 2.Cookie设置到期时间

> 用于指定该cookie何时失效，通过setMaxAge(int time)设置，单位秒
>
> 负整数，表示不存储该cookie，仅存在于浏览器内存中，cookie的maxAge属性默认值就是-1，一旦关闭浏览器窗口，那么cookie就会销毁
>
> 正整数，大于0的整数表示存储的秒数，浏览器将cookie保存到硬盘上，不随浏览器的关闭而销毁
>
> 0，表示删除Cookie

```java
//        存活30秒
        c.setMaxAge(30);
```

### Cookie的注意点

> 1.Cookie保存在当前浏览器中，即仅本电脑，本浏览器有效
>
> 2.Cookie中不能出现中文，若出现需要通过URLDecoder.encode()来进行编码，获取时通过URLDecoder.decode()来进行解码
>
> 3.同名Cookie会覆盖
>
> 4.Cookie的存储量由浏览器决定（存在上限），Cookie内容大小有限（4k左右）

### Cookie的路径

> Cookie的setPath设置Cookie的路径，这个路径直接决定服务器的请求是否会从浏览器加载某些cookie。

情景一：当前服务器下的任意资源都可以获取Cookie对象

```java
cookie.setPath("/");
```

情景二：当前项目下的任意资源可以获取Cookie对象

```java
//默认不设置Cookie的path或设置为当前站点名
cookie.setPath("/ser01");
```

情景三：指定项目下的任意资源可以获取Cookie对象

```java
//设置到指定的站点名
cookie.setPath("/ser01");
```

情景四：指定目录下的资源可以获取Cookie对象

```java
//设置指定项目的站点名
cookie.setPath("/ser01/cookie01");
```

> 注cookie的路径指的是可以访问的最顶层路径，其子路径也可以获取cookie

## HttpSession对象

HttpSession对象是javax.servlet.http.HttpSession的实例/接口，servlet容器使用此接口创建HTTP客户端和HTTP服务器之间的会话

Session的作用是为了标识一次会话，或确认一个用户；并在一次会话（一个用户的多次请求）期间共享数据。通过getSession（）方法获取当前会话的session对象

```java
//        获取session对象(如果存在则获取，不存在则创建Session对象)
        HttpSession session=requset.getSession();

//        获取session的标识符
        String id=session.getId();
        System.out.println(id);
//        获取session的创建时间(时间戳的方式)
        session.getCreationTime();
//        获取最后访问时间
        session.getLastAccessedTime();
//        判断是否最新会话
        session.isNew();
```

### 1.标识符JSESSIONID

> 每当一次请求到达服务器，如果开启了会话（访问了session），服务器第一步会查看是否从客户端回传一个名为JSESSIONID的cookie，若没有则创建一个新的session对象，并用唯一的sessionid为此次会话做一个标记，若回传了cookie，服务器会根据JSESSIONID的值查找是否存在对应session，若不存在则会创建一个新的session对象，若存在则返回该session对象，实现数据共享
>
> JSESSIONID有效时间为关闭浏览器

### 2.Session域对象

在一次会话中可以实现数据共享，请求转发和重定向都有效

```java
//设置域对象内容
        session.setAttribute(String name,Object value);

        //获取域对象内容
        session.getAttribute(String name);
        
        //删除域对象内容
        session.removeAttribute(String name);
```

### 3.Session对象的销毁

#### 默认时间到期

> 当客户端第一次请求servlet并且操作session时，session对象生成，Tomcat中session默认的存活时间为30min

默认时间在Tomcat中的conf目录web.xml文件中修改

```xml
<!-- session默认的最大不活动时间。单位分钟 -->
<session-config>
    <session-timeout>30</session-timeout>
</session-config>
```

#### 设定到期时间

```java
//设置session的最大不活动时间,单位 秒
session.setMaxInactiveInterval(15);//15秒
//查看session的最大不活动时间
session.getMaxInactiveInterval();
```

#### 立即失效

```java
//立即销毁session对象
session.invalidate();
```

#### 关闭浏览器

session底层依赖cookie实现，cookie对象默认只在浏览器内存中存在，关闭浏览器即失效

#### 关闭服务器

关闭服务器，session失效

## ServletContext对象

> 每一个web应用都有且仅有一个ServletContext对象，又称Application对象，在Servlet容器启动时会为每个web应用创建一个ServletContext对象

### ServletContext对象的获取

```java
//        通过request对象获取
        ServletContext servletContext=requset.getServletContext();
//        通过session对象获取
        ServletContext servletContext1=requset.getSession().getServletContext();
//        通过ServletConfig对象获取
        ServletContext servletContext2=getServletConfig().getServletContext();
//        直接获取
        ServletContext servletContext3=getServletContext();

//        常用方法
//        1.获取当前服务器的版本信息
        requset.getServletContext().getServerInfo();
//        2.获取项目的真实路径
        requset.getServletContext().getRealPath("/");
```

### ServletContext域对象

可以使得某个应用程序共享数据，但不建议放过多数据，因为ServletContext中的数据一旦存入，未通过手动移除将一直保存

```java
//设置域对象内容
        ServletContext.setAttribute(String name,Object value);

        //获取域对象内容
        ServletContext.getAttribute(String name);
        
        //删除域对象内容
        ServletContext.removeAttribute(String name);
```



Servlet三大域对象

1.request域对象

​	在一次请求中有效，请求转发有效，重定向失效

2.session域对象

​	在一次会话中有效，请求转发和重定向都有效，session销毁后失效

3.ServletContext域对象

​	在整个应用程序中有效，服务器关闭后失效

## Filter过滤器

> 使用时需要继承HttpFilter类，注解@WebFilter("/*")，标注哪些请求需要经过过滤器

### doFilter方法

- 发生过滤事件时调用，内部放置过滤逻辑

  ```java
  public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException{
      chain.doFilter(request,response)//放行通过
  }
  ```

  



## 文件的上传和下载

### 文件上传

前台页面

```html
<%--
    文件上传表单
        1.表单提交类型method="post"
        2.表单类型enctype="multipart/form-data" 文件上传类型
        3.表单元素类型,文件域设置name属性，未设置name属性后台会无法接受数据
     	4.设置文件提交地址action
--%>
  <form method="post" action="ser01" enctype="multipart/form-data">
      姓名：<input type="text" name="uname"><br>
      密码：<input type="file" name="myfile"><br>
      <button type="submit">提交</button>
  </form>
```

后台实现

@MultipartConfig//文件上传,必须设置该注解

```java

    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("文件上传....");
        //设置请求的编码
        request.setCharacterEncoding("UTF-8");
        //获取普通表单项(获取参数)
        String uname=request.getParameter("uname");//表单中表单元素name属性值
        System.out.println("uname:"+uname);
        //获取Part对象(Servlet将multipart/form-data 的post请求封装成Part对象)
        Part part=request.getPart("myfile");//表单中file文件域的name属性值
        //获取上传的文件名
        String fileName=GetName(part);
        System.out.println("filename："+fileName);
        //表单中file文件域的name属性
        String Name=part.getName();
        System.out.println("Name:"+Name);
        //得到文件存放的路径
        String filePath=request.getServletContext().getRealPath("/");
        System.out.println("文件存储路径:"+filePath);

        //存储到指定路径
        part.write(filePath+"/"+fileName);
    }
    public String GetName(Part part){
        String partHeader=part.getHeader("content-disposition");
        for(String content:partHeader.split(";")){
//            System.out.println(content);
            //trim()去除字符串前后的空格
            if(content.trim().startsWith("filename")){
                return content.substring(content.indexOf("=")+1).trim().replace("\"","");
            }
        }
        return null;
    }
```

### 文件下载

超链接下载

在使用超链接（a标签）时，如果遇到浏览器能够识别的资源时会默认显示文件内容，如果遇到浏览器不能识别的文件，则默认会下载；可以通过设置doload属性实现资源的下载

```html
<a href="/download/文本.txt">文本资源</a>
<a href="/download/图片1.jpg">图片资源</a>
.txt .jpg等文件浏览器能够识别，故点击默认为打开文件
<a href="/download/压缩.zip">压缩资源</a><br>
.zip .rar等文件浏览器无法识别，故点击的默认行为为下载文件
<h1>---------------</h1><br>
可通过添加download属性使超链接默认行为改为下载资源
<a href="/download/文本.txt" download="">文本资源</a>
<a href="/download/图片1.jpg" download="">图片资源</a>
<a href="/download/压缩.zip" download="">压缩资源</a>
```

后台实现下载

实现步骤

1.通过response.setContentType设置Content-type头字段的值，为某种浏览器无法激活某个程序来处理的MIME类型
