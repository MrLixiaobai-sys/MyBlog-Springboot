package com.lsy.service.Imple;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lsy.domain.entity.Article;
import com.lsy.mapper.ArticleMapper;
import com.lsy.service.ArticleService;
import org.springframework.stereotype.Service;

@Service
public class ArticleServiceImple extends ServiceImpl<ArticleMapper,Article> implements ArticleService {
}
