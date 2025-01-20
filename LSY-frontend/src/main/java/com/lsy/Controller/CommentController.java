package com.lsy.Controller;

import com.lsy.annotation.SystempLog;
import com.lsy.constants.CommentStatus;
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
    @SystempLog(businessName = "查询评论列表")
    public ResponseResult commentList(Long articleId,Integer pageNum,Integer pageSize){
        return commentService.commentList(CommentStatus.ARTICLE_COMMENT_TYPE,articleId,pageNum,pageSize);

    }

    //分页查询友链下的评论
    @GetMapping("linkCommentList")
    @SystempLog(businessName = "查询友链评论列表")
    public ResponseResult linkCommentList(Integer pageNum,Integer pageSize){
        return commentService.commentList(CommentStatus.LINK_COMMENT_TYPE,null,pageNum,pageSize);

    }

    //发表评论
    @PostMapping
    @SystempLog(businessName = "发表评论")
    public ResponseResult addComment(@RequestBody Comment comment){
        return commentService.addComment(comment);
    }


}
