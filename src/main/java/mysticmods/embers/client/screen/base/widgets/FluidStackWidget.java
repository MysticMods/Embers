package mysticmods.embers.client.screen.base.widgets;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FastColor;
import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.templates.FluidTank;

import java.util.Arrays;
import java.util.function.Supplier;

public class FluidStackWidget extends AbstractWidget {

	private final Screen displayOn;
	private final Supplier<FluidTank> getFluid;

	public FluidStackWidget(Screen displayOn, Supplier<FluidTank> getFluid, int pX, int pY, int pWidth, int pHeight) {
		super(pX, pY, pWidth, pHeight, Component.empty());
		this.displayOn = displayOn;
		this.getFluid = getFluid;
	}

	@Override
	public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTick) {
		Minecraft minecraft = Minecraft.getInstance();
		RenderSystem.setShader(GameRenderer::getPositionTexShader);
		RenderSystem.defaultBlendFunc();
		RenderSystem.enableDepthTest();
		FluidTank fluidTank = getFluid.get();
		if (!fluidTank.isEmpty()) {
			FluidStack fluidStack = fluidTank.getFluid();
			ResourceLocation still = IClientFluidTypeExtensions.of(fluidStack.getFluid()).getStillTexture(fluidStack);
			if (still != null) {
				AbstractTexture texture = minecraft.getTextureManager().getTexture(TextureAtlas.LOCATION_BLOCKS);
				if (texture instanceof TextureAtlas atlas) {
					TextureAtlasSprite sprite = atlas.getSprite(still);
					RenderSystem.setShaderTexture(0, TextureAtlas.LOCATION_BLOCKS);


					int color = IClientFluidTypeExtensions.of(fluidStack.getFluid()).getTintColor(fluidStack);
					RenderSystem.setShaderColor(
							FastColor.ARGB32.red(color) / 255.0F,
							FastColor.ARGB32.green(color) / 255.0F,
							FastColor.ARGB32.blue(color) / 255.0F,
							FastColor.ARGB32.alpha(color) / 255.0F);
					RenderSystem.enableBlend();

					int stored = fluidTank.getFluidAmount();
					float capacity = fluidTank.getCapacity();
					float filledVolume = stored / capacity;
					int renderableHeight = (int) (filledVolume * height);


					int atlasWidth = (int) (sprite.getWidth() / (sprite.getU1() - sprite.getU0()));
					int atlasHeight = (int) (sprite.getHeight() / (sprite.getV1() - sprite.getV0()));

					poseStack.pushPose();
					poseStack.translate(0, height - 16, 0);
					for (int i = 0; i < Math.ceil(renderableHeight / 16f); i++) {
						int drawingHeight = Math.min(16, renderableHeight - 16 * i);
						int notDrawingHeight = 16 - drawingHeight;
						blit(poseStack, x, y + notDrawingHeight, displayOn.getBlitOffset(), sprite.getU0() * atlasWidth, sprite.getV0() * atlasHeight + notDrawingHeight, getWidth(), drawingHeight, atlasWidth, atlasHeight);
						poseStack.translate(0, -16, 0);
					}

					RenderSystem.setShaderColor(1, 1, 1, 1);
					poseStack.popPose();
				}
			}
			renderToolTip(poseStack, mouseX, mouseY);
		}
	}

	public void renderToolTip(PoseStack poseStack, int mouseX, int mouseY) {
		if (isHovered(mouseX, mouseY)) {
			displayOn.renderTooltip(poseStack, Arrays.asList(getFluid.get().getFluid().getDisplayName().getVisualOrderText(), Component.translatable(getFluid.get().getFluidAmount() + "mB").getVisualOrderText()), mouseX, mouseY);
		}
	}

	@Override
	public void updateNarration(NarrationElementOutput pNarrationElementOutput) {
	}

	protected boolean isHovered(int mouseX, int mouseY) {
		return mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;
	}
}
