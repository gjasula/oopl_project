package ch.fhnw.oop.project_2.views;

import ch.fhnw.oop.project_2.presentationmodels.Mountain;
import ch.fhnw.oop.project_2.presentationmodels.MountainPM;
import javafx.beans.binding.Bindings;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

/**
 * Created by heimo on 30.12.15.
 */
public class MountainTable extends VBox implements ViewMixin<MountainPM> {
    //Generate model
    private final MountainPM model;

    // Generate UI Elements
    private TableView<Mountain> mountainOverview;
    private Label numberOfMountainsLabel;

    //Constructor
    public MountainTable(MountainPM model) {
        super();
        this.model = model;
        init();
    }

    @Override
    public MountainPM getPresentationModel() {return model;}

    @Override
    public void initializeControls() {

        mountainOverview = initialiseMountainTable();
        numberOfMountainsLabel = new Label();
    }

    private TableView<Mountain> initialiseMountainTable() {
        TableView<Mountain> tableView = new TableView<>(model.getMountains());
        //Set the columns and names
        TableColumn<Mountain, String> idCol   = new TableColumn<>("ID");
        TableColumn<Mountain, String> nameCol   = new TableColumn<>("Name");
        TableColumn<Mountain, String> heightCol   = new TableColumn<>("H\u00f6he");

        //Set data of each column
        idCol.setCellValueFactory(cell -> cell.getValue().mountainIdProperty());
        nameCol.setCellValueFactory(cell -> cell.getValue().nameProperty());
        heightCol.setCellValueFactory(cell -> cell.getValue().heightProperty());

        //Add columns to tableview
        tableView.getColumns().addAll(idCol, nameCol, heightCol);
        return tableView;
    }

    @Override
    public void layoutControls() {
        setMinWidth(220);
        setVgrow(mountainOverview, Priority.ALWAYS);

        getChildren().addAll(mountainOverview, numberOfMountainsLabel);
    }

    @Override
    public void addBindings() {
        numberOfMountainsLabel.textProperty().bind(Bindings.size(model.getMountains()).asString());
    }

    @Override
    public void addValueChangedListeners() {

        //Listener of tableview
        model.selectedMountainIdProperty().addListener((observable, oldValue, newValue) -> {
            mountainOverview.getSelectionModel().select((int) newValue);
        });
    }

}
