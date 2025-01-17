package com.lsy.service.Imple;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lsy.constants.CommentStatus;
import com.lsy.domain.ResponseResult;
import com.lsy.domain.Vo.CommentVo;
import com.lsy.domain.Vo.PageVo;
import com.lsy.domain.entity.Comment;
import com.lsy.mapper.CommentMapper;
import com.lsy.mapper.UserMapper;
import com.lsy.service.CommentService;
import com.lsy.utils.BeanCopyUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class CommentServiceImple extends ServiceImpl<CommentMapper, Comment> implements CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private UserMapper userMapper;

    @Override
    public ResponseResult commentList(Long articleId, Integer pageNum, Integer pageSize) {

//        1.根据articleId查询评论
        LambdaQueryWrapper<Comment> commentWrapper = new LambdaQueryWrapper<>();
        commentWrapper.eq(Comment::getArticleId,articleId);

//        2.查询是否有子评论
//        commentWrapper.eq(Comment::getToCommentId, CommentStatus.TO_COMMENT_USER_ID_NOT_EXIST);

//        3.分页查询
        Page<Comment> page = new Page<>(articleId,pageNum);
        page(page,commentWrapper);

        List<Comment> comments = page.getRecords();


        List<CommentVo> commentVos = BeanCopyUtils.copyBeanList(comments,CommentVo.class);

        for (CommentVo commentVo : commentVos){

            //获取回复评论的用户id
            Long  id = commentVo.getId();

            //根据id查询子评论的信息
            List<CommentVo> childComment = getChildComment(id);

            commentVo.setChildren(childComment);

            //对昵称和回复昵称进行赋值
            String nickName = userMapper.selectById(commentVo.getCreateBy()).getNickName();
            commentVo.setNickName(nickName);

            if(commentVo.getToCommentUserId()!=-1){
                String toNickName = userMapper.selectById(commentVo.getToCommentUserId()).getNickName();
                commentVo.setToCommentNickName(toNickName);
            }

        }

        PageVo pageVo =new PageVo(commentVos,page.getTotal());

        return ResponseResult.okResult(pageVo);
    }


    public List<CommentVo> getChildComment(Long id){


            if(id == -1){
                return null;
            }

            LambdaQueryWrapper<Comment> lambdaQueryWrapper = new LambdaQueryWrapper();
            lambdaQueryWrapper.eq(Comment::getRootId,id);
            List<Comment> commentList = commentMapper.selectList(lambdaQueryWrapper);

            List<CommentVo> commentVo = BeanCopyUtils.copyBeanList(commentList,CommentVo.class);

            return commentVo;


        }

}
