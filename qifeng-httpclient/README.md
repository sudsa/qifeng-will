#HttpClient 
##概念：
支持HTTP协议的客户端编程工具包。
##特性：
1*.基于标准的java语言,实现了Http1.0和Http1.1  
2*.实现了Http全部的方法（GET, POST, PUT, DELETE, HEAD, OPTIONS, TRACE）  
3*.支持HTTPS协议  
4*.支持代理服务器（Nginx等）  
5*.支持自动（跳转）转向  
6.Basic, Digest, NTLMv1, NTLMv2, NTLM2 Session, SNPNEGO/Kerberos认证方案  
7.连接管理器支持多线程应用。支持设置最大连接数，同时支持设置每个主机的最大连接数，发现并关闭过期的连接。
8.自动处理Set-Cookie中的Cookie  
9.插件式的自定义Cookie策略  
10.直接获取服务器发送的response code和 headers 
11.设置连接超时的能力 
##org.apache.commons.httpclient.HttpClient与org.apache.http.client.HttpClient的区别
Commons的HttpClient项目不再被开发,它已被Apache HttpComponents项目HttpClient和HttpCore模组取代，
提供更好的性能和更大的灵活性  
##使用HttpClient发送请求、接收响应步骤：
1.创建HttpClient对象  
2.创建请求方法的实例，并指定请求URL。要发送GET请求，创建HttpGet对象；要发送POST请求，创建HttpPost对象  
3.如果需要发送请求参数，可调用HttpGet、HttpPost共同的setParams(HttpParams params)方法来添加请求参数；
  对于HttpPost对象而言，也可调用setEntity(HttpEntity entity)方法来设置请求参数  
4.调用HttpClient对象的execute(HttpUriRequest request)发送请求，该方法返回一个HttpResponse  
5.调用HttpResponse的getAllHeaders()、getHeaders(String name)等方法可获取服务器的响应头；
  调用HttpResponse的getEntity()方法可获取HttpEntity对象，该对象包装了服务器的响应内容。程序
  可通过该对象获取服务器的响应内容 
6.释放连接。无论执行方法是否成功，都必须释放连接  

##其他：
用springboot的方法格式处理HttpClient(时间可以搭建一下)
https://blog.csdn.net/wsywb111/article/details/80311716

