package com.caibucai.bean;

/**
 * @author Csy
 * @Classname PropertyValue
 * @date 2021/9/7 10:56
 * @Description TODO
 */
public class PropertyValue {

    private int id;
    private Product product;
    private Property property;
    private String value;

    /**
     * 这里的pid 和 ptid 在原来是写成 product property
     * 没有与表有很好的对应性
     * 在以后的使用中 也是通过pid 来找到对应的product
     * 所以真实使用 是将product放入 对应的pid
     * ptid 也是同样道理
     */

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Property getProperty() {
        return property;
    }

    public void setProperty(Property property) {
        this.property = property;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
