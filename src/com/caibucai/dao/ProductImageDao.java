package com.caibucai.dao;

import com.caibucai.bean.Product;
import com.caibucai.bean.ProductImage;

import java.util.List;

public interface ProductImageDao {
    String type_single = "type_single";
    String type_detail = "type_detail";

    /**
     * 获取全部图片数量
     * @return
     */
    public int getTotal();

    /**
     * 添加图片
     * @param bean
     */
    public void add(ProductImage bean);

    /**
     * 更新图片
     */
    public void update(ProductImage bean);

    /**
     * 删除图片
     */
    public void delete(int id);

    /**
     *  获取图片
     */
    public ProductImage get(int id);

    public List<ProductImage> list(Product p, String type);

    public List<ProductImage> list(Product p,String type,int start,int count);

}
