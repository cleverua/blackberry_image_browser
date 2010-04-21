package com.cleverua.bb.example;

import net.rim.device.api.ui.UiApplication;

import com.cleverua.bb.ui.MosaicScreen;

public class SampleApplication extends UiApplication {
    
    private static SampleApplication application;

    public static void main(String[] args) {
        application = new SampleApplication();
        
        int modelsSize = 100;
        Model[] models = new Model[modelsSize];
        for (int i = 0; i < modelsSize; i++) {
            models[i] = new Model("file:///SDCard/big_img.jpg", "Item " + i);
        }
        
        application.pushScreen(new MosaicScreen(models, 4, 3));
        application.enterEventDispatcher();
    }
}
