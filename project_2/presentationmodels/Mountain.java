package ch.fhnw.oop.project_2.presentationmodels;


import javafx.beans.property.*;

/**
 * Created by steudler on 29.09.2015.
 */
public class Mountain {

    // Initialization
    private final IntegerProperty mountainId = new SimpleIntegerProperty();
    private final StringProperty name = new SimpleStringProperty();
    private final IntegerProperty height = new SimpleIntegerProperty();
    private final StringProperty type = new SimpleStringProperty();
    private final StringProperty region = new SimpleStringProperty();
    private final StringProperty cantons = new SimpleStringProperty();
    private final StringProperty range = new SimpleStringProperty();
    private final DoubleProperty isolation = new SimpleDoubleProperty();
    private final SimpleStringProperty isolationPoint = new SimpleStringProperty();
    private final IntegerProperty prominence = new SimpleIntegerProperty();
    private final StringProperty prominencePoint = new SimpleStringProperty();
    private final StringProperty caption = new SimpleStringProperty();
    private final StringProperty filename = new SimpleStringProperty();
    private final ObjectProperty<Mountain> pictures = new SimpleObjectProperty<>();


    // Default Constructor
    public Mountain () {    }

    // Constructor
    public Mountain(String[] line) {
        setMountainId(Integer.parseInt(line[0]));
        setName(line[1]);
        setHeight(Integer.parseInt(line[2]));
        setType(line[3]);
        setRegion(line[4]);
        setCantons(line[5]);
        setRange(line[6]);
        setIsolation(Double.parseDouble(line[7]));
        setIsolationPoint(line[8]);
        setProminence(Integer.parseInt(line[9]));
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

        return getMountainId() == mountain.getMountainId();
    }

    //@Override
    //public int hashCode() {
    //    return getMountainId().hashCode();
    //}

    public String infoAsLine() {
        return String.join("\t",
                Integer.toString(getMountainId()),
                getName(),
                Integer.toString(getHeight()),
                getType(),
                getRegion(),
                getCantons(),
                getRange(),
                Double.toString(getIsolation()),
                getIsolationPoint(),
                Integer.toString(getProminence()),
                getProminencePoint(),
                getCaption());
    }

    @Override
    public String toString() {
        return infoAsLine();
    }

    // Getter and Setter


    public int getMountainId() {
        return mountainId.get();
    }

    public IntegerProperty mountainIdProperty() {
        return mountainId;
    }

    public void setMountainId(int mountainId) {
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

    public int getHeight() {
        return height.get();
    }

    public IntegerProperty heightProperty() {
        return height;
    }

    public void setHeight(int height) {
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

    public double getIsolation() {
        return isolation.get();
    }

    public DoubleProperty isolationProperty() {
        return isolation;
    }

    public void setIsolation(double isolation) {
        this.isolation.set(isolation);
    }

    public String getIsolationPoint() {
        return isolationPoint.get();
    }

    public SimpleStringProperty isolationPointProperty() {
        return isolationPoint;
    }

    public void setIsolationPoint(String isolationPoint) {
        this.isolationPoint.set(isolationPoint);
    }

    public int getProminence() {
        return prominence.get();
    }

    public IntegerProperty prominenceProperty() {
        return prominence;
    }

    public void setProminence(int prominence) {
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

    public String getFilename() {
        return filename.get();
    }

    public StringProperty filenameProperty() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename.set(mountainIdProperty().toString() + "-1.jpg");
    }

    public Mountain getPictures() {
        return pictures.get();
    }

    public ObjectProperty<Mountain> picturesProperty() {
        return pictures;
    }

    public void setPictures(Mountain pictures) {
        this.pictures.set(pictures);
    }
}