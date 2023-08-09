package com.littlejenny.bakery.utils;

import com.littlejenny.bakery.pojo.MyPage;
import org.springframework.ui.Model;

public class ModelUtil {
    public static void addPageNumberListAndSelectedPageOrBothNullToModelWithPage(Model model, MyPage<?> myPage){
        int[] pageNumberArray = myPage.getPageNumberArrayOrNull();
        if(pageNumberArray != null){
            model.addAttribute("pageNumberList",pageNumberArray);
            model.addAttribute("selectedPage",myPage.getCurrent());
        }
    }
}
