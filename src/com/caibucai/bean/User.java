package com.caibucai.bean;


/**
 * @author Csy
 * @Classname User
 * @date 2021/9/6 16:27
 * @Description TODO
 */


public class User {

    private int id;
    private String name;
    private String password;

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // »ñÈ¡ÄäÃû
    public String getAnonymousName() {
        return "";
    }

}
