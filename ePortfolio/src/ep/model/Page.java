/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ep.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;

/**
 *
 * @author linti
 */
public class Page {
    String pageTitle;
    String pageFooter;
    String cssLayout;
    String cssColor;
    String cssFont;
    int numComponents;
    ObservableList<Component> components;
    

    public Page() {
        pageTitle = "New Page Title";
        pageFooter = "New Page Footer";
        cssLayout = "temp";
        cssColor= "temp";
        cssFont= "temp";
        numComponents = 0;
        components = FXCollections.observableArrayList();
    }

    public String getPageTitle() {
        return pageTitle;
    }

    public void setPageTitle(String string) {
        this.pageTitle = string;
    }

    public String getPageFooter() {
        return pageFooter;
    }

    public void setPageFooter(String text) {
        this.pageFooter = text;
    }
    
    
}
