/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kjzkczfinalf20;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 *
 * @author Kevin
 */
public class About {
    
    protected Alert aboutAlert;
    
    public About(String aboutInfo){
        aboutAlert= new Alert(AlertType.INFORMATION);
        aboutAlert.setHeaderText("About");
        aboutAlert.setContentText(aboutInfo);
        
        
    }
    
    public void showInfo(){
        aboutAlert.show();
    }
    
    
    
}
