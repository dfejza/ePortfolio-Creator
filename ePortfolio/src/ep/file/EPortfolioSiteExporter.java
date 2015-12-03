/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package ep.file;

/**
 * Exports site directory
 * @author linti
 */

import ep.model.Component;
import ep.model.Page;
import ep.model.ssm.Slide;
import ep.view.EPortfolioView;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 *
 * @author McKillaGorilla
 */
public class EPortfolioSiteExporter {
    
    // WE'LL USE THIS TO BUILD PATHS
    public static String SLASH = "/";
    public static String JSON_EXT = ".json";
    
    // HERE ARE THE DIRECTORIES WE CARE ABOUT
    public static String BASE_DIR = "./base/";
    public static String SITES_DIR = "./sites/";
    public static String CSS_COLOR_DIR = "css/color/";
    public static String CSS_FONT_DIR = "css/font/";
    public static String CSS_LAYOUT_DIR = "css/layout/";
    public static String DATA_DIR = "data/";
    public static String SLIDE_SHOWS_DIR = DATA_DIR + "slide_shows/";
    public static String ICONS_DIR = "icons/";
    public static String IMG_DIR = "img/";
    public static String VIDEO_DIR = "video/";
    public static String JS_DIR = "js/";
    public static String JSON_DIR = "json/";
    
    // AND HERE ARE THE FILES WE CARE ABOUT
    public static String INDEX_FILE = "index.html";
    public static String STYLESHEET_FILE = "slideshow_maker.css";
    public static String JS_FILE = "SlideshowMaker.js";
    public static String DATA_FILE = "exportData.json";
    
    public void exportSite(EPortfolioView initUI) throws IOException {
        // THE SITE HOME PATH
        String homeSitePath = SITES_DIR + initUI.getStudentName() + SLASH;
        
        // NOW MAKE THE SITE DIRECTORIES AND COPY OVER THE FILES
        // THAT ONLY NEED TO BE COPIED ONCE
        File siteDir = new File(homeSitePath);
        
        // FIRST DELETE THE OLD FILES IN CASE THINGS
        // LIKE THE PAGE FORMAT MAY HAVE CHANGED
        if (siteDir.exists())
            deleteDir(siteDir);
        
        // NOW MAKE THE HOME DIR
        siteDir.mkdirs();
        
        // MAKE THE CSS, DATA, IMG, AND JS DIRECTORIES
        new File(homeSitePath + "css/").mkdir();
        new File(homeSitePath + CSS_COLOR_DIR).mkdir();
        new File(homeSitePath + CSS_FONT_DIR).mkdir();
        new File(homeSitePath + CSS_LAYOUT_DIR).mkdir();
        new File(homeSitePath + DATA_DIR).mkdir();
        new File(homeSitePath + ICONS_DIR).mkdir();
        new File(homeSitePath + IMG_DIR).mkdir();
        new File(homeSitePath + VIDEO_DIR).mkdir();
        new File(homeSitePath + JS_DIR).mkdir();
        
        // NOW COPY OVER THE HTML, CSS, ICON, AND JAVASCRIPT FILES
        copyAllFiles(BASE_DIR, homeSitePath);
        copyAllFiles(BASE_DIR + CSS_COLOR_DIR, homeSitePath + CSS_COLOR_DIR);
        copyAllFiles(BASE_DIR + CSS_FONT_DIR, homeSitePath + CSS_FONT_DIR);
        copyAllFiles(BASE_DIR + CSS_LAYOUT_DIR, homeSitePath + CSS_LAYOUT_DIR);
        copyAllFiles(BASE_DIR + JS_DIR, homeSitePath + JS_DIR);
        
        // NOW FOR THE TWO THINGS THAT WE HAVE TO COPY OVER EVERY TIME,
        // NAMELY, THE DATA FILE AND THE IMAGES
        // FIRST COPY THE DATA FILE
        Path dataSrcPath = new File(initUI.getJSONSavedPath()).toPath();
        Path dataDestPath = new File(homeSitePath + DATA_DIR + DATA_FILE).toPath();
        
        Files.copy(dataSrcPath, dataDestPath);
        
        //get banner image if exists
        if(initUI.getBannerImageLoc().compareTo("No Banner image")==0){
            Path srcImgPath = new File(initUI.getBannerImageLoc()).toPath();
            Path p = Paths.get(initUI.getBannerImageLoc());
            String file = p.getFileName().toString();
            Path destImgPath = new File(homeSitePath + IMG_DIR + file).toPath();
            Files.copy(srcImgPath, destImgPath);
        }
        
        // AND NOW ALL THE SLIDESHOW IMAGES
        for (Page page : initUI.getPages().getPages()) {
            Path htmlPathSRC = new File(BASE_DIR + "index.html").toPath();
            Path htmlPathDST = new File(homeSitePath + page.getPageTitle() + ".html").toPath();
            Files.copy(htmlPathSRC,htmlPathDST);
            for (Component comp : page.getComponents()) {
                if(comp.getComponentType()==3){
                        String test = comp.getImagePath().replaceFirst("^(file:/)","");
                        Path srcImgPath = new File(test).toPath();
                        Path p = Paths.get(test);
                        String file = p.getFileName().toString();
                        Path destImgPath = new File(homeSitePath + VIDEO_DIR + file).toPath();
                        Files.copy(srcImgPath, destImgPath);
                }
                if(comp.getComponentType()==2){
                        String test = comp.getImagePath();
                        Path srcImgPath = new File(test).toPath();
                        Path p = Paths.get(test);
                        String file = p.getFileName().toString();
                        Path destImgPath = new File(homeSitePath + IMG_DIR + file).toPath();
                        Files.copy(srcImgPath, destImgPath);
                }
                //if slide show exists, copy
                if(comp.getComponentType()==4){
                    for(Slide s : comp.getSS().getSlides()){
                        Path srcImgPath = new File(s.getImagePath() + SLASH + s.getImageFileName()).toPath();
                        Path destImgPath = new File(homeSitePath + IMG_DIR + s.getImageFileName()).toPath();
                        Files.copy(srcImgPath, destImgPath);
                    }
                }
            }
        }
    }
    
    public void deleteDir(File dir) {
        File[] files = dir.listFiles();
        for (File f : files) {
            if (f.isDirectory()) {
                deleteDir(f);
                f.delete();
            }
            else
                f.delete();
        }
        dir.delete();
    }
    
    public void copyAllFiles(String sourceFile, String destinationDir) throws IOException {
        File srcDir = new File(sourceFile);
        File[] files = srcDir.listFiles();
        for (File f : files) {
            Path srcPath = f.toPath();
            Path newPath = new File(destinationDir).toPath();
            if (!f.isDirectory()) {
                Files.copy(srcPath, newPath.resolve(srcPath.getFileName()));
            }
        }
    }
}
