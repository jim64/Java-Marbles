//class to gather info for start screen and to run the animation
//Timeline used with this animation as no interaction required 
//for this animation.
//pane used for button and label so can be moved together and 
//keep relative distances
//No automated testing possible for this class 
//magic numbers appear in this class due to need to specifically locate
//objects. Where possible, and it makes sense to do so, these have been
//linked to the screen size


import java.util.*;
import javafx.animation.*;
import javafx.beans.property.DoubleProperty;
import javafx.event.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.scene.shape.*;
import javafx.scene.transform.*;
import javafx.util.*;


public class StartScreen  {
  
  private Group g0;
  private GridPane pane;
  private int width;
  private int height;
  private ArrayList<Sphere> s;
 
  //constructor that takes screen height / width
  StartScreen(int x, int y)  {
    
    width = x;
    height = y;
  
  }
  
  //simple wrapper to create all objects for menu screen
  public Group getGame(Button play)  {
  
    g0 = new Group();
    create(play);
    layout(play);
    g0.getChildren().add(pane);
    addMarbles();
    return g0;
  
  } 
  
  //initiates all objects and adjusts style / place
  private void create(Button play) {
  
    pane = new GridPane();
    Label intro = new Label("Don't Lose Your Marbles!");
    Label l1 = new Label("Left Click to Place Block");
    Label l2 = new Label("Right Click to remove Block");
    play.setTranslateX(80);
    intro.setStyle("-fx-text-fill: silver; -fx-font-size: 16; "+
    "-fx-font-weight: bold; fx-padding : 20;");
    l1.setStyle("-fx-text-fill: silver; -fx-font-size: 12;");
    l2.setStyle("-fx-text-fill: silver; -fx-font-size: 12;");
    play.setStyle("-fx-font-size: 14; -fx-font-weight: bold; "+
    "-fx-text-fill: black;");
    l1.setTranslateX(35);
    l2.setTranslateX(25);
    pane.add(intro, 0, 0);
    pane.add(l1, 0, 2);
    pane.add(l2, 0, 3);
    pane.add(play, 0, 1);

  }
    
  //adds objects to pane and adjust pane
  private void layout(Button play) {
    
    pane.setMaxWidth(width / 2);
    pane.setMaxHeight(height / 6);
    pane.setVgap(50);
    pane.setTranslateY(height / 3);
    pane.setTranslateX(width / 4);
    
  }   
    
      
  //adds sphere to group and places in array for animation purposes    
  private void addMarbles()  {
  
     s = new ArrayList<Sphere>();
     int height0 = 525;
     int j = 0;
     Colour c = new Colour();
     
     for(int i = 0; i < 8; i++)  {
      if(i > 3) height0 = 100;
      if(i > 4) j--;
      if(i <= 3) j++;
      Sphere m0 = new Sphere(10);
      m0.setTranslateX(j * 25);
      m0.setTranslateY(height0);
      m0.setMaterial(c.getRandomColour());
      s.add(m0);
      g0.getChildren().add(m0);
    
     } 
     
  }

  //animates via a timeline as no interaction needed with these on this screen
  public void animate()  {
     
     Translate t = new Translate(1, 0);
     for(Sphere m0 : s)  {
      m0.getTransforms().add(t);
     }
     Duration d = Duration.seconds(5);
     DoubleProperty length = t.xProperty();
     KeyValue key = new KeyValue(length, width - 120);
     KeyFrame frame = new KeyFrame(d, key);
     Timeline tl = new Timeline();
     tl.getKeyFrames().add(frame);
     tl.setAutoReverse(true);
     tl.setCycleCount(Animation.INDEFINITE);
     tl.play();
  
  }

}
