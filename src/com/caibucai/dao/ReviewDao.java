package com.caibucai.dao;

import com.caibucai.bean.Review;

import java.util.List;

public interface ReviewDao {

    int getTotal();

    int getTotal(int pid);

    void add(Review bean);

    void update(Review bean);

    void delete(int id);

    Review get(int id);

    List<Review> list(int pid);

    int getCount(int pid);

    List<Review> list(int pid,int start,int count);

    boolean isExist(String content,int pid);
}
