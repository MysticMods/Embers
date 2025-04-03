package mysticmods.embers.particles;

import mysticmods.embers.particles.options.EmbersParticleOptions;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;
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
    protected int getLightColor(float partialTick) {
        return 0xF000F0;
    }

    @Override
    public void tick() {
        this.setSpriteFromAge(spriteSet);
        super.tick();
    }

    @Override
    public @NotNull ParticleRenderType getRenderType() {
        return EmbersParticleRenderTypes.GLOW;
    }
}
