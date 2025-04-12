package mysticmods.embers.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import mysticmods.embers.machines.anvil.copper.CopperAnvilBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;


public class CopperAnvilRenderer implements BlockEntityRenderer<CopperAnvilBlockEntity> {

    public CopperAnvilRenderer(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public void render(CopperAnvilBlockEntity blockEntity, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        Level level = blockEntity.getLevel();
        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
        for (int i = 0; i < blockEntity.itemHandler.getFilledSlotAmount(); i++) {
            ItemStack stack = blockEntity.itemHandler.getStackInSlot(i);
            if (!stack.isEmpty()) {
                poseStack.pushPose();
                Vec3 offset = new Vec3(0.5f, 1f, 0.5f);
                poseStack.translate(offset.x(), offset.y(), offset.z());
                poseStack.mulPose(Axis.XP.rotationDegrees(90));
                poseStack.scale(0.4f, 0.4f, 0.4f);
                itemRenderer.renderStatic(stack, ItemDisplayContext.FIXED, packedLight, packedOverlay, poseStack, bufferSource, level, 0);
                poseStack.popPose();
            }
        }
    }
}
