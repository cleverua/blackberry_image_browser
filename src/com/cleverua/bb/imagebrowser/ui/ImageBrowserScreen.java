package com.cleverua.bb.imagebrowser.ui;

import java.util.Vector;

import net.rim.device.api.i18n.ResourceBundleFamily;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.FieldChangeListener;
import net.rim.device.api.ui.component.LabelField;
import net.rim.device.api.ui.component.Menu;
import net.rim.device.api.ui.container.MainScreen;

import com.cleverua.bb.imagebrowser.IImageBrowserItemModel;
import com.cleverua.bb.utils.FieldsUtils;

public class ImageBrowserScreen extends MainScreen {

    private static final String SCREEN_TITLE = "Image Browser Sample";
    
    private LabelField screenTitleFeild;
    private IImageBrowserItemModel[] models;
    private int columns;
    private int rows;
    
    private ImageBrowserItemFieldsManager mosaicManager;
    
    private ImageBrowserPreviewScreen previewScreen = new ImageBrowserPreviewScreen();

    public ImageBrowserScreen(IImageBrowserItemModel[] modelsArray, int columns, int rows) {
        super();
        
        models  = modelsArray;
        
        this.columns = columns;
        this.rows    = rows;
        
        initUI();
    }
    
    public ImageBrowserScreen(Vector modelsVector, int columns, int rows) {
        super();
        
        models = new IImageBrowserItemModel[modelsVector.size()];
        modelsVector.copyInto(models);
        
        this.columns = columns;
        this.rows    = rows;
        
        initUI();
    }

    public boolean onMenu(int instance) {
        if (instance == Menu.INSTANCE_CONTEXT) {
            return true;
        }
        return super.onMenu(instance);
    }
    
    protected boolean onSavePrompt() {
        return true;
    }
    
    /**
     * Does nothing
     */
    public void setTitle(ResourceBundleFamily arg0, int arg1) {
        /* do nothing */
    }
    
    /**
     * Does nothing
     */
    public void setTitle(String str) {
        /* do nothing */
    }
    
    /**
     * Does nothing
     */
    public void setTitle(Field f) {
        /* do nothing */
    }
    
    private void initUI() {
        screenTitleFeild = FieldsUtils.getScreenTitleFeild(SCREEN_TITLE);
        add(screenTitleFeild);
        addMosaicItemFields();
    }
    
    private void addMosaicItemFields() {
        int offset = screenTitleFeild.getPreferredHeight();
        mosaicManager = new ImageBrowserItemFieldsManager(columns, rows, offset);
        
        final int modelsSize = models.length;
        
        for (int i = 0; i < modelsSize; i++) {
            final IImageBrowserItemModel model = models[i];
            ImageBrowserItemField field = new ImageBrowserItemField(model);
            field.setChangeListener(new FieldChangeListener() {
                public void fieldChanged(Field f, int c) {
                    previewScreen.show(model);
                }
            });
            mosaicManager.add(field);
        }
        
        add(mosaicManager);
    }
}