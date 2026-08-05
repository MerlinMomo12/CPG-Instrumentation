package de.merlinmomo12.createpowergridinstrumentation.content.transmitter.temperature;

import com.simibubi.create.foundation.block.IBE;

import de.merlinmomo12.createpowergridinstrumentation.content.transmitter.base.AbstractTransmitterBlock;
import de.merlinmomo12.createpowergridinstrumentation.content.transmitter.base.AbstractTransmitterBlockEntity;
import de.merlinmomo12.createpowergridinstrumentation.registry.AllBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.network.PacketDistributor;
import org.patryk3211.powergrid.electricity.base.ElectricBlock;
import org.patryk3211.powergrid.electricity.base.IDecoratedTerminal;
import org.patryk3211.powergrid.electricity.base.TerminalBoundingBox;
import org.patryk3211.powergrid.electricity.base.terminals.BlockStateTerminalCollection;


public class TemperatureTransmitterBlock
        extends AbstractTransmitterBlock
        implements IBE<TemperatureTransmitterBlockEntity> {


    /*
     * Basis: NORTH
     * entspricht deinem Blockbench Modell
     */

    private static final VoxelShape NORTH_SHAPE =
            Shapes.or(
                    box(4, 4, 1, 12, 12, 3),
                    box(4.9, 4.9, 0, 11.1, 11.1, 1),
                    box(6.95, 6.95, -8, 9.05, 9.05, 0)
            );


    private static final TerminalBoundingBox[] NORTH_TERMINALS =
            new TerminalBoundingBox[] {

                    new TerminalBoundingBox(
                            IDecoratedTerminal.CONNECTOR,
                            5.5, 9, 2.5,
                            7.5, 11, 3.5
                    ).withColor(IDecoratedTerminal.RED),


                    new TerminalBoundingBox(
                            IDecoratedTerminal.CONNECTOR,
                            8.5, 9, 2.5,
                            10.5, 11, 3.5
                    ).withColor(IDecoratedTerminal.BLUE)

            };


    public TemperatureTransmitterBlock(BlockBehaviour.Properties settings) {
        super(settings);


        registerDefaultState(
                stateDefinition.any()
                        .setValue(
                                BlockStateProperties.FACING,
                                Direction.NORTH
                        )
        );


        setTerminalCollection(
                BlockStateTerminalCollection.builder(this)
                        .forAllStates(state ->
                                rotateTerminals(
                                        state.getValue(
                                                BlockStateProperties.FACING
                                        )
                                )
                        )
                        .withShapeMapper(state ->
                                rotateShape(
                                        state.getValue(
                                                BlockStateProperties.FACING
                                        )
                                )
                        )
                        .build()
        );
    }



    private static TerminalBoundingBox[] rotateTerminals(
            Direction direction) {

        return switch (direction) {

            case NORTH ->
                    NORTH_TERMINALS;


            case SOUTH ->
                    BlockStateTerminalCollection.each(
                            NORTH_TERMINALS,
                            terminal ->
                                    terminal.rotateAroundY(180)
                    );


            case EAST ->
                    BlockStateTerminalCollection.each(
                            NORTH_TERMINALS,
                            terminal ->
                                    terminal.rotateAroundY(90)
                    );


            case WEST ->
                    BlockStateTerminalCollection.each(
                            NORTH_TERMINALS,
                            terminal ->
                                    terminal.rotateAroundY(270)
                    );


            case UP ->
                    BlockStateTerminalCollection.each(
                            NORTH_TERMINALS,
                            terminal ->
                                    terminal.rotateAroundX(270)
                    );


            case DOWN ->
                    BlockStateTerminalCollection.each(
                            NORTH_TERMINALS,
                            terminal ->
                                    terminal.rotateAroundX(90)
                    );
        };
    }



    private static VoxelShape rotateShape(Direction direction) {

        return switch (direction) {

            case NORTH ->
                    NORTH_SHAPE;


            case SOUTH ->
                    Shapes.or(
                            box(4,4,13,12,12,15),
                            box(4.9,4.9,15,11.1,11.1,16),
                            box(6.95,6.95,16,9.05,9.05,24)
                    );


            case EAST ->
                    Shapes.or(
                            box(13,4,4,15,12,12),
                            box(15,4.9,4.9,16,11.1,11.1),
                            box(16,6.95,6.95,24,9.05,9.05)
                    );


            case WEST ->
                    Shapes.or(
                            box(1,4,4,3,12,12),
                            box(0,4.9,4.9,1,11.1,11.1),
                            box(-8,6.95,6.95,0,9.05,9.05)
                    );


            case UP ->
                    Shapes.or(
                            box(4,13,4,12,15,12),
                            box(4.9,15,4.9,11.1,16,11.1),
                            box(6.95,16,6.95,9.05,24,9.05)
                    );


            case DOWN ->
                    Shapes.or(
                            box(4,1,4,12,3,12),
                            box(4.9,0,4.9,11.1,1,11.1),
                            box(6.95,-8,6.95,9.05,0,9.05)
                    );
        };
    }







    @Override
    protected void createBlockStateDefinition(
            StateDefinition.Builder<Block, BlockState> builder) {

        builder.add(BlockStateProperties.FACING);
    }



    @Override
    public BlockState getStateForPlacement(
            BlockPlaceContext context) {

        return defaultBlockState()
                .setValue(
                        BlockStateProperties.FACING,
                        context.getClickedFace().getOpposite()
                );
    }



    @Override
    public Class<TemperatureTransmitterBlockEntity> getBlockEntityClass() {
        return TemperatureTransmitterBlockEntity.class;
    }



    @Override
    public BlockEntityType<? extends TemperatureTransmitterBlockEntity> getBlockEntityType() {
        return AllBlockEntityTypes.TEMPERATURE_TRANSMITTER.get();
    }
}