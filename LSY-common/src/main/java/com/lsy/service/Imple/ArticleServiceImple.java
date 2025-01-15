package com.lsy.service.Imple;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lsy.constants.ArticleStatus;
import com.lsy.domain.ResponseResult;
import com.lsy.domain.Vo.ArticleListVo;
import com.lsy.domain.Vo.MostViewArticleVo;
import com.lsy.domain.Vo.PageVo;
import com.lsy.domain.entity.Article;
import com.lsy.domain.entity.Category;
import com.lsy.mapper.ArticleMapper;
import com.lsy.service.ArticleService;
import com.lsy.service.CategoryService;
import com.lsy.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class ArticleServiceImple extends ServiceImpl<ArticleMapper, Article> implements ArticleService {


    @Autowired
    private CategoryService categoryService;
    //    获取最多浏览量的10条文章
    @Override
    public ResponseResult getMostViewsArticle() {

//        通过mybatis-plus的LambdaQueryWrapper查询出浏览量最多的10条文章
        LambdaQueryWrapper<Article> wrapper = new QueryWrapper<Article>().lambda();
//        查询出正式发布的文章(状态0为正式发布,1为草稿中)
//        1.增加查询条件:状态为0(发布状态)
        wrapper.eq(Article::getStatus, ArticleStatus.ARTICLE_STATUS_NORMAL);
//        2.按照浏览量降序
        wrapper.orderByDesc(Article::getViewCount);
//        3.分页查询,并获取10条数据
//        定义分页参数
        Page<Article> page = new Page<>(1, 10);
//        分页查询并将数据封装到page对象中
        page(page, wrapper);
//        获取10条查询结果
        List<Article> records = page.getRecords();

        List<MostViewArticleVo> vs = BeanCopyUtils.copyBeanList(records, MostViewArticleVo.class);
        return ResponseResult.okResult(vs);
    }

    //    根据分类id查询出对于文章(分页查询)(不传入id则展示所有)
    @Override
    public ResponseResult articleListByCategoryId(Integer categoryId, Integer pageNum, Integer pageSize) {

//        1.判断是否传入分类id(不传入默认为-1)
        LambdaQueryWrapper<Article> ArticleWrapper = new LambdaQueryWrapper<>();

//        如果categoryId不为空且大于0时才进行增加查询条件(Article::getCategoryId, categoryId),即查询条件为:where categoryId=传入分类id
        ArticleWrapper.eq(Objects.nonNull(categoryId) && categoryId > 0, Article::getCategoryId, categoryId);
//        2.状态正式发布(0)
        ArticleWrapper.eq(Article::getStatus,ArticleStatus.ARTICLE_STATUS_NORMAL);
//        3.对isTop进行降序(1为置顶)
        ArticleWrapper.orderByDesc(Article::getIsTop);
//        4.分页查询
        Page<Article> page =new Page<>(pageNum,pageSize);
//        将查询条件和分页条件封装到page中
        page(page,ArticleWrapper);

        List<Article> articles = page.getRecords();
//        得到的article实体类里面没有categoryName(前端要求接受的属性)

        //文章表里面的CategoryId在分类表必须要有
            for (Article article : articles){
    //        1.得到article里面的分类id
                Long categoryId1 = article.getCategoryId();
    //        2.通过分类id查询到分类表里面的name
                Category category = categoryService.getById(categoryId1);
    //        3.将得到的name赋值给article里面的categoryName即可
                if (category != null) {
                    // 3. 如果存在分类，设置 categoryName
                    article.setCategoryName(category.getName());
                } else {
                    // 如果不存在分类，设置一个默认值或不设置
                    article.setCategoryName("未分类");
                }
            }


//        5.封装到Vo中
        List<ArticleListVo> articleListVos = BeanCopyUtils.copyBeanList(articles, ArticleListVo.class);
//        6.返回响应结果(显示总条数,分页)
        PageVo pageVo =new PageVo(articleListVos,page.getTotal());

        return ResponseResult.okResult(pageVo);
    }
}
