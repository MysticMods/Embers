package mysticmods.embers.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import mysticmods.embers.machines.caminite_mold.CaminiteMoldBlockEntity;
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

import java.util.ArrayList;
import java.util.List;

public class CaminiteMoldRenderer implements BlockEntityRenderer<CaminiteMoldBlockEntity> {

    public CaminiteMoldRenderer(BlockEntityRendererProvider.Context context) {
    }

    private final ArrayList<Vec3> offsets = new ArrayList(List.of(
            new Vec3(0.25f, 0.8f, 0.25f),
            new Vec3(0.5f, 0.8f, 0.25f),
            new Vec3(0.75f, 0.8f, 0.25f),
            new Vec3(0.25f, 0.8f, 0.5f),
            new Vec3(0.5f, 0.8f, 0.5f),
            new Vec3(0.75f, 0.8f, 0.5f),
            new Vec3(0.25f, 0.8f, 0.75f),
            new Vec3(0.5f, 0.8f, 0.75f),
            new Vec3(0.75f, 0.8f, 0.75f)
    ));

    @Override
    public void render(CaminiteMoldBlockEntity blockEntity, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        Level level = blockEntity.getLevel();
        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
        for (int i = 0; i < blockEntity.getItemHandler().getSlots(); i++) {
            ItemStack stack = blockEntity.getItemHandler().getStackInSlot(i);
            if (!stack.isEmpty()) {
                poseStack.pushPose();
                Vec3 offset = this.offsets.get(i);
                poseStack.translate(offset.x(), offset.y(), offset.z());
                poseStack.scale(0.2f, 0.2f, 0.2f);
                itemRenderer.renderStatic(stack, ItemDisplayContext.FIXED, LightTexture.FULL_BRIGHT, packedOverlay, poseStack, bufferSource, level, 0);
                poseStack.popPose();
            }
        }
    }

}
