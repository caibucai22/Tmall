package com.caibucai.bean;

import java.util.Date;

/**
 * @author Csy
 * @Classname Review
 * @date 2021/9/7 10:35
 * @Description TODO
 */
public class Review {
    /**
     * review��
     *
     * id ����
     * content ��������
     * createDate ����ʱ��
     *
     */
    private int id;
    private String content;
    private Date createDate;

    /**
     * ���û��� User ��ϵ
     */
    private User user;

    /**
     * ���Ʒ�� Product ��ϵ
     */
    private Product product;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
