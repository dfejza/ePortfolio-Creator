/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ep.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author linti
 */
public class Page {
    private String pageTitle;
    private String pageFooter;
    private String cssLayout;
    private String cssColor;
    private String cssFont;
    int numComponents;
    private ObservableList<Component> components;
    private int pageFontChoice;
    private String pageFontSize;
    

    public Page() {
        pageTitle = "New Page Title";
        pageFooter = "New Page Footer";
        pageFontSize = "12";
        pageFontChoice = 0;
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
    
    public int getPageFontChoice(){
        return pageFontChoice;
    }
    
    public void setPageFontChoice(int txt){
        this.pageFontChoice = txt;
    }
    
    public String getPageFontSize(){
        return pageFontSize;
    }
    
    public void setPageFontSize(String txt){
        this.pageFontSize = txt;
    }
    
    public void addComponent(Component comp){
        components.add(comp);
        numComponents++;
    }
    
  public ObservableList<Component> getComponents() {
	return components;
    }
  
}
