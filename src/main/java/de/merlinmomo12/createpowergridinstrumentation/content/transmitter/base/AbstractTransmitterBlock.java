package de.merlinmomo12.createpowergridinstrumentation.content.transmitter.base;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.patryk3211.powergrid.electricity.base.ElectricBlock;

public class AbstractTransmitterBlock extends ElectricBlock {
    public AbstractTransmitterBlock(Properties settings) {
        super(settings);
    }

    @Override
    public InteractionResult use(
            BlockState state,
            Level level,
            BlockPos pos,
            Player player,
            InteractionHand hand,
            BlockHitResult hit
    ) {

        if(!level.isClientSide) {

            if(level.getBlockEntity(pos) instanceof AbstractTransmitterBlockEntity be) {

                player.openMenu(be);
            }

        }

        return InteractionResult.SUCCESS;
    }

}
