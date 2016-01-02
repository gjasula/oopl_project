package ch.fhnw.oop.project_2.presentationmodels;

import javafx.beans.property.Property;

/**
 * Created by jslenovo on 02.01.2016.
 */
public class ValueChangeCommand implements Command {
    private final MountainPM model;
    private final Property property;
    private final Object oldValue;
    private final Object newValue;

    public ValueChangeCommand(MountainPM model, Property property, Object oldValue, Object newValue) {
        this.model = model;
        this.property = property;
        this.oldValue = oldValue;
        this.newValue = newValue;
    }

    public void undo() {
        model.setPropertyValue(property, oldValue);
    }

    public void redo() {
        model.setPropertyValue(property, newValue);
    }
}
