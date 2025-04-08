package mysticmods.embers.init;

import mysticmods.embers.Embers;
import mysticmods.embers.particles.types.EmbersParticleType;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import team.lodestar.lodestone.systems.particle.world.type.LodestoneWorldParticleType;

public class EmbersParticles {
    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES =
            DeferredRegister.create(BuiltInRegistries.PARTICLE_TYPE, Embers.MODID);

    public static DeferredHolder<ParticleType<?>, LodestoneWorldParticleType> PARTICLE_GLOW = PARTICLE_TYPES.register("particle_glow", LodestoneWorldParticleType::new);


//    public static final DeferredHolder<ParticleType<?>, EmbersParticleType> PARTICLE_GLOW = PARTICLE_TYPES.register(
//            "particle_glow",
//            () -> new EmbersParticleType(true)
//    );
//    public static final DeferredHolder<ParticleType<?>, EmbersParticleType> HAMMER_SPARK = PARTICLE_TYPES.register(
//            "hammer_spark",
//            () -> new EmbersParticleType(true)
//    );

    public static void register(IEventBus bus) {
        PARTICLE_TYPES.register(bus);
    }

    public static void registerParticleFactory(RegisterParticleProvidersEvent event) {
        for (DeferredHolder<ParticleType<?>, ? extends ParticleType<?>> entry : PARTICLE_TYPES.getEntries()) {
            event.registerSpriteSet((LodestoneWorldParticleType)entry.get(), LodestoneWorldParticleType.Factory::new);
        }
    }
}
