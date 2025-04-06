package mysticmods.embers.machines.caminite_forge;

import mysticmods.embers.Embers;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class CaminiteForgeScreen extends AbstractContainerScreen<CaminiteForgeMenu> {
    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(Embers.MODID, "textures/gui/caminite_forge.png");

    public CaminiteForgeScreen(CaminiteForgeMenu menu, Inventory inventory, Component title) {
        super(menu, inventory, title);
        this.imageWidth = 176;
        this.imageHeight = 173;
    }

    @Override
    protected void init() {
        super.init();
        this.titleLabelY = 4;
        this.inventoryLabelY = this.imageHeight - 92;
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(guiGraphics, mouseX, mouseY, partialTick);
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        this.renderTooltip(guiGraphics, mouseX, mouseY);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        // Draw the background texture
        int x = (this.width - this.imageWidth) / 2;
        int y = (this.height - this.imageHeight) / 2;
        guiGraphics.blit(TEXTURE, x, y, 0, 0, this.imageWidth, this.imageHeight);

        // Draw the progress indicator if there's an item being processed
        CaminiteForgeBlockEntity blockEntity = this.menu.getBlockEntity();
        if (blockEntity.getProgress() > 0) {
            float progress = blockEntity.getProgress() * 16; // 24 is the width of the progress arrow
            guiGraphics.blit(TEXTURE, x + 79, y + 40, 176, 0, Math.round(progress), 16);
        }
    }
}
