package numbers;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class Number {
    private long number;
    private Map<Properties, Boolean> properties;

    public Number(long number) {
        this.number = number;
        properties = new HashMap<>(10);
        properties.put(Properties.BUZZ, checkBuzz());
        properties.put(Properties.DUCK, checkDucK());
        properties.put(Properties.PALINDROMIC, checkPalindromic());
        properties.put(Properties.GAPFUL, checkGapful());
        properties.put(Properties.SPY, checkSpy());
        properties.put(Properties.SQUARE, checkSquare());
        properties.put(Properties.SUNNY, checkSunny());
        properties.put(Properties.JUMPING, checkJumping());
        properties.put(Properties.HAPPY, checkHappy());
        properties.put(Properties.SAD, !checkHappy());
        properties.put(Properties.EVEN, checkEven());
        properties.put(Properties.ODD, !checkEven());
    }

    public boolean checkPropertyValue(Properties property) {
        for (Entry entrySet : this.properties.entrySet()) {
            if (entrySet.getKey().equals(property)) {
                return (boolean) entrySet.getValue();
            }
        }
        return false;
    }

    private boolean checkEven() {
        return this.number % 2 == 0;
    }

    private boolean checkBuzz() {
        return this.number % 7 == 0 || this.number % 10 == 7;
    }

    private boolean checkDucK() {
        boolean duckNumberIndicator = false;
        long number = this.number;
        do {
            if (number % 10 == 0) {
                duckNumberIndicator = true;
                break;
            }
            number /= 10;
        }
        while (number > 1);
        return duckNumberIndicator;
    }

    private boolean checkPalindromic() {
        int lengthOfNumber = Long.toString(this.number).length();
        long reverseNumber = 0;
        long tempNumber = this.number;
        for (int i = lengthOfNumber - 1; i >= 0; i--) {
            reverseNumber += tempNumber % 10 * ((long) Math.pow(10.0, i));
            tempNumber /= 10;
        }
        return reverseNumber == this.number;
    }

    private boolean checkGapful() {
        String stringNumber = Long.toString(this.number);
        String[] stringNumberArray = stringNumber.split("");
        if (stringNumberArray.length <= 2) {
            return false;
        } else {
            long divider = Long.parseLong(stringNumberArray[0]) * 10 +
                    Long.parseLong(stringNumberArray[stringNumberArray.length - 1]);
            return this.number % divider == 0;
        }
    }

    private boolean checkSpy() {
        int sumOfDigits = 0;
        int productOfDigits = 1;
        int lastDigit;
        long number = this.number;
        while (number > 0) {
            lastDigit = (int) (number % 10);
            sumOfDigits += lastDigit;
            productOfDigits *= lastDigit;
            number /= 10;
        }
        return sumOfDigits == productOfDigits;
    }

    private boolean checkSquare() {
        long number = this.number;
        long squareRoot = (long) Math.sqrt(number);
        return squareRoot * squareRoot == number;
    }

    private boolean checkSunny() {
        long number = this.number + 1;
        long squareRoot = (long) Math.sqrt(number);
        return squareRoot * squareRoot == number;
    }

    private boolean checkJumping() {
        long number = this.number;
        if (number >= 0 && number <= 9) {
            return true;
        }
        boolean result = true;
        while (result && number / 10 > 0) {
            int lastInt = (int) (number % 10);
            number /= 10;
            int preLastInt = (int) (number % 10);
            if (preLastInt - lastInt == -1 || preLastInt - lastInt == 1) {
                continue;
            } else {
                result = false;
            }
        }
        return result;
    }

    private boolean checkHappy() {
        long number = this.number;
        while (number != 1 && number != 4 && number != 7) {
            long resultNumber = 0;
            while (number > 0) {
                resultNumber += (number % 10) * (number % 10);
                number /= 10;
            }
            number = resultNumber;
        }
        return !(number == 4);
    }

    public void showPropertiesOfNumber() {
        System.out.println(String.join("\n",
                "Properties of " + this.number,
                "buzz: " + properties.get(Properties.BUZZ),
                "duck: " + properties.get(Properties.DUCK),
                "palindromic: " + properties.get(Properties.PALINDROMIC),
                "gapful: " + properties.get(Properties.GAPFUL),
                "spy: " + properties.get(Properties.SPY),
                "square: " + properties.get(Properties.SQUARE),
                "sunny: " + properties.get(Properties.SUNNY),
                "jumping: " + properties.get(Properties.JUMPING),
                "happy: " + properties.get(Properties.HAPPY),
                "sad: " + properties.get(Properties.SAD),
                "even: " + properties.get(Properties.EVEN),
                "odd: " + properties.get(Properties.ODD)));
    }

    public void showProperties() {
        System.out.print(this.number + " is");
        System.out.println(String.join("",
                properties.get(Properties.BUZZ) ? " buzz" : "",
                properties.get(Properties.DUCK) ? " duck" : "",
                properties.get(Properties.PALINDROMIC) ? " palindromic" : "",
                properties.get(Properties.GAPFUL) ? " gapful" : "",
                properties.get(Properties.SPY) ? " spy" : "",
                properties.get(Properties.SQUARE) ? " square" : "",
                properties.get(Properties.SUNNY) ? " sunny" : "",
                properties.get(Properties.JUMPING) ? " jumping" : "",
                properties.get(Properties.HAPPY) ? " happy" : " sad",
                properties.get(Properties.EVEN) ? " even." : " odd."));
    }

    public static boolean checkStringForProperty(String property) {
        if (property.matches("[-A-Z]+")) {
            if (property.matches("-[A-Z]+")) {
                return checkStringForProperty(property.substring(1));
            }
            for (Properties element : Properties.values()) {
                if (property.equals(element.name())) {
                    return true;
                }
            }
        }
        return false;
    }

    public static void showPropertiesInSequence(long startNumber, long numbersInRow) {
        for (int i = 0; i < numbersInRow; i++) {
            Number number = new Number(startNumber);
            number.showProperties();
            startNumber++;
        }
    }

    public static void showNumbersByProperties(long startNumber, long numbersInRow,
                                               String... properties) {
        boolean flag;
        for (int i = 0; i < numbersInRow; ) {
            flag = true;
            Number number = new Number(startNumber);
            for (String property : properties) {
                if (property.matches("^-[A-Z]+")) {
                    if (!number.checkPropertyValue(Properties.valueOf(property.substring(1)))) {
                        continue;
                    } else {
                        flag = false;
                        break;
                    }
                }
                if (!number.checkPropertyValue(Properties.valueOf(property))) {
                    flag = false;
                    break;
                }
            }
            if (flag) {
                number.showProperties();
                i++;
            }
            startNumber++;
        }
    }
}
