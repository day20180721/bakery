package com.littlejenny.bakery.controller;

import com.littlejenny.bakery.generator.dto.Category;
import com.littlejenny.bakery.generator.service.CategoryService;
import com.littlejenny.bakery.generator.service.ShopPageService;
import com.littlejenny.bakery.pojo.MyPage;
import com.littlejenny.bakery.pojo.R;
import com.littlejenny.bakery.utils.DateUtil;
import com.littlejenny.bakery.utils.ModelUtil;
import com.littlejenny.bakery.utils.RequestUtil;
import com.littlejenny.bakery.vo.ShopProductVO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

@RequestMapping("/shop")
@Controller
public class ShopController {

    @Autowired
    ShopPageService shopPageService;
    @Autowired
    BShopController bShopController;
    @Autowired
    CategoryService categoryService;

    @GetMapping({"", "/"})
    public String index(Model model, HttpServletRequest req) throws ParseException {
        return indexWithCategoryIdAndPage(model,req,-1,1L);
    }

    @GetMapping({"/{categoryId}/{page}"})
    public String indexWithCategoryIdAndPage(Model model, HttpServletRequest req, @PathVariable("categoryId") Integer categoryId, @PathVariable("page") Long page) throws ParseException {
        Date selectedDate = RequestUtil.getSelectedDate(req);
        MyPage<ShopProductVO> searchResult = addShopProductVOToModelWithCategoryIdAndPage(model, selectedDate, categoryId, page);
        ModelUtil.addPageNumberListAndSelectedPageOrBothNullToModelWithPage(model,searchResult);
        addSelectedCategoryIdToModel(model, categoryId);
        addCategoryListToModel(model,selectedDate);
        bShopController.addSaleDaysForCalenderToModel(model, selectedDate);
        return "shop";
    }

    public void addSelectedCategoryIdToModel(Model model, Integer categoryId) {
        model.addAttribute("selectedCategoryId", categoryId);
    }

    public MyPage<ShopProductVO> addShopProductVOToModelWithCategoryIdAndPage(Model model, Date selectedDate, Integer categoryId, Long page) throws ParseException {
        MyPage<ShopProductVO> searchResult = null;
        if (noSelectedCategory(categoryId)) {
            searchResult = shopPageService.pageShopProductByDate(selectedDate, page);
        } else {
            searchResult = shopPageService.pageShopProductByDateAndCategoryId(selectedDate, categoryId, page);
        }
        model.addAttribute("productVOs", searchResult.getRecords());
        return searchResult;
    }
    private boolean noSelectedCategory(Integer categoryId){
        return categoryId == -1;
    }
    public void addCategoryListToModel(Model model,Date date) throws ParseException {
        List<Category> categoryList = categoryService.listByDate(date);
        model.addAttribute("categoryList", categoryList);
    }
    @ResponseBody
    @GetMapping("/highLight")
    public R listHighLightDate(HttpServletRequest req) {
        return bShopController.listHighLightDate(req);
    }
}
