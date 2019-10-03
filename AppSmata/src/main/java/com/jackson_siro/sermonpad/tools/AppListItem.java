package com.jackson_siro.sermonpad.tools;

public class AppListItem {
    String title = null;
    String descri = null;
    String code = null;
    String number;
    boolean selected = false;

    public AppListItem(String title, String descri, String code, String number, boolean selected) {
        super();
        this.title = title;
        this.descri = descri;
        this.code = code;
        this.number = number;
        this.selected = selected;
    }
    public String getTitle()
    {
        return title;
    }
    public void setTitle(String title)
    {
        this.title = title;
    }
    public String getDescri()
    {
        return descri;
    }
    public void setSongs(String descri)
    {
        this.descri = descri;
    }
    public String getCode()
    {
        return code;
    }
    public void setCode(String code)
    {
        this.code = code;
    }
    public String getNumber()
    {
        return number;
    }
    public void setNumber(String number)
    {
        this.number = number;
    }
    public boolean isSelected()
    {
        return selected;
    }
    public void setSelected(boolean selected)
    {
        this.selected = selected;
    }
}
