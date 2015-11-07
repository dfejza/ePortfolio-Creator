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

// MODIFY CURRENT PAGE's DOM IN THIS FOLLOWING FUNCTION
function initPage() {
    //Get current page
    var fileName = location.href.split("/").slice(-1); 
    fileName = fileName[0];
    currentPage = 0;
    for(var i = 1; i < numPages; i++) {
        if(fileName.replace(/%20/g, " ") === pages[i].pageTitle.concat(HTML_TAG)){
            currentPage = i;
        }
    }
    
    //Modify DOMs banner img loc
    document.getElementById("bannerImgID").src = bannerImageLoc;
    
    //Modify DOM's nav bar link text for index.html
    document.getElementById("navbar_indexlink").innerHTML = pages[0].pageTitle;
    
    // Generate the rest of the page's hyperlinks dynamically
    var a;
    for (var i = 1; i < numPages; i++) {
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
             
         }else if(currentComponent.type == 4){
             
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


/*function Page(initImgFile, initCaption) {
    this.imgFile = initImgFile;
    this.caption = initCaption;
}*/

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