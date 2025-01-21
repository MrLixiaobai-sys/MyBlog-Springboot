package com.lsy.Controller;

import com.lsy.domain.ResponseResult;
import com.lsy.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/content/tag")
public class TagController {

    @Autowired
    private TagService tagService;


    //输出tag表
    @GetMapping("/list")
    public ResponseResult listAll() {

        return ResponseResult.okResult(tagService.list());
    }
}
