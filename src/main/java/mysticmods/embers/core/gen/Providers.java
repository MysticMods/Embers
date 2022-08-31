package mysticmods.embers.core.gen;

import com.mojang.serialization.Codec;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.registries.ForgeRegistries;

public class Providers {

	public static ProviderBuilder<BiomeModifier> biomeModifer(String modid) {
		return new ProviderBuilder<>(modid, ForgeRegistries.Keys.BIOME_MODIFIERS, ModProvider::new);
	}

	public static ProviderBuilder<PlacedFeature> placedFeature(String modid) {
		return new ProviderBuilder<>(modid, Registry.PLACED_FEATURE_REGISTRY, ModProvider::new);
	}

	static String getProviderDirectory(ResourceKey<? extends Registry<?>> registry) {
		return registry.location().getNamespace().equals("minecraft") ? registry.location().getPath()  : registry.location().getNamespace() + "/" + registry.location().getPath();
	}

	@SuppressWarnings("unchecked")
	static <T> Codec<T> getProviderCodec(ResourceKey<Registry<T>> registry) {
		return (Codec<T>) RegistryAccess.REGISTRIES.get(registry).codec();
	}
}
