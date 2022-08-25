package mysticmods.embers.core.network;

import mysticmods.embers.Embers;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

public class NetworkHandler {

	private static final String PROTOCOL_VERSION = "1.0";
	public static SimpleChannel INSTANCE;

	private static int id = 0;

	private static int nextId() {
		return id++;
	}

	public static void register() {

		INSTANCE = NetworkRegistry.newSimpleChannel(new ResourceLocation(Embers.MOD_ID, "network"), () -> PROTOCOL_VERSION, s -> true, s -> true);

		// Register all packets
		//INSTANCE.registerMessage(nextId(), PacketShieldUpdate.class, PacketShieldUpdate::encode, PacketShieldUpdate::decode, PacketShieldUpdate::handle);
	}
}
