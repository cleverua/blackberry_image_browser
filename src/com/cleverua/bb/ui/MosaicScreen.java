package com.cleverua.bb.ui;

import java.util.Vector;

import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.FieldChangeListener;
import net.rim.device.api.ui.component.Dialog;
import net.rim.device.api.ui.component.LabelField;
import net.rim.device.api.ui.component.Menu;
import net.rim.device.api.ui.container.MainScreen;

import com.cleverua.bb.example.Model;
import com.cleverua.bb.utils.FieldsUtils;

public class MosaicScreen extends MainScreen {

    private static final String SCREEN_TITLE = "Image Browser Sample";
    
    private LabelField screenTitleFeild;
    private Model[] models;
    private int columns;
    private int rows;

    public MosaicScreen(Model[] modelsArray, int columns, int rows) {
        super();
        
        models  = modelsArray;
        
        this.columns = columns;
        this.rows    = rows;
        
        initUI();
    }
    
    public MosaicScreen(Vector modelsVector, int columns, int rows) {
        super();
        
        models = new Model[modelsVector.size()];
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
    
    private void initUI() {
        screenTitleFeild = FieldsUtils.getScreenTitleFeild(SCREEN_TITLE);
        add(screenTitleFeild);
        addMosaicItemFields();
    }
    
    private void addMosaicItemFields() {
        int offset = screenTitleFeild.getPreferredHeight();
        NewMosaicManager mosaicManager = new NewMosaicManager(columns, rows, offset);
        
        final int modelsSize = models.length;
        
        for (int i = 0; i < modelsSize; i++) {
            MosaicItemField field = new MosaicItemField(models[i]);
            field.setChangeListener(new FieldChangeListener() {
                public void fieldChanged(Field f, int c) {
                    Dialog.inform("Not implemented yet!!!");
                }
            });
            mosaicManager.add(field);
        }
        
        add(mosaicManager);
    }

}