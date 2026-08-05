package de.merlinmomo12.createpowergridinstrumentation.content.transmitter.base;

import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import de.merlinmomo12.createpowergridinstrumentation.content.electricity.sim.CurrentSinkWire;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import org.patryk3211.powergrid.electricity.base.ElectricBlockEntity;

import java.util.List;


public abstract class AbstractTransmitterBlockEntity extends ElectricBlockEntity {

    protected CurrentSinkWire wire;
    protected float lowValue = 4f;
    protected float highValue = 20f;
    public abstract TransmitterType getTransmitterType();





    public AbstractTransmitterBlockEntity(
            BlockEntityType<?> type,
            BlockPos pos,
            BlockState state) {


        super(type, pos, state);
        System.out.println("BlockEntity erstellt");
    }
    public float getLowValue() {
        return lowValue;
    }

    public void setLowValue(float lowValue) {
        this.lowValue = lowValue;
        setChanged();
    }

    public float getHighValue() {
        return highValue;
    }

    public void setHighValue(float highValue) {
        this.highValue = highValue;
        setChanged();
    }
    @Override
    public void addBehaviours(List<BlockEntityBehaviour> behaviours) {
        super.addBehaviours(behaviours);

        behaviours.add(new TransmitterMenuBehaviour(this));
    }

    @Override
    protected void write(CompoundTag tag, HolderLookup.Provider registries, boolean clientPacket) {
        super.write(tag, registries, clientPacket);

        tag.putFloat("LowValue", lowValue);
        tag.putFloat("HighValue", highValue);


    }
    @Override
    protected void read(CompoundTag tag, HolderLookup.Provider registries, boolean clientPacket) {
        super.read(tag, registries, clientPacket);

        lowValue = tag.getFloat("LowValue");
        highValue = tag.getFloat("HighValue");

    }
    @Override
    public void tick() {
        super.tick();

        if (level != null && !level.isClientSide && level.getGameTime() % 100 == 0) {
            level.players().forEach(player ->
                    player.sendSystemMessage(getTransmitterType().getName())
            );
        }
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