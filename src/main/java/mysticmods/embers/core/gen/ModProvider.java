package mysticmods.embers.core.gen;

import com.google.gson.JsonElement;
import com.mojang.serialization.JsonOps;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.tags.TagKey;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.JsonCodecProvider;

import java.util.Arrays;
import java.util.Map;

public class ModProvider<T> extends JsonCodecProvider<T> {

	public ModProvider(DataGenerator dataGenerator, ExistingFileHelper existingFileHelper, String modid, Map<ResourceLocation, T> entries, ResourceKey<Registry<T>> registry) {
		super(dataGenerator, existingFileHelper, modid, RegistryOps.create(JsonOps.INSTANCE, RegistryAccess.builtinCopy()), PackType.SERVER_DATA, Providers.getProviderDirectory(registry), Providers.getProviderCodec(registry), entries);
	}

	public <U> HolderSet<U> getHolderSet(TagKey<U> tag) {
		return ((RegistryOps<JsonElement>) dynamicOps).registry(tag.registry()).orElseThrow().getOrCreateTag(tag);
	}

	public <U> HolderSet<U> getHolderSet(ResourceKey<Registry<U>> registry, ResourceLocation... names) {
		var reg = ((RegistryOps<JsonElement>) dynamicOps).registry(registry).orElseThrow();
		return HolderSet.direct(Arrays.stream(names)
				.map(n -> reg.getOrCreateHolderOrThrow(ResourceKey.create(registry, n)))
				.toList());
	}
}
