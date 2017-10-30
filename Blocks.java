//class for blocks to use to stop marbles. 
//holds each blocks data and test for whether it has been 
//touched
//called by Level class

import javafx.scene.*;
import javafx.scene.paint.*;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.*;
import javafx.scene.transform.*;

public class Blocks {

  private int width;
  private int height = 10; 
  private int YPos;
  private int XPos;  
  private PhongMaterial colour;
  private int marbleSize;


  //constructor
  Blocks(int x, int x0, int y0, int marbleSize0)  {
  
    Colour c = new Colour();
    width = x;
    YPos = y0;
    XPos = x0;
    marbleSize = marbleSize0;
    
  }
  
  //getters to return copies of values
  public int getX()  {
  
    int x = XPos;
    
    return x;
    
  }
  
  public int getY()  {
  
    int y = YPos;
    
    return y;
    
  }
  
  public PhongMaterial getColour()  {
  
    PhongMaterial p = colour;
    
    return p;
    
  }
   
  //used to detect if block clicked on
  public boolean selected(int x, int y)  {
  
    if(((y > YPos -(height/2)) && (y < YPos + height)) 
       && ((x > XPos) && (x < XPos+width)))
      return true;
    
    else 
      return false;
  
  }
   
  //if clicked on movesblock off screen
  public void moveBlock()  {
  
    XPos = -100;
    YPos = -100;
 
  } 
   
  //Checks whether x and y are close enough to be considered in bounds
  //magic number 2 remains as it is simply a buffer to detect the top
  //it bares no relation to anything other than pixel placement and so
  //cannot be linked to anything in particular  
  public boolean touched(int x, int y)  {
  
    int top = YPos -(height + 2);
    int left = XPos -marbleSize;
    int right = XPos + width + marbleSize;
     
    //touching top
    if((y > top-(marbleSize/2) && y < top) && (x < right && x > left)) return true;
   
    return false;
  
  }
  

  private void test()  {
    assert(width == 100);
    assert(height == 10);
    assert(YPos == 100);
    assert(XPos == 100);
    assert(getX() == 100);
    assert(getY() == 100);
    assert(selected(101, 100) == true);
    assert(selected(199, 100) == true);
    assert(selected(50, 100) == false);
    assert(selected(150, 50) == false);
    //test top touch
    assert(touched(150, 87) == true);
    assert(touched(150, 86) == true);
    assert(touched(96, 86) == true);
    assert(touched(204, 86) == true);
    assert(touched(210, 86) == false);
    assert(touched(89, 86) == false);
    assert(touched(150, 120) == false);  
    moveBlock();
    assert(XPos == -100);
    assert(YPos == -100);
  
  }
  
  public static void main(String[] args)  {
    boolean testing = false;

    assert(testing = true);
    if(testing)  {
      Blocks b = new Blocks(100, 100, 100, 10);
      b.test();
    }
  }


}
