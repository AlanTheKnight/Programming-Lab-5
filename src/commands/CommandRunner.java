package commands;

import managers.CommandManager;
import utils.Console;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

/**
 * The CommandRunner class is responsible for running the commands.
 *
 * @author AlanTheKnight
 */
public class CommandRunner {
    private final CommandManager commandManager;
    private final Console console;
    private int MAX_RECURSION_LEVEL = 10;
    private int recursionLevel = 0;

    /**
     * Creates a new CommandRunner.
     *
     * @param console        the console
     * @param commandManager the command manager
     */
    public CommandRunner(Console console, CommandManager commandManager) {
        this.console = console;
        this.commandManager = commandManager;
    }

    /**
     * Get the maximum recursion level.
     *
     * @return the maximum recursion level
     */
    public int getMaxRecursionLevel() {
        return MAX_RECURSION_LEVEL;
    }

    /**
     * Set the maximum recursion level.
     *
     * @param maxRecursionLevel the maximum recursion level
     */
    public void setMaxRecursionLevel(int maxRecursionLevel) {
        MAX_RECURSION_LEVEL = maxRecursionLevel;
    }

    /**
     * Process the prompt.
     *
     * @param prompt the prompt to process
     * @return the exit code of the command
     */
    public ExitCode processPrompt(String prompt) {
        var readCommand = prompt.trim().split("\\s+");
        if (readCommand[0].isEmpty())
            return ExitCode.OK;

        if (readCommand[0].startsWith("//") || readCommand[0].startsWith("#"))
            return ExitCode.OK;

        if (readCommand[0].equals("execute_script")) {
            if (readCommand.length != 2) {
                if (readCommand.length == 1)
                    console.printError("Не указан файл для выполнения. Используйте команду help для получения справки.");
                else
                    console.printError("Неверное количество аргументов");
                return ExitCode.ERROR;
            }

            console.printSuccess("Выполнение скрипта " + readCommand[1] + "...");

            recursionLevel++;

            if (recursionLevel > MAX_RECURSION_LEVEL) {
                console.printError("Превышен максимальный уровень рекурсии: " + MAX_RECURSION_LEVEL);
                return ExitCode.ERROR;
            }

            commandManager.addToHistory(prompt);
            return executeScript(readCommand[1]);
        } else {
            var command = commandManager.getCommand(readCommand[0]);
            if (command == null) {
                console.printError("Команда не найдена. Используйте команду help для получения справки.");
                return ExitCode.ERROR;
            }

            commandManager.addToHistory(prompt);
            boolean commandStatus = command.run(readCommand, console);
            return commandStatus ? ExitCode.OK : ExitCode.ERROR;
        }
    }

    /**
     * Run the CommandRunner in interactive mode.
     */
    public void run() {
        try {
            ExitCode commandStatus;
            do {
                commandStatus = processPrompt(console.readLine());
            } while (commandStatus != ExitCode.EXIT);
        } catch (Exception e) {
            console.printError(e.getMessage());
        }
    }

    /**
     * Execute a script.
     *
     * @param filename the filename of the script
     * @return the exit code of the command
     */
    public ExitCode executeScript(String filename) {
        if (!new File(filename).exists()) {
            console.printError("Файл скрипта " + filename + " не найден");
            return ExitCode.ERROR;
        }

        if (!Files.isReadable(Paths.get(filename))) {
            console.printError("Файл скрипта " + filename + " не доступен для чтения");
            return ExitCode.ERROR;
        }

        try (Scanner scanner = new Scanner(new File(filename))) {
            console.selectFileScanner(scanner);
            var commandStatus = ExitCode.OK;
            do {
                commandStatus = processPrompt(scanner.nextLine());
                if (commandStatus == ExitCode.ERROR) {
                    console.printError("Ошибка при выполнении скрипта " + filename);
                    console.selectFileScanner(null);
                    return ExitCode.ERROR;
                }
            } while (commandStatus == ExitCode.OK && scanner.hasNextLine());
        } catch (Exception e) {
            console.printError(e.getMessage());
            console.printError("Ошибка при выполнении скрипта " + filename);
            return ExitCode.ERROR;
        }

        console.selectFileScanner(null);
        recursionLevel--;
        console.printSuccess("Выполнение скрипта " + filename + " завершено");

        return ExitCode.OK;
    }

    /**
     * The exit code of the command.
     */
    public enum ExitCode {
        /**
         * The command was executed successfully.
         */
        OK,

        /**
         * There was an error while executing the command.
         */
        ERROR,

        /**
         * The command was executed successfully and the program should exit.
         */
        EXIT
    }
}
