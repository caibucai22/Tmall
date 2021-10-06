package com.caibucai.utils;

/**
 * @author Csy
 * @Classname Page
 * @date 2021/9/9 9:40
 * @Description 用于实现分页
 */
public class Page {

    int start;
    int count;
    int total;
    String param;

    public Page(int start, int count) {
        this.start = start;
        this.count = count;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    /**
     * @return
     */
    public boolean isHasPreviouse() {
        return start != 0;
    }

    public boolean isHasNext() {
        return start != getLast();
    }

    public int getLast() {
        int last;
        // 假设总数是50，是能够被5整除，那么最后一页的开始就是45
        if (0 == total % count) {
            last = total - count;
        } else {
            // 不能被整除时，如47 此时最后一页开始就是45
            // 就是被余数去掉
            last = total - total % count;
        }

        last = last < 0 ? 0 : last;

        return last;
    }

    public int getTotalPage() {
        int totalPage;
        // 如果总数是50 能够被5整除 就有10页
        if (0 == total % count) {
            totalPage = total / count;
        } else {
            // 不能整除就多1页
            totalPage = total / count + 1;
        }
        // 针对 0 特殊情况进行处理
        if (0 == totalPage) {
            totalPage = 1;
        }
        return totalPage;
    }


}
