package mysticmods.embers.api;

import mysticmods.embers.Embers;
import mysticmods.embers.registries.MalleableMetal;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.registries.RegistryBuilder;

public class EmbersRegistries {
    public static final Registry<MalleableMetal> MALLEABLE_METALS = new RegistryBuilder<>(Keys.MALLEABLE_METALS).create();

    public static class Keys {
        // Registry keys
        public static ResourceKey<Registry<MalleableMetal>> MALLEABLE_METALS = key(ResourceLocation.fromNamespaceAndPath(Embers.MODID, "malleable_metals"));

        private static <T> ResourceKey<Registry<T>> key(ResourceLocation name) {
            return ResourceKey.createRegistryKey(name);
        }
    }

}

