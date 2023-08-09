package com.littlejenny.bakery.controller;

import com.littlejenny.bakery.exception.OutOfOpeningTimeException;
import com.littlejenny.bakery.exception.TimeFormatException;
import com.littlejenny.bakery.generator.dto.Order;
import com.littlejenny.bakery.generator.service.OrderPageService;
import com.littlejenny.bakery.generator.service.OrderService;
import com.littlejenny.bakery.pojo.ErrorField;
import com.littlejenny.bakery.pojo.ErrorMap;
import com.littlejenny.bakery.pojo.MyPage;
import com.littlejenny.bakery.pojo.R;
import com.littlejenny.bakery.to.OrderTO;
import com.littlejenny.bakery.utils.DateUtil;
import com.littlejenny.bakery.utils.ModelUtil;
import com.littlejenny.bakery.utils.RequestUtil;
import com.littlejenny.bakery.vo.OrderVO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import me.zhyd.oauth.model.AuthUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.ArrayList;

@Controller
@RequestMapping("${customer-prefix-value}/order")
@Validated
public class OrderController {
    @Autowired
    OrderPageService orderPageService;

    @GetMapping({"/", ""})
    public String index(HttpServletRequest request,Model model) {
        return indexWithPage(request,model,1L);
    }
    @GetMapping("/{page}")
    public String indexWithPage(HttpServletRequest request,Model model,@PathVariable("page")Long page) {
        MyPage<OrderVO> searchResult = addOrderListToModelWithPage(request, model, page);
        ModelUtil.addPageNumberListAndSelectedPageOrBothNullToModelWithPage(model,searchResult);
        return "order";
    }

    public MyPage<OrderVO> addOrderListToModelWithPage(HttpServletRequest request, Model model, Long page) {
        MyPage<OrderVO> searchResult = orderPageService.pageByCustomerId(page,RequestUtil.getUserOrNull(request).getUuid());
        model.addAttribute("orders", searchResult.getRecords());
        return searchResult;
    }


    @ResponseBody
    @PostMapping("/{date}")
    public R save(@PathVariable("date") String date, @RequestBody @Valid OrderTO orderTo, BindingResult bindingResult,HttpServletRequest request) throws ParseException {
        try {
            orderPageService.saveByUserIdAndDateAndOrderTO(RequestUtil.getUserOrNull(request).getUuid(), DateUtil.getDateFromStringInGMT8(date), orderTo);
            return R.ok;
        }catch (OutOfOpeningTimeException e){
            return R.error(ErrorMap.getErrorMap("opening-time",e.getMessage()));
        }catch (TimeFormatException e){
            return R.error(ErrorMap.getErrorMap("opening-time",e.getMessage()));
        }
    }
}
