package mysticmods.embers.core.particles.types;

import com.mojang.serialization.MapCodec;
import mysticmods.embers.core.particles.options.EmbersParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import org.jetbrains.annotations.NotNull;

public class EmbersParticleType extends ParticleType<EmbersParticleOptions> {

    public EmbersParticleType(boolean overrideLimitter) {
        super(overrideLimitter);
    }

    @Override
    public @NotNull MapCodec<EmbersParticleOptions> codec() {
        return EmbersParticleOptions.CODEC;
    }

    @Override
    public @NotNull StreamCodec<? super RegistryFriendlyByteBuf, EmbersParticleOptions> streamCodec() {
        return EmbersParticleOptions.STREAM_CODEC;
    }


}
