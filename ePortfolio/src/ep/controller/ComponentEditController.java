/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ep.controller;

import ep.model.Component;
import ep.view.EPortfolioView;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;

/**
 *
 * @author linti
 */
public class ComponentEditController {
    private ImageSelectionController imagesel;
    private VideoSelectionController videoSel;
    private EPortfolioView ui;
    Component currentComponent;
    String componentChoice;
    
    public void processAddTextComponent() {
        currentComponent = new Component();
        drawComponentSelect();
        
        if(componentChoice.compareTo("Heading")==0){
            drawHeadingCreate();
            saveComponent();
        }else if(componentChoice.compareTo("Paragraph")==0){
            drawParagraphCreate();
            saveComponent();
        }else if (componentChoice.compareTo("List")==0){
            
        }else{
            
        }
    }

    public void processAddImageComponent() {
        imagesel.processSelectImage();
        imagesel.askImageParameters();
        //imagesel.getImageCaption();
        //imagesel.getImageHeight();
        //imagesel.getImageWidth();
        //imagesel.getAllighnment();
    }

    public void processAddSlideshowComponent() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void processAddVideoComponent() {
        videoSel.processSelectVideo();
        videoSel.askVideoParameters();
        //videoSel.getVideoCaption();
        //videoSel.getVideoHeight();
        //videoSel.getVideoWidth();
    }

    public void processEditComponent() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void processRemoveComponent() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void selectLayout() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void selectColor() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
   public ComponentEditController(EPortfolioView initUI) {
	ui = initUI;
                  imagesel = new ImageSelectionController(ui);
                  videoSel = new VideoSelectionController(ui);
                  
    }

    public void selectBannerImage() throws MalformedURLException {
        imagesel.processBannerImage();
    }

    
    
    private void drawComponentSelect() {
        List<String> choices = new ArrayList<>();
        choices.add("Heading");
        choices.add("Paragraph");
        choices.add("List");

        ChoiceDialog<String> dialog = new ChoiceDialog<>("Heading", choices);
        dialog.setTitle("Component Selection Dialog");
        dialog.setHeaderText("Select which type of text component to add");
        dialog.setContentText("Choose a type:");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()){
            componentChoice = result.get();
        }else{
            componentChoice = null;
        }
    }

    private void drawHeadingCreate() {
    TextInputDialog dialog = new TextInputDialog("Heading Goes Here");
    dialog.setTitle("Heading Input Dialog");
    dialog.setHeaderText("Heading Dialog");
    dialog.setContentText("Enter the heading:");
    Optional<String> result = dialog.showAndWait();
    if (result.isPresent())
       currentComponent.headingComponent(result.get());
    }

    private void saveComponent() {
        throw new UnsupportedOperationException("SAVE COMPONENT TO LIST HERE"); //To change body of generated methods, choose Tools | Templates.
    }

    private void drawParagraphCreate() {
        Dialog<Component> dialog = new Dialog<>();
        dialog.setTitle("Paragraph Component");
        dialog.setHeaderText("Enter the parameters for this paragraph");
        dialog.setResizable(true);
        dialog.getDialogPane().setPrefSize(480, 320);
        
        ObservableList<String> options = 
        FXCollections.observableArrayList(
            "Font 1",
            "Font 2",
            "Font 3",
            "Font 4",
            "Font 5"
        );
        final ComboBox comboBox = new ComboBox(options);
        comboBox.getSelectionModel().selectFirst();
        TextArea paragraphText = new TextArea();
        paragraphText.setWrapText(true);
        
        paragraphText.setPromptText("Enter body text here.");
        
        BorderPane wrapper = new BorderPane();
        FlowPane body = new FlowPane();
        GridPane grid = new GridPane();

        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(1, 150, 20, 1));
        body.setHgap(10);
        body.setVgap(10);
        
        grid.add(new Label("Font:"), 0, 0);
        grid.add(comboBox, 1, 0);
        
        wrapper.setTop(grid);
        wrapper.setCenter(body);
        
        
        body.getChildren().add(new Label("Body Text:"));
        body.getChildren().add(paragraphText);
        
         dialog.getDialogPane().setContent(wrapper);
        ButtonType buttonTypeOk = new ButtonType("Okay", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().add(buttonTypeOk);

        dialog.setResultConverter((ButtonType b) -> {
            if (b == buttonTypeOk) {
                currentComponent.bodyComponent(paragraphText.getText(),comboBox.getSelectionModel().getSelectedItem().toString());
            }
            return null;
        });
        
       Optional<Component> result = dialog.showAndWait();
       /*        if (result.isPresent()){
       //currentComponent.headingComponent(result.get());
       }*/
    }
}
