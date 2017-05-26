package com.zrb.domain;

import java.io.Serializable;

/**
 * Created by zrb on 2017/5/20.
 */
public class Course implements Serializable {
    private Integer id;
    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
