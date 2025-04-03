package mysticmods.embers.registries;

import mysticmods.embers.Embers;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.registries.RegistryBuilder;

public class MalleableMetalRegistry {
    public static final ResourceKey<Registry<MalleableMetal>> MALLEABLE_METAL_REGISTRY_KEY = ResourceKey.createRegistryKey(ResourceLocation.fromNamespaceAndPath(Embers.MODID, "malleable_metal"));
    public static final Registry<MalleableMetal> MALLEABLE_METAL_REGISTRY = new RegistryBuilder<>(MALLEABLE_METAL_REGISTRY_KEY).create();

}
