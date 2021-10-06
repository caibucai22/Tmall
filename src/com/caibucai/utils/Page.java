package com.caibucai.utils;

/**
 * @author Csy
 * @Classname Page
 * @date 2021/9/9 9:40
 * @Description ����ʵ�ַ�ҳ
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
        // ����������50�����ܹ���5��������ô���һҳ�Ŀ�ʼ����45
        if (0 == total % count) {
            last = total - count;
        } else {
            // ���ܱ�����ʱ����47 ��ʱ���һҳ��ʼ����45
            // ���Ǳ�����ȥ��
            last = total - total % count;
        }

        last = last < 0 ? 0 : last;

        return last;
    }

    public int getTotalPage() {
        int totalPage;
        // ���������50 �ܹ���5���� ����10ҳ
        if (0 == total % count) {
            totalPage = total / count;
        } else {
            // ���������Ͷ�1ҳ
            totalPage = total / count + 1;
        }
        // ��� 0 ����������д���
        if (0 == totalPage) {
            totalPage = 1;
        }
        return totalPage;
    }


}
