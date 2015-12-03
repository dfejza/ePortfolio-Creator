/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package ep.file;

import ep.model.Component;
import ep.model.Page;
import ep.model.ssm.Slide;
import ep.model.ssm.SlideShowModel;
import ep.view.EPortfolioView;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonWriter;
import javax.json.JsonWriterFactory;
import javax.json.stream.JsonGenerator;

/**
 * This class uses the JSON standard to read and write ePortfolio data files.
 * @author linti
 */
public class EPortfolioFileManager {
    public static String JSON_STUDENT_NAME = "student_name";
    public static String JSON_BANNER = "bannerImageLoc";
    public static String JSON_FOOTER = "footer";
    
    public static String JSON_PAGES = "page";
    public static String JSON_CSS_LAYOUT = "cssLayout";
    public static String JSON_CSS_COLOR = "cssColor";
    public static String JSON_CSS_FONT = "cssFont";
    public static String JSON_PAGETITLE = "pageTitle";
    public static String JSON_PAGEHEADER = "pageHeader";
    public static String JSON_PAGEFOOTER = "pageFooter";
    public static String JSON_PAGEFONT = "pageFont";
    public static String JSON_NUM_COMP = "numComponents";
    
    public static String JSON_COMPONENT = "component";
    public static String JSON_COMP_TYPE = "type";
    public static String JSON_COMP_TEXT = "text";
    public static String JSON_COMP_IMGLOC = "imageLoc";
    public static String JSON_COMP_VIDEOLOC = "videoLoc";
    public static String JSON_COMP_HSC = "hyperlinkStartChar";
    public static String JSON_COMP_HEC = "hyperlinkEndChar";
    public static String JSON_COMP_HADD = "hyperlinkAddress";
    public static String JSON_COMP_WIDTH = "width";
    public static String JSON_COMP_HEIGHT = "height";
    public static String JSON_COMP_ALLIGHNMENT = "allignment";
    public static String JSON_COMP_HINJECT = "hyperlinkComponentInject";
    public static String JSON_COMP_FONT = "compFont";
    
    public static String JSON_COMP_SLIDESHOW = "slideShow";
    public static String JSON_COMP_SLIDESHOW_NUMIMAGES = "numOfImages";
    public static String JSON_COMP_SLIDESHOW_CAPTION = "caption";
    public static String JSON_COMP_SLIDESHOW_IMAGE = "image";
    public static String JSON_IMAGE_FILE_NAME = "image_file_name";
    public static String JSON_IMAGE_PATH = "image_path";
    public static String JSON_CAPTION = "caption";
    
    
    public static String JSON_COMP_LISTDATA = "listData";
    
    public static String JSON_EXT = ".json";
    public static String SLASH = "/";
    private EPortfolioView ui;
    
    public void saveSlideShow(EPortfolioView initUI, String path) throws FileNotFoundException {
        this.ui = initUI;
        StringWriter sw = new StringWriter();
        
        JsonArray slidesJsonArray = makePagesJsonArray(ui.getPages().getPages());
        
        JsonObject slideShowJsonObject = Json.createObjectBuilder()
                .add(JSON_CSS_LAYOUT, ui.getCSSLayout())
                .add(JSON_CSS_COLOR, ui.getCSSColor())
                .add(JSON_STUDENT_NAME, ui.getStudentName())
                .add(JSON_BANNER, ui.getBannerImageLoc())
                .add(JSON_PAGES, slidesJsonArray)
                .build();
        
        Map<String, Object> properties = new HashMap<>(1);
        properties.put(JsonGenerator.PRETTY_PRINTING, true);
        
        JsonWriterFactory writerFactory = Json.createWriterFactory(properties);
        JsonWriter jsonWriter = writerFactory.createWriter(sw);
        jsonWriter.writeObject(slideShowJsonObject);
        jsonWriter.close();
        
        // INIT THE WRITER
        String slideShowTitle = "" + ui.getStudentName();
        String jsonFilePath;
        if(path==null){
            jsonFilePath = "./" + slideShowTitle + JSON_EXT;
            ui.setJSONSavedPath(jsonFilePath);
        }else{
            jsonFilePath = path;
            ui.setJSONSavedPath(jsonFilePath);
        }
        OutputStream os = new FileOutputStream(jsonFilePath);
        JsonWriter jsonFileWriter = Json.createWriter(os);
        jsonFileWriter.writeObject(slideShowJsonObject);
        String prettyPrinted = sw.toString();
        PrintWriter pw = new PrintWriter(jsonFilePath);
        pw.write(prettyPrinted);
        pw.close();
        System.out.println(prettyPrinted);
    }
    
    public void loadSlideShow(EPortfolioView initUI, String jsonFilePath) throws IOException {
        this.ui = initUI;
        if(!ui.getworkspaceInitialized()){
            ui.startNewSession();
            ui.initPageInputs();
            ui.initPageInputsListeners();
        }else{
            ui.getPages().reset();
        }
        // LOAD THE JSON FILE WITH ALL THE DATA
        JsonObject json = loadJSONFile(jsonFilePath);
        
        // NOW LOAD THE COURSE
        //slideShowToLoad.reset();
        initUI.setBannerImageString(json.getString(JSON_BANNER));
        initUI.setColortheme(json.getString(JSON_CSS_COLOR));
        initUI.setLayout(json.getString(JSON_CSS_LAYOUT));
        initUI.setStudentName(json.getString(JSON_STUDENT_NAME));
        
        
        JsonArray jsonPageArray = json.getJsonArray(JSON_PAGES);
        
        for (int i = 0; i < jsonPageArray.size(); i++) {
            ObservableList<Component> components = FXCollections.observableArrayList();
            JsonObject pageJso = jsonPageArray.getJsonObject(i);
            JsonArray jsonComponentArray = pageJso.getJsonArray(JSON_COMPONENT);
            for (int j = 0; j < jsonComponentArray.size(); j++) {
                String[] listData = null;
                SlideShowModel slideShowModel = new SlideShowModel();
                JsonObject componentJSO = jsonComponentArray.getJsonObject(j);
                if(componentJSO.getInt(JSON_COMP_TYPE)==4){
                    JsonArray jsonSlideArray = componentJSO.getJsonArray(JSON_COMP_SLIDESHOW);
                    for (int k = 0; k < jsonSlideArray.size(); k++) {
                        JsonObject slideJSO = jsonSlideArray.getJsonObject(k);
                        slideShowModel.addJSonSlide(slideJSO.getString(JSON_IMAGE_FILE_NAME),
                                slideJSO.getString(JSON_IMAGE_PATH),
                                slideJSO.getString(JSON_CAPTION));
                    }
                }
                
                if(componentJSO.getInt(JSON_COMP_TYPE)==6){
                    JsonArray jsonListArray = componentJSO.getJsonArray(JSON_COMP_LISTDATA);
                    listData = new String[jsonListArray.size()];
                    for (int k = 0; k < jsonListArray.size(); k++) {
                        listData[k] = jsonListArray.getString(k);
                    }
                }
                
                Component newComp = new Component(
                        componentJSO.getInt(JSON_COMP_TYPE),
                        componentJSO.getString(JSON_COMP_FONT),
                        componentJSO.getString(JSON_COMP_TEXT),
                        componentJSO.getString(JSON_COMP_IMGLOC),
                        componentJSO.getInt(JSON_COMP_WIDTH),
                        componentJSO.getInt(JSON_COMP_HEIGHT),
                        componentJSO.getInt(JSON_COMP_ALLIGHNMENT),
                        componentJSO.getInt(JSON_COMP_HSC),
                        componentJSO.getInt(JSON_COMP_HEC),
                        componentJSO.getString(JSON_COMP_HADD),
                        componentJSO.getInt(JSON_COMP_HINJECT),
                        listData,
                        slideShowModel);
                
                components.add(newComp);
            }
            
            ui.addPage(pageJso.getString(JSON_CSS_FONT),
                    pageJso.getString(JSON_PAGETITLE),
                    pageJso.getString(JSON_PAGEHEADER),
                    pageJso.getString(JSON_PAGEFOOTER),
                    pageJso.getInt(JSON_PAGEFONT),
                    components);
        }
        ui.reloadPages();
        ui.reloadCurrentPage();
    }
    
    private JsonArray makePagesJsonArray(List<Page> pages) {
        JsonArrayBuilder jsb = Json.createArrayBuilder();
        for (Page page : pages) {
            JsonObject jso = makeSlideJsonComponent(page);
            jsb.add(jso);
        }
        JsonArray jA = jsb.build();
        return jA;
    }
    
