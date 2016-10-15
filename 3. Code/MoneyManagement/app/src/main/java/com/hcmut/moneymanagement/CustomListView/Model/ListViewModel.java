package com.hcmut.moneymanagement.CustomListView.Model;

/**
 * Created by Admin on 15-Oct-16.
 */
public class ListViewModel {
    private String title;
    private String description;
    private String icon;

    public ListViewModel(String icon, String title, String description) {
        this.title = title;
        this.icon = icon;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
