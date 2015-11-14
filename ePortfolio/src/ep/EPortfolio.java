/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ep;

import ep.file.EPortfolioFileManager;
import ep.file.EPortfolioSiteExporter;
import ep.view.EPortfolioView;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author linti
 */
public class EPortfolio extends Application {
    
     // THIS WILL PERFORM READING AND WRITING OF METADATA
    EPortfolioFileManager fileManager = new EPortfolioFileManager();
    
    // THIS WILL EXPORT THE WEB SITES
    EPortfolioSiteExporter siteExporter = new EPortfolioSiteExporter();
    
    // THIS HAS THE FULL USER INTERFACE AND ONCE IN EVENT
    // HANDLING MODE, BASICALLY IT BECOMES THE FOCAL
    // POINT, RUNNING THE UI AND EVERYTHING ELSE
    EPortfolioView ui = new EPortfolioView(fileManager, siteExporter);
    
    @Override
    public void start(Stage primaryStage) {
        // SET THE WINDOW ICON
	//String imagePath = PATH_IMAGES + ICON_WINDOW_LOGO;
	//File file = new File(imagePath);
        
        // GET AND SET THE IMAGE
        //URL fileURL = file.toURI().toURL();
        //Image windowIcon = new Image(fileURL.toExternalForm());
        //primaryStage.getIcons().add(windowIcon);
        
        //String appTitle = props.getProperty(TITLE_WINDOW);
        String appTitle = "ePortfolio Maker";
        ui.startUI(primaryStage, appTitle);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
