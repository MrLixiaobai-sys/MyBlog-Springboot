package com.lsy.Controller;

import com.lsy.annotation.SystempLog;
import com.lsy.domain.ResponseResult;
import com.lsy.domain.entity.Article;
import com.lsy.service.ArticleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/article")
@Api(tags = "博客", description = "博客相关接口")
public class ArticleController {

    @Autowired
    private ArticleService articleService;


    //查询出浏览量最多的10条文章
    @GetMapping("/getMostViewsArticle")
    @SystempLog(businessName = "查询出浏览量最多的10条文章")
    @ApiOperation(value = "热门博客列表", notes = "查询出浏览量最多的10条文章")
    public ResponseResult getMostViewsArticle() {
        return articleService.getMostViewsArticle();
    }

//    根据分类id查询出对于文章(分页查询)
    @GetMapping("/articleList")
    @SystempLog(businessName = "根据分类id查询出对应文章(分页查询)")
    @ApiOperation(value = "对文章进行分类查询", notes = "根据分类id查询出对于文章(分页查询)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "categoryId", value = "分类id", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "pageNum", value = "页码", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "pageSize", value = "每页显示条数", required = true, dataType = "Integer")
    })
    public ResponseResult articleListByCategoryId(Integer categoryId,Integer pageNum,Integer pageSize){
        return articleService.articleListByCategoryId(categoryId,pageNum,pageSize);
    }

//    基于restful风格更加{id}查询出对应文章详情
    @GetMapping("{id}")
    @SystempLog(businessName = "基于restful风格根据{id}查询出对应文章详情")
    @ApiOperation(value = "查询出对应文章详情", notes = "基于restful风格根据{id}查询出对应文章详情")
    @ApiImplicitParam(name = "id", value = "文章id", required = true, dataType = "Long")
    public ResponseResult getArticleDetail(@PathVariable Long id){
        return articleService.getArticleDetail(id);
    }

    @PutMapping("/updateViewCount/{id}")
    @SystempLog(businessName = "更新文章浏览量")
    @ApiOperation(value = "更新文章浏览量", notes = "通过redis缓存实时更新文章浏览量")
    @ApiImplicitParam(name = "id", value = "文章id", required = true, dataType = "Long")
    public ResponseResult updateViewCount(@PathVariable Long id){
        return articleService.updateViewCount(id);
    }

}
