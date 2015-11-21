/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ep.model;

/**
 *
 * @author linti
 */
public class Component {
    int type;
    String text;
    String font;
    String imageLoc;
    String videoLoc;
    int hyperlinkStartChar;
    int hyperlinkEndChar;
    String hyperlinkAddress;
    int hyperlinkComponentInject;
    String listData[];
    SlideShow slideShow;
    
    public Component(){
        type = 0;
        font = null;
        text = null;
        imageLoc = null;
        videoLoc = null;
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
    
    public void bodyComponent(String bodytxt, String fontChoice){
        type = 1;
        text = bodytxt;
        font = fontChoice;
    }
    
    
    
}
