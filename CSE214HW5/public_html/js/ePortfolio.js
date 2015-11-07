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
// nav bar text
var pageText01;
var pageText02;
var pageText03;
var pageText04;
var pageText05;
//page count
var numPages;

var header;
var footer;
var bannerImageLoc;

// DATA FOR CURRENT PAGE
var pages;
var currentPage;

// MODIFY CURRENT PAGE's DOM IN THIS FOLLOWING FUNCTION
function initPage() {
    //Get current page
    var fileName = location.href.split("/").slice(-1); 
    fileName = fileName[0];
    currentPage = 0;
    for(var i = 1; i < numPages; i++) {
        if(fileName === pages[i].pageTitle.concat(HTML_TAG)){
            currentPage = i;
        }
    }
    
    //Modify DOMs banner img loc
    document.getElementById("bannerImgID").src = bannerImageLoc;
    
    //Modify DOM's nav bar link text
    document.getElementById("navbar_indexlink").innerHTML = pages[0].pageTitle;
    
    // Generate the rest of the pages dynamically
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
    
}

function loadData(jsonFile) {
    $.getJSON(jsonFile, function(json) {
	loadPages(json);
	initPage();
    });
}

function loadPages(ePortfolioData) {
    pageText01 = ePortfolioData.pageText01;
    pageText02 = ePortfolioData.pageText02;
    pageText03 = ePortfolioData.pageText03;
    pageText04 = ePortfolioData.pageText04;
    pageText05 = ePortfolioData.pageText05;
    
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