package mysticmods.embers.init;

import mysticmods.embers.Embers;
import mysticmods.embers.particles.types.EmbersParticleType;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class EmbersParticles {
    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES =
            DeferredRegister.create(BuiltInRegistries.PARTICLE_TYPE, Embers.MODID);


    public static final DeferredHolder<ParticleType<?>, EmbersParticleType> PARTICLE_GLOW = PARTICLE_TYPES.register(
            "particle_glow",
            () -> new EmbersParticleType(true)
    );

    public static void register(IEventBus bus) {
        PARTICLE_TYPES.register(bus);
    }
}
