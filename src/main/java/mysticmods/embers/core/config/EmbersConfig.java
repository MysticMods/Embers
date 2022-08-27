package mysticmods.embers.core.config;

import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class EmbersConfig {

	public static final ServerConfig SERVER_CONFIG;
	public static final ForgeConfigSpec SERVER_CONFIG_SPEC;

	static {
		Pair<ServerConfig, ForgeConfigSpec> configSpecPair = new ForgeConfigSpec.Builder().configure(ServerConfig::new);
		SERVER_CONFIG = configSpecPair.getLeft();
		SERVER_CONFIG_SPEC = configSpecPair.getRight();
	}
}
