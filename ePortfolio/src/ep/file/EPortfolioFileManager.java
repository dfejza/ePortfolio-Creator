/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package ep.file;

import ep.model.Component;
import ep.model.Page;
import ep.view.EPortfolioView;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
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
    public static String JSON_NUM_COMP = "numComponents";
    
    public static String JSON_COMPONENT = "component";
    public static String JSON_COMP_TYPE = "type";
    public static String JSON_COMP_TEXT = "text";
    public static String JSON_COMP_IMGLOC = "imageLoc";
    public static String JSON_COMP_VIDEOLOC = "videoLoc";
    public static String JSON_COMP_HSC = "hyperlinkStartChar";
    public static String JSON_COMP_HEC = "hyperlinkEndChar";
    public static String JSON_COMP_HADD = "hyperlinkAddress";
    public static String JSON_COMP_WIDTH = "hyperlinkComponentInject";
    public static String JSON_COMP_HEIGHT = "hyperlinkComponentInject";
    public static String JSON_COMP_ALLIGHNMENT = "hyperlinkComponentInject";
;
    
    public static String JSON_COMP_SLIDESHOW = "slideShow";
    public static String JSON_COMP_SLIDESHOW_NUMIMAGES = "numOfImages";
    public static String JSON_COMP_SLIDESHOW_CAPTION = "caption";
    public static String JSON_COMP_SLIDESHOW_IMAGE = "image";
    
    
    public static String JSON_COMP_LISTDATA = "listData";
    
    public static String JSON_EXT = ".json";
    public static String SLASH = "/";
    private EPortfolioView ui;
    
    public void saveSlideShow(EPortfolioView initUI) {
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
        /*	// BUILD THE SLIDES ARRAY
        JsonArray slidesJsonArray = makeSlidesJsonArray(slideShowToSave.getSlides());
        
        // NOW BUILD THE COURSE USING EVERYTHING WE'VE ALREADY MADE
        JsonObject slideShowJsonObject = Json.createObjectBuilder()
        .add(JSON_TITLE, slideShowToSave.getTitle())
        .add(JSON_SLIDES, slidesJsonArray)
        .build();
        
        Map<String, Object> properties = new HashMap<>(1);
        properties.put(JsonGenerator.PRETTY_PRINTING, true);
        
        JsonWriterFactory writerFactory = Json.createWriterFactory(properties);
        JsonWriter jsonWriter = writerFactory.createWriter(sw);
        jsonWriter.writeObject(slideShowJsonObject);
        jsonWriter.close();
        
        // INIT THE WRITER
        String slideShowTitle = "" + slideShowToSave.getTitle();
        //String jsonFilePath = PATH_SLIDE_SHOWS + SLASH + slideShowTitle + JSON_EXT;
        OutputStream os = new FileOutputStream(jsonFilePath);
        JsonWriter jsonFileWriter = Json.createWriter(os);
        jsonFileWriter.writeObject(slideShowJsonObject);
        String prettyPrinted = sw.toString();
        PrintWriter pw = new PrintWriter(jsonFilePath);
        pw.write(prettyPrinted);
        pw.close();
        System.out.println(prettyPrinted);*/
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
                        
	JsonObject jso = Json.createObjectBuilder()
		.add(JSON_COMP_TYPE, comp.getComponentType())
                		.add(JSON_COMP_TEXT, comp.getText())
		.add(JSON_COMP_IMGLOC,comp.getImagePath())
                                    .add(JSON_COMP_VIDEOLOC,comp.getImagePath())
		.add(JSON_COMP_HSC, "null"  )
		.add(JSON_COMP_HEC, "null" )
                                    .add(JSON_COMP_HADD, "null" )
		.add(JSON_COMP_WIDTH,comp.getWidth() )
                                    .add(JSON_COMP_HEIGHT,comp.getHeight()  )
                                    .add(JSON_COMP_ALLIGHNMENT,comp.getAllign() )
                                    //.add(JSON_COMP_SLIDESHOW, SLIDESHOWARRAY)
                                    //.add(JSON_COMP_LISTDATA, LISTARRAY)
		.build();
	return jso;
    }
}