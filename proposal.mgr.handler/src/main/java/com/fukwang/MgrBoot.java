package com.fukwang;

import com.fukwang.util.RedisUtil;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.Resource;
//import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;


@SpringBootApplication
//@EnableRedisHttpSession
@MapperScan("com.fukwang.mapper")
public class MgrBoot {


    public static void main(String[] args) {

        SpringApplication.run(MgrBoot.class,args);

        System.out.println("hello proposal11111!!!");
    }
}
