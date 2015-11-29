/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package ep.model.ssm;

import ep.controller.ComponentEditController;
import ep.model.Component;
import ep.model.ssm.SlideShowModel;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author linti
 */
public class SlideShowMakerView {
    // THIS IS THE MAIN APPLICATION UI WINDOW AND ITS SCENE GRAPH
    Stage primaryStage;
    Scene primaryScene;
    
    // THIS PANE ORGANIZES THE BIG PICTURE CONTAINERS FOR THE
    // APPLICATION GUI
    BorderPane ssmPane;
    
    // THIS IS THE TOP TOOLBAR AND ITS CONTROLS
    FlowPane fileToolbarPane;
    Button newSlideShowButton;
    Button loadSlideShowButton;
    Button saveSlideShowButton;
    Button viewSlideShowButton;
    Button exitButton;
    
    // WORKSPACE
    BorderPane workspace;
    
    // THIS WILL GO IN THE LEFT SIDE OF THE SCREEN
    FlowPane slideEditToolbar;
    Button addSlideButton;
    Button removeSlideButton;
    Button moveSlideUpButton;
    Button moveSlideDownButton;
    Button okayButton;
    
    // FOR THE SLIDE SHOW TITLE
    FlowPane titlePane;
    Label titleLabel;
    TextField titleTextField;
    
    // AND THIS WILL GO IN THE CENTER
    ScrollPane slidesEditorScrollPane;
    VBox slidesEditorPane;
    
    // THIS IS THE SLIDE SHOW WE'RE WORKING WITH
    SlideShowModel slideShow;
    
    // THIS CONTROLLER RESPONDS TO SLIDE SHOW EDIT BUTTONS
    private SlideShowEditController editController;
    
    public static String PROPERTIES_SCHEMA_FILE_NAME = "properties_schema.xsd";
    public static String PATH_DATA = "./data/";
    public static String PATH_SLIDE_SHOWS = PATH_DATA + "slide_shows/";
    public static String PATH_IMAGES = "./images/";
    public static String PATH_ICONS = PATH_IMAGES + "icons/";
    public static String PATH_SLIDE_SHOW_IMAGES = PATH_IMAGES + "slide_show_images/";
    public static String PATH_CSS = "/ep/style/";
    public static String STYLE_SHEET_UI = PATH_CSS + "EPortfolioStyle.css";
    
    // HERE ARE THE LANGUAGE INDEPENDENT GUI ICONS
    public static String ICON_WINDOW_LOGO = "SSM_Logo.png";
    public static String ICON_NEW_SLIDE_SHOW = "New.png";
    public static String ICON_LOAD_SLIDE_SHOW = "Load.png";
    public static String ICON_SAVE_SLIDE_SHOW = "Save.png";
    public static String ICON_VIEW_SLIDE_SHOW = "View.png";
    public static String ICON_EXIT = "Exit.png";
    public static String ICON_ADD_SLIDE = "Add.png";
    public static String ICON_REMOVE_SLIDE = "Remove.png";
    public static String ICON_MOVE_UP = "MoveUp.png";
    public static String ICON_MOVE_DOWN = "MoveDown.png";
    public static String ICON_PREVIOUS = "Previous.png";
    public static String ICON_NEXT = "Next.png";
    
    // UI SETTINGS
    public static String    DEFAULT_SLIDE_IMAGE = "DefaultStartSlide.png";
    public static int	    DEFAULT_THUMBNAIL_WIDTH = 200;
    public static int	    DEFAULT_SLIDE_SHOW_HEIGHT = 500;
    
    // CSS STYLE SHEET CLASSES
    
    // CSS - FOR THE LANGUAGE SELECTION DIALOG
    public static String    CSS_CLASS_LANG_DIALOG_PANE = "lang_dialog_pane";
    public static String    CSS_CLASS_LANG_PROMPT = "lang_prompt";
    public static String    CSS_CLASS_LANG_COMBO_BOX = "lang_combo_box";
    public static String    CSS_CLASS_LANG_OK_BUTTON = "lang_ok_button";
    
