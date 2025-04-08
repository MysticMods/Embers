package mysticmods.embers.particles;

import mysticmods.embers.particles.options.EmbersParticleOptions;
import mysticmods.embers.particles.options.HammerSparkParticleOptions;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import org.jetbrains.annotations.NotNull;

public class HammerSparkParticleProvider implements ParticleProvider<HammerSparkParticleOptions> {
    private final SpriteSet spriteSet;

    public HammerSparkParticleProvider(SpriteSet spriteSet) {
        this.spriteSet = spriteSet;
    }

    @Override
    public Particle createParticle(@NotNull HammerSparkParticleOptions options, @NotNull ClientLevel level,
                                   double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
        return new HammerSparkParticle(level, x, y, z, xSpeed, ySpeed, zSpeed, spriteSet);
    }


}
