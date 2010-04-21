package com.cleverua.bb.ui;

import net.rim.device.api.system.Display;
import net.rim.device.api.ui.XYDimension;
import net.rim.device.api.ui.container.FlowFieldManager;

public class NewMosaicManager extends FlowFieldManager {

    private int columns; 
    private int rows;
    private int yOffset;
    
    public NewMosaicManager(int columns, int rows, int yOffset) {
        super(0);
        this.columns = (columns == 0) ? 1 : columns; 
        this.rows    = (rows == 0)    ? 1 : rows;
        this.yOffset = yOffset;
    }
    
    protected void sublayout(int width, int height) {
        final int fieldWidth  = Math.min(Display.getWidth(), width) / columns;
        final int fieldHeight = (Math.min(Display.getHeight(), height) - yOffset) / rows;
        final XYDimension fieldDimension = new XYDimension(fieldWidth, fieldHeight);
        
        final int fieldsCount = getFieldCount();
        
        for (int i = 0; i < fieldsCount; i++) {
            ((MosaicItemField) getField(i)).setDimention(fieldDimension);
        }
        
        super.sublayout(width, height);
    }
    
}
