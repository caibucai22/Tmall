package com.caibucai.dao;

import com.caibucai.bean.Category;

import java.util.List;

public interface CategoryDao {

    public int getTotal();

    /**
     * 添加类型
     * @param bean
     */
    public void add(Category bean);

    /**
     * 更新类型
     */
    public void update(Category bean);

    /**
     * 删除类型
     */
    public void delete(int id);

    /**
     * 获取类型
     */
    public Category get(int id);

    public List<Category> list();

    public List<Category> list(int start,int count);
}
