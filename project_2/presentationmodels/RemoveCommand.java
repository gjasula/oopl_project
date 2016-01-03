package ch.fhnw.oop.project_2.presentationmodels;

/**
 * Created by jslenovo on 02.01.2016.
 */
public class RemoveCommand implements Command {
    private final MountainPM  model;
    private final Mountain removed;
    private final int pos;

    public RemoveCommand(MountainPM model, Mountain removed, int pos) {
        this.model = model;
        this.removed = removed;
        this.pos = pos;
    }

    @Override
    public void undo() {
        model.getMountains().add(pos, removed);
        model.setSelectedMountainId(removed.getMountainId());
    }

    @Override
    public void redo() {
        model.removeFromList(removed);
    }

}
