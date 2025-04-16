package mysticmods.embers.machines.caminite_smelter.menu;

import mysticmods.embers.Embers;
import mysticmods.embers.machines.caminite_forge.CaminiteForgeBlockEntity;
import mysticmods.embers.machines.caminite_smelter.CaminiteSmelterBlockEntity;
import mysticmods.embers.network.CaminiteForgeToggleAlloyData;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.neoforged.neoforge.network.PacketDistributor;
import org.jetbrains.annotations.NotNull;

public class CaminiteSmelterScreen extends AbstractContainerScreen<CaminiteSmelterMenu> {
    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(Embers.MODID, "textures/gui/caminite_smelter.png");

    public CaminiteSmelterScreen(CaminiteSmelterMenu menu, Inventory inventory, Component title) {
        super(menu, inventory, title);
        this.imageWidth = 176;
        this.imageHeight = 173;
    }

    @Override
    protected void init() {
        super.init();
        this.titleLabelY = 4;
        this.inventoryLabelY = this.imageHeight - 92;

        // Add alloy mode button
        int x = (this.width - this.imageWidth) / 2;
        int y = (this.height - this.imageHeight) / 2;
    }

    @Override
    public void render(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(guiGraphics, mouseX, mouseY, partialTick);
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        this.renderTooltip(guiGraphics, mouseX, mouseY);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        int x = (this.width - this.imageWidth) / 2;
        int y = (this.height - this.imageHeight) / 2;
        guiGraphics.blit(TEXTURE, x, y, 0, 0, this.imageWidth, this.imageHeight);

        CaminiteSmelterBlockEntity blockEntity = this.menu.getBlockEntity();
        if (blockEntity.getProgress() > 0) {
            float progress = blockEntity.getProgress() * 16;
            guiGraphics.blit(TEXTURE, x + 79, y + 40, 176, 0, Math.round(progress), 16);
        }

        int emberLevel = blockEntity.getEmberIntensity().getIntensity();
        if (emberLevel> 0) {
            float progress = (float) emberLevel / blockEntity.getEmberIntensity().getMinIntensity() * 54;
            guiGraphics.blit(TEXTURE, x + 60, y + 64, 176, 16, Math.round(progress), 9);
        }
    }
}
