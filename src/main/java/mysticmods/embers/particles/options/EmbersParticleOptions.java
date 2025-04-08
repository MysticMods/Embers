package mysticmods.embers.particles.options;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import mysticmods.embers.init.EmbersParticles;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.codec.StreamCodec;
import org.jetbrains.annotations.NotNull;

public class EmbersParticleOptions implements ParticleOptions {
    private final float red, green, blue;

    // Codec for serializing and deserializing particle data (for world save & data-driven systems)
    public static final MapCodec<EmbersParticleOptions> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Codec.FLOAT.fieldOf("red").forGetter(EmbersParticleOptions::getRed),
            Codec.FLOAT.fieldOf("green").forGetter(EmbersParticleOptions::getGreen),
            Codec.FLOAT.fieldOf("blue").forGetter(EmbersParticleOptions::getBlue)
    ).apply(instance, EmbersParticleOptions::new));

    // StreamCodec for network communication (for syncing particle data between server and client)
    public static final StreamCodec<ByteBuf, EmbersParticleOptions> STREAM_CODEC = StreamCodec.of(
            (buf, options) -> { // Writing to packet
                buf.writeFloat(options.red);
                buf.writeFloat(options.green);
                buf.writeFloat(options.blue);
            },
            buf -> new EmbersParticleOptions(buf.readFloat(), buf.readFloat(), buf.readFloat()) // Reading from packet
    );

    public EmbersParticleOptions(float red, float green, float blue) {
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    public float getRed() { return red; }
    public float getGreen() { return green; }
    public float getBlue() { return blue; }

    @Override
    public @NotNull ParticleType<?> getType() {
        return EmbersParticles.PARTICLE_GLOW.get();
    }
}
