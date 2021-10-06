package com.caibucai.dao;

import com.caibucai.bean.Category;
import com.caibucai.bean.Product;

import java.util.List;

public interface ProductDao {
    /**
     * ��ȡĳ�ֲ�Ʒ����
     */
    public int getTotal(int cid);

    /**
     * �����Ʒ
     * @param bean
     */
    public void add(Product bean);

    /**
     * ������Ʒ
     * @param bean
     */
    public void update(Product bean);

    /**
     *
     * ���ݲ�Ʒidɾ����Ʒ
     */

    void delete(int id);


    public Product get(int id);

    /**
     *
     * @param cid
     * @return
     */
    public List<Product> list(int cid);

    /**
     *
     */
    public List<Product> list(int cid,int start,int count);

    /**
     *
     */
    public List<Product> list();

    /**
     *
     */
    public List<Product> list(int start,int count);

    /**
     *
     */
    public void fill(List<Category> categories);

    public void fill(Category c);

    /**
     *
     */
    public void fillByRow(List<Category> categories);

    /**
     * ���ò�Ʒ��һ��ͼƬ
     */
    public void setFirstProductImage(Product p);

    /**
     *
     */
    public void setSaleAndReviewNumber(Product p);

    /**
     *
     */
    public void setSaleAndReviewNumber(List<Product> products);

    /**
     *
     */
    public List<Product> search(String keyword,int start,int count);

}
