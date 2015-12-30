package ch.fhnw.oop.project_2.views;

/**
 * Created by heimo on 30.12.15.
 */
public interface ViewMixin<T> {

    T getPresentationModel();

    default void init() {
        initializeControls();
        layoutControls();
        addEventHandlers();
        addValueChangedListeners();
        addBindings();
    }

    void initializeControls();

    void layoutControls();

    default void addEventHandlers() {
    }

    default void addValueChangedListeners() {
    }

    default void addBindings() {
    }
}