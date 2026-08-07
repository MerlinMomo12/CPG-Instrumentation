package de.merlinmomo12.createpowergridinstrumentation.content.transmitter.base;

import com.simibubi.create.foundation.gui.widget.SelectionScrollInput;
import net.createmod.catnip.gui.AbstractSimiScreen;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;

import de.merlinmomo12.createpowergridinstrumentation.content.transmitter.base.AbstractTransmitterBlockEntity;
import de.merlinmomo12.createpowergridinstrumentation.content.transmitter.base.TransmitterType;
import de.merlinmomo12.createpowergridinstrumentation.content.transmitter.base.TransmitterUnit;

import java.util.List;

public class TransmitterScreen extends AbstractSimiScreen {

    private static final int WIDTH = 176;
    private static final int HEIGHT = 166;

    private final AbstractTransmitterBlockEntity blockEntity;

    // Transmitter-Daten
    private TransmitterType transmitterType;

    // Units
    private List<TransmitterUnit> availableUnits;
    private TransmitterUnit outputUnit;
    private TransmitterUnit measurementUnit;

    // Messwerte
    private double measurement;
    private double convertedMeasurement;

    // Range
    private double lowerRange;
    private double upperRange;

    // GUI
    private SelectionScrollInput outputUnitInput;


    public TransmitterScreen(AbstractTransmitterBlockEntity blockEntity) {
        super(Component.literal("Transmitter"));
        this.blockEntity = blockEntity;
    }


    @Override
    protected void init() {
        setWindowSize(WIDTH, HEIGHT);
        super.init();
        transmitterType = blockEntity.getTransmitterType();
        availableUnits = transmitterType.getUnits();
        outputUnit = blockEntity.getOutputUnit();

        measurement = blockEntity.getMeasurement();

        lowerRange = blockEntity.getLowerRange();
        upperRange = blockEntity.getUpperRange();
        System.out.println(
                "Lower: " + lowerRange +
                        " Upper: " + upperRange +
                        " Output: " + outputUnit +
                        " Type: " + transmitterType +
                        " Units: " + availableUnits
        );
    }


    @Override
    protected void renderWindow(
            GuiGraphics graphics,
            int mouseX,
            int mouseY,
            float partialTicks
    ) {

        int x = guiLeft;
        int y = guiTop;

        graphics.fill(
                x,
                y,
                x + WIDTH,
                y + HEIGHT,
                0xFF222222
        );

        graphics.drawString(
                font,
                title,
                x + 10,
                y + 10,
                0xFFFFFF
        );
    }
}

