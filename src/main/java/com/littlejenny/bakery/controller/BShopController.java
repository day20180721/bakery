package com.littlejenny.bakery.controller;

import com.littlejenny.bakery.generator.dto.Product;
import com.littlejenny.bakery.generator.service.ProductOnSaleService;
import com.littlejenny.bakery.pojo.R;
import com.littlejenny.bakery.generator.service.BShopPageService;
import com.littlejenny.bakery.to.BShopProductTO;
import com.littlejenny.bakery.utils.DateUtil;
import com.littlejenny.bakery.utils.RequestUtil;
import com.littlejenny.bakery.vo.BShopProductVO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.*;

@Controller
@Slf4j
@RequestMapping("${business-prefix-value}/b-shop")
@Validated
public class BShopController {

    @Autowired
    BShopPageService bShopPageService;
    @Autowired
    ProductOnSaleService productOnSaleService;

    @GetMapping({"/", ""})
    public String index(Model model, HttpServletRequest req) throws ParseException {
        Date date = DateUtil.getDateFromStringInGMT8(RequestUtil.getSelectedStringDate(req));
        addOnSaleProductsToModel(model, date);
        addNotOnSaleProductsToModel(model, date);
        addSaleDaysForCalenderToModel(model, date);
        return "b-shop";
    }

    public void addOnSaleProductsToModel(Model model, Date date) throws ParseException {
        List<BShopProductVO> onsales = bShopPageService.listOnSaleProductByDate(date);
        model.addAttribute("onsales", onsales);
    }

    public void addNotOnSaleProductsToModel(Model model, Date date) throws ParseException {
        List<Product> notOnSales = bShopPageService.listNotOnSaleProductByDate(date);
        model.addAttribute("notOnSales", notOnSales);
    }

    public void addSaleDaysForCalenderToModel(Model model, Date date) {
        List<String> highlightDates = productOnSaleService.listOnSaleDayListInSpecifiedYearAndMonth(DateUtil.getYearFromDate(date), DateUtil.getMonthFromDate(date));
        model.addAttribute("highlightDates", highlightDates);

    }

    @ResponseBody
    @PostMapping("/{date}")
    public R saveOrUpdateBatch(@RequestBody List<@Valid BShopProductTO> productTos, @PathVariable("date") String date, BindingResult bindingResult) throws ParseException {
        return bShopPageService.saveOrUpdateBatch(productTos, DateUtil.getDateFromStringInGMT8(date)) ? R.ok : R.error;
    }

    @ResponseBody
    @DeleteMapping("/{date}")
    public R removeBatch(@RequestBody List<Integer> idList, @PathVariable("date") String date) throws ParseException {
        return productOnSaleService.removeBatchByIdsAndDate(idList, DateUtil.getDateFromStringInGMT8(date)) ? R.ok : R.error;
    }

    @ResponseBody
    @GetMapping("/highLight")
    public R listHighLightDate(HttpServletRequest req) {
        String yearMonth = RequestUtil.getSelectedStringDate(req);
        List<String> highlightDates = productOnSaleService.listOnSaleDayListInSpecifiedYearAndMonth(yearMonth);
        return R.ok(highlightDates);
    }
}
