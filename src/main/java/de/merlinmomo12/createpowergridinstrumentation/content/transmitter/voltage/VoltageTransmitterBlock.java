package de.merlinmomo12.createpowergridinstrumentation.content.transmitter.voltage;

import com.simibubi.create.foundation.block.IBE;

import de.merlinmomo12.createpowergridinstrumentation.content.transmitter.base.AbstractTransmitterBlock;
import de.merlinmomo12.createpowergridinstrumentation.registry.AllBlockEntityTypes;

import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import org.patryk3211.powergrid.electricity.base.IDecoratedTerminal;
import org.patryk3211.powergrid.electricity.base.TerminalBoundingBox;
import org.patryk3211.powergrid.electricity.base.terminals.BlockStateTerminalCollection;


public class VoltageTransmitterBlock
        extends AbstractTransmitterBlock
        implements IBE<VoltageTransmitterBlockEntity> {


    /*
     * ============================================================
     * BASIS SHAPE
     * ============================================================
     *
     * UP = Bodenmontage
     */

    private static final VoxelShape BASE_SHAPE =
            Shapes.or(

                    // Base
                    box(
                            0, 0, 0,
                            16, 2, 16
                    ),

                    // Main raised body
                    box(
                            4, 2, 3,
                            12, 3, 13
                    ),

                    // Corners
                    box(
                            13, 1.5, 13,
                            15, 2.5, 15
                    ),

                    box(
                            13, 1.5, 1,
                            15, 2.5, 3
                    ),

                    box(
                            1, 1.5, 13,
                            3, 2.5, 15
                    ),

                    box(
                            1, 1.5, 1,
                            3, 2.5, 3
                    )
            );


    /*
     * ============================================================
     * BASIS TERMINALS
     * ============================================================
     *
     * UP = Bodenmontage
     *
     * Grün:
     *   Supply +
     *   Supply -
     *
     * Rot/Blau:
     *   Voltage measurement +
     *   Voltage measurement -
     */

    private static final TerminalBoundingBox[] BASE_TERMINALS =
            new TerminalBoundingBox[] {

                    // Supply +
                    new TerminalBoundingBox(
                            IDecoratedTerminal.CONNECTOR,
                            1, 2, 1,
                            3, 3, 3
                    ).withColor(
                            IDecoratedTerminal.GREEN
                    ),

                    // Supply -
                    new TerminalBoundingBox(
                            IDecoratedTerminal.CONNECTOR,
                            13, 2, 1,
                            15, 3, 3
                    ).withColor(
                            IDecoratedTerminal.GREEN
                    ),

                    // Voltage measurement +
                    new TerminalBoundingBox(
                            IDecoratedTerminal.CONNECTOR,
                            1, 2, 13,
                            3, 3, 15
                    ).withColor(
                            IDecoratedTerminal.RED
                    ),

                    // Voltage measurement -
                    new TerminalBoundingBox(
                            IDecoratedTerminal.CONNECTOR,
                            13, 2, 13,
                            15, 3, 15
                    ).withColor(
                            IDecoratedTerminal.BLUE
                    )
            };


    /*
     * ============================================================
     * CONSTRUCTOR
     * ============================================================
     */

    public VoltageTransmitterBlock(
            BlockBehaviour.Properties settings
    ) {

        super(settings);

        registerDefaultState(
                stateDefinition.any()
                        .setValue(
                                BlockStateProperties.FACING,
                                Direction.UP
                        )
        );


        setTerminalCollection(
                BlockStateTerminalCollection.builder(this)

                        .forAllStates(state ->
                                getTerminals(
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


    /*
     * ============================================================
     * TERMINALS
     * ============================================================
     *
     * Wandmontage:
     *
     *       MEASUREMENT
     *        R       B
     *
     *
     *        SUPPLY
     *        G       G
     *
     * Die grünen Terminals liegen bei jeder Wandmontage unten.
     */

    private static TerminalBoundingBox[] getTerminals(
            Direction direction
    ) {

        return switch (direction) {

            /*
             * ====================================================
             * BODEN
             * ====================================================
             */

            case UP ->
                    BASE_TERMINALS;


            /*
             * ====================================================
             * DECKE
             * ====================================================
             */

            case DOWN ->
                    new TerminalBoundingBox[] {

                            // Supply +
                            new TerminalBoundingBox(
                                    IDecoratedTerminal.CONNECTOR,
                                    1, 13, 1,
                                    3, 14, 3
                            ).withColor(
                                    IDecoratedTerminal.GREEN
                            ),

                            // Supply -
                            new TerminalBoundingBox(
                                    IDecoratedTerminal.CONNECTOR,
                                    13, 13, 1,
                                    15, 14, 3
                            ).withColor(
                                    IDecoratedTerminal.GREEN
                            ),

                            // Voltage measurement +
                            new TerminalBoundingBox(
                                    IDecoratedTerminal.CONNECTOR,
                                    1, 13, 13,
                                    3, 14, 15
                            ).withColor(
                                    IDecoratedTerminal.RED
                            ),

                            // Voltage measurement -
                            new TerminalBoundingBox(
                                    IDecoratedTerminal.CONNECTOR,
                                    13, 13, 13,
                                    15, 14, 15
                            ).withColor(
                                    IDecoratedTerminal.BLUE
                            )
                    };


            /*
             * ====================================================
             * NORDWAND
             * ====================================================
             */

            case NORTH ->
                    new TerminalBoundingBox[] {

                            // Supply +
                            new TerminalBoundingBox(
                                    IDecoratedTerminal.CONNECTOR,
                                    1, 1, 13,
                                    3, 3, 15
                            ).withColor(
                                    IDecoratedTerminal.GREEN
                            ),

                            // Supply -
                            new TerminalBoundingBox(
                                    IDecoratedTerminal.CONNECTOR,
                                    13, 1, 13,
                                    15, 3, 15
                            ).withColor(
                                    IDecoratedTerminal.GREEN
                            ),

                            // Voltage measurement +
                            new TerminalBoundingBox(
                                    IDecoratedTerminal.CONNECTOR,
                                    1, 13, 13,
                                    3, 15, 15
                            ).withColor(
                                    IDecoratedTerminal.RED
                            ),

                            // Voltage measurement -
                            new TerminalBoundingBox(
                                    IDecoratedTerminal.CONNECTOR,
                                    13, 13, 13,
                                    15, 15, 15
                            ).withColor(
                                    IDecoratedTerminal.BLUE
                            )
                    };


            /*
             * ====================================================
             * SÜDWAND
             * ====================================================
             */

            case SOUTH ->
                    new TerminalBoundingBox[] {

                            // Supply +
                            new TerminalBoundingBox(
                                    IDecoratedTerminal.CONNECTOR,
                                    1, 1, 1,
                                    3, 3, 3
                            ).withColor(
                                    IDecoratedTerminal.GREEN
                            ),

                            // Supply -
                            new TerminalBoundingBox(
                                    IDecoratedTerminal.CONNECTOR,
                                    13, 1, 1,
                                    15, 3, 3
                            ).withColor(
                                    IDecoratedTerminal.GREEN
                            ),

                            // Voltage measurement +
                            new TerminalBoundingBox(
                                    IDecoratedTerminal.CONNECTOR,
                                    1, 13, 1,
                                    3, 15, 3
                            ).withColor(
                                    IDecoratedTerminal.RED
                            ),

                            // Voltage measurement -
                            new TerminalBoundingBox(
                                    IDecoratedTerminal.CONNECTOR,
                                    13, 13, 1,
                                    15, 15, 3
                            ).withColor(
                                    IDecoratedTerminal.BLUE
                            )
                    };


            /*
             * ====================================================
             * OSTWAND
             * ====================================================
             */

            case EAST ->
                    new TerminalBoundingBox[] {

                            // Supply +
                            new TerminalBoundingBox(
                                    IDecoratedTerminal.CONNECTOR,
                                    1, 1, 1,
                                    3, 3, 3
                            ).withColor(
                                    IDecoratedTerminal.GREEN
                            ),

                            // Supply -
                            new TerminalBoundingBox(
                                    IDecoratedTerminal.CONNECTOR,
                                    1, 1, 13,
                                    3, 3, 15
                            ).withColor(
                                    IDecoratedTerminal.GREEN
                            ),

                            // Voltage measurement +
                            new TerminalBoundingBox(
                                    IDecoratedTerminal.CONNECTOR,
                                    1, 13, 1,
                                    3, 15, 3
                            ).withColor(
                                    IDecoratedTerminal.RED
                            ),

                            // Voltage measurement -
                            new TerminalBoundingBox(
                                    IDecoratedTerminal.CONNECTOR,
                                    1, 13, 13,
                                    3, 15, 15
                            ).withColor(
                                    IDecoratedTerminal.BLUE
                            )
                    };


            /*
             * ====================================================
             * WESTWAND
             * ====================================================
             */

            case WEST ->
                    new TerminalBoundingBox[] {

                            // Supply +
                            new TerminalBoundingBox(
                                    IDecoratedTerminal.CONNECTOR,
                                    13, 1, 13,
                                    15, 3, 15
                            ).withColor(
                                    IDecoratedTerminal.GREEN
                            ),

                            // Supply -
                            new TerminalBoundingBox(
                                    IDecoratedTerminal.CONNECTOR,
                                    13, 1, 1,
                                    15, 3, 3
                            ).withColor(
                                    IDecoratedTerminal.GREEN
                            ),

                            // Voltage measurement +
                            new TerminalBoundingBox(
                                    IDecoratedTerminal.CONNECTOR,
                                    13, 13, 13,
                                    15, 15, 15
                            ).withColor(
                                    IDecoratedTerminal.RED
                            ),

                            // Voltage measurement -
                            new TerminalBoundingBox(
                                    IDecoratedTerminal.CONNECTOR,
                                    13, 13, 1,
                                    15, 15, 3
                            ).withColor(
                                    IDecoratedTerminal.BLUE
                            )
                    };
        };
    }


    /*
     * ============================================================
     * VOXEL SHAPE
     * ============================================================
     */

    private static VoxelShape rotateShape(
            Direction direction
    ) {

        return switch (direction) {

            /*
             * ====================================================
             * BODEN
             * ====================================================
             */

            case UP ->
                    BASE_SHAPE;


            /*
             * ====================================================
             * DECKE
             * ====================================================
             */

            case DOWN ->
                    Shapes.or(

                            // Base
                            box(
                                    0, 14, 0,
                                    16, 16, 16
                            ),

                            // Main body
                            box(
                                    4, 13, 3,
                                    12, 14, 13
                            ),

                            // Corners
                            box(
                                    13, 13.5, 13,
                                    15, 14.5, 15
                            ),

                            box(
                                    13, 13.5, 1,
                                    15, 14.5, 3
                            ),

                            box(
                                    1, 13.5, 13,
                                    3, 14.5, 15
                            ),

                            box(
                                    1, 13.5, 1,
                                    3, 14.5, 3
                            )
                    );


            /*
             * ====================================================
             * NORDWAND
             * ====================================================
             */

            case NORTH ->
                    Shapes.or(

                            // Base
                            box(
                                    0, 0, 14,
                                    16, 16, 16
                            ),

                            // Main body
                            box(
                                    4, 3, 13,
                                    12, 13, 14
                            ),

                            // Corners
                            box(
                                    13, 13, 13.5,
                                    15, 15, 14.5
                            ),

                            box(
                                    13, 1, 13.5,
                                    15, 3, 14.5
                            ),

                            box(
                                    1, 13, 13.5,
                                    3, 15, 14.5
                            ),

                            box(
                                    1, 1, 13.5,
                                    3, 3, 14.5
                            )
                    );


            /*
             * ====================================================
             * SÜDWAND
             * ==================================================== */

            case SOUTH ->
                    Shapes.or(

                            // Base
                            box(
                                    0, 0, 0,
                                    16, 16, 2
                            ),

                            // Main body
                            box(
                                    4, 3, 2,
                                    12, 13, 3
                            ),

                            // Corners
                            box(
                                    13, 13, 1.5,
                                    15, 15, 2.5
                            ),

                            box(
                                    13, 1, 1.5,
                                    15, 3, 2.5
                            ),

                            box(
                                    1, 13, 1.5,
                                    3, 15, 2.5
                            ),

                            box(
                                    1, 1, 1.5,
                                    3, 3, 2.5
                            )
                    );


            /*
             * ====================================================
             * OSTWAND
             * ====================================================
             */

            case EAST ->
                    Shapes.or(

                            // Base
                            box(
                                    0, 0, 0,
                                    2, 16, 16
                            ),

                            // Main body
                            box(
                                    2, 3, 4,
                                    3, 13, 12
                            ),

                            // Corners
                            box(
                                    1.5, 13, 13,
                                    2.5, 15, 15
                            ),

                            box(
                                    1.5, 13, 1,
                                    2.5, 15, 3
                            ),

                            box(
                                    1.5, 1, 13,
                                    2.5, 3, 15
                            ),

                            box(
                                    1.5, 1, 1,
                                    2.5, 3, 3
                            )
                    );


            /*
             * ====================================================
             * WESTWAND
             * ====================================================
             */

            case WEST ->
                    Shapes.or(

                            // Base
                            box(
                                    14, 0, 0,
                                    16, 16, 16
                            ),

                            // Main body
                            box(
                                    13, 3, 4,
                                    14, 13, 12
                            ),

                            // Corners
                            box(
                                    13.5, 13, 13,
                                    14.5, 15, 15
                            ),

                            box(
                                    13.5, 13, 1,
                                    14.5, 15, 3
                            ),

                            box(
                                    13.5, 1, 13,
                                    14.5, 3, 15
                            ),

                            box(
                                    13.5, 1, 1,
                                    14.5, 3, 3
                            )
                    );
        };
    }


    /*
     * ============================================================
     * BLOCKSTATE
     * ============================================================
     */

    @Override
    protected void createBlockStateDefinition(
            StateDefinition.Builder<Block, BlockState> builder
    ) {

        builder.add(
                BlockStateProperties.FACING
        );
    }


    /*
     * ============================================================
     * PLACEMENT
     * ============================================================
     */

    @Override
    public BlockState getStateForPlacement(
            BlockPlaceContext context
    ) {

        return defaultBlockState()
                .setValue(
                        BlockStateProperties.FACING,
                        context.getClickedFace()
                );
    }


    /*
     * ============================================================
     * BLOCK ENTITY
     * ============================================================
     */

    @Override
    public Class<VoltageTransmitterBlockEntity>
    getBlockEntityClass() {

        return VoltageTransmitterBlockEntity.class;
    }


    @Override
    public BlockEntityType<? extends VoltageTransmitterBlockEntity>
    getBlockEntityType() {

        return AllBlockEntityTypes.VOLTAGE_TRANSMITTER.get();
    }
}