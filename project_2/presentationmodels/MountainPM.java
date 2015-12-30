package ch.fhnw.oop.project_2.presentationmodels;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.BufferedWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by heimo on 30.12.15.
 */
public class MountainPM {
    private final StringProperty windowTitle = new SimpleStringProperty("Mountain App - J\u00fcrg Steudler");

    //Label which change their content
    private final StringProperty titleLabel = new SimpleStringProperty();
    private final StringProperty titleHeightLabel = new SimpleStringProperty();
    private final StringProperty titleRegionLabel = new SimpleStringProperty();

    private final StringProperty nameTextField = new SimpleStringProperty();
    private final StringProperty heigthTextField = new SimpleStringProperty();
    private final StringProperty isolationPointTextField = new SimpleStringProperty();
    private final StringProperty prominencePointTextField = new SimpleStringProperty();
    private final StringProperty isolationTextField = new SimpleStringProperty();
    private final StringProperty prominenceTextField = new SimpleStringProperty();
    private final StringProperty typeTextField = new SimpleStringProperty();
    private final StringProperty regionTextField = new SimpleStringProperty();
    private final StringProperty cantonsTextField = new SimpleStringProperty();
    private final StringProperty rangeTextField = new SimpleStringProperty();
    private final StringProperty captionTextField = new SimpleStringProperty();

    private static final String FILE_NAME = "mountains.csv";

    private static final String TAB = "\\t";

    private final IntegerProperty selectedMountainId = new SimpleIntegerProperty(-1);

    private final ObservableList<Mountain> allMountains = FXCollections.observableArrayList();

    public final Mountain mountainProxy = new Mountain();

    //public MountainPM() {this(allMountains());}

    public MountainPM() {
        allMountains.addAll(readFromFile());

        selectedMountainIdProperty().addListener((observable, oldValue, newValue) -> {
            Mountain oldSelection = getMountain((int) oldValue);
            Mountain newSelection = getMountain((int) newValue);
            if (oldSelection != null){
                mountainProxy.mountainIdProperty().bindBidirectional(oldSelection.mountainIdProperty());
                mountainProxy.nameProperty().bindBidirectional(oldSelection.nameProperty());
                mountainProxy.heightProperty().bindBidirectional(oldSelection.heightProperty());
                mountainProxy.regionProperty().bindBidirectional(oldSelection.regionProperty());
            }
            if (newSelection != null) {
                mountainProxy.mountainIdProperty().bindBidirectional(newSelection.mountainIdProperty());
                mountainProxy.nameProperty().bindBidirectional(newSelection.nameProperty());
                mountainProxy.heightProperty().bindBidirectional(newSelection.heightProperty());
                mountainProxy.regionProperty().bindBidirectional(newSelection.regionProperty());
            }
        });
    }


    public final Mountain getMountainProxy () { return mountainProxy; }

    private Mountain getMountain (int id) {
        Optional<Mountain> pmOptional = allMountains.stream()
                .filter(mountain -> Objects.equals(mountain.getMountainId(), id))
                .findAny();
                return pmOptional.isPresent() ? pmOptional.get() : null;
    }

    public void save() {
        try (BufferedWriter writer = Files.newBufferedWriter(getPath(FILE_NAME, true))) {
            writer.write("#id;name;height;type;region;cantons;range;isolation;isolationPoint;prominence;prominencePoint;caption");
            writer.newLine();
            allMountains.stream().forEach(mountain -> {
                try {
                    writer.write(mountain.infoAsLine());
                    writer.newLine();
                } catch (IOException e) {
                    throw new IllegalStateException(e);
                }
            });
        } catch (IOException e) {
            throw new IllegalStateException("save failed");
        }
    }

    public void remove() {
        //int selectedIndex = Inde;
        //mountain.remove(selectedIndex);
    }

    public void add() {

    }

    private List<Mountain> readFromFile() {
        try (Stream<String> stream = getStreamOfLines(FILE_NAME)) {
            return stream.skip(1)                              // erste Zeile ist die Headerzeile; ueberspringen
                    .map(s -> new Mountain(s.split(TAB))) // aus jeder Zeile ein Objekt machen
                    .collect(Collectors.toList());        // alles aufsammeln
        }
    }

