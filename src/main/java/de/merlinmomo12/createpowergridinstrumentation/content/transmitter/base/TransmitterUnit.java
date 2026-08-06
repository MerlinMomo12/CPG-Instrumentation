package de.merlinmomo12.createpowergridinstrumentation.content.transmitter.base;

import java.util.function.DoubleUnaryOperator;

public enum TransmitterUnit {


    CELSIUS(
            TransmitterType.TEMPERATURE,
            "°C",
            value -> value
    ),

    KELVIN(
            TransmitterType.TEMPERATURE,
            "K",
            value -> value + 273.15
    ),

    FAHRENHEIT(
            TransmitterType.TEMPERATURE,
            "°F",
            value -> value * 9 / 5 + 32
    );


    private final TransmitterType type;
    private final String symbol;

    /*
     * Wandelt IMMER vom Basistyp
     * (bei Temperatur Celsius)
     * in diese Einheit um
     */
    private final DoubleUnaryOperator converter;


    TransmitterUnit(
            TransmitterType type,
            String symbol,
            DoubleUnaryOperator converter
    ) {
        this.type = type;
        this.symbol = symbol;
        this.converter = converter;
    }


    public double convertFromBase(double value) {
        return converter.applyAsDouble(value);
    }


    public double convertTo(
            double value,
            TransmitterUnit target
    ) {

        if(this.type != target.type) {
            throw new IllegalArgumentException(
                    "Falscher TransmitterType!"
            );
        }


        // aktuell:
        // Sensor liefert immer die Basiseinheit
        return target.convertFromBase(value);
    }


    public TransmitterType getType() {
        return type;
    }


    public String getSymbol() {
        return symbol;
    }
}