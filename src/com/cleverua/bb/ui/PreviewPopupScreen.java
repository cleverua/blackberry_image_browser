package com.cleverua.bb.ui;

import net.rim.device.api.ui.component.BitmapField;
import net.rim.device.api.ui.component.LabelField;
import net.rim.device.api.ui.container.PopupScreen;
import net.rim.device.api.ui.container.VerticalFieldManager;

// FIXME: not finished

public class PreviewPopupScreen extends PopupScreen {

    BitmapField bitmapField = new BitmapField();
    LabelField label;
    
    public PreviewPopupScreen() {
        super(new VerticalFieldManager());
    }

    public void show() {
        
    }
    
    public void close() {
        
        super.close();
    }
}
