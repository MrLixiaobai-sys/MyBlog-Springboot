package com.lsy.job;

import com.lsy.constants.ArticleStatus;
import com.lsy.domain.entity.Article;
import com.lsy.service.ArticleService;
import com.lsy.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Component
public class ViewCountIncreaseJob {
    @Autowired
    private RedisCache redisCache;
    @Autowired
    private ArticleService articleService;

    //定时任务:每隔一段时间(5s)定时刷新一下数据库(从redis)
    @Scheduled(cron = "0/30 * * * * ?")
    public void increaseViewCount(){
//        1.获取redis的map<id,viewCount>
        Map<String, Integer> viewCountMap = redisCache.getCacheMap(ArticleStatus.REDIS_ARTICLE_VIEWCOUNT);
//            转换为List<Article>集合
        List<Article> articles = viewCountMap.entrySet()
                .stream()
                .map(entry -> new Article(Long.valueOf(entry.getKey()), Long.valueOf(entry.getValue())))
                .collect(Collectors.toList());
//        2.将数据更新到数据库(批量更新)
        articleService.updateBatchById(articles);

    }

}
