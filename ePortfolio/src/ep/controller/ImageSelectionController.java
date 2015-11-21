package ep.controller;

import ep.model.Component;
import ep.view.EPortfolioView;
import java.io.File;
import java.net.MalformedURLException;
import java.util.Optional;
import javafx.geometry.Insets;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.util.Callback;

/**
 * This controller provides a controller for when the user chooses to
 * select an image for the slide show.
 * 
 * @author McKilla Gorilla & _____________
 */
public class ImageSelectionController {
    EPortfolioView ui;
    ImageParameters imageParameters;
    
    /**
     * Default contstructor doesn't need to initialize anything
     */
    public ImageSelectionController(EPortfolioView initUi) {   
	ui = initUi;
    }
    
    /**
     * This function provides the response to the user's request to
     * select an image.
     * 
     * @param slideToEdit - Slide for which the user is selecting an image.
     * 
     * @param view The user interface control group where the image
     * will appear after selection.
     */
    //public void processSelectImage(Component componentToEdit) {
    public void processSelectImage() {
	FileChooser imageFileChooser = new FileChooser();
	
	// SET THE STARTING DIRECTORY
	imageFileChooser.setInitialDirectory(new File("./images/"));
	
	// LET'S ONLY SEE IMAGE FILES
	FileChooser.ExtensionFilter jpgFilter = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG");
	FileChooser.ExtensionFilter pngFilter = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.PNG");
	FileChooser.ExtensionFilter gifFilter = new FileChooser.ExtensionFilter("GIF files (*.gif)", "*.GIF");
	imageFileChooser.getExtensionFilters().addAll(jpgFilter, pngFilter, gifFilter);
	
	// LET'S OPEN THE FILE CHOOSER
	File file = imageFileChooser.showOpenDialog(null);
	if (file != null) {
	    String path = file.getPath().substring(0, file.getPath().indexOf(file.getName()));
	    String fileName = file.getName();
	    /*slideToEdit.setImage(path, fileName);
	    view.updateSlideImage();
	    ui.updateFileToolbarControls(false);*/
	}
    }
    
    public void askImageParameters(){
        Dialog<ImageParameters> dialog = new Dialog<>();
        dialog.setTitle("Image Parameters");
        dialog.setHeaderText("Enter the parameters for this image");

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField caption = new TextField();
        TextField displayWidth = new TextField();
        TextField displayHeight = new TextField();
        RadioButton neither = new RadioButton();
        RadioButton left = new RadioButton();
        RadioButton right = new RadioButton();
        caption.setPromptText("Enter caption for image here");
        displayHeight.setPromptText("800");
        displayWidth.setPromptText("600");
        grid.add(new Label("Caption:"), 0, 0);
        grid.add(caption, 1, 0);
        grid.add(new Label("Display Height:"), 0, 1);
        grid.add(displayHeight, 1, 1);
        grid.add(new Label("Display Width:"), 0, 2);
        grid.add(displayWidth, 1, 2);
        grid.add(new Label("Float Image:"), 0, 3);
        grid.add(new Label("LEFT:"), 0, 4);
        grid.add(left, 1, 4);
        grid.add(new Label("RIGHT:"), 0, 5);
        grid.add(right, 1, 5);
        grid.add(new Label("NEITHER:"), 0, 6);
        grid.add(neither, 1, 6);
        dialog.getDialogPane().setContent(grid);
        ButtonType buttonTypeOk = new ButtonType("Okay", ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().add(buttonTypeOk);

        dialog.setResultConverter(new Callback<ButtonType, ImageParameters>() {
            @Override
            public ImageParameters call(ButtonType b) {

                if (b == buttonTypeOk) {
                    int radioSelection;
                    if(left.isSelected()) radioSelection = 1;
                    else if(right.isSelected()) radioSelection = 2;
                    else radioSelection = 3;
                    return new ImageParameters(caption.getText(), displayHeight.getText(), displayWidth.getText(), radioSelection);
                }

                return null;
            }
        });
        
        Optional<ImageParameters> result = dialog.showAndWait();
        if (result.isPresent()){
            this.imageParameters = result.get();
        }
    }
    
    public String getImageCaption(){
        return imageParameters.caption;
    }
    public int getImageHeight(){
        return imageParameters.height;
    }
   public int getImageWidth(){
        return imageParameters.width;
    }
   public int getAllighnment(){
        return imageParameters.allighnment;
    }

    void processBannerImage() throws MalformedURLException {
        FileChooser imageFileChooser = new FileChooser();

        // SET THE STARTING DIRECTORY
        imageFileChooser.setInitialDirectory(new File("./images/"));

        // LET'S ONLY SEE IMAGE FILES
        FileChooser.ExtensionFilter jpgFilter = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG");
        FileChooser.ExtensionFilter pngFilter = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.PNG");
        FileChooser.ExtensionFilter gifFilter = new FileChooser.ExtensionFilter("GIF files (*.gif)", "*.GIF");
        imageFileChooser.getExtensionFilters().addAll(jpgFilter, pngFilter, gifFilter);

        // LET'S OPEN THE FILE CHOOSER
        File file = imageFileChooser.showOpenDialog(null);
        if (file != null) {
            String path = file.getPath().substring(0, file.getPath().indexOf(file.getName()));
            String fileName = file.getName();
            ui.bannerImageText.setText(path + fileName);
            ui.setBannerImage(file);
            /*slideToEdit.setImage(path, fileName);
            view.updateSlideImage();
            ui.updateFileToolbarControls(false);*/
        }else{
            ui.bannerImageText.setText("No Banner Image");
        }
    }
    
    
    private class ImageParameters{
        String caption;
        int height;
        int width;
        int allighnment;
        
        private ImageParameters(String text, String text0, String text1, int radioSelection) {
            this.caption = text;
            this.height = Integer.parseInt(text0);
            this.width = Integer.parseInt(text1);
            this.allighnment = radioSelection;
        }
        
    }
    
}