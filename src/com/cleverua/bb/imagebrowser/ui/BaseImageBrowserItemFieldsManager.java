package com.cleverua.bb.imagebrowser.ui;

import net.rim.device.api.system.Display;
import net.rim.device.api.ui.container.FlowFieldManager;

public class BaseImageBrowserItemFieldsManager extends FlowFieldManager {

    private int columns;
    private int rows;
    private int yOffset;
    
    /* package */ BaseImageBrowserItemFieldsManager(int columns, int rows, int yOffset) {
        super(0);
        this.columns = (columns == 0) ? 1 : columns;
        this.rows    = (rows == 0)    ? 1 : rows;
        this.yOffset = yOffset;
    }
    
    protected void sublayout(int width, int height) {
        // we could make fieldWidth/fieldHeight to be instance fields as well as
        // define them in constructor, however need to make it here, because some 
        // devices repaint screen on device rotation (e.g. Storm)
        final int fieldWidth  = Math.min(Display.getWidth(), width) / columns;
        final int fieldHeight = (Math.min(Display.getHeight(), height) - yOffset) / rows;
        
        final int fieldsCount = getFieldCount();
        
        for (int i = 0; i < fieldsCount; i++) {
            ((ImageBrowserItemField) getField(i)).setDimention(fieldWidth, fieldHeight);
        }
        
        super.sublayout(width, height);
    }

}
