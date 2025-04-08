package mysticmods.embers.datagen;

import mysticmods.embers.Embers;
import mysticmods.embers.init.EmbersParticles;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.common.data.ParticleDescriptionProvider;

public class EmbersParticleDescriptionProvider extends ParticleDescriptionProvider {

    public EmbersParticleDescriptionProvider(PackOutput output, ExistingFileHelper fileHelper) {
        super(output, fileHelper);
    }

    @Override
    protected void addDescriptions() {
//        sprite(EmbersParticles.PARTICLE_GLOW.get(), ResourceLocation.fromNamespaceAndPath(Embers.MODID, "particle_glow"));
//        sprite(EmbersParticles.HAMMER_SPARK.get(), ResourceLocation.fromNamespaceAndPath(Embers.MODID, "hammer_spark"));

    }
}