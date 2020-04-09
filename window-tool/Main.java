package sample;

import javafx.application.Application;

import javafx.scene.Scene;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        WebView view = new WebView();
        view.getEngine().setJavaScriptEnabled(true);
        view.getEngine().setUserAgent("Secur-o-Cord/0.0.1 (+http://ctection.com/");
        view.getEngine().load("https://discordapp.com");
        Scene scene = new Scene(view, 1280, 720);

        primaryStage.setTitle("Secur-o-Cord");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
