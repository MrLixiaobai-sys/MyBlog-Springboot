package com.lsy.Controller;

import com.lsy.annotation.SystempLog;
import com.lsy.domain.ResponseResult;
import com.lsy.service.LinkService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/link")
@Api(tags = "友链", description = "友链相关接口")
public class LinkController {

    @Autowired
    private LinkService linkService;

//    获取所有友站信息
    @GetMapping("/getAllLink")
    @SystempLog(businessName = "获取所有友站信息")
    @ApiOperation(value = "获取所有友站信息",notes = "获取所有友站信息")
    public ResponseResult getAllLinks(){
        return linkService.getAllLinks();
    }
}
