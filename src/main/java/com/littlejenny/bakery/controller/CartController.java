package com.littlejenny.bakery.controller;

import com.littlejenny.bakery.generator.service.*;
import com.littlejenny.bakery.pojo.ErrorMap;
import com.littlejenny.bakery.to.CartItemRemoveTO;
import com.littlejenny.bakery.utils.DateUtil;
import com.littlejenny.bakery.vo.CartItemVO;
import com.littlejenny.bakery.pojo.R;
import com.littlejenny.bakery.to.CartItemTO;
import com.littlejenny.bakery.utils.RequestUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

@RequestMapping("${customer-prefix-value}/cart")
@Controller
@Validated
public class CartController {
    @Autowired
    CartService cartService;
    @Autowired
    CartPageService cartPageService;
    @Autowired
    BakeryConfigController bakeryConfigController;

    @GetMapping({"", "/"})
    public String index(Model model, HttpServletRequest req) throws ParseException {
        Date date = RequestUtil.getSelectedDate(req);
        addCartItemVOsToModel(req, model, date);
        addReservationDaysForCalenderToModel(req, model, date);
        bakeryConfigController.addOpeningDurationToModel(model);
        return "cart";
    }

    private void addCartItemVOsToModel(HttpServletRequest request, Model model, Date date) throws ParseException {
        List<CartItemVO> items = cartPageService.listCartItemVOByUserIdAndDate(RequestUtil.getUserOrNull(request).getUuid(), date);
        model.addAttribute("items", items);
    }

    public void addReservationDaysForCalenderToModel(HttpServletRequest request, Model model, Date date) {
        List<String> highlightDates = cartService.listReservationDayListByYearAndMonthAndUserId(DateUtil.getYearFromDate(date), DateUtil.getMonthFromDate(date), RequestUtil.getUserOrNull(request).getUuid());
        model.addAttribute("highlightDates", highlightDates);
    }

    @Autowired
    CartItemPageService cartItemPageService;

    @ResponseBody
    @PostMapping("/{date}")
    public R save(@PathVariable("date") String date, @RequestBody @Valid CartItemTO cartItemTO, BindingResult bindingResult, HttpServletRequest request) throws ParseException {
        return cartItemPageService.save(RequestUtil.getUserOrNull(request).getUuid(), DateUtil.getDateFromStringInGMT8(date), cartItemTO) ? R.ok : R.error;
    }

    @ResponseBody
    @PutMapping("/{date}")
    public R update(@PathVariable("date") String date, @RequestBody @Valid CartItemTO cartItemTO, BindingResult bindingResult, HttpServletRequest request) throws ParseException {
        return cartItemPageService.update(RequestUtil.getUserOrNull(request).getUuid(), DateUtil.getDateFromStringInGMT8(date), cartItemTO) ? R.ok : R.error;

    }

    @Autowired
    CartItemService cartItemService;

    @ResponseBody
    @DeleteMapping("/")
    public R delete(@RequestBody @Valid CartItemRemoveTO cartItemDeleteTO, BindingResult bindingResult) {

        return cartItemPageService.remove(cartItemDeleteTO) ? R.ok : R.error(ErrorMap.getErrorMap(bindingResult));
    }

    @ResponseBody
    @GetMapping("/highLight")
    public R getHighLightDates(HttpServletRequest req) {
        String date = RequestUtil.getSelectedStringDate(req);
        List<String> highlightDates =
                cartService.listReservationDayListByYearAndMonthAndUserId(date, RequestUtil.getUserOrNull(req).getUuid());
        return R.ok(highlightDates);
    }
}
