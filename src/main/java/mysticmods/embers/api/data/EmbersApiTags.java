package mysticmods.embers.api.data;

import mysticmods.embers.api.EmbersApi;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class EmbersApiTags {
	public static class Blocks {
		public static TagKey<Block> EMBER_EMITTER = modTag("ember_emitter");
		public static TagKey<Block> EMBER_USING = modTag("ember_using");

		public static TagKey<Block> ORES_VERMILLIONITE = forgeTag("ores/vermillionite");

		static TagKey<Block> modTag(String name) {
			return BlockTags.create(new ResourceLocation(EmbersApi.EMBERS_MOD_ID, name));
		}

		static TagKey<Block> forgeTag(String name) {
			return BlockTags.create(new ResourceLocation("forge", name));
		}
	}

	public static class Items {

		public static TagKey<Item> CINNABAR = forgeTag("cinnabar");
		public static TagKey<Item> VERMILLIONITE = forgeTag("raw/vermillionite");
		static TagKey<Item> modTag(String name) {
			return ItemTags.create(new ResourceLocation(EmbersApi.EMBERS_MOD_ID, name));
		}

		static TagKey<Item> forgeTag(String name) {
			return ItemTags.create(new ResourceLocation("forge", name));
		}
	}
}
