package com.lsy.service.Imple;



import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lsy.domain.ResponseResult;
import com.lsy.domain.Vo.CategoryVo;
import com.lsy.domain.entity.Category;

import com.lsy.mapper.CategoryMapper;
import com.lsy.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class CategoryServiceImple extends ServiceImpl<CategoryMapper,Category> implements CategoryService {

    @Autowired
    CategoryMapper categoryMapper;

    @Override
    public ResponseResult getCategory() {
        return ResponseResult.okResult(categoryMapper.selectCategory());
    }
}
