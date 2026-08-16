package de.merlinmomo12.createpowergridinstrumentation.content.transmitter.current;

import de.merlinmomo12.createpowergridinstrumentation.content.transmitter.base.AbstractTransmitterBlockEntity;
import de.merlinmomo12.createpowergridinstrumentation.content.transmitter.base.TransmitterType;
import de.merlinmomo12.createpowergridinstrumentation.content.transmitter.base.TransmitterUnit;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import org.patryk3211.powergrid.electricity.sim.ElectricWire;
import org.patryk3211.powergrid.electricity.sim.node.IElectricNode;

public class CurrentTransmitterBlockEntity
        extends AbstractTransmitterBlockEntity {

    private ElectricWire measurementWire;
    private IElectricNode node1;
    private IElectricNode node2;

    public CurrentTransmitterBlockEntity(
            BlockEntityType<?> type,
            BlockPos pos,
            BlockState state
    ) {
        super(type, pos, state);
    }



    @Override
    protected TransmitterUnit getMeasurementUnit() {
        return TransmitterUnit.CURRENT;
    }

    @Override
    public TransmitterType getTransmitterType() {
        return TransmitterType.CURRENT;
    }

    @Override
    protected double getDefaultLowerRange() {
        return 0.0;
    }

    @Override
    protected double getDefaultUpperRange() {
        return 20.0;
    }

    @Override
    protected TransmitterUnit getDefaultOutputUnit() {
        return TransmitterUnit.CURRENT;
    }

    @Override
    protected int getTerminalCount() {
        return 4;
    }

    @Override
    protected void buildMeasurementCircuit(CircuitBuilder builder) {
        builder.setTerminalCount(4);

        measurementWire = builder.connect(
                0.01f,
                builder.terminalNode(2),
                builder.terminalNode(3)
        );
    }

    @Override
    protected double getMeasurement() {
        if (measurementWire == null)
            return 0.0;

        return Math.abs(measurementWire.current());
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable(
                "block.createpowergridinstrumentation.current_transmitter"
        );
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(
            int i,
            Inventory inventory,
            Player player
    ) {
        return null;
    }
}