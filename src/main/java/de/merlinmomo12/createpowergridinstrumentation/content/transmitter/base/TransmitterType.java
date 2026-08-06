package de.merlinmomo12.createpowergridinstrumentation.content.transmitter.base;

import net.minecraft.network.chat.Component;


public enum TransmitterType {


    TEMPERATURE(
            "transmitter.type.temperature"
    ),

    PRESSURE(
            "transmitter.type.pressure"
    ),

    FLOW(
            "transmitter.type.flow"
    ),

    VOLTAGE(
            "transmitter.type.voltage"
    ),

    CURRENT(
            "transmitter.type.current"
    );


    private final String translationKey;


    TransmitterType(String translationKey) {
        this.translationKey = translationKey;
    }


    public Component getName() {
        return Component.translatable(
                translationKey
        );
    }
}