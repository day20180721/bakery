package com.littlejenny.bakery.generator.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.littlejenny.bakery.generator.dto.Category;
import com.littlejenny.bakery.generator.service.CategoryService;
import com.littlejenny.bakery.generator.mapper.CategoryMapper;
import com.littlejenny.bakery.pojo.MyPage;
import com.littlejenny.bakery.utils.DateUtil;
import com.littlejenny.bakery.utils.PageUtil;
import org.springframework.cache.annotation.*;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.text.ParseException;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category>
        implements CategoryService {
    @Caching(evict = {
            @CacheEvict(value = "categorys", allEntries = true),
            @CacheEvict(value = "category", key = "#category.id")
    })
    @Override
    public boolean saveWithLargestOrderOrUpdate(Category category) {
        if (category.getId() != null) return updateById(category);
        else return getBaseMapper().insertWithLargestOrder(category) == 1;
    }

    @Cacheable(value = "categorys", key = "getMethodName()")
    @Override
    public List<Category> listOrderBySortOrderAsc() {
        QueryWrapper<Category> wrapper = new QueryWrapper<>();
        wrapper = wrapper.orderByAsc("sort_order");
        return list(wrapper);
    }

    @Cacheable(value = "categorys", key = "'page-'+#page")
    @Override
    public MyPage<Category> pageOrderBySortOrderAsc(Long page) {
        return getBaseMapper().listOrderBySortOrderAscWithPage(PageUtil.of(page));
    }

    @Override
    public List<Category> listByDate(Date date)  {
        return getBaseMapper().listByDateAndSortOrderAsc(date);
    }

    @Caching(evict = {
            @CacheEvict(value = "categorys", allEntries = true),
            @CacheEvict(value = "category", key = "#p0")
    })
    @Override
    public boolean removeById(Serializable id) {
        return super.removeById(id);
    }
    @Cacheable(value = "category", key = "#p0")
    @Override
    public Category getById(Serializable id) {
        return super.getById(id);
    }

    @CacheEvict(value = "categorys", allEntries = true)
    @Override
    public boolean updateBatchById(Collection<Category> entityList) {
        return super.updateBatchById(entityList);
    }
}




