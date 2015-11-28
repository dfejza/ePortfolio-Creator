/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ep.view;

import static ep.controller.ComponentEditController.STYLE_SHEET_UI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.DialogPane;

/**
 *
 * @author dardan
 */
public class ComponentSelect {
public String drawComponentSelect() {
        String componentChoice = "";
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
        return componentChoice;
    }
}
