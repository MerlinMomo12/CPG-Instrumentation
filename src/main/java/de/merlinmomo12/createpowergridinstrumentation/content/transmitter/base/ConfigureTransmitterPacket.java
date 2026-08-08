package de.merlinmomo12.createpowergridinstrumentation.content.transmitter.base;

import com.simibubi.create.foundation.networking.BlockEntityConfigurationPacket;
import de.merlinmomo12.createpowergridinstrumentation.registry.AllPackets;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.BlockPos;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.server.level.ServerPlayer;

public class ConfigureTransmitterPacket
        extends BlockEntityConfigurationPacket<AbstractTransmitterBlockEntity> {

    public static final StreamCodec<ByteBuf, ConfigureTransmitterPacket> STREAM_CODEC =
            StreamCodec.composite(
                    BlockPos.STREAM_CODEC,
                    packet -> packet.pos,

                    ByteBufCodecs.VAR_INT,
                    packet -> packet.outputUnit.ordinal(),

                    ByteBufCodecs.DOUBLE,
                    packet -> packet.lowerRange,

                    ByteBufCodecs.DOUBLE,
                    packet -> packet.upperRange,

                    ConfigureTransmitterPacket::new
            );

    private final TransmitterUnit outputUnit;
    private final double lowerRange;
    private final double upperRange;

    public ConfigureTransmitterPacket(
            BlockPos pos,
            TransmitterUnit outputUnit,
            double lowerRange,
            double upperRange
    ) {
        super(pos);

        this.outputUnit = outputUnit;
        this.lowerRange = lowerRange;
        this.upperRange = upperRange;
    }

    private ConfigureTransmitterPacket(
            BlockPos pos,
            int outputUnit,
            double lowerRange,
            double upperRange
    ) {
        super(pos);

        if (outputUnit < 0 ||
                outputUnit >= TransmitterUnit.values().length) {
            this.outputUnit = null;
        } else {
            this.outputUnit =
                    TransmitterUnit.values()[outputUnit];
        }

        this.lowerRange = lowerRange;
        this.upperRange = upperRange;
    }

    @Override
    protected void applySettings(
            ServerPlayer player,
            AbstractTransmitterBlockEntity be
    ) {

        if (outputUnit == null)
            return;

        // Unit muss zum Transmitter-Typ gehören
        if (outputUnit.getType() != be.getTransmitterType())
            return;

        // Keine ungültigen Zahlen akzeptieren
        if (!Double.isFinite(lowerRange) ||
                !Double.isFinite(upperRange))
            return;

        // Lower muss kleiner als Upper sein
        if (lowerRange >= upperRange)
            return;

        // Werte übernehmen
        be.setOutputUnit(outputUnit);
        be.setLowerRange(lowerRange);
        be.setUpperRange(upperRange);
    }

    @Override
    public PacketTypeProvider getTypeProvider() {
        return AllPackets.CONFIGURE_TRANSMITTER;
    }
}