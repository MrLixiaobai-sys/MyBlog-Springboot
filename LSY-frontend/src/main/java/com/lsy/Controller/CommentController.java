package com.lsy.Controller;

import com.lsy.domain.ResponseResult;
import com.lsy.domain.entity.Comment;
import com.lsy.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    //分页查询当前文章下的评论
    @GetMapping("/commentList")
    public ResponseResult commentList(Long articleId,Integer pageNum,Integer pageSize){
        return commentService.commentList(articleId,pageNum,pageSize);

    }

    //发表评论
    @PostMapping
    public ResponseResult addComment(@RequestBody Comment comment){
        return commentService.addComment(comment);
    }


}
