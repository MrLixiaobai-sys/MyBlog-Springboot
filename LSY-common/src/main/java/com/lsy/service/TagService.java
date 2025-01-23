package com.lsy.service;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lsy.domain.ResponseResult;
import com.lsy.domain.Vo.TagListVo;
import com.lsy.domain.entity.Tag;

/**
 * 标签(Tag)表服务接口
 *
 * @author makejava
 * @since 2025-01-21 18:04:33
 */
public interface TagService extends IService<Tag> {

    ResponseResult PageList(TagListVo tagListVo, Integer pageNum, Integer pageSize);

    ResponseResult getTag(Long id);

    ResponseResult updateTag(TagListVo tagListVo);

    ResponseResult listAllTag();
}
