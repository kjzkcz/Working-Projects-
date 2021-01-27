/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kjzkczfinalf20;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 *
 * @author Kevin
 */
public class InfoPageController implements Initializable{
    
    
    private About aboutBttn;
    private Help helpBttn;
    @FXML
    private Button aboutPageButton;
    public FXMLDocumentController storyPageController;
    public Scene infoScene;
    public Stage stage;
    
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //TODO
    }
    
    public void start(Stage stage){
        this.stage = stage; 
        String aboutInfo="This Application uses the NYT Top Stories API which gathers data about the top stories currently running in The New York Times "
                + "this application was developed because I really enjoy using another news site Reddit, which has their posts sorted by 'subreddit' or "
                + "in my case as a section, I wanted to make an app that emulated visiting different subreddits without having to input any user queries. This app "
                + "was developed by Kevin Zeng, please press the Help button for further information regarding the UI";
        String helpInfo="This viewer will automatically load the Arts Section first, if you wish to look at any other section, please use the buttons to select what topic you would "
            + "like to see next, clicking the button again while on that page will simply reload the page, most importantly, please do not click on the buttons too fast, let each"
                + " selection of articles completely load before loading another section";
        
        aboutBttn=new About(aboutInfo);
        helpBttn=new Help(helpInfo,"Help");
    }
    
    @FXML
    public void goBackToPg1(ActionEvent e){
        System.out.println("Going Back To Top Story Viewer");
        stage.setScene(infoScene);
    } 
    
    @FXML
    public void showAbout(MouseEvent e) {
        aboutBttn.showInfo();
        
    }

    @FXML
    public void showHelp(MouseEvent e) {
        helpBttn.showHelp();
    }
    
}
