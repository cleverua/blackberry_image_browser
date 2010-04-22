package com.cleverua.bb.ui;

import net.rim.device.api.ui.UiApplication;
import net.rim.device.api.ui.component.BitmapField;
import net.rim.device.api.ui.component.LabelField;
import net.rim.device.api.ui.container.PopupScreen;

public class PreviewPopupScreen extends PopupScreen {

    BitmapField bitmapField = new BitmapField();
    LabelField label;
    
    public PreviewPopupScreen() {
        super(new NewMosaicManager(1, 1, 0), DEFAULT_CLOSE);
    }

    public void show(IMosaicModel model) {
        add(new MosaicItemField(model, false));
        UiApplication.getUiApplication().pushScreen(this);
    }
    
    public void close() {
        deleteAll();
        super.close();
    }
}
