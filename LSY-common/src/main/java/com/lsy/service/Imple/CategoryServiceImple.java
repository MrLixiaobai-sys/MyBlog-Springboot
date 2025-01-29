package com.lsy.service.Imple;



import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lsy.domain.ResponseResult;
import com.lsy.domain.Vo.*;
import com.lsy.domain.dto.CategoryListDTO;
import com.lsy.domain.entity.Category;

import com.lsy.enums.BlogHttpCodeEnum;
import com.lsy.mapper.CategoryMapper;
import com.lsy.service.CategoryService;
import com.lsy.utils.BeanCopyUtils;
import com.lsy.utils.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.List;


@Service
public class CategoryServiceImple extends ServiceImpl<CategoryMapper,Category> implements CategoryService {

    @Autowired
    CategoryMapper categoryMapper;

    @Autowired
    CategoryService categoryService;

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

    //导出分类数据为excel
    //注意只能用void 类型，原因是 EasyExcel.write()方法中会自动关闭流，如果使用ResponseResult返回，会关闭response，导致下载失败
    @Override
    public void export(HttpServletResponse response) {
        try {
//            1.设置下载文件的请求头
            WebUtils.setDownLoadHeader("分类.xlsx",response);
//            2.获取需要导出的数据
            List<Category> categories = categoryService.list();
            List<ExcelCategoryVo> excelcategoryVos = BeanCopyUtils.copyBeanList(categories, ExcelCategoryVo.class);
//            3.将获得分类数据写入到Excel中
            EasyExcel.write(response.getOutputStream(), ExcelCategoryVo.class).autoCloseStream(Boolean.FALSE).sheet("分类导出")
                    .doWrite(excelcategoryVos);
        } catch (Exception e) {
            // 重置response 因为上述可能在写入时出现错误，导致响应数据不为json格式，需要重置
            response.reset();
            //如果出现异常也要响应json
            ResponseResult result = ResponseResult.errorResult(BlogHttpCodeEnum.SYSTEM_ERROR);
            WebUtils.renderString(response, JSON.toJSONString(result));
        }
    }

    /*
         需要分页查询分类列表。
         能根据分类名称进行模糊查询。
         能根据状态进行查询
     */
    @Override
    public ResponseResult listCategory(CategoryListDTO categoryListDTO) {

        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.hasText(categoryListDTO.getName()), Category::getName, categoryListDTO.getName());
        queryWrapper.eq(StringUtils.hasText(categoryListDTO.getStatus()), Category::getStatus, categoryListDTO.getStatus());
        Page<Category> page = new Page<>();
        page(page, queryWrapper);
        List<CategoryListVo> categoryListVos = BeanCopyUtils.copyBeanList(page.getRecords(), CategoryListVo.class);
        return ResponseResult.okResult(new PageVo(categoryListVos, page.getTotal()));
    }
}
