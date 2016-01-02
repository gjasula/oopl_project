package ch.fhnw.oop.project_2.presentationmodels;

/**
 * @author Dieter Holz
 */
public interface Command {
	void undo();

	void redo();
}
