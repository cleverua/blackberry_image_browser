package com.cleverua.bb.imagebrowser.ui;

import net.rim.device.api.ui.Color;
import net.rim.device.api.ui.Graphics;

public class ImageBrowserItemFieldsManager extends BaseImageBrowserItemFieldsManager {

    /* package */ ImageBrowserItemFieldsManager(int columns, int rows, int yOffset) {
        super(columns, rows, yOffset);
    }
    
    protected void paint(Graphics gfx) {
        gfx.setBackgroundColor(Color.BLACK);
        gfx.clear();
        super.paint(gfx);
    }
}