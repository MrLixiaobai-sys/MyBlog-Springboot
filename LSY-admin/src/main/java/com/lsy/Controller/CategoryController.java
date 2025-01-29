package com.lsy.Controller;

import com.lsy.domain.ResponseResult;
import com.lsy.domain.dto.CategoryListDTO;
import com.lsy.domain.entity.Category;
import com.lsy.service.CategoryService;
import com.lsy.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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
    @PreAuthorize("@ps.hasPermission('content:category:export')")
    @GetMapping("/export")
    public void export(HttpServletResponse response){

        categoryService.export(response);
    }

    //分页查询分类列表(支持进行模糊查询）
    @GetMapping("/list")
    public ResponseResult list(CategoryListDTO categoryListDTO) {

        return categoryService.listCategory(categoryListDTO);
    }

    //新增分类
    @PostMapping()
    public ResponseResult add(@RequestBody CategoryListDTO categoryListDTO) {
        Category category = BeanCopyUtils.copyBean(categoryListDTO, Category.class);
        categoryService.save(category);
        return ResponseResult.okResult();
    }

    //根据id查询分类
    @GetMapping("/{id}")
    public ResponseResult getInfo(@PathVariable("id") Long id) {
        Category category = categoryService.getById(id);
        return ResponseResult.okResult(category);
    }

    //修改分类
    @PutMapping()
    public ResponseResult update(@RequestBody CategoryListDTO categoryListDTO) {
        Category category = BeanCopyUtils.copyBean(categoryListDTO, Category.class);
        categoryService.updateById(category);
        return ResponseResult.okResult();
    }

    //删除分类
    @DeleteMapping("/{id}")
    public ResponseResult delete(@PathVariable("id") Long id) {
        categoryService.removeById(id);
        return ResponseResult.okResult();
    }

}
