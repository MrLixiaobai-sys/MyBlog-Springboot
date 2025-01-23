package com.lsy.Controller;

import com.lsy.domain.ResponseResult;
import com.lsy.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/content/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    //查询所有的分类接口（封装为响应对象）
    @GetMapping("/listAllCategory")
    public ResponseResult listAllCategory() {

        return categoryService.listAllCategory();
    }

    //导出分类数据为excel
    @GetMapping("/export")
    public void export(HttpServletResponse response){

        categoryService.export(response);
    }
}
