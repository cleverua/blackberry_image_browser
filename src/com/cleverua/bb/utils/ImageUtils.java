package com.cleverua.bb.utils;

import net.rim.device.api.math.Fixed32;
import net.rim.device.api.system.EncodedImage;

public class ImageUtils {

    /**
     * @return EncodedImage - a copy of eImage resized to new dimensions (toWidth x toHeight)
     */
    public static EncodedImage resize(EncodedImage eImage, int toWidth, int toHeight, 
            boolean keepAspectRatio) {
    
        int scaleX = Fixed32.div(Fixed32.toFP(eImage.getWidth()),  Fixed32.toFP(toWidth));
        int scaleY = Fixed32.div(Fixed32.toFP(eImage.getHeight()), Fixed32.toFP(toHeight));
        
        if (keepAspectRatio) {
            int scale = (scaleX > scaleY) ? scaleX : scaleY;
            return eImage.scaleImage32(scale, scale);
        } else {
            return eImage.scaleImage32(scaleX, scaleY);
        }
    }
    
}
