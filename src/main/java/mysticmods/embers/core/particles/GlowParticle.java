package mysticmods.embers.core.particles;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.VertexFormat;
import mysticmods.embers.core.particles.options.EmbersParticleOptions;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.client.renderer.texture.TextureAtlas;
import org.jetbrains.annotations.NotNull;

public class GlowParticle extends TextureSheetParticle {
    private final SpriteSet spriteSet;

    public GlowParticle(ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed, SpriteSet spriteSet, EmbersParticleOptions options) {
        super(level, x, y, z);
        this.spriteSet = spriteSet;
        this.gravity = 0;

        this.lifetime = 100;
        this.scale(0.6f);

        this.setColor(options.getRed(), options.getGreen(), options.getBlue());
        this.xd = xSpeed;
        this.yd = ySpeed;
        this.zd = zSpeed;

        this.setSpriteFromAge(spriteSet);
    }

    @Override
    public void tick() {
        this.setSpriteFromAge(spriteSet);
        super.tick();
    }

    @Override
    public @NotNull ParticleRenderType getRenderType() {
        return EmbersParticleRenderTypes.PARTICLE_SHEET_ADDITIVE;
    }
}
