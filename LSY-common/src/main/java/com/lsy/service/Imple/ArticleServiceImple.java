package com.lsy.service.Imple;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lsy.constants.ArticleStatus;
import com.lsy.domain.ResponseResult;
import com.lsy.domain.Vo.MostViewArticleVo;
import com.lsy.domain.entity.Article;
import com.lsy.mapper.ArticleMapper;
import com.lsy.service.ArticleService;
import com.lsy.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleServiceImple extends ServiceImpl<ArticleMapper,Article> implements ArticleService {

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
        Page<Article> page = new Page<>(1,10);
//        分页查询并将数据封装到page对象中
        page(page,wrapper);
//        获取10条查询结果
        List<Article> records = page.getRecords();

        List<MostViewArticleVo> vs = BeanCopyUtils.copyBeanList(records, MostViewArticleVo.class);
        return ResponseResult.okResult(vs);
    }
}
