package com.cleverua.bb.imagebrowser.ui;

import net.rim.device.api.system.Bitmap;
import net.rim.device.api.system.Characters;
import net.rim.device.api.system.EncodedImage;
import net.rim.device.api.ui.Color;
import net.rim.device.api.ui.DrawStyle;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.Graphics;
import net.rim.device.api.ui.UiApplication;

import com.cleverua.bb.imagebrowser.BitmapLoader;
import com.cleverua.bb.imagebrowser.IBitmapLoading;
import com.cleverua.bb.imagebrowser.IImageBrowserItemModel;
import com.cleverua.bb.utils.ImageUtils;

public class ImageBrowserItemField extends Field {

    private static final String READING_LABEL = "Reading...";
    private static final String ERROR_LABEL   = "Error";
    
    private static final int PADDING_LEFT   = 10;
    private static final int PADDING_RIGHT  = 12;
    private static final int PADDING_TOP    = 10;
    private static final int PADDING_BOTTOM = 11;
    
    protected int paddingLeft, paddingRight, paddingTop, paddingBottom, totalPaddingX, totalPaddingY;
    
    private static final Bitmap BTN_STYLE_LEFT_CORNER = 
        Bitmap.getBitmapResource("resources/images/btn_style/corner_left.png");
    
    private static final Bitmap BTN_STYLE_RIGHT_CORNER = 
        Bitmap.getBitmapResource("resources/images/btn_style/corner_right.png");
    
    private static final EncodedImage BTN_STYLE_MIDDLE = 
        EncodedImage.getEncodedImageResource("resources/images/btn_style/middle.png");
    
    private int w; // Field's width
    private int h; // Field's height

    private boolean drawBtnStyle;
    
    private String imgUrl;
    private int desiredImgHeight, desiredImgWidth;
    private Bitmap imageBmp;
    private boolean isLoadingImage;
    private boolean errorLoadingImage;
    
    private String label;
    private int labelHeight, labelWidth;
    
    private int readingHeight, readingWidth;
    
    private int errorLoadingWidth, errorLoadingHeight;
    
    protected int labelToImageGap;
    
    /* package */ ImageBrowserItemField(IImageBrowserItemModel model, boolean isFocusable, boolean drawBtnStyle) {
        super(isFocusable ? Field.FOCUSABLE : Field.NON_FOCUSABLE);
        
        imgUrl = model.getImgUrl();
        label  = model.getLabel();
        this.drawBtnStyle = drawBtnStyle;
        
        paddingLeft   = PADDING_LEFT;
        paddingRight  = PADDING_RIGHT;
        paddingTop    = PADDING_TOP;
        paddingBottom = PADDING_BOTTOM;
        
        totalPaddingX = paddingRight + paddingLeft;
        totalPaddingY = paddingTop + paddingBottom;
    }
    
    /* package */ ImageBrowserItemField(IImageBrowserItemModel model) {
        this(model, true, true);
    }
    
    /* package */ void setDimention(int width, int height) {
        if (w != width || h != height) {
            // dimensions changed (probably screen was rotated), so reload imageBmp Bitmap
            imageBmp = null;
            isLoadingImage = false;
        }
        
        w = width;
        h = height;
    }
    
    public int getPreferredHeight() {
        return h;
    }
    
    public int getPreferredWidth() {
        return w;
    }
    
    protected void layout(int width, int height) {
        /*Logger.debug(this, "layout: entered for '" + label + "', width = " + 
        width + ", height = " + height);*/
        
        // ignore params, just use our custom w/h fields we've previously populated at setDimention()
        setExtent(w, h);
    }

    protected void drawFocus(Graphics gfx, boolean on) {
        // we don't call super.drawFocus(), completely overriding native behavior,
        // otherwise some devices (e.g. Storm) draw some native focus graphics
        // that looks a bit ugly for us
        if (on) {
            gfx.setBackgroundColor(0x005DE7);
            gfx.clear();
            paint(gfx);
        }
    }
    
    protected void paintBackground(Graphics gfx) {
        if (drawBtnStyle) {
            gfx.setBackgroundColor(Color.BLACK);
            gfx.clear();
        }
        super.paintBackground(gfx);
    }
    
