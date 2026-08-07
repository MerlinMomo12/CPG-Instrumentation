package de.merlinmomo12.createpowergridinstrumentation.content.transmitter.base;

import net.minecraft.network.chat.Component;

import java.util.Arrays;
import java.util.List;


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
    public List<TransmitterUnit> getUnits() {
        return Arrays.stream(TransmitterUnit.values())
                .filter(unit -> unit.getType() == this)
                .toList();
    }
}