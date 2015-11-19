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
    
    
    
}
