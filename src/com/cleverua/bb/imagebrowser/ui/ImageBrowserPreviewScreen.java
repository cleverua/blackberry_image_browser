package com.cleverua.bb.imagebrowser.ui;

import net.rim.device.api.ui.UiApplication;
import net.rim.device.api.ui.container.PopupScreen;

import com.cleverua.bb.imagebrowser.IImageBrowserItemModel;

public class ImageBrowserPreviewScreen extends PopupScreen {

    /* package */ ImageBrowserPreviewScreen() {
        super(new ImageBrowserPreviewScreenManager(), DEFAULT_CLOSE);
    }

    /* package */ void show(IImageBrowserItemModel model) {
        add(new ImageBrowserPreviewItemField(model));
        UiApplication.getUiApplication().pushScreen(this);
    }
    
    public void close() {
        deleteAll();
        super.close();
    }
}
