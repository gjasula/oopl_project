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

        String stylesheet = getClass().getResource("style.css").toExternalForm();
        scene.getStylesheets().add(stylesheet);

        primaryStage.titleProperty().bind(model.windowTitleProperty());
        primaryStage.setScene(scene);
        primaryStage.setMinHeight(600);
        primaryStage.setMinWidth(800);

        primaryStage.show();

        model.selectedMountainIdProperty();
    }

    public static void main(String[] args) throws Exception {launch(args); }

}
