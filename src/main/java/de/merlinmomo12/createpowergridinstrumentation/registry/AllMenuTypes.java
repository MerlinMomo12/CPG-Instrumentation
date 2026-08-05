package de.merlinmomo12.createpowergridinstrumentation.registry;

import com.tterrag.registrate.builders.MenuBuilder.ForgeMenuFactory;
import com.tterrag.registrate.builders.MenuBuilder.ScreenFactory;
import com.tterrag.registrate.util.entry.MenuEntry;
import com.tterrag.registrate.util.nullness.NonNullSupplier;
import de.merlinmomo12.createpowergridinstrumentation.CreatePowergridInstrumentation;
import de.merlinmomo12.createpowergridinstrumentation.content.transmitter.base.TransmitterMenu;
import de.merlinmomo12.createpowergridinstrumentation.content.transmitter.base.TransmitterScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.MenuAccess;
import net.minecraft.world.inventory.AbstractContainerMenu;


public class AllMenuTypes {

    public static final MenuEntry<TransmitterMenu> TRANSMITTER =
            register(
                    "transmitter",
                    TransmitterMenu::new,
                    () -> TransmitterScreen::new
            );


    private static <C extends AbstractContainerMenu, S extends Screen & MenuAccess<C>> MenuEntry<C> register(
            String name,
            ForgeMenuFactory<C> factory,
            NonNullSupplier<ScreenFactory<C, S>> screenFactory
    ) {
        return CreatePowergridInstrumentation.REGISTRATE
                .menu(name, factory, screenFactory)
                .register();
    }


    public static void register() {}
}