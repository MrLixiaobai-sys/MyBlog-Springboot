package com.lsy.constants;

public class ArticleStatus {
    //    文章为草稿状态常量
    public static final int ARTICLE_STATUS_DRAFT = 1;

    //    文章为已发布状态常量
    public static final int ARTICLE_STATUS_NORMAL = 0;
    public static final int INCREATE_ARTICLE_VIEW_COUNT = 1;
    public static final String REDIS_ARTICLE_VIEWCOUNT = "article:viewCountMap";
}
