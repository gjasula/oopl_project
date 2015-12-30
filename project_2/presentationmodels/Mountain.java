package ch.fhnw.oop.project_2.presentationmodels;


import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Created by steudler on 29.09.2015.
 */
public class Mountain {

    // Initialization
    private final StringProperty mountainId = new SimpleStringProperty();
    private final StringProperty name = new SimpleStringProperty();
    private final StringProperty height = new SimpleStringProperty();
    private final StringProperty type = new SimpleStringProperty();
    private final StringProperty region = new SimpleStringProperty();
    private final StringProperty cantons = new SimpleStringProperty();
    private final StringProperty range = new SimpleStringProperty();
    private final StringProperty isolation = new SimpleStringProperty();
    private final StringProperty isolationPoint = new SimpleStringProperty();
    private final StringProperty prominence = new SimpleStringProperty();
    private final StringProperty prominencePoint = new SimpleStringProperty();
    private final StringProperty caption = new SimpleStringProperty();

    // Constructor
    public Mountain(String[] line) {
        setMountainId(line[0]);
        setName(line[1]);
        setHeight(line[2]);
        setType(line[3]);
        setRegion(line[4]);
        setCantons(line[5]);
        setRange(line[6]);
        setIsolation(line[7]);
        setIsolationPoint(line[8]);
        setProminence(line[9]);
        setProminencePoint(line[10]);
        setCaption(line[11]);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Mountain mountain = (Mountain) o;

        return getMountainId().equals(mountain.getMountainId());
    }

    @Override
    public int hashCode() {
        return getMountainId().hashCode();
    }

    public String infoAsLine() {
        return String.join("\t",
                getMountainId(),
                getName(),
                getHeight(),
                getType(),
                getRegion(),
                getCantons(),
                getRange(),
                getIsolation(),
                getIsolationPoint(),
                getProminence(),
                getProminencePoint(),
                getCaption());
    }

    @Override
    public String toString() {
        return infoAsLine();
    }

    // Getter and Setter

    public String getMountainId() {
        return mountainId.get();
    }

    public StringProperty mountainIdProperty() {
        return mountainId;
    }

    public void setMountainId(String mountainId) {
        this.mountainId.set(mountainId);
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getHeight() {
        return height.get();
    }

    public StringProperty heightProperty() {
        return height;
    }

    public void setHeight(String height) {
        this.height.set(height);
    }

    public String getType() {
        return type.get();
    }

    public StringProperty typeProperty() {
        return type;
    }

    public void setType(String type) {
        this.type.set(type);
    }

    public String getRegion() {
        return region.get();
    }

    public StringProperty regionProperty() {
        return region;
    }

    public void setRegion(String region) {
        this.region.set(region);
    }

    public String getCantons() {
        return cantons.get();
    }

    public StringProperty cantonsProperty() {
        return cantons;
    }

    public void setCantons(String cantons) {
        this.cantons.set(cantons);
    }

    public String getRange() {
        return range.get();
    }

    public StringProperty rangeProperty() {
        return range;
    }

    public void setRange(String range) {
        this.range.set(range);
    }

    public String getIsolation() {
        return isolation.get();
    }

    public StringProperty isolationProperty() {
        return isolation;
    }

    public void setIsolation(String isolation) {
        this.isolation.set(isolation);
    }

    public String getIsolationPoint() {
        return isolationPoint.get();
    }

    public StringProperty isolationPointProperty() {
        return isolationPoint;
    }

    public void setIsolationPoint(String isolationPoint) {
        this.isolationPoint.set(isolationPoint);
    }

    public String getProminence() {
        return prominence.get();
    }

    public StringProperty prominenceProperty() {
        return prominence;
    }

    public void setProminence(String prominence) {
        this.prominence.set(prominence);
    }

    public String getProminencePoint() {
        return prominencePoint.get();
    }

    public StringProperty prominencePointProperty() {
        return prominencePoint;
    }

    public void setProminencePoint(String prominencePoint) {
        this.prominencePoint.set(prominencePoint);
    }

    public String getCaption() {
        return caption.get();
    }

    public StringProperty captionProperty() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption.set(caption);
    }
}