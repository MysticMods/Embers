package mysticmods.embers.datagen;

import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.common.data.ParticleDescriptionProvider;

public class ModParticleDescriptionProvider extends ParticleDescriptionProvider {

    public ModParticleDescriptionProvider(PackOutput output, ExistingFileHelper fileHelper) {
        super(output, fileHelper);
    }

    @Override
    protected void addDescriptions() {
//        sprite(EmbersParticles.PARTICLE_GLOW.get(), ResourceLocation.fromNamespaceAndPath(Embers.MODID, "particle_glow"));
//        sprite(EmbersParticles.HAMMER_SPARK.get(), ResourceLocation.fromNamespaceAndPath(Embers.MODID, "hammer_spark"));

    }
}