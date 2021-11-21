## validation校验框架中：@NotNull、@NotEmpty、@NotBlank的区别？
1) @NotNull:    
任何对象的value不能为null
2) @NotEmpty:     
集合对象的元素不为0，即集合不为空，也可以用于字符串不为null
3) @NotBlank:   
只能用于字符串不为null，并且字符串trim()以后length要大于0