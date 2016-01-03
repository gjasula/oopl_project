package ch.fhnw.oop.project_2;


import ch.fhnw.oop.project_2.presentationmodels.MountainPM;
import ch.fhnw.oop.project_2.views.AppUI;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Created by jslenovo on 04.11.2015.
 */
public class AppStarter extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        MountainPM model = new MountainPM();
        Parent rootPanel = new AppUI(model);
        Scene scene = new Scene(rootPanel);
        // Roboto Font and FontAwesome
        String fontStylesheet = getClass().getResource("resources/css/fonts.css").toExternalForm();
        scene.getStylesheets().add(fontStylesheet);
        // Material Design
        String materialStylesheet = getClass().getResource("resources/css/material-design-skin.css").toExternalForm();
        scene.getStylesheets().add(materialStylesheet);
        // Stylesheet
        String stylesheet = getClass().getResource("resources/css/style.css").toExternalForm();
        scene.getStylesheets().add(stylesheet);
        primaryStage.titleProperty().bind(model.windowTitleProperty());
        primaryStage.setScene(scene);
        primaryStage.setMinHeight(600);
        primaryStage.setMinWidth(800);
        primaryStage.show();
    }
    public static void main(String[] args) {launch(args);}
}