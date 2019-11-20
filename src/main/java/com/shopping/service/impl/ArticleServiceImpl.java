package com.shopping.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shopping.commons.exception.SuperMarketException;
import com.shopping.entity.Article;
import com.shopping.mapper.ArticleMapper;
import com.shopping.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    private ArticleMapper articleMapper;
    @Override
    public PageInfo<Article> findArticle(Article article, Integer page, Integer limit) throws SuperMarketException {
        PageHelper.startPage(page,limit);
        List<Article> articleList=articleMapper.selectArticle(article);
        if (articleList==null){
            throw new SuperMarketException("没有找到公众号文章");
        }
        PageInfo<Article> pageInfo = new PageInfo<>(articleList);
        return pageInfo;
    }

    @Override
    public void modifyArticle(Article article) {
        articleMapper.updateByPrimaryKey(article);
    }

    @Override
    public void removeArticle(Integer id) {
        articleMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void addArticle(Article article) {
        article.setCreatetime(new Date());
        articleMapper.insertSelective(article);
    }
}
