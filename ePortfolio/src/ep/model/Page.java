/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package ep.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javax.json.JsonValue;

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
    private int pageFontIDX;
    private String pageFontChoice;
    private String pageFontSize;
    private Component selectedComponent;
    private String pageHeader;
    
    
    public Page() {
        pageTitle = "New Page Title";
        pageFooter = "New Page Footer";
        pageHeader = "New Page Header";
        pageFontSize = "12";
        pageFontIDX = 0;
        pageFontChoice = "font1.css";
        /*        cssLayout = "temp";
        cssColor= "temp";
        cssFont= "temp";*/
        numComponents = 0;
        components = FXCollections.observableArrayList();
        selectedComponent = null;
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
        return pageFontIDX;
    }
    
    public void setPageFontChoice(int txt){
        this.pageFontIDX = txt;
        switch(txt){
            case 0:
                pageFontChoice = "font1.css";
                break;
            case 1:
                pageFontChoice = "font2.css";
                break;
            case 2:
                pageFontChoice = "font3.css";
                break;
            case 3:
                pageFontChoice = "font4.css";
                break;
            case 4:
                pageFontChoice = "font5.css";
                break;
            default:
                pageFontChoice = "font1.css";
                break;
        }
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
    
    public Component getSelectedComponent(){
        return selectedComponent;
    }
    
    public void removeSelectedComponent(){
        components.remove(selectedComponent);
        selectedComponent = null;
    }
    
    public void setSelectedComponent(Component comp){
        this.selectedComponent = comp;
    }
    public ObservableList<Component> getComponents() {
        return components;
    }
    
    public boolean isSelectedComponent(Component component) {
        return selectedComponent == component;
    }
    
    public String getCSSFontChoice(){
        return pageFontChoice;
    }

    public String getPageHeader() {
        return pageHeader;
    }
    public void setPageHeader(String text) {
        this.pageHeader = text;
    }
}
