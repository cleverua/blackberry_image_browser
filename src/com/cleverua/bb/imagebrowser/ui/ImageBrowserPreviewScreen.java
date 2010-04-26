package com.cleverua.bb.imagebrowser.ui;

import net.rim.device.api.ui.UiApplication;
import net.rim.device.api.ui.container.PopupScreen;

import com.cleverua.bb.imagebrowser.IImageBrowserItemModel;

public class ImageBrowserPreviewScreen extends PopupScreen {

    public ImageBrowserPreviewScreen() {
        super(new ImageBrowserPreviewScreenManager(), DEFAULT_CLOSE);
    }

    public void show(IImageBrowserItemModel model) {
        add(new ImageBrowserItemField(model, false));
        UiApplication.getUiApplication().pushScreen(this);
    }
    
    public void close() {
        deleteAll();
        super.close();
    }
}
