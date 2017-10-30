//class to hold materials for 3d objects and return colours
//used by sphere, blocks and goals

import javafx.scene.paint.PhongMaterial;
import javafx.scene.paint.Color;
import java.util.*;

public class Colour  {

  //returns a random colour : blue, green or yellow
  //used for marble  / sphere colours
  public PhongMaterial getRandomColour()  {
      
    Random rand = new Random();
    int next = (rand.nextInt() % 3);
    if(next < 0) next *= -1;
    
    final PhongMaterial blue = new PhongMaterial();
    final PhongMaterial green = new PhongMaterial();
    final PhongMaterial yellow = new PhongMaterial(); 
        
    blue.setDiffuseColor(Color.BLUE);
    blue.setSpecularColor(Color.LIGHTBLUE);
    
    green.setDiffuseColor(Color.GREEN);
    green.setSpecularColor(Color.LIGHTGREEN);
    
    yellow.setDiffuseColor(Color.YELLOW);
    yellow.setDiffuseColor(Color.ORANGE);
    
    switch(next)  {
      case 0 : return blue; 
      case 1 : return green;
      case 2 : return yellow;
      default : return blue;
      
    }
  
  
  }
  
  //specific for box colour
  public PhongMaterial getBoxColour()  {
    
    final PhongMaterial red = new PhongMaterial();
       red.setSpecularColor(Color.SILVER);
       red.setDiffuseColor(Color.GREY);
  
    return red;
  
  
  }
  
  //specific for goal colour
  public PhongMaterial getGoalColour()  {
  
    final PhongMaterial gold = new PhongMaterial();
       gold.setSpecularColor(Color.SILVER);
       gold.setDiffuseColor(Color.GOLD);
 
    return gold;
  
  }

}
