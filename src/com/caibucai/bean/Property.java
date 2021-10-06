package com.caibucai.bean;

/**
 * @author Csy
 * @Classname Property
 * @date 2021/9/7 10:25
 * @Description TODO
 */
public class Property {
    private int id;
    private String name;

    /**
     * 与 Category 表关系
     */
    private Category category;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
