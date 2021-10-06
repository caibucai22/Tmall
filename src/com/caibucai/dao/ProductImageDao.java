package com.caibucai.dao;

import com.caibucai.bean.Product;
import com.caibucai.bean.ProductImage;

import java.util.List;

public interface ProductImageDao {
    String type_single = "type_single";
    String type_detail = "type_detail";

    /**
     * ��ȡȫ��ͼƬ����
     * @return
     */
    public int getTotal();

    /**
     * ���ͼƬ
     * @param bean
     */
    public void add(ProductImage bean);

    /**
     * ����ͼƬ
     */
    public void update(ProductImage bean);

    /**
     * ɾ��ͼƬ
     */
    public void delete(int id);

    /**
     *  ��ȡͼƬ
     */
    public ProductImage get(int id);

    public List<ProductImage> list(Product p, String type);

    public List<ProductImage> list(Product p,String type,int start,int count);

}
