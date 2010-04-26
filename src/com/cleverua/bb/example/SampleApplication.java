package com.cleverua.bb.example;

import net.rim.device.api.ui.UiApplication;

import com.cleverua.bb.imagebrowser.ui.ImageBrowserScreen;

public class SampleApplication extends UiApplication {
    
    private static SampleApplication application;

    public static void main(String[] args) {
        final int modelsSize = 20;
        Model[] models = new Model[modelsSize];
        for (int i = 0; i < modelsSize; i++) {
            models[i] = new Model("file:///SDCard/big_img.jpg", "Item " + i);
        }

        application = new SampleApplication();
        application.pushScreen(new ImageBrowserScreen(models, 3, 3));
        application.enterEventDispatcher();
    }
}
