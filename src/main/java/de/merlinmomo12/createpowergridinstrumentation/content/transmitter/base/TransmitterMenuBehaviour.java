package de.merlinmomo12.createpowergridinstrumentation.content.transmitter.base;


import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.BehaviourType;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;



public class TransmitterMenuBehaviour extends BlockEntityBehaviour{

    public static final BehaviourType<TransmitterMenuBehaviour> TYPE =
            new BehaviourType<>("transmitter_menu");
    public TransmitterMenuBehaviour(SmartBlockEntity be) {
        super(be);
    }

    public static boolean use(Level level, BlockPos pos, Player player, InteractionHand hand) {
        if(!player.getItemInHand(hand).is(Items.NAME_TAG))
            return false;
        var behaviour = BlockEntityBehaviour.get(level, pos, TYPE);
        if(behaviour == null)
            return false;
        if(!level.isClientSide && player instanceof ServerPlayer serverPlayer)
            player.sendSystemMessage(Component.literal("Temperaturtransmitter aktiviert."));
            //MenuRegistry.openExtendedMenu(serverPlayer, behaviour, behaviour.blockEntity::sendToMenu);
        return true;

    }

    @Override
    public BehaviourType<?> getType() {
        return TYPE;
    }
}
