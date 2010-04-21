package com.cleverua.bb.utils;

import java.util.Vector;

import net.rim.device.api.ui.Color;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.Graphics;
import net.rim.device.api.ui.Manager;
import net.rim.device.api.ui.XYPoint;
import net.rim.device.api.ui.component.LabelField;

public class FieldsUtils {
    /**
     * @param net.rim.device.api.ui.Field - field
     * @return net.rim.device.api.ui.XYPoint - Display coordinates where the field starts
     */
    public static XYPoint getOndisplayStartingPoint(Field field) {
        
        // get all parent managers
        Vector parentManagers = getParentManagers(field);
        
        int x = 0;
        int y = 0;
        
        int parentManagersQty = parentManagers.size();
        
        for (int i = 0; i < parentManagersQty; i++) {
            
            Manager manager = (Manager) parentManagers.elementAt(i);
            
            x += manager.getLeft();
            y += manager.getTop();
            
            x += manager.getPaddingLeft();
            y += manager.getPaddingTop();
            
            x -= manager.getHorizontalScroll();
            y -= manager.getVerticalScroll();
        }
        
        return new XYPoint(x, y);
    }
    
    /**
     * @param net.rim.device.api.ui.Field - field
     * @return java.util.Vector - vector of all parent managers for the field
     */
    public static Vector getParentManagers(Field field) {
        
        Vector parentManagers = new Vector();
        
        Manager lastAddedParentManager = field.getManager();
        
        if (lastAddedParentManager == null) {
            return parentManagers;
        }
        
        parentManagers.addElement(lastAddedParentManager);
        
        while (true) {
            lastAddedParentManager = (Manager) parentManagers.elementAt(parentManagers.size() - 1);
            
            Manager nextParentManager = lastAddedParentManager.getManager();
            
            if (nextParentManager == null) {
                break;
            }
            
            parentManagers.addElement(nextParentManager);
        }
        
        return parentManagers;
    }
    
    public static LabelField getScreenTitleFeild(String label) {
        return new LabelField(
            label, (LabelField.ELLIPSIS | LabelField.USE_ALL_WIDTH | LabelField.HCENTER)
        ) {
            protected void paint(Graphics gfx) {
                gfx.setBackgroundColor(Color.BLACK);
                gfx.clear();
                gfx.setColor(Color.WHITE);
                super.paint(gfx);
            }
        };
    }
}
