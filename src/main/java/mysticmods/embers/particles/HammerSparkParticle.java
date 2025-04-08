package mysticmods.embers.particles;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;
import org.jetbrains.annotations.NotNull;

public class HammerSparkParticle extends TextureSheetParticle {
    private final SpriteSet spriteSet;

    public HammerSparkParticle(ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed, SpriteSet spriteSet) {
        super(level, x, y, z);
        this.spriteSet = spriteSet;
        this.gravity = 0.15F; // Sparks fall a bit
        this.lifetime = 10 + random.nextInt(5); // Very short-lived
        this.scale(0.15f + random.nextFloat() * 0.1f); // Small sparks
        this.setColor(1.0f, 0.8f + random.nextFloat() * 0.2f, 0.3f); // Fiery yellow-orange

        // Add some randomized velocity
        this.xd = xSpeed + (random.nextDouble() - 0.5) * 0.1;
        this.yd = ySpeed + random.nextDouble() * 0.2;
        this.zd = zSpeed + (random.nextDouble() - 0.5) * 0.1;

        this.setSpriteFromAge(spriteSet);
    }

    @Override
    protected int getLightColor(float partialTick) {
        return 0xF000F0; // Full brightness like glowstone/lava
    }

    @Override
    public void tick() {
        super.tick();
        this.setSpriteFromAge(spriteSet);

        // Optional: Fade out over time
        float lifeRatio = (float) this.age / this.lifetime;
        this.alpha = 1.0f - lifeRatio;
    }

    @Override
    public @NotNull ParticleRenderType getRenderType() {
        return EmbersParticleRenderTypes.GLOW;
    }
}