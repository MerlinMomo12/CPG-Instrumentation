package de.merlinmomo12.createpowergridinstrumentation.registry;

import com.tterrag.registrate.util.entry.BlockEntityEntry;
import de.merlinmomo12.createpowergridinstrumentation.CreatePowergridInstrumentation;
import de.merlinmomo12.createpowergridinstrumentation.content.transmitter.temperature.TemperatureTransmitterBlockEntity;

public class AllBlockEntityTypes {
    public static final BlockEntityEntry<TemperatureTransmitterBlockEntity> TEMPERATURE_TRANSMITTER =
            CreatePowergridInstrumentation.REGISTRATE
                    .blockEntity(
                            "temperature_transmitter",
                            TemperatureTransmitterBlockEntity::new
                    )
                    .validBlock(AllBlocks.TEMPERATURE_TRANSMITTER)
                    .register();
    public static void register() {
        // Force class loading to trigger Registrate calls
    }
}
