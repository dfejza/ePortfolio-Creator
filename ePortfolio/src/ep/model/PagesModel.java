/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package ep.model;

import ep.view.EPortfolioView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author linti
 */
public class PagesModel {
    EPortfolioView ui;
    String title;
    ObservableList<Page> pages;
    int selectedPage;
    Page selectedPageObject;
    
    public PagesModel(EPortfolioView initUI) {
        ui = initUI;
        pages = FXCollections.observableArrayList();
        reset();
    }
    
    public ObservableList<Page> getPages() {
        return pages;
    }
    
    public Page getIndexedPage(int idx){
        Page temp =  pages.get(idx);
        return temp;
    }
    
    public void addPage(){
        Page pageToAdd = new Page();
        pageToAdd.setPageTitle(pageToAdd.getPageTitle() + pages.size());
        if(pages.size()==0)
            setSelectedpageObject(pageToAdd);
        pages.add(pageToAdd);
    }
    
    public void setSelectedPage(int sp){
        this.selectedPage = sp;
    }
    public int getSelectedpage(){
        return selectedPage;
    }
    public Page getSelectedpageObject(){
        return selectedPageObject;
    }
    
    public void setSelectedpageObject(Page temp){
        selectedPageObject = temp;
    }
    
    /**
     * Resets the slide show to have no slides and a default title.
     */
    public void reset() {
        pages.clear();
        //selectedPage = null;
    }
    
    public int size() {
        return pages.size();
    }
    
    public void removePage() {
        pages.remove(selectedPageObject);
    }

    public void addPage(String cssFont, String pageTitle, String pageHeader, String pageFooter, int pageFont,ObservableList<Component> components) {
        Page pageToAdd = new Page();
        pageToAdd.setPageTitle(pageTitle);
        pageToAdd.setPageHeader(pageHeader);
        pageToAdd.setPageFooter(pageFooter);
        pageToAdd.setPageFontChoice(pageFont);
        pageToAdd.reloadComponents(components);
        pages.add(pageToAdd);
    }
}
