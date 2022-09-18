package com.fukwang;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
@MapperScan("com.fukwang.mapper")
public class AppBoot {
    public static void main(String[] args) {
        SpringApplication.run(AppBoot.class,args);
        System.out.println("hello proposal!!!");
//        System.out.println("冲突解决 还没pull就开始写了 然后push上去");
//        System.out.println("冲突解决02090001");
//        System.out.println("冲突解决0209000222");
        System.out.println("冲突解决0209000333");
        System.out.println("冲突解决020900055577757");
    }
}
