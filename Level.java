//class to implement each level. Holds seperate arrays for the actual 
//graphics objects and the underlying logic objects to seperate these.

//due to interactions with all other classes unable to auto-test this class.
//Other logic only classes are auto-tested and this is tested via its 
//functionality

//some magic number remain : got rid of these as far as possible or where it
//made sense to use object or screen size. Others left in as they are not 
//relational - either positional or set up


import java.util.*;
import javafx.animation.*;
import javafx.beans.property.*;
import javafx.event.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.scene.shape.*;
import javafx.scene.transform.*;
import javafx.util.*;


public class Level {

  private int width;
  private int height;
  private int level = 0;
  private boolean change = false;
  private boolean playing = true;
  private ArrayList<Blocks> r;
  private ArrayList<Box> b;
  private ArrayList<Marble> m;
  private ArrayList<Sphere> lost;
  private ArrayList<Sphere> s;
  private IntegerProperty score;
  private int cnt = 0;
  private Marble m1;
  private Sphere s1;
  private Cylinder leftGoal;
  private Cylinder rightGoal;
  private Goal goal;
  private Group root;
  private Random rand = new Random();
  private GridPane pane;
  
  /*************************************************************
                          LEVEL SET UP
   *************************************************************/
  Level(int width, int height)  {
  
    this.width = width;
    this.height = height;
    
  }
  
  //initial setup class
  public Group setUp()  {
  
    root = new Group();
  
    r = new ArrayList<Blocks>(); 
    b = new ArrayList<Box>();
    m = new ArrayList<Marble>();
    s = new ArrayList<Sphere>();
  
    m1 = new Marble(width / 2, 10, 10);
    m1.setDirection(0);
    s1 = new Sphere(10);
    setSphere(s1);
    addLabels();
    addGoal();
       
    return root;
     
  }
  
  //initialises the next level by clearing current data, resetting scores
  //and marble count
  
  private void nextLevel()  {
  
    playing = true;
    cleanScreen(); 
    addGoal();
    score.setValue(0);
    cnt = 0;
  
  }
  
  //empties arraylists and clears group for level reset
  private void cleanScreen()  {
  
    root.getChildren().remove(pane);
   
    for(Box temp : b)
      root.getChildren().remove(temp);
    for(Sphere temp : s)
      root.getChildren().remove(temp);
      
    root.getChildren().remove(leftGoal);
    root.getChildren().remove(rightGoal);
    r.clear();
    b.clear();
    m.clear();
    s.clear();
  
  
  }
  
  //sets up initial sphere
  private void setSphere(Sphere s)  {
  
    s1.setMaterial(m1.getMarbleColour());
    moveSphere(s1, m1.getX(), m1.getY());
    root.getChildren().add(s1);
   
  }
  
  //adds labels for win / score section
  private void addLabels()  {
  
    Label text = new Label("WON : ");
    text.setStyle("-fx-text-fill: blue; -fx-font-size: 14;"+
                  " -fx-font-weight: bold;");
    text.setTranslateY(10);
    
    score = new SimpleIntegerProperty(0);
    Label screenScore = new Label();
    screenScore.setStyle("-fx-font-size: 14; -fx-font-weight: bold;");
    screenScore.textProperty().bind(score.asString());
    screenScore.setTranslateY(10);
    screenScore.setTranslateX(60); 
    screenScore.setTextFill(Color.BLUE); 
     
    root.getChildren().add(text);
    root.getChildren().add(screenScore);
  
  
  }
  
  //adds goal to screen. Goal formed of two cylinder with goal class
  //holding data
  private void addGoal()  {
  
    goal = new Goal(width / 2, height - 50);
    leftGoal = new Cylinder(goal.getGoalWidth(), goal.getGoalHeight());
    rightGoal = new Cylinder(goal.getGoalWidth(), goal.getGoalHeight());
    leftGoal.setMaterial(goal.getColour());
    rightGoal.setMaterial(goal.getColour());
    moveGoal();
    goal.setSpeed(level);
    
    root.getChildren().add(leftGoal);
    root.getChildren().add(rightGoal);
  
  }
  

  
  /*************************************************************
                            MOVEMENT
   **************************************************************/
  
  //simple wrapper to move blocks
  private void moveBlock(Box b, int x, int y)  {
  
    b.setTranslateX(x+50);
    b.setTranslateY(y);
  
  }
  
  //simple wrapper to move spheres
  private void moveSphere(Sphere s, int x, int y)  {
    
    s.setTranslateX(x);
    s.setTranslateY(y);

  }
  
  //simple wrapper to move two cylinders that make up posts
  private void moveGoal()  {
  
    leftGoal.setTranslateX(goal.getLeft());
    rightGoal.setTranslateX(goal.getRight());
    leftGoal.setTranslateY(goal.getTop());
    rightGoal.setTranslateY(goal.getTop());
  
  }
  
  
  /*************************************************************
                          LOGIC / ACTION
   *************************************************************/
  
