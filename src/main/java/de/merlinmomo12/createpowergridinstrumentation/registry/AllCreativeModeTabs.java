package de.merlinmomo12.createpowergridinstrumentation.registry;

import com.tterrag.registrate.util.entry.RegistryEntry;
import de.merlinmomo12.createpowergridinstrumentation.CreatePowergridInstrumentation;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class AllCreativeModeTabs {

    public static final RegistryEntry<CreativeModeTab, CreativeModeTab> MAIN_TAB =
            CreatePowergridInstrumentation.REGISTRATE.defaultCreativeTab("main_tab", builder ->
                    builder
                            .title(Component.translatable("creativetab.mcreatepowergridinstrumentation.instrument_item_tab"))
                            .icon(() -> new ItemStack(AllBlocks.TEMPERATURE_TRANSMITTER))  // Replace with your own icon
                            .displayItems((parameters, output) -> {

                        /*
                        output.accept(Items.DIAMOND);
                        output.accept(Items.DIAMOND_BOOTS);
                         */
                    })
                            .build()

            ).register();

    public static void register() {
        // Force class loading to trigger Registrate calls
    }
}