package ch.fhnw.oop.project_2.presentationmodels;

import javafx.beans.binding.Bindings;
import javafx.beans.property.*;
import javafx.beans.value.ChangeListener;
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

    private final IntegerProperty selectedMountainId = new SimpleIntegerProperty(-1);
    private final IntegerProperty selectedIndex = new SimpleIntegerProperty(-1);

    private final ObservableList<Command> undoStack = FXCollections.observableArrayList();
    private final ObservableList<Command> redoStack = FXCollections.observableArrayList();

    private final BooleanProperty undoDisabled = new SimpleBooleanProperty();
    private final BooleanProperty redoDisabled = new SimpleBooleanProperty();

    private final ObservableList<Mountain> allMountains = FXCollections.observableArrayList();

    private static final String FILE_NAME = "mountains.csv";

    private static final String TAB = "\\t";

    public final Mountain mountainProxy = new Mountain();

    private final ChangeListener<Object> propertyChangeListenerForUndoSupport = (observable, oldValue, newValue) -> {
        redoStack.clear();
        undoStack.add(0, new ValueChangeCommand(MountainPM.this, (Property) observable, oldValue, newValue));
    };

    public MountainPM() {
        allMountains.addAll(readFromFile());

        undoDisabled.bind(Bindings.isEmpty(undoStack));
        redoDisabled.bind(Bindings.isEmpty(redoStack));

        selectedMountainIdProperty().addListener((observable1, oldId, newId) -> {
            try {
                setSelectedIndex(allMountains.indexOf(getMountain((Integer) newId)));
            } catch (Exception e) {
                setSelectedIndex(-1);
            }
        });

        selectedIndexProperty().addListener((observable1, oldId, newIndex) -> {
            try {
               // setSelectedMountainId(allMountains.get((Integer) newIndex).getMountainId());
            } catch (Exception e) {
                setSelectedMountainId(-1);
            }
        });

        selectedMountainIdProperty().addListener((observable, oldValue, newValue) -> {
            Mountain oldSelection = getMountain((int) oldValue);
            Mountain newSelection = getMountain((int) newValue);
            if (oldSelection != null){
                //unbindFromProxy(oldSelection);
                mountainProxy.mountainIdProperty().unbindBidirectional(oldSelection.mountainIdProperty());
                mountainProxy.nameProperty().unbindBidirectional(oldSelection.nameProperty());
                mountainProxy.heightProperty().unbindBidirectional(oldSelection.heightProperty());
                mountainProxy.regionProperty().unbindBidirectional(oldSelection.regionProperty());
                mountainProxy.isolationPointProperty().unbindBidirectional(oldSelection.isolationPointProperty());
                mountainProxy.prominencePointProperty().unbindBidirectional(oldSelection.prominencePointProperty());
                mountainProxy.isolationProperty().unbindBidirectional(oldSelection.isolationProperty());
                mountainProxy.prominenceProperty().unbindBidirectional(oldSelection.prominenceProperty());
                mountainProxy.typeProperty().unbindBidirectional(oldSelection.typeProperty());
                mountainProxy.cantonsProperty().unbindBidirectional(oldSelection.cantonsProperty());
                mountainProxy.rangeProperty().unbindBidirectional(oldSelection.rangeProperty());
                mountainProxy.captionProperty().unbindBidirectional(oldSelection.captionProperty());
            }
            if (newSelection != null) {
                mountainProxy.mountainIdProperty().bindBidirectional(newSelection.mountainIdProperty());
                mountainProxy.nameProperty().bindBidirectional(newSelection.nameProperty());
                mountainProxy.heightProperty().bindBidirectional(newSelection.heightProperty());
                mountainProxy.regionProperty().bindBidirectional(newSelection.regionProperty());
                mountainProxy.isolationPointProperty().bindBidirectional(newSelection.isolationPointProperty());
                mountainProxy.prominencePointProperty().bindBidirectional(newSelection.prominencePointProperty());
                mountainProxy.isolationProperty().bindBidirectional(newSelection.isolationProperty());
                mountainProxy.prominenceProperty().bindBidirectional(newSelection.prominenceProperty());
                mountainProxy.typeProperty().bindBidirectional(newSelection.typeProperty());
                mountainProxy.cantonsProperty().bindBidirectional(newSelection.cantonsProperty());
                mountainProxy.rangeProperty().bindBidirectional(newSelection.rangeProperty());
                mountainProxy.captionProperty().bindBidirectional(newSelection.captionProperty());
            }
        });
        setSelectedMountainId(0);
    }

    public final Mountain getMountainProxy () { return mountainProxy; }

    public Mountain getMountain (int id) {
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

    }

    public void addNewMountain() {
        int newId = allMountains.size();
        Mountain newMountain = new Mountain();
        //newMountain.setMountainId(Integer.valueOf(newId));

        addToList(newId-1, newMountain);

        redoStack.clear();
        undoStack.add(0, new AddCommand(this, newMountain, allMountains.size() - 1));
    }
    public void add() {
        //nameTextFieldProperty().getValue(), heigthTextFieldProperty().getValue()
        //allMountains.add(String.valueOf(allMountains.size()));
        //new Mountain(nameTextFieldProperty().getValue())
    }

    void setPropertyValue(Property property, Object newValue) {
        property.removeListener(propertyChangeListenerForUndoSupport);
        property.setValue(newValue);
        property.addListener(propertyChangeListenerForUndoSupport);
    }

    void addToList(int position, Mountain mountain) {
        allMountains.add(position, mountain);
        //setSelectedMountainId(mountain.getMountainId((int)));
    }

    void removeFromList(Mountain mountain) {
        //unbindFromProxy(mountain);
        disableUndoSupport(mountain);

        allMountains.remove(mountain);

        if(!allMountains.isEmpty()){
            //setSelectedMountainId(allMountains.get(0).getMountainId());
        }
    }

    public void undo() {
        if (undoStack.isEmpty()) {
            return;
        }
        Command cmd = undoStack.get(0);
        undoStack.remove(0);
        redoStack.add(0, cmd);

        cmd.undo();
    }

    public void redo() {
        if (redoStack.isEmpty()) {
            return;
        }
        Command cmd = redoStack.get(0);
        redoStack.remove(0);
        undoStack.add(0, cmd);

        cmd.redo();
    }

    private void disableUndoSupport(Mountain mountain) {
        mountain.mountainIdProperty().removeListener(propertyChangeListenerForUndoSupport);
        mountain.nameProperty().removeListener(propertyChangeListenerForUndoSupport);
        mountain.regionProperty().removeListener(propertyChangeListenerForUndoSupport);

    }

    private void enableUndoSupport(Mountain mountain) {
        mountain.mountainIdProperty().removeListener(propertyChangeListenerForUndoSupport);
        mountain.nameProperty().removeListener(propertyChangeListenerForUndoSupport);
        mountain.regionProperty().removeListener(propertyChangeListenerForUndoSupport);

    }

    private void bindToProxy(Mountain mountain) {
        mountain.mountainIdProperty().bindBidirectional(mountain.mountainIdProperty());
        mountain.nameProperty().bindBidirectional(mountain.nameProperty());
        mountain.regionProperty().bindBidirectional(mountain.regionProperty());

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

    public int getSelectedIndex() {
        return selectedIndex.get();
    }

    public IntegerProperty selectedIndexProperty() {
        return selectedIndex;
    }

    public void setSelectedIndex(int selectedIndex) {
        this.selectedIndex.set(selectedIndex);
    }

    public boolean getUndoDisabled() {
        return undoDisabled.get();
    }

    public BooleanProperty undoDisabledProperty() {
        return undoDisabled;
    }

    public void setUndoDisabled(boolean undoDisabled) {
        this.undoDisabled.set(undoDisabled);
    }

    public boolean getRedoDisabled() {
        return redoDisabled.get();
    }

    public BooleanProperty redoDisabledProperty() {
        return redoDisabled;
    }

    public void setRedoDisabled(boolean redoDisabled) {
        this.redoDisabled.set(redoDisabled);
    }
}
