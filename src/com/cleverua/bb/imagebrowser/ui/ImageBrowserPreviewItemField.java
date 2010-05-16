package com.cleverua.bb.imagebrowser.ui;

import com.cleverua.bb.imagebrowser.IImageBrowserItemModel;

public class ImageBrowserPreviewItemField extends ImageBrowserItemField {

    /* package */ ImageBrowserPreviewItemField(IImageBrowserItemModel model) {
        super(model, false, false);
        labelToImageGap = 5;
        paddingLeft     = 0;
        paddingRight    = 0;
        paddingTop      = 0;
        paddingBottom   = 0;
        totalPaddingX   = 0;
        totalPaddingY   = 0;
    }

}
