package com.lsy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lsy.domain.ResponseResult;
import com.lsy.domain.Vo.CategoryVo;
import com.lsy.domain.entity.Category;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface CategoryMapper extends BaseMapper<Category> {
    List<CategoryVo> selectCategory();
}
