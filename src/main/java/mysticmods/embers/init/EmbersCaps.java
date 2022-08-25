package mysticmods.embers.init;

import mysticmods.embers.Embers;
import mysticmods.embers.api.capability.IEmberIntensity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Embers.MOD_ID)
public class EmbersCaps {
	public static final ResourceLocation EMBER_CAP_ID = new ResourceLocation(Embers.MOD_ID, "ember");

	public static final Capability<IEmberIntensity> EMBER_INTENSITY = CapabilityManager.get(new CapabilityToken<>() {
	});

	@SubscribeEvent
	public void registerCaps(RegisterCapabilitiesEvent event) {
		event.register(IEmberIntensity.class);
	}

	@SubscribeEvent
	public static void attachCapability(AttachCapabilitiesEvent<Block> event) {
	}
}