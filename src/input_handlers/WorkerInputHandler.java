package input_handlers;

import input_handlers.EnumInputHandler.EnumInputException;
import input_handlers.NumberInputHandler.NumberInputException;
import input_handlers.NumberInputHandler.NumberValidationException;
import models.*;
import utils.Console;

import java.time.LocalDate;

/**
 * Input handler for worker elements of the collection.
 *
 * @author AlanTheKnight
 */
public class WorkerInputHandler {
    /**
     * Inputs a worker.
     *
     * @param console console
     * @return new Worker object
     * @throws WorkerInputException if input was unsuccessful
     */
    public static Worker inputWorker(Console console) throws WorkerInputException {
        try {
            String name = inputName(console);
            Coordinates coordinates = inputCoordinates(console);
            LocalDate endDate = inputEndDate(console);
            Long salary = NumberInputHandler.input(console, Long.class, "salary", true, (number) -> {
                if (number <= 0) {
                    console.printError("Поле salary должно быть больше 0");
                    return false;
                }
                return true;
            });
            Person person = inputPerson(console);
            Position position = EnumInputHandler.input(console, Position.class, false, "position");
            Status status = EnumInputHandler.input(console, Status.class, false, "status");
            return new Worker(0, name, coordinates, salary, endDate, position, status, person);
        } catch (EnumInputException | NumberInputException | NumberValidationException e) {
            throw new WorkerInputException(e.getMessage());
        }
    }

    /**
     * Inputs coordinates.
     *
     * @param console console
     * @return new Coordinates object or null if input was unsuccessful
     * @throws WorkerInputException if input was unsuccessful
     */
    public static Coordinates inputCoordinates(Console console) throws WorkerInputException {
        try {
            int x = NumberInputHandler.input(console, Integer.class, "coordinates.x");
            Float y = NumberInputHandler.input(console, Float.class, "coordinates.y", true,
                    (number) -> {
                        if (number <= -154.0f) {
                            console.printError("Поле coordinates.y должно быть больше -154");
                            return false;
                        }
                        return true;
                    });
            return new Coordinates(x, y);
        } catch (NumberInputException e) {
            throw new WorkerInputException("Ошибка ввода координат");
        } catch (NumberValidationException e) {
            throw new WorkerInputException("");
        }
    }

    /**
     * Inputs name.
     *
     * @param console console
     * @return name
     * @throws WorkerInputException if input was unsuccessful
     */
    public static String inputName(Console console) throws WorkerInputException {
        while (true) {
            if (console.isInteractive())
                console.print("name: ");
            String name = console.readLine(false).trim();
            if (name.isEmpty()) {
                if (!console.isInteractive()) {
                    throw new WorkerInputException("Поле name не может быть пустым");
                } else
                    console.printError("Поле name не может быть пустым");
                continue;
            }
            return name;
        }
    }

    /**
     * Inputs end date.
     *
     * @param console console
     * @return end date or null
     * @throws WorkerInputException if input was unsuccessful
     */
    public static LocalDate inputEndDate(Console console) throws WorkerInputException {
        while (true) {
            if (console.isInteractive())
                console.print("endDate (yyyy-mm-dd): ");
            String endDate = console.readLine(false).trim();
            if (endDate.isEmpty()) {
                return null;
            }
            if (!endDate.matches("\\d{4}-\\d{2}-\\d{2}")) {
                if (!console.isInteractive())
                    throw new WorkerInputException("Поле endDate должно быть в формате yyyy-mm-dd");
                else
                    console.printError("Поле endDate должно быть в формате yyyy-mm-dd");
                continue;
            }
            return LocalDate.parse(endDate);
        }
    }

    /**
     * Inputs person.
     *
     * @param console console
     * @return new Person object
     * @throws WorkerInputException if input was unsuccessful
     */
    public static Person inputPerson(Console console) throws WorkerInputException {
        try {
            Double height = NumberInputHandler.input(console, Double.class, "person.height",
                    true, (number) -> {
                        if (number <= 0) {
                            console.printError("Поле person.height должно быть больше 0");
                            return false;
                        }
                        return true;
                    });
            Long weight = NumberInputHandler.input(console, Long.class, "person.weight",
                    true, (number) -> {
                        if (number <= 0) {
                            console.printError("Поле person.weight должно быть больше 0");
                            return false;
                        }
                        return true;
                    });
            Color hairColor = EnumInputHandler.input(console, Color.class, false, "person.hairColor");
            Country nationality = EnumInputHandler.input(console, Country.class, false, "person.nationality");

            // Additional null checks
            if (height == null) throw new WorkerInputException("Поле person.height не может быть пустым");
            if (weight == null) throw new WorkerInputException("Поле person.weight не может быть пустым");

            return new Person(height, weight, hairColor, nationality);
        } catch (NumberInputException | EnumInputException | NumberValidationException e) {
            throw new WorkerInputException(e.getMessage());
        }
    }

    /**
     * Exception for worker input handler, thrown when input is unsuccessful.
     */
    public static class WorkerInputException extends Exception {
        WorkerInputException(String message) {
            super(message);
        }
    }
}
