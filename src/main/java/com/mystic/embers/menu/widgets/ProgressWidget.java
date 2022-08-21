package com.mystic.embers.menu.widgets;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import java.util.function.Supplier;

public class ProgressWidget extends AbstractWidget {
	public enum Direction {
		BOTTOM_UP,
		TOP_DOWN,
		LEFT_RIGHT
	}

	private Supplier<Float> getter;

	private final Screen screen;
	private final int u;
	private final int v;
	private final Direction direction;
	private final ResourceLocation textureLocation;

	public ProgressWidget(Screen screen, Supplier<Float> getter, int x, int y, int width, int height, int u, int v, Direction direction, ResourceLocation textureLocation) {
		super(x, y, width, height, Component.empty());
		this.screen = screen;
		this.getter = getter;
		this.u = u;
		this.v = v;
		this.direction = direction;
		this.visible = true;
		this.textureLocation = textureLocation;
	}

	// Stop the click sound
	@Override
	public boolean mouseClicked(double pMouseX, double pMouseY, int pButton) {
		return false;
	}

	@Override
	public void updateNarration(NarrationElementOutput narrationElementOutput) {
	}

	@Override
	public void renderButton(PoseStack poseStack, int mouseX, int mouseY, float partialTick) {
		float progress = getter.get();

		int yOffset = 0;
		int xOffset = 0;
		int width = this.width;
		int height = this.height;

		switch (direction) {
			case BOTTOM_UP -> {
				yOffset = (int) (this.height * (1.0f - progress));
				height = (int) (this.height * progress);
			}
			case TOP_DOWN -> {
				height = (int) (this.height * progress);
			}
			case LEFT_RIGHT -> {
				width = (int) (this.width * progress);
			}
		}
		RenderSystem.setShaderTexture(0, this.textureLocation);
		poseStack.pushPose();
		blit(poseStack, x + xOffset, y + yOffset, u, v + yOffset, width, height);
		poseStack.popPose();

		if (this.isHoveredOrFocused()) {
			this.renderToolTip(poseStack, mouseX, mouseY);
		}
	}

}
