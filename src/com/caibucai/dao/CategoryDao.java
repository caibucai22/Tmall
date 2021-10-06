package com.caibucai.dao;

import com.caibucai.bean.Category;

import java.util.List;

public interface CategoryDao {

    public int getTotal();

    /**
     * �������
     * @param bean
     */
    public void add(Category bean);

    /**
     * ��������
     */
    public void update(Category bean);

    /**
     * ɾ������
     */
    public void delete(int id);

    /**
     * ��ȡ����
     */
    public Category get(int id);

    public List<Category> list();

    public List<Category> list(int start,int count);
}
