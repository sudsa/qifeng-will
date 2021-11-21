package com.qifeng.will.qifengwebflux.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.index.Indexed;

import java.io.Serializable;
import java.lang.annotation.Documented;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class User implements Serializable {
    private static final long serialVersionUID = -6138629548125135687L;
    @Id
    private String id;
    @Indexed
    private String username;
    private String phone;
    private String email;
    private String name;
    private Date birthday;
}
