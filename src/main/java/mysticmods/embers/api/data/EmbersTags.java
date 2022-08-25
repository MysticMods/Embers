package mysticmods.embers.api.data;

import mysticmods.embers.api.EmbersApi;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public class EmbersTags {
	public static class Blocks extends EmbersTags {
		public static TagKey<Block> EMBER_HEAT_BLOCK = modTag("heat_block");
		public static TagKey<Block> EMBER_ACCEPTING = modTag("ember_accepting");
		public static TagKey<Block> EMBER_GENERATOR = modTag("ember_generator");

		static TagKey<Block> modTag(String name) {
			return BlockTags.create(new ResourceLocation(EmbersApi.EMBERS_MOD_ID, name));
		}
	}
}
