package ch.fhnw.oop.project_2.views;

import ch.fhnw.oop.project_2.presentationmodels.Mountain;
import ch.fhnw.oop.project_2.presentationmodels.MountainPM;
import javafx.beans.binding.Bindings;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;

/**
 * Created by heimo on 30.12.15.
 */
public class MountainForm extends GridPane implements ViewMixin<MountainPM> {
    //Generate model
    private final MountainPM model;

    // Generate labels
    private Label nameLabel;
    private Label heightLabel;
    private Label typeLabel;
    private Label regionLabel;
    private Label cantonsLabel;
    private Label rangeLabel;
    private Label isolationLabel;
    private Label isolationPointLabel;
    private Label prominenceLabel;
    private Label prominencePointLabel;
    private Label captionLabel;
    private Label numberOfMountainsLabel;

    //Generate textfields
    private TextField nameTextField;
    private TextField heightTextField;
    private TextField typeTextField;
    private TextField regionTextField;
    private TextField cantonsTextField;
    private TextField rangeTextField;
    private TextField isolationTextField;
    private TextField isolationPointTextField;
    private TextField prominenceTextField;
    private TextField prominencePointTextField;
    private TextField captionTextField;

    //Constructor
    public MountainForm(MountainPM model) {
        super();
        this.model = model;
        init();
    }

    @Override
    public MountainPM getPresentationModel() {return model;}

    @Override
    public void initializeControls() {
        nameLabel = new Label("Name");
        isolationLabel = new Label("Dominanz");
        isolationPointLabel = new Label("km bis");
        typeLabel = new Label("Typ");
        cantonsLabel = new Label("Kantone");
        heightLabel = new Label("Hoehe (m)");
        prominenceLabel = new Label("Scharten");
        prominencePointLabel = new Label("m bis");
        rangeLabel = new Label("Gebiet");
        regionLabel = new Label("Region");
        captionLabel = new Label("Bildunterschrift");
        numberOfMountainsLabel = new Label ();

        nameTextField = new TextField();
        isolationTextField = new TextField();
        isolationPointTextField = new TextField();
        typeTextField = new TextField();
        cantonsTextField = new TextField();
        heightTextField = new TextField();
        prominenceTextField = new TextField();
        prominencePointTextField = new TextField();
        rangeTextField = new TextField();
        regionTextField = new TextField();
        captionTextField = new TextField();
    }

    @Override
    public void layoutControls() {

        //Editor construction
        setMinWidth(600);

        setVgap(20);
        setHgap(5);
        ColumnConstraints cc = new ColumnConstraints();
        cc.setHgrow(Priority.ALWAYS);
        getColumnConstraints().addAll(cc, cc, cc, cc);
        RowConstraints rc = new RowConstraints();
        rc.setMinHeight(10);
        getRowConstraints().addAll(rc, rc, rc, rc, rc);

        //Add all labels and fields to the editor
        add(nameLabel,0,0); //child, columnIndex, rowIndex
        add(nameTextField,1,0); //child, columnIndex, rowIndex
        add(heightLabel,2,0); //child, columnIndex, rowIndex
        add(heightTextField,3,0); //child, columnIndex, rowIndex
        add(isolationLabel,0,1);
        add(isolationTextField,1,1);
        add(prominenceLabel,2,1);
        add(prominencePointTextField,3,1);
        add(isolationPointLabel,0,2);
        add(isolationPointTextField,1,2);
        add(prominencePointLabel,2,2);
        add(prominenceTextField,3,2);
        add(typeLabel,0,3);
        add(typeTextField,1,3);
        add(regionLabel,2,3);
        add(regionTextField,3,3);
        add(cantonsLabel,0,4);
        add(cantonsTextField,1,4);
        add(rangeLabel,2,4);
        add(rangeTextField,3,4);
        add(captionLabel,0,5);
        add(captionTextField,1,5);
    }

    @Override
    public void addBindings() {

        Mountain proxy = model.getMountainProxy();
        //Textfield properties
        nameTextField.textProperty().bind(model.nameTextFieldProperty());
        heightTextField.textProperty().bind(model.heigthTextFieldProperty());
        isolationPointTextField.textProperty().bind(model.isolationPointTextFieldProperty());
        prominencePointTextField.textProperty().bind(model.prominencePointTextFieldProperty());
        isolationTextField.textProperty().bind(model.isolationTextFieldProperty());
        prominenceTextField.textProperty().bind(model.prominenceTextFieldProperty());
        typeTextField.textProperty().bind(model.typeTextFieldProperty());
        regionTextField.textProperty().bind(model.regionTextFieldProperty());
        cantonsTextField.textProperty().bind(model.cantonsTextFieldProperty());
        rangeTextField.textProperty().bind(model.rangeTextFieldProperty());
        captionTextField.textProperty().bind(model.captionTextFieldProperty());

        numberOfMountainsLabel.textProperty().bind(Bindings.size(model.getMountains()).asString());


    }

    @Override
    public void addValueChangedListeners() {


    }
}