    // CSS - FOR THE TOOLBARS
    public static String    CSS_CLASS_HORIZONTAL_TOOLBAR_PANE = "horizontal_toolbar_pane";
    public static String    CSS_CLASS_VERTICAL_TOOLBAR_PANE = "vertical_toolbar_pane";
    public static String    CSS_CLASS_VERTICAL_TOOLBAR_BUTTON = "vertical_toolbar_button";
    public static String    CSS_CLASS_HORIZONTAL_TOOLBAR_BUTTON = "horizontal_toolbar_button";
    
    // CSS - SLIDESHOW EDITING
    public static String    CSS_CLASS_TITLE_PANE = "title_pane";
    public static String    CSS_CLASS_TITLE_PROMPT = "title_prompt";
    public static String    CSS_CLASS_TITLE_TEXT_FIELD = "title_text_field";
    public static String    CSS_CLASS_CAPTION_PROMPT = "caption_prompt";
    public static String    CSS_CLASS_CAPTION_TEXT_FIELD = "caption_text_field";
    
    // CSS - PANELS
    public static String    CSS_CLASS_WORKSPACE = "workspace";
    public static String    CSS_CLASS_SLIDES_EDITOR_PANE = "slides_editor_pane";
    public static String    CSS_CLASS_SLIDE_EDIT_VIEW = "slide_edit_view";
    public static String    CSS_CLASS_SELECTED_SLIDE_EDIT_VIEW = "selected_slide_edit_view";
    
    // UI LABELS
    public static String    LABEL_SLIDE_SHOW_TITLE = "slide_show_title";
    public static String    LABEL_LANGUAGE_SELECTION_PROMPT = "Select a Language:";
    public static String    OK_BUTTON_TEXT = "OK";
    Component currentComponent;
    ComponentEditController componentController;
    /**
     * Default constructor, it initializes the GUI for use, but does not yet
     * load all the language-dependent controls, that needs to be done via the
     * startUI method after the user has selected a language.
     * @param currentComponent
     */
    /*    public SlideShowMakerView(SlideShowFileManager initFileManager,
    SlideShowSiteExporter initSiteExporter) {*/
    public SlideShowMakerView(Component currentComponent, ComponentEditController aThis) {
        slideShow = new SlideShowModel(this);
        this.currentComponent = currentComponent;
        this.componentController = aThis;
    }
    
    // ACCESSOR METHODS
    public SlideShowModel getSlideShow() {
        return slideShow;
    }
    
    public Stage getWindow() {
        return primaryStage;
    }
    
    
    /**
     * Initializes the UI controls and gets it rolling.
     *
     * @param initPrimaryStage The window for this application.
     *
     * @param windowTitle The title for this window.
     */
    public void startUI(Stage initPrimaryStage, String windowTitle) {
        // INIT THE CENTER WORKSPACE CONTROLS BUT DON'T ADD THEM
        // TO THE WINDOW YET
        initWorkspace();
        
        
        // NOW SETUP THE EVENT HANDLERS
        initEventHandlers();
        
        if(currentComponent.getComponentType()==4){
            slideShow = currentComponent.getSS();
            reloadSlideShowPane();
        }
        
        // AND FINALLY START UP THE WINDOW (WITHOUT THE WORKSPACE)
        // KEEP THE WINDOW FOR LATER
        primaryStage = initPrimaryStage;
        initWindow(windowTitle);
    }
    
