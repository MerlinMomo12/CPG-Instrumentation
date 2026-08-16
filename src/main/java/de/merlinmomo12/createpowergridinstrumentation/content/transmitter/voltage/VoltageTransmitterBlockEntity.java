package de.merlinmomo12.createpowergridinstrumentation.content.transmitter.voltage;

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
import org.patryk3211.powergrid.electricity.sim.node.IElectricNode;

public class VoltageTransmitterBlockEntity
        extends AbstractTransmitterBlockEntity {

    private IElectricNode node1;
    private IElectricNode node2;

    public VoltageTransmitterBlockEntity(
            BlockEntityType<?> type,
            BlockPos pos,
            BlockState state
    ) {
        super(type, pos, state);
    }

    @Override
    protected double getMeasurement() {
        if (node1 == null || node2 == null)
            return 0.0;


        return node1.getVoltage() - node2.getVoltage();
    }

    @Override
    protected TransmitterUnit getMeasurementUnit() {
        return TransmitterUnit.VOLTAGE;
    }

    @Override
    public TransmitterType getTransmitterType() {
        return TransmitterType.VOLTAGE;
    }

    @Override
    protected double getDefaultLowerRange() {
        return 0.0;
    }

    @Override
    protected double getDefaultUpperRange() {
        return 24.0;
    }

    @Override
    protected TransmitterUnit getDefaultOutputUnit() {
        return TransmitterUnit.VOLTAGE;
    }

    @Override
    protected int getTerminalCount() {
        return 4;
    }

    @Override
    protected void buildMeasurementCircuit(CircuitBuilder builder) {
        node1 = builder.terminalNode(2);
        node2 = builder.terminalNode(3);
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable(
                "block.createpowergridinstrumentation.voltage_transmitter"
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