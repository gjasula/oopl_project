package project_2;

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
        AppModel model = new AppModel();

        Parent rootPanel = new AppUI(model);

        Scene scene = new Scene(rootPanel);

        String stylesheet = getClass().getResource("style.css").toExternalForm();
        scene.getStylesheets().add(stylesheet);

        primaryStage.titleProperty().bind(model.windowTitleProperty());
        primaryStage.setScene(scene);
        primaryStage.setMinHeight(600);
        primaryStage.setMinWidth(800);

        primaryStage.show();
    }

    public static void main(String[] args) {launch(args);}

}
