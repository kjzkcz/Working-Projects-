/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kjzkczgpacalculatorf20;


import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

/**
 *
 * @author Kevin
 */
public class KjzkczGPACalculatorF20 extends Application {
    public String title ="GPA Calculator";
    public int width= 400;
    public int height=400;
    public String fontStyle="Times New Roman MS";
        
    

    
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle(title);
        
        GridPane root = new GridPane();
        root.setAlignment(Pos.BASELINE_CENTER);
        root.setHgap(8);
        root.setVgap(8);
        root.setPadding(new Insets(10,0,10,0));
        
        
        Label course1=new Label("Course 1: "); 
        Label course2=new Label("Course 2: ");
        Label course3=new Label("Course 3: ");
        Label course4=new Label("Course 4: ");
        
        TextField score1=new TextField();
        TextField score2=new TextField();
        TextField score3=new TextField();
        TextField score4=new TextField();
        

        format(root,course1,score1,0,0,1,0);
        format(root,course2,score2,0,1,1,1);
        format(root,course3,score3,0,2,1,2);
        format(root,course4,score4,0,3,1,3);
        
        //final GPA label and Text area      
        Label finalGPA=new Label("Final GPA: ");
        finalGPA.setFont(Font.font(fontStyle, FontWeight.NORMAL,18));
        finalGPA.setTextFill(Color.YELLOW);
        root.add(finalGPA,0,4);
        //text area
        TextArea finalScore=new TextArea();
        finalScore.setPrefRowCount(2);
        finalScore.setPrefWidth(300);
        finalScore.setWrapText(true);
        finalScore.setEditable(false);
        root.add(finalScore,1,4);
 
        //ERROR ALERT FOR PARAMETERS
        Alert textFieldError= new Alert(AlertType.ERROR);
        textFieldError.setHeaderText("Error");
        textFieldError.setContentText("Please input a number between 1-100");
 
        addFav(textFieldError);       
        Alert emulateFieldError= new Alert(AlertType.ERROR);
        emulateFieldError.setHeaderText("Error");
        emulateFieldError.setContentText("Please input a number between 0-4");
        addFav(emulateFieldError);
 
        
        //EMULATE BUTTON
        Button emulateButton=new Button("Emulate GPA");
        emulateButton.setAlignment(Pos.CENTER);
        emulateButton.setMaxWidth(Double.MAX_VALUE);
        TextInputDialog emulateTd= new TextInputDialog();
        emulateTd.setContentText("Please enter your desired GPA (0-4): ");
        emulateTd.setHeaderText("Calculate Desired GPA");
        emulateTd.setTitle("GPA");
        addFav(emulateTd);
              
        Label desiredGPA=new Label("No text input");
               
        emulateButton.setOnAction((ActionEvent e)->{
            emulateTd.showAndWait();
      
            desiredGPA.setText(emulateTd.getEditor().getText());
            double inputGPA=Double.parseDouble(desiredGPA.getText());
              
            if( inputGPA>4||inputGPA<0 ){
                
                emulateFieldError.show();
                emulateTd.getEditor().clear();
                
            }
            else
            {
               inputGPA=inputGPA/4*100;
               score1.setText(String.valueOf(inputGPA));
               score2.setText(String.valueOf(inputGPA));
               score3.setText(String.valueOf(inputGPA));
               score4.setText(String.valueOf(inputGPA));
               
            }
            
        });
            
        ///CALCULATE BUTTON CODE
        Button calculateButton=new Button("Calculate GPA");
        calculateButton.setAlignment(Pos.CENTER);
        calculateButton.setMaxWidth(Double.MAX_VALUE);
        calculateButton.setOnAction((ActionEvent e)->{
            
           String calculatedGPA=calculateGPA(score1,score2,score3,score4, textFieldError);                                      
           finalScore.setText(calculatedGPA);
            
        });

        ////START OF CLEAR BUTTON CODE
        Button clearButton=new Button("Clear");
        clearButton.setAlignment(Pos.CENTER);
        clearButton.setMaxWidth(Double.MAX_VALUE);
        clearButton.setOnAction((ActionEvent e)->{
            score1.setText("");
            score2.setText("");
            score3.setText("");
            score4.setText("");
            finalScore.setText("");
        });
     
       //START OF ALERT BUTTON CODE       
        Button alertButton=new Button("Alert");
        alertButton.setAlignment(Pos.CENTER);
        alertButton.setMaxWidth(Double.MAX_VALUE);
        Alert messageAlert=new Alert(AlertType.INFORMATION);       
        addFav(messageAlert);       
        messageAlert.setTitle("Alert");
        messageAlert.setHeaderText("Message");
  
