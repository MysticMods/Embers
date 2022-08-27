package mysticmods.embers.init;

import com.tterrag.registrate.Registrate;
import com.tterrag.registrate.providers.ProviderType;
import mysticmods.embers.Embers;

public class EmbersTags {
	static {

		final Registrate REGISTRATE = Embers.registrate();
		REGISTRATE.addDataGenerator(ProviderType.ENTITY_TAGS, b -> {

		});

		REGISTRATE.addDataGenerator(ProviderType.BLOCK_TAGS, b -> {
			b.tag(mysticmods.embers.api.data.EmbersTags.Blocks.EMBER_EMITTER);
			b.tag(mysticmods.embers.api.data.EmbersTags.Blocks.EMBER_USING);

		});

		REGISTRATE.addDataGenerator(ProviderType.ITEM_TAGS, b -> {

		});
	}

	public static void init() {
	}
}