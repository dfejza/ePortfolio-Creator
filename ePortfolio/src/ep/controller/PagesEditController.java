/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ep.controller;

import ep.model.PagesModel;
import ep.view.EPortfolioView;

/**
 *
 * @author linti
 */
public class PagesEditController {
        // APP UI
    private EPortfolioView ui;
    
    public PagesEditController(EPortfolioView initUI){
        ui = initUI;
    }
    
    public void processAddPageRequest() {
        PagesModel pages = ui.getPages();
        if(pages.size()==0){
            ui.initPageInputs();
        }
        pages.addPage();
        ui.reloadPages();
    }
    
    public void processRemovePageRequest() {
        
    }
    public void processSelectPageRequest(){
        
    }
}
