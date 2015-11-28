/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package ep.controller;

import ep.model.Component;
import ep.model.ssm.SlideShowMakerView;
import ep.view.ComponentSelect;
import ep.view.EPortfolioView;
import ep.view.HeadingDialogue;
import ep.view.ListDialogue;
import ep.view.ParagraphDialogue;
import java.net.MalformedURLException;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author linti
 */
public class ComponentEditController {
    public static String PATH_CSS = "/ep/style/";
    public static String STYLE_SHEET_UI = PATH_CSS + "EPortfolioStyle.css";
    private final ImageSelectionController imagesel;
    private final VideoSelectionController videoSel;
    private final ComponentSelect cSelect;
    private final HeadingDialogue hDialogue;
    private final ListDialogue lDialogue;
    private final ParagraphDialogue pDialogue;
    private final EPortfolioView ui;
    private Component currentComponent;
    private String componentChoice;
    
    public void processAddTextComponent() {
        currentComponent = new Component();
        componentChoice = cSelect.drawComponentSelect();
        
        if(componentChoice.compareTo("Heading")==0){
            currentComponent = hDialogue.drawHeadingCreate();
            //saveComponent();
            ui.getPages().getSelectedpageObject().addComponent(currentComponent);
            ui.reloadCurrentPage();
        }else if(componentChoice.compareTo("Paragraph")==0){
            currentComponent = pDialogue.drawParagraphCreate();
            //saveComponent();
            ui.getPages().getSelectedpageObject().addComponent(currentComponent);
            ui.reloadCurrentPage();
        }else if (componentChoice.compareTo("List")==0){
            currentComponent = lDialogue.drawListCreate();
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
    }
    
    public void processEditComponent() {
        int type = 0;
        type = ui.getPages().getSelectedpageObject().getSelectedComponent().fetchComponentType();
        
    }
    
    public void processRemoveComponent() {
        ui.getPages().getSelectedpageObject().removeSelectedComponent();
        ui.reloadCurrentPage();
        if(ui.getPages().getSelectedpageObject().getComponents().isEmpty() || ui.getPages().getSelectedpageObject().getSelectedComponent()==null){
            ui.disableEditButton();
        }
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
        hDialogue = new HeadingDialogue();
        lDialogue = new ListDialogue();
        pDialogue = new ParagraphDialogue();
        cSelect = new ComponentSelect();
        
    }
    
    public void selectBannerImage() throws MalformedURLException {
        imagesel.processBannerImage();
    }

}
