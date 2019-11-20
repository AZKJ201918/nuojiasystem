package com.shopping.service;

import com.github.pagehelper.PageInfo;
import com.shopping.commons.exception.SuperMarketException;
import com.shopping.entity.Article;

public interface ArticleService {
    PageInfo<Article> findArticle(Article article, Integer page, Integer limit) throws SuperMarketException;

    void modifyArticle(Article article);

    void removeArticle(Integer id);

    void addArticle(Article article);
}
