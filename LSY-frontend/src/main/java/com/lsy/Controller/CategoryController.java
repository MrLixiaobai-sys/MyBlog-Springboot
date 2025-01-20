package com.lsy.Controller;

import com.lsy.annotation.SystempLog;
import com.lsy.domain.ResponseResult;
import com.lsy.domain.entity.Category;
import com.lsy.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    //获取文章类型
    @GetMapping("/getCategory")
    @SystempLog(businessName = "获取文章类型")
    public ResponseResult getCategory(){
        return categoryService.getCategory();
    }

}
