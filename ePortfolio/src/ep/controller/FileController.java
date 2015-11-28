/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package ep.controller;

import ep.file.EPortfolioFileManager;
import ep.file.EPortfolioSiteExporter;
import ep.view.EPortfolioView;

/**
 *
 * @author linti
 */
public class FileController {
    // WE WANT TO KEEP TRACK OF WHEN SOMETHING HAS NOT BEEN SAVED
    private boolean saved;
    // THE APP UI
    private EPortfolioView ui;
    // THIS GUY KNOWS HOW TO READ AND WRITE SLIDE SHOW DATA
    private EPortfolioFileManager EPIO;
    // THIS ONE EXPORTS OUR SITE
    private EPortfolioSiteExporter siteExporter;
    
    
    public FileController(EPortfolioView initUI, EPortfolioFileManager EPIO) {
        // NOTHING YET
        saved = true;
        ui = initUI;
        this.EPIO = EPIO;
        //siteExporter = initSiteExporter;
    }
    public void handleNewEPRequest() {
        ui.startNewSession();
    }
    public void handleLoadEPRequest() {
        
    }
    public boolean handleSaveEPRequest() {
        return false;
    }
    public boolean handleSaveAsEPRequest() {
        return false;
    }
    public void handleViewEPRequest() {
        
    }
    public void handleExitRequest() {
        
    }
    
}
