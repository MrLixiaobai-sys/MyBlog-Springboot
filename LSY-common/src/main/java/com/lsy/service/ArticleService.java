package com.lsy.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.lsy.domain.ResponseResult;
import com.lsy.domain.entity.Article;

public interface ArticleService extends IService<Article> {
//    查询出浏览量最多的10条文章
    ResponseResult getMostViewsArticle();

    ResponseResult articleListByCategoryId(Integer categoryId,Integer pageNum,Integer pageSize);

    ResponseResult getArticleDetail(Long id);
}
