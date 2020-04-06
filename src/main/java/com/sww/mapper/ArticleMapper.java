package com.sww.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sww.pojo.Article;
import com.sww.pojo.packed.PackedArticle;
import org.apache.ibatis.annotations.Mapper;


/**
 * @author sww
 */
@Mapper
public interface ArticleMapper extends BaseMapper<Article> {
    /**
     * 获取story列表
     * @return story list
     */
    public IPage<PackedArticle> getArticles();
}
