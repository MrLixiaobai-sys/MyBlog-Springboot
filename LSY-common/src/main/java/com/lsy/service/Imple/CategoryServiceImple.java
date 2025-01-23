package com.lsy.service.Imple;



import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lsy.domain.ResponseResult;
import com.lsy.domain.Vo.AllCategoryVo;
import com.lsy.domain.Vo.CategoryVo;
import com.lsy.domain.entity.Category;

import com.lsy.mapper.CategoryMapper;
import com.lsy.service.CategoryService;
import com.lsy.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CategoryServiceImple extends ServiceImpl<CategoryMapper,Category> implements CategoryService {

    @Autowired
    CategoryMapper categoryMapper;

    @Override
    public ResponseResult getCategory() {
        return ResponseResult.okResult(categoryMapper.selectCategory());
    }

    //查询所有的分类并封装为返回类型
    @Override
    public ResponseResult listAllCategory() {

        List<CategoryVo> categoryVos = categoryMapper.selectCategory();
        List<AllCategoryVo> allCategoryVos = BeanCopyUtils.copyBeanList(categoryVos, AllCategoryVo.class);
        return ResponseResult.okResult(allCategoryVos);
    }
}
