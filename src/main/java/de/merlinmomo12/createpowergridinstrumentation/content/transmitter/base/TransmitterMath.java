package de.merlinmomo12.createpowergridinstrumentation.content.transmitter.math;

public final class TransmitterMath {

    /**
     * Standard 4–20 mA Bereich
     */
    public static final double MIN_CURRENT = 4.0;
    public static final double MAX_CURRENT = 20.0;

    /**
     * NAMUR NE43 Fehlerströme
     */
    public static final double FAIL_LOW_CURRENT = 3.6;
    public static final double FAIL_HIGH_CURRENT = 21.0;

    private TransmitterMath() {
    }

    /**
     * Berechnet den Ausgangsstrom eines linearen 4–20 mA Transmitters.
     *
     * @param measuredValue Messwert (bereits in der gewünschten Einheit)
     * @param lowerRange Lower Range Value (LRV)
     * @param upperRange Upper Range Value (URV)
     * @return Strom in mA
     */
    public static double calculateCurrent(
            double measuredValue,
            double lowerRange,
            double upperRange
    ) {

        if (upperRange <= lowerRange) {
            throw new IllegalArgumentException(
                    "UpperRange muss größer als LowerRange sein."
            );
        }

        double percentage =
                (measuredValue - lowerRange)
                        / (upperRange - lowerRange);

        percentage = clamp(percentage, 0.0, 1.0);

        return MIN_CURRENT
                + percentage * (MAX_CURRENT - MIN_CURRENT);
    }

    /**
     * Liefert den NAMUR NE43 Fail-Low-Strom.
     *
     * @return Strom in mA
     */
    public static double getFailLowCurrent() {
        return FAIL_LOW_CURRENT;
    }

    /**
     * Liefert den NAMUR NE43 Fail-High-Strom.
     *
     * @return Strom in mA
     */
    public static double getFailHighCurrent() {
        return FAIL_HIGH_CURRENT;
    }

    /**
     * Begrenzt einen Wert auf einen Bereich.
     */
    private static double clamp(
            double value,
            double min,
            double max
    ) {

        return Math.max(min, Math.min(max, value));

    }

}