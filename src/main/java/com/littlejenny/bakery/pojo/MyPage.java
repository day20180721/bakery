package com.littlejenny.bakery.pojo;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.littlejenny.bakery.utils.NumberUtil;
import lombok.ToString;

import java.io.Serial;
@ToString
public class MyPage<T> extends Page<T> {
    @Serial
    private static final long serialVersionUID = -7149462916138918365L;
    private int indexSize;
    private int maxCountInHalf;

    private int BeginSlotCount;
    private int endSlotCount;


    public static <T> MyPage<T> of(long currentPage, int size, int indexSize) {
        MyPage<T> myPage = new MyPage<>(currentPage, size, indexSize);
        myPage.setTotal(0);
        myPage.setSearchCount(true);
        return myPage;
    }

    private MyPage(long currentPage, int size, int indexSize) {
        super(currentPage,size);
        if (currentPage < 1) throw new RuntimeException("當前頁數不能小於 1");
        if (size < 1) throw new RuntimeException("顯示數量不能小於 1");
        if (indexSize < 3) throw new RuntimeException("顯示頁碼數量不能小於 3");
        this.indexSize = indexSize;
        this.maxCountInHalf = indexSize / 2;
        setBeginAndEndIndexSlotCount();
    }

    private void setBeginAndEndIndexSlotCount() {
        if (NumberUtil.isGreaterThenZeroAndEven(indexSize)) {
            this.BeginSlotCount = maxCountInHalf - 1;
        } else {
            this.BeginSlotCount = maxCountInHalf;
        }
        this.endSlotCount = maxCountInHalf;
    }

    public int[] getPageNumberArrayOrNull() {
        if (total == 0) {
            return null;
        }
        setExtraBeginSlotCount();
        setExtraEndSlotCount();
        setBeginIndex();
        setEndIndex();
        return getPageNumberArray();
    }
    private long extraBeginSlotCount;

    public void setExtraBeginSlotCount() {
        long expectedEndSlotIndex = current + endSlotCount;
        if (getMaxIndex() < expectedEndSlotIndex) {
            extraBeginSlotCount = expectedEndSlotIndex - getMaxIndex();
        }
    }

    private long extraEndSlotCount;

    public void setExtraEndSlotCount() {
        long expectedBeginSlotIndex = current - BeginSlotCount;
        if (expectedBeginSlotIndex < 1) {
            extraEndSlotCount = 1 - expectedBeginSlotIndex;
        }
    }

    private int beginIndex;

    private void setBeginIndex() {
        this.beginIndex = getBeginHalfBeginIndexAtLeastOne();
    }

    private int getBeginHalfBeginIndexAtLeastOne() {

        long expectedBeginIndex = current - BeginSlotCount;
        return Math.toIntExact(Math.max(1, expectedBeginIndex - extraBeginSlotCount));
    }

    private int endIndex;

    private void setEndIndex() {
        this.endIndex = getEndHalfEndIndex();
    }

    private int getEndHalfEndIndex() {
        long maxIndex = getMaxIndex();
        long expectedEndIndex = current + endSlotCount;
        return Math.toIntExact(Math.min(maxIndex, expectedEndIndex + extraEndSlotCount));
    }

    private long getMaxIndex() {
        if (total % size > 0) {
            return total / size + 1;
        } else {
            return total / size;
        }
    }

    private int[] getPageNumberArray() {
        int capacity = endIndex - beginIndex + 1;
        int[] result = new int[capacity];
        for (int index = beginIndex, i = 0; index <= endIndex; index++, i++) {
            result[i] = index;
        }
        return result;
    }
}
