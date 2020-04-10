package com.sww.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sww.mapper.ArticleMapper;
import com.sww.pojo.Article;
import com.sww.pojo.view.PackedArticle;
import com.sww.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * @author sww
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    private ArticleMapper articleMapper;

    @Autowired
    public void setArticleMapper(ArticleMapper articleMapper) {
        this.articleMapper = articleMapper;
    }

    @Override
    public boolean articleExist(Long articleId) {
        return null != getOne(new QueryWrapper<Article>().eq("id", articleId));
    }

    @Override
    public List<PackedArticle> getArticles(Page<PackedArticle> page, int articleType) {
        return articleMapper
                .getArticles(page, articleType)
                .getRecords();
    }

    @Override
    public PackedArticle getArticle(Long id) {
        return articleMapper.getArticle(id);
    }

    @Override
    public List<PackedArticle> getArticlesBySet(Set<Object> articleIds) {
        return articleMapper.getArticlesBySet(articleIds);
    }
}