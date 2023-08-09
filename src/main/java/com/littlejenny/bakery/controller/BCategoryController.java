package com.littlejenny.bakery.controller;

import com.littlejenny.bakery.generator.dto.Category;
import com.littlejenny.bakery.generator.service.BCategoryPageService;
import com.littlejenny.bakery.generator.service.CategoryService;
import com.littlejenny.bakery.pojo.MyPage;
import com.littlejenny.bakery.pojo.R;
import com.littlejenny.bakery.to.BCategoryTO;
import com.littlejenny.bakery.utils.ModelUtil;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@Slf4j
@RequestMapping("${business-prefix-value}/b-category")
@Validated
public class BCategoryController {
    @Autowired
    CategoryService categoryService;
    @GetMapping({"/", ""})
    public String index(Model model) {
        return indexWithPage(model,1L);
    }
    @GetMapping("/{page}")
    public String indexWithPage(Model model, @PathVariable("page") Long page) {
        MyPage<Category> searchResult = addCategoryListToModelWithPage(model, page);
        ModelUtil.addPageNumberListAndSelectedPageOrBothNullToModelWithPage(model, searchResult);
        return "b-category";
    }

    private MyPage<Category> addCategoryListToModelWithPage(Model model, Long page) {
        MyPage<Category> searchResult = categoryService.pageOrderBySortOrderAsc(page);
        model.addAttribute("categorys", searchResult.getRecords());
        return searchResult;
    }

    @ResponseBody
    @PostMapping("/saveOrUpdate")
    public R updateCategory(@RequestBody @Valid Category category, BindingResult bindingResult) {
        return categoryService.saveWithLargestOrderOrUpdate(category) ? R.ok : R.error;
    }

    @Autowired
    BCategoryPageService bCategoryPageService;

    @ResponseBody
    @PostMapping("/updateBatchSortOrder")
    public R updateBatchSortOrder(@RequestBody @Valid @NotEmpty(message = "種類列表不可為空") List<@Valid BCategoryTO> categoryList, BindingResult bindingResult) {
        return bCategoryPageService.updateBatchSortOrder(categoryList) ? R.ok : R.error;
    }

    @ResponseBody
    @PostMapping("/remove")
    public R removeCategory(@RequestBody Integer categoryId) {
        return categoryService.removeById(categoryId) ? R.ok : R.error;
    }
}
