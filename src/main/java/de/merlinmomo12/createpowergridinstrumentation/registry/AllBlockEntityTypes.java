package de.merlinmomo12.createpowergridinstrumentation.registry;

import com.tterrag.registrate.util.entry.BlockEntityEntry;
import de.merlinmomo12.createpowergridinstrumentation.CreatePowergridInstrumentation;
import de.merlinmomo12.createpowergridinstrumentation.content.transmitter.current.CurrentTransmitterBlockEntity;
import de.merlinmomo12.createpowergridinstrumentation.content.transmitter.temperature.TemperatureTransmitterBlockEntity;
import de.merlinmomo12.createpowergridinstrumentation.content.transmitter.voltage.VoltageTransmitterBlock;
import de.merlinmomo12.createpowergridinstrumentation.content.transmitter.voltage.VoltageTransmitterBlockEntity;

public class AllBlockEntityTypes {
    public static final BlockEntityEntry<TemperatureTransmitterBlockEntity> TEMPERATURE_TRANSMITTER =
            CreatePowergridInstrumentation.REGISTRATE
                    .blockEntity(
                            "temperature_transmitter",
                            TemperatureTransmitterBlockEntity::new
                    )
                    .validBlock(AllBlocks.TEMPERATURE_TRANSMITTER)
                    .register();
    public static final BlockEntityEntry<VoltageTransmitterBlockEntity> VOLTAGE_TRANSMITTER =
            CreatePowergridInstrumentation.REGISTRATE
                    .blockEntity(
                            "voltage_transmitter",
                            VoltageTransmitterBlockEntity::new
                    )
                    .validBlock(AllBlocks.VOLTAGE_TRANSMITTER)
                    .register();
    public static final BlockEntityEntry<CurrentTransmitterBlockEntity> CURRENT_TRANSMITTER =
            CreatePowergridInstrumentation.REGISTRATE
                    .blockEntity(
                            "current_transmitter",
                            CurrentTransmitterBlockEntity::new
                    )
                    .validBlock(AllBlocks.CURRENT_TRANSMITTER)
                    .register();
    public static void register() {
        // Force class loading to trigger Registrate calls
    }
}
