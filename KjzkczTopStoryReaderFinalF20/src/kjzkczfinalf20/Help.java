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
public class Help extends About {
//    private String helpInfo="This viewer will automatically load the Arts Section first, if you wish to look at any other section, please use the buttons to select what topic you would"
//            + "like to see next, clicking the button again while on that page will simply reload the page";
    
    public Alert helpAlert;

    public Help(String helpInfo,String header) {
        super(helpInfo);
        aboutAlert.setHeaderText(header);
    }
    
    public void showHelp(){
        super.showInfo();
    }
    
    
    
}
