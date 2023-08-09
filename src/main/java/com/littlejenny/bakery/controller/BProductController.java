package com.littlejenny.bakery.controller;

import com.littlejenny.bakery.generator.dto.Category;
import com.littlejenny.bakery.generator.service.BProductPageService;
import com.littlejenny.bakery.generator.service.CategoryService;
import com.littlejenny.bakery.generator.service.ProductService;
import com.littlejenny.bakery.pojo.MyPage;
import com.littlejenny.bakery.pojo.R;
import com.littlejenny.bakery.to.BProductTO;
import com.littlejenny.bakery.utils.ModelUtil;
import com.littlejenny.bakery.vo.BProductVO;
import jakarta.validation.Valid;
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
@RequestMapping("${business-prefix-value}/b-product")
@Validated
public class BProductController {

    @Autowired
    BProductPageService bProductPageService;
    @Autowired
    ProductService productService;
    @Autowired
    CategoryService categoryService;

    @GetMapping({"/", ""})
    public String index(Model model) {
        return indexWithPage(model,1L);
    }

    @GetMapping("/{page}")
    public String indexWithPage(Model model, @PathVariable("page") Long page) {
        MyPage<BProductVO> searchResult = addBProductVOsToModelWithPage(model, page);
        ModelUtil.addPageNumberListAndSelectedPageOrBothNullToModelWithPage(model, searchResult);
        addCategorysToModel(model);
        return "b-product";
    }

    public MyPage<BProductVO> addBProductVOsToModelWithPage(Model model, long page) {
        MyPage<BProductVO> searchResult = bProductPageService.pageProductVO(page);
        model.addAttribute("products", searchResult.getRecords());
        return searchResult;
    }

    public void addCategorysToModel(Model model) {
        List<Category> categorys = categoryService.listOrderBySortOrderAsc();
        model.addAttribute("categorys", categorys);
    }

    @PostMapping("/saveOrUpdate")
    @ResponseBody
    public R saveOrUpdate(@RequestBody @Valid BProductTO productTo, BindingResult bindingResult) {
        bProductPageService.saveOrUpdate(productTo);
        return R.ok;
    }

    @ResponseBody
    @PostMapping("/remove")
    public R removeProduct(@RequestBody Integer productId) {
        return productService.removeById(productId) ? R.ok : R.error;
    }
}
