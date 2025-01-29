package com.lsy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lsy.domain.ResponseResult;
import com.lsy.domain.dto.CategoryListDTO;
import com.lsy.domain.entity.Category;

import javax.servlet.http.HttpServletResponse;


public interface CategoryService extends IService<Category> {

    ResponseResult getCategory();

    ResponseResult listAllCategory();

    void export(HttpServletResponse response);

    ResponseResult listCategory(CategoryListDTO categoryListDTO);
}
