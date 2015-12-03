/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package ep.model;

import ep.model.ssm.SlideShow;
import ep.model.ssm.SlideShowModel;
import javafx.scene.control.ListView;
import javax.json.JsonValue;

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
        imageLoc = "null";
        width = "1024.0";
        height = "768.0";
        justification = 1;
        hyperlinkStartChar = 0;
        hyperlinkEndChar = 0;
        hyperlinkAddress = "null";
        hyperlinkComponentInject = 0;
        listData = null;
        slideShow = null;
    }
    
    public Component(int type, String font, String text, String imageLoc, int width, int height, int justification, int hsc,
            int hec, String hladd, int hlci, String[] listdata, SlideShowModel slideshow){
        this.type = type;
        this.font = font;
        this.text = text;
        this.imageLoc = imageLoc;
        this.width = Integer.toString(width);
        this.height = Integer.toString(height);
        this.justification = justification;
        this.hyperlinkStartChar = hsc;
        this.hyperlinkEndChar = hec;
        this.hyperlinkAddress = hladd;
        this.hyperlinkComponentInject = hlci;
        this.listData = listdata;
        this.slideShow = slideshow;
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

    public int getAllign() {
        return justification;
    }

    /**
     * @return the hyperlinkStartChar
     */
    public int getHyperlinkStartChar() {
        return hyperlinkStartChar;
    }

    /**
     * @param hyperlinkStartChar the hyperlinkStartChar to set
     */
    public void setHyperlinkStartChar(int hyperlinkStartChar) {
        this.hyperlinkStartChar = hyperlinkStartChar;
    }

    /**
     * @return the hyperlinkEndChar
     */
    public int getHyperlinkEndChar() {
        return hyperlinkEndChar;
    }

    /**
     * @param hyperlinkEndChar the hyperlinkEndChar to set
     */
    public void setHyperlinkEndChar(int hyperlinkEndChar) {
        this.hyperlinkEndChar = hyperlinkEndChar;
    }

    /**
     * @return the hyperlinkAddress
     */
    public String getHyperlinkAddress() {
        return hyperlinkAddress;
    }

    /**
     * @param hyperlinkAddress the hyperlinkAddress to set
     */
    public void setHyperlinkAddress(String hyperlinkAddress) {
        this.hyperlinkAddress = hyperlinkAddress;
    }

    /**
     * @return the hyperlinkComponentInject
     */
    public int getHyperlinkComponentInject() {
        return hyperlinkComponentInject;
    }

    /**
     * @param hyperlinkComponentInject the hyperlinkComponentInject to set
     */
    public void setHyperlinkComponentInject(int hyperlinkComponentInject) {
        this.hyperlinkComponentInject = hyperlinkComponentInject;
    }
}
