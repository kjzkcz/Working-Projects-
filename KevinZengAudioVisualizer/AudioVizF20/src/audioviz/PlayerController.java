/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package audioviz;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import javax.swing.*; 



/**
 * FXML Controller class
 *
 * @author Kevin Zeng
 * 
 * 
 * References: 
 * http://stackoverflow.com/questions/11994366/how-to-reference-primarystage
 */
public class PlayerController implements Initializable {

    @FXML
    private AnchorPane vizPane;

    @FXML
    private MediaView mediaView;

    @FXML
    private Text filePathText;

    @FXML
    private Text lengthText;

    @FXML
    private Text currentText;

    @FXML
    private Text bandsText;

    @FXML
    private Text visualizerNameText;

    @FXML
    private Text errorText;

    @FXML
    private Menu visualizersMenu;

    @FXML
    private Menu bandsMenu;

    @FXML
    private Slider timeSlider;

    @FXML
    private Button playPause;
    
    @FXML
    private Slider volumeSlider;
    
    @FXML
    private Text volumeLevelText;
    
    @FXML
    private ProgressBar songVisualBar;
    
    private Media media;
    private MediaPlayer mediaPlayer;

    private Integer numOfBands = 40;
    private final Double updateInterval = 0.05;

    private ArrayList<Visualizer> visualizers;
    private Visualizer currentVisualizer;
    private final Integer[] bandsList = {1, 2, 4, 8, 16, 40, 60, 100};

    private int currentStatus = 0;
    
    private int second1 = 0;
    
    private int minute2 = 0;
    
    private String updateString="";
    
    private double dec2Percent=0.0;
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        bandsText.setText(Integer.toString(numOfBands));
        
        visualizers = new ArrayList<>();

        visualizers.add(new KjzkczDopeVisual());
        
        for (Visualizer visualizer : visualizers) {
            MenuItem menuItem = new MenuItem(visualizer.getName());
            menuItem.setUserData(visualizer);
            menuItem.setOnAction((ActionEvent event) -> {
                selectVisualizer(event);
            });
            visualizersMenu.getItems().add(menuItem);
        }
        
        currentVisualizer = visualizers.get(0);
        visualizerNameText.setText(currentVisualizer.getName());

