#问题：
##1.解决PDF添加水印报错iText： Font 'STSong-Light' with 'UniGB-UCS2-H' is not recognized
https://blog.csdn.net/HaHa_Sir/article/details/84350671
添加包：
    <dependency>
	    <groupId>com.itextpdf</groupId>
	    <artifactId>itext-asian</artifactId>
	    <version>5.2.0</version>
	</dependency>

##2.java.lang.NoClassDefFoundError: org/apache/poi/xwpf/usermodel/IRunBody异常:
3.9架包中没有IRunBody类，所以降低poi-ooxml架包版本：3.9->3.15:  
    <dependency>
		<groupId>org.apache.poi</groupId>
		<artifactId>poi-ooxml</artifactId>
		<version>3.15</version>
	</dependency>
	
##3.找不到org.apache.poi.wp.usermodel.Paragraph的类文件：
poi引用的包要保持版本号一致，把它改成3.15： 
		<dependency>
			<groupId>org.apache.poi</groupId>
			<artifactId>poi</artifactId>
			<version>3.15</version>
		</dependency>

##4.Invalid nested tag br found, expected closing tag：
html转pdf对html的语法格式要求严格，我增加了一个方法去处理不规范的格式handleIllegalHtmlFormat()