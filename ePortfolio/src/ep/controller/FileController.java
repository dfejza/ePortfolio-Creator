/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package ep.controller;

import ep.file.EPortfolioFileManager;
import ep.file.EPortfolioSiteExporter;
import ep.view.EPortfolioView;
import ep.view.YesNoCancelDialog;
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
        try {
            // WE MAY HAVE TO SAVE CURRENT WORK
            boolean continueToExit = true;
            if (!saved) {
                // THE USER CAN OPT OUT HERE
                continueToExit = promptToSavev2();
            }
            // IF THE USER REALLY WANTS TO EXIT THE APP
            if (continueToExit) {
                if(!ui.getworkspaceInitialized()){
                    ui.startNewSession();
                }else{
                    ui.getPages().reset();
                    ui.setBannerImageString("Select Banner Image");
                    ui.setStudentName("");
                    ui.reloadPages();
                }
                saved = false;
            }
        } catch (IOException ioe) {
            
        }
        

    }
    public void handleLoadEPRequest() throws IOException {
        EPIO.loadSlideShow(ui, promptToOpen());
        saved = true;
        ui.disableSaveButtons();
    }
    public boolean handleSaveEPRequest() throws FileNotFoundException {
        //SOMETHING TO ASK FOR DIRECTORY?
        String dir = "";
        EPIO.saveSlideShow(ui,null);
        saved = true;
        ui.disableSaveButtons();
        return false;
    }
    public boolean handleSaveAsEPRequest() throws FileNotFoundException {
        String dir = "";
        dir = promptToSaveFile();
        EPIO.saveSlideShow(ui,dir);
        saved = true;
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
        try {
            // WE MAY HAVE TO SAVE CURRENT WORK
            boolean continueToExit = true;
            if (!saved) {
                // THE USER CAN OPT OUT HERE
                continueToExit = promptToSave();
            }
            
            // IF THE USER REALLY WANTS TO EXIT THE APP
            if (continueToExit) {
                // EXIT THE APPLICATION
                System.exit(0);
            }
        } catch (IOException ioe) {
            
        }
    }
    
    
    
    private String promptToOpen() {
        // AND NOW ASK THE USER FOR THE COURSE TO OPEN
        FileChooser slideShowFileChooser = new FileChooser();
        slideShowFileChooser.setInitialDirectory(new File("./"));
        File selectedFile = slideShowFileChooser.showOpenDialog(ui.getWindow());
        return selectedFile.getPath();
    }
    
    private String promptToSaveFile() {
        // AND NOW ASK THE USER FOR THE COURSE TO OPEN
        FileChooser slideShowFileChooser = new FileChooser();
        slideShowFileChooser.setInitialDirectory(new File("./"));
        slideShowFileChooser.getExtensionFilters().addAll(new ExtensionFilter("JSON File", "*.json"));
        slideShowFileChooser.setInitialFileName(ui.getStudentName() + ".json");
        File selectedFile = slideShowFileChooser.showSaveDialog(ui.getWindow());
        return selectedFile.getPath();
    }
    
    public void markFileAsNotSaved() {
        saved = false;
        ui.enableSaveButtons();
    }
    public boolean isSaved() {
        return saved;
    }
    
    private boolean promptToSave() throws IOException {
        // PROMPT THE USER TO SAVE UNSAVED WORK
        YesNoCancelDialog yesNoCancelDialog = new YesNoCancelDialog(ui.getWindow());
        yesNoCancelDialog.show("Would you like to save your work before exiting?");
        
        // AND NOW GET THE USER'S SELECTION
        String selection = yesNoCancelDialog.getSelection();
        boolean saveWork = selection.equals(YesNoCancelDialog.YES);
        boolean cancel = selection.equals(YesNoCancelDialog.CANCEL);
        
        // IF THE USER SAID YES, THEN SAVE BEFORE MOVING ON
        if (saveWork) {
            handleSaveAsEPRequest();
        } // IF THE USER SAID CANCEL, THEN WE'LL TELL WHOEVER
        // CALLED THIS THAT THE USER IS NOT INTERESTED ANYMORE
        else if (cancel) {
            return false;
        }
        
        // IF THE USER SAID NO, WE JUST GO ON WITHOUT SAVING
        // BUT FOR BOTH YES AND NO WE DO WHATEVER THE USER
        // HAD IN MIND IN THE FIRST PLACE
        return true;
    }
    
    private boolean promptToSavev2() throws IOException {
        // PROMPT THE USER TO SAVE UNSAVED WORK
        YesNoCancelDialog yesNoCancelDialog = new YesNoCancelDialog(ui.getWindow());
        yesNoCancelDialog.show("Would you like to save your work before starting anew?");
        
        // AND NOW GET THE USER'S SELECTION
        String selection = yesNoCancelDialog.getSelection();
        boolean saveWork = selection.equals(YesNoCancelDialog.YES);
        boolean cancel = selection.equals(YesNoCancelDialog.CANCEL);
        
        // IF THE USER SAID YES, THEN SAVE BEFORE MOVING ON
        if (saveWork) {
            handleSaveAsEPRequest();
        } // IF THE USER SAID CANCEL, THEN WE'LL TELL WHOEVER
        // CALLED THIS THAT THE USER IS NOT INTERESTED ANYMORE
        else if (cancel) {
            return false;
        }
        
        // IF THE USER SAID NO, WE JUST GO ON WITHOUT SAVING
        // BUT FOR BOTH YES AND NO WE DO WHATEVER THE USER
        // HAD IN MIND IN THE FIRST PLACE
        return true;
    }
    
}
