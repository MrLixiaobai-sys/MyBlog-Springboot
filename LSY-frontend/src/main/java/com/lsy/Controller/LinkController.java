package com.lsy.Controller;

import com.lsy.domain.ResponseResult;
import com.lsy.service.LinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/Link")
public class LinkController {

    @Autowired
    private LinkService linkService;

//    获取所有友站信息
    @GetMapping("/getAllLinks")
    public ResponseResult getAllLinks(){
        return linkService.getAllLinks();
    }
}
