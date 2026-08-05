package de.merlinmomo12.createpowergridinstrumentation.content.transmitter.base;

import net.minecraft.network.chat.Component;

public enum TransmitterType {

    TEMPERATURE("°C", "transmitter.type.temperature"),
    PRESSURE("bar", "transmitter.type.pressure"),
    FLOW("m³/h", "transmitter.type.flow"),
    VOLTAGE("V", "transmitter.type.voltage"),
    CURRENT("mA", "transmitter.type.current");

    private final String unit;
    private final String translationKey;

    TransmitterType(String unit, String translationKey) {
        this.unit = unit;
        this.translationKey = translationKey;
    }

    /**
     * Gibt die Einheit zurück (z.B. °C oder bar).
     */
    public String getUnit() {
        return unit;
    }

    /**
     * Gibt den Translation-Key zurück.
     */
    public String getTranslationKey() {
        return translationKey;
    }

    /**
     * Gibt den lokalisierten Namen zurück.
     */
    public Component getName() {
        return Component.translatable(translationKey);
    }
}