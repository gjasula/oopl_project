package ch.fhnw.oop.project_2.views;

import ch.fhnw.oop.project_2.presentationmodels.Mountain;
import ch.fhnw.oop.project_2.presentationmodels.MountainPM;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.shape.Circle;

/**
 * Created by heimo on 30.12.15.
 */
public class MountainHeader extends GridPane implements ViewMixin<MountainPM> {

    //Generate model
    private final MountainPM model;

    //Generate UI Elements
    private ImageView imageView;
    private Image image;
    private Circle circle;

    //Generate labels
    private Label titleLabel;
    private Label titleHeightLabel;
    private Label titleRegionLabel;

    //Constructor
    public MountainHeader(MountainPM model) {
        super();
        this.model = model;
        init();
    }


    @Override
    public MountainPM getPresentationModel() {return model;}

    @Override
    public void initializeControls() {
        //Labels and textfield of Editor
        titleLabel = new Label("test");
        titleHeightLabel = new Label ("titleheightlabel");
        titleRegionLabel = new Label ("titleregionlabel");

        imageView = new ImageView();
    }

    @Override
    public void layoutControls() {

        //Editor construction
        setMinWidth(600);

        setVgap(20);
        setHgap(5);
        ColumnConstraints cc = new ColumnConstraints();
        cc.setHgrow(Priority.ALWAYS);
        getColumnConstraints().addAll(cc, cc);
        RowConstraints rc = new RowConstraints();
        rc.setMinHeight(10);
        getRowConstraints().addAll(rc, rc, rc);

        // image = new Image(getClass().getResourceAsStream("mountains/" + model.mountainProxy.getMountainId() + "-1.jpg"));
        circle = new Circle();
        circle.setCenterX(100.0f);
        circle.setCenterY(100.0f);
        circle.setRadius(50.0f);

        // image = new Image;
        imageView.setImage(image);
        imageView.setFitHeight(200);
        imageView.setPreserveRatio(true);
        // imageView.setClip();


        //Add all labels and fields to the editor
        //add(titleLabel,0,0); //child, columnIndex, rowIndex, columnSpan, rowSpan
        //add(titleHeightLabel,0,1); //child, columnIndex, rowIndex, columnSpan, rowSpan
        //add(titleRegionLabel,0,2); //child, columnIndex, rowIndex, columnSpan, rowSpan
        //add(imageView,1,0); //Image anzeige oben rechts
    }

    @Override
    public void addBindings() {
        Mountain proxy = model.getMountainProxy();

        //Title properties are connected to the textfieldproperty
        titleLabel.textProperty().bind(proxy.nameProperty());
        titleHeightLabel.textProperty().bind(proxy.heightProperty());
        titleRegionLabel.textProperty().bind(proxy.regionProperty());
    }

    @Override
    public void addValueChangedListeners() {


    }
}
