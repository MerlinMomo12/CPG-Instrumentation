package de.merlinmomo12.createpowergridinstrumentation.content.transmitter.base;

import de.merlinmomo12.createpowergridinstrumentation.content.transmitter.base.AbstractTransmitterBlockEntity;
import net.createmod.catnip.gui.AbstractSimiScreen;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class TransmitterScreen extends AbstractSimiScreen {

    private static final int WIDTH = 176;
    private static final int HEIGHT = 166;

    private static final ResourceLocation BACKGROUND =
            ResourceLocation.fromNamespaceAndPath(
                    "createpowergridinstrumentation",
                    "textures/gui/transmitter.png"
            );

    private final AbstractTransmitterBlockEntity blockEntity;

    public TransmitterScreen(AbstractTransmitterBlockEntity be) {
        super(Component.literal("Transmitter"));
        this.blockEntity = be;
    }

    @Override
    protected void init() {
        setWindowSize(WIDTH, HEIGHT);
        super.init();
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

        graphics.blit(
                BACKGROUND,
                x,
                y,
                0,
                0,
                WIDTH,
                HEIGHT,
                WIDTH,
                HEIGHT
        );
    }
}