package com.caibucai.bean;

/**
 * @author Csy
 * @Classname ProductImage
 * @date 2021/9/7 10:17
 * @Description TODO
 */
public class ProductImage {

    /**
     * ProductImage ��
     *  id ����
     *  type ����
     */

    private int id;
    private String type;

    /**
     * �� ��Ʒ product���ϵ
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
