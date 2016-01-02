package ch.fhnw.oop.project_2.views;

import ch.fhnw.oop.project_2.presentationmodels.Mountain;
import ch.fhnw.oop.project_2.presentationmodels.MountainPM;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.util.converter.NumberStringConverter;

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

        nameCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Mountain, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Mountain, String> event) {
                //((Mountain) event.getTableView().getItems().get(event.getTablePosition().getRow()).setName(event.getNewValue());
            }
        });

        //Add columns to tableview
        tableView.getColumns().addAll(idCol, nameCol, heightCol);

        // Select first item of the list to ensure we are always rendering first item in right panel.
        tableView.getSelectionModel().select(0);

        return tableView;
    }

    @Override
    public void layoutControls() {
        setMinWidth(220);
        setVgrow(mountainOverview, Priority.ALWAYS);
        int row = model.getSelectedMountainId();
        //mountainOverview.getSelectionModel().select();
        getChildren().addAll(mountainOverview, numberOfMountainsLabel);
    }

    @Override
    public void addBindings() {

        Mountain proxy = model.getMountainProxy();

        //Bindings.bindBidirectional(model.selectedMountainIdProperty(), proxy.nameProperty(), new NumberStringConverter());

       // mountainOverview.selectionModelProperty().bindBidirectional(getPresentationModel().selectedMountainIdProperty().getValue(String.valueOf(model.selectedMountainIdProperty())));

        mountainOverview.getSelectionModel().getSelectedItem().nameProperty().bind(proxy.nameProperty());
        //mountainOverview.getSelectionModel().getSelectedItem().heightProperty().bind(proxy.heightProperty());
        //mountainOverview.getSelectionModel().getSelectedItem().cantonsProperty().bind(proxy.cantonsProperty());

        //mountainOverview.focusModelProperty().bindBidirectional(getPresentationModel().selectedMountainIdProperty());
        //mountainOverview.selectionModelProperty().bindBidirectional(getPresentationModel().selectedMountainIdProperty(selected));

        // Label to show how much mountains we have
        numberOfMountainsLabel.textProperty().bind(Bindings.size(model.getMountains()).asString());
    }

    @Override
    public void addValueChangedListeners() {

        mountainOverview.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                Mountain mountain = mountainOverview.getSelectionModel().getSelectedItem();
                //mountain.nameProperty(model.nameTextFieldProperty().setValue(););
                System.out.println(mountain.toString());
                //mountainOverview.getSelectionModel().select((int) newValue);
                //model.setSelectedMountainId(mo.getMountain());
            }
        });

        mountainOverview.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            model.setSelectedMountainId(Integer.parseInt(newValue.getMountainId()));
        });
//
        //model.selectedMountainIdProperty().addListener((observable, oldValue, newValue) -> {
        //    mountainOverview.getSelectionModel().select((int) newValue);
        //});
    }
}
