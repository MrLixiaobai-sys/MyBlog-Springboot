package com.lsy.Controller;

import com.lsy.domain.ResponseResult;
import com.lsy.domain.dto.PostArticleDTO;
import com.lsy.domain.entity.Article;
import com.lsy.mapper.ArticleMapper;
import com.lsy.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/content/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private ArticleMapper articleMapper;

//    新增博文
    @PostMapping()
    public ResponseResult addArticle(@RequestBody PostArticleDTO postArticleDTO){

        return articleService.addArticle(postArticleDTO);
    }

    //分页查询文章功能，根据标题和摘要**模糊查询*
    @GetMapping("/list")
    public ResponseResult articleList(Integer pageNum, Integer pageSize, String title, String summary){

        return articleService.articleList(pageNum,pageSize,title,summary);
    }

    //根据id查询博文
    @GetMapping("/{id}")
    public ResponseResult getArticleDetail(@PathVariable Long id){

        Article article = articleMapper.selectById(id);
        return ResponseResult.okResult(article);
    }

    //更新博文
    @PutMapping()
    public ResponseResult updateArticle(@RequestBody Article article){

        articleMapper.update(article,null);
        return ResponseResult.okResult();
    }

    //删除博文
    @DeleteMapping("/{id}")
    public ResponseResult deleteArticle(@PathVariable Long id){
        articleService.removeById(id);
        return ResponseResult.okResult();
    }


}
