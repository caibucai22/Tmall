package com.caibucai.bean;

/**
 * @author Csy
 * @Classname ProductImage
 * @date 2021/9/7 10:17
 * @Description TODO
 */
public class ProductImage {

    /**
     * ProductImage 表
     *  id 主键
     *  type 类型
     */

    private int id;
    private String type;

    /**
     * 与 产品 product表关系
     */
    private Product product;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
