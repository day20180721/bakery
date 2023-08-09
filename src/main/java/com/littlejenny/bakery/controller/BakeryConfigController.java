package com.littlejenny.bakery.controller;


import com.littlejenny.bakery.generator.service.BakeryConfigService;
import com.littlejenny.bakery.pojo.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Controller
@Slf4j
@RequestMapping("${business-prefix-value}/b-bakery-config")
@Validated
public class BakeryConfigController {
    @Autowired
    private BakeryConfigService bakeryConfigService;

    @GetMapping({"", "/"})
    public String index(Model model) {
        addOpeningDurationToModel(model);
        addReservationDeadLineToModel(model);
        return "b-bakery-config";
    }

    public void addOpeningDurationToModel(Model model) {
        Integer openTime = bakeryConfigService.getOpenTime();
        Integer closeTime = bakeryConfigService.getCloseTime();
        model.addAttribute("openTime", openTime);
        model.addAttribute("closeTime", closeTime);
    }

    public void addReservationDeadLineToModel(Model model) {
        Integer reservationDateLine = bakeryConfigService.getReservationDateLine();
        model.addAttribute("reservationDateLine", reservationDateLine);
    }

    @PutMapping("/open/{time}")
    @ResponseBody
    public R updateOpenTime(@PathVariable("time") Integer time) {
        return bakeryConfigService.updateOpenTime(time) ? R.ok : R.error;
    }

    @PutMapping("/close/{time}")
    @ResponseBody
    public R updateCloseTime(@PathVariable("time") Integer time) {
        return bakeryConfigService.updateCloseTime(time) ? R.ok : R.error;
    }

    @PutMapping("/reservationDeadLine/{deadLine}")
    @ResponseBody
    public R updateReservationDeadLine(@PathVariable("deadLine") Integer deadLine) {
        return bakeryConfigService.updateReservationDateLine(deadLine) ? R.ok : R.error;
    }
}
