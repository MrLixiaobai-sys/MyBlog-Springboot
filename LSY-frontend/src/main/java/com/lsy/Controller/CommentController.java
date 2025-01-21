package com.lsy.Controller;

import com.lsy.annotation.SystempLog;
import com.lsy.constants.CommentStatus;
import com.lsy.domain.ResponseResult;
import com.lsy.domain.entity.Comment;
import com.lsy.service.CommentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comment")
@Api(tags = "评论", description = "评论相关接口")
public class CommentController {

    @Autowired
    private CommentService commentService;

    //分页查询当前文章下的评论
    @GetMapping("/commentList")
    @SystempLog(businessName = "查询评论列表")
    @ApiOperation(value = "查询评论列表",notes = "分页查询当前文章下的评论")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "articleId", value = "文章id", required = true, dataType = "Long"),
            @ApiImplicitParam(name = "pageNum", value = "页码", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "pageSize", value = "每页显示条数", required = true, dataType = "Integer")
    })
    public ResponseResult commentList(Long articleId,Integer pageNum,Integer pageSize){
        return commentService.commentList(CommentStatus.ARTICLE_COMMENT_TYPE,articleId,pageNum,pageSize);

    }

    //分页查询友链下的评论
    @GetMapping("linkCommentList")
    @SystempLog(businessName = "查询友链评论列表")
    @ApiOperation(value = "查询友链评论列表",notes = "分页查询友链下的评论")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "页码", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "pageSize", value = "每页显示条数", required = true, dataType = "Integer")
    })
    public ResponseResult linkCommentList(Integer pageNum,Integer pageSize){
        return commentService.commentList(CommentStatus.LINK_COMMENT_TYPE,null,pageNum,pageSize);

    }

    //发表评论
    @PostMapping
    @SystempLog(businessName = "发表评论")
    @ApiOperation(value = "发表评论",notes = "发表评论")
    @ApiImplicitParam(name = "comment", value = "评论信息", required = true, dataType = "Comment")
    public ResponseResult addComment(@RequestBody Comment comment){
        return commentService.addComment(comment);
    }


}
