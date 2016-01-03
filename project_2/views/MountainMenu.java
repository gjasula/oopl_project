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
        saveButton = new Button("Save");
        addButton = new Button("Add");
        removeButton = new Button("Remove");
        undoButton = new Button("Undo");
        redoButton = new Button("Redo");

        // Search Field
        searchTextfield = new TextField();
    }

    @Override
    public void layoutControls() {

        //Toolbar on top
        setSpacing(20);
        getChildren().addAll(saveButton, addButton, removeButton, undoButton, redoButton, searchTextfield);

        //Add different CSS classes to element
        getStyleClass().add("topmenu");
        searchTextfield.getStyleClass().add("search");
    }
    @Override
    public void addEventHandlers() {
        addButton.setOnAction(event -> model.add());
        saveButton.setOnAction(event -> model.save());
        removeButton.setOnAction(event -> model.remove());
        undoButton.setOnAction(event -> model.undo());
        redoButton.setOnAction(event -> model.redo());
    }

    @Override
    public void addBindings() {}

    @Override
    public void addValueChangedListeners() {


    }


}
