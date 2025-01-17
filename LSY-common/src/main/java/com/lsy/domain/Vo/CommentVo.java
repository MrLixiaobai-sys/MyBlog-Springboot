package com.lsy.domain.Vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentVo {

    //评论id
    private long id;

    //文章id
    private Long articleId;

    //根评论id
    private Long rootId;

    //评论内容
    private String content;

    //所回复的目标评论的userid
    private Long toCommentUserId;

    //所回复的评论人昵称
    private String toCommentNickName;

    //回复目标评论id
    private Long toCommentId;

    private Long createBy;

    private Date createTime;

    //评论用户昵称
    private String NickName;

    private List<CommentVo> children;


}
