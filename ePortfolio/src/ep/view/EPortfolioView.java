/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package ep.view;

import ep.controller.ComponentEditController;
import ep.controller.FileController;
import ep.controller.PagesEditController;
import ep.file.EPortfolioFileManager;
import ep.file.EPortfolioSiteExporter;
import ep.model.Component;
import ep.model.Page;
import ep.model.PagesModel;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
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
    
    // this is to control the pages
    TabPane pageSelectionPane;
    
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
    BorderPane pageContainer;
    VBox PageContainerVBox;
    GridPane pageContainerPageSettings;
    
    // This pane holds the siteToolbarPane and the current page being viewed. Only meant to be displayed in the page editor view.
    // SiteToolbarPane should be to the left, and pageVBox should be center.
    BorderPane epPaneCenterSegment_Content;
    
    // THIS IS FOR SAVING AND LOADING SLIDE SHOWS
    EPortfolioFileManager fileManager;
    
    // THIS IS FOR EXPORTING THE SLIDESHOW SITE
    EPortfolioSiteExporter siteExporter;
    
    
    // THIS CLASS WILL HANDLE ALL ERRORS FOR THIS PROGRAM
    //private ErrorHandler errorHandler;
    // THIS IS THE SLIDE SHOW WE'RE WORKING WITH
    PagesModel pages;
    
    // THIS CONTROLLER WILL ROUTE THE PROPER RESPONSES
    // ASSOCIATED WITH THE FILE TOOLBAR
    private FileController fileController;
    
    // THIS CONTROLLER RESPONDS TO SLIDE SHOW EDIT BUTTONS
    private PagesEditController pagesEditController;
    private ComponentEditController componentEditController;
    private WebView webView;
    private ScrollPane scrollPane;
    private WebEngine webEngine;
    private TextField studentNameField;
    private TextField pageFooter;
    private TextField pageHeader;
    private TextField pageTitle;
    private String bannerImage;
    private String studentName;
    private String colorTheme;
    private String layoutTheme;
    public Label bannerImageText;
    private ComboBox pageFontChoice;
    private ScrollPane slidesEditorScrollPane;
    private boolean workspaceInitialized;
    private String jsonFilePath;
    
    public EPortfolioView(EPortfolioFileManager initFileManager, EPortfolioSiteExporter initSiteExporter) {
        // FIRST HOLD ONTO THE FILE MANAGER
        fileManager = initFileManager;
        
        // AND THE SITE EXPORTER
        siteExporter = initSiteExporter;
        
        // MAKE THE DATA MANAGING MODEL
        pages = new PagesModel(this);
        
        bannerImageText = new Label();
        // WE'LL USE THIS ERROR HANDLER WHEN SOMETHING GOES WRONG
        //errorHandler = new ErrorHandler(this);
        workspaceInitialized = false;
    }
    public void startUI(Stage initPrimaryStage, String windowTitle) throws MalformedURLException{
        initFileToolbar();
        initSiteToolbarPane();
        initPageEditorWorkspaceToolbar();
        initWorkspace();
        setLayoutHierarchy();
        //webView();
        initTabBar();
        initPaneCSS();
        initStrings();
        
        initEventHandlers();
        
        primaryStage = initPrimaryStage;
        initWindow(windowTitle);
    }
    
    private void initWorkspace() {
        pageContainer = new BorderPane();
        pageContainerPageSettings = new GridPane();
        pageSelectionPane = new TabPane();
        pageSelectionPane.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
        pageContainer.setTop(pageContainerPageSettings);
        pageContainer.setCenter(pageSelectionPane);
        exportEPButton.setDisable(true);
    }
    
    public void initPageInputs(){
                
        if(!workspaceInitialized){
            workspaceInitialized = true;
        bannerImageText.setText("No Banner Image");
        pageContainerPageSettings.setHgap(5.5);
        pageContainerPageSettings.setVgap(5.5);
        studentNameField = new TextField();
        pageTitle = new TextField();
        pageFooter = new TextField();
        pageHeader = new TextField();
        ObservableList<String> options =
                FXCollections.observableArrayList(
                        "Alegreya Sans",
                        "Lato",
                        "Indie Flower",
                        "Titillium Web",
                        "Sigmar One"
                );
        pageFontChoice = new ComboBox(options);
        pageFontChoice.getSelectionModel().selectFirst();
        //bannerImage = new ImageView();
        selectBannerImageButton = new Button();
        selectBannerImageButton.setText("Select Banner Image");
        pageContainerPageSettings.add(new Label("Student Name:"),0,0);
        pageContainerPageSettings.add(studentNameField,1,0);
        pageContainerPageSettings.add(new Label("Page Title:"),0,1);
        pageContainerPageSettings.add(pageTitle,1,1);
        pageContainerPageSettings.add(new Label("Page Header:"),2,1);
        pageContainerPageSettings.add(pageHeader,3,1);
        pageContainerPageSettings.add(new Label("Page Footer:"),4,1);
        pageContainerPageSettings.add(pageFooter,5,1);
        pageContainerPageSettings.add(new Label("Page Font:"),6,1);
        pageContainerPageSettings.add(pageFontChoice,7,1);
        //pageContainerPageSettings.add(new Label("Banner Image:"),0,3);
        pageContainerPageSettings.add(selectBannerImageButton,0,3);
        pageContainerPageSettings.add(bannerImageText,1,3);
        }
    }
    
    private void initPaneCSS() {
        // SETUP ALL THE STYLE CLASSES
        pageContainer.getStyleClass().add("workspace");
        pageEditorWorkspaceToolbar.getStyleClass().add("vertical_toolbar_pane");
        siteToolbarPane.getStyleClass().add("horizontal_toolbar_pane");
        fileToolbarPane.getStyleClass().add("horizontal_toolbar_pane_top");
        workspaceModeToolbar.getStyleClass().add("tab-pane");
        epPaneTopSegment_Toolbars.getStyleClass().add("tab-pane");
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
        //primaryStage.setWidth(bounds.getWidth());
        //primaryStage.setHeight(bounds.getHeight());
        primaryStage.setWidth(1920);
        primaryStage.setHeight(1080);
        
        // SETUP THE UI, NOTE WE'LL ADD THE WORKSPACE LATER
        epPane = new BorderPane();
        //epPane.getStyleClass().add(CSS_CLASS_WORKSPACE);
        epPane.setTop(epPaneTopSegment_Toolbars);
        epPane.setCenter(workspaceModeToolbar);
        primaryScene = new Scene(epPane);
        
        // NOW TIE THE SCENE TO THE WINDOW, SELECT THE STYLESHEET
        primaryStage.setTitle("ePortfolio Generator");
        // WE'LL USE TO STYLIZE OUR GUI CONTROLS, AND OPEN THE WINDOW
        primaryScene.getStylesheets().add(STYLE_SHEET_UI);
        epPane.getStyleClass().add("workspace");
        primaryStage.setScene(primaryScene);
        primaryStage.show();
    }
    
    private void initEventHandlers() {
        
        // FIRST THE FILE CONTROLS
        fileController = new FileController(this, fileManager);
        
        newEPButton.setOnAction(e -> {
            fileController.handleNewEPRequest();
        });
        loadEPButton.setOnAction(e -> {
            try {
                fileController.handleLoadEPRequest();
            } catch (IOException ex) {
                Logger.getLogger(EPortfolioView.class.getName()).log(Level.SEVERE, null, ex);
            }
            enableExportButton();
        });
        saveEPButton.setOnAction(e -> {
            try {
                fileController.handleSaveEPRequest();
            } catch (FileNotFoundException ex) {
                Logger.getLogger(EPortfolioView.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        saveAsEPButton.setOnAction(e -> {
            try {
                fileController.handleSaveAsEPRequest();
            } catch (FileNotFoundException ex) {
                Logger.getLogger(EPortfolioView.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        exportEPButton.setOnAction(e -> {
            fileController.handleViewEPRequest();
        });
        exitButton.setOnAction(e -> {
            fileController.handleExitRequest();
        });
        
        
        // PAGE CONTROLS
        pagesEditController = new PagesEditController(this);
        addPageButton.setOnAction(e -> {
            pagesEditController.processAddPageRequest();
            fileController.markFileAsNotSaved();
            enableExportButton();
            enableNewButton();
        });
        removePageButton.setOnAction(e -> {
            pagesEditController.processRemovePageRequest();
            fileController.markFileAsNotSaved();
        });
        /*selectPageButton.setOnAction(e -> {
        pagesEditController.processSelectPageRequest();
        });*/
        
        componentEditController = new ComponentEditController(this);
        // WORKSPACE CONTROLS
        addTextComponentButton.setOnAction(e -> {
            componentEditController.processAddTextComponent();
            fileController.markFileAsNotSaved();
        });
        addImageComponentButton.setOnAction(e -> {
            componentEditController.processAddImageComponent();
            fileController.markFileAsNotSaved();
        });
        addVideoComponentButton.setOnAction(e -> {
            componentEditController.processAddVideoComponent();
            fileController.markFileAsNotSaved();
        });
        addSlideshowComponentButton.setOnAction(e -> {
            componentEditController.processAddSlideshowComponent();
            fileController.markFileAsNotSaved();
        });
        editComponentButton.setOnAction(e -> {
            componentEditController.processEditComponent();
            fileController.markFileAsNotSaved();
        });
        removeComponentButton.setOnAction(e -> {
            componentEditController.processRemoveComponent();
            fileController.markFileAsNotSaved();
        });
        selectLayoutButton.setOnAction(e -> {
            componentEditController.selectLayout();
            fileController.markFileAsNotSaved();
        });
        selectColorButton.setOnAction(e -> {
            componentEditController.selectColor();
            fileController.markFileAsNotSaved();
        });
        
        //pagetabpane listener
        pageSelectionPane.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends Tab> ov, Tab t, Tab t1) -> {
            int temp = pageSelectionPane.getSelectionModel().getSelectedIndex();
            if(temp>=0){
                pages.setSelectedpageObject(pages.getPages().get(temp));
                redrawCurrentPageText();
                t1.setText(pages.getSelectedpageObject().getPageTitle());
                setWorkspaceButtons(false);
                reloadCurrentPage();
            }else{
                setWorkspaceButtons(true);
            }
        });
        
         workspaceModeToolbar.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends Tab> ov, Tab t, Tab t1) -> {
            int temp = workspaceModeToolbar.getSelectionModel().getSelectedIndex();
            if(temp==1){
                fileController.handleViewEPRequest();
                                try {
                webView();
                } catch (MalformedURLException ex) {
                    Logger.getLogger(EPortfolioView.class.getName()).log(Level.SEVERE, null, ex);
                }
                selectSiteViewerWorkspace.setContent(webView);
            }
        });
    }
    public void initPageInputsListeners() {
        //pagetitle,footer,studentname and imageview listeners
        pageTitle.textProperty().addListener(e -> {
            pages.getSelectedpageObject().setPageTitle(pageTitle.getText());
            pageSelectionPane.getSelectionModel().getSelectedItem().setText(pageTitle.getText());
            fileController.markFileAsNotSaved();
            //reloadPages();
        });
        pageFooter.textProperty().addListener(e -> {
            pages.getSelectedpageObject().setPageFooter(pageFooter.getText());
            fileController.markFileAsNotSaved();
            //reloadPages();
        });
        pageHeader.textProperty().addListener(e -> {
            pages.getSelectedpageObject().setPageHeader(pageHeader.getText());
            fileController.markFileAsNotSaved();
            //reloadPages();
        });
        studentNameField.textProperty().addListener(e -> {
            studentName = studentNameField.getText();
            fileController.markFileAsNotSaved();
            //reloadPages();
        });
        
        selectBannerImageButton.setOnAction(e -> {
            try {
                componentEditController.selectBannerImage();
                fileController.markFileAsNotSaved();
            } catch (MalformedURLException ex) {
                Logger.getLogger(EPortfolioView.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        pageFontChoice.valueProperty().addListener(e -> {
            pages.getSelectedpageObject().setPageFontChoice(pageFontChoice.getSelectionModel().getSelectedIndex());
            fileController.markFileAsNotSaved();
        });
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
        epPaneCenterSegment_Content.setCenter(pageContainer);
    }
    
    private void initFileToolbar(){
        fileToolbarPane = new FlowPane();
        newEPButton = initChildButton(fileToolbarPane, ICON_NEW_SLIDE_SHOW,	"New ePortfolio",	    "horizontal_toolbar_button", false);
        loadEPButton = initChildButton(fileToolbarPane, ICON_LOAD_SLIDE_SHOW,	"Load ePortfolio",    "horizontal_toolbar_button", false);
        saveEPButton = initChildButton(fileToolbarPane, ICON_SAVE_SLIDE_SHOW,	"Save ePortfolio",    "horizontal_toolbar_button", true);
        saveAsEPButton = initChildButton(fileToolbarPane, ICON_SAVE_SLIDE_SHOW,	"Save ePortfolio As...",    "horizontal_toolbar_button", true);
        exportEPButton = initChildButton(fileToolbarPane, ICON_VIEW_SLIDE_SHOW,	"Generate ePortfolio site",    "horizontal_toolbar_button", true);
        exitButton = initChildButton(fileToolbarPane, ICON_EXIT, "Exit Application", "horizontal_toolbar_button", false);
    }
    
    private void initSiteToolbarPane(){
        siteToolbarPane = new FlowPane();
        addPageButton = initChildButton(siteToolbarPane, "page/add.png",	"Add page",    "horizontal_toolbar_button", false);
        removePageButton = initChildButton(siteToolbarPane, "page/remove.png",	"Remove page",	    "horizontal_toolbar_button", true);
        // selectPageButton = initChildButton(siteToolbarPane, "page/select.png",	"Select page",    "horizontal_toolbar_button", true);
    }
    
    private void initPageEditorWorkspaceToolbar(){
        pageEditorWorkspaceToolbar = new FlowPane();
        addTextComponentButton = initChildButton(pageEditorWorkspaceToolbar, "workspace/addtext.png",	"Add text component",    "vertical_toolbar_button", true);
        addImageComponentButton = initChildButton(pageEditorWorkspaceToolbar, "workspace/addimage.png",	"Add image component",	    "vertical_toolbar_button", true);
        addVideoComponentButton = initChildButton(pageEditorWorkspaceToolbar, "workspace/addvideo.png",	"Add video component",    "vertical_toolbar_button", true);
        addSlideshowComponentButton = initChildButton(pageEditorWorkspaceToolbar, "workspace/addslideshow.png",	"Add a slideshow",    "vertical_toolbar_button", true);
        editComponentButton = initChildButton(pageEditorWorkspaceToolbar, "workspace/editcomponent.png",	"Edit selected component",    "vertical_toolbar_button", true);
        removeComponentButton = initChildButton(pageEditorWorkspaceToolbar,  "workspace/remove.png",	"Remove selected component",    "vertical_toolbar_button", true);
        
        Separator separator = new Separator();
        separator.setMaxWidth(80);
        pageEditorWorkspaceToolbar.getChildren().add(separator);
        
        selectLayoutButton = initChildButton(pageEditorWorkspaceToolbar, "workspace/selectlayout.png",	"Select the layout of your portfolio",    "vertical_toolbar_button", false);
        selectColorButton = initChildButton(pageEditorWorkspaceToolbar, "workspace/selectcolor.png",	"Select the color scheme for your portfolio",    "vertical_toolbar_button", false);
    }
    
    private void initTabBar(){
        workspaceModeToolbar = new TabPane();
        workspaceModeToolbar.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
        selectPageEditorWorkspaceTab = new Tab();
        selectSiteViewerWorkspace = new Tab();
        
        
        selectPageEditorWorkspaceTab.setText("Page Editor Workspace");
        selectSiteViewerWorkspace.setText("Site Viewer Workspace");
        
        selectPageEditorWorkspaceTab.setContent(epPaneCenterSegment_Content);
        selectSiteViewerWorkspace.setContent(webView);
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
    
    public void webView() throws MalformedURLException {
        // SETUP THE UI
        webView = new WebView();
        //scrollPane = new ScrollPane(webView);
        
        // GET THE URL
        String indexPath = "./sites/" + getStudentName() + "/index.html";
        File indexFile = new File(indexPath);
        URL indexURL = indexFile.toURI().toURL();
        
        // SETUP THE WEB ENGINE AND LOAD THE URL
        webEngine = webView.getEngine();
        webEngine.load(indexURL.toExternalForm());
        webEngine.reload();
        webEngine.setJavaScriptEnabled(true);
    }
    
    // Initialize the UI for a new session
    public void startNewSession() {
        workspaceModeToolbar.getTabs().add(selectPageEditorWorkspaceTab);
        workspaceModeToolbar.getTabs().add(selectSiteViewerWorkspace);
    }
    
    public PagesModel getPages() {
        return pages;
    }
    
    public void reloadPages(){
        int temp = pageSelectionPane.getSelectionModel().getSelectedIndex();
        pages.setSelectedPage(temp);
        if(pages.getPages().size()>0){
            removePageButton.setDisable(false);
        }else{
            removePageButton.setDisable(true);
        }
        
        pageSelectionPane.getTabs().clear();
        for (Page page : pages.getPages()) {
            Tab tab = new Tab();
            tab.setText(page.getPageTitle());
            VBox vbox = new VBox();
            slidesEditorScrollPane = new ScrollPane(vbox);
            tab.setContent(slidesEditorScrollPane);
            pageSelectionPane.getTabs().add(tab);
        }
        
        
        if(pages.getPages().size()!=0){
            pageSelectionPane.getSelectionModel().select(temp);
        reloadCurrentPage() ;
        }
    }
    
    public void reloadComponents(ScrollPane test) throws MalformedURLException{
        VBox vbox = (VBox) test.getContent();
        vbox.getChildren().clear();
        for (Component component : pages.getSelectedpageObject().getComponents()) {
            ComponentEditView componentEditor = new ComponentEditView(this, component);
            if (pages.getSelectedpageObject().isSelectedComponent(component))
                componentEditor.getStyleClass().add("selected_comp");
            else
                componentEditor.getStyleClass().add("not_selected_comp");
            
            vbox.getChildren().add(componentEditor);
            componentEditor.setOnMousePressed(e -> {
                pages.getSelectedpageObject().setSelectedComponent(component);
                try {
                    if(pages.getSelectedpageObject().isSelectedComponent(null)){
                        removeComponentButton.setDisable(true);
                        editComponentButton.setDisable(true);
                    }else{
                        removeComponentButton.setDisable(false);
                        editComponentButton.setDisable(false);
                    }
                    this.reloadComponents(test);
                } catch (MalformedURLException ex) {
                    Logger.getLogger(EPortfolioView.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
        }
        //ComponentsEditView slideEditor = new ComponentsEditView(this, slide);
    }
    
    private void redrawCurrentPageText() {
        pageTitle.setText(pages.getSelectedpageObject().getPageTitle());
        pageFooter.setText(pages.getSelectedpageObject().getPageFooter());
        pageHeader.setText(pages.getSelectedpageObject().getPageHeader());
        bannerImageText.setText(bannerImage);
        studentNameField.setText(studentName);
        pageFontChoice.getSelectionModel().select(pages.getSelectedpageObject().getPageFontChoice());;
        //bannerImage.setImage(pages.getSelectedpageObject().getBannerImage());
    }
    
    public void setBannerImage(File file) throws MalformedURLException {
        URL fileURL = file.toURI().toURL();
        Image slideImage = new Image(fileURL.toExternalForm());
        bannerImage = bannerImageText.getText();
    }
    
    public void reloadCurrentPage() {
        try {
            reloadComponents((ScrollPane) pageSelectionPane.getSelectionModel().getSelectedItem().getContent());
        } catch (MalformedURLException ex) {
            Logger.getLogger(EPortfolioView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void setWorkspaceButtons(boolean b) {
        addTextComponentButton.setDisable(b);
        addImageComponentButton.setDisable(b);
        addVideoComponentButton.setDisable(b);
        addSlideshowComponentButton.setDisable(b);
    }
    
    public void disableEditButton() {
        editComponentButton.setDisable(true);
        removeComponentButton.setDisable(true);
    }

    public void setLayout(String layoutChoice) {
        this.layoutTheme = layoutChoice;
    }

    public void setColortheme(String colorTheme) {
        this.colorTheme = colorTheme;
    }

    private void initStrings() {
        bannerImage = "No Banner Image";
        studentName = "";
        colorTheme = "color4.css";
        layoutTheme = "layout4.css";
    }

    public String getCSSLayout() {
        return layoutTheme;
    }

    public String getCSSColor() {
        return colorTheme;
    }

    public String getStudentName() {
        return studentName;
    }

    public String getBannerImageLoc() {
        return bannerImage;
    }
    
    public void setBannerImageString(String txt){
        this.bannerImage = txt;
    }

    public void setStudentName(String asdas) {
        this.studentName = asdas;
    }
    
    public void addPage(String cssFont, String pageTitle, String pageHeader, String pageFooter,int pageFont,ObservableList<Component> components ){
        pagesEditController.JSONAddPageRequest(cssFont,pageTitle,pageHeader,pageFooter,pageFont,components);
    }

    public Stage getWindow() {
	return primaryStage;
    }

    public boolean getworkspaceInitialized(){
        return workspaceInitialized;
    }

    public void setJSONSavedPath(String jsonFilePath) {
        this.jsonFilePath = jsonFilePath;
    }
    public String getJSONSavedPath(){
        return jsonFilePath;
    }

    public void markAsNotSaved() {
        fileController.markFileAsNotSaved();
    }

    public void disableSaveButtons() {
        saveEPButton.setDisable(true);
        saveAsEPButton.setDisable(true);
    }
    
    public void enableSaveButtons() {
        saveEPButton.setDisable(false);
        saveAsEPButton.setDisable(false);
    }
    
    public void disableExportButton(){
        exportEPButton.setDisable(true);
    }
    public void enableExportButton(){
        exportEPButton.setDisable(false);
    }
    
    public void disableNewButton(){
        newEPButton.setDisable(true);
    }
    public void enableNewButton(){
        newEPButton.setDisable(false);
    }
    
    public void addPageSingle(){
        pagesEditController.processAddPageRequest();
        fileController.markFileAsNotSaved();
        enableExportButton();
    }
}
