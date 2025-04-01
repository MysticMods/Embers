package mysticmods.embers.datagen;

import mysticmods.embers.Embers;
import mysticmods.embers.init.EmbersParticleTypes;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.common.data.ParticleDescriptionProvider;

public class EmbersParticleDescriptionProvider extends ParticleDescriptionProvider {

    public EmbersParticleDescriptionProvider(PackOutput output) {
        super(output);
    }

    @Override
    protected void addDescriptions() {
        sprite(EmbersParticleTypes.PARTICLE_GLOW.get(), ResourceLocation.fromNamespaceAndPath(Embers.MODID, "particle_glow"));

    }
}