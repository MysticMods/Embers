package mysticmods.embers.client.renderer.blocks;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import mysticmods.embers.core.machines.anvil.copper.CopperAnvilEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import static net.minecraft.client.renderer.texture.OverlayTexture.NO_OVERLAY;

public class CopperAnvilRenderer implements BlockEntityRenderer<CopperAnvilEntity> {

	public CopperAnvilRenderer(BlockEntityRendererProvider.Context context) {
	}

	@Override
	public void render(CopperAnvilEntity blockEntity, float partialTicks, PoseStack poseStack, MultiBufferSource pBufferSource, int pPackedLight, int pPackedOverlay) {
		Level level = Minecraft.getInstance().level;
		ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
		for (int i = 0; i < blockEntity.itemHandler.getFilledSlotAmount(); i++) {
			ItemStack stack = blockEntity.itemHandler.getStackInSlot(i);
			if (!stack.isEmpty()) {
				poseStack.pushPose();
				Vec3 offset = new Vec3(0.5f, 1f, 0.5f);
				poseStack.translate(offset.x(), offset.y(), offset.z());
				poseStack.mulPose(Vector3f.XP.rotationDegrees(90));
				poseStack.scale(0.4f, 0.4f, 0.4f);
				itemRenderer.renderStatic(stack, ItemTransforms.TransformType.FIXED, pPackedLight, NO_OVERLAY, poseStack, pBufferSource, 0);
				poseStack.popPose();
			}
		}
	}

}
