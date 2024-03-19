package managers;

import commands.Command;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * The CommandManager class is responsible for managing the commands.
 *
 * @author AlanTheKnight
 */
public class CommandManager {
    private final Map<String, Command> commands = new LinkedHashMap<>();
    private final ArrayList<String> history = new ArrayList<>();

    /**
     * Register a command.
     * @param command the command to register
     */
    public void register(Command command) {
        commands.put(command.getName(), command);
    }

    /**
     * Get commands map.
     * @return the commands map
     */
    public Map<String, Command> getCommands() {
        return commands;
    }

    /**
     * Get history list.
     * @return the history list
     */
    public ArrayList<String> getHistory() {
        return history;
    }

    /**
     * Add command to history.
     * @param command the command to add
     */
    public void addToHistory(String command) {
        history.add(command);
    }

    /**
     * Get command by its name
     *
     * @param name the name of the command
     * @return the command
     */
    public Command getCommand(String name) {
        return commands.get(name);
    }
}
