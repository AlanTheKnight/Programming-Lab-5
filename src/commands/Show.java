package commands;

import managers.CollectionManager;
import models.Worker;
import utils.Console;

/**
 * Command for printing all elements of the collection to the standard
 * output stream.
 *
 * @author AlanTheKnight
 */
public class Show extends ConsoleCollectionCommand {

    /**
     *  Create a new Show command.
     *
     * @param console           console
     * @param collectionManager collection manager
     */
    public Show(Console console, CollectionManager collectionManager) {
        super("show", "Вывести в стандартный поток вывода все элементы коллекции в строковом представлении", "show",
                console, collectionManager);
    }

    /**
     * Create a new Show command with the given name and description.
     *
     * @param console           console
     * @param collectionManager collection manager
     * @param name              name of the command
     * @param description       description of the command
     */
    public Show(Console console, CollectionManager collectionManager, String name, String description) {
        super(name, description, name, console, collectionManager);
    }

    @Override
    public boolean execute(String[] arguments) {
        for (Worker w : collectionManager.getWorkers().values()) {
            console.println(w.toString());
        }
        return true;
    }
}
