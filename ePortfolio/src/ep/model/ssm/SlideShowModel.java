/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package ep.model.ssm;
import ep.model.ssm.Slide;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * This class manages all the data associated with a slideshow.
 *
 * @author McKilla Gorilla & _____________
 */
public class SlideShowModel {
    SlideShowMakerView ui;
    String title;
    ObservableList<Slide> slides;
    Slide selectedSlide;
    
    public SlideShowModel(SlideShowMakerView initUI) {
        ui = initUI;
        slides = FXCollections.observableArrayList();
        reset();
    }

    public SlideShowModel() {
        slides = FXCollections.observableArrayList();
        reset();
    }
    
    // ACCESSOR METHODS
    public boolean isSlideSelected() {
        return selectedSlide != null;
    }
    
    public boolean isSelectedSlide(Slide testSlide) {
        return selectedSlide == testSlide;
    }
    
    public ObservableList<Slide> getSlides() {
        return slides;
    }
    
    public Slide getSelectedSlide() {
        return selectedSlide;
    }
    
    public String getTitle() {
        return title;
    }
    
    // MUTATOR METHODS
    public void setSelectedSlide(Slide initSelectedSlide) {
        selectedSlide = initSelectedSlide;
    }
    
    public void setTitle(String initTitle) {
        title = initTitle;
    }
    
    // SERVICE METHODS
    
    /**
     * Resets the slide show to have no slides and a default title.
     */
    public void reset() {
        slides.clear();
        title = "Default Title";
        selectedSlide = null;
    }
    
    /**
     * Adds a slide to the slide show with the parameter settings.
     * @param initImageFileName File name of the slide image to add.
     * @param initImagePath File path for the slide image to add.
     * @param initCaption Caption for the slide image to add.
     */
    public void addSlide(   String initImageFileName,
            String initImagePath,
            String initCaption) {
        Slide slideToAdd = new Slide(initImageFileName, initImagePath, initCaption);
        slides.add(slideToAdd);
        ui.reloadSlideShowPane();
    }
    
    public void addJSonSlide(   String initImageFileName,
            String initImagePath,
            String initCaption) {
        Slide slideToAdd = new Slide(initImageFileName, initImagePath, initCaption);
        slides.add(slideToAdd);
    }
    
    /**
     * Removes the currently selected slide from the slide show and
     * updates the display.
     */
    public void removeSelectedSlide() {
        if (isSlideSelected()) {
            slides.remove(selectedSlide);
            selectedSlide = null;
            ui.reloadSlideShowPane();
        }
    }
    
    /**
     * Moves the currently selected slide up in the slide
     * show by one slide.
     */
    public void moveSelectedSlideUp() {
        if (isSlideSelected()) {
            moveSlideUp(selectedSlide);
            ui.reloadSlideShowPane();
        }
    }
    
    // HELPER METHOD
    private void moveSlideUp(Slide slideToMove) {
        int index = slides.indexOf(slideToMove);
        if (index > 0) {
            Slide temp = slides.get(index);
            slides.set(index, slides.get(index-1));
            slides.set(index-1, temp);
        }
    }
    
    /**
     * Moves the currently selected slide down in the slide
     * show by one slide.
     */
    public void moveSelectedSlideDown() {
        if (isSlideSelected()) {
            moveSlideDown(selectedSlide);
            ui.reloadSlideShowPane();
        }
    }
    
    // HELPER METHOD
    private void moveSlideDown(Slide slideToMove) {
        int index = slides.indexOf(slideToMove);
        if (index < (slides.size()-1)) {
            Slide temp = slides.get(index);
            slides.set(index, slides.get(index+1));
            slides.set(index+1, temp);
        }
    }
}