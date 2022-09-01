package mysticmods.embers.init;

import com.tterrag.registrate.Registrate;
import com.tterrag.registrate.providers.ProviderType;
import mysticmods.embers.Embers;
import mysticmods.embers.api.data.Tags;

public class EmbersTags {
	public static final Registrate REGISTRATE = Embers.registrate();

	static {
		REGISTRATE.addDataGenerator(ProviderType.ENTITY_TAGS, b -> {

		});

		REGISTRATE.addDataGenerator(ProviderType.BLOCK_TAGS, b -> {
			b.tag(Tags.Blocks.EMBER_EMITTER);
			b.tag(Tags.Blocks.EMBER_USING);

		});

		REGISTRATE.addDataGenerator(ProviderType.ITEM_TAGS, b -> {

		});
	}

	public static void init() {
	}
}