package com.mystic.embers.menu;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mystic.embers.Embers;
import com.mystic.embers.menu.widgets.FluidStackWidget;
import com.mystic.embers.menu.widgets.ProgressWidget;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class CaminiteForgeScreen extends AbstractContainerScreen<CaminiteForgeMenu> {

    private static final ResourceLocation TEXTURE = new ResourceLocation(Embers.MODID, "textures/gui/caminite_forge.png");

    public CaminiteForgeScreen(CaminiteForgeMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
        this.leftPos = 0;
        this.topPos = 0;
        this.imageWidth = 176;
        this.imageHeight = 166;

    }

    @Override
    protected void init() {
        super.init();
        addRenderableOnly(new FluidStackWidget(this, () -> menu.getEntity().getOutputTank(), getGuiLeft() + 27 , getGuiTop() + 22, 9, 42));
        addRenderableOnly(new ProgressWidget(this, () -> menu.getEntity().getProgress(), getGuiLeft() + 79, getGuiTop() + 31, 15, 15, 176, 0, ProgressWidget.Direction.BOTTOM_UP, TEXTURE));
    }

    @Override
    protected void renderBg(PoseStack pPoseStack, float pPartialTick, int pMouseX, int pMouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.setShaderTexture(0, TEXTURE);
        blit(pPoseStack, this.leftPos, this.topPos, 0, 0,this.imageWidth, this.imageHeight);
    }

    @Override
    protected void renderLabels(PoseStack pPoseStack, int pMouseX, int pMouseY) {
        //super.renderLabels(pPoseStack, pMouseX, pMouseY);
    }



}
