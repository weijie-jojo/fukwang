package com.fukwang.commandLineRunner;

import com.fukwang.pojo.TDict;
import com.fukwang.service.TDictService;
import org.springframework.boot.CommandLineRunner;

import org.springframework.stereotype.Component;
import javax.annotation.Resource;
import java.util.List;


@Component
public class InitDictMap implements CommandLineRunner {
    @Resource
    private TDictService tDictService;
    private static List<TDict> tDictList;

    @Override
    public void run(String... args) throws Exception {
        List<TDict> tDictLists = tDictService.selectAll();
        InitDictMap.tDictList = tDictLists;
    }
    public static List<TDict> getList(){

        return tDictList;
    }
}
