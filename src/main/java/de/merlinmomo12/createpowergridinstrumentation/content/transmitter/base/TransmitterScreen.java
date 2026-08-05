package de.merlinmomo12.createpowergridinstrumentation.content.transmitter.base;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class TransmitterScreen extends AbstractContainerScreen<TransmitterMenu> {

    public TransmitterScreen(
            TransmitterMenu menu,
            Inventory inventory,
            Component title
    ) {
        super(menu, inventory, title);

        this.imageWidth = 220;
        this.imageHeight = 180;
    }


    @Override
    protected void renderBg(
            GuiGraphics graphics,
            float partialTick,
            int mouseX,
            int mouseY
    ) {
        int left = this.leftPos;
        int top = this.topPos;


        // Hintergrund
        graphics.fill(
                left,
                top,
                left + imageWidth,
                top + imageHeight,
                0xFF202020
        );


        // Kopfbereich
        graphics.fill(
                left,
                top,
                left + imageWidth,
                top + 25,
                0xFF303030
        );


        // Trennlinien
        graphics.fill(
                left + 10,
                top + 55,
                left + imageWidth - 10,
                top + 56,
                0xFF606060
        );

        graphics.fill(
                left + 10,
                top + 110,
                left + imageWidth - 10,
                top + 111,
                0xFF606060
        );


        // Statusfeld
        graphics.fill(
                left + 15,
                top + 65,
                left + 205,
                top + 100,
                0xFF151515
        );


        // Button Simulation
        graphics.fill(
                left + 70,
                top + 135,
                left + 150,
                top + 160,
                0xFF404040
        );


        // Button Hover Effekt
        if(mouseX >= left + 70 &&
                mouseX <= left + 150 &&
                mouseY >= top + 135 &&
                mouseY <= top + 160) {

            graphics.fill(
                    left + 70,
                    top + 135,
                    left + 150,
                    top + 160,
                    0xFF606060
            );
        }
    }


    @Override
    protected void renderLabels(
            GuiGraphics graphics,
            int mouseX,
            int mouseY
    ) {

        // Titel
        graphics.drawString(
                this.font,
                "Temperature Transmitter",
                10,
                8,
                0xFFFFFF
        );


        // Werte
        graphics.drawString(
                this.font,
                "Input:",
                15,
                35,
                0xAAAAAA
        );

        graphics.drawString(
                this.font,
                "75.0 °C",
                80,
                35,
                0xFFFFFF
        );


        graphics.drawString(
                this.font,
                "Output:",
                15,
                75,
                0xAAAAAA
        );

        graphics.drawString(
                this.font,
                "12.4 mA",
                80,
                75,
                0x55FF55
        );


        graphics.drawString(
                this.font,
                "HART Range:",
                15,
                120,
                0xAAAAAA
        );


        graphics.drawString(
                this.font,
                "0 - 100 °C",
                100,
                120,
                0xFFFFFF
        );


        graphics.drawCenteredString(
                this.font,
                "Configure",
                110,
                143,
                0xFFFFFF
        );
    }


    @Override
    public void render(
            GuiGraphics graphics,
            int mouseX,
            int mouseY,
            float partialTick
    ) {
        super.render(graphics, mouseX, mouseY, partialTick);

        // Tooltips etc.
        this.renderTooltip(
                graphics,
                mouseX,
                mouseY
        );
    }
}