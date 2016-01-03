package ch.fhnw.oop.project_2.views;

import ch.fhnw.oop.project_2.presentationmodels.Mountain;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.TableCell;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by jslenovo on 03.01.2016.
 */
public class MountainTableCell extends TableCell<Mountain, String> {

    private static final Map<Mountain, Image> PICTURES = new HashMap<>();

    private static final Insets INSETS = new Insets (1, 8, 1, 5);
    private Circle circle = new Circle(30);

    //@Override
    protected  void updatePicture(Mountain picture, boolean empty) {
        //super.updatePicture(picture, empty);

        if (!empty) {
            Image img = PICTURES.get(picture);
            if (img == null) {
                img = new Image(getClass().getResource("mountains/"+ picture +"-1.jpg")
                .toExternalForm(), 18, 18, true,true, true);
                PICTURES.put(picture, img);
            }

            ImageView imageView = new ImageView(img);

            //setGraphic(imageView);
            //setTooltip(new Tooltip(picture));
            setAlignment(Pos.CENTER);
            setPadding(INSETS);
            circle.setClip(imageView);
        }
    }

}
