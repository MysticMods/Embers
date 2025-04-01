package mysticmods.embers.core.particles;

import mysticmods.embers.core.particles.options.EmbersParticleOptions;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import org.jetbrains.annotations.NotNull;

public class GlowParticleProvider implements ParticleProvider<EmbersParticleOptions> {
    private final SpriteSet spriteSet;

    public GlowParticleProvider(SpriteSet spriteSet) {
        this.spriteSet = spriteSet;
    }

    @Override
    public Particle createParticle(@NotNull EmbersParticleOptions options, @NotNull ClientLevel level,
                                   double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
        // We don't use the type and speed, and pass in everything else. You may of course use them if needed.
        return new GlowParticle(level, x, y, z, xSpeed, ySpeed, zSpeed, spriteSet, options);
    }
}
