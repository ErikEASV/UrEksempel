package com.example.ureksempel;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class Ur extends Application {
    @Override
    public void start(Stage vindue) throws IOException {

        Pane scenegraf = new Pane();

        Stopur stopur = new Stopur(scenegraf);

        Viserur viserur = new Viserur(scenegraf);

        Button nulstilknap = new Button();
        nulstilknap.setTranslateX(100);
        nulstilknap.setTranslateY(350);
        nulstilknap.setText("Nulstil");
        nulstilknap.setOnAction(event -> stopur.nulstil());
        scenegraf.getChildren().add(nulstilknap);

        Button startStopknap = new Button();
        startStopknap.setTranslateX(200);
        startStopknap.setTranslateY(350);
        startStopknap.setText("Start/Stop");
        startStopknap.setOnAction(event -> stopur.startStop());
        scenegraf.getChildren().add(startStopknap);

        Button visTiden = new Button();
        visTiden.setTranslateX(300);
        visTiden.setTranslateY(350);
        visTiden.setText("Vis tiden");
        visTiden.setOnAction(event -> viserur.vis());
        scenegraf.getChildren().add(visTiden);

        Scene scene = new Scene(scenegraf, 600, 400);
        vindue.setTitle("Stopur!");
        vindue.setScene(scene);
        vindue.show();
    }

    public static void main(String[] args) {
        launch();
    }
}