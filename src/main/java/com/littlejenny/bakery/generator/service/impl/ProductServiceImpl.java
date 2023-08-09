package com.littlejenny.bakery.generator.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.littlejenny.bakery.generator.dto.Product;
import com.littlejenny.bakery.generator.service.ProductService;
import com.littlejenny.bakery.generator.mapper.ProductMapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;

/**
 * @author user
 * @description 针对表【product】的数据库操作Service实现
 * @createDate 2023-07-11 16:57:42
 */
@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product>
        implements ProductService {
    @Cacheable(value = "products", key = "#methodName+'-'+#idList")
    @Override
    public List<Product> listAndExcludeById(List<Integer> idList) {
        return getBaseMapper().listAndExcludeById(idList);
    }

    @Caching( evict = {
            @CacheEvict(value = "products", allEntries = true),
            @CacheEvict(value = "product", key = "#entity.id")
    })
    @Override
    public boolean saveOrUpdate(Product entity) {
        return super.saveOrUpdate(entity);
    }

    @Caching(evict = {
            @CacheEvict(value = "products", allEntries = true),
            @CacheEvict(value = "product", key = "#id")
    })
    @Override
    public boolean removeById(Serializable id) {
        return super.removeById(id);
    }

    @Cacheable(value = "product", key = "#id")
    @Override
    public Product getById(Serializable id) {
        return super.getById(id);
    }

    @Cacheable(value = "products", key = "'page'+'-'+#page.current")
    @Override
    public <E extends IPage<Product>> E page(E page) {
        return super.page(page);
    }
}