    // UI SETUP HELPER METHODS
    private void initWorkspace() {
        // FIRST THE WORKSPACE ITSELF, WHICH WILL CONTAIN TWO REGIONS
        workspace = new BorderPane();
        
        // THIS WILL GO IN THE LEFT SIDE OF THE SCREEN
        slideEditToolbar = new FlowPane();
        addSlideButton = this.initChildButton(slideEditToolbar,		ICON_ADD_SLIDE,	    "Add Slide",	    CSS_CLASS_VERTICAL_TOOLBAR_BUTTON,  false);
        removeSlideButton = this.initChildButton(slideEditToolbar,	ICON_REMOVE_SLIDE,  "Remove Slide",   CSS_CLASS_VERTICAL_TOOLBAR_BUTTON,  false);
        moveSlideUpButton = this.initChildButton(slideEditToolbar,	ICON_MOVE_UP,	    "Move Slide Up",	    CSS_CLASS_VERTICAL_TOOLBAR_BUTTON,  false);
        moveSlideDownButton = this.initChildButton(slideEditToolbar,	ICON_MOVE_DOWN,	    "Move Slide Down",	    CSS_CLASS_VERTICAL_TOOLBAR_BUTTON,  false);
        
        // AND THIS WILL GO IN THE CENTER
        slidesEditorPane = new VBox();
        slidesEditorScrollPane = new ScrollPane(slidesEditorPane);
        slidesEditorScrollPane.setFitToWidth(true);
        slidesEditorScrollPane.setFitToHeight(true);
        initTitleControls();
        
        // Init the OKAY button
        okayButton = new Button();
        okayButton.setText("Save Slide Show");
        StackPane buttonbox = new StackPane(okayButton);
        buttonbox.setAlignment(okayButton, Pos.BOTTOM_RIGHT);
        
        // NOW PUT THESE TWO IN THE WORKSPACE
        workspace.setTop(slideEditToolbar);
        workspace.setCenter(slidesEditorScrollPane);
        workspace.setBottom(buttonbox);
        
        // SETUP ALL THE STYLE CLASSES
        workspace.getStyleClass().add(CSS_CLASS_WORKSPACE);
        slideEditToolbar.getStyleClass().add("horizontal_toolbar_pane");
        slidesEditorPane.getStyleClass().add(CSS_CLASS_SLIDES_EDITOR_PANE);
        slidesEditorScrollPane.getStyleClass().add(CSS_CLASS_SLIDES_EDITOR_PANE);
    }
    
    private void initEventHandlers() {
        // THEN THE SLIDE SHOW EDIT CONTROLS
        editController = new SlideShowEditController(this);
        addSlideButton.setOnAction(e -> {
            editController.processAddSlideRequest();
        });
        removeSlideButton.setOnAction(e -> {
            editController.processRemoveSlideRequest();
        });
        moveSlideUpButton.setOnAction(e -> {
            editController.processMoveSlideUpRequest();
        });
        moveSlideDownButton.setOnAction(e -> {
            editController.processMoveSlideDownRequest();
        });
        okayButton.setOnAction(e -> {
            exportTheStuff();
        });
    }
    
    private void initWindow(String windowTitle) {
        // SET THE WINDOW TITLE
        primaryStage.setTitle(windowTitle);
        
        
        primaryStage.setWidth(600);
        primaryStage.setHeight(800);
        
        // SETUP THE UI, NOTE WE'LL ADD THE WORKSPACE LATER
        ssmPane = new BorderPane();
        //ssmPane.getStyleClass().add(CSS_CLASS_WORKSPACE);
        //ssmPane.setTop(fileToolbarPane);
        ssmPane.setCenter(workspace);
        primaryScene = new Scene(ssmPane);
        
        // NOW TIE THE SCENE TO THE WINDOW, SELECT THE STYLESHEET
        // WE'LL USE TO STYLIZE OUR GUI CONTROLS, AND OPEN THE WINDOW
        primaryScene.getStylesheets().add(STYLE_SHEET_UI);
        primaryStage.setScene(primaryScene);
        primaryStage.show();
    }
    
    /**
     * This helps initialize buttons in a toolbar, constructing a custom button
     * with a customly provided icon and tooltip, adding it to the provided
     * toolbar pane, and then returning it.
     */
    public Button initChildButton(
            Pane toolbar,
            String iconFileName,
            String tooltip,
            String cssClass,
            boolean disabled) {
        String imagePath = "file:" + PATH_ICONS + iconFileName;
        Image buttonImage = new Image(imagePath);
        Button button = new Button();
        button.getStyleClass().add(cssClass);
        button.setDisable(disabled);
        button.setGraphic(new ImageView(buttonImage));
        Tooltip buttonTooltip = new Tooltip(tooltip);
        button.setTooltip(buttonTooltip);
        toolbar.getChildren().add(button);
        return button;
    }
    
    
    public void updateSlideshowEditToolbarControls() {
        // AND THE SLIDESHOW EDIT TOOLBAR
        addSlideButton.setDisable(false);
        boolean slideSelected = slideShow.isSlideSelected();
        removeSlideButton.setDisable(!slideSelected);
        moveSlideUpButton.setDisable(!slideSelected);
        moveSlideDownButton.setDisable(!slideSelected);
    }
    
