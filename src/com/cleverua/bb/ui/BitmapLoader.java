package com.cleverua.bb.ui;

import net.rim.device.api.ui.UiApplication;

import com.cleverua.bb.ui.IBitmapLoading;
import com.cleverua.bb.utils.IOUtils;
import com.cleverua.bb.utils.Logger;
import com.cleverua.bb.utils.WorkQueue;

public class BitmapLoader {

    private static final WorkQueue WORKER = new WorkQueue(1, 16);
    
    public static void requestLoading(final IBitmapLoading loading) {
        UiApplication.getUiApplication().invokeLater(new Runnable() {
            public void run() {
                WORKER.execute(new Runnable() {
                    public void run() {
                        try {
                            loading.setBitmap(
                                IOUtils.resizeImage(
                                    loading.getImgUrl(), 
                                    loading.getDesiredImgWidth(), 
                                    loading.getDesiredImgHeight()
                                ).getBitmap()
                            );
                        } catch (Exception e) {
                            loading.fail();
                            Logger.debug("BitmapLoader - failed to load image: " + e);
                        }
                    }
                });
            }
        });
    }
}
