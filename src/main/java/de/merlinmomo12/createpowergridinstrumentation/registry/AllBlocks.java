package de.merlinmomo12.createpowergridinstrumentation.registry;

import com.tterrag.registrate.util.entry.BlockEntry;
import de.merlinmomo12.createpowergridinstrumentation.CreatePowergridInstrumentation;
import de.merlinmomo12.createpowergridinstrumentation.content.transmitter.temperature.TemperatureTransmitterBlock;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.neoforged.neoforge.client.model.generators.ConfiguredModel;

public class AllBlocks {
    public static final BlockEntry<TemperatureTransmitterBlock> TEMPERATURE_TRANSMITTER =
            CreatePowergridInstrumentation.REGISTRATE
                    .block("temperature_transmitter", TemperatureTransmitterBlock::new)
                    .initialProperties(() -> Blocks.IRON_BLOCK)
                    .properties(p -> p.noOcclusion())
                    .item()
                    .build()
                    .register();
    public static void register() {
        // Force class loading to trigger Registrate calls
    }
}
