package com.example.ureksempel;

import javafx.animation.Interpolator;
import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.effect.BoxBlur;
import javafx.scene.effect.Effect;
import javafx.scene.effect.MotionBlur;
import javafx.scene.effect.Reflection;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

import java.time.LocalTime;

public class Viserur {
    boolean vises;

    // hele uret samles i en scenegrafgruppe
    Group ur = new Group();

    Pane scenegraf;
    public Viserur(Pane s) {
        scenegraf = s;
        forberedUret();
        vises = true;
        vis();
    }

    private void forberedUret() {
        // Lav skiven
        Circle krans = new Circle(50);
        krans.setFill(Color.LIGHTGRAY);
        krans.setOpacity(0.5);
        krans.setCenterX(400);
        krans.setCenterY(100);
        ur.getChildren().add(krans);

        // Minut- og timemærker
        Group mærker = new Group();
        for (int i = 0; i < 60; i++) {
            Line mærke;
            if (i%5 == 0)   // Lav længere mærke for hver time
                mærke = new Line(0, -40, 0, -50);
            else
                mærke = new Line(0, -45, 0, -50);
            mærke.setTranslateX(400); mærke.setTranslateY(100);
            mærke.getTransforms().add(new Rotate(i * (360 / 60)));
            mærker.getChildren().add(mærke);
        }
        ur.getChildren().add(mærker);

        // Lav hver viser. Få transitionen for viseren tilbage, så vi kan stille uret
        // Timeviser:
        PathTransition ptt = sætViser(13, 35, 3, Color.BLACK, 12*60*60);
        // Minutviser:
        PathTransition ptm = sætViser(20, 50, 3, Color.BLACK, 60*60);
        // Sekundviser:
        PathTransition pts = sætViser(23, 55, 1, Color.RED, 60);

        // Stil uret. Husk at viserne skal justeres afhængigt af minutter og sekunder
        double timer = LocalTime.now().getHour();
        double minutter = LocalTime.now().getMinute();
        double sekunder = LocalTime.now().getSecond();
        // Viserne placeres rigtigt ved at hoppe til det rigtige tidspunkt i transitionen
        ptt.jumpTo(Duration.hours(timer+minutter/60.0));
        ptm.jumpTo(Duration.minutes(minutter+sekunder/60.0));
        pts.jumpTo(Duration.seconds(sekunder));

        // ...og start uret
        ptt.play();
        ptm.play();
        pts.play();
    }

    private PathTransition sætViser(double stiRadius, double viserlgd, double viserbrd, Color viserfarve, double sek) {
        // Dette laver en viser, der følger en cirkulær sti, som returneres.
        // Først laver vi en path(sti) som en cirkel. Stien skal ikke ses, så vi lægger den ikke på scenegrafen.
        Circle sti = new Circle(stiRadius);
        sti.setRotate(-90);
        sti.setCenterX(400);
        sti.setCenterY(100);
        // ...så laver vi en viser som et rektangel
        Rectangle viser = new Rectangle();
        viser.setHeight(viserlgd);
        viser.setWidth(viserbrd);
        viser.setStroke(viserfarve);
        // så bruger vi stien som transition, og sørger for at den tilknyttede viser roteres ortogonalt på tangenten.
        PathTransition pt = new PathTransition(Duration.seconds(sek), sti);
        pt.setNode(viser);
        pt.setInterpolator(Interpolator.LINEAR);
        pt.setCycleCount(Timeline.INDEFINITE);
        pt.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
        // ...og husker at sætte viseren på uret
        ur.getChildren().add(viser);
        // Stien returneres, så den kan manipuleres til at sætte uret
        return pt;
    }

    public void vis() {
        if (vises) {
            vises = false;
            scenegraf.getChildren().remove(ur);
        } else {
            vises = true;

            // Eftersom hele uret er i "ur" kan vi nemt lave effekter på det - eller flytte det.
            /*BoxBlur bb = new BoxBlur();
            bb.setWidth(5);
            bb.setHeight(5);
            bb.setIterations(3);
            ur.setEffect(bb);*/
            /*Reflection r = new Reflection();
            r.setFraction(0.8);
            ur.setEffect(r);*/
            
            scenegraf.getChildren().add(ur);
        }
    }

}
