package com.fukwang.handler;

import com.fukwang.commandLineRunner.InitDictMap;
import com.fukwang.dto.DataDto;
import com.fukwang.pojo.TDict;
import com.fukwang.service.TDictService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


import javax.annotation.Resource;

@RestController
public class TDictHandler {
    @Resource
    private TDictService tDictService;

    @GetMapping("/initMain")
    public DataDto<TDict> GetDict() {
        DataDto<TDict> dto = new DataDto();
        dto.success(InitDictMap.getList());
        return dto;
    }
}
