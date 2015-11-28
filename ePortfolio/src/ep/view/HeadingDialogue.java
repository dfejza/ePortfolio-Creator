/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package ep.view;

import static ep.controller.ComponentEditController.STYLE_SHEET_UI;
import ep.model.Component;
import java.util.Optional;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextInputDialog;

/**
 *
 * @author dardan
 */
public class HeadingDialogue {
    
    public Component drawHeadingCreate() {
        Component currentComponent = new Component();
        TextInputDialog dialog = new TextInputDialog("Heading Goes Here");
        dialog.setTitle("Heading Input Dialog");
        dialog.setHeaderText("Heading Dialog");
        dialog.setContentText("Enter the heading:");
        
        DialogPane dialogPane = dialog.getDialogPane();
        dialogPane.getStylesheets().add(
                getClass().getResource(STYLE_SHEET_UI).toExternalForm());
        
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()){
            currentComponent.headingComponent(result.get());
            return currentComponent;
        }
        return currentComponent;
    }
}
