# 循环依赖
## 1.发生场景：
使用构造函数注入时，如果两个及其以上Bean的构造函数（入参）相互间引用、互为依赖项后，就会发生循环依赖。
Setter注入允许循环依赖，但可能导致有些功能无法使用，所以不建议在配置使用循环依赖。
## 2.Setter注入与构造函数注入的区别：
**Setter注入：** 在Bean实例创建完毕之后注入。  
**构造函数注入：** 在组件（Bean）创建期间被执行。   
## 3.自动注入：
### 概念：
让Spring容器自动地向Bean中注入依赖项；  
### 三种模式： 
byType、byName、constructor  
**byType：**     
Spring首先通过Java反射查看Bean定义的类（即，目的是为了查看类中的属性），然后尝试将容器中可用的"Bean注入"
与其类型相匹配，匹配成功的属性，再通过调用这些属性的set方法来注入。   
**byName：**   
容器尝试将属性名与Bean名称进行匹配，并注入匹配的Bean。   
**constructor:**   
该模式类似于ByType模式，使用constructor模式时，Spring尝试找到类型与构造函数参数项匹配的Bean。如果，如果针对某一参数
有多个候选Bean，Spring将无法注入该参数。

## 参考文件：
https://blog.csdn.net/qq_18298439/article/details/88818418