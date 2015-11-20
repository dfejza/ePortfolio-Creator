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
    
   public PagesModel(EPortfolioView initUI) {
        ui = initUI;
        pages = FXCollections.observableArrayList();
        reset();	
    }
  public ObservableList<Page> getPages() {
	return pages;
    }
  
   public void addPage(){
        Page pageToAdd = new Page();
        pageToAdd.setPageTitle("Page " + pages.size());
        pages.add(pageToAdd);
   }
   
   public void setSelectedPage(int sp){
       this.selectedPage = sp;
   }
   public int getSelectedpage(){
       return selectedPage;
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
}
