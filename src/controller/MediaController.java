package controller;

import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.File;
import java.io.IOException;
import java.math.RoundingMode;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ResourceBundle;

public class MediaController implements Initializable {
    @FXML
    private Button stop;
    @FXML
    private Button pause;
    @FXML
    private Button previous;
    @FXML
    private Button next;
    @FXML
    private Button fileSelect;
    @FXML
    private Button playList;
    @FXML
    private Button forward;
    @FXML
    private Button backward;
    @FXML
    private MediaView mediaView;
    @FXML
    private Slider volume;
    @FXML
    private Slider timeSlider;
    @FXML
    private Label volumePercent;
    @FXML
    private Label time;
    @FXML
    private Label remainedTime;
    @FXML
    private  Label currentTime;
    @FXML
    private VBox parent;
    @FXML
    private HBox h;
    @FXML
    private Button auto;
    @FXML
    private Separator line;
    private MediaPlayer mediaPlayer;
    private Media media;
    private File file;
    private boolean isPlaying=false;
    private boolean autoplay=false;
    public static boolean start;
    private Stage theStage;
    private PlayListController playListController;
    public static int mediaNum;
    boolean deleted;
    @Override
    public void initialize(URL location, ResourceBundle resources) {

 //volume adjustment
        volume.setValue(50);
        volumePercent.setText("50");
        volume.valueProperty().addListener(observable -> {
            DecimalFormat df = new DecimalFormat("#");
            df.setRoundingMode(RoundingMode.CEILING);
            String v=df.format(volume.getValue());
            volumePercent.setText(v);
        });


 //previous button actions
        previous.setOnAction(event -> {
             deleted = delete();
            if(deleted){
                mediaPlayer.stop();
                Playing(playListController.getMedia().get(0));
                isPlaying=true;
                if(pause.getStylesheets().size()==0){
                    pause.getStylesheets().add(getClass().getResource("../view/Play.css").toExternalForm());}
                playListController.getLabelArrayList().get(0)
                        .setText(playListController.getLabelArrayList().get(0).getText()+"|playing");
                mediaNum=0;
            }
            if(!deleted){
            if(mediaNum-1>=0){
                mediaPlayer.stop();
                mediaNum--;
                Playing(playListController.getMedia().get(mediaNum));
                isPlaying=true;
                if(pause.getStylesheets().size()==0){
                    pause.getStylesheets().add(getClass().getResource("../view/Play.css").toExternalForm());}
                playListController.getLabelArrayList().get(mediaNum).setText(playListController.getLabelArrayList().get(mediaNum).getText()+"|playing");
                int l=playListController.getLabelArrayList().get(mediaNum+1).getText().indexOf("|");
                playListController.getLabelArrayList().get(mediaNum+1).
                        setText(playListController.getLabelArrayList().get(mediaNum+1).getText().substring(0,l));

            }
        }});
  //nex button actions
        next.setOnAction(event -> {
             deleted = delete();
            if(deleted){
                mediaPlayer.stop();
                Playing(playListController.getMedia().get(0));
                isPlaying=true;
                if(pause.getStylesheets().size()==0){
                    pause.getStylesheets().add(getClass().getResource("../view/Play.css").toExternalForm());}
                playListController.getLabelArrayList().get(0)
                        .setText(playListController.getLabelArrayList().get(0).getText()+"|playing");
                mediaNum=0;
            }
            if(!deleted){
            if(playListController.getMedia().size()>mediaNum+1){
                mediaPlayer.stop();
                mediaNum++;
                Playing(playListController.getMedia().get(mediaNum));
                isPlaying=true;
                if(pause.getStylesheets().size()==0){
                    pause.getStylesheets().add(getClass().getResource("../view/Play.css").toExternalForm());}
                playListController.getLabelArrayList().get(mediaNum).setText(playListController.getLabelArrayList().get(mediaNum).getText()+"|playing");
                int l=playListController.getLabelArrayList().get(mediaNum-1).getText().indexOf("|");
                playListController.getLabelArrayList().get(mediaNum-1).
                        setText(playListController.getLabelArrayList().get(mediaNum-1).getText().substring(0,l));
            }
        }});
 //parent styling
        parent.setStyle("-fx-background-color: rgba(0, 0, 0, .7)");
 //pause button actions
        pause.setOnAction(event2 ->
        {if(playListController.getMedia().size()>0){start=true;}
            if(start)
            if(isPlaying){
            mediaPlayer.pause();
                pause.getStylesheets().remove(0);
            isPlaying=false;}
        else {if(mediaPlayer!=null)
            mediaPlayer.play();isPlaying=true;
                if(pause.getStylesheets().size()==0){
                pause.getStylesheets().add(getClass().getResource("../view/Play.css").toExternalForm());}
        }
            if(playListController.getMedia().size()>0 &&mediaPlayer==null ){
                Playing(playListController.getMedia().get(0));
                playListController.getLabelArrayList().get(0)
                        .setText(playListController.getLabelArrayList().get(0).getText()+"|playing");
                if(pause.getStylesheets().size()==0){
                pause.getStylesheets().add(getClass().getResource("../view/Play.css").toExternalForm());}
                isPlaying=true;
            }
        });
 //stop button actions
        stop.setOnAction(event -> {
            if(start){
                    mediaPlayer.seek(Duration.ZERO);
                    timeSlider.setValue(0);
                    isPlaying=false;
                mediaPlayer.pause();
                if(pause.getStylesheets().size()==1){
                    pause.getStylesheets().remove(0);}

            }
        });
//file button actions
        fileSelect.setOnAction(event -> {
            String filepath = null;
            FileChooser fileChooser=new FileChooser();
            FileChooser.ExtensionFilter filter=new FileChooser.ExtensionFilter("mp4","*.mp4");
            FileChooser.ExtensionFilter filter1=new FileChooser.ExtensionFilter("mp3","*.mp3");
            FileChooser.ExtensionFilter filter2=new FileChooser.ExtensionFilter("mkv","*.mkv");
            fileChooser.getExtensionFilters().addAll(filter,filter1,filter2);
            Node source = (Node) event.getSource();
            theStage =(Stage) source.getScene().getWindow();
             file=fileChooser.showOpenDialog(theStage);
            if(file!=null){
             filepath= file.toURI().toString();}
            if(filepath!=null){
                if(playListController.getMedia().size()>0){
                    mediaPlayer.stop();
                }
                 media=new Media(filepath);
                while (playListController.getMedia().size()!=0){
                    playListController.getMedia().remove(0);
                    playListController.getLabelArrayList().remove(0);
                    playListController.getMediaBox().getChildren().remove(0);
                }
                playListController.getMedia().add(media);
                Label label =new Label(file.getName()+"|playing");
                playListController.labelAction(label);
                playListController.getLabelArrayList().add(label);
                playListController.getMediaBox().getChildren().add(label);
                mediaNum=0;
                Playing(media);
                if(pause.getStylesheets().size()==0){
                pause.getStylesheets().add(getClass().getResource("../view/Play.css").toExternalForm());}
                start=true;
                isPlaying=true;
                mediaPlayer.setOnEndOfMedia(this::mediaAction);
            }
        });
        FXMLLoader fxmlLoader=new FXMLLoader(this.getClass().getResource("..//view//PlayList.fxml"));
        try {
            fxmlLoader.load();
            playListController=fxmlLoader.getController();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage stage=new Stage();
        stage.setScene(new Scene(fxmlLoader.getRoot()));
        stage.setTitle("playlist");
        stage.setResizable(false);
        playList.setOnAction(event -> stage.show());
//auto play
        auto.setOnAction(event -> {
            autoplay = !autoplay;
            if (autoplay) {
                auto.setStyle("-fx-background-color: red");
            }else {auto.setStyle("-fx-background-color: transparent");}
        });

    }
//setting time of video
    public void length(Duration duration, Label time){
        if(duration.toHours()>=1){
            int h= (int) duration.toHours();
                String a=String.format("%02d",h);
            DecimalFormat df = new DecimalFormat("#");
            df.setRoundingMode(RoundingMode.DOWN);
            double m=duration.toHours();
            while (m>=1){
                m-=1;
            }
            m*=60;
            double s=m;
            while (s>=1){s--;}
            s*=60;
            DecimalFormat dd = new DecimalFormat("#");
            dd.setRoundingMode(RoundingMode.DOWN);
            String m1=df.format(m);
           if(Integer.parseInt(m1)<10){
               String z="0";
               m1=z+m1;
           }
            String s1=df.format(s);
            if(Integer.parseInt(s1)<10){
                String z="0";
                s1=z+s1;
            }
            time.setText(a+":"+m1+":"+s1);
        }else {
            if(duration.toMinutes()>=1){
                int m= (int) duration.toMinutes();
                String a=String.format("%02d",m);
                double s =duration.toMinutes();
                while (s >=1){
                    s--;}
                s *=60;
                DecimalFormat df = new DecimalFormat("#");

                df.setRoundingMode(RoundingMode.DOWN);
                String s1=df.format(s);
                if(Integer.parseInt(s1)<10){
                    String z="0";
                    s1=z+s1;
                }
                time.setText("00:"+a+":"+s1);}else {
                int s=(int)duration.toSeconds();
                String a=String.format("%02d",s);
                time.setText("00:00:"+a);
            }}
    }
//method for playing media
    public void Playing(Media media){
        line.setVisible(true);
        mediaPlayer=new MediaPlayer(media);
mediaPlayer.setOnEndOfMedia(this::mediaAction);
        mediaView.setMediaPlayer(mediaPlayer);
        mediaPlayer.setVolume(volume.getValue()/100);
        volume.valueProperty().addListener(observable -> mediaPlayer.setVolume(volume.getValue()/100));
        //set on ready actions of media
        mediaPlayer.setOnReady(() -> {
            Duration duration= media.getDuration();
            timeSlider.setMax(duration.toSeconds());
            timeSlider.widthProperty().addListener
                    (observable -> h.setSpacing(timeSlider.getWidth()-300));

//time adjusting
            length(duration,time);
            mediaPlayer.currentTimeProperty().addListener(observable -> {
                Duration duration1=mediaPlayer.getCurrentTime();
                length(duration1,remainedTime);
                Duration duration2= Duration.millis(media.getDuration().toMillis()-mediaPlayer.getCurrentTime().toMillis());
                length(duration2,currentTime);
            });
            //size adjusting
            DoubleProperty mvw = mediaView.fitWidthProperty();
            DoubleProperty mvh = mediaView.fitHeightProperty();
            //mediaView.fitHeightProperty().bind(parent.heightProperty().subtract(h.heightProperty().add(10)));
            mvw.bind(Bindings.selectDouble(mediaView.sceneProperty(), "width"));
            mvh.bind(Bindings.select(mediaView.sceneProperty(),"height"));
            mediaView.setPreserveRatio(true);
        });
//full screen by  double click
        mediaView.setOnMouseClicked(
                event1 -> {Node source = (Node) event1.getSource();
                if(event1.getClickCount()==2){
            theStage =(Stage) source.getScene().getWindow();
                    if(!theStage.isFullScreen()){
                        parent.getStylesheets().add(getClass().getResource("../view/Style.css").toExternalForm());}
            theStage.setFullScreen(true);}
        });
// key actions fpr parent vbox
        parent.setOnKeyPressed(keyEvent -> {
            switch (keyEvent.getCode()){
                case ESCAPE: if(parent.getStylesheets().size()==1) {parent.getStylesheets()
                        .remove(0);parent.setStyle("-fx-background-color: rgba(0, 0, 0, .7)");break;}
                case DOWN: volume.setValue(volume.getValue()-1);break;
                case UP:volume.setValue(volume.getValue()+1);break;
                case LEFT:
                    mediaPlayer.seek(mediaPlayer.getCurrentTime().add(Duration.seconds(-30)));break;
                case RIGHT:
                    mediaPlayer.seek(mediaPlayer.getCurrentTime().add(Duration.seconds(+30)));break;
                case P:if(isPlaying){
                    mediaPlayer.pause(); pause.getStylesheets().remove(0);isPlaying=false;}
                    else {mediaPlayer.play();if(pause.getStylesheets().size()==0){
                    pause.getStylesheets().add(getClass().getResource("../view/Play.css").toExternalForm());}isPlaying=true;}break;
                case S:if(isPlaying){
                    mediaPlayer.seek(Duration.ZERO);
                    mediaPlayer.pause();
                    isPlaying=false;
                    pause.getStylesheets().remove(0);
                }break;
            }
        });
//key action for time slider
        timeSlider.setOnKeyPressed(keyEvent -> {
            switch (keyEvent.getCode()){
                case ESCAPE: if(parent.getStylesheets().size()==1) {parent.getStylesheets()
                        .remove(0);parent.setStyle("-fx-background-color: rgba(0, 0, 0, .7)");break;}
                case LEFT:
                    mediaPlayer.seek(mediaPlayer.getCurrentTime().add(Duration.seconds(-30)));break;
                case RIGHT:
                    mediaPlayer.seek(mediaPlayer.getCurrentTime().add(Duration.seconds(+30)));break;}
            });
//key action for volume
        volume.setOnKeyPressed(keyEvent -> {
            switch (keyEvent.getCode()){
                case ESCAPE: if(parent.getStylesheets().size()==1) {parent.getStylesheets()
                        .remove(0);parent.setStyle("-fx-background-color: rgba(0, 0, 0, .7)");break;}
                case LEFT:
                    volume.setValue(volume.getValue()+10);
                    mediaPlayer.seek(mediaPlayer.getCurrentTime().add(Duration.seconds(-30)));break;
                case RIGHT:
                    volume.setValue(volume.getValue()-10);
                    mediaPlayer.seek(mediaPlayer.getCurrentTime().add(Duration.seconds(+30)));break;
                case DOWN: volume.setValue(volume.getValue()-1);break;
                case UP:volume.setValue(volume.getValue()+1);break;
            }
        });
//forward & backward actions
        forward.setOnAction(event -> mediaPlayer.seek(mediaPlayer.getCurrentTime().add(Duration.seconds(+30))));
        backward.setOnAction(event -> mediaPlayer.seek(mediaPlayer.getCurrentTime().add(Duration.seconds(-30))));
//time adjusting by time slider
        mediaPlayer.currentTimeProperty().addListener(observable -> timeSlider.setValue(mediaPlayer.getCurrentTime().toSeconds()));
        timeSlider.setOnMouseClicked(event1 -> mediaPlayer.seek(Duration.seconds(timeSlider.getValue())));
        timeSlider.setOnMouseDragged(event1 -> mediaPlayer.seek(Duration.seconds(timeSlider.getValue())));
        mediaPlayer.play();
    }
//media actions for when media is finished
    public void mediaAction(){
        boolean deleted = delete();
        if(deleted){
            Playing(playListController.getMedia().get(0));
            playListController.getLabelArrayList().get(0)
                    .setText(playListController.getLabelArrayList().get(0).getText()+"|playing");
            mediaNum=0;
        }
        if(!deleted){
            if(autoplay&& playListController.getMedia().size()==1) {mediaPlayer.seek(Duration.ZERO); }
            else {
                if(playListController.getMedia().size()==mediaNum+1&&autoplay){
                    if(playListController.getMedia().size()!=0){
                Playing(playListController.getMedia().get(0));
                playListController.getLabelArrayList().get(0)
                        .setText(playListController.getLabelArrayList().get(0).getText()+"|playing");
                        int l=playListController.getLabelArrayList().get(mediaNum).getText().indexOf("|");
                        playListController.getLabelArrayList().get(mediaNum).
                                setText(playListController.getLabelArrayList().get(mediaNum).getText().substring(0,l));
                        mediaNum=0;
                    }
                    else {
                        if(autoplay){
                        mediaPlayer.seek(Duration.ZERO);}
                    else{ mediaPlayer.seek(Duration.ZERO);
                            pause.getStylesheets().remove(0);
                        mediaPlayer.pause();
                        isPlaying=false;
                    }

                    }
            }else {
                    if(playListController.getMedia().size()==mediaNum+1&&!autoplay){
                      mediaPlayer.seek(Duration.ZERO);
                      mediaPlayer.pause();
                      isPlaying=false;
                        pause.getStylesheets().remove(0);
                    }
                    else {if(playListController.getMedia().size()>mediaNum+1){
                        mediaNum++;
                        Playing(playListController.getMedia().get(mediaNum));
                        int l=playListController.getLabelArrayList().get(mediaNum-1).getText().indexOf("|");
                        playListController.getLabelArrayList().get(mediaNum-1).
                                setText(playListController.getLabelArrayList().get(mediaNum-1).getText().substring(0,l));
                        playListController.getLabelArrayList().get(mediaNum)
                                .setText(playListController.getLabelArrayList().get(mediaNum).getText()+"|playing");
                    }

                    }}
                }
            }}
//find out if current media is deleted
public boolean delete() {
    boolean deleted = false;
    for (int i = 0; i < playListController.getLabelArrayList().size(); i++) {
        if (playListController.getLabelArrayList().get(i).getText().contains("|")) {
            deleted = false;
            break;
        } else {
            if (i == playListController.getLabelArrayList().size() - 1
                    && !playListController.getLabelArrayList().get(i).getText().contains("|")) {
                deleted = true;
            }
        }
    }
return deleted;
    }
    }
