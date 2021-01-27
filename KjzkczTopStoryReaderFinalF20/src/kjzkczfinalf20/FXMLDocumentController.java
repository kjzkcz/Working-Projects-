/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kjzkczfinalf20;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

/**
 *
 * @author Kevin
 */
public class FXMLDocumentController implements Initializable, ButtonMethods, Favicon {
    
    private TopStoryManager newsManager;
    ArrayList<TopStory> stories;
    private Stage stage;
    private Label label;
    @FXML
    private WebView webView;
    
    private WebEngine webEngine;
    ObservableList<String> newsListItems;
    @FXML
    private ListView TopListView;
    
    private String searchPlaceholder="";
    private About aboutBttn;
    private Help helpBttn;
    private SectionLoader carSection;
    private SectionLoader bookSection;
    private SectionLoader buissSection;
    private SectionLoader foodSection;
    private SectionLoader healthSection;
    private SectionLoader politicsSection;
    private SectionLoader scienceSection;
    private SectionLoader techSection;
    @FXML
    private VBox bottomLayer;
    //pg 1
    private Scene storyScene;
    //pg 2
    private Scene infoScene; 
    public InfoPageController infoPageController; 
    


            
           
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //simple thread usage to show end of application
         Runtime.getRuntime().addShutdownHook(new Thread(()-> {
        System.out.println("Shutdown Hook Running: Bye Bye!");
      
    }));
        
    } 
    
    public void createSections(){
        carSection= new SectionLoader("automobiles");
        bookSection= new SectionLoader("books");
        buissSection= new SectionLoader("business");
        foodSection= new SectionLoader("food");
        healthSection= new SectionLoader("health");
        politicsSection= new SectionLoader("politics");
        scienceSection= new SectionLoader("science");
        techSection= new SectionLoader("technology");
    }
    
    public void start(Stage stage) {
        this.stage = stage;
        
        webEngine = webView.getEngine();
        newsManager = new TopStoryManager();
        newsListItems = FXCollections.observableArrayList();
        //obj.addFav(bottomLayer);
        addFav(bottomLayer);
        createSections();       
        String aboutInfo="This Application uses the NYT Top Stories API which gathers data about the top stories currently running in The New York Times "
                + "this application was developed because I really enjoy using another news site Reddit, which has their posts sorted by 'subreddit' or "
                + "in my case as a section, I wanted to make an app that emulated visiting different subreddits without having to input any user queries. This app "
                + "was developed by Kevin Zeng, please press the Help button for further information regarding the UI";
        String helpInfo="This viewer will automatically load the Arts Section first, if you wish to look at any other section, please use the buttons to select what topic you would "
            + "like to see next, clicking the button again while on that page will simply reload the page, most importantly, please do not click on the buttons too fast, let each"
                + " selection of articles completely load before loading another section";
        
        aboutBttn=new About(aboutInfo);
        helpBttn=new Help(helpInfo,"Help");
       
        TopListView.getSelectionModel().selectedIndexProperty().addListener(
                (ObservableValue<? extends Number> ov, Number old_val, Number new_val) -> {

                    if((int) new_val < 0 || (int) new_val > (stories.size() - 1)){
                        return; 
                    }
                    
                    TopStory story = stories.get((int) new_val); 
                    webEngine.load(story.url);    
                }
        ); 
        
         loadNews(); 
        
        storyScene=stage.getScene();
    }
    @FXML
    private void goToInfoPage(ActionEvent e){
        
            System.out.println("Going to Info Page"); 
        
        try {
            if(infoScene == null){
                System.out.println("Creating Information Scene"); 
                
                FXMLLoader loader = new FXMLLoader(getClass().getResource("InfoPage.fxml"));
                Parent page2Root = loader.load(); 
                infoPageController = loader.getController(); 
                infoPageController.infoScene = storyScene; 
                infoPageController.storyPageController = this; 
                infoScene = new Scene(page2Root); 
            }
        } catch (Exception ex){
            displayExceptionAlert(ex); 
        }
        
        
        
        stage.setScene(infoScene); 
        infoPageController.start(stage);
        
    }
    
    private void loadNews() {
        try {
            newsManager.load(); 
        } catch(Exception ex){
            displayExceptionAlert(ex); 
            return; 
        }
        
        stories = newsManager.getTopStories();
        
        newsListItems.clear(); 

        for(TopStory story : stories){
            newsListItems.add(story.title); 
        }
        
        TopListView.setItems(newsListItems);
        
        if(stories.size() > 0){
            TopListView.getSelectionModel().select(0);
            TopListView.getFocusModel().focus(0); 
        }
    }
    private void loadNews(SectionLoader section) {
        try {
            section.load(); 
        } catch(Exception ex){
            displayExceptionAlert(ex); 
            return; 
        }
        
        stories = section.getTopStories();
        
        newsListItems.clear(); 

        for(TopStory story : stories){
            newsListItems.add(story.title); 
        }
        
        TopListView.setItems(newsListItems);
        
        if(stories.size() > 0){
            TopListView.getSelectionModel().select(0);
            TopListView.getFocusModel().focus(0); 
        }
    }
    //professor Wergeles error alert from class example, should never be used so I left it as is
    private void displayErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Error!");
        alert.setContentText(message);
        alert.showAndWait();
    }
    //professor Wergeles exeption alert from class example, should never be used so I left it as is
    private void displayExceptionAlert(Exception ex) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Exception");
        alert.setHeaderText("An Exception Occurred!");
        alert.setContentText("An exception occurred.  View the exception information below by clicking Show Details.");

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        ex.printStackTrace(pw);
        String exceptionText = sw.toString();

        Label label = new Label("The exception stacktrace was:");

        TextArea textArea = new TextArea(exceptionText);
        textArea.setEditable(false);
        textArea.setWrapText(true);

        textArea.setMaxWidth(Double.MAX_VALUE);
        textArea.setMaxHeight(Double.MAX_VALUE);
        GridPane.setVgrow(textArea, Priority.ALWAYS);
        GridPane.setHgrow(textArea, Priority.ALWAYS);

        GridPane expContent = new GridPane();
        expContent.setMaxWidth(Double.MAX_VALUE);
        expContent.add(label, 0, 0);
        expContent.add(textArea, 0, 1);

        alert.getDialogPane().setExpandableContent(expContent);

        alert.showAndWait();
    }
    
    
    
    @Override
    @FXML
    public void loadCars(MouseEvent e) {

        loadNews(carSection);
    }
    
    @Override
    @FXML
    public void loadArts(MouseEvent e) {
        loadNews();
    }

    @Override
    @FXML
    public void loadBooks(MouseEvent e) {
        loadNews(bookSection);
    }
    
    @Override
    @FXML
    public void loadBuiss(MouseEvent e){
        loadNews(buissSection);
    }
    
    
    @Override
    @FXML
    public void loadFood(MouseEvent e) {
        loadNews(foodSection);
    }

    @Override
    @FXML
    public void loadHealth(MouseEvent e) {
       loadNews(healthSection); 
    }

    @Override
    @FXML
    public void loadPolitics(MouseEvent e) {
        loadNews(politicsSection);
    }

    @Override
    @FXML
    public void loadScience(MouseEvent e) {
        loadNews(scienceSection);
    }

    @Override
    @FXML
    public void loadTech(MouseEvent e) {
        loadNews(techSection);
    }

    @Override
    public void showAbout(MouseEvent e) {
        aboutBttn.showInfo();
        
    }

    @Override
    public void showHelp(MouseEvent e) {
        helpBttn.showHelp();
    }

    
        
    @Override
    public void addFav(VBox vb) {
        Stage favStage=(Stage) vb.getScene().getWindow();
        favStage.getIcons().add(new Image(getClass().getResourceAsStream("mizfav.png")));
    }
  
        
        

    
    
}
    
       
    

