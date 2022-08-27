package mysticmods.embers.core.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class ServerConfig {


	public final ForgeConfigSpec.ConfigValue<Integer> EMITTER_SEARCH_RANGE;
	public ServerConfig(ForgeConfigSpec.Builder builder) {
		builder.push("Search Ranges");
		builder.comment("Contains the configuration for the search radius of various machines");
		EMITTER_SEARCH_RANGE = builder.comment("The amount of blocks radius to search max for a valid ember emitter. Larger values will make the lookup take longer.")
				.defineInRange("emitter_search_range", 5, 1, 50);
		builder.pop();
	}
}
