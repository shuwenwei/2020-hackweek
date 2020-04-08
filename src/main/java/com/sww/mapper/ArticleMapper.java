package com.sww.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sww.pojo.Article;
import com.sww.pojo.view.PackedArticle;
import org.apache.ibatis.annotations.Mapper;


/**
 * @author sww
 */
@Mapper
public interface ArticleMapper extends BaseMapper<Article> {
    /**
     * 获取story列表
     * @param page page
     * @param articleType 0:swank, 1:story
     * @return story list
     */
    public IPage<PackedArticle> getArticles(Page<PackedArticle> page, int articleType);

    /**
     * 获取单篇文章
     * @param id 文章id
     * @return 文章对象
     */
    public PackedArticle getArticle(Long id);
}
