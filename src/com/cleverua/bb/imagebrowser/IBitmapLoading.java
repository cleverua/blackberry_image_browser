package com.cleverua.bb.imagebrowser;

import net.rim.device.api.system.Bitmap;

public interface IBitmapLoading {
    void setBitmap(final Bitmap b);
    void fail();
    String getImgUrl();
    int getDesiredImgWidth();
    int getDesiredImgHeight();
}