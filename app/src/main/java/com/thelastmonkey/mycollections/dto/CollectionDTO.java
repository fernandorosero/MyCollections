package com.thelastmonkey.mycollections.dto;

import java.io.Serializable;

/**
 * Created by tatof on 13/08/2016.
 */
public class CollectionDTO implements Serializable {

    private String idColecction;
    private String name;
    private String date;
    private String imgPAth;

    public String getIdColecction() {
        return idColecction;
    }

    public void setIdColecction(String idColecction) {
        this.idColecction = idColecction;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getImgPAth() {
        return imgPAth;
    }

    public void setImgPAth(String imgPAth) {
        this.imgPAth = imgPAth;
    }



}
