/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package audioviz;

import static java.lang.Integer.min;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author Kevin
 */
public class KjzkczDopeVisual implements Visualizer{
    
     private final String name = "KJZKCZ Dope Visualizer";
    
    private Integer numOfBands;
    private AnchorPane vizPane;
    
    private final Double bandHeightPercentage = 1.3;
    private final Double barWidth = 19.0;
    private final Double barHeight=10.0;
    
    
    
    
    private Double width = 0.0;
    private Double height = 0.0;
    
    private Color orange=Color.hsb(38.8, 1.0, 1.0, 1.0);    
    private Double bandWidth = 0.0;
   
    
    private Rectangle[] vizBars;
    
    public KjzkczDopeVisual() {
    }
    
    @Override
    public String getName() {
        return name;
    }
    
   

    @Override
    public void start(Integer numBands, AnchorPane vizPane) {
        end();
        this.numOfBands= numBands;
        this.vizPane=vizPane;
        
        height= vizPane.getHeight();
        width=vizPane.getWidth();
        
        Rectangle clip= new Rectangle(width*2,height*2);
        clip.setLayoutX(0);
        clip.setLayoutY(0);
        vizPane.setClip(clip);
        
        bandWidth=width/numBands;
        //bandHeight= height * bandHeightPercentage;
        //halfBandHeight= bandHeight/2;
        vizBars=new Rectangle[numBands];
        
        
        for(int i=0; i<numBands; i++){
            Rectangle rectangle= new Rectangle();
            rectangle.maxHeight(height*0.100);
            
            //rectangle.setX(bandWidth / 2 + bandWidth * i);
            rectangle.setX(bandWidth * i);
            rectangle.setY(height/200);
            
            rectangle.setHeight(barHeight/2);
            rectangle.setWidth(barWidth/1.2);
          
            rectangle.setFill(Color.hsb(0.0, 0.0, 1.0, 1.0));
            vizPane.getChildren().add(rectangle);
            vizBars[i]=rectangle;
            
        }
       
                   
        
        
    }

    @Override
    public void end() {
        if(vizBars !=null){
            for (Rectangle rectangle : vizBars){
                vizPane.getChildren().remove(rectangle);
            }
            vizBars=null;
            vizPane.setClip(null);
        }
    }

    @Override
    public void draw(double timestamp, double lenght, float[] magnitudes, float[] phases) {
        if(vizBars==null){
            return;
        }
        Integer num= min(vizBars.length, magnitudes.length);
        Double hue = 30.0;
        Double hue2 = (34.0 - (magnitudes[0]*9));
        for(int i=0; i<num; i++){
            vizBars[i].setHeight(60.0 - (magnitudes[i]*6) );//*halfbadnheight+barWidth

            
            
            vizBars[i].setFill(Color.BLACK);
            

        }
        
        vizPane.setStyle("-fx-background-color: orange");
        
    
    }
    
    
    
}
