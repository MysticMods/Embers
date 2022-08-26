package mysticmods.embers.api.data;

import mysticmods.embers.api.EmbersApi;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public class EmbersTags {
	public static class Blocks {
		public static TagKey<Block> EMBER_EMITTER = modTag("ember_emitter");
		public static TagKey<Block> EMBER_USING = modTag("ember_using");

		static TagKey<Block> modTag(String name) {
			return BlockTags.create(new ResourceLocation(EmbersApi.EMBERS_MOD_ID, name));
		}
	}
}
