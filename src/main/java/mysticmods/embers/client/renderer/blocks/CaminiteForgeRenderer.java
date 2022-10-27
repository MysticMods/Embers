package mysticmods.embers.client.renderer.blocks;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
import mysticmods.embers.client.renderer.items.TransparentItemRenderer;
import mysticmods.embers.core.machines.forge.CaminiteForgeEntity;
import mysticmods.embers.init.EmbersBlocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

import java.util.HashMap;

import static net.minecraft.client.renderer.texture.OverlayTexture.NO_OVERLAY;

public class CaminiteForgeRenderer implements BlockEntityRenderer<CaminiteForgeEntity> {

    private final HashMap<Direction, Vec3> offsets = new HashMap<>();
    private final HashMap<Direction, Quaternion> rotations = new HashMap<>();
    private final TransparentItemRenderer itemRenderer = new TransparentItemRenderer();

    public CaminiteForgeRenderer(BlockEntityRendererProvider.Context context) {
        offsets.put(Direction.WEST, new Vec3(-0.1, 0.5f, 0.5f));
        offsets.put(Direction.EAST, new Vec3(1.1f, 0.5f, 0.5f));
        offsets.put(Direction.NORTH, new Vec3(.5f, 0.5f, -0.1f));
        offsets.put(Direction.SOUTH, new Vec3(.5f, 0.5f, 1.1f));
        offsets.put(Direction.UP, new Vec3(-0.1, 0.5f, 0.5f));
        offsets.put(Direction.DOWN, new Vec3(-0.1, 0.5f, 0.5f));

        rotations.put(Direction.WEST, Vector3f.YP.rotationDegrees(90));
        rotations.put(Direction.EAST, Vector3f.YP.rotationDegrees(90));
        rotations.put(Direction.NORTH, Vector3f.XP.rotationDegrees(0));
        rotations.put(Direction.SOUTH, Vector3f.XP.rotationDegrees(0));
        rotations.put(Direction.UP, Vector3f.YP.rotationDegrees(90));
        rotations.put(Direction.DOWN, Vector3f.YP.rotationDegrees(90));
    }

    @Override
    public void render(CaminiteForgeEntity blockEntity, float partialTicks, PoseStack poseStack, MultiBufferSource pBufferSource, int pPackedLight, int pPackedOverlay) {
        Level level = Minecraft.getInstance().level;
        Minecraft mc = Minecraft.getInstance();

        HitResult hitresult = mc.hitResult;
        if (hitresult != null && hitresult.getType() == HitResult.Type.BLOCK) {
            BlockPos blockpos = ((BlockHitResult)hitresult).getBlockPos();
            BlockState blockstate = mc.level.getBlockState(blockpos);
            if ((blockstate.getBlock() == EmbersBlocks.CAMINITE_FORGE.get() || blockstate.getBlock() == EmbersBlocks.CAMINITE_FORGE_COMPONENT.get())
            && (blockpos.equals(blockEntity.getBlockPos()) || blockpos.equals(blockEntity.getBlockPos().above()))) {
                ItemStack stack = blockEntity.getItemHandler().getStackInSlot(1);
                if (!stack.isEmpty()) {
                    poseStack.pushPose();
                    Vec3 directionOffset = offsets.get(((BlockHitResult)hitresult).getDirection());
                    poseStack.translate(directionOffset.x(), directionOffset.y(), directionOffset.z());
                    poseStack.mulPose(rotations.get(((BlockHitResult)hitresult).getDirection()));
                    poseStack.scale(0.4f, 0.4f, 0.4f);
                    itemRenderer.setAlpha(0.5f);
                    itemRenderer.renderStatic(stack, ItemTransforms.TransformType.FIXED, pPackedLight, NO_OVERLAY, poseStack, pBufferSource, 0);
                    poseStack.popPose();
                }
            }
        }
    }

}