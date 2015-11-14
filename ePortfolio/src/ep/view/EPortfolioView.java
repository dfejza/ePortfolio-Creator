/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ep.view;

import ep.file.EPortfolioFileManager;
import ep.file.EPortfolioSiteExporter;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author linti
 */
public class EPortfolioView {
        // THIS IS THE MAIN APPLICATION UI WINDOW AND ITS SCENE GRAPH
    Stage primaryStage;
    Scene primaryScene;
    
    // THIS PANE ORGANIZES THE BIG PICTURE CONTAINERS FOR THE
    // APPLICATION GUI
    BorderPane epPane;
    
    // THIS IS THE TOP TOOLBAR AND ITS CONTROLS
    FlowPane fileToolbarPane;
    Button newEPButton;
    Button loadEPButton;
    Button saveEPButton;
    Button saveAsEPButton;
    Button exportEPButton;
    Button exitButton;
    
    // this is the Workspace toolbar/tabPane and its controls
    TabPane workspaceModeToolbar;
    Tab selectPageEditorWorkspaceTab;
    Tab selectSiteViewerWorkspace;
    
    // This pane holds both FileToolbarPan and Tab Pane, and is ment to be in the top portion of epPane.
    BorderPane epPaneTopSegment_Toolbars;
    
    // This toolbar privides buttons allowing user to place/edit components
    FlowPane pageEditorWorkspaceToolbar;
    Button selectLayoutButton;
    Button selectColorButton;
    Button selectBannerImageButton;
    Button chooseComponentFontButton;
    //textbox for page title and student name
    Button addTextComponentButton;
    Button addImageComponentButton;
    Button addSlideshowComponentButton;
    Button addVideoComponentButton;
    Button addHyperlinkComponentButton;
    Button removeComponentButton;
    Button editComponentButton;
    
    // THIS IS THE PAGE TOOLBAR AND ITS CONTROLS
    FlowPane siteToolbarPane;
    Button removePageButton;
    Button selectPageButton;
    Button addPageButton;
    
    // This holds the page's components (its a page)
    VBox PageContainerVBox;
    
    // This pane holds the siteToolbarPane and the current page being viewed. Only meant to be displayed in the page editor view.
    // SiteToolbarPane should be to the left, and pageVBox should be center.
    BorderPane epPaneCenterSegment_Content;
    
    // Creates the intended layout schemes of major panes. This is meant to be called AFTER all the children panels are constructed and 
    // before the main window is constructed.
    private void setLayoutHierarchy(){
        epPaneTopSegment_Toolbars = new BorderPane();
        epPaneTopSegment_Toolbars.setTop(fileToolbarPane);
        epPaneTopSegment_Toolbars.setBottom(workspaceModeToolbar);
        
        epPaneCenterSegment_Content = new BorderPane();
        epPaneCenterSegment_Content.setTop(siteToolbarPane);
        epPaneCenterSegment_Content.setLeft(pageEditorWorkspaceToolbar);
        epPaneCenterSegment_Content.setCenter(PageContainerVBox);
        
        epPane = new BorderPane();
        epPane.setTop(epPaneTopSegment_Toolbars);
        epPane.setCenter(epPaneCenterSegment_Content);
    }
    

    
    
    
    
    
    
    
    
    
    // THIS IS FOR SAVING AND LOADING SLIDE SHOWS
     EPortfolioFileManager fileManager;
    
    // THIS IS FOR EXPORTING THE SLIDESHOW SITE
     EPortfolioSiteExporter siteExporter;

    // THIS CLASS WILL HANDLE ALL ERRORS FOR THIS PROGRAM
    //private ErrorHandler errorHandler;

    // THIS CONTROLLER WILL ROUTE THE PROPER RESPONSES
    // ASSOCIATED WITH THE FILE TOOLBAR
    //private FileController fileController;
    
    // THIS CONTROLLER RESPONDS TO SLIDE SHOW EDIT BUTTONS
    //private SlideShowEditController editController;
    
     public EPortfolioView(EPortfolioFileManager initFileManager, EPortfolioSiteExporter initSiteExporter) {
        // FIRST HOLD ONTO THE FILE MANAGER
        fileManager = initFileManager;

        // AND THE SITE EXPORTER
        siteExporter = initSiteExporter;

        // MAKE THE DATA MANAGING MODEL
        //slideShow = new SlideShowModel(this);

        // WE'LL USE THIS ERROR HANDLER WHEN SOMETHING GOES WRONG
        //errorHandler = new ErrorHandler(this);
        setLayoutHierarchy();
    }
}
