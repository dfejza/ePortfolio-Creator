/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

//Global variables
var IMG_PATH;
var VIDEO_PATH;
var ICON_PATH;
var HTML_TAG;
var HTML_PRETAG;

//page count
var numPages;

var studentName;
var cssColor;
var cssLayout;
var bannerImageLoc;

// DATA FOR PAGES
var pages;
var currentPage;
var currentComponent;

// DATA FOR SLIDESHOW
var numOfSlideshows;
var slides = new Array();
var currentSlide;

// MODIFY CURRENT PAGE's DOM IN THIS FOLLOWING FUNCTION
function initPage() {   
    var slideshowbuttoncount = 0;
    numOfSlideshows = 0;
    //Get current page
    var fileName = location.href.split("/").slice(-1); 
    fileName = fileName[0];
    currentPage = 0;
    for(var i = 1; i < numPages; i++) {
        if(fileName.replace(/%20/g, " ") === pages[i].pageTitle.concat(HTML_TAG)){
            currentPage = i;
        }
    }
    
    // LOAD THE CSS STYLE,FONT AND COLOR
    document.getElementById("color_css").href=  "./css/color/" + cssColor;
    document.getElementById("font_css").href="./css/font/" + pages[currentPage].cssFont;
    document.getElementById("layout_css").href="./css/layout/" + cssLayout;
    
    
    //Modify DOMs banner img loc
    document.getElementById("bannerText").innerHTML = studentName;
    
    if(bannerImageLoc!="No Banner Image"){
        var imageName = bannerImageLoc.replace(/^.*[\\\/]/, '');
        document.getElementById("bannerImgID").src = "./img/"+imageName;
    }else{
        document.getElementById("banner").removeChild(document.getElementById("bannerImgID"));
    }
    
    //Modify DOM's nav bar link text for index.html
    document.getElementById("navbar_indexlink").innerHTML = pages[0].pageTitle;
    
    //Create header and footer for current page, if exists.
    var header = pages[currentPage].pageHeader;
    var footer = pages[currentPage].pageFooter;
    if(header.lenght!=0){
        var iDiv = document.createElement('div');
        iDiv.id = 'header';
        var h = document.createElement("H1");
        h.innerHTML = header;
        iDiv.appendChild(h);
        //document.getElementsByTagName('body')[0].appendChild(iDiv);
        document.getElementsByTagName('body')[0].insertBefore(iDiv, document.getElementById("content"));
    }
    // Generate the rest of the page's hyperlinks dynamically
    var a;
    for (var i = 1; i < numPages; i++) {        
        //Create the link
        a = document.createElement('a');
        a.setAttribute("href", HTML_PRETAG.concat(pages[i].pageTitle.concat(HTML_TAG)));
        a.innerHTML = pages[i].pageTitle;
        // apend the anchor to the body
        // of course you can append it almost to any other dom element
        document.getElementById('navbar').appendChild(a);
        
        //document.divs[i].innerHTML = pages[i].pageTitle;
        //document.divs[i].href = pages[i].pageTitle.concat(HTML_TAG);
    }
    
    //Generat the Body of the selected page
    for (var i = 0; i < pages[currentPage].component.length; i++) {
        currentComponent = pages[currentPage].component[i];
        document.title = pages[currentPage].pageTitle;
        
        // There is an int vale for each component, depicting type
        // type == 1 -> Text
        // type == 2 -> img
        // type == 3 -> video
        // type == 4 -> SlideShow
        // type == 5 -> Hyperlink 
        // type == 9 -> Header
        if(currentComponent.type == 1){
            
            var para = document.createElement("P");                       // Create a <p> element
            var text = currentComponent.text;
            //[url=http://www.example.com/]Example[/url]
            while (text.indexOf("[url=") > -1){
                var startidx = text.indexOf("[url=");
                var endidx = text.indexOf("[/url]");
                var slectedString  = text.substring(startidx, endidx+6);
                var substring  = text.substring(startidx, endidx+6);
                substring = substring.replace(/\[url=([^\s\]]+)\s*\](.*(?=\[\/url\]))\[\/url\]/g, '<a href="$1">$2</a>');
                text = text.replace(slectedString, substring);
            }
            para.innerHTML = text;
            //change the front of the component
            var type = currentComponent.compFont;
            para.style.fontFamily = type;
            /*
            if(type=="Alegreya Sans"){
                    text.setStyle("-fx-font-family: Alegreya Sans;");
                    getStylesheets().add("http://fonts.googleapis.com/css?family=Alegreya+Sans");
                }else if(type=="Lato"){
                    text.setStyle("-fx-font-family: Lato;");
                    getStylesheets().add("http://fonts.googleapis.com/css?family=Lato&subset=latin");
                }else if(type=="Indie Flower"){
                    text.setStyle("-fx-font-family: Indie Flower;");
                    getStylesheets().add("https://fonts.googleapis.com/css?family=Indie+Flower");
                }else if(type=="Titillium Web"){
                    text.setStyle("-fx-font-family: Titillium Web;");
                    getStylesheets().add("https://fonts.googleapis.com/css?family=Titillium+Web");
                }else if(type=="Sigmar One"){
                    text.setStyle("-fx-font-family: Sigmar One;");
                    getStylesheets().add("https://fonts.googleapis.com/css?family=Sigmar+One");
            }*/
            para.id = "c".concat(i);            // Create a ID for later use
            document.getElementById("content_body").appendChild(para);  
            
        }else if(currentComponent.type == 2){
            
            var elem = document.createElement("img");
            var x = document.createElement("FIGCAPTION");
            var imageName = currentComponent.imageLoc.replace(/^.*[\\\/]/, '');
            elem.setAttribute("src", "./img/"+imageName);
            elem.id = "c".concat(i);
            elem.className = "image_class";
            elem.setAttribute("height", currentComponent.height);
            elem.setAttribute("width", currentComponent.width);
            
            //get allign property
            if(currentComponent.allignment==1){
                //left
                elem.setAttribute("align", "left");
            }else if (currentComponent.allignment==2){
                //right
                elem.setAttribute("align", "right");
            }
            x.className = "imageCaption_class";
            x.textContent=currentComponent.text;
            //x.createTextNode(currentComponent.text);
            var z = document.createElement("div");
            z.className = "images_class";
            z.appendChild(elem);
            z.appendChild(x);
            document.getElementById("content_body").appendChild(z);
            
        }else if(currentComponent.type == 3){
            
            // Video function
            var video = document.createElement('video');
            
            /*// First create the canvas
            var canvas;
             canvas = document.createElement('canvas');
             canvas.id = "canvas"+i;
             document.getElementById("content_body").appendChild(canvas);*/
            
            // Next create the video
            var imageName = currentComponent.imageLoc.replace(/^.*[\\\/]/, '');
            video.src = "./video/"+imageName;
            video.setAttribute("height", currentComponent.height);
            video.setAttribute("width", currentComponent.width);
            video.controls = true;
            video.className = "video_class";
            video.id = "c".concat(i);
            document.getElementById("content_body").appendChild(video);   
            
        }else if(currentComponent.type == 4){
            var tempSlideShow = new Array();
            var test = currentComponent.slideShow.length;
            //Create a slideshow object
            for (var j = 0; j < test; j++) {
                var slide = new Slide(currentComponent.slideShow[j].image_file_name, currentComponent.slideShow[j].caption);
                tempSlideShow[j] = slide;
            }
            tempSlideShow.numOfImages = test;
            tempSlideShow.currentSlide = 0;
            slides[numOfSlideshows] = tempSlideShow;
            
            
            //Slide Show Component
            var elem = document.createElement("div");
            elem.id = "c".concat(i);
            elem.className = "SlideShow";
            document.getElementById("content_body").appendChild(elem);
            
            var slideshowimg = document.createElement("img");
            slideshowimg.id = "ssi".concat(slideshowbuttoncount);
            slideshowimg.className = "SlideShowImage";
            slideshowimg.src = "./img/"+tempSlideShow[0].imgFile;
            document.getElementById("c".concat(i)).appendChild(slideshowimg);
            var caption = document.createElement("P");
            caption.id = "ssc".concat(slideshowbuttoncount);
            caption.className = "SlideShowCaption";
            caption.innerHTML = tempSlideShow[0].caption;
            document.getElementById("content_body").appendChild(caption);
            
            elem = document.createElement("div");
            elem.id = "slideshow_controls".concat(i);
            elem.className = "slideshow_controls";
            document.getElementById("content_body").appendChild(elem);
            
            var btn = document.createElement("BUTTON");        // Create a <button> element
            btn.className = "SlideShowButtonPrev";
            var t = document.createTextNode("Previous");       // Create a text node
            btn.id = "btnP"+slideshowbuttoncount;
            r = btn.id;
            btn.appendChild(t);                                // Append the text to <button>
            // Use an immediate function to dynamically create slideshows
            (function(r){
                btn.onclick = function () {
                    var thenum = r.replace( /^\D+/g, ''); 
                    thenum = parseInt(thenum);
                    //increment slides
                    slides[thenum].currentSlide--;
                    if(slides[thenum].currentSlide==-1)
                        slides[thenum].currentSlide = slides[thenum].numOfImages-1;
                    
                    document.getElementById("ssi"+thenum).src = "./img/"+slides[thenum][slides[thenum].currentSlide].imgFile;
                    document.getElementById("ssc"+thenum).innerHTML = slides[thenum][slides[thenum].currentSlide].caption;
                };
            })(r);
            document.getElementById("slideshow_controls".concat(i)).appendChild(btn);                    // Append <button>
            
            btn = document.createElement("BUTTON");        // Create a <button> element
            t = document.createTextNode("Next");       // Create a text node
            btn.id = "btnN"+slideshowbuttoncount;
            btn.className = "SlideShowButtonNext";
            btn.appendChild(t);                                // Append the text to <button>
            (function(r){
                btn.onclick = function () {
                    var thenum = r.replace( /^\D+/g, ''); 
                    thenum = parseInt(thenum);
                    //increment slides
                    slides[thenum].currentSlide++;
                    if(slides[thenum].currentSlide==slides[thenum].numOfImages)
                        slides[thenum].currentSlide = 0;
                    
                    document.getElementById("ssi"+thenum).src = "./img/"+slides[thenum][slides[thenum].currentSlide].imgFile;
                    document.getElementById("ssc"+thenum).innerHTML = slides[thenum][slides[thenum].currentSlide].caption;
                };
            })(r);
            document.getElementById("slideshow_controls".concat(i)).appendChild(btn);                    // Append <button>
            
            numOfSlideshows++;
            slideshowbuttoncount = slideshowbuttoncount + 1;
            
        }else if(currentComponent.type == 5){
            var temp =  document.getElementById(currentComponent.hyperlinkComponentInject).innerHTML;
            var linkName = temp.substring(currentComponent.hyperlinkStartChar, currentComponent.hyperlinkEndChar)
            var tempFilling = "".concat("<a href=",currentComponent.hyperlinkAddress,"> ",linkName,"</a>");
            
            var tempPrefix = temp.slice(0, currentComponent.hyperlinkStartChar);
            var tempSuffix = temp.slice(currentComponent.hyperlinkEndChar, temp.length);;
            document.getElementById(currentComponent.hyperlinkComponentInject).innerHTML = "".concat(tempPrefix,tempFilling,tempSuffix);
            
        }else if(currentComponent.type == 6){
            // LISTS
            var listContainer = document.createElement("div");
            document.getElementById("content_body").appendChild(listContainer);
            var listElement = document.createElement("ul");
            listContainer.appendChild(listElement);
            var numberOfListItems = currentComponent.listData.length;
            for( var iijj =  0 ; iijj < numberOfListItems ; ++iijj){
                
                // create a <li> for each one.
                var listItem = document.createElement("li");
                
                // add the item text
                var text = currentComponent.listData[iijj];
                text = text.replace(/\[url=([^\s\]]+)\s*\](.*(?=\[\/url\]))\[\/url\]/g, '<a href="$1">$2</a>');
                listItem.innerHTML = text;
                
                // add listItem to the listElement
                listElement.appendChild(listItem);
                
            }
            
            
        }else if(currentComponent.type == 9){
            var elem = document.createElement("H1");                       // Create a <p> element
            elem.className = "header_class";
            var t = document.createTextNode(currentComponent.text);      // Create a text node
            elem.appendChild(t);
            elem.id = "c".concat(i);            // Create a ID for later use
            document.getElementById("content_body").appendChild(elem);  
        }
    }
    
    if(footer.lenght!=0){
        var iDiv = document.createElement('div');
        iDiv.id = 'footer';
        var h = document.createElement("H1");
        h.innerHTML = footer;
        iDiv.appendChild(h);
        document.body.appendChild(iDiv);
        //document.getElementsByTagName('body')[0].insertAfter(iDiv, document.getElementById("content"));
    }
}

function loadData(jsonFile) {
    $.getJSON(jsonFile, function(json) {
	loadPages(json);
	initPage();
    });
}

function loadPages(ePortfolioData) {
    numPages = ePortfolioData.page.length;
    studentName = ePortfolioData.student_name;
    cssColor = ePortfolioData.cssColor;
    cssLayout = ePortfolioData.cssLayout;
    bannerImageLoc = ePortfolioData.bannerImageLoc;
    
    for (var i = 0; i < numPages; i++) {
	var rawPage = ePortfolioData.page[i];
	//var page = new Page(rawSlide.image_file_name, rawSlide.caption);
	pages[i] =  ePortfolioData.page[i];
    }
}

function initEPortfolio() {
    IMG_PATH = "./img/";
    VIDEO_PATH = "./video/";
    //ICON_PATH = "./icons/";
    HTML_PRETAG = "./";
    HTML_TAG = ".html";
    
    pages = new Array();
    var ePortfolioDataFile = "./data/exportData.json";
    loadData(ePortfolioDataFile);
}

function Slide(initImgFile, initCaption) {
    this.imgFile = initImgFile;
    this.caption = initCaption;
}