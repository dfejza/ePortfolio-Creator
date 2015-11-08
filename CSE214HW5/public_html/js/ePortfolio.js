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

var header;
var footer;
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
    document.getElementById("color_css").href=pages[currentPage].cssColor;
    document.getElementById("font_css").href=pages[currentPage].cssLayout;
    document.getElementById("layout_css").href=pages[currentPage].cssFont;

    
    //Modify DOMs banner img loc
    document.getElementById("bannerText").innerHTML = header;
    document.getElementById("bannerImgID").src = bannerImageLoc;
    
    //Modify DOM's nav bar link text for index.html
    document.getElementById("navbar_indexlink").innerHTML = pages[0].pageTitle;
    
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
     for (var i = 0; i < pages[currentPage].numComponents; i++) {
         currentComponent = pages[currentPage].component[i];
         
         // There is an int vale for each component, depicting type
         // type == 1 -> Text
         // type == 2 -> img
         // type == 3 -> video
         // type == 4 -> SlideShow
         // type == 5 -> Hyperlink 
         // type == 9 -> Header
         if(currentComponent.type == 1){
             
             var para = document.createElement("P");                       // Create a <p> element
             var t = document.createTextNode(currentComponent.text);      // Create a text node
             para.appendChild(t);
             para.id = "c".concat(i);            // Create a ID for later use
             document.getElementById("content_body").appendChild(para);  
             
         }else if(currentComponent.type == 2){
             
             var elem = document.createElement("img");
             elem.setAttribute("src", currentComponent.imageLoc);
             elem.id = "c".concat(i);
             //elem.setAttribute("height", "768");
             //elem.setAttribute("width", "1024");
             document.getElementById("content_body").appendChild(elem);
             
         }else if(currentComponent.type == 3){
             
             // Video function
            var video = document.createElement('video');

             /*// First create the canvas
            var canvas;
             canvas = document.createElement('canvas');
             canvas.id = "canvas"+i;
             document.getElementById("content_body").appendChild(canvas);*/
            
             // Next create the video
            video.src = currentComponent.videoLoc;
            video.controls = true;
            video.id = "c".concat(i);
            document.getElementById("content_body").appendChild(video);   
             
         }else if(currentComponent.type == 4){
             var tempSlideShow = new Array();
             //Create a slideshow object
            for (var j = 0; j < currentComponent.slideShow.numOfImages; j++) {
                var slide = new Slide(currentComponent.slideShow.image[j], currentComponent.slideShow.caption[j]);
                tempSlideShow[j] = slide;
            }
            tempSlideShow.numOfImages = currentComponent.slideShow.numOfImages;
            tempSlideShow.currentSlide = 0;
            slides[numOfSlideshows] = tempSlideShow;
            
             
             //Slide Show Component
             var elem = document.createElement("div");
             elem.id = "c".concat(i);
             elem.className = "SlideShowImage";
             document.getElementById("content_body").appendChild(elem);
             
             var slideshowimg = document.createElement("img");
             slideshowimg.id = "ssi".concat(i);
             slideshowimg.src = tempSlideShow[0].imgFile;
             document.getElementById("c".concat(i)).appendChild(slideshowimg);
             var caption = document.createElement("P");
             caption.id = "ssc".concat(i);
             caption.innerHTML = tempSlideShow[0].caption;
             document.getElementById("content_body").appendChild(caption);
              
             elem = document.createElement("div");
             elem.id = "slideshow_controls".concat(i);
             elem.className = "slideshow_controls";
             document.getElementById("content_body").appendChild(elem);
             
            var btn = document.createElement("BUTTON");        // Create a <button> element
            var t = document.createTextNode("Previous");       // Create a text node
            btn.id = "btnP"+i;
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

                    document.getElementById("ssi"+thenum).src = slides[thenum][slides[thenum].currentSlide].imgFile;
                    document.getElementById("ssc"+thenum).innerHTML = slides[thenum][slides[thenum].currentSlide].caption;
                };
            })(r);
            document.getElementById("slideshow_controls".concat(i)).appendChild(btn);                    // Append <button>
            
            btn = document.createElement("BUTTON");        // Create a <button> element
            t = document.createTextNode("Next");       // Create a text node
            btn.id = "btnN"+i;
            btn.appendChild(t);                                // Append the text to <button>
             (function(r){
                btn.onclick = function () {
                    var thenum = r.replace( /^\D+/g, ''); 
                    thenum = parseInt(thenum);

                    //increment slides
                    slides[thenum].currentSlide++;
                    if(slides[thenum].currentSlide==slides[thenum].numOfImages)
                        slides[thenum].currentSlide = 0;

                    document.getElementById("ssi"+thenum).src = slides[thenum][slides[thenum].currentSlide].imgFile;
                    document.getElementById("ssc"+thenum).innerHTML = slides[thenum][slides[thenum].currentSlide].caption;
                };
            })(r);
            document.getElementById("slideshow_controls".concat(i)).appendChild(btn);                    // Append <button>
            
            numOfSlideshows++;
             
         }else if(currentComponent.type == 5){
             var temp =  document.getElementById(currentComponent.hyperlinkComponentInject).innerHTML;
             var linkName = temp.substring(currentComponent.hyperlinkStartChar, currentComponent.hyperlinkEndChar)
             var tempFilling = "".concat("<a href=",currentComponent.hyperlinkAddress,"> ",linkName,"</a>");
             
             var tempPrefix = temp.slice(0, currentComponent.hyperlinkStartChar);
             var tempSuffix = temp.slice(currentComponent.hyperlinkEndChar, temp.length);;
             document.getElementById(currentComponent.hyperlinkComponentInject).innerHTML = "".concat(tempPrefix,tempFilling,tempSuffix);
             
         }else if(currentComponent.type == 6){
             
         }else if(currentComponent.type == 9){
             var elem = document.createElement("H1");                       // Create a <p> element
             var t = document.createTextNode(currentComponent.text);      // Create a text node
             elem.appendChild(t);
             elem.id = "c".concat(i);            // Create a ID for later use
             document.getElementById("content_body").appendChild(elem);  
         }
     }
}

function loadData(jsonFile) {
    $.getJSON(jsonFile, function(json) {
	loadPages(json);
	initPage();
    });
}

function loadPages(ePortfolioData) {
    numPages = ePortfolioData.numPages;
    header = ePortfolioData.header;
    footer = ePortfolioData.footer;
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
    var ePortfolioDataFile = "./json/exportData.json";
    loadData(ePortfolioDataFile);
}

function Slide(initImgFile, initCaption) {
    this.imgFile = initImgFile;
    this.caption = initCaption;
}