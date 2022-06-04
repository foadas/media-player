package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class PlayListController implements Initializable {
    @FXML
   private Button add;
    @FXML
    private Button delete;
    @FXML
   private VBox mediaBox;

    private Media media;
    private File file;
    private  ArrayList<Media> mediaArrayList =new ArrayList<>();
    private ArrayList<Label> labelArrayList=new ArrayList<>();
    private Label samp;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
 //samp for style
         samp=new Label("");
  //add button actions
        add.setOnAction(e -> {
            Node source = (Node) e.getSource();
            Stage theStage =(Stage) source.getScene().getWindow();
            String filepath=null;
            FileChooser fileChooser=new FileChooser();
            FileChooser.ExtensionFilter filter=new FileChooser.ExtensionFilter("mp4","*.mp4");
            FileChooser.ExtensionFilter filter1=new FileChooser.ExtensionFilter("mp3","*.mp3");
            FileChooser.ExtensionFilter filter2=new FileChooser.ExtensionFilter("mkv","*.mkv");
            fileChooser.getExtensionFilters().addAll(filter,filter1,filter2);
            file=fileChooser.showOpenDialog(theStage);
            String name = null;
            if(file!=null){
                filepath= file.toURI().toString();
                 name=file.getName();
            }
            if(filepath!=null){
                media=new Media(filepath);
                getMedia().add(media);
                Label label=new Label(name);
                labelAction(label);
                getLabelArrayList().add(label);
                getMediaBox().getChildren().add(getLabelArrayList().get(getLabelArrayList().size()-1));
            }

        });
 //delete buttons actions
        delete.setOnAction(event -> {
            for (int i = 0; i <labelArrayList.size() ; i++) {
                if(labelArrayList.get(i).getStyle().contains("-fx-background-color: #cae6fd")){
                    labelArrayList.remove(i);
                    mediaArrayList.remove(i);
                    mediaBox.getChildren().remove(i);
                    i=-1;
                }
            }
            if(labelArrayList.size()==0){
                MediaController.mediaNum=-1;
            }
        });

    }
 //selecting label actions
    public void labelAction(Label label){
        samp.setStyle("-fx-background-color: #cae6fd");
        label.setOnMouseClicked(event -> {
            if(event.isControlDown()){
                label.setStyle("-fx-background-color: #cae6fd");
            }else {
                if(label.getStyle().contains("-fx-background-color: #cae6fd"))
                {label.setStyle("-fx-background-color: grey");}
                else {
                    label.setStyle("-fx-background-color: #cae6fd");}
                for (Label value : labelArrayList) {
                    if (label != value) {
                        value.setStyle("-fx-background-color: grey");
                    }
                }}
        });
    }
    public  ArrayList<Media> getMedia() {
        return mediaArrayList;
    }

    public  void setMedia(ArrayList<Media> media) {
        mediaArrayList = media;
    }

    public ArrayList<Label> getLabelArrayList() {
        return labelArrayList;
    }

    public void setLabelArrayList(ArrayList<Label> labelArrayList) {
        this.labelArrayList = labelArrayList;
    }
    public VBox getMediaBox() {
        return mediaBox;
    }

    public void setMediaBox(VBox mediaBox) {
        this.mediaBox = mediaBox;
    }
}
