

float i = 0, j = 0;

class AnimatedLine{
  private float x1,y1,x2,y2;
  private float updateIndex = 0;
  public boolean completed = false;
  public float step = 0.01;
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
    stroke(252, 45, 45);
    strokeWeight(stroke/2); 
    line(x1, y1, x2, y2);
  }
}

class TimesTable{
  private float step,factor=1;
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
      currentVector = PVector.fromAngle((TWO_PI/count)*((currentStep*factor % count))+HALF_PI);
      currentVector.mult(radius);
      lines[index++] = new AnimatedLine(prevVector.x,prevVector.y,currentVector.x,currentVector.y,2);
    }
  }

  public void updateArt(){
    index = 0;
    factor += 0.03;
    currentStep = 0;
    textSize(20);    
    fill(0, 102, 153);
    text("Factor:" + factor, -470, -470); 
    while(index<count){
        currentStep+=step;
        setCurrentVectorAndLine();
    }
    animate();
  }

  private void animate(){
    stroke(0, 60, 160);
    strokeWeight(3);
    noFill();
    circle(0,0,radius*2+3);
    for(int i = 0; i < index; i++){
          lines[i].drawLine();
    }
  }
}

AnimatedLine l1;
TimesTable t1;
PVector vec1;
void setup(){
  size(1000,1000);  
  frameRate(240);
}

void draw(){
  background(250, 255, 224);
  translate(500,500); 

  if(t1==null){
    t1 = new TimesTable(400,1,300);
  }
  else{
    t1.updateArt();
  }

}
void mouseClicked() {
  t1 = null;
}