        alertButton.setOnAction((ActionEvent e)->{
            
           String alertGPA=calculateGPA(score1,score2,score3,score4, textFieldError);                                      
           finalScore.setText(alertGPA);
                                      
                    messageAlert.setContentText(alertGPA);
                    messageAlert.show();         
        });

 
        VBox buttonsVbox=new VBox(15);
        buttonsVbox.setAlignment(Pos.BOTTOM_CENTER);
        buttonsVbox.getChildren().addAll(emulateButton,calculateButton,clearButton,alertButton);
          
        root.add(buttonsVbox,0,5,2,1);

        Scene scene=new Scene(root, width, height);
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("mizfav.png")));
        
        root.setStyle("-fx-background-color: black");
 
        primaryStage.setScene(scene);
        primaryStage.show();
        
       
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    

    public void format(GridPane root,Label course,TextField score, int lrow, int lcolumn,int tfrow, int tfcolumn){
        course.setFont(Font.font(fontStyle, FontWeight.NORMAL,18));
        course.setTextFill(Color.YELLOW);
        root.add(course,lrow,lcolumn);
        score.setPrefWidth(300);
        root.add(score,tfrow,tfcolumn);
    }
    
    
   public static boolean isNumeric(String str) { 
         // null or empty
        if (str == null || str.length() == 0) {
            return false;
        }
        

        for (char c : str.toCharArray()) {
            if (!Character.isDigit(c)) {
                //Check to allow doubles in the input field
                for (int i=1;i<str.length();i++){
                    if(str.charAt(i)!='.');
                    return true;
                }
                
                
                return false;
                        
            }
        }

        return true;
}
    
   
    public String getLetterGrade(double finalValue){
            if(finalValue>=0.98){
                return "A+";
                    }
            else if(finalValue>=0.93){
                return "A";
                    }
            else if(finalValue>=0.90){
                    return "A-";
            }
            else if(finalValue>=0.88){
                 return "B+";
            }
            else if(finalValue>=0.83){
                 return "B";
            }
            else if(finalValue>=0.80){
                 return "B-";
            }
            else if(finalValue>=0.78){
                 return "C+";
            }
            else if(finalValue>=0.73){
                 return "C";
            }
            else if(finalValue>=0.70){
                 return "C-";
            }
            else if(finalValue>=0.68){
                 return "D+";
            }
            else if(finalValue>=0.63){
                 return "D";
            }
            else if(finalValue>=0.60){
                 return "D-";
            }
            else if(finalValue>=0.0){
                 return "F";
            }
            return "0";
    }
    
    public void addFav(Alert alert){
        Stage favStage=(Stage) alert.getDialogPane().getScene().getWindow();
        favStage.getIcons().add(new Image(getClass().getResourceAsStream("mizfav.png")));
        
    }
    public void addFav(TextInputDialog dialog){
        
        Stage favStage=(Stage) dialog.getDialogPane().getScene().getWindow();
        favStage.getIcons().add(new Image(getClass().getResourceAsStream("mizfav.png")));
        
    }

    public String calculateGPA(TextField score1,TextField score2, TextField score3, TextField score4, Alert alert){
        
   

            if(isNumeric(score1.getText())==false||isNumeric(score2.getText())==false||isNumeric(score3.getText())==false||isNumeric(score4.getText())==false){
                alert.show();
            }
            else if(score1.getText().isEmpty()||score2.getText().isEmpty()||score3.getText().isEmpty()||score4.getText().isEmpty()){
                alert.show();
            }
            else if(  (Double.parseDouble(score1.getText())>100||Double.parseDouble(score1.getText())<0)
                    ||(Double.parseDouble(score2.getText())>100||Double.parseDouble(score2.getText())<0)
                    ||(Double.parseDouble(score3.getText())>100||Double.parseDouble(score3.getText())<0)
                    ||(Double.parseDouble(score4.getText())>100||Double.parseDouble(score4.getText())<0)  ){
             alert.show();       
            }

            double finalValue=Double.parseDouble(score1.getText())+Double.parseDouble(score2.getText())
                    +Double.parseDouble(score3.getText())+Double.parseDouble(score4.getText());
            
                    finalValue=finalValue/4/100;
  
                    String percentage=("My final GPA should be \n"+"(("+ Double.parseDouble(score1.getText())+""
                    + "+"+Double.parseDouble(score2.getText())+"+"+Double.parseDouble(score3.getText())+"+"
                    +Double.parseDouble(score4.getText())+")/4)100 = "+ getLetterGrade(finalValue));
                    
                    return percentage;             
    }    
}
