import commands.*;
import managers.CollectionManager;
import managers.CommandManager;
import managers.DumpManager;
import utils.StandardConsole;

/**
 * Main class.
 */
public class Main {
    /**
     * Main method.
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        var console = new StandardConsole();

        if (args.length == 0) {
            console.printError("Не указан файл с данными.");
            return;
        }

        var dumpManager = new DumpManager(args[0], console);
        var collectionManager = new CollectionManager(dumpManager);
        try {
            collectionManager.loadCollection();
        } catch (IllegalArgumentException | DumpManager.DocumentReadException e) {
            console.printError("Не удалось загрузить коллекцию из файла.");
            console.printError(e.getMessage());
            return;
        }

        var commandManager = new CommandManager() {
            {
                register(new Exit(console));
                register(new History(console, this));
                register(new Help(console, this));
                register(new Save(console, collectionManager));
                register(new Show(console, collectionManager));
                register(new RemoveKey(console, collectionManager));
                register(new Show(console, collectionManager, "print_ascending",
                        "Вывести элементы коллекции в порядке возрастания"));
                register(new Clear(console, collectionManager));
                register(new Info(console, collectionManager));
                register(new RemoveGreaterKey(console, collectionManager));
                register(new FilterByEndDate(console, collectionManager));
                register(new RemoveAnyByEndDate(console, collectionManager));
                register(new ExecuteScript());
                register(new Insert(console, collectionManager));
                register(new Update(console, collectionManager));
                register(new RemoveGreater(console, collectionManager));
            }
        };

        var commandRunner = new CommandRunner(console, commandManager);
        commandRunner.run();
    }
}