  //adds new blocks and box to arraylists and group
  private void addNewBlock(int x, int y)  {
  
    Blocks n = new Blocks(100, x, y, m1.getSize()/2);
    Box b1 = new Box(100, 10, 3);

    b.add(b1);
    b1.setMaterial(n.getColour());
    moveBlock(b1, x, y);
      
    root.getChildren().add(b1);
    b1.toBack();
    r.add(n);
  
  }
  
  //checks mouse events : adds and remove blocks
  public void addBlock(MouseEvent e)  {
  
    int x = (int) e.getSceneX();
    int y = (int) e.getSceneY();
    
    if(e.isPrimaryButtonDown())  {
      if(y < height - 75)  {
        addNewBlock(x, y);
      }
    }
    else  {
      for(int i = 0; i < b.size(); i++)  {
        if(r.get(i).selected(x, y)) { 
          r.get(i).moveBlock(); 
          moveBlock(b.get(i), r.get(i).getX(), r.get(i).getY());
        }
      }
    }

  }
  
  //moves spheres along bottom of screen to win positions
  private void moveWinMarbles()  {
  
    for(int i = 0; i < m.size(); i++)  {
      int place = (m.indexOf(m.get(i)) * m.get(i).getSize()) +10;
      int x = m.get(i).getX();
      if(x < place -1 || x > place+1)  {
        m.get(i).move();
        moveSphere(s.get(i), m.get(i).getX(), m.get(i).getY());
      }
    }
  
  }
  
  //updates each frame : moves marble, checks if blocks touched and checks win
  public void update(long now)  {
    
    if(playing)  {   
      moveWinMarbles();
      boolean touched = false;

      for(Blocks b : r)  {
        if(b.touched(m1.getX(), m1.getY()))  {
          touched = true;
        }
        if (touched == false)
          m1.setDirection(0);
      }    
      if (touched == true)  {        
        m1.touchPosition(); 
        touched = false;
      }   
      m1.move();
      moveSphere(s1, m1.getX(), m1.getY());
      goal.move();
      moveGoal();
      checkWin();  
    }
  
  }
  
  //increments level counter and moves game on
  private void newLevel(ActionEvent e)  {

    level++;
    nextLevel();
    
  }
  
  //sets up win panel display. Sets in gridpane so can all be located at 
  //same time
  private void winPanel()  {
  
    playing = false;
    int resultInt = score.intValue();
    String result = Integer.toString(score.intValue());
      
    pane = new GridPane();
    Label win = new Label("You scored: "+result+" out of 5");
    win.setStyle("-fx-text-fill: blue; -fx-font-size: 25; "+
                 "-fx-font-weight: bold");

    Button next = new Button("Next Level");
    next.setStyle("-fx-text-fill: blue; -fx-font-size: 14; "+
                  "-fx-font-weight: bold");
    next.setTranslateX(100);
    next.setOnAction(this::newLevel);
      
    pane.add(win, 0, 0);
    pane.add(next, 0, 1);
    adjustPane(pane);
      
  }
  
  //adjusts pane settings
  private void adjustPane(GridPane pane)  {
  
    pane.setMaxWidth(width);
    pane.setMaxHeight(height / 3);
    pane.setVgap(100);
    pane.setTranslateY(height / 3);
    pane.setTranslateX(width / 6);
    root.getChildren().add(pane);
   
  }
  
   
  //checks for win and re-directs marbles
  private void checkWin()  {
  
    if(cnt == 5)  {
      winPanel();
    }

    if(m1.getY() > height - 30)  {
      if(goal.isGoal(m1.getX()))  {      
        score.set(score.get()+1);
        addWinningMarbles();
      }
      if(playing)  {
        moveMarble();
      }
    }

  }
  
  //adds new marbles for each win, adds both to sphere array and to 
  //marble array for graphics / logic
  private void addWinningMarbles()  {
  
    Marble temp = new Marble(m1.getX(), height - 20, 10);
    
    if(temp.getX() < (m.size() * (temp.getSize() * 2)))  
      temp.setDirection(1);
    else  
      temp.setDirection(-1);
 
    temp.setMarbleColour(m1.getMarbleColour());
    Sphere temp2 = new Sphere(10);
    temp2.setMaterial(m1.getMarbleColour());
    moveSphere(temp2, temp.getX(), temp.getY());
    m.add(temp);
    s.add(temp2);
    root.getChildren().add(temp2);
    
  }
  
  //moves marble back to top of screen, choosing new colour and position
  //and sets sphere to right location.
  private void moveMarble()  {
    
    int next = rand.nextInt() % width;
    if( next < 25 || next > width -25) next = width/2;

    Colour c = new Colour();
    m1.setMarbleColour(c.getRandomColour());
    m1.newPosition(next, 10);
    s1.setMaterial(m1.getMarbleColour());
    moveSphere(s1, m1.getX(), m1.getY());
    cnt++;
  
  }

  
}


