package com.caibucai.dao;

import com.caibucai.bean.Product;
import com.caibucai.bean.PropertyValue;

import java.util.List;

/**
 * @author Csy
 * @Classname PropertyValueDao
 * @date 2021/9/9 19:36
 * @Description TODO
 */
public interface PropertyValueDao {

    int getTotal();

    void add(PropertyValue bean);

    void update(PropertyValue bean);

    void delete(int id);

    PropertyValue get(int id);

    PropertyValue get(int ptid,int pid);

    List<PropertyValue> list();

    List<PropertyValue> list(int start,int count);

    /**
     *
     * @param p
     */
    void init(Product p);

    List<PropertyValue> list(int pid);


}
