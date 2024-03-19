package input_handlers;

import utils.Console;

/**
 * Input for numeric values.
 *
 * @author AlanTheKnight
 */
public final class NumberInputHandler {
    /**
     * Asks the user for a numeric value.
     *
     * @param <T>         number type
     * @param console     console
     * @param numberClass number class
     * @param prompt      prompt
     * @param isRequired  whether the value is required
     * @param checker     number checker
     * @return number
     * @throws NumberInputException      if number conversion was unsuccessful, in file console mode
     * @throws NumberValidationException if input was unsuccessful or invalid, in file console mode
     */
    public static <T extends Number> T input(Console console, Class<T> numberClass, String prompt,
                                             boolean isRequired, NumberChecker<T> checker)
            throws NumberInputException, NumberValidationException {
        while (true) {
            if (console.isInteractive())
                console.print("Введите " + prompt + ": ");

            String input = console.readLine(false).trim();

            if (input.isEmpty()) {
                if (isRequired) {
                    if (!console.isInteractive()) {
                        throw new NumberValidationException("Поле " + prompt + " не может быть пустым");
                    } else {
                        console.printError("Поле " + prompt + " не может быть пустым");
                    }
                    continue;
                } else {
                    return null;
                }
            }
            try {
                T number = getNumber(numberClass, input);

                var isValid = true;
                if (checker != null) {
                    isValid = checker.check(number);
                }

                if (isValid) {
                    return number;
                } else {
                    if (!console.isInteractive()) {
                        throw new NumberValidationException("Введенное значение не прошло проверку");
                    }
                }

            } catch (NumberFormatException e) {
                if (!console.isInteractive()) {
                    throw new NumberInputException("Значение должно быть числом типа " + numberClass.getSimpleName());
                } else {
                    console.printError("Значение должно быть числом типа " + numberClass.getSimpleName());
                }
            }
        }

    }

    /**
     * Converts a string to a number.
     *
     * @param numberClass number class
     * @param input       input string
     * @param <T>         number type
     * @return number
     * @throws NumberInputException if input was unsuccessful
     */
    private static <T extends Number> T getNumber(Class<T> numberClass, String input) throws NumberInputException {
        T number;
        if (numberClass == Integer.class) {
            number = numberClass.cast(Integer.parseInt(input));
        } else if (numberClass == Long.class) {
            number = numberClass.cast(Long.parseLong(input));
        } else if (numberClass == Float.class) {
            number = numberClass.cast(Float.parseFloat(input));
        } else if (numberClass == Double.class) {
            number = numberClass.cast(Double.parseDouble(input));
        } else {
            throw new NumberInputException("Unsupported number class: " + numberClass);
        }
        return number;
    }

    public static <T extends Number> T input(Console console, Class<T> numberClass, String prompt)
            throws NumberInputException, NumberValidationException {
        return input(console, numberClass, prompt, true, null);
    }

    public interface NumberChecker<T extends Number> {
        boolean check(T number);
    }

    /**
     * Exception for number input, thrown when conversion is unsuccessful.
     *
     * @author AlanTheKnight
     */
    public static class NumberInputException extends Exception {
        NumberInputException(String message) {
            super(message);
        }
    }

    public static class NumberValidationException extends Exception {
        NumberValidationException(String message) {
            super(message);
        }
    }
}
