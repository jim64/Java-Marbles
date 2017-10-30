//marble class. Holds values for each marble and methods to update
//holds no graphics info
//called by level class.

import java.util.*;
import javafx.scene.paint.PhongMaterial;


public class Marble {

  private int XPos;
  private int YPos;
  private double speed = 2.0;
  private int direction = 0;
  private int radius;
  private boolean left = true;
  private int maxWidth = 490;
  PhongMaterial colour;
  
  //constructor
  Marble(int x, int y, int radius0) {
  
    Colour c = new Colour();
    colour = c.getRandomColour();
    XPos = x;
    YPos = y;
    radius = radius0;

  }
  
  //uses direction to determine which move to complete
  public void move()  {
  
    switch(direction) {    
      case -1 : goLeft(); break;
      case 1 : goRight(); break;
      default : goDown();
    }
  
  }
  
  //allows marble position to be changed. For use when marble out of screen 
  //boundaries
  public void newPosition(int x, int y) {
    
    XPos = x;
    YPos = y;  
  
  }
  
  
  //Next three move the marble by updating the X or Y position by the 
  //speed variable. Also check to see if out of screen bounds
  private void goRight()  {

    XPos += speed;
    checkPosition();
  
  }
  
    
  private void goLeft()  {

    XPos -= speed;
    checkPosition();
 
  }
   
  private void goDown()  {

    YPos += speed;
    checkPosition();
 
  }
  
  //allows access to copy of X or Y pos so can check where Marble is located
  public int getX()  {
  
    int x = XPos;
    return x;
    
  }
  
  public int getY()  {
    
    int y = YPos;
    return y;
    
  }
  
  //returns marble colour : for use with setting sphere colour
  public PhongMaterial getMarbleColour()  {
  
    PhongMaterial temp = colour;
    return colour;
  
  }
  
  //returns size of marble : for use setting sphere size
  public int getSize()  {
  
    int size = radius * 2;
    
    return size;
  }
  
  //reverses the direction when marble at screen edge
  private void checkPosition()  {

    if(XPos < radius) {
      direction = 1;
      left = false;
    }
    else if(XPos > maxWidth) {
      direction = -1;
      left = true;
    }

  }
  
  //changes direction : to be called when collision detected
  public void touchPosition()  {
  
    if(left == true) direction = -1;
    else direction = 1;    
  
  }
  
  //allows for change to direction : used when collision finished
  public void setDirection(int i)  {
  
    direction = i;
  
  }
  
  //sets Marble to random colour
  public void setMarbleColour(PhongMaterial pm)  {
   
    colour = pm;

  }
  
  private void test()  {
  
    assert(XPos == 50);
    assert(YPos == 50);
    assert(radius == 10);
    newPosition(10,10);
    assert(XPos == 10);
    assert(YPos == 10);
    goRight();
    assert(XPos == 12);
    goLeft();
    assert(XPos == 10);
    goDown();
    assert(YPos == 12);
    assert(getX() == 10);
    assert(getY() == 12);
    assert(getSize() == 20);
    direction = 0;
    left = true;
    XPos = 500;
    checkPosition();
    assert(direction == -1);
    assert(left == true);
    left = false;
    touchPosition();
    assert(direction == 1);
    left = true;
    touchPosition();
    assert(direction == -1);
    setDirection(0);
    assert(direction == 0);
  
  }
  
  public static void main(String[] args)  {
    boolean testing = false;

    assert(testing = true);
    if(testing)  {
      Marble m = new Marble(50, 50, 10);
      m.test();
    }
  }
  
}
