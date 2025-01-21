package com.lsy.runner;

import com.lsy.constants.ArticleStatus;
import com.lsy.domain.entity.Article;
import com.lsy.mapper.ArticleMapper;
import com.lsy.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class ViewCountRunner implements CommandLineRunner {

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private RedisCache redisCache;

    @Override
    public void run(String... args) throws Exception {
//        1.从数据库查询出所有博客信息
        List<Article> articles = articleMapper.selectList(null);
//        2.将查询得博客信息封装为redis的数据(key:map)
        Map<String, Integer> ViewCountMap = articles.stream()
                .collect(Collectors.toMap(article1 -> article1.getId().toString(),
                        article -> article.getViewCount().intValue()));
//        3.将数据保存到redis中(文章id对应一个浏览量)
        redisCache.setCacheMap(ArticleStatus.REDIS_ARTICLE_VIEWCOUNT, ViewCountMap);

    }
}
