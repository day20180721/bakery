package com.littlejenny.bakery.generator.service;

import com.littlejenny.bakery.to.BCategoryTO;

import java.util.List;

public interface BCategoryPageService {
    boolean updateBatchSortOrder(List<BCategoryTO> categoryList);
}
