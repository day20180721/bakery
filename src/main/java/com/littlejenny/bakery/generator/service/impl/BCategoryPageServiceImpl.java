package com.littlejenny.bakery.generator.service.impl;

import com.littlejenny.bakery.generator.dto.Category;
import com.littlejenny.bakery.generator.service.BCategoryPageService;
import com.littlejenny.bakery.generator.service.CategoryService;
import com.littlejenny.bakery.to.BCategoryTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class BCategoryPageServiceImpl implements BCategoryPageService {
    @Autowired
    CategoryService categoryService;
    @Override
    public boolean updateBatchSortOrder(List<BCategoryTO> categoryListTO) {
        return categoryService.updateBatchById(getCategoryListFromTO(categoryListTO));
    }
    private List<Category> getCategoryListFromTO(List<BCategoryTO> categoryList){
        AtomicInteger index = new AtomicInteger();
        return categoryList.stream().map(item ->{
            Category category = new Category();
            category.setId(item.getId());
            category.setSortOrder(index.get());
            index.getAndIncrement();
            return category;
        }).toList();
    }
}
