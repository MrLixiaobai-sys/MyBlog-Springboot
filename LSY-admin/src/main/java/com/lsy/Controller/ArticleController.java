package com.lsy.Controller;

import com.lsy.domain.ResponseResult;
import com.lsy.domain.dto.PostArticleDTO;
import com.lsy.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/content/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @PostMapping()
    public ResponseResult addArticle(@RequestBody PostArticleDTO postArticleDTO){

        return articleService.addArticle(postArticleDTO);
    }
}