        for (Integer bands : bandsList) {
            MenuItem menuItem = new MenuItem(Integer.toString(bands));
            menuItem.setUserData(bands);
            menuItem.setOnAction((ActionEvent event) -> {
                selectBands(event);
            });
            bandsMenu.getItems().add(menuItem);
        }
    }

    private void selectVisualizer(ActionEvent event) {
        MenuItem menuItem = (MenuItem) event.getSource();
        Visualizer visualizer = (Visualizer) menuItem.getUserData();
        changeVisualizer(visualizer);
    }

    private void selectBands(ActionEvent event) {
        MenuItem menuItem = (MenuItem) event.getSource();
        numOfBands = (Integer) menuItem.getUserData();
        if (currentVisualizer != null) {
            currentVisualizer.start(numOfBands, vizPane);
        }
        if (mediaPlayer != null) {
            mediaPlayer.setAudioSpectrumNumBands(numOfBands);
        }
        bandsText.setText(Integer.toString(numOfBands));
    }

    private void changeVisualizer(Visualizer visualizer) {
        if (currentVisualizer != null) {
            currentVisualizer.end();
        }
        currentVisualizer = visualizer;
        currentVisualizer.start(numOfBands, vizPane);
        visualizerNameText.setText(currentVisualizer.getName());
    }

    private void openMedia(File file) {
        filePathText.setText("");
        errorText.setText("");

        if (mediaPlayer != null) {
            mediaPlayer.dispose();
        }

        try {
            media = new Media(file.toURI().toString());
            mediaPlayer = new MediaPlayer(media);
            mediaView.setMediaPlayer(mediaPlayer);
            mediaPlayer.setOnReady(() -> {
                handleReady();
            });
            mediaPlayer.setOnEndOfMedia(() -> {
                handleEndOfMedia();
            });
            mediaPlayer.setAudioSpectrumNumBands(numOfBands);
            mediaPlayer.setAudioSpectrumInterval(updateInterval);
            mediaPlayer.setAudioSpectrumListener((double timestamp, double duration, float[] magnitudes, float[] phases) -> {
                handleVisualize(timestamp, duration, magnitudes, phases);
            });
            mediaPlayer.setAutoPlay(true);
            filePathText.setText(file.getPath());
            currentStatus = 1;
            mediaPlayer.play();
            playPause.setText("Pause");
        } catch (Exception ex) {
            errorText.setText(ex.toString());
        }
    }

    private void handleReady() {
        Duration duration = mediaPlayer.getTotalDuration();

        Duration ct = mediaPlayer.getCurrentTime();
        currentText.setText(ct.toString());
        currentVisualizer.start(numOfBands, vizPane);
        timeSlider.setMin(0);
        timeSlider.setMax(duration.toMillis());
        //timeSlider.setVisible(false);
        
        //songVisualBar.setMinWidth(duration.toMillis()%60);
        
        
        volumeSlider.setMin(0);
        volumeSlider.setMax(1);
        volumeSlider.setValue(1);
    }

    private void handleEndOfMedia() {
        mediaPlayer.stop();
        mediaPlayer.seek(Duration.ZERO);
        timeSlider.setValue(0);
        
        volumeSlider.setValue(0);
    }

    private void handleVisualize(double timestamp, double duration, float[] magnitudes, float[] phases) {
        Duration currentTime=mediaPlayer.getCurrentTime();
        Duration totalTime=mediaPlayer.getTotalDuration();
        
        Duration ct = currentTime;
        Duration sec = currentTime;
        double ms = ct.toMillis();
        double s= sec.toSeconds();
        dec2Percent=volumeSlider.getValue()*100;
        //this is the added
       
        volumeLevelText.setText(String.format("%d%% ", (int)dec2Percent));
        
        //viusal bar indicator for time

        
        Duration songDur = totalTime;
        double dur=(songDur.toSeconds()-s);
        //length
        lengthText.setText(String.format("%d:%02d:%02ds", (int)dur / 3600, ((int)dur % 3600) / 60, ((int)dur % 60)));
        //time
        currentText.setText(String.format("%d:%02d:%02ds", (int)s / 3600, ((int)s % 3600) / 60, ((int)s % 60)));
         
        songVisualBar.setProgress(((currentTime.toMillis()*totalTime.toMillis())/1000000000/36));
        timeSlider.setValue(ms);
        

        currentVisualizer.draw(timestamp, duration, magnitudes, phases);
    }

    @FXML
    private void handleOpen(Event event) {
        Stage primaryStage = (Stage) vizPane.getScene().getWindow();
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(primaryStage);
        if (file != null) {
            openMedia(file);
        }
    }

    @FXML
    private void handlePlayPause(ActionEvent event) {
        if (mediaPlayer != null) {
            if (currentStatus == 0) {
                currentStatus = 1;
                mediaPlayer.play();
                volumeSlider.setValue(mediaPlayer.getVolume());
                playPause.setText("Pause");
            } else {
                currentStatus = 0;
                mediaPlayer.pause();
                playPause.setText("Play");
            }
        }
    }
    
    @FXML
    private void handleSliderMousePressed(Event event) {
        if (mediaPlayer != null) {
            mediaPlayer.pause();
        }
    }

    @FXML
    private void handleSliderMouseReleased(Event event) {
        if (mediaPlayer != null) {
            mediaPlayer.seek(new Duration(timeSlider.getValue()));
            //System.out.println(timeSlider.getValue());
            currentVisualizer.start(numOfBands, vizPane);
            mediaPlayer.play();
        }
    }

    @FXML
    private void handleVolumeSliderMouseReleased(MouseEvent event) {
        if (mediaPlayer != null) {
            mediaPlayer.setVolume(volumeSlider.getValue());
        }
    }
}
