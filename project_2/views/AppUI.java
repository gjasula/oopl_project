package ch.fhnw.oop.project_2.views;


import ch.fhnw.oop.project_2.presentationmodels.MountainPM;
import javafx.scene.control.*;
import javafx.scene.layout.*;


/**
 * Created by jslenovo on 04.11.2015.
 */

public class AppUI extends BorderPane implements ViewMixin<MountainPM> {

    //Generate model
    private final MountainPM model;

    private MountainMenu mountainMenu;
    private MountainTable mountainTable;
    private MountainHeader mountainHeader;
    private MountainForm mountainForm;

    private VBox mountainDetail;

    @Override
    public MountainPM getPresentationModel () {return model;}

    //Generate UI elements
    private SplitPane splitPane;


    //Constructor
    public AppUI(MountainPM model) {
        this.model = model;
        initializeControls();
        layoutControls();
        addEventHandlers();
        addValueChangedListeners();
        addBindings();
    }

    public void initializeControls() {
        //UI Elements
        mountainMenu = new MountainMenu(model);
        mountainTable = new MountainTable(model);
        mountainHeader = new MountainHeader(model);
        mountainForm= new MountainForm(model);

        mountainDetail = new VBox();
        splitPane = new SplitPane();
    }

    public void layoutControls() {

        // Add splitpane in the middle
        mountainDetail.getChildren().addAll(mountainHeader, mountainForm);

        splitPane.getItems().addAll(mountainTable, mountainDetail);
        //Place all elements on specified position
        setTop(mountainMenu);
        setCenter(new SplitPane(mountainTable, mountainDetail));

        mountainMenu.getStyleClass().add("main-menu");
        mountainTable.getStyleClass().add("table-view");

        mountainDetail.getStyleClass().add("mountain-details");
        mountainHeader.getStyleClass().add("mountain-header");
        mountainForm.getStyleClass().add("mountain-form");

    }

    public void addBindings(){

    }


}