    private JsonObject makeSlideJsonComponent(Page page) {
        //create the JSON component array before adding it do the object
        JsonArray componentJsonArray = makeSlideJsonComponent(page.getComponents());
        
        JsonObject jso = Json.createObjectBuilder()
                .add(JSON_CSS_FONT, page.getCSSFontChoice())
                .add(JSON_PAGETITLE, page.getPageTitle())
                .add(JSON_PAGEHEADER, page.getPageHeader())
                .add(JSON_PAGEFOOTER, page.getPageFooter())
                .add(JSON_PAGEFONT, page.getPageFontChoice())
                .add(JSON_COMPONENT, componentJsonArray)
                .build();
        return jso;
    }
    
    private JsonArray makeSlideJsonComponent(List<Component> comp) {
        JsonArrayBuilder jsb = Json.createArrayBuilder();
        for (Component component : comp) {
            JsonObject jso = saturateJSONComp(component);
            jsb.add(jso);
        }
        JsonArray jA = jsb.build();
        return jA;
    }
    
    private JsonObject saturateJSONComp(Component comp) {
        //create the JSON component array before adding it do the object
        //JsonArray componentJsonArray = makeSlideJsonComponent((List<Component>) page.getComponents());
        int compType = comp.getComponentType();
        JsonArray slidesJsonArray;
        if(compType==4){
            slidesJsonArray = makeSlidesJsonArray(comp.getSS().getSlides());
        }else{
            slidesJsonArray = Json.createArrayBuilder().build();
        }
        
        JsonArray listJsonArray;
        if(compType==6){
            listJsonArray = makeListJsonArray(comp.getListData());
        }else{
            listJsonArray = Json.createArrayBuilder().build();
        }
        
        
        JsonObject jso = Json.createObjectBuilder()
                .add(JSON_COMP_TYPE, comp.getComponentType())
                .add(JSON_COMP_TEXT, comp.getText())
                .add(JSON_COMP_IMGLOC,comp.getImagePath())
                .add(JSON_COMP_VIDEOLOC,comp.getImagePath())
                .add(JSON_COMP_HSC, comp.getHyperlinkStartChar() )
                .add(JSON_COMP_HEC, comp.getHyperlinkEndChar() )
                .add(JSON_COMP_HADD, comp.getHyperlinkAddress() )
                .add(JSON_COMP_HINJECT, comp.getHyperlinkComponentInject() )
                .add(JSON_COMP_FONT,comp.getFontType())
                .add(JSON_COMP_WIDTH,comp.getWidth() )
                .add(JSON_COMP_HEIGHT,comp.getHeight()  )
                .add(JSON_COMP_ALLIGHNMENT,comp.getAllign() )
                .add(JSON_COMP_SLIDESHOW, slidesJsonArray)
                .add(JSON_COMP_LISTDATA, listJsonArray)
                .build();
        return jso;
    }
    
    private JsonArray makeSlidesJsonArray(List<Slide> slides) {
        JsonArrayBuilder jsb = Json.createArrayBuilder();
        for (Slide slide : slides) {
            JsonObject jso = makeSlideJsonObject(slide);
            jsb.add(jso);
        }
        JsonArray jA = jsb.build();
        return jA;
    }
    
    private JsonObject makeSlideJsonObject(Slide slide) {
        JsonObject jso = Json.createObjectBuilder()
                .add(JSON_IMAGE_FILE_NAME, slide.getImageFileName())
                .add(JSON_IMAGE_PATH, slide.getImagePath())
                .add(JSON_CAPTION, slide.getCaption())
                .build();
        return jso;
    }
    
    private JsonArray makeListJsonArray(String[] listData) {
        JsonArrayBuilder jsb = Json.createArrayBuilder();
        for (String ld : listData) {
            jsb.add(ld);
        }
        JsonArray jA = jsb.build();
        return jA;
    }
    
    private JsonObject loadJSONFile(String jsonFilePath) throws IOException {
        InputStream is = new FileInputStream(jsonFilePath);
        JsonReader jsonReader = Json.createReader(is);
        JsonObject json = jsonReader.readObject();
        jsonReader.close();
        is.close();
        return json;
    }
}