package commands;

import input_handlers.WorkerInputHandler;
import managers.CollectionManager;
import models.Worker;
import utils.Console;

/**
 * Command for adding a new element with a given key.
 *
 * @author AlanTheKnight
 */
public class Insert extends ConsoleCollectionCommand {
    /**
     * Constructor for the command.
     *
     * @param console           console
     * @param collectionManager collection manager
     */
    public Insert(Console console, CollectionManager collectionManager) {
        super("insert",
                "Добавить новый элемент с заданным ключом",
                "insert <id>",
                console, collectionManager);
    }

    @Override
    public boolean execute(String[] arguments) {
        Integer id = readIdArg(arguments[1], console);
        if (id == null) {
            return false;
        }

        try {
            Worker w = WorkerInputHandler.inputWorker(console);
            collectionManager.insertWorker(id, w);
            console.printSuccess("Элемент с id " + id + " успешно добавлен");
            return true;
        } catch (WorkerInputHandler.WorkerInputException e) {
            console.printError("Ошибка ввода: " + e.getMessage());
            return false;
        }
    }
}
