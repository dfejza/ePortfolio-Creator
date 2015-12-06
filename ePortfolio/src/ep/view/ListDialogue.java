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
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;

/**
 *
 * @author dardan
 */
public class ListDialogue {
    public Component drawListCreate() {
        Component currentComponent = new Component();
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
        
        Button addHyper = new Button();
        addHyper.setText("Add Hyperlink");
        addHyper.setDisable(true);
        
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
        grid.getChildren().add(addHyper);
        
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
            addHyper.setDisable(false);
        });
        removeFromList.setOnAction(e -> {
            list.getItems().remove(list.getSelectionModel().getSelectedItem());
            if(list.getItems().size()==0)
                removeFromList.setDisable(true);
        });
        
        addHyper.setOnAction(e -> {
            String hyperlink = getHyperlinkDialogue();
            String selectedText = list.getSelectionModel().getSelectedItem();
            String newNext = "[url=" +"http://" +hyperlink + "]" + selectedText + "[/url]";
            list.getItems().set(list.getSelectionModel().getSelectedIndex(), newNext);
            if(list.getItems().size()==0)
                addToList.setDisable(true);
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
                return currentComponent;
            }
            return currentComponent;
        });
        Optional<Component> result = dialog.showAndWait();
        return currentComponent;
    }
    
    public void editListCreate(Component currentComponent) {
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
        Button addHyper = new Button();
        addHyper.setText("Add Hyperlink");
        addHyper.setDisable(false);
        grid.getChildren().add(addHyper);
        grid.getChildren().add(removeFromList);
        
        ListView<String> list = new ListView<String>();
        ObservableList<String> items =FXCollections.observableArrayList ();
        
        String[] listData = currentComponent.getListData();
        for (String listData1 : listData) {
            items.add(listData1);
        }
        
        list.setItems(items);
        list.setPrefWidth(420);
        list.setPrefHeight(200);
        removeFromList.setDisable(false);
        body.getChildren().add(list);
        addToList.setOnAction(e -> {
            items.add(listConents.getText());
            listConents.setText("");
            removeFromList.setDisable(false);
            addHyper.setDisable(false);
        });
        removeFromList.setOnAction(e -> {
            list.getItems().remove(list.getSelectionModel().getSelectedItem());
            if(list.getItems().size()==0)
                removeFromList.setDisable(true);
        });
        
        addHyper.setOnAction(e -> {
            String hyperlink = getHyperlinkDialogue();
            String selectedText = list.getSelectionModel().getSelectedItem();
            String newNext = "[url=" +"http://" +hyperlink + "]" + selectedText + "[/url]";
            list.getItems().set(list.getSelectionModel().getSelectedIndex(), newNext);
            if(list.getItems().size()==0)
                addToList.setDisable(true);
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
