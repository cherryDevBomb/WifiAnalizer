package analyzer.util;

import javafx.scene.image.Image;

public class IconUtils {
    private static final String EXCELLENT_PATH = "/icons/excellent.png";
    private static final String GOOD_PATH = "/icons/good.png";
    private static final String FAIR_PATH = "/icons/fair.png";
    private static final String WEAK_PATH = "/icons/weak.png";

    private static final int SIZE = 30;

    public static Image getIconBySignalPower(int signalPower) {
        if (isBetween(signalPower, -50, 0)) {
            return new Image(IconUtils.class.getResourceAsStream(EXCELLENT_PATH), SIZE, SIZE, true, false);
        } else if (isBetween(signalPower, -60, -50)) {
            return new Image(IconUtils.class.getResourceAsStream(GOOD_PATH), SIZE, SIZE, true, false);
        } else if (isBetween(signalPower, -70, -60)) {
            return new Image(IconUtils.class.getResourceAsStream(FAIR_PATH), SIZE, SIZE, true, false);
        } else {
            return new Image(IconUtils.class.getResourceAsStream(WEAK_PATH), SIZE, SIZE, true, false);
        }
    }

    private static boolean isBetween(int n, int low, int high) {
        return (n >= low) && (n < high);
    }
}
