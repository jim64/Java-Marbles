//main graphics class. Has been kept simple and a seperate Startscreen class
//and level class created to build the different scenes. 
//This class should just load screen handle input and do timings
//Unable to auto-test but tested via functionality
//button needs to be initiatialised here and passed to startscreen to 
//avoid need interdependency between this class and StartScreen

import javafx.application.*;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.scene.paint.*;
import javafx.animation.*;
import javafx.event.EventHandler;
import javafx.event.*;
import javafx.util.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.io.File;



public class Screen extends Application  {
  
  private StartScreen startScreen; 
  private Level l;
  private int height = 700;
  private int width = 500;
  private Stage stage;
  private Button play = new Button("Play");

  //initiates game
  public void start(Stage stage)  {
  
    this.stage = stage;  
    startScreen = new StartScreen(width, height); 
    Group g = startScreen.getGame(play); 
    loadMusic();
    Scene scene = new Scene(g, width, height, Color.BLACK);
    stage.setScene(scene);
    play.setOnAction(this::gameScreen);
    stage.setTitle("Don't Lose Your Marbles!");
    stage.show();
    startScreen.animate();
  
  }
 
  //loads main game screen : triggered from button on initial screen
  private void gameScreen(ActionEvent e)  {
   
    l = new Level(width, height);
    Group root = l.setUp();
    Scene scene = new Scene(root, width, height, Color.BLACK);
    stage.setTitle("Dont lose your marbles!"); 
    scene.setOnMousePressed(this::addBlocks);
    stage.setScene(scene);
    stage.show();
    timer.start();
  
  }
  
  //simple wrapper for mouse 
  private void addBlocks(MouseEvent e)  {

    l.addBlock(e);

  }
  
  //simple wrapper for animationTimer main game
  AnimationTimer timer = new AnimationTimer() {
    public void handle(long now) {
      l.update(now);     
    }
  };
  
  //loads music to play on continuous loop
  private void loadMusic()  {
    try {
      String mfile = "m.wav";
      Media m = new Media(new File(mfile).toURI().toString());
      MediaPlayer mPlayer = new MediaPlayer(m);
      mPlayer.setVolume(0.5);
      mPlayer.setCycleCount(MediaPlayer.INDEFINITE);
      mPlayer.play();
    } catch(Exception e) {
      System.out.println(e);
    }
  
  }

}

