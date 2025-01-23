package com.lsy.Controller;

import com.lsy.domain.ResponseResult;
import com.lsy.domain.Vo.TagListVo;
import com.lsy.domain.entity.Tag;
import com.lsy.service.TagService;
import com.lsy.utils.BeanCopyUtils;
import javafx.scene.control.Tab;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/content/tag")
public class TagController {

    @Autowired
    private TagService tagService;


    //输出tag表（测试用）
//    @GetMapping("/list")
//    public ResponseResult listAll() {
//
//        return ResponseResult.okResult(tagService.list());
//    }

    //分页查询标签列表
    @GetMapping("/list")
    public ResponseResult list(TagListVo tagListVo, Integer pageNum, Integer pageSize) {
        return tagService.PageList(tagListVo, pageNum, pageSize);
    }

    //新增标签
    @PostMapping()
    public ResponseResult addTag(@RequestBody TagListVo tagListVo) {
        Tag tag = BeanCopyUtils.copyBean(tagListVo, new Tag().getClass());
        tagService.save(tag);
        return ResponseResult.okResult();
    }

    //删除标签
    //removeById为mybatis-plus的软删除，对于del_flag被配置了逻辑删除，所以removeById中的delete会被改为update
    @DeleteMapping("/{id}")
    public ResponseResult deleteTag(@PathVariable Long id) {
        tagService.removeById(id);
//        tagService.getBaseMapper().deleteById(id);          //物理删除
        return ResponseResult.okResult();
    }

    //获取标签信息
    @GetMapping("/{id}")
    public ResponseResult getTag(@PathVariable Long id) {
        return tagService.getTag(id);
    }

    //修改标签信息
    @PutMapping()
    public ResponseResult updateTag(@RequestBody TagListVo tagListVo) {
        return tagService.updateTag(tagListVo);
    }

    //查询所有标签接口（封装为响应对象）
    @GetMapping("/listAllTag")
    public ResponseResult listAllTag() {
        return tagService.listAllTag();
    }


}
