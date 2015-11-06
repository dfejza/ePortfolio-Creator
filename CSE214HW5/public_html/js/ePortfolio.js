/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

//Global variables
var IMG_PATH;
var VIDEO_PATH;
var ICON_PATH;
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


function Page(initImgFile, initCaption) {
    this.imgFile = initImgFile;
    this.caption = initCaption;
}

function initSlideshow() {
    IMG_PATH = "./img/";
    VIDEO_PATH = "./video/";
    //ICON_PATH = "./icons/";
    
    pages = new Array();
    var ePortfolioDataFile = "./json/exportData.json";
    loadData(ePortfolioDataFile);
}