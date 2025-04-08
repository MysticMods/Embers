package mysticmods.embers.particles.options;

import mysticmods.embers.init.EmbersParticles;
import mysticmods.embers.particles.EmbersParticleRenderTypes;
import mysticmods.embers.particles.types.EmbersParticleType;
import net.minecraft.core.particles.ParticleType;
import org.jetbrains.annotations.NotNull;

public class HammerSparkParticleOptions extends EmbersParticleOptions{

    public HammerSparkParticleOptions(float red, float green, float blue) {
        super(red, green, blue);
    }

    @Override
    public @NotNull ParticleType<?> getType() {
        return EmbersParticles.HAMMER_SPARK.get();
    }
}
