package com.lsy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lsy.domain.ResponseResult;
import com.lsy.domain.entity.Category;



public interface CategoryService extends IService<Category> {

    ResponseResult getCategory();
}
