package com.sww.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sww.mapper.ArticleMapper;
import com.sww.pojo.Article;
import com.sww.pojo.packed.PackedArticle;
import com.sww.service.ArticleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author sww
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    private ArticleMapper articleMapper;

    @Resource
    public void setStoryMapper(ArticleMapper articleMapper) {
        this.articleMapper = articleMapper;
    }

    @Override
    public boolean articleExist(Long articleId) {
        return null != getOne(new QueryWrapper<Article>().eq("id", articleId));
    }

    @Override
    public List<PackedArticle> getArticles(int page) {
        return articleMapper
                .getArticles()
                .setPages(page)
                .getRecords();
    }
}