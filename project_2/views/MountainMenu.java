package ch.fhnw.oop.project_2.views;

import ch.fhnw.oop.project_2.presentationmodels.MountainPM;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

import javax.swing.text.View;

/**
 * Created by heimo on 30.12.15.
 */
public class MountainMenu extends HBox implements ViewMixin<MountainPM> {
    //Generate model
    private final MountainPM model;

    //Generate buttons
    private Button saveButton;
    private Button addButton;
    private Button removeButton;
    private Button undoButton;
    private Button redoButton;

    // Searchbox
    private TextField searchTextfield;

    //Constructor
    public MountainMenu(MountainPM model) {
        super();
        this.model = model;
        init();
    }

    @Override
    public MountainPM getPresentationModel() {return model;}

    @Override
    public void initializeControls() {

        // Buttons
        saveButton = new Button();
        addButton = new Button();
        removeButton = new Button();
        undoButton = new Button();
        redoButton = new Button();

        // Search Field
        searchTextfield = new TextField();
    }

    @Override
    public void layoutControls() {

        //Toolbar on top
        setSpacing(20);
        getChildren().addAll(saveButton, addButton, removeButton, undoButton, redoButton, searchTextfield);

        //Add different ID to Buttons CSS
        saveButton.setText('\uf0c7' + "");
        addButton.setText('\uf055' + "");
        removeButton.setText('\uf056' + "");
        undoButton.setText('\uf0e2' + "");
        redoButton.setText('\uf0e3' + "");

        //Add different ID to Buttons CSS
        saveButton.setId("save-btn");
        addButton.setId("add-btn");
        removeButton.setId("remove-btn");
        undoButton.setId("undo-btn");
        redoButton.setId("redo-btn");


    }
    @Override
    public void addEventHandlers() {
        addButton.setOnAction(event -> model.add());
        saveButton.setOnAction(event -> model.save());
        removeButton.setOnAction(event -> model.remove());
        redoButton.setOnAction(event -> model.redo());
        undoButton.setOnAction(event -> model.undo());
    }

    @Override
    public void addBindings() {}

    @Override
    public void addValueChangedListeners() {


    }


}
