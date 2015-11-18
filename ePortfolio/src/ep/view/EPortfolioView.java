/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ep.view;

import ep.file.EPortfolioFileManager;
import ep.file.EPortfolioSiteExporter;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Separator;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 *
 * @author linti
 */
public class EPortfolioView {
        // THIS IS THE MAIN APPLICATION UI WINDOW AND ITS SCENE GRAPH
    Stage primaryStage;
    Scene primaryScene;
    
    // Placeholder Icon files
    public static String PATH_IMAGES = "./images/";
    public static String PATH_ICONS = PATH_IMAGES + "icons/";
    public static String PATH_ICONS_PAGESELECTION = PATH_ICONS + "page/";
    public static String PATH_CSS = "/ep/style/";
    public static String STYLE_SHEET_UI = PATH_CSS + "EPortfolioStyle.css";
    
    
    public static String ICON_NEW_SLIDE_SHOW = "New.png";
    public static String ICON_LOAD_SLIDE_SHOW = "Load.png";
    public static String ICON_SAVE_SLIDE_SHOW = "Save.png";
    public static String ICON_VIEW_SLIDE_SHOW = "View.png";
    public static String ICON_EXIT = "Exit.png";
        public static String ICON_ADD_SLIDE = "Add.png";
    public static String ICON_REMOVE_SLIDE = "Remove.png";
    
    
    
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

    }
    public void startUI(Stage initPrimaryStage, String windowTitle){
        initFileToolbar();
        initSiteToolbarPane();
        initPageEditorWorkspaceToolbar();
        initWorkspace();
        setLayoutHierarchy();
        initTabBar();
        
        initPaneCSS();
        
        primaryStage = initPrimaryStage;
        initWindow(windowTitle);
    }
    
    private void initWorkspace() {
        // NOW PUT THESE TWO IN THE WORKSPACE
        PageContainerVBox = new VBox();
    }
    
    private void initPaneCSS() {
        // SETUP ALL THE STYLE CLASSES
        PageContainerVBox.getStyleClass().add("workspace");
        pageEditorWorkspaceToolbar.getStyleClass().add("vertical_toolbar_pane");
        siteToolbarPane.getStyleClass().add("horizontal_toolbar_pane");
    }

    private void initWindow(String windowTitle) {
	// SET THE WINDOW TITLE
	primaryStage.setTitle(windowTitle);

	// GET THE SIZE OF THE SCREEN
	Screen screen = Screen.getPrimary();
	Rectangle2D bounds = screen.getVisualBounds();

	// AND USE IT TO SIZE THE WINDOW
	primaryStage.setX(bounds.getMinX());
	primaryStage.setY(bounds.getMinY());
	primaryStage.setWidth(bounds.getWidth());
	primaryStage.setHeight(bounds.getHeight());

        // SETUP THE UI, NOTE WE'LL ADD THE WORKSPACE LATER
	epPane = new BorderPane();
	//epPane.getStyleClass().add(CSS_CLASS_WORKSPACE);
	epPane.setTop(epPaneTopSegment_Toolbars);
                  epPane.setCenter(workspaceModeToolbar);
	primaryScene = new Scene(epPane);
	
        // NOW TIE THE SCENE TO THE WINDOW, SELECT THE STYLESHEET
	// WE'LL USE TO STYLIZE OUR GUI CONTROLS, AND OPEN THE WINDOW
	primaryScene.getStylesheets().add(STYLE_SHEET_UI);
	primaryStage.setScene(primaryScene);
	primaryStage.show();
    }
     
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
    }
    
    private void initFileToolbar(){
        fileToolbarPane = new FlowPane();
        newEPButton = initChildButton(fileToolbarPane, ICON_NEW_SLIDE_SHOW,	"New ePortfolio",	    "horizontal_toolbar_button", false);
        loadEPButton = initChildButton(fileToolbarPane, ICON_LOAD_SLIDE_SHOW,	"Load ePortfolio",    "horizontal_toolbar_button", false);
        saveEPButton = initChildButton(fileToolbarPane, ICON_SAVE_SLIDE_SHOW,	"Save ePortfolio",    "horizontal_toolbar_button", true);
        saveAsEPButton = initChildButton(fileToolbarPane, ICON_VIEW_SLIDE_SHOW,	"Save ePortfolio As...",    "horizontal_toolbar_button", true);
        exportEPButton = initChildButton(fileToolbarPane, ICON_VIEW_SLIDE_SHOW,	"Generate ePortfolio site",    "horizontal_toolbar_button", true);
        exitButton = initChildButton(fileToolbarPane, ICON_EXIT, "Exit Application", "horizontal_toolbar_button", false);
    }
    
    private void initSiteToolbarPane(){
        siteToolbarPane = new FlowPane();
        addPageButton = initChildButton(siteToolbarPane, "page/add.png",	"Add page",    "horizontal_toolbar_button", false);
        removePageButton = initChildButton(siteToolbarPane, "page/remove.png",	"Remove page",	    "horizontal_toolbar_button", true);
        selectPageButton = initChildButton(siteToolbarPane, "page/select.png",	"Select page",    "horizontal_toolbar_button", true);
    }
    
    private void initPageEditorWorkspaceToolbar(){
        pageEditorWorkspaceToolbar = new FlowPane();
        addTextComponentButton = initChildButton(pageEditorWorkspaceToolbar, "workspace/addtext.png",	"Add text component",    "vertical_toolbar_button", false);
        addImageComponentButton = initChildButton(pageEditorWorkspaceToolbar, "workspace/addimage.png",	"Add image component",	    "vertical_toolbar_button", false);
        addVideoComponentButton = initChildButton(pageEditorWorkspaceToolbar, "workspace/addvideo.png",	"Add video component",    "vertical_toolbar_button", false);
        addSlideshowComponentButton = initChildButton(pageEditorWorkspaceToolbar, "workspace/addslideshow.png",	"Add a slideshow",    "vertical_toolbar_button", false);
        editComponentButton = initChildButton(pageEditorWorkspaceToolbar, "workspace/editcomponent.png",	"Edit selected component",    "vertical_toolbar_button", false);
        removeComponentButton = initChildButton(pageEditorWorkspaceToolbar,  "workspace/remove.png",	"Remove selected component",    "vertical_toolbar_button", false);
        
        Separator separator = new Separator();
        separator.setMaxWidth(80);
        pageEditorWorkspaceToolbar.getChildren().add(separator);
        
        selectLayoutButton = initChildButton(pageEditorWorkspaceToolbar, "workspace/selectlayout.png",	"Select the layout of your portfolio",    "vertical_toolbar_button", false);
        selectColorButton = initChildButton(pageEditorWorkspaceToolbar, "workspace/selectcolor.png",	"Select the color scheme for your portfolio",    "vertical_toolbar_button", false);
    }
    
    private void initTabBar(){
        workspaceModeToolbar = new TabPane();
        
        selectPageEditorWorkspaceTab = new Tab();
        selectSiteViewerWorkspace = new Tab();
        
        selectPageEditorWorkspaceTab.setText("Page Editor Workspace");
        selectSiteViewerWorkspace.setText("Site Viewer Workspace");
        
        selectPageEditorWorkspaceTab.setContent(epPaneCenterSegment_Content);
        //selectSiteViewerWorkspace.setContent(SOMETHINGWEBVIEWER);
                
        workspaceModeToolbar.getTabs().add(selectPageEditorWorkspaceTab);
        workspaceModeToolbar.getTabs().add(selectSiteViewerWorkspace);
    }
    
    public Button initChildButton(Pane toolbar, String iconFileName, String tooltip, String cssClass, boolean disabled) {
        
	String imagePath = "file:" + PATH_ICONS + iconFileName;
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
