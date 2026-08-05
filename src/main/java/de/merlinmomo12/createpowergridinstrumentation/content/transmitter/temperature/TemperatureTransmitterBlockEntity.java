package de.merlinmomo12.createpowergridinstrumentation.content.transmitter.temperature;

import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import de.merlinmomo12.createpowergridinstrumentation.content.transmitter.base.AbstractTransmitterBlockEntity;
import de.merlinmomo12.createpowergridinstrumentation.content.transmitter.base.TransmitterType;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import org.patryk3211.powergrid.electricity.base.ThermalBehaviour;


public class TemperatureTransmitterBlockEntity extends AbstractTransmitterBlockEntity {


    public TemperatureTransmitterBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    private float temperature() {
        var facing = getBlockState().getValue(BlockStateProperties.FACING);
        var thermal = BlockEntityBehaviour.get(level, worldPosition.relative(facing), ThermalBehaviour.TYPE);
        if(thermal != null) {
            return thermal.getTemperature();
        }
        return 22.0f;
    }

    @Override
    public TransmitterType getTransmitterType() {
        return TransmitterType.TEMPERATURE;
    }

    @Override
    public void tick() {
        assert level != null;
        super.tick();
        var temperature = temperature();
        wire.setTargetCurrent(temperature / 1000.0f);
    }
}