package com.cleverua.bb.ui;

import com.cleverua.bb.utils.Logger;

import net.rim.device.api.system.Bitmap;
import net.rim.device.api.system.Characters;
import net.rim.device.api.ui.DrawStyle;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.Graphics;
import net.rim.device.api.ui.UiApplication;
import net.rim.device.api.ui.XYDimension;

public class MosaicItemField extends Field {

    private static final String READING_LABEL = "Reading...";
    private static final String ERROR_LABEL   = "Error";
    
    private static final int PADDING       = 5;
    private static final int TOTAL_PADDING = PADDING * 2;
    
    private int w; // Field's width
    private int h; // Field's height
    
    private String imgUrl;
    private int desiredImgHeight;
    private int desiredImgWidth;
    private Bitmap bmp;
    private boolean isLoadingImage;
    private boolean errorLoadingImage;
    
    private String label;
    private int labelHeight;
    private int labelWidth;
    
    private int readingHeight;
    private int readingWidth;
    
    private int errorLoadingWidth;
    private int errorLoadingHeight;
    
    public MosaicItemField(IMosaicModel mosaicModel, boolean isFocusable) {
        super(isFocusable ? Field.FOCUSABLE : Field.NON_FOCUSABLE);
        imgUrl = mosaicModel.getImgUrl();
        label  = mosaicModel.getLabel();
    }
    
    void setDimention(XYDimension fieldDimension) {
        /*Logger.debug(this, "setDimention: entered for '" + label + 
                "', width = " + fieldDimension.width + ", height = " + fieldDimension.height);*/
        
        if (w != fieldDimension.width || h != fieldDimension.height) {
            // dimensions changed - probably screen was rotated, so need to reload Bitmap
            bmp = null;
            isLoadingImage = false;
        }
        
        w = fieldDimension.width;
        h = fieldDimension.height;
    }
    
    public int getPreferredHeight() {
        return h;
    }
    
    public int getPreferredWidth() {
        return w;
    }
    
    protected void layout(int width, int height) {
        Logger.debug(this, "layout: entered for '" + label + "', width = " + width + ", height = " + height);
        
        // ignore params, just use our custom w and h, that were previously set at setDimention()
        setExtent(w, h);
        
        //Logger.debug(this, "layout: passed");
    }

    protected void paint(Graphics gfx) {
        Logger.debug(this, "paint: entered for '" + label + '\'');
        
        if (label != null) {
            drawLabel(gfx);
        }
        
        if (imgUrl == null) { 
            return;
        }
        
        if (bmp == null) {
            
            if (errorLoadingImage) {
                drawError(gfx);
                return;
            }
            
            if (!isLoadingImage) {
                desiredImgHeight = h - labelHeight - TOTAL_PADDING;
                desiredImgWidth  = w - TOTAL_PADDING;
                isLoadingImage = true;
                
                BitmapLoader.requestLoading(bitmapLoading);
            }
            
            if (isLoadingImage) {
                drawLoading(gfx);
            }
            
        } else {
            drawBitmap(gfx);
        }
        
        //Logger.debug(this, "paint: passed");
        
    }
    
    protected boolean navigationClick(int status, int time) {
        fieldChangeNotify(0);
        return true;
    }
    
    protected boolean keyChar(char character, int status, int time) {
        if (character == Characters.ENTER) {
            fieldChangeNotify(0);
            return true;
        }
        return super.keyChar(character, status, time);
    }
    
    private void drawError(Graphics gfx) {
        errorLoadingHeight = gfx.getFont().getHeight();
        errorLoadingWidth  = w - TOTAL_PADDING;
        
        gfx.drawText(
            ERROR_LABEL, 
            PADDING, ((h - labelHeight - errorLoadingHeight - PADDING) >> 1), 
            (DrawStyle.ELLIPSIS | DrawStyle.HCENTER), 
            errorLoadingWidth
        );
    }
    
    private void drawLoading(Graphics gfx) {
        readingHeight = gfx.getFont().getHeight();
        readingWidth  = w - TOTAL_PADDING;
        
        gfx.drawText(
            READING_LABEL, 
            PADDING, ((h - labelHeight - readingHeight - PADDING) >> 1), 
            (DrawStyle.ELLIPSIS | DrawStyle.HCENTER), 
            readingWidth
        );
    }
    
    private void drawLabel(Graphics gfx) {
        labelHeight = gfx.getFont().getHeight();
        labelWidth  = w - TOTAL_PADDING;
        
        gfx.drawText(
            label, 
            PADDING, (h - labelHeight - PADDING), 
            (DrawStyle.ELLIPSIS | DrawStyle.HCENTER), 
            labelWidth
        );
    }
    
    private void drawBitmap(Graphics gfx) {
        gfx.drawBitmap(
            PADDING + ((desiredImgWidth  - bmp.getWidth())  >> 1), 
            PADDING + ((desiredImgHeight - bmp.getHeight()) >> 1), 
            bmp.getWidth(), bmp.getHeight(), 
            bmp, 0, 0
        );
    }
    
    private IBitmapLoading bitmapLoading = new IBitmapLoading() {
        
        public int getDesiredImgHeight() {
            return desiredImgHeight;
        }
        
        public int getDesiredImgWidth() {
            return desiredImgWidth;
        }
        
        public String getImgUrl() {
            return imgUrl;
        }
        
        public void setBitmap(final Bitmap b) {
            UiApplication.getUiApplication().invokeLater(new Runnable() {
                public void run() {
                    bmp = b;
                    isLoadingImage = false;
                    invalidate();
                }
            });
        }

        public void fail() {
            UiApplication.getUiApplication().invokeLater(new Runnable() {
                public void run() {
                    errorLoadingImage = true;
                    isLoadingImage = false;
                    invalidate();
                }
            });
        }
    };
    
}
