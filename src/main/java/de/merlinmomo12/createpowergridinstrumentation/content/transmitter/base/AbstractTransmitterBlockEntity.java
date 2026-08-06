package de.merlinmomo12.createpowergridinstrumentation.content.transmitter.base;

import de.merlinmomo12.createpowergridinstrumentation.content.electricity.sim.CurrentSinkWire;

import de.merlinmomo12.createpowergridinstrumentation.registry.AllMenuTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import org.patryk3211.powergrid.electricity.base.ElectricBlockEntity;


public abstract class AbstractTransmitterBlockEntity extends ElectricBlockEntity implements MenuProvider {

    protected CurrentSinkWire wire;
    protected TransmitterUnit outputUnit;

    protected abstract double getMeasurement();

    protected abstract TransmitterUnit getMeasurementUnit();

    public abstract TransmitterType getTransmitterType();





    public AbstractTransmitterBlockEntity(
            BlockEntityType<?> type,
            BlockPos pos,
            BlockState state) {


        super(type, pos, state);
        outputUnit = TransmitterUnit.FAHRENHEIT;
        System.out.println("BlockEntity erstellt");
    }


    @Override
    public Component getDisplayName() {
        return Component.literal("Transmitter");
    }


    @Override
    public AbstractContainerMenu createMenu(
            int id,
            Inventory inventory,
            Player player
    ) {

        return new TransmitterMenu(
                AllMenuTypes.TRANSMITTER.get(),
                id,
                inventory,
                this
        );
    }

    @Override
    public void tick() {
        super.tick();

        if(level == null || level.isClientSide)
            return;


        // Rohwert vom Sensor
        double measuredValue = getMeasurement();


        // Umrechnung von Sensor-Einheit zu gewünschter Einheit
        double convertedValue =
                getMeasurementUnit()
                        .convertTo(
                                measuredValue,
                                outputUnit
                        );
        wire.setTargetCurrent(convertedValue/1000.0f);



        // Hier später:
        // convertedValue -> TransmitterMath -> mA

    }



    @Override
    public void buildCircuit(CircuitBuilder builder) {
        builder.setTerminalCount(2);

        wire = new CurrentSinkWire(
                builder.terminalNode(0),
                builder.terminalNode(1)
        );

        builder.add(wire);
    }
}