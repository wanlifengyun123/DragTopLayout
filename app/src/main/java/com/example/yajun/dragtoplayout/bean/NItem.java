package com.example.yajun.dragtoplayout.bean;

import java.io.Serializable;

/**
 * Created by yajun on 2016/6/8.
 */
public class NItem  implements Serializable {

    public String type;
    public String imgSrc;
    public String imgTxt;

    @Override
    public String toString() {
        return "NItem{" +
                "imgSrc='" + imgSrc + '\'' +
                ", imgTxt='" + imgTxt + '\'' +
                '}';
    }
}
