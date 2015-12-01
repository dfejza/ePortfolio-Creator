/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ep.view;

import static ep.controller.ComponentEditController.STYLE_SHEET_UI;
import ep.model.Component;
import static ep.view.EPortfolioView.PATH_ICONS;
import java.util.Optional;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

/**
 *
 * @author linti
 */
public class StyleSelect {
    String selection;
        public String selectedLayout() {
        selection = "style1.png";
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Layout Selection");
        dialog.setHeaderText("Layout Selection");
        
        HBox hBox = new HBox();
        ScrollPane bigBox = new ScrollPane(hBox);
        VBox choice1 = new VBox();
        VBox choice2 = new VBox();
        VBox choice3 = new VBox();
        VBox choice4 = new VBox();
        VBox choice5 = new VBox();
        
        Button button1 = initChildButton(choice1,"./images/icons/style/style1.png","Navigation bar on the left, content centered.", "horizontal_toolbar_button", false);
        Button button2 = initChildButton(choice2,"./images/icons/style/style2.png","Centered focus with navigation bar top most.", "horizontal_toolbar_button", false);
        Button button3 = initChildButton(choice3,"./images/icons/style/style3.png","Centered focus with navigation bar between student name and content.", "horizontal_toolbar_button", false);
        Button button4 = initChildButton(choice4,"./images/icons/style/style4.png","Centered focus with navigation bar between banner image and student name.", "horizontal_toolbar_button", false);
        Button button5 = initChildButton(choice5,"./images/icons/style/style5.png","Centered focus with navigation bar at the bottom.", "horizontal_toolbar_button", false);
        hBox.getChildren().addAll(choice1,choice2,choice3,choice4,choice5);
        
        
        button1.setOnAction((ActionEvent e) -> {
            selection = "style1.png";
            dialog.setResult(selection);
        });
        button2.setOnAction((ActionEvent e) -> {
            selection = "style2.png";
            dialog.setResult(selection);
        });
        button3.setOnAction((ActionEvent e) -> {
            selection = "style3.png";
            dialog.setResult(selection);
        });
        button4.setOnAction((ActionEvent e) -> {
            selection = "style4.png";
            dialog.setResult(selection);
        });
        button5.setOnAction((ActionEvent e) -> {
            selection = "style5.png";
            dialog.setResult(selection);
        });
        
        dialog.getDialogPane().setContent(bigBox);
        Optional<String> result = dialog.showAndWait();
        return selection;
    }
        
        public String selectedColorTheme() {
        selection = "color1.png";
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Layout Selection");
        dialog.setHeaderText("Layout Selection");
        
        HBox hBox = new HBox();
        ScrollPane bigBox = new ScrollPane(hBox);
        VBox choice1 = new VBox();
        VBox choice2 = new VBox();
        VBox choice3 = new VBox();
        VBox choice4 = new VBox();
        VBox choice5 = new VBox();
        
        Button button1 = initChildButton(choice1,"./images/icons/color/color1.png","Dark colors.", "horizontal_toolbar_button", false);
        Button button2 = initChildButton(choice2,"./images/icons/color/color2.png","Shades of red.", "horizontal_toolbar_button", false);
        Button button3 = initChildButton(choice3,"./images/icons/color/color3.png","Shades of green.", "horizontal_toolbar_button", false);
        Button button4 = initChildButton(choice4,"./images/icons/color/color4.png","Shades of blue.", "horizontal_toolbar_button", false);
        Button button5 = initChildButton(choice5,"./images/icons/color/color5.png","Shades of orange.", "horizontal_toolbar_button", false);
        hBox.getChildren().addAll(choice1,choice2,choice3,choice4,choice5);
        
        
        button1.setOnAction((ActionEvent e) -> {
            selection = "color1.png";
            dialog.setResult(selection);
        });
        button2.setOnAction((ActionEvent e) -> {
            selection = "color2.png";
            dialog.setResult(selection);
        });
        button3.setOnAction((ActionEvent e) -> {
            selection = "color3.png";
            dialog.setResult(selection);
        });
        button4.setOnAction((ActionEvent e) -> {
            selection = "color4.png";
            dialog.setResult(selection);
        });
        button5.setOnAction((ActionEvent e) -> {
            selection = "color5.png";
            dialog.setResult(selection);
        });
        
        dialog.getDialogPane().setContent(bigBox);
        Optional<String> result = dialog.showAndWait();
        return selection;
    }
        
        
    public Button initChildButton(VBox toolbar, String iconFileName, String tooltip, String cssClass, boolean disabled) {
        
        String imagePath = "file:" + iconFileName;
        Image buttonImage = new Image(imagePath) {};
        Button button = new Button();
        button.getStyleClass().add(cssClass);
        button.setDisable(disabled);
        button.setGraphic(new ImageView(buttonImage));
        Tooltip buttonTooltip = new Tooltip(tooltip);
        button.setTooltip(buttonTooltip);
        toolbar.getChildren().add(button);
        return button;
    }
    
}
