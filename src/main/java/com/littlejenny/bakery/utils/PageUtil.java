package com.littlejenny.bakery.utils;

import com.littlejenny.bakery.pojo.MyPage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PageUtil {
    private static Integer pageSize;
    private static Integer pageIndexSize;

    @Value("${mybatis-plus-page-size}")
    public void setPageSize(Integer pageSize) {
        PageUtil.pageSize = pageSize;
    }
    @Value("${mybatis-plus-page-index-size}")
    public void setPageIndexSize(Integer pageIndexSize) {
        PageUtil.pageIndexSize = pageIndexSize;
    }

    public static <T> MyPage<T> of(long current) {
        return MyPage.of(current,pageSize,pageIndexSize);
    }
    public static <T> MyPage<T> replaceRecordListWithSourcePage(MyPage<?> source, List<T> replacement){
        MyPage<T> newPage = MyPage.of(source.getCurrent(), pageSize, pageIndexSize);
        newPage.setRecords(replacement);
        newPage.setTotal(source.getTotal());
        return newPage;
    }
}
