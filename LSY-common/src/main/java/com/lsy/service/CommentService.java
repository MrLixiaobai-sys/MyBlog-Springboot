package com.lsy.service;

import com.lsy.domain.ResponseResult;

public interface CommentService {
    ResponseResult commentList(Long articleId, Integer pageNum, Integer pageSize);
}
