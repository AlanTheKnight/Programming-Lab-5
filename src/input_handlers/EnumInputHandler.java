package input_handlers;

import utils.Console;
import utils.EnumHelper;

/**
 * Input handler for enum values.
 *
 * @author AlanTheKnight
 */
public final class EnumInputHandler {
    /**
     * Asks the user for an enum value.
     *
     * @param <T>        enum type
     * @param console    console
     * @param enumClass  enum class
     * @param isRequired whether the value is required
     * @param prompt     prompt
     * @return enum value
     * @throws EnumInputException if input was unsuccessful, in file console mode
     */
    public static <T extends Enum<T>> T input(Console console, Class<T> enumClass, boolean isRequired, String prompt)
            throws EnumInputException {
        while (true) {
            if (console.isInteractive())
                console.println("Выберите одно из значений: " + EnumHelper.enumToString(enumClass));
            String input = console.readLine(false).trim();
            if (input.isEmpty()) {
                if (isRequired) {
                    console.printError("Поле не может быть пустым");
                    if (!console.isInteractive())
                        throw new EnumInputException("Поле " + prompt + " не может быть пустым");
                    continue;
                } else {
                    return null;
                }
            }
            try {
                return Enum.valueOf(enumClass, input);
            } catch (IllegalArgumentException e) {
                String msg = "Значение поля " + prompt + " должно быть одним из: " + EnumHelper.enumToString(enumClass) + ". Полученное значение: " + input;
                if (!console.isInteractive())
                    throw new EnumInputException(msg);
                else
                    console.printError(msg);
            }
        }
    }

    /**
     * Inputs an enum value.
     *
     * @param <T>       enum type
     * @param console   console
     * @param enumClass enum class
     * @param prompt    prompt
     * @return enum value
     * @throws EnumInputException if input was unsuccessful, in file console mode
     */
    public static <T extends Enum<T>> T input(Console console, Class<T> enumClass, String prompt)
            throws EnumInputException {
        return input(console, enumClass, true, prompt);
    }

    /**
     * Exception for enum input handler, thrown when input is unsuccessful, in file
     * console mode.
     *
     * @author AlanTheKnight
     */
    public static class EnumInputException extends Exception {
        /**
         * Exception message
         */
        private final String message;

        public EnumInputException(String message) {
            this.message = message;
        }

        @Override
        public String getMessage() {
            return message;
        }
    }
}
