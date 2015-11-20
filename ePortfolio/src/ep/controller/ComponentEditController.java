/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ep.controller;

import ep.view.EPortfolioView;

/**
 *
 * @author linti
 */
public class ComponentEditController {
    private ImageSelectionController imagesel;
    private VideoSelectionController videoSel;
    private EPortfolioView ui;

    public void processAddTextComponent() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
   
    
}
