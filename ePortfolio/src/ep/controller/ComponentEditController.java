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
import ep.view.StyleSelect;
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
    private final StyleSelect styleSelect;
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
        currentComponent = new Component();
        SlideShowMakerView ssmui = new SlideShowMakerView(currentComponent,this);
        Stage stage = new Stage();
        stage.initStyle(StageStyle.UTILITY);
        String appTitle = "Slide Show Creator";
        ssmui.startUI(stage, appTitle);
        
    }
    
    public void processAddVideoComponent() {
        currentComponent = new Component();
        videoSel.processSelectVideo(currentComponent);
        videoSel.askVideoParameters(currentComponent);
        ui.getPages().getSelectedpageObject().addComponent(currentComponent);
        ui.reloadCurrentPage();
    }
    
    public void reloadComponents(){
        ui.getPages().getSelectedpageObject().addComponent(currentComponent);
        ui.reloadCurrentPage();
    }
    public void reload(){
        ui.reloadCurrentPage();
    }
    
    public void processEditComponent() {
        int type = 0;
        ui.markAsNotSaved();
        Component editedComponet = ui.getPages().getSelectedpageObject().getSelectedComponent();
        type = editedComponet.fetchComponentType();
        switch (type) {
            case 1:
                //body
                pDialogue.editParagraphCreate(editedComponet);
                break;
            case 2:
                imagesel.processSelectImage(editedComponet);
                imagesel.askImageParameters(editedComponet);
                break;
            case 3:
                videoSel.processSelectVideo(editedComponet);
                videoSel.askVideoParameters(editedComponet);
                break;
            case 4:
                SlideShowMakerView ssmui = new SlideShowMakerView(editedComponet,this);
                Stage stage = new Stage();
                stage.initStyle(StageStyle.UTILITY);
                String appTitle = "Slide Show Creator";
                ssmui.startUI(stage, appTitle);
                break;
            case 6:
                //list
                lDialogue.editListCreate(editedComponet);
                break;
            case 9:
                //header
                hDialogue.editHeadingCreate(editedComponet);
                break;
            default:
                break;
        }
        
        ui.reloadCurrentPage();
        
    }
    
    public void processRemoveComponent() {
        ui.getPages().getSelectedpageObject().removeSelectedComponent();
        ui.reloadCurrentPage();
        if(ui.getPages().getSelectedpageObject().getComponents().isEmpty() || ui.getPages().getSelectedpageObject().getSelectedComponent()==null){
            ui.disableEditButton();
        }
    }
    
    public void selectLayout() {
        String layoutChoice = styleSelect.selectedLayout();
        ui.setLayout(layoutChoice);
    }
    
    public void selectColor() {
        String colorTheme = styleSelect.selectedColorTheme();
        ui.setColortheme(colorTheme);
    }
    
    
    public ComponentEditController(EPortfolioView initUI) {
        ui = initUI;
        imagesel = new ImageSelectionController(ui);
        videoSel = new VideoSelectionController(ui);
        hDialogue = new HeadingDialogue();
        lDialogue = new ListDialogue();
        pDialogue = new ParagraphDialogue();
        cSelect = new ComponentSelect();
        styleSelect = new StyleSelect();
        
    }
    
    public void selectBannerImage() throws MalformedURLException {
        imagesel.processBannerImage();
    }

}
