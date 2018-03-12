package com.maintenancemonitor.util;

import javax.faces.bean.ManagedBean;

@ManagedBean(name = "megaMenuView")
public class MegaMenuView {
  
    private String orientation = "horizontal";
 
    public String getOrientation() {
        return orientation;
    }
 
    public void setOrientation(String orientation) {
        this.orientation = orientation;
    }
}