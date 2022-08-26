package mysticmods.embers.init;

import mysticmods.embers.Embers;
import com.tterrag.registrate.Registrate;
import com.tterrag.registrate.providers.ProviderType;
import net.minecraft.world.level.block.Blocks;

public class EmbersTags {
	static {

		final Registrate REGISTRATE = Embers.registrate();
		REGISTRATE.addDataGenerator(ProviderType.ENTITY_TAGS, b -> {

		});

		REGISTRATE.addDataGenerator(ProviderType.BLOCK_TAGS, b -> {
			b.tag(mysticmods.embers.api.data.EmbersTags.Blocks.EMBER_EMITTER).add(Blocks.LAVA);
			b.tag(mysticmods.embers.api.data.EmbersTags.Blocks.EMBER_USING);
			b.tag(mysticmods.embers.api.data.EmbersTags.Blocks.EMBER_GENERATOR);
			// Internal mod tags
			//b.tag(EmbersTags.Blocks.EMBER_HEAT_BLOCK).addTags(Blocks.EARTH_SOIL, Blocks.AIR_SOIL, Blocks.FIRE_SOIL, Blocks.WATER_SOIL, Blocks.ELEMENTAL_SOIL);

		});

		REGISTRATE.addDataGenerator(ProviderType.ITEM_TAGS, b -> {

		});
	}

	public static void init() {
	}
}