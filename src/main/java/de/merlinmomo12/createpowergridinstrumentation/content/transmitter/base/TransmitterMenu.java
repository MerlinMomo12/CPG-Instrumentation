package de.merlinmomo12.createpowergridinstrumentation.content.transmitter.base;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;

import com.simibubi.create.foundation.gui.menu.MenuBase;

public class TransmitterMenu extends MenuBase<AbstractTransmitterBlockEntity> {


    public TransmitterMenu(
            MenuType<?> type,
            int id,
            Inventory inv,
            RegistryFriendlyByteBuf extraData
    ) {
        super(type, id, inv, extraData);
    }


    public TransmitterMenu(
            MenuType<?> type,
            int id,
            Inventory inv,
            AbstractTransmitterBlockEntity blockEntity
    ) {
        super(type, id, inv, blockEntity);
    }

    @Override
    protected AbstractTransmitterBlockEntity createOnClient(RegistryFriendlyByteBuf extraData) {
        return null;
    }


    @Override
    protected void initAndReadInventory(AbstractTransmitterBlockEntity contentHolder) {

    }


    @Override
    protected void addSlots() {

    }


    @Override
    protected void saveData(AbstractTransmitterBlockEntity contentHolder) {

    }


    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        return ItemStack.EMPTY;
    }
}