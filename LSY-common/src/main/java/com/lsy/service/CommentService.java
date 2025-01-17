package com.lsy.service;

import com.lsy.domain.ResponseResult;
import com.lsy.domain.entity.Comment;

public interface CommentService {
    ResponseResult commentList(Long articleId, Integer pageNum, Integer pageSize);

    ResponseResult addComment(Comment comment);
}
