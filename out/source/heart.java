import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class heart extends PApplet {



float i = 0, j = 0;

class AnimatedLine{
  private float x1,y1,x2,y2;
  private float updateIndex = 0;
  public boolean completed = false;
  public float step = 0.01f;
  private int stroke = 1;
  public AnimatedLine(float a, float b, float c, float d,int e){
    x1 = a;
    y1 = b;
    x2 = c;
    y2 = d;
    stroke = e;   
  }
  public void animateLine(){     
    stroke(252, 45, 45);
    strokeWeight(stroke); 
    if(updateIndex<=1){
      float currentX = x1*(1-updateIndex) + x2*updateIndex;   
      float currentY = y1*(1-updateIndex) + y2*updateIndex;
      line(x1, y1, currentX, currentY);
      updateIndex+=step;
    }
    else{
      if(!completed) completed = true;
      drawLine();
    }
  }
  public void drawLine(){
    line(x1, y1, x2, y2);
  }
}

class TimesTable{
  private float step;
  private int count, radius, index = 0;
  private float currentStep = 0, timer=0;
  private PVector currentVector, prevVector;
  private AnimatedLine[] lines; 
  public TimesTable(int _radius, float _step, int _count){
    radius = _radius;
    step = _step;
    count = _count;
    lines = new AnimatedLine[count];
  }

  private void setCurrentVectorAndLine(){
    if(index<count){
      prevVector = PVector.fromAngle((TWO_PI*((currentStep/count)))+HALF_PI);
      prevVector.mult(radius); 
      currentVector = PVector.fromAngle((TWO_PI/count)*((currentStep*2 % count))+HALF_PI);
      currentVector.mult(radius);
      lines[index++] = new AnimatedLine(prevVector.x,prevVector.y,currentVector.x,currentVector.y,2);
    }
  }

  public void updateArt(){
    if(currentStep == 0){
      currentStep+=step;
      setCurrentVectorAndLine();
    }
    else{
      if(((timer++)%5)==0){
        currentStep+=step;
        setCurrentVectorAndLine();
      }
      animate();
    }
  }

  private void animate(){
    stroke(0, 60, 160);
    strokeWeight(3);
    noFill();
    circle(0,0,radius*2+3);
    for(int i = 0; i < index; i++){
          lines[i].animateLine();
    }
  }
}

AnimatedLine l1;
TimesTable t1;
PVector vec1;
public void setup(){
    
  frameRate(120);
}

public void draw(){
  background(250, 255, 224);
  translate(500,500); 

  if(t1==null){
    t1 = new TimesTable(400,1,200);
  }
  else{
    t1.updateArt();
  }

}
public void mouseClicked() {
  t1 = null;
}
  public void settings() {  size(1000,1000); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "heart" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
