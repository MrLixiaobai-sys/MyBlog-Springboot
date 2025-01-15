package com.lsy.Controller;

import com.lsy.domain.ResponseResult;
import com.lsy.domain.entity.Article;
import com.lsy.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;


    //        查询出article表中的所有数据
    @GetMapping("/list")
    private List<Article> articleList() {
        return articleService.list();
    }

    //        查询出浏览量最多的10条文章
    @GetMapping("/getMostViewsArticle")
    public ResponseResult getMostViewsArticle() {
        return articleService.getMostViewsArticle();
    }

//    根据分类id查询出对于文章(分页查询)
    @GetMapping("/articleList")
    public ResponseResult articleListByCategoryId(Integer categoryId,Integer pageNum,Integer pageSize){
        return articleService.articleListByCategoryId(categoryId,pageNum,pageSize);
    }


}