    protected void paint(Graphics gfx) {
        //Logger.debug(this, "paint: entered for '" + label + '\'');
        
        final int fontHeight = gfx.getFont().getHeight();
        
        if (label != null) {
            drawLabel(gfx, fontHeight);
        }
        
        if (imgUrl != null) {

            if (imageBmp != null) {
                drawBitmap(gfx);
                
            } else {
                
                if (errorLoadingImage) {
                    drawError(gfx, fontHeight);
                    
                } else {
                    if (!isLoadingImage) {
                        desiredImgHeight = h - labelToImageGap - labelHeight - totalPaddingY;
                        desiredImgWidth  = w - totalPaddingX;
                        isLoadingImage = true;
                        
                        BitmapLoader.requestLoading(bitmapLoading);
                    }
                    
                    if (isLoadingImage) {
                        drawLoading(gfx, fontHeight);
                    }
                }
            }
        }
        
        if (drawBtnStyle) {
            drawBtnStyle(gfx);
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
    
    private void drawBtnStyle(Graphics gfx) {
        final int initialColor = gfx.getColor();
        
        // draw border
        gfx.setColor(0x424242);
        gfx.drawLine(w - 1, 0, w - 1, h - 3);
        gfx.drawLine(0, h - 1, w - 1, h - 1);
        
        gfx.setColor(Color.BLACK);
        gfx.drawLine(w - 2, 0, w - 2, h - 2);
        gfx.drawLine(0, h - 2, w - 1, h - 2);
        
        gfx.setColor(initialColor);
        
        // corners are equal in size, so we can use just one value for both corners
        final int cornerWidth = BTN_STYLE_LEFT_CORNER.getWidth();
        final int middleWidth = w - 2 * cornerWidth - 2;
        
        // height is equal for all three pieces
        final int height = BTN_STYLE_LEFT_CORNER.getHeight();
        
        // draw left corner
        gfx.drawBitmap(0, 0, cornerWidth, height, BTN_STYLE_LEFT_CORNER, 0, 0);
        
        final EncodedImage middle = ImageUtils.resize(BTN_STYLE_MIDDLE, middleWidth + 1, height, false);
        
        // draw middle
        gfx.drawImage(cornerWidth, 0, middleWidth, height, middle, 0, 0, 0);
        
        // draw right corner
        gfx.drawBitmap(cornerWidth + middleWidth, 0, cornerWidth, height, BTN_STYLE_RIGHT_CORNER, 0, 0);
    }
    
    private void drawError(Graphics gfx, int fontHeight) {
        errorLoadingHeight = fontHeight;
        errorLoadingWidth  = w - totalPaddingX;
        
        final int initialColor = gfx.getColor();
        if (drawBtnStyle) {
            gfx.setColor(Graphics.WHITE);
        }
        
        gfx.drawText(
            ERROR_LABEL, 
            paddingLeft, 
            ((h - labelToImageGap - labelHeight - errorLoadingHeight - paddingBottom) >> 1), 
            (DrawStyle.ELLIPSIS | DrawStyle.HCENTER), 
            errorLoadingWidth
        );
        
        gfx.setColor(initialColor);
    }
    
    private void drawLoading(Graphics gfx, int fontHeight) {
        readingHeight = fontHeight;
        readingWidth  = w - totalPaddingX;
        
        final int initialColor = gfx.getColor();
        
        if (drawBtnStyle) {
            gfx.setColor(Graphics.WHITE);
        }
        
        gfx.drawText(
            READING_LABEL, 
            paddingLeft, 
            ((h - labelToImageGap - labelHeight - readingHeight - paddingBottom) >> 1), 
            (DrawStyle.ELLIPSIS | DrawStyle.HCENTER), 
            readingWidth
        );
        
        gfx.setColor(initialColor);
    }
    
    private void drawLabel(Graphics gfx, int fontHeight) {
        labelHeight = fontHeight;
        labelWidth  = w - totalPaddingX;
        
        final int initialColor = gfx.getColor();
        
        if (drawBtnStyle) {
            gfx.setColor(Graphics.WHITE);
        }
        
        gfx.drawText(
            label, 
            paddingLeft, (h - labelHeight - paddingBottom), 
            (DrawStyle.ELLIPSIS | DrawStyle.HCENTER), 
            labelWidth
        );
        
        gfx.setColor(initialColor);
    }
    
    private void drawBitmap(Graphics gfx) {
        final int width  = imageBmp.getWidth();
        final int height = imageBmp.getHeight();
        
        final int x = paddingLeft + ((desiredImgWidth  - width)  >> 1);
        final int y = paddingTop  + ((desiredImgHeight - height) >> 1);
        
        gfx.drawBitmap(x, y, width, height, imageBmp, 0, 0);
        
        final int initialColor = gfx.getColor();
        gfx.setColor(Color.GRAY);
        gfx.drawRect(x, y, width, height);
        gfx.setColor(initialColor);
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
                    imageBmp = b;
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