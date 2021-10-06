package com.caibucai.dao;

import com.caibucai.bean.Property;

import java.util.List;

public interface PropertyDao {

    int getTotal(int cid);

    void add(Property bean);

    void update(Property bean);

    void delete(int id);

    Property get(String name,int cid);

    Property get(int id);

    List<Property> list(int cid);

    List<Property> list(int cid,int start,int count);
}
