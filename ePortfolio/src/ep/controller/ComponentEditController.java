/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ep.controller;

import ep.model.Component;
import ep.model.ssm.SlideShowMakerView;
import ep.view.EPortfolioView;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;

/**
 *
 * @author linti
 */
public class ComponentEditController {
        public static String PATH_CSS = "/ep/style/";
    public static String STYLE_SHEET_UI = PATH_CSS + "EPortfolioStyle.css";
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
            //saveComponent();
            ui.getPages().getSelectedpageObject().addComponent(currentComponent);
            ui.reloadCurrentPage();
        }else if(componentChoice.compareTo("Paragraph")==0){
            drawParagraphCreate();
            //saveComponent();
            ui.getPages().getSelectedpageObject().addComponent(currentComponent);
            ui.reloadCurrentPage();
        }else if (componentChoice.compareTo("List")==0){
            drawListCreate();
            //saveComponent();
            ui.getPages().getSelectedpageObject().addComponent(currentComponent);
            ui.reloadCurrentPage();
        }else{
            
        }
    }

    public void processAddImageComponent() {
        currentComponent = new Component();
        imagesel.processSelectImage(currentComponent);
        imagesel.askImageParameters(currentComponent);
        ui.getPages().getSelectedpageObject().addComponent(currentComponent);
        ui.reloadCurrentPage();
        //imagesel.getImageCaption();
        //imagesel.getImageHeight();
        //imagesel.getImageWidth();
        //imagesel.getAllighnment();
    }

    public void processAddSlideshowComponent() {
        SlideShowMakerView ssmui = new SlideShowMakerView();
        Stage stage = new Stage();
        stage.initStyle(StageStyle.UTILITY);
        String appTitle = "Slide Show Creater";
        ssmui.startUI(stage, appTitle);
    }

    public void processAddVideoComponent() {
        currentComponent = new Component();
        videoSel.processSelectVideo(currentComponent);
        videoSel.askVideoParameters(currentComponent);
        ui.getPages().getSelectedpageObject().addComponent(currentComponent);
        ui.reloadCurrentPage();
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

        
        DialogPane dialogPane = dialog.getDialogPane();
        dialogPane.getStylesheets().add(
           getClass().getResource(STYLE_SHEET_UI).toExternalForm());
        
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
    
    DialogPane dialogPane = dialog.getDialogPane();
    dialogPane.getStylesheets().add(
    getClass().getResource(STYLE_SHEET_UI).toExternalForm());
        
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
        dialog.getDialogPane().setPrefSize(980, 320);
                DialogPane dialogPane = dialog.getDialogPane();
        dialogPane.getStylesheets().add(
           getClass().getResource(STYLE_SHEET_UI).toExternalForm());
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
        BorderPane hyperLinkPane = new BorderPane();
        GridPane grid = new GridPane();

        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(1, 150, 20, 1));
        body.setHgap(10);
        body.setVgap(10);
        
        grid.add(new Label("Font:"), 0, 0);
        grid.add(comboBox, 1, 0);
        
        Button addToList = new Button();
        Button removeFromList = new Button();
        addToList.setText("Add Hyperlink");
        removeFromList.setText("Remove Hyperlink");
        TextField listConents = new TextField();
        
        ListView<String> list = new ListView<String>();
        ObservableList<String> items =FXCollections.observableArrayList ();
        list.setItems(items);
        list.setPrefWidth(320);
        list.setPrefHeight(300);
        addToList.setDisable(true);
        removeFromList.setDisable(true);
        
        wrapper.addEventFilter(MouseEvent.MOUSE_RELEASED, (MouseEvent mouseEvent) -> {
            if(paragraphText.getSelectedText().length()>0){
                addToList.setDisable(false);
            }else{
                addToList.setDisable(true);
            }
        });

        
        addToList.setOnAction(e -> {
            items.add(paragraphText.getSelectedText());
            String hyperlink = getHyperlinkDialogue();
            removeFromList.setDisable(false);
        });
        removeFromList.setOnAction(e -> {
            list.getItems().remove(list.getSelectionModel().getSelectedItem());
            if(list.getItems().size()==0)
                removeFromList.setDisable(true);
        });
        hyperLinkPane.setTop(new FlowPane(addToList,removeFromList));
        hyperLinkPane.setCenter(list);
        //hyperLinkPane.setBottom(removeFromList);
        wrapper.setTop(grid);
        wrapper.setCenter(body);
         wrapper.setRight(hyperLinkPane);
        
        
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

    private void drawListCreate() {
        Dialog<Component> dialog = new Dialog<>();
        dialog.setTitle("List Component");
        dialog.setHeaderText("Enter the parameters for this list");
        dialog.setResizable(false);
        dialog.getDialogPane().setPrefSize(400, 320);
                DialogPane dialogPane = dialog.getDialogPane();
        dialogPane.getStylesheets().add(
           getClass().getResource(STYLE_SHEET_UI).toExternalForm());
        BorderPane wrapper = new BorderPane();
        FlowPane body = new FlowPane();
        FlowPane grid = new FlowPane();

        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(10, 1, 10, 1));
        body.setHgap(10);
        body.setVgap(10);
        
        TextField listConents = new TextField();
        listConents.setPromptText("Enter list item here!");
        listConents.setPrefWidth(360);
        Button addToList = new Button();
        Button removeFromList = new Button();
        addToList.setText("Add To List");
        removeFromList.setText("Remove Selected");
        grid.getChildren().add(new Label("List Text:"));
        grid.getChildren().add(listConents);
        grid.getChildren().add(addToList);
        grid.getChildren().add(removeFromList);
        
        ListView<String> list = new ListView<String>();
        ObservableList<String> items =FXCollections.observableArrayList ();
        list.setItems(items);
        list.setPrefWidth(420);
        list.setPrefHeight(200);
        removeFromList.setDisable(true);
       body.getChildren().add(list);
        addToList.setOnAction(e -> {
            items.add(listConents.getText());
            listConents.setText("");
            removeFromList.setDisable(false);
        });
        removeFromList.setOnAction(e -> {
            list.getItems().remove(list.getSelectionModel().getSelectedItem());
            if(list.getItems().size()==0)
                removeFromList.setDisable(true);
        });
        wrapper.setTop(grid);
        wrapper.setCenter(body);
        

        
        dialog.getDialogPane().setContent(wrapper);
        ButtonType buttonTypeOk = new ButtonType("Okay", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().add(buttonTypeOk);

        dialog.setResultConverter((ButtonType b) -> {
            if (b == buttonTypeOk) {
                int elementCount = items.size();
                String[] listElements = new String[elementCount];
                for(int i = 0; i<elementCount;i++){
                    listElements[i] = items.get((i));
                }
                currentComponent.listComponent(listElements,elementCount);
            }
            return null;
        });
        Optional<Component> result = dialog.showAndWait();
    }

    private String getHyperlinkDialogue() {
    TextInputDialog dialog = new TextInputDialog("www.google.com");
    dialog.getDialogPane().setPrefSize(400, 320);
     DialogPane dialogPane = dialog.getDialogPane();
     dialogPane.getStylesheets().add(
    getClass().getResource(STYLE_SHEET_UI).toExternalForm());
    dialog.setTitle("Hyperlink Dialog");
    dialog.setHeaderText("Enter a Hyperlink for your selected text.");
    dialog.setContentText("HTTP Address:");

    // Traditional way to get the response value.
    Optional<String> result = dialog.showAndWait();
    if (result.isPresent()){
        return result.get();
    }
    return null;
    }
}
