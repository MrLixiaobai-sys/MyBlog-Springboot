package com.lsy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lsy.domain.ResponseResult;
import com.lsy.domain.dto.LinkListDTO;
import com.lsy.domain.entity.Link;

public interface LinkService extends IService<Link> {
    ResponseResult getAllLinks();

    ResponseResult pageLink(LinkListDTO linkListDTO);
}
