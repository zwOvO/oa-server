package com.oa.config;

import com.baomidou.mybatisplus.spring.MybatisMapperRefresh;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;

public class MybatisMapperAutoRefresh {
    @Autowired
    SqlSessionFactory sqlSessionFactory;

    @Bean
    public MybatisMapperRefresh autoRefresh(@Value("classpath:/com/oa/mapper/xml/*Mapper.xml")Resource mapperLocations[], @Value("false") boolean enabled) {
        MybatisMapperRefresh mybatisMapperRefresh = new MybatisMapperRefresh(mapperLocations,sqlSessionFactory,enabled);
        return mybatisMapperRefresh;
    }
}
