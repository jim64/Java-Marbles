//class to hold goal position and size. Also check to see if x, y is wihtin
//the goal bounds
//called by level class.

import javafx.scene.paint.*;

public class Goal  {

  private int x = 0;
  private int y = 0;
  private int width = 50;
  private int speed = 0;
  private int direction = -1;
  private int postWidth = 5;
  private int postHeight = 20;
  private int maxMove = 440;
  private PhongMaterial colour;
  
  
  //constructor sets initial placement and colour
  Goal(int x0, int y0)  {
    
    x = x0;
    y = y0;
    Colour c = new Colour();
    colour = c.getGoalColour();
  
  }
  
  //used to adjust goal speed for level selection
  public void setSpeed(int speed0)  {
  
    speed = speed0;
  
  }
  
  
  //next 6 methods just to return copies on values
  public PhongMaterial getColour()  {
  
    PhongMaterial temp = colour;
    
    return temp;
  
  }
  
  public int getGoalWidth()  {
  
    int temp = postWidth;
    
    return temp;
  
  }
  
  public int getGoalHeight()  {
  
    int temp = postHeight;
    
    return temp;
  
  }
  
    
  public int getLeft()  {
  
    int temp = x;
    
    return x;
  
  }
  
  public int getRight()  {
  
    int temp = x + width;
    
    return temp;
    
  }
  
  public int getTop()  {
  
   int temp = y;
   
   return temp; 
    
  }
  
  //checks whether x, y is within goal bounds. Used to check win
  public boolean isGoal(int x0)  {
  
    if((x0 > x) && (x0 < x+width))
      return true;
    
    return false;
  
  }
  
  //moves x position by speed value
  public void move()  {
    
    checkEdge();
    
    if(direction == -1)  {
      x -= speed;
    }
    else  {
      x += speed;
    }
  
  }

  //checks whether at edge of screen and adjusts to stay in bounds
  //maxMove allows for screen size changes
  private void checkEdge()  {
  
   if(x < 10) direction  = 1;
   if(x > maxMove) direction = -1;
  
  }
  
  private void test()  {
  
    assert(x == 10);
    assert(y == 20);
    setSpeed(1);
    assert(speed == 1);
    assert(getGoalWidth() == 5);
    assert(getGoalHeight() == 20);
    assert(getLeft() == 10);
    assert(getRight() == 60);
    assert(getTop() == 20);
    //tests whether goal is done
    assert(isGoal(11) == true);
    assert(isGoal(10) == false);
    assert(isGoal(59) == true);
    assert(isGoal(60) == false);
    assert(isGoal(5) == false);
    assert(isGoal(89) == false);
    move();
    assert(x == 9);
    direction = 1;
    move();
    assert(x == 10);
    x = 5;
    checkEdge();
    assert(direction == 1);
    x = 500;
    checkEdge();
    assert(direction == -1);
  
  
  }

  public static void main(String[] args)  {
    boolean testing = false;

    assert(testing = true);
    if(testing)  {
      Goal g = new Goal(10, 20);
      g.test();
    }
  }

}
