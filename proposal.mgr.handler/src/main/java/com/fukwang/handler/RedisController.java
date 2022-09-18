package com.fukwang.handler;


import com.fukwang.config.RedisConfig;
import com.fukwang.pojo.UserEntity;
import com.fukwang.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @program: springbootdemo
 * @Date: 2019/1/25 15:03
 * @Author: Mr.Zheng
 * @Description:
 */
@Slf4j
@RequestMapping("/redis")
@RestController
public class RedisController {

    private static int ExpireTime = 60;   // redis中存储的过期时间60s

    @Resource
    private RedisUtil  redisUtil;
    @Resource
    private RedisTemplate<String,String> redisTemplate;

    @RequestMapping("set")
    public String redisset(){
//        UserEntity userEntity =new UserEntity();
//        userEntity.setId(Long.valueOf(1));
//        userEntity.setGuid(String.valueOf(1));
//        userEntity.setName("zhangsan");
//        userEntity.setAge(String.valueOf(20));
//        userEntity.setCreateTime(new Date());

        //return redisUtil.set(key,userEntity,ExpireTime);

        return redisTemplate.opsForValue().get("ds");
    }

    @RequestMapping("get")
    public Object redisget(String key){
        return redisUtil.get(key);
    }

    @RequestMapping("expire")
    public boolean expire(String key){
        return redisUtil.expire(key,ExpireTime);
    }
}
