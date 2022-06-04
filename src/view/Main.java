package view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader=new FXMLLoader(this.getClass().getResource("Media.fxml"));
        fxmlLoader.load();
        Scene scene=new Scene(fxmlLoader.getRoot());
        primaryStage.setScene(scene);
        primaryStage.setTitle("MediaPlayer");
        primaryStage.setMinHeight(300);
        primaryStage.setMinWidth(620);
        primaryStage.setHeight(720);
        primaryStage.setWidth(1225);
        primaryStage.show();


    }
}
