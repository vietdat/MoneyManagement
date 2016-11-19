package com.hcmut.moneymanagement.activity.NavDrawItem.model;

/**
 * Created by Admin on 13-Oct-16.
 */
public class NavDrawerItem {
    private boolean showNotify;
    private String title;
    private String nameOfImage;


    public NavDrawerItem() {

    }

    public String getNameOfImage() {
        return nameOfImage;
    }

    public void setNameOfImage(String nameOfImage) {
        this.nameOfImage = nameOfImage;
    }

    public NavDrawerItem(boolean showNotify, String title, String nameOfImage) {
        this.showNotify = showNotify;
        this.title = title;
        this.nameOfImage = nameOfImage;
    }

    public boolean isShowNotify() {
        return showNotify;
    }

    public void setShowNotify(boolean showNotify) {
        this.showNotify = showNotify;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
