package project_2;


import javafx.beans.binding.Bindings;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

/**
 * Created by jslenovo on 04.11.2015.
 */
public class AppUI extends BorderPane {
    //Generate model
    private AppModel model;

    //Generate UI elements
    private HBox menu;
    private HBox splitter;
    private VBox vBox;
    private SplitPane splitPane;
    private GridPane editor;
    private ImageView imageView;
    private Image image;
    private Mountain mountain;
    private ListView<String> listView;
    private TableView<Mountain> tabelle;

    //Generate buttons
    private Button saveButton;
    private Button addButton;
    private Button removeButton;
    private Button undoButton;
    private Button redoButton;

    //Generate labels
    private Label titleLabel;
    private Label titleHeightLabel;
    private Label titleRegionLabel;
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
    private TextField searchTextfield;
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
    public AppUI(AppModel model) {
        this.model = model;
        initializeControls();
        layoutControls();
        addEventHandlers();
        addValueChangedListeners();
        addBindings();
    }

    private void initializeControls() {
        //UI Elements
        menu = new HBox();
        splitter = new HBox();

        splitPane = new SplitPane();
        editor = new GridPane();
        imageView = new ImageView();
        vBox = new VBox();

        //List on left side
        listView = new ListView<>();
        tabelle = initialiseMountainTable();

        //Buttons
        saveButton = new Button("Save");
        addButton = new Button("Add");
        removeButton = new Button("Remove");
        undoButton = new Button("Undo");
        redoButton = new Button("Redo");

        //Labels and textfield of Editor
        titleLabel = new Label ();
        titleHeightLabel = new Label ("titleheightlabel");
        titleRegionLabel = new Label ("titleregionlabel");
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

        searchTextfield = new TextField();
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

    private void layoutControls() {
        //saveButton.setMaxWidth(Double.MAX_VALUE);
        //addButton.setMaxWidth(Double.MAX_VALUE);
        //removeButton.setMaxWidth(Double.MAX_VALUE);
        //undoButton.setMaxWidth(Double.MAX_VALUE);
        //redoButton.setMaxWidth(Double.MAX_VALUE);

        //Toolbar on top
        menu.setSpacing(20);
        menu.getChildren().addAll(saveButton, addButton, removeButton, undoButton, redoButton, searchTextfield);

        //Add table on left side
        vBox.setMinWidth(220);
        //vBox.setSpacing(20);
        vBox.setVgrow(tabelle, Priority.ALWAYS);
        vBox.getChildren().addAll(tabelle, numberOfMountainsLabel);

        //Add splitpane in the middle

        splitPane.getItems().addAll(vBox,editor);
        splitter.getChildren().addAll(splitPane);

        //Editor construction
        editor.setMinWidth(600);

        editor.setVgap(20);
        editor.setHgap(5);
        ColumnConstraints cc = new ColumnConstraints();
        cc.setHgrow(Priority.ALWAYS);
        editor.getColumnConstraints().addAll(cc, cc, cc, cc);
        RowConstraints rc = new RowConstraints();
        rc.setMinHeight(10);
        editor.getRowConstraints().addAll(rc,rc,rc,rc,rc,rc,rc,rc,rc);
        //imageView.setImage(image);

        //Add all labels and fields to the editor
        editor.add(titleLabel,0,0); //child, columnIndex, rowIndex, coloumSpan, rowSpan
        editor.add(titleHeightLabel,0,1); //child, columnIndex, rowIndex, coloumSpan, rowSpan
        editor.add(titleRegionLabel,0,2); //child, columnIndex, rowIndex, coloumSpan, rowSpan
        editor.add(imageView,2,0); //Image anzeige oben rechts
        editor.add(nameLabel,0,3); //child, columnIndex, rowIndex
        editor.add(nameTextField,1,3); //child, columnIndex, rowIndex
        editor.add(heightLabel,2,3); //child, columnIndex, rowIndex
        editor.add(heightTextField,3,3); //child, columnIndex, rowIndex
        editor.add(isolationLabel,0,4);
        editor.add(isolationTextField,1,4);
        editor.add(prominenceLabel,2,4);
        editor.add(prominencePointTextField,3,4);
        editor.add(isolationPointLabel,0,5);
        editor.add(isolationPointTextField,1,5);
        editor.add(prominencePointLabel,2,5);
        editor.add(prominenceTextField,3,5);
        editor.add(typeLabel,0,6);
        editor.add(typeTextField,1,6);
        editor.add(regionLabel,2,6);
        editor.add(regionTextField,3,6);
        editor.add(cantonsLabel,0,7);
        editor.add(cantonsTextField,1,7);
        editor.add(rangeLabel,2,7);
        editor.add(rangeTextField,3,7);
        editor.add(captionLabel,0,8);
        editor.add(captionTextField,1,8);

        //Place all elements on specified position
        setTop(menu);
        setCenter(splitter);
        //setLeft(vBox);
        //setRight(editor);
        //setBottom(numberOfMountainsLabel);

        //Add different CSS classes to element
        menu.getStyleClass().add("topmenu");
        searchTextfield.getStyleClass().add("search");
    }

    private void addEventHandlers() {
        //addButton.setOnAction(event -> model.add());
        saveButton.setOnAction(event -> model.save());
        removeButton.setOnAction(event -> model.remove());

    }

    private void addValueChangedListeners() {
        //Clear mountain data
        //showMountainDetails(null);

        //Listener of tableview
        model.selectedMountainIdProperty().addListener((observable, oldValue, newValue) -> {
            tabelle.getSelectionModel().select((int) newValue);
        });

    }

    private void addBindings(){
      //Title properties are connected to the textfieldproperty
      titleLabel.textProperty().bind(model.nameTextFieldProperty());
      titleHeightLabel.textProperty().bind(model.heigthTextFieldProperty());
      titleRegionLabel.textProperty().bind(model.regionTextFieldProperty());

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

    //Hilfsklasse zur Überwachung der Tableview
    private void showMountainDetails(Mountain mountain) {


        if (mountain != null) {
            titleLabel.setText(mountain.getName());
        }
        else {
            titleLabel.setText("");
        }
    }


}
