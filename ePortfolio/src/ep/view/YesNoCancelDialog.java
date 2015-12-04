/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package ep.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author linti
 */

public class YesNoCancelDialog extends Stage {
    // GUI CONTROLS FOR OUR DIALOG
    VBox messagePane;
    Scene messageScene;
    Label messageLabel;
    Button yesButton;
    Button noButton;
    Button cancelButton;
    String selection;
    // CSS - FOR THE LANGUAGE SELECTION DIALOG
    public static String    CSS_CLASS_LANG_DIALOG_PANE = "lang_dialog_pane";
    public static String    CSS_CLASS_LANG_PROMPT = "lang_prompt";
    public static String    CSS_CLASS_LANG_COMBO_BOX = "lang_combo_box";
    public static String    CSS_CLASS_LANG_OK_BUTTON = "lang_ok_button";
    public static String PATH_CSS = "/ep/style/";
    public static String STYLE_SHEET_UI = PATH_CSS + "EPortfolioStyle.css";
    // CONSTANT CHOICES
    public static final String YES = "Yes";
    public static final String NO = "No";
    public static final String CANCEL = "Cancel";
    
    /**
     * Initializes this dialog so that it can be used repeatedly
     * for all kinds of messages.
     *
     * @param primaryStage The owner of this modal dialog.
     */
    public YesNoCancelDialog(Stage primaryStage) {
        // MAKE THIS DIALOG MODAL, MEANING OTHERS WILL WAIT
        // FOR IT WHEN IT IS DISPLAYED
        initModality(Modality.WINDOW_MODAL);
        initOwner(primaryStage);
        
        // LABEL TO DISPLAY THE CUSTOM MESSAGE
        messageLabel = new Label();
        
        EventHandler yesNoCancelHandler = (EventHandler<ActionEvent>) (ActionEvent ae) -> {
            Button sourceButton = (Button)ae.getSource();
            YesNoCancelDialog.this.selection = sourceButton.getText();
            YesNoCancelDialog.this.hide();
        };
        
        // YES, NO, AND CANCEL BUTTONS
        yesButton = new Button(YES);
        noButton = new Button(NO);
        cancelButton = new Button(CANCEL);
        yesButton.setOnAction(yesNoCancelHandler);
        noButton.setOnAction(yesNoCancelHandler);
        cancelButton.setOnAction(yesNoCancelHandler);
        
        // NOW ORGANIZE OUR BUTTONS
        HBox buttonBox = new HBox();
        buttonBox.getChildren().add(yesButton);
        buttonBox.getChildren().add(noButton);
        buttonBox.getChildren().add(cancelButton);
        
        // WE'LL PUT EVERYTHING HERE
        messagePane = new VBox();
        messagePane.setAlignment(Pos.CENTER);
        messagePane.getChildren().add(messageLabel);
        messagePane.getChildren().add(buttonBox);
        
        // CSS CLASSES
        yesButton.getStyleClass().add(CSS_CLASS_LANG_OK_BUTTON);
        noButton.getStyleClass().add(CSS_CLASS_LANG_OK_BUTTON);
        cancelButton.getStyleClass().add(CSS_CLASS_LANG_OK_BUTTON);
        messageLabel.getStyleClass().add(CSS_CLASS_LANG_PROMPT);
        messagePane.getStyleClass().add(CSS_CLASS_LANG_DIALOG_PANE);
        buttonBox.getStyleClass().add(CSS_CLASS_LANG_DIALOG_PANE);
        
        // MAKE IT LOOK NICE
        messagePane.setPadding(new Insets(10, 20, 20, 20));
        messagePane.setSpacing(10);
        
        // AND PUT IT IN THE WINDOW
        messageScene = new Scene(messagePane);
        messageScene.getStylesheets().add(STYLE_SHEET_UI);
        this.setScene(messageScene);
    }
    
    /**
     * Accessor method for getting the selection the user made.
     *
     * @return Either YES, NO, or CANCEL, depending on which
     * button the user selected when this dialog was presented.
     */
    public String getSelection() {
        return selection;
    }
    
    /**
     * This method loads a custom message into the label and
     * then pops open the dialog.
     *
     * @param message Message to appear inside the dialog.
     */
    public void show(String message) {
        messageLabel.setText(message);
        this.showAndWait();
    }
}