package com.lsy.service.Imple;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lsy.domain.entity.Tag;
import com.lsy.mapper.TagMapper;
import org.springframework.stereotype.Service;
import com.lsy.service.TagService;

/**
 * 标签(Tag)表服务实现类
 *
 * @author makejava
 * @since 2025-01-21 18:04:34
 */
@Service("tagService")
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {

}
