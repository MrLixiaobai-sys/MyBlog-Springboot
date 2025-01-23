package com.lsy.service.Imple;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lsy.domain.ResponseResult;
import com.lsy.domain.Vo.AllTagVo;
import com.lsy.domain.Vo.PageVo;
import com.lsy.domain.Vo.TagListVo;
import com.lsy.domain.entity.Tag;
import com.lsy.mapper.TagMapper;
import com.lsy.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.lsy.service.TagService;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 标签(Tag)表服务实现类
 *
 * @author makejava
 * @since 2025-01-21 18:04:34
 */
@Service("tagService")
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {

    @Autowired
    private TagService tagService;

    //根据标签名进行分页查询
    @Override
    public ResponseResult PageList(TagListVo tagVo, Integer pageNum, Integer pageSize) {

        LambdaQueryWrapper<Tag> queryWrapper = new LambdaQueryWrapper<>();
//        当name不为null时会进行模糊查询
        queryWrapper.eq(StringUtils.hasText(tagVo.getName()), Tag::getName, tagVo.getName());
        queryWrapper.eq(StringUtils.hasText(tagVo.getRemark()), Tag::getRemark, tagVo.getRemark());
        queryWrapper.eq(Tag::getDelFlag, 0);
        Page<Tag> page = new Page<>(pageNum, pageSize);
        page(page, queryWrapper);
        List<TagListVo> tagListVos = BeanCopyUtils.copyBeanList(page.getRecords(), TagListVo.class);
        return ResponseResult.okResult(new PageVo(tagListVos, page.getTotal()));
    }

    //根据id获取标签
    @Override
    public ResponseResult getTag(Long id) {
        Tag tag = tagService.getById(id);
        TagListVo tagListVo = BeanCopyUtils.copyBean(tag, new TagListVo().getClass());
        return ResponseResult.okResult(tagListVo);
    }

    //修改标签
    @Override
    public ResponseResult updateTag(TagListVo tagListVo) {
        Tag tag = BeanCopyUtils.copyBean(tagListVo, new Tag().getClass());
        tagService.updateById(tag);
        return ResponseResult.okResult();
    }

    //查询出所有标签，封装为响应对象
    @Override
    public ResponseResult listAllTag() {
        List<Tag> tags = tagService.list();
        List<AllTagVo> AllTagVos = BeanCopyUtils.copyBeanList(tags, AllTagVo.class);
        return ResponseResult.okResult(AllTagVos);
    }
}
