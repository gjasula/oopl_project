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

    private final IntegerProperty selectedMountainId = new SimpleIntegerProperty(-1);
    private final IntegerProperty selectedIndex = new SimpleIntegerProperty(-1);
    private final ObjectProperty<Mountain> pictures = new SimpleObjectProperty<>();

    private final ObservableList<Command> undoStack = FXCollections.observableArrayList();
    private final ObservableList<Command> redoStack = FXCollections.observableArrayList();

    private final BooleanProperty undoDisabled = new SimpleBooleanProperty();
    private final BooleanProperty redoDisabled = new SimpleBooleanProperty();

    private ObservableList<Mountain> allMountains = FXCollections.observableArrayList();

    private static final String FILE_NAME = "../resources/data/mountains.csv";

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
               setSelectedMountainId(allMountains.get((Integer) newIndex).getMountainId());
            } catch (Exception e) {
                setSelectedMountainId(-1);
            }
        });

        selectedMountainIdProperty().addListener((observable, oldValue, newValue) -> {
            Mountain oldSelection = getMountain((int) oldValue);
            Mountain newSelection = getMountain((int) newValue);

            if (oldSelection != null){
                unbindFromProxy(oldSelection);
                disableUndoSupport(oldSelection);
            }
            if (newSelection != null) {
                bindToProxy(newSelection);
                enableUndoSupport(newSelection);
            }
        });
        setSelectedMountainId(0);
    }

    public final Mountain getMountainProxy () { return mountainProxy; }

    public ObservableList<Mountain> getMountains() {
        return allMountains;
    }

    public void remove() {
        Mountain toBeRemoved = getMountain(getSelectedMountainId());
        int currentPosition = allMountains.indexOf(toBeRemoved);

        removeFromList(toBeRemoved);

        redoStack.clear();
        undoStack.add(0, new RemoveCommand(this, toBeRemoved, currentPosition));

    }

    public void add() {
        int newId = allMountains.size();
        Mountain newMountain = new Mountain();
        newMountain.setMountainId(Integer.valueOf(newId));

        addToList(newId-1, newMountain);

        redoStack.clear();
        undoStack.add(0, new AddCommand(this, newMountain, allMountains.size() - 1));
    }

    void setPropertyValue(Property property, Object newValue) {
        property.removeListener(propertyChangeListenerForUndoSupport);
        property.setValue(newValue);
        property.addListener(propertyChangeListenerForUndoSupport);
    }

    void addToList(int position, Mountain mountain) {
        allMountains.add(position, mountain);
        setSelectedMountainId(mountain.getMountainId());
    }

    void removeFromList(Mountain mountain) {
        unbindFromProxy(mountain);
        disableUndoSupport(mountain);

        allMountains.remove(mountain);

        if(!allMountains.isEmpty()){
            setSelectedMountainId(allMountains.get(0).getMountainId());
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

    private void disableUndoSupport(Mountain mountain) {
        mountain.mountainIdProperty().removeListener(propertyChangeListenerForUndoSupport);
        mountain.nameProperty().removeListener(propertyChangeListenerForUndoSupport);
        mountain.regionProperty().removeListener(propertyChangeListenerForUndoSupport);
        mountain.heightProperty().removeListener(propertyChangeListenerForUndoSupport);
        mountain.cantonsProperty().removeListener(propertyChangeListenerForUndoSupport);
        mountain.typeProperty().removeListener(propertyChangeListenerForUndoSupport);
        mountain.isolationPointProperty().removeListener(propertyChangeListenerForUndoSupport);
        mountain.isolationProperty().removeListener(propertyChangeListenerForUndoSupport);
        mountain.prominencePointProperty().removeListener(propertyChangeListenerForUndoSupport);
        mountain.prominenceProperty().removeListener(propertyChangeListenerForUndoSupport);
        mountain.captionProperty().removeListener(propertyChangeListenerForUndoSupport);
        mountain.rangeProperty().removeListener(propertyChangeListenerForUndoSupport);
        mountain.filenameProperty().removeListener(propertyChangeListenerForUndoSupport);
        mountain.picturesProperty().removeListener(propertyChangeListenerForUndoSupport);
    }

    private void enableUndoSupport(Mountain mountain) {
        mountain.mountainIdProperty().addListener(propertyChangeListenerForUndoSupport);
        mountain.nameProperty().addListener(propertyChangeListenerForUndoSupport);
        mountain.regionProperty().addListener(propertyChangeListenerForUndoSupport);
        mountain.heightProperty().addListener(propertyChangeListenerForUndoSupport);
        mountain.cantonsProperty().addListener(propertyChangeListenerForUndoSupport);
        mountain.typeProperty().addListener(propertyChangeListenerForUndoSupport);
        mountain.isolationPointProperty().addListener(propertyChangeListenerForUndoSupport);
        mountain.isolationProperty().addListener(propertyChangeListenerForUndoSupport);
        mountain.prominencePointProperty().addListener(propertyChangeListenerForUndoSupport);
        mountain.prominenceProperty().addListener(propertyChangeListenerForUndoSupport);
        mountain.captionProperty().addListener(propertyChangeListenerForUndoSupport);
        mountain.rangeProperty().addListener(propertyChangeListenerForUndoSupport);
        mountain.filenameProperty().addListener(propertyChangeListenerForUndoSupport);
        mountain.picturesProperty().addListener(propertyChangeListenerForUndoSupport);
    }

    private void bindToProxy(Mountain mountain) {
        mountainProxy.mountainIdProperty().bindBidirectional(mountain.mountainIdProperty());
        mountainProxy.nameProperty().bindBidirectional(mountain.nameProperty());
        mountainProxy.regionProperty().bindBidirectional(mountain.regionProperty());
        mountainProxy.heightProperty().bindBidirectional(mountain.heightProperty());
        mountainProxy.cantonsProperty().bindBidirectional(mountain.cantonsProperty());
        mountainProxy.typeProperty().bindBidirectional(mountain.typeProperty());
        mountainProxy.isolationPointProperty().bindBidirectional(mountain.isolationPointProperty());
        mountainProxy.isolationProperty().bindBidirectional(mountain.isolationProperty());
        mountainProxy.prominencePointProperty().bindBidirectional(mountain.prominencePointProperty());
        mountainProxy.prominenceProperty().bindBidirectional(mountain.prominenceProperty());
        mountainProxy.captionProperty().bindBidirectional(mountain.captionProperty());
        mountainProxy.rangeProperty().bindBidirectional(mountain.rangeProperty());
        mountainProxy.filenameProperty().bindBidirectional(mountain.filenameProperty());
        mountainProxy.picturesProperty().bindBidirectional(mountain.picturesProperty());
    }

    private void unbindFromProxy(Mountain mountain) {
        mountainProxy.mountainIdProperty().unbindBidirectional(mountain.mountainIdProperty());
        mountainProxy.nameProperty().unbindBidirectional(mountain.nameProperty());
        mountainProxy.regionProperty().unbindBidirectional(mountain.regionProperty());
        mountainProxy.heightProperty().unbindBidirectional(mountain.heightProperty());
        mountainProxy.cantonsProperty().unbindBidirectional(mountain.cantonsProperty());
        mountainProxy.typeProperty().unbindBidirectional(mountain.typeProperty());
        mountainProxy.isolationPointProperty().unbindBidirectional(mountain.isolationPointProperty());
        mountainProxy.isolationProperty().unbindBidirectional(mountain.isolationProperty());
        mountainProxy.prominencePointProperty().unbindBidirectional(mountain.prominencePointProperty());
        mountainProxy.prominenceProperty().unbindBidirectional(mountain.prominenceProperty());
        mountainProxy.captionProperty().unbindBidirectional(mountain.captionProperty());
        mountainProxy.rangeProperty().unbindBidirectional(mountain.rangeProperty());
        mountainProxy.filenameProperty().unbindBidirectional(mountain.filenameProperty());
        mountainProxy.picturesProperty().unbindBidirectional(mountain.picturesProperty());
    }

    private List<Mountain> readFromFile() {
        try (Stream<String> stream = getStreamOfLines(FILE_NAME)) {
            return stream.skip(1)                              // erste Zeile ist die Headerzeile; ueberspringen
                    .map(s -> new Mountain(s.split(";"))) // aus jeder Zeile ein Objekt machen
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

    public Mountain getMountain (int id) {
        Optional<Mountain> pmOptional = allMountains.stream()
                .filter(mountain -> mountain.getMountainId() == id)
                .findAny();
        return pmOptional.isPresent() ? pmOptional.get() : null;
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
