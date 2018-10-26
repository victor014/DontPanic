package sample.Controllers;

import com.sun.javafx.application.LauncherImpl;
import javafx.application.Application;
import javafx.application.Preloader;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.Splash;


public class Main extends Application {
    public static int duracion = 80000; //duracion del splash 80000
    public static int steps = 1;

    @Override
    public void init() throws Exception {
        for (int i = 0; i < duracion; i++) {
            double progreso = (100 * i) / duracion;
            LauncherImpl.notifyPreloader(this, new Preloader.ProgressNotification(progreso));
        }//LLAVE FOR
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("../Views/Principal.fxml"));
        primaryStage.setTitle("Compilador");
        primaryStage.setScene(new Scene(root, 960, 625));
        primaryStage.show();
    }


    public static void main(String[] args) {
        LauncherImpl.launchApplication(Main.class, Splash.class, args);
    }
}