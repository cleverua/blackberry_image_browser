package com.cleverua.bb.example;

import net.rim.device.api.ui.UiApplication;
import net.rim.device.api.ui.component.Dialog;

import com.cleverua.bb.imagebrowser.ui.ImageBrowserScreen;
import com.cleverua.bb.utils.IOUtils;

public class SampleApplication extends UiApplication {
    
    private static final String RESOURCE_PREFIX = "/resources/images/";

    private static final String IMAGE_BROWSER_SAMPLE_DIR = 
        "file:///SDCard/image_browser_sample/";
    
    private static SampleApplication application;

    public static void main(String[] args) {
        
        application = new SampleApplication();
        
        UiApplication.getUiApplication().invokeLater(new Runnable() {
            public void run() {
                
                // check for SDCard
                if (!IOUtils.isSDCardAccessible()) {
                    Dialog.alert("SDCard not found. Please, insert SDCard and try again.");
                    return;
                }
                
                try {
                    IOUtils.createDir(IMAGE_BROWSER_SAMPLE_DIR);
                } catch (Exception e) {
                    Dialog.alert("Failed to create '" + IMAGE_BROWSER_SAMPLE_DIR + "' dir (" + e + ')');
                    return;
                }
                
                String url, imageName;
                final int modelsSize = 20;
                Model[] models = new Model[modelsSize];
                
                for (int i = 0; i < modelsSize; i++) {
                    
                    imageName = "" + (i + 1) + ".jpg";
                    url = IMAGE_BROWSER_SAMPLE_DIR + imageName;
                    
                    try {
                        if (!IOUtils.isPresent(url)) {
                            IOUtils.saveDataToFile(
                                url, 
                                SampleApplication.class.getResourceAsStream(RESOURCE_PREFIX + imageName)
                            );
                        }
                    } catch (Exception e) {
                        Dialog.alert("Failed to put test images on SDCard (" + e + ')');
                        return;
                    }
                    
                    models[i] = new Model(url, "Item " + (i + 1));
                }
                
                application.pushScreen(new ImageBrowserScreen(models, 3, 3));
            }
        });
        
        application.enterEventDispatcher();
    }
}
