/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ep.view;

import ep.controller.ImageSelectionController;
import ep.model.Component;
import java.awt.Font;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

/**
 *
 * @author linti
 */


public class ComponentEditView extends VBox{
    EPortfolioView ui;
    
    // SLIDE THIS COMPONENT EDITS
    Component component;
    
    // DISPLAYS THE IMAGE FOR THIS SLIDE
    ImageView imageSelectionView;
    
    // CONTROLS FOR EDITING THE CAPTION
    VBox captionVBox;
    Text text;
    TextField captionTextField;
    
    // PROVIDES RESPONSES FOR IMAGE SELECTION
    ImageSelectionController imageController;
    
    public ComponentEditView(EPortfolioView initUi, Component initComponent) {
	// KEEP THIS FOR LATER
	ui = initUi;
	
	// FIRST SELECT THE CSS STYLE CLASS FOR THIS CONTAINER
	this.getStyleClass().add("component_outline");
	
	// KEEP THE SLIDE FOR LATER
	component = initComponent;
                  int type = component.getComponentType();
                  if(type==1) drawBodyComp();
                  if(type==2) drawImageComp();
                  if(type==3) drawVideoComp();
                  if(type==4) drawSlideShowComp();
                  //if(type==5) Hyperlink IGNORECASE?();
                  if(type==6) drawListComp();
                  if(type==9) drawHeaderComp();
                   
	// MAKE SURE WE ARE DISPLAYING THE PROPER IMAGE

                  /*
                  // SETUP THE CAPTION CONTROLS
                  captionVBox = new VBox();
                  captionLabel = new Label(props.getProperty(LanguagePropertyType.LABEL_CAPTION));
                  captionTextField = new TextField();
                  captionTextField.setText(slide.getCaption());
                  captionVBox.getChildren().add(captionLabel);
                  captionVBox.getChildren().add(captionTextField);
                  
                  // LAY EVERYTHING OUT INSIDE THIS COMPONENT
                  getChildren().add(imageSelectionView);
                  getChildren().add(captionVBox);
                  
                  // SETUP THE EVENT HANDLERS
                  imageController = new ImageSelectionController(ui);
                  imageSelectionView.setOnMousePressed(e -> {
                  imageController.processSelectImage(slide, this);
                  });
                  captionTextField.textProperty().addListener(e -> {
                  String text = captionTextField.getText();
                  slide.setCaption(text);
                  ui.updateFileToolbarControls(false);
                  });*/
	
	// CHOOSE THE STYLE
	//captionLabel.getStyleClass().add(CSS_CLASS_CAPTION_PROMPT);
	//captionTextField.getStyleClass().add(CSS_CLASS_CAPTION_TEXT_FIELD);
    }
    
    /**
     * This function gets the image for the slide and uses it to
     * update the image displayed.
     */
    public void updateSlideImage() throws MalformedURLException {
	//String imagePath = slide.getImagePath() + SLASH + slide.getImageFileName();
                  String imagePath = "C:/temp";
	File file = new File(imagePath);
        
	//try {
	    // GET AND SET THE IMAGE
	    URL fileURL = file.toURI().toURL();
	    Image slideImage = new Image(fileURL.toExternalForm());
	    imageSelectionView.setImage(slideImage);
	    
	    // AND RESIZE IT
	    double scaledWidth = 33333333;
	    double perc = scaledWidth / slideImage.getWidth();
	    double scaledHeight = slideImage.getHeight() * perc;
	    imageSelectionView.setFitWidth(scaledWidth);
	    imageSelectionView.setFitHeight(scaledHeight);
            /*	} catch (Exception e) {
            ErrorHandler eH = new ErrorHandler(null);
            eH.processError(LanguagePropertyType.ERROR_UNEXPECTED);
            }*/
    }   

    private void drawBodyComp() {
            text = new Text();
            text.setWrappingWidth(400);
            text.setTextAlignment(TextAlignment.LEFT);           
            text.setText(component.getText());

            // LAY EVERYTHING OUT INSIDE THIS COMPONENT
            getChildren().add(text);
    }

    private void drawImageComp() {
        //imageSelectionView = new ImageView();
        //updateSlideImage();
    }

    private void drawVideoComp() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void drawSlideShowComp() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void drawListComp() {
            String[] listData = component.getListData();
            String line = "";
        for (String listData1 : listData) {
            getChildren().add(new Text(("- "+listData1)));
        }
        //this.getStyleClass().add("component_list");
        // LAY EVERYTHING OUT INSIDE THIS COMPONENT
        //getChildren().add(text);
    }

    private void drawHeaderComp() {
                  text = new Text();
                  text.setWrappingWidth(600);
                  text.setTextAlignment(TextAlignment.CENTER);           
                  text.setText(component.getText());
                  text.getStyleClass().add("HEADER_FONT");
                  // LAY EVERYTHING OUT INSIDE THIS COMPONENT
                  getChildren().add(text);
    }
}
