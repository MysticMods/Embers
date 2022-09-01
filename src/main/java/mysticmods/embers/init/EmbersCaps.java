package mysticmods.embers.init;

import mysticmods.embers.Embers;
import mysticmods.embers.api.capability.IEmber;
import mysticmods.embers.api.capability.IEmberEmitter;
import mysticmods.embers.api.capability.IEmberIntensity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
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
	public static final ResourceLocation EMBER_INTENSITY_CAP_ID = new ResourceLocation(Embers.MOD_ID, "ember_intensity");
	public static final ResourceLocation EMBER_EMITTER_CAP_ID = new ResourceLocation(Embers.MOD_ID, "ember_emitter");
	public static final ResourceLocation EMBER_CAP_ID = new ResourceLocation(Embers.MOD_ID, "ember");

	public static final Capability<IEmberIntensity> EMBER_INTENSITY = CapabilityManager.get(new CapabilityToken<>() {
	});

	public static final Capability<IEmberEmitter> EMBER_EMITTER = CapabilityManager.get(new CapabilityToken<>() {
	});

	public static final Capability<IEmber> EMBER = CapabilityManager.get(new CapabilityToken<>() {
	});

	@SubscribeEvent
	public void registerCaps(RegisterCapabilitiesEvent event) {
		event.register(IEmberIntensity.class);
		event.register(IEmberEmitter.class);
		event.register(IEmber.class);
	}

	@SubscribeEvent
	public static void attachCapability(AttachCapabilitiesEvent<Level> event) {
		event.addCapability(EMBER_CAP_ID, );
	}
}