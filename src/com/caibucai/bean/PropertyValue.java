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
     * �����pid �� ptid ��ԭ����д�� product property
     * û������кܺõĶ�Ӧ��
     * ���Ժ��ʹ���� Ҳ��ͨ��pid ���ҵ���Ӧ��product
     * ������ʵʹ�� �ǽ�product���� ��Ӧ��pid
     * ptid Ҳ��ͬ������
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
