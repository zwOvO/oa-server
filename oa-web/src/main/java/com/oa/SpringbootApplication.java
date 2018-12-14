package com.oa;/*
 * Copyright (C) 2018 royal Inc., All Rights Reserved.
 */

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;

/** 
 * @Description: 指定项目为springboot，由此类当作程序入口，自动装配 web 依赖的环境
 * @author zhengwen
 * @since 2018年10月21日11:44:22
 *
 */
//@SpringBootApplication
//@MapperScan("com.oa.mapper")
public class SpringbootApplication  {
	public static void main(String[] args) {
        SpringApplication.run(SpringbootApplication.class, args);
    }
}
