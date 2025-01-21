package com.lsy.Controller;

import com.lsy.annotation.SystempLog;
import com.lsy.domain.ResponseResult;
import com.lsy.domain.entity.Article;
import com.lsy.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;


    //查询出浏览量最多的10条文章
    @GetMapping("/getMostViewsArticle")
    @SystempLog(businessName = "查询出浏览量最多的10条文章")
    public ResponseResult getMostViewsArticle() {
        return articleService.getMostViewsArticle();
    }

//    根据分类id查询出对于文章(分页查询)
    @GetMapping("/articleList")
    @SystempLog(businessName = "根据分类id查询出对于文章(分页查询)")
    public ResponseResult articleListByCategoryId(Integer categoryId,Integer pageNum,Integer pageSize){
        return articleService.articleListByCategoryId(categoryId,pageNum,pageSize);
    }

//    基于restful风格更加{id}查询出对应文章详情
    @GetMapping("{id}")
    @SystempLog(businessName = "基于restful风格更加{id}查询出对应文章详情")
    public ResponseResult getArticleDetail(@PathVariable Long id){
        return articleService.getArticleDetail(id);
    }

    @PutMapping("/updateViewCount/{id}")
    public ResponseResult updateViewCount(@PathVariable Long id){
        return articleService.updateViewCount(id);
    }

}
