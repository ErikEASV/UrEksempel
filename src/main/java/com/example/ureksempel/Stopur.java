package com.example.ureksempel;

import javafx.animation.Interpolator;
import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class Stopur {
    PathTransition pt;
    boolean igang;

    public Stopur(Pane scenegraf) {

        Circle skive = new Circle(50);
        skive.setRotate(-90);
        skive.setCenterX(200);
        skive.setCenterY(200);

        Circle krans = new Circle(100);
        krans.setFill(Color.LIGHTBLUE);
        krans.setOpacity(0.5);
        krans.setCenterX(200);
        krans.setCenterY(200);
        scenegraf.getChildren().add(krans);

        //Circle sekundviser = new Circle(10);
        Rectangle sekundviser = new Rectangle();
        sekundviser.setHeight(100);
        sekundviser.setWidth(3);

        pt = new PathTransition(Duration.seconds(60), skive);
        pt.setNode(sekundviser);
        pt.setInterpolator(Interpolator.LINEAR);
        pt.setCycleCount(Timeline.INDEFINITE);
        pt.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
        scenegraf.getChildren().add(sekundviser);
        pt.play();
        igang = true;
    }

    public void nulstil() {
        pt.jumpTo(Duration.seconds(0));
    }

    public void startStop() {
        if (igang) {
            pt.pause();
            igang = false;
        } else {
            pt.play();
            igang = true;
        }

    }
}
