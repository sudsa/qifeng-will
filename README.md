# springboot_demo

#### 介绍
springboot聚合项目

#### 软件架构
springboot2.1.9+聚合Maven项目


#### 安装教程

1.  xxxx
2.  xxxx
3.  xxxx

#### 使用说明

0.  springboot-utils 常用工具类汇总（bean拷贝，空值不用复制、Excel导入导出工具等）
1.  springboot-dynamic-datasource AOP多态数据源切换+atomikos分布式事务 20191228
2.  springboot-httpclient  httpclient各种使用方法举例
3.  springboot-redis redis的spring注解方式使用方法
4.  springboot-activemq activemq三种配置方法
5.  springboot-aoplog Spring切面日志使用方法+java源注解使用方法+脱敏注解使用方法 20200130
6.  springboot-contract-template 合同线上化，通过合同模板+表单数据生成PDF，并且加水印，并且支持docx转PDF 20200215
7.  springboot-multi-thread   多线程相关知识  
8.  springboot-spring-knowledge spring框架相关知识 20200322
9.  springboot-some-function  一些小功能 20200430   
10. springboot-design-pattern 设计模式 20200511
11. springboot-java-basic-note java基础知识 20200520
12. springboot-mongodb  springboot配置MongoDB 20200614
13. springboot-generator-code 代码生成器 20200619


#### 问题：
### 1.maven打包问题，使用以下插件会报RELEASE:repackage failed:
	<plugins>
		<plugin>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-maven-plugin</artifactId>
		</plugin>
	</plugins>
*解决：https://blog.csdn.net/Mint6/article/details/89078678	





