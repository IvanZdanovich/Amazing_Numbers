package numbers;

public enum Properties {
    BUZZ, DUCK, PALINDROMIC, GAPFUL, SPY, EVEN, ODD, SQUARE, SUNNY, JUMPING, HAPPY, SAD;

    private Properties opposite;

    static {
        EVEN.opposite = ODD;
        ODD.opposite = EVEN;
        DUCK.opposite = SPY;
        SPY.opposite = DUCK;
        SUNNY.opposite = SQUARE;
        SQUARE.opposite = SUNNY;
        HAPPY.opposite = SAD;
        SAD.opposite = HAPPY;
    }

    public static boolean checkForExclusiveProperties(Properties firstProperty, Properties secondProperty) {
        if (firstProperty.opposite == null) {
            return false;
        }
        return firstProperty.opposite.equals(secondProperty);
    }
}
