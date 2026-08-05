package de.merlinmomo12.createpowergridinstrumentation.content.transmitter.base;

import com.mojang.blaze3d.systems.RenderSystem;
import de.merlinmomo12.createpowergridinstrumentation.CreatePowergridInstrumentation;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class TransmitterScreen extends AbstractContainerScreen<TransmitterMenu> {

    private static final ResourceLocation GUI =
            ResourceLocation.fromNamespaceAndPath(
                    CreatePowergridInstrumentation.MODID,
                    "textures/gui/transmitter.png"
            );

    public TransmitterScreen(TransmitterMenu menu, Inventory inventory, Component title) {
        super(menu, inventory, title);

        imageWidth = 176;
        imageHeight = 166;

        inventoryLabelX = 8;
        inventoryLabelY = imageHeight - 94;

        titleLabelX = 4;
        titleLabelY = 3;
    }

    @Override
    protected void renderBg(GuiGraphics graphics, float partialTick, int mouseX, int mouseY) {
        int x = leftPos;
        int y = topPos;

        RenderSystem.setShaderTexture(0, GUI);
        graphics.blit(GUI, x, y, 0, 0, imageWidth, imageHeight);

        // ===== TYPE =====
        drawCenteredString(graphics, "Temperature", 4, 3, 167, 11, 0x404040);

        // ===== LOWER RANGE =====
        drawCenteredString(graphics, "0.00", 105, 28, 123, 41, 0x404040);

        // ===== UPPER RANGE =====
        drawCenteredString(graphics, "100.00", 105, 50, 123, 63, 0x404040);

        // ===== LOWER UNIT =====
        drawCenteredString(graphics, "°C", 141, 28, 154, 41, 0x404040);

        // ===== UPPER UNIT =====
        drawCenteredString(graphics, "°C", 141, 50, 154, 63, 0x404040);

        // ===== STATUS =====
        drawCenteredString(graphics, "OK", 4, 80, 139, 103, 0x00AA00);
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        renderBackground(graphics, mouseX, mouseY, partialTick);
        super.render(graphics, mouseX, mouseY, partialTick);
        renderTooltip(graphics, mouseX, mouseY);
    }

    /**
     * Zeichnet einen String horizontal und vertikal zentriert innerhalb
     * der angegebenen Box.
     */
    private void drawCenteredString(
            GuiGraphics graphics,
            String text,
            int x1,
            int y1,
            int x2,
            int y2,
            int color) {

        int boxWidth = x2 - x1;
        int boxHeight = y2 - y1;

        int textWidth = font.width(text);

        int drawX = leftPos + x1 + (boxWidth - textWidth) / 2;
        int drawY = topPos + y1 + (boxHeight - font.lineHeight) / 2;

        graphics.drawString(font, text, drawX, drawY, color, false);
    }
}