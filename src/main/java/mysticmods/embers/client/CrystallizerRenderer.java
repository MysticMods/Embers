package mysticmods.embers.client;

import com.mojang.blaze3d.vertex.PoseStack;
import mysticmods.embers.machines.crystallizer.EmberCrystallizerBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class CrystallizerRenderer implements BlockEntityRenderer<EmberCrystallizerBlockEntity> {

    public CrystallizerRenderer(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public void render(EmberCrystallizerBlockEntity blockEntity, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        Level level = blockEntity.getLevel();
        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
        ItemStack stack = blockEntity.getItemHandler().getStackInSlot(0);
        if (!stack.isEmpty()) {
            poseStack.pushPose();
            Vec3 offset = new Vec3(0.5f, 1f, 0.5f);
            poseStack.translate(offset.x(), offset.y(), offset.z());
            poseStack.scale(0.4f, 0.4f, 0.4f);
            itemRenderer.renderStatic(stack, ItemDisplayContext.FIXED, LightTexture.FULL_BRIGHT, packedOverlay, poseStack, bufferSource, level, 0);
            poseStack.popPose();
        }
    }
}
