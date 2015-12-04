/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package ep.view;

import com.sun.jndi.toolkit.url.Uri;
import ep.controller.ImageSelectionController;
import ep.model.Component;
import ep.model.ssm.Slide;
import ep.model.ssm.SlideShowModel;
import java.awt.Font;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
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
    MediaView mediaView;
    
    // CONTROLS FOR EDITING THE CAPTION
    VBox captionVBox;
    Text text;
    TextField captionTextField;
    int index;
    String imagePath;
    
    // PROVIDES RESPONSES FOR IMAGE SELECTION
    ImageSelectionController imageController;
    
    public ComponentEditView(EPortfolioView initUi, Component initComponent) {
        try {
            // KEEP THIS FOR LATER
            ui = initUi;
            index = 0;
            
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
            
        } catch (MalformedURLException ex) {
            Logger.getLogger(ComponentEditView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * This function gets the image for the slide and uses it to
     * update the image displayed.
     */
    public void updateSlideImage() throws MalformedURLException {
        //String imagePath = slide.getImagePath() + SLASH + slide.getImageFileName();
        String imagePath = component.getImagePath();
        File file = new File(imagePath);
        
        //try {
        // GET AND SET THE IMAGE
        URL fileURL = file.toURI().toURL();
        Image slideImage = new Image(fileURL.toExternalForm());
        imageSelectionView.setImage(slideImage);
        
        // AND RESIZE IT
        imageSelectionView.setFitWidth(component.getWidth());
        imageSelectionView.setFitHeight(component.getHeight());
        
        /*	} catch (Exception e) {
        ErrorHandler eH = new ErrorHandler(null);
        eH.processError(LanguagePropertyType.ERROR_UNEXPECTED);
        }*/
    }
    
    public void updateVideo() {
        //String imagePath = slide.getImagePath() + SLASH + slide.getImageFileName();
        String imagePath = component.getImagePath();
        //file.toURI().toString()
        File file = new File(imagePath);
        
        Media media = new Media(file.toURI().toString());
        // Create the player and set to play automatically.
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setAutoPlay(false);
        // Create the view and add it to the Scene.
        mediaView = new MediaView(mediaPlayer);
        // AND RESIZE IT
        mediaView.setFitWidth(component.getWidth());
        mediaView.setFitHeight(component.getHeight());
        
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
        /*        String type = component.getFontType();
        if(type.compareTo("Alegreya Sans")==0){
        text.setStyle("-fx-font-family: Alegreya Sans;");
        getStylesheets().add("http://fonts.googleapis.com/css?family=Alegreya+Sans");
        }else if(type.compareTo("Lato")==0){
        text.setStyle("-fx-font-family: Lato;");
        getStylesheets().add("http://fonts.googleapis.com/css?family=Lato&subset=latin");
        }else if(type.compareTo("Indie Flower")==0){
        text.setStyle("-fx-font-family: Indie Flower;");
        getStylesheets().add("https://fonts.googleapis.com/css?family=Indie+Flower");
        }else if(type.compareTo("Titillium Web")==0){
        text.setStyle("-fx-font-family: Titillium Web;");
        getStylesheets().add("https://fonts.googleapis.com/css?family=Titillium+Web");
        }else if(type.compareTo("Sigmar One")==0){
        text.setStyle("-fx-font-family: Sigmar One;");
        getStylesheets().add("https://fonts.googleapis.com/css?family=Sigmar+One");
        }*/
        
        // LAY EVERYTHING OUT INSIDE THIS COMPONENT
        getChildren().add(text);
    }
    
    private void drawImageComp() throws MalformedURLException {
        imageSelectionView = new ImageView();
        updateSlideImage();
        getChildren().add(imageSelectionView);
        getChildren().add(new Text(component.getText()));
    }
    
    private void drawVideoComp() {
        mediaView = new MediaView();
        updateVideo();
        getChildren().add(mediaView);
        getChildren().add(new Text(component.getText()));
    }
    
    private void drawSlideShowComp() {
        SlideShowModel ss = component.getSS();
        ObservableList<Slide> slides = ss.getSlides();
        imageSelectionView = new ImageView();
        imagePath = slides.get(index).getImagePath() + "/" + slides.get(0).getImageFileName();
        File file = new File(imagePath);
        URL fileURL = null;
        try {
            fileURL = file.toURI().toURL();
        } catch (MalformedURLException ex) {
            Logger.getLogger(ComponentEditView.class.getName()).log(Level.SEVERE, null, ex);
        }
        Image slideImage = new Image(fileURL.toExternalForm());
        imageSelectionView.setImage(slideImage);
        imageSelectionView.setFitWidth(800);
        imageSelectionView.setFitHeight(600);
        getChildren().add(imageSelectionView);
        Text caption = new Text(imagePath);
        getChildren().add(caption);
        caption.setText(slides.get(index).getCaption());
        
        // YES, NO, AND CANCEL BUTTONS
        Button prev = new Button();
        prev.setText("Previous Image");
        Button next = new Button();
        next.setText("Next Image");
        
        // NOW ORGANIZE OUR BUTTONS
        HBox buttonBox = new HBox();
        buttonBox.getChildren().add(prev);
        buttonBox.getChildren().add(next);
        
        getChildren().add(buttonBox);
        
        next.setOnAction(e -> {
            index++;
            if(index>=slides.size()){
                index=0;
            }
            imagePath = slides.get(index).getImagePath() + "/" + slides.get(index).getImageFileName();
            File fileTemp = new File(imagePath);
            URL fileURLTemp = null;
            try {
                fileURLTemp = fileTemp.toURI().toURL();
            } catch (MalformedURLException ex) {
                Logger.getLogger(ComponentEditView.class.getName()).log(Level.SEVERE, null, ex);
            }
            Image slideImageTemp = new Image(fileURLTemp.toExternalForm());
            
            imageSelectionView.setImage(slideImageTemp);
            caption.setText(slides.get(index).getCaption());
        });
        
        prev.setOnAction(e -> {
            index--;
            if(index==-1){
                index=slides.size()-1;
            }
            imagePath = slides.get(index).getImagePath() + "/" + slides.get(index).getImageFileName();
            File fileTemp = new File(imagePath);
            URL fileURLTemp = null;
            try {
                fileURLTemp = fileTemp.toURI().toURL();
            } catch (MalformedURLException ex) {
                Logger.getLogger(ComponentEditView.class.getName()).log(Level.SEVERE, null, ex);
            }
            Image slideImageTemp = new Image(fileURLTemp.toExternalForm());
            
            imageSelectionView.setImage(slideImageTemp);
            caption.setText(slides.get(index).getCaption());
        });
        
        
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
