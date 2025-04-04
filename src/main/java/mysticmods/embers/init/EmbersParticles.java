package mysticmods.embers.init;

import mysticmods.embers.Embers;
import mysticmods.embers.core.particles.types.EmbersParticleType;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class EmbersParticles {
    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES =
            DeferredRegister.create(BuiltInRegistries.PARTICLE_TYPE, Embers.MODID);


    public static final Supplier<EmbersParticleType> PARTICLE_GLOW = PARTICLE_TYPES.register(
            "particle_glow",
            () -> new EmbersParticleType(true)
    );

    public static void init() {
    }
}
