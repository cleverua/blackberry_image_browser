package com.cleverua.bb.imagebrowser.ui;

import net.rim.device.api.system.Display;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.Manager;

/**
 * This class is currently unused, but kept for possible future usage.
 */
public class UnusedCustomManager extends Manager {

    private int columns; 
    private int rows;
    private int yOffset;
    
    public UnusedCustomManager(int columns, int rows, int yOffset) {
        super(0);
        this.columns = (columns == 0) ? 1 : columns; 
        this.rows    = (rows == 0)    ? 1 : rows;
        this.yOffset = yOffset;
    }

    protected void sublayout(int width, int height) {
        int totalManagerHeight = 0;
        
        final int fieldWidth  = Math.min(Display.getWidth(), width) / columns;
        final int fieldHeight = (Math.min(Display.getHeight(), height) - yOffset) / rows;
        
        final int fieldsCount = getFieldCount();
        
        if (fieldsCount > 0) {
            totalManagerHeight = fieldHeight; // at least
        }
        
        for (int i = 0, column = 0, row = 0; i < fieldsCount; i++, column++) {
            final Field f = getField(i);
            
            if (column == columns) {
                column = 0;
                row++;
                totalManagerHeight += fieldHeight;
            }
            
            setPositionChild(f, column * fieldWidth, row * fieldHeight);
            layoutChild(f, fieldWidth, fieldHeight);
        }

        setExtent(width, totalManagerHeight);
    }
    
    protected boolean navigationMovement(int dx, int dy, int status, int time) {
        final int focusedIndex = getFieldWithFocusIndex();
        Field nextFocused = null;
        
        if (dy > 0) /* move down */ {
            
            if (isInLastRaw(focusedIndex)) {
                /* let the super() handle this case */
            } else {
                try { 
                    nextFocused = getField(focusedIndex + columns);
                } catch (Exception e) {
                    nextFocused = getField(getFieldCount() - 1); /* choose the last one */
                }
            }
            
        } else if (dy < 0) /* move up */ {
            
            if (focusedIndex < columns) {
                /* we are on the first row - let the super() handle this case */
            } else {
                nextFocused = getField(focusedIndex - columns);
            }
            
        } else if (dx > 0) /* move right */ {
            
            if (isInRightColumn(focusedIndex) && (focusedIndex != (getFieldCount() - 1))) {
                return true; /* just stay unchanged */
            } else {
                /* let the super() handle this case */
            }
            
        } else if (dx < 0) /* move left */ {
            
            if (isInLeftColumn(focusedIndex)) {
                return true; /* just stay unchanged */
            } else {
                /* let the super() handle this case */
            }
        } 
        
        if (nextFocused != null) {
            nextFocused.setFocus();
            return true;
        }
        
        return super.navigationMovement(dx, dy, status, time);
    }
    
    private boolean isInLastRaw(int index) {
        final int fieldsCount = getFieldCount(); 
        final int fullRowsCount = fieldsCount / columns;
        final int fullRowsFieldsCount = fullRowsCount * columns;
        
        if (fullRowsFieldsCount == fieldsCount) {
            return index >= (fieldsCount - columns);
        } else {
            return index >= fullRowsFieldsCount;
        }
    }
    
    private boolean isInLeftColumn(int index) {
        return (index / columns * columns) == index;
    }
    
    private boolean isInRightColumn(int index) {
        return ((index + 1) / columns * columns) == (index + 1);
    }
}
