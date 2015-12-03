/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package ep.controller;

import ep.file.EPortfolioFileManager;
import ep.file.EPortfolioSiteExporter;
import ep.view.EPortfolioView;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

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
        siteExporter = new EPortfolioSiteExporter();
        //siteExporter = initSiteExporter;
    }
    public void handleNewEPRequest() {
        ui.startNewSession();
    }
    public void handleLoadEPRequest() throws IOException {
        EPIO.loadSlideShow(ui, promptToOpen());
        
    }
    public boolean handleSaveEPRequest() throws FileNotFoundException {
        //SOMETHING TO ASK FOR DIRECTORY?
        String dir = "";
        EPIO.saveSlideShow(ui,null);
        return false;
    }
    public boolean handleSaveAsEPRequest() throws FileNotFoundException {
        String dir = "";
        dir = promptToSave();
        EPIO.saveSlideShow(ui,dir);
        return false;
    }
    public void handleViewEPRequest() {
        String dir = "./temp.json";
        try {
            EPIO.saveSlideShow(ui,dir);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FileController.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            siteExporter.exportSite(ui);
        } catch (IOException ex) {
            Logger.getLogger(FileController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    public void handleExitRequest() {
        
    }
    
    
    
     private String promptToOpen() {
        // AND NOW ASK THE USER FOR THE COURSE TO OPEN
        FileChooser slideShowFileChooser = new FileChooser();
        slideShowFileChooser.setInitialDirectory(new File("./"));
        File selectedFile = slideShowFileChooser.showOpenDialog(ui.getWindow());
        return selectedFile.getPath();
    }
     
     private String promptToSave() {
        // AND NOW ASK THE USER FOR THE COURSE TO OPEN
        FileChooser slideShowFileChooser = new FileChooser();
        slideShowFileChooser.setInitialDirectory(new File("./"));
        slideShowFileChooser.getExtensionFilters().addAll(new ExtensionFilter("JSON File", "*.json"));
        slideShowFileChooser.setInitialFileName(ui.getStudentName() + ".json");
        File selectedFile = slideShowFileChooser.showSaveDialog(ui.getWindow());
        return selectedFile.getPath();
    }
}
