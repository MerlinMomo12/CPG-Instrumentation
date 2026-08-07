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
    protected double lowerRange;
    protected double upperRange;


    protected abstract double getMeasurement();

    protected abstract TransmitterUnit getMeasurementUnit();

    public abstract TransmitterType getTransmitterType();
    protected double getDefaultLowerRange() {
        return 0.0;
    }

    protected double getDefaultUpperRange() {
        return 100.0;
    }







    public AbstractTransmitterBlockEntity(
            BlockEntityType<?> type,
            BlockPos pos,
            BlockState state) {


        super(type, pos, state);
        lowerRange = 0.0;
        upperRange = 100.0;
        outputUnit = TransmitterUnit.CELSIUS;
        System.out.println("BlockEntity erstellt");
    }
    public TransmitterUnit getOutputUnit() {
        return outputUnit;
    }

    public double getLowerRange() {
        return lowerRange;
    }

    public double getUpperRange() {
        return upperRange;
    }
    public void setOutputUnit(TransmitterUnit outputUnit) {
        this.outputUnit = outputUnit;
        setChanged();
    }

    public void setLowerRange(double lowerRange) {
        this.lowerRange = lowerRange;
        setChanged();
    }

    public void setUpperRange(double upperRange) {
        this.upperRange = upperRange;
        setChanged();
    }
    @Override
    protected void read(
            CompoundTag tag,
            HolderLookup.Provider registries,
            boolean clientPacket
    ) {
        super.read(tag, registries, clientPacket);

        if (tag.contains("OutputUnit")) {
            outputUnit = TransmitterUnit.valueOf(
                    tag.getString("OutputUnit")
            );
        }

        if(tag.contains("LowerRange"))
            lowerRange = tag.getDouble("LowerRange");

        if(tag.contains("UpperRange"))
            upperRange = tag.getDouble("UpperRange");
    }
    @Override
    protected void write(
            CompoundTag tag,
            HolderLookup.Provider registries,
            boolean clientPacket
    ) {
        super.write(tag, registries, clientPacket);

        tag.putString("OutputUnit", outputUnit.name());
        tag.putDouble("LowerRange", lowerRange);
        tag.putDouble("UpperRange", upperRange);
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

        double currentAmpere =
                TransmitterMath.calculateCurrent(
                        convertedValue,
                        lowerRange,
                        upperRange
                );


        wire.setTargetCurrent(currentAmpere/1000.0f);



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