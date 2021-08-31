package numbers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        welcomeUser();
        instructUser();
        processRequest();
    }

    private static void welcomeUser() {
        System.out.println("Welcome to Amazing Numbers!");
    }

    private static void instructUser() {
        System.out.println(String.join("\n", "Supported requests:",
                "- enter a natural number to know its properties;",
                "- enter two natural numbers to to obtain the properties of the list:",
                "   * the first parameter represents a starting number;",
                "   * the second parameters show how many consecutive numbers are to be processed;",
                "- two natural numbers and a property to search for;",
                "- two natural numbers and two properties to search for;",
                "- a property preceded by minus must not be present in numbers;",
                "- separate the parameters with one space;",
                "- enter 0 to exit."));
    }

    private static void processRequest() {
        while (true) {
            System.out.println("\nEnter a request: ");
            Scanner scanner = new Scanner(System.in);
            String inputString = scanner.nextLine().toUpperCase(Locale.ROOT);
            String[] inputArray = inputString.split(" ");
            if (!checkInputParameters(inputArray)) {
                continue;
            }
            if (exit(inputArray[0])) {
                break;
            }
            if (inputArray.length >= 3) {
                if (!checkInputProperties(Arrays.copyOfRange(inputArray, 2, inputArray.length))) {
                    continue;
                }
            }
            outputResult(inputArray);
        }
    }

    private static boolean checkInputParameters(String... parameters) {
        if (parameters.length > 0 && (!parameters[0].matches("\\d+") && Long.parseLong(parameters[0]) < 0)) {
            System.out.println("The first parameter should be a natural number or zero.");
            return false;
        }
        if (parameters.length > 1 && (!parameters[1].matches("\\d+") && Long.parseLong(parameters[1]) <= 0)) {
            System.out.println("The second parameter should be a natural number.");
            return false;
        }
        return true;
    }

    private static boolean checkInputProperties(String... properties) {
        ArrayList<String> wrongProperties = new ArrayList<>(properties.length);
        for (String property : properties) {
            if (!Number.checkStringForProperty(property)) {
                wrongProperties.add(property);
            }
        }
        if (wrongProperties.size() == 1) {
            System.out.printf("The property %s is wrong.\nAvailable properties: %s",
                    Arrays.toString(wrongProperties.toArray()), Arrays.toString(Properties.values()));
            return false;
        }
        if (wrongProperties.size() > 1) {
            System.out.printf("The properties %s are wrong.\nAvailable properties: %s",
                    Arrays.toString(wrongProperties.toArray()), Arrays.toString(Properties.values()));
            return false;
        }
        if (properties.length > 1) {
            ArrayList<String> positiveProperties = new ArrayList<>();
            ArrayList<String> negativeProperties = new ArrayList<>();
            for (String property : properties) {
                if (property.matches("-[A-Z]+")) {
                    negativeProperties.add(property.substring(1));
                } else {
                    positiveProperties.add(property);
                }
            }
            if (positiveProperties.size() > 1) {
                if (!checkInputForMutuallyExclusiveProperties(positiveProperties
                        .toArray(new String[positiveProperties.size()]))) {
                    return false;
                }
            }
            if (negativeProperties.size() > 1) {
                if (!checkInputForMutuallyExclusiveProperties(negativeProperties
                        .toArray(new String[negativeProperties.size()]))) {
                    return false;
                }
            }
            for (String firstProperty : properties) {
                for (String secondProperty : properties) {
                    if (firstProperty.equals(secondProperty.substring(1))) {
                        System.out.printf("The request contains mutually exclusive properties: [%s, %s]\n" +
                                "There are no numbers with these properties.\n", firstProperty, secondProperty);
                        return false;
                    }
                }
            }
            return true;
        }
        return true;
    }

    private static void outputResult(String... inputArray) {
        long startNumber = Long.parseLong(inputArray[0]);
        Number number = new Number(startNumber);
        if (inputArray.length == 1) {
            number.showPropertiesOfNumber();
            return;
        }
        long numbersInRow = Long.parseLong(inputArray[1]);
        if (inputArray.length == 2) {
            Number.showPropertiesInSequence(startNumber, numbersInRow);
            return;
        }
        if (inputArray.length >= 3) {
            Number.showNumbersByProperties
                    (startNumber, numbersInRow, Arrays.copyOfRange(inputArray, 2, inputArray.length));
        }
    }

    private static boolean exit(String firstParameter) {
        if (Long.parseLong(firstParameter) == 0) {
            System.out.println("Goodbye!");
            return true;
        }
        return false;
    }

    private static boolean checkInputForMutuallyExclusiveProperties(String... properties) {
        for (String firstProperty : properties) {
            for (String secondProperty : properties) {
                if (Properties.checkForExclusiveProperties(Properties.valueOf(firstProperty),
                        Properties.valueOf(secondProperty))) {
                    System.out.printf("The request contains mutually exclusive properties: [%s, %s]\n" +
                            "There are no numbers with these properties.\n", firstProperty, secondProperty);
                    return false;
                }
            }
        }
        return true;
    }
}
