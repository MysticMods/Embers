package mysticmods.embers.core.particles;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.TriState;

import javax.annotation.Nullable;
import java.util.function.Function;

public class EmbersParticleRenderTypes {
    public static final Function<ResourceLocation, RenderType> ADDITIVE_PARTICLE = Util.memoize(
            texture -> RenderType.create(
                    "additive_particle",
                    DefaultVertexFormat.PARTICLE,
                    VertexFormat.Mode.QUADS,
                    256,
                    false,
                    false,
                    RenderType.CompositeState.builder()
                            .setShaderState(RenderType.PARTICLE_SHADER)
                            .setTextureState(new RenderStateShard.TextureStateShard(texture, TriState.FALSE, false))
                            .setTransparencyState(RenderType.LIGHTNING_TRANSPARENCY) // 🚀 Additive blending
                            .setOutputState(RenderType.PARTICLES_TARGET)
                            .setLightmapState(RenderType.NO_LIGHTMAP) // ✅ Ignores lighting!
                            .setWriteMaskState(RenderType.COLOR_DEPTH_WRITE)
                            .createCompositeState(false)
            )
    );

    public static RenderType additiveParticle(ResourceLocation texture) {
        return ADDITIVE_PARTICLE.apply(texture);
    }

    public static final ParticleRenderType PARTICLE_SHEET_ADDITIVE = new ParticleRenderType(
            "PARTICLE_SHEET_ADDITIVE", additiveParticle(TextureAtlas.LOCATION_PARTICLES)
    );
}