/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package ep.model;

import ep.model.ssm.SlideShow;
import ep.model.ssm.SlideShowModel;
import javafx.scene.control.ListView;

/**
 *
 * @author linti
 */
public class Component {
    private int type;
    private String text;
    private String font;
    private String imageLoc;
    private String videoLoc;
    private int hyperlinkStartChar;
    private int hyperlinkEndChar;
    private String hyperlinkAddress;
    private int hyperlinkComponentInject;
    private String[] listData;
    private SlideShowModel slideShow;
    private String width;
    private String height;
    private String caption;
    private int justification;
    
    public Component(){
        type = 0;
        font = "Font 1";
        text = "Enter text here.";
        imageLoc = null;
        width = "1024.0";
        height = "768.0";
        justification = 1;
        hyperlinkStartChar = 0;
        hyperlinkEndChar = 0;
        hyperlinkAddress = null;
        hyperlinkComponentInject = 0;
        listData = null;
        slideShow = null;
    }
    
    public void headingComponent(String headingtxt){
        type = 9;
        text = headingtxt;
    }
    
    public void imageComponent(String imgpath){
        type = 2;
        imageLoc = imgpath;
    }
    public void setParam(String height, String width, String caption, int just){
        this.width = width;
        this.height = height;
        this.text = caption;
        this.justification = just;
    }
    public int getJust(){
        return justification;
    }
    
    public void bodyComponent(String bodytxt, String fontChoice){
        this.type = 1;
        this.text = bodytxt;
        this.font = fontChoice;
    }
    public void slideShowComponent(SlideShowModel ss){
        this.type = 4;
        this.slideShow = ss;
    }
    
    public String getFontType(){
        return font;
    }
    
    public String getText(){
        return text;
    }
    
    public void listComponent(String[] listElements, int elementCount) {
        type = 6;
        listData = new String[elementCount];
        listData = listElements;
    }
    public int getComponentType(){
        return type;
    }
    
    public String[] getListData() {
        return listData;
    }
    
    public String getImagePath() {
        return imageLoc;
    }
    
    public double getWidth() {
        return Double.parseDouble(width);
    }
    
    public double getHeight() {
        return Double.parseDouble(height);
    }
    
    public void videoComponent(String string) {
        type = 3;
        imageLoc = string;
    }
    
    public void setParam(String height, String width, String caption) {
        this.width = width;
        this.height = height;
        this.text = caption;
    }
    
    public int fetchComponentType() {
        return type;
    }

    public SlideShowModel getSS() {
        return slideShow;
    }
}