    private Stream<String> getStreamOfLines(String fileName) {
        try {
            return Files.lines(getPath(fileName, true), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    private Path getPath(String fileName, boolean locatedInSameFolder)  {
        try {
            if(!locatedInSameFolder) {
                fileName = "/" + fileName;
            }
            return Paths.get(getClass().getResource(fileName).toURI());
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public ObservableList<Mountain> getMountains() {
        return allMountains;
    }



    //Getter and setter
    public String getWindowTitle() {
        return windowTitle.get();
    }

    public StringProperty windowTitleProperty() {
        return windowTitle;
    }

    public void setWindowTitle(String windowTitle) {
        this.windowTitle.set(windowTitle);
    }

    public String getTitleLabel() {
        return titleLabel.get();
    }

    public StringProperty titleLabelProperty() {
        return titleLabel;
    }

    public void setTitleLabel(String titleLabel) {
        this.titleLabel.set(titleLabel);
    }

    public String getTitleHeightLabel() {
        return titleHeightLabel.get();
    }

    public StringProperty titleHeightLabelProperty() {
        return titleHeightLabel;
    }

    public void setTitleHeightLabel(String titleHeightLabel) {
        this.titleHeightLabel.set(titleHeightLabel);
    }

    public String getTitleRegionLabel() {
        return titleRegionLabel.get();
    }

    public StringProperty titleRegionLabelProperty() {
        return titleRegionLabel;
    }

    public void setTitleRegionLabel(String titleRegionLabel) {
        this.titleRegionLabel.set(titleRegionLabel);
    }

    public String getNameTextField() {
        return nameTextField.get();
    }

    public StringProperty nameTextFieldProperty() {
        return nameTextField;
    }

    public void setNameTextField(String nameTextField) {
        this.nameTextField.set(nameTextField);
    }

    public String getHeigthTextField() {
        return heigthTextField.get();
    }

    public StringProperty heigthTextFieldProperty() {
        return heigthTextField;
    }

    public void setHeigthTextField(String heigthTextField) {
        this.heigthTextField.set(heigthTextField);
    }

    public String getIsolationPointTextField() {
        return isolationPointTextField.get();
    }

    public StringProperty isolationPointTextFieldProperty() {
        return isolationPointTextField;
    }

    public void setIsolationPointTextField(String isolationPointTextField) {
        this.isolationPointTextField.set(isolationPointTextField);
    }

    public String getProminencePointTextField() {
        return prominencePointTextField.get();
    }

    public StringProperty prominencePointTextFieldProperty() {
        return prominencePointTextField;
    }

    public void setProminencePointTextField(String prominencePointTextField) {
        this.prominencePointTextField.set(prominencePointTextField);
    }

    public String getIsolationTextField() {
        return isolationTextField.get();
    }

    public StringProperty isolationTextFieldProperty() {
        return isolationTextField;
    }

    public void setIsolationTextField(String isolationTextField) {
        this.isolationTextField.set(isolationTextField);
    }

    public String getProminenceTextField() {
        return prominenceTextField.get();
    }

    public StringProperty prominenceTextFieldProperty() {
        return prominenceTextField;
    }

    public void setProminenceTextField(String prominenceTextField) {
        this.prominenceTextField.set(prominenceTextField);
    }

    public String getTypeTextField() {
        return typeTextField.get();
    }

    public StringProperty typeTextFieldProperty() {
        return typeTextField;
    }

    public void setTypeTextField(String typeTextField) {
        this.typeTextField.set(typeTextField);
    }

    public String getRegionTextField() {
        return regionTextField.get();
    }

    public StringProperty regionTextFieldProperty() {
        return regionTextField;
    }

    public void setRegionTextField(String regionTextField) {
        this.regionTextField.set(regionTextField);
    }

    public String getCantonsTextField() {
        return cantonsTextField.get();
    }

    public StringProperty cantonsTextFieldProperty() {
        return cantonsTextField;
    }

    public void setCantonsTextField(String cantonsTextField) {
        this.cantonsTextField.set(cantonsTextField);
    }

    public String getRangeTextField() {
        return rangeTextField.get();
    }

    public StringProperty rangeTextFieldProperty() {
        return rangeTextField;
    }

    public void setRangeTextField(String rangeTextField) {
        this.rangeTextField.set(rangeTextField);
    }

    public String getCaptionTextField() {
        return captionTextField.get();
    }

    public StringProperty captionTextFieldProperty() {
        return captionTextField;
    }

    public void setCaptionTextField(String captionTextField) {
        this.captionTextField.set(captionTextField);
    }

    public int getSelectedMountainId() {
        return selectedMountainId.get();
    }

    public IntegerProperty selectedMountainIdProperty() {
        return selectedMountainId;
    }

    public void setSelectedMountainId(int selectedMountainId) {
        this.selectedMountainId.set(selectedMountainId);
    }
}
