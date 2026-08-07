package de.merlinmomo12.createpowergridinstrumentation.content.transmitter.temperature;

import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
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
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import org.jetbrains.annotations.Nullable;
import org.patryk3211.powergrid.electricity.base.ThermalBehaviour;


public class TemperatureTransmitterBlockEntity extends AbstractTransmitterBlockEntity {


    public TemperatureTransmitterBlockEntity(
            BlockEntityType<?> type,
            BlockPos pos,
            BlockState state
    ) {
        super(type, pos, state);
    }


    private float temperature() {

        var facing =
                getBlockState()
                        .getValue(BlockStateProperties.FACING);


        var thermal =
                BlockEntityBehaviour.get(
                        level,
                        worldPosition.relative(facing),
                        ThermalBehaviour.TYPE
                );


        if(thermal != null) {
            return thermal.getTemperature();
        }


        return 22.0f;
    }



    @Override
    protected double getMeasurement() {
        return temperature();
    }



    @Override
    protected TransmitterUnit getMeasurementUnit() {
        return TransmitterUnit.CELSIUS;
    }



    @Override
    public TransmitterType getTransmitterType() {
        return TransmitterType.TEMPERATURE;
    }
    @Override
    protected double getDefaultLowerRange() {
        return -50;
    }

    @Override
    protected double getDefaultUpperRange() {
        return 150;
    }


    @Override
    public @Nullable AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
        return null;
    }

    @Override
    public Component getDisplayName() {
        return null;
    }
}