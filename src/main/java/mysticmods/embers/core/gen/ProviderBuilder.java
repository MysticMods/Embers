package mysticmods.embers.core.gen;

import net.minecraft.core.Registry;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.JsonCodecProvider;

import java.util.HashMap;
import java.util.Map;

public class ProviderBuilder<T> {

	private final Map<ResourceLocation, T> entries = new HashMap<>();
	private final String modid;
	private final ProviderBuild<T> providerBuild;
	private final ResourceKey<Registry<T>> registry;

	public ProviderBuilder(String modid, ResourceKey<Registry<T>> registry, ProviderBuild<T> providerBuild) {
		this.modid = modid;
		this.registry = registry;
		this.providerBuild = providerBuild;
	}

	public ProviderBuilder<T> add(String name, T entry) {
		entries.compute(new ResourceLocation(modid, name), (key, value) -> {
			if (value != null) {
				throw new IllegalStateException("Provider entry is a duplicate: " + key);
			}
			return entry;
		});
		return this;
	}

	public JsonCodecProvider<T> build(DataGenerator generator, ExistingFileHelper helper) {
		return providerBuild.build(generator, helper, modid, entries, registry);
	}

	@FunctionalInterface
	public interface ProviderBuild<B> {
		JsonCodecProvider<B> build(DataGenerator dataGenerator, ExistingFileHelper existingFileHelper, String modid, Map<ResourceLocation, B> entries, ResourceKey<Registry<B>> registry);
	}
}
