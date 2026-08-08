
        package de.merlinmomo12.createpowergridinstrumentation.content.transmitter.base;

import com.simibubi.create.foundation.gui.AllIcons;
import com.simibubi.create.foundation.gui.widget.IconButton;
import com.simibubi.create.foundation.gui.widget.ScrollInput;
import com.simibubi.create.foundation.gui.widget.SelectionScrollInput;

import de.merlinmomo12.createpowergridinstrumentation.CreatePowergridInstrumentation;
import net.createmod.catnip.gui.AbstractSimiScreen;
import net.createmod.catnip.gui.element.GuiGameElement;

import net.createmod.catnip.platform.CatnipServices;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class TransmitterScreen extends AbstractSimiScreen {

    private static final int WIDTH = 181;
    private static final int HEIGHT = 166;
    private static final ResourceLocation TEXTURE =
            CreatePowergridInstrumentation.asResource(
                    "textures/gui/transmitter.png"
            );

    private final AbstractTransmitterBlockEntity blockEntity;

    // =========================
    // Transmitter-Daten
    // =========================

    private TransmitterType transmitterType;

    // =========================
    // Range
    // =========================

    private double lowerRange;
    private double upperRange;

    // =========================
    // Units
    // =========================

    private List<TransmitterUnit> availableUnits;
    private TransmitterUnit outputUnit;

    // =========================
    // GUI Inputs
    // =========================

    private ScrollInput lowerRangeInput;
    private ScrollInput upperRangeInput;
    private SelectionScrollInput outputUnitInput;

    private IconButton confirmButton;

    // =========================
    // Darstellung
    // =========================

    private ItemStack renderedItem;


    public TransmitterScreen(AbstractTransmitterBlockEntity blockEntity) {
        super(Component.literal("Transmitter"));
        this.blockEntity = blockEntity;
    }


    @Override
    protected void init() {
        setWindowSize(WIDTH, HEIGHT);
        super.init();

        // =========================
        // Werte aus der BlockEntity
        // =========================

        transmitterType = blockEntity.getTransmitterType();

        availableUnits = transmitterType.getUnits();

        outputUnit = blockEntity.getOutputUnit();

        lowerRange = blockEntity.getLowerRange();
        upperRange = blockEntity.getUpperRange();


        // =========================
        // Transmitter-Item
        // =========================
        // Verwendet automatisch den tatsächlich
        // platzierten Transmitter-Block.

        renderedItem = new ItemStack(
                blockEntity.getBlockState().getBlock()
        );


        // =========================
        // Output Unit
        // =========================

        List<Component> unitNames = availableUnits.stream()
                .map(unit -> Component.translationArg(Component.literal(unit.getSymbol())))
                .toList();

        int currentUnitIndex =
                availableUnits.indexOf(outputUnit);

        // Falls aus irgendeinem Grund keine passende
        // Unit gefunden wurde
        if (currentUnitIndex < 0) {
            currentUnitIndex = 0;
        }

        outputUnitInput = (SelectionScrollInput) new SelectionScrollInput(
                guiLeft + 117,
                guiTop + 23,
                42,
                17
        )
                .forOptions(unitNames)
                .titled(Component.literal("Output Unit"))
                .setState(currentUnitIndex);

        addRenderableWidget(outputUnitInput);


        // =========================
        // Lower Range
        // =========================

        lowerRangeInput = new ScrollInput(
                guiLeft + 57,
                guiTop + 23,
                47,
                17
        )
                .withRange(-10000, 10000)
                .withStepFunction(input ->
                        input.shift ? 10 : 1
                )
                .titled(Component.literal("Lower Range"))
                .setState((int) lowerRange);

        addRenderableWidget(lowerRangeInput);


        // =========================
        // Upper Range
        // =========================

        upperRangeInput = new ScrollInput(
                guiLeft + 57,
                guiTop + 47,
                47,
                17
        )
                .withRange(-10000, 10000)
                .withStepFunction(input ->
                        input.shift ? 10 : 1
                )
                .titled(Component.literal("Upper Range"))
                .setState((int) upperRange);

        addRenderableWidget(upperRangeInput);


        // =========================
// Confirm Button
// =========================

        confirmButton = new IconButton(
                guiLeft + 149,
                guiTop + 79,
                17,
                17,
                AllIcons.I_CONFIRM
        );

        confirmButton.withCallback(() -> {

            // Aktuell ausgewählte Unit
            int selectedUnitIndex = outputUnitInput.getState();

            if (selectedUnitIndex < 0 ||
                    selectedUnitIndex >= availableUnits.size()) {
                return;
            }

            TransmitterUnit selectedUnit =
                    availableUnits.get(selectedUnitIndex);

            // Aktuelle Range-Werte
            double selectedLowerRange =
                    lowerRangeInput.getState();

            double selectedUpperRange =
                    upperRangeInput.getState();

            // Packet erstellen
            ConfigureTransmitterPacket packet =
                    new ConfigureTransmitterPacket(
                            blockEntity.getBlockPos(),
                            selectedUnit,
                            selectedLowerRange,
                            selectedUpperRange
                    );

            // Zum Server schicken
            CatnipServices.NETWORK.sendToServer(packet);

            // Screen schließen
            onClose();
        });

        addRenderableWidget(confirmButton);
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
        // GUI Hintergrund
        // =========================

        graphics.blit(
                TEXTURE,
                x ,
                y ,
                0,
                0,
                WIDTH,
                HEIGHT,
                256,
                256
        );


        // =========================
        // Titel
        // =========================

        Component title =
                transmitterType.getName();

        graphics.drawString(
                font,
                title,
                x + WIDTH / 2 - font.width(title) / 2,
                y + 4,
                0xFFFFFF
        );


        // =========================
        // Output Unit
        // =========================



        if (outputUnitInput != null &&
                !availableUnits.isEmpty()) {

            int selectedIndex =
                    outputUnitInput.getState();

            if (selectedIndex >= 0 &&
                    selectedIndex < availableUnits.size()) {

                TransmitterUnit selectedUnit =
                        availableUnits.get(selectedIndex);

                graphics.drawString(
                        font,
                        selectedUnit.getSymbol(),
                        x + 130,
                        y + 28,
                        0xFFFFFF
                );
            }
        }




        // =========================
        // Range Werte
        // =========================

        if (lowerRangeInput != null) {

            graphics.drawString(
                    font,
                    String.valueOf(lowerRangeInput.getState()),
                    x + 63,
                    y + 52,
                    0xFFFFFF
            );
        }

        if (upperRangeInput != null) {

            graphics.drawString(
                    font,
                    String.valueOf(upperRangeInput.getState()),
                    x + 63,
                    y + 28,
                    0xFFFFFF
            );
        }





        // =========================
        // Transmitter Item
        // =========================

        if (renderedItem != null) {

            GuiGameElement.of(renderedItem)
                    .<GuiGameElement.GuiRenderBuilder>at(
                            x + WIDTH + 6,
                            y + HEIGHT - 56,
                            -200
                    )
                    .scale(5)
                    .render(graphics);
        }
    }
}

