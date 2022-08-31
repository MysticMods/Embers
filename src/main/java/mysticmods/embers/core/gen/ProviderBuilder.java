package mysticmods.embers.core.gen;

import net.minecraft.core.Registry;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.JsonCodecProvider;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class ProviderBuilder<T> {

	private final Map<ResourceLocation, T> entries = new HashMap<>();
	private final String modid;
	private ModProvider<T> provider;

	public ProviderBuilder(String modid, ResourceKey<Registry<T>> registry, ProviderBuild<T> providerBuild, DataGenerator generator, ExistingFileHelper helper) {
		this.modid = modid;
		this.provider = providerBuild.build(generator, helper, modid, entries, registry);
	}

	public ProviderBuilder<T> add(String name, Function<ModProvider<T>, T> entryWithContext) {
		entries.compute(new ResourceLocation(modid, name), (key, value) -> {
			if (value != null) {
				throw new IllegalStateException("Provider entry is a duplicate: " + key);
			}
			return entryWithContext.apply(provider);
		});
		return this;
	}

	public JsonCodecProvider<T> build() {
		return provider;
	}

	@FunctionalInterface
	public interface ProviderBuild<B> {
		ModProvider<B> build(DataGenerator dataGenerator, ExistingFileHelper existingFileHelper, String modid, Map<ResourceLocation, B> entries, ResourceKey<Registry<B>> registry);
	}
}
