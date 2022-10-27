package mysticmods.embers.client.renderer.items;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.color.item.ItemColors;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class TransparentItemRenderer  extends ItemRenderer {

    private final ItemColors newItemColors;
    private float alpha = 1.0f;

    public TransparentItemRenderer(){
        super(Minecraft.getInstance().getTextureManager(), Minecraft.getInstance().getModelManager(), Minecraft.getInstance().getItemColors(), Minecraft.getInstance().getItemRenderer().getBlockEntityRenderer());
        this.newItemColors = Minecraft.getInstance().getItemColors();
    }

    public TransparentItemRenderer(TextureManager pTextureManager, ModelManager pModelManager, ItemColors pItemColors, BlockEntityWithoutLevelRenderer pBlockEntityRenderer) {
        super(pTextureManager, pModelManager, pItemColors, pBlockEntityRenderer);
        this.newItemColors = pItemColors;
    }

    @Override
    public void renderQuadList(PoseStack pPoseStack, VertexConsumer pBuffer, List<BakedQuad> pQuads, ItemStack pItemStack, int pCombinedLight, int pCombinedOverlay) {
        boolean flag = !pItemStack.isEmpty();
        PoseStack.Pose posestack$pose = pPoseStack.last();

        for(BakedQuad bakedquad : pQuads) {
            int i = -1;
            if (flag && bakedquad.isTinted()) {
                i = this.newItemColors.getColor(pItemStack, bakedquad.getTintIndex());
            }

            float f = (float)(i >> 16 & 255) / 255.0F;
            float f1 = (float)(i >> 8 & 255) / 255.0F;
            float f2 = (float)(i & 255) / 255.0F;
            pBuffer.putBulkData(posestack$pose, bakedquad, f, f1, f2, this.alpha, pCombinedLight, pCombinedOverlay, true);
        }
    }

    public float getAlpha() {
        return alpha;
    }

    public void setAlpha(float alpha) {
        this.alpha = alpha;
    }
}
