/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ep.controller;

import ep.model.Component;
import ep.view.EPortfolioView;
import java.io.File;
import java.util.Optional;
import javafx.geometry.Insets;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.util.Callback;

/**
 *
 * @author linti
 */
public class VideoSelectionController {
    EPortfolioView ui;
    
    /**
     * Default contstructor doesn't need to initialize anything
     */
    public VideoSelectionController(EPortfolioView initUi) {   
	ui = initUi;
    }
    
    /**
     * This function provides the response to the user's request to
     * select an video.
     */
    //public void processSelectImage(Component componentToEdit) {
    public void processSelectVideo(Component currentComponent) {
	FileChooser imageFileChooser = new FileChooser();
	
	// SET THE STARTING DIRECTORY
	imageFileChooser.setInitialDirectory(new File("./videos/"));
	
	// LET'S ONLY SEE IMAGE FILES
	FileChooser.ExtensionFilter jpgFilter = new FileChooser.ExtensionFilter("MP4 files (*.mp4)", "*.MP4");
	FileChooser.ExtensionFilter pngFilter = new FileChooser.ExtensionFilter("WebM files (*.webm)", "*.WEBM");
	FileChooser.ExtensionFilter gifFilter = new FileChooser.ExtensionFilter("Ogg files (*.ogg)", "*.OGG");
	imageFileChooser.getExtensionFilters().addAll(jpgFilter, pngFilter, gifFilter);
	
	// LET'S OPEN THE FILE CHOOSER
	File file = imageFileChooser.showOpenDialog(null);
	if (file != null) {
	    //String path = file.getPath().substring(0, file.getPath().indexOf(file.getName()));
	    //String fileName = file.getName();
                      currentComponent.videoComponent(file.toURI().toString());
	}else{
                    currentComponent.videoComponent(null);
        }
    }
    
    public void askVideoParameters(Component currentComponent){
        Dialog<Component> dialog = new Dialog<>();
        dialog.setTitle("Video Parameters");
        dialog.setHeaderText("Enter the parameters for this video");
        dialog.setContentText("Please enter your name:");

        // Create the username and password labels and fields.
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField caption = new TextField();
        TextField displayWidth = new TextField();
        TextField displayHeight = new TextField();
        caption.setPromptText("Enter caption for video here");
        displayHeight.setPromptText("800");
        displayWidth.setPromptText("600");
        grid.add(new Label("Caption:"), 0, 0);
        grid.add(caption, 1, 0);
        grid.add(new Label("Display Height:"), 0, 1);
        grid.add(displayHeight, 1, 1);
        grid.add(new Label("Display Width:"), 0, 2);
        grid.add(displayWidth, 1, 2);
        dialog.getDialogPane().setContent(grid);
        ButtonType buttonTypeOk = new ButtonType("Okay", ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().add(buttonTypeOk);

        dialog.setResultConverter(new Callback<ButtonType, Component>() {
            @Override
            public Component call(ButtonType b) {

                if (b == buttonTypeOk) {
                    currentComponent.setParam(displayHeight.getText(), displayWidth.getText(), caption.getText());
                }
                return null;
            }
        });
        
        Optional<Component> result = dialog.showAndWait();
    }

}
