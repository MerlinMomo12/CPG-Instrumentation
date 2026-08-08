package de.merlinmomo12.createpowergridinstrumentation.content.transmitter.base;

import com.simibubi.create.foundation.gui.widget.ScrollInput;
import com.simibubi.create.foundation.gui.widget.SelectionScrollInput;
import net.createmod.catnip.gui.AbstractSimiScreen;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;



import java.util.List;

public class TransmitterScreen extends AbstractSimiScreen {

    private static final int WIDTH = 176;
    private static final int HEIGHT = 166;

    private final AbstractTransmitterBlockEntity blockEntity;

    // Transmitter-Daten
    private TransmitterType transmitterType;

    // Range
    private double lowerRange;
    private double upperRange;

    // Units
    private List<TransmitterUnit> availableUnits;
    private TransmitterUnit outputUnit;
    // Range
    private ScrollInput lowerRangeInput;
    private ScrollInput upperRangeInput;

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

        // Werte aus der BlockEntity laden
        transmitterType = blockEntity.getTransmitterType();
        availableUnits = transmitterType.getUnits();

        outputUnit = blockEntity.getOutputUnit();

        lowerRange = blockEntity.getLowerRange();
        upperRange = blockEntity.getUpperRange();

        System.out.println("ENTITY OUTPUT UNIT: " + blockEntity.getOutputUnit());
        System.out.println("SCREEN OUTPUT UNIT: " + outputUnit);
        System.out.println("AVAILABLE UNITS: " + availableUnits);


// Mögliche Units für das GUI
        List<Component> unitNames = availableUnits.stream()
                .map(unit -> (Component) Component.literal(unit.getSymbol()))
                .toList();

// Index der aktuell gespeicherten Output-Unit
        int currentUnitIndex = availableUnits.indexOf(outputUnit);
        System.out.println("currentUnitIndex: " + currentUnitIndex);


// Output-Unit Auswahl
        outputUnitInput = (SelectionScrollInput) new SelectionScrollInput(
                guiLeft + 100,
                guiTop + 23,
                52,
                42
        )
                .forOptions(unitNames)
                .titled(Component.literal("Output Unit"))
                .setState(currentUnitIndex);

        addRenderableWidget(outputUnitInput);


        lowerRangeInput = new ScrollInput(
                guiLeft + 10,
                guiTop + 60,
                70,
                18
        )
                .withRange(-10000, 10000)
                .withStepFunction(input -> input.shift ? 10 : 1)
                .titled(Component.literal("Lower Range"))
                .setState((int) lowerRange);

        addRenderableWidget(lowerRangeInput);



        upperRangeInput = new ScrollInput(
                guiLeft + 95,
                guiTop + 60,
                70,
                18
        )
                .withRange(-10000, 10000)
                .withStepFunction(input -> input.shift ? 10 : 1)
                .titled(Component.literal("Upper Range"))
                .setState((int) upperRange);

        addRenderableWidget(upperRangeInput);
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

        // =========================
        // Hintergrund
        // =========================

        graphics.fill(
                x,
                y,
                x + WIDTH,
                y + HEIGHT,
                0xFF222222
        );


        // =========================
        // Titel
        // =========================

        Component title = transmitterType.getName();

        graphics.drawString(
                font,
                title,
                x + WIDTH / 2 - font.width(title) / 2,
                y + 8,
                0xFFFFFF
        );


        // =========================
        // Output Unit
        // =========================

        graphics.drawString(
                font,
                "Output Unit",
                x + 10,
                y + 29,
                0xFFFFFF
        );

        if (outputUnitInput != null && !availableUnits.isEmpty()) {

            int selectedIndex = outputUnitInput.getState();

            if (selectedIndex >= 0 &&
                    selectedIndex < availableUnits.size()) {

                TransmitterUnit selectedUnit =
                        availableUnits.get(selectedIndex);

                graphics.drawString(
                        font,
                        selectedUnit.getSymbol(),
                        x + 105,
                        y + 29,
                        0xFFFFFF
                );
            }
        }


        // =========================
        // Range
        // =========================

        graphics.drawString(
                font,
                "Lower Range",
                x + 10,
                y + 53,
                0xFFFFFF
        );

        graphics.drawString(
                font,
                "Upper Range",
                x + 95,
                y + 53,
                0xFFFFFF
        );


        // =========================
        // Aktuelle Range-Werte
        // =========================

        if (lowerRangeInput != null) {

            graphics.drawString(
                    font,
                    String.valueOf(lowerRangeInput.getState()),
                    x + 15,
                    y + 70,
                    0xFFFFFF
            );
        }

        if (upperRangeInput != null) {

            graphics.drawString(
                    font,
                    String.valueOf(upperRangeInput.getState()),
                    x + 100,
                    y + 70,
                    0xFFFFFF
            );
        }


        // =========================
        // Einheit der Range
        // =========================

        if (outputUnitInput != null && !availableUnits.isEmpty()) {

            int selectedIndex = outputUnitInput.getState();

            if (selectedIndex >= 0 &&
                    selectedIndex < availableUnits.size()) {

                TransmitterUnit selectedUnit =
                        availableUnits.get(selectedIndex);

                String symbol = selectedUnit.getSymbol();

                graphics.drawString(
                        font,
                        symbol,
                        x + 65,
                        y + 70,
                        0xFFAAAAAA
                );
            }
        }
    }

}

