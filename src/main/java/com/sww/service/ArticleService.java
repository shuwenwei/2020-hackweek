package com.sww.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sww.pojo.Article;
import com.sww.pojo.view.PackedArticle;

import java.util.List;

/**
 * @author sww
 */
public interface ArticleService extends IService<Article> {

    /**
     * 判断文章是否存在
     * @param articleId story id
     * @return true 存在 false 不存在
     */
    public boolean articleExist(Long articleId);

    /**
     * 获取story列表
     * @param page 页数
     * @param articleType 0:swank 1:story
     * @return article list
     */
    public List<PackedArticle> getArticles(int page, int articleType);
}
