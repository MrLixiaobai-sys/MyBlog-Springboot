package com.lsy.service.Imple;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lsy.constants.LinkStatus;
import com.lsy.domain.ResponseResult;
import com.lsy.domain.Vo.LinkVo;
import com.lsy.domain.Vo.PageVo;
import com.lsy.domain.dto.LinkListDTO;
import com.lsy.domain.entity.Link;
import com.lsy.mapper.LinkMapper;
import com.lsy.service.LinkService;
import com.lsy.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class LinkServiceImple extends ServiceImpl<LinkMapper, Link> implements LinkService {

    @Autowired
    private LinkService linkService;


//    获取所有友站相关信息
    @Override
    public ResponseResult getAllLinks() {

        LambdaQueryWrapper<Link> linkWrapper = new LambdaQueryWrapper<>();
        linkWrapper.eq(Link::getDelFlag, LinkStatus.DEL_FLAG_NO_DEL);
        List<Link> links = linkService.list(linkWrapper);
        List<LinkVo> linkVos = BeanCopyUtils.copyBeanList(links, LinkVo.class);
        return ResponseResult.okResult(linkVos);
    }

    /*
     需要分页查询友链列表。
     能根据友链名称进行模糊查询。
     能根据状态进行查询
     */
    @Override
    public ResponseResult pageLink(LinkListDTO linkListDTO) {
        LambdaQueryWrapper<Link> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.hasText(linkListDTO.getName()), Link::getName, linkListDTO.getName());
        queryWrapper.eq(StringUtils.hasText(linkListDTO.getStatus()), Link::getStatus, linkListDTO.getStatus());
        Page<Link> page = new Page<>();
        page(page, queryWrapper);
        List<LinkVo> linkVos = BeanCopyUtils.copyBeanList(page.getRecords(), LinkVo.class);
        return ResponseResult.okResult(new PageVo(linkVos, page.getTotal()));
    }


}
