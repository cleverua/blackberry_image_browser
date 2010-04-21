package com.cleverua.bb.example;

import com.cleverua.bb.ui.IMosaicModel;

public class Model implements IMosaicModel {

    private String imgUrl;
    private String label;
    
    public Model(String imgUrl, String label) {
        this.imgUrl = imgUrl;
        this.label = label;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public String getLabel() {
        return label;
    }
}
