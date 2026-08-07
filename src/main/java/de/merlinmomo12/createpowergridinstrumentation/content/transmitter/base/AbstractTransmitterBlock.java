package de.merlinmomo12.createpowergridinstrumentation.content.transmitter.base;

import net.createmod.catnip.gui.ScreenOpener;
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
    protected InteractionResult useWithoutItem(
            BlockState state,
            Level level,
            BlockPos pos,
            Player player,
            BlockHitResult hit
    ) {
        if (level.isClientSide) {
            BlockEntity blockEntity = level.getBlockEntity(pos);

            if (blockEntity instanceof AbstractTransmitterBlockEntity transmitter) {
                ScreenOpener.open(new TransmitterScreen(transmitter));
            }

            return InteractionResult.SUCCESS;
        }

        BlockEntity blockEntity = level.getBlockEntity(pos);

        if (!(blockEntity instanceof AbstractTransmitterBlockEntity transmitter))
            return InteractionResult.PASS;

        // Hier wird später der Screen geöffnet
        return InteractionResult.SUCCESS;
    }

}
