package ch.fhnw.oop.project_2.presentationmodels;


/**
 * Created by jslenovo on 02.01.2016.
 */
public class AddCommand implements Command {

    private final MountainPM model;
    private final Mountain added;
    private final int pos;

    public AddCommand(MountainPM model, Mountain added, int pos) {
        this.model = model;
        this.added = added;
        this.pos = pos;
    }

    @Override
    public void undo() {
        model.removeFromList(added);
    }

    @Override
    public void redo() {
        model.addToList(pos, added);
    }
}
