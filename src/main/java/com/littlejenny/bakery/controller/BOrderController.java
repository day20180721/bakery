package com.littlejenny.bakery.controller;

import com.littlejenny.bakery.enums.OrderStatus;
import com.littlejenny.bakery.generator.service.BOrderPageService;
import com.littlejenny.bakery.pojo.MyPage;
import com.littlejenny.bakery.pojo.R;
import com.littlejenny.bakery.to.BOrderTO;
import com.littlejenny.bakery.utils.ModelUtil;
import com.littlejenny.bakery.vo.BOrderVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("${business-prefix-value}/b-order")
@Validated
public class BOrderController {
    @Autowired
    BOrderPageService bOrderPageService;

    @GetMapping({"", "/"})
    public String index(Model model) {
        return indexWithPage(model,1L);
    }

    @GetMapping("/{page}")
    public String indexWithPage(Model model, @PathVariable("page") Long page) {
        MyPage<BOrderVO> searchResult = addOrderListToModelWithPage(model, page);
        ModelUtil.addPageNumberListAndSelectedPageOrBothNullToModelWithPage(model,searchResult);
        addOrderStatusListToModel(model);
        return "b-order";
    }

    private MyPage<BOrderVO> addOrderListToModelWithPage(Model model, Long page) {
        MyPage<BOrderVO> searchResult = bOrderPageService.pageBOrderVO(page);
        model.addAttribute("orders", searchResult.getRecords());
        return searchResult;
    }

    private void addOrderStatusListToModel(Model model) {
        List<OrderStatus> orderStatusList = OrderStatus.getAll();
        model.addAttribute("orderStatusList", orderStatusList);
    }

    @ResponseBody
    @PostMapping("/updateOrderStatus")
    public R updateOrderStatus(@RequestBody BOrderTO to) {
        return bOrderPageService.updateOrderStatus(to) ? R.ok : R.error;
    }
}
