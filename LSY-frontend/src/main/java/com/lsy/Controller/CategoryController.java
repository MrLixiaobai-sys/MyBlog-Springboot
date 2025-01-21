package com.lsy.Controller;

import com.lsy.annotation.SystempLog;
import com.lsy.domain.ResponseResult;
import com.lsy.domain.entity.Category;
import com.lsy.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/category")
@Api(tags = "文章类型", description = "文章类型相关接口")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    //获取文章类型
    @GetMapping("/getCategory")
    @SystempLog(businessName = "获取文章类型")
    @ApiOperation(value = "文章类型接口",notes = "获取所有的文章类型")
    public ResponseResult getCategory(){
        return categoryService.getCategory();
    }

}