    /**
     * Uses the slide show data to reload all the components for
     * slide editing.
     *
     * @param slideShowToLoad SLide show being reloaded.
     */
    public void reloadSlideShowPane() {
        slidesEditorPane.getChildren().clear();
        reloadTitleControls();
        for (Slide slide : slideShow.getSlides()) {
            SlideEditView slideEditor = new SlideEditView(slide);
            if (slideShow.isSelectedSlide(slide))
                slideEditor.getStyleClass().add(CSS_CLASS_SLIDE_EDIT_VIEW);
            else
                slideEditor.getStyleClass().add(CSS_CLASS_SELECTED_SLIDE_EDIT_VIEW);
            slidesEditorPane.getChildren().add(slideEditor);
            slideEditor.setOnMousePressed(e -> {
                slideShow.setSelectedSlide(slide);
                this.reloadSlideShowPane();
            });
        }
        updateSlideshowEditToolbarControls();
    }
    
    private void initTitleControls() {
        String labelPrompt = "Title";
        titlePane = new FlowPane();
        titleLabel = new Label(labelPrompt);
        titleTextField = new TextField();
        
        titlePane.getChildren().add(titleLabel);
        titlePane.getChildren().add(titleTextField);
        
        String titlePrompt = "Title";
        titleTextField.setText(titlePrompt);
        
        titleTextField.textProperty().addListener(e -> {
            slideShow.setTitle(titleTextField.getText());
        });
        
        titlePane.getStyleClass().add(CSS_CLASS_TITLE_PANE);
        titleLabel.getStyleClass().add(CSS_CLASS_TITLE_PROMPT);
        titleTextField.getStyleClass().add(CSS_CLASS_TITLE_TEXT_FIELD);
    }
    
    public void reloadTitleControls() {
        if (slidesEditorPane.getChildren().size() == 0)
            slidesEditorPane.getChildren().add(titlePane);
        titleTextField.setText(slideShow.getTitle());
    }

    private void exportTheStuff() {
        int type = 1;
        if(currentComponent.getComponentType() == 4){
            type = 2;
        }
        currentComponent.slideShowComponent(slideShow);
        if(type==1){
            componentController.reloadComponents();
        }else{
            componentController.reload();
        }
            
        primaryStage.close();
    }
    
    
    /**
     * This controller provides responses for the slideshow edit toolbar,
     * which allows the user to add, remove, and reorder slides.
     *
     * @author McKilla Gorilla & _____________
     */
    public class SlideShowEditController {
        // APP UI
        private SlideShowMakerView ui;
        
        /**
         * This constructor keeps the UI for later.
         */
        public SlideShowEditController(SlideShowMakerView initUI) {
            ui = initUI;
        }
        
        /**
         * Provides a response for when the user wishes to add a new
         * slide to the slide show.
         */
        public void processAddSlideRequest() {
            SlideShowModel slideShow = ui.getSlideShow();
            slideShow.addSlide(DEFAULT_SLIDE_IMAGE, PATH_SLIDE_SHOW_IMAGES, "DEFAULT CAPTION");
            ui.reloadSlideShowPane();
        }
        
        /**
         * Provides a response for when the user has selected a slide
         * and wishes to remove it from the slide show.
         */
        public void processRemoveSlideRequest() {
            SlideShowModel slideShow = ui.getSlideShow();
            slideShow.removeSelectedSlide();
            ui.reloadSlideShowPane();
        }
        
        /**
         * Provides a response for when the user has selected a slide
         * and wishes to move it up in the slide show.
         */
        public void processMoveSlideUpRequest() {
            SlideShowModel slideShow = ui.getSlideShow();
            slideShow.moveSelectedSlideUp();
            ui.reloadSlideShowPane();
        }
        
        /**
         * Provides a response for when the user has selected a slide
         * and wises to move it down in the slide show.
         */
        public void processMoveSlideDownRequest() {
            SlideShowModel slideShow = ui.getSlideShow();
            slideShow.moveSelectedSlideDown();
            ui.reloadSlideShowPane();
        }
    }
}
