package com.just.study.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@MapperScan("com.just.project.mapper")
@Configuration
public class MybatisConfig {

    /**
     * 分页插件
     * @return
     */
    @Bean("paginationInnerInterceptor")
    public PaginationInnerInterceptor innerInterceptor() {
        PaginationInnerInterceptor paginationInnerInterceptor = new PaginationInnerInterceptor();
        paginationInnerInterceptor.setMaxLimit(10000L);
        paginationInnerInterceptor.setDbType(DbType.MYSQL);
        return paginationInnerInterceptor;
    }

    /**
     * 加入mybatis - plus 插件中
     * @param innerInterceptor
     * @return
     */
    @Bean
    public MybatisPlusInterceptor plusInterceptor(@Qualifier("paginationInnerInterceptor") PaginationInnerInterceptor innerInterceptor) {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(innerInterceptor);
        return interceptor;
    }

}
