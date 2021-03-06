/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package ep.view;

import static ep.controller.ComponentEditController.STYLE_SHEET_UI;
import ep.model.Component;
import java.util.Optional;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.IndexRange;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;

/**
 *
 * @author dardan
 */
public class ParagraphDialogue {
    public Component drawParagraphCreate() {
        Component currentComponent = new Component();
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
                        "Alegreya Sans",
                        "Lato",
                        "Indie Flower",
                        "Titillium Web",
                        "Sigmar One"
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
        ;
        
        Button addToList = new Button();
        addToList.setText("Add Hyperlink");
        grid.add(addToList, 2, 0);
        addToList.setDisable(true);
        
        wrapper.addEventFilter(MouseEvent.MOUSE_RELEASED, (MouseEvent mouseEvent) -> {
            if(paragraphText.getSelectedText().length()>0){
                addToList.setDisable(false);
            }else{
                addToList.setDisable(true);
            }
        });
        
        
        addToList.setOnAction(e -> {
            String hyperlink = getHyperlinkDialogue();
            String selectedText = paragraphText.getSelectedText();
            String newNext = "[url=" +"http://" +hyperlink + "]" + selectedText + "[/url]";
            IndexRange selection = paragraphText.getSelection();
            String content = paragraphText.getText();
            String newString = content.substring(0, selection.getStart()) +newNext+ content.substring(selection.getEnd());           
            paragraphText.setText(newString);
            
        });
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
                return currentComponent;
            }
            return null;
        });
        
        Optional<Component> result = dialog.showAndWait();
        return currentComponent;
    }
    
    public void editParagraphCreate(Component currentComponent) {
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
                        "Alegreya Sans",
                        "Lato",
                        "Indie Flower",
                        "Titillium Web",
                        "Sigmar One"
                );
        final ComboBox comboBox = new ComboBox(options);
        comboBox.getSelectionModel().select(1);
        TextArea paragraphText = new TextArea(currentComponent.getText());
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
        addToList.setText("Add Hyperlink");
        grid.add(addToList, 2, 0);
        addToList.setDisable(true);
        
        wrapper.addEventFilter(MouseEvent.MOUSE_RELEASED, (MouseEvent mouseEvent) -> {
            if(paragraphText.getSelectedText().length()>0){
                addToList.setDisable(false);
            }else{
                addToList.setDisable(true);
            }
        });
        
        
        addToList.setOnAction(e -> {
            String hyperlink = getHyperlinkDialogue();
            String selectedText = paragraphText.getSelectedText();
            String newNext = "[url=" +"http://" +hyperlink + "]" + selectedText + "[/url]";
            IndexRange selection = paragraphText.getSelection();
            String content = paragraphText.getText();
            String newString = content.substring(0, selection.getStart()) +newNext+ content.substring(selection.getEnd());           
            paragraphText.setText(newString);
            
        });
        
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
