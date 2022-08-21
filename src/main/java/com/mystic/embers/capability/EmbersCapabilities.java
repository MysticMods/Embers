package com.mystic.embers.capability;

import com.mystic.embers.Embers;
import com.mystic.embers.capability.reciever.EmberReceiverCapability;
import com.mystic.embers.capability.reciever.EmberReceiverCapabilityProvider;
import com.mystic.embers.capability.reciever.IEmberReceiverCapability;
import com.mystic.embers.init.ModBlockEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Embers.MODID)
public class EmbersCapabilities {
	public static final ResourceLocation EMBERS_RECEIVER_CAPABILITY_ID = new ResourceLocation(Embers.MODID, "ember_reciever_capability");

	public static final Capability<IEmberReceiverCapability> EMBER_RECEIVER_CAPABILITY = CapabilityManager.get(new CapabilityToken<>() {
	});

	@SubscribeEvent
	public void registerCaps(RegisterCapabilitiesEvent event) {
		event.register(EmberReceiverCapability.class);
	}

	@SubscribeEvent
	public static void attachCapability(AttachCapabilitiesEvent<BlockEntity> event) {
		if (event.getObject().getType() == ModBlockEntity.EMBER_CRYSTALLIZER.get()) {
			event.addCapability(EMBERS_RECEIVER_CAPABILITY_ID, new EmberReceiverCapabilityProvider());
		}
	}

	public static LazyOptional<IEmberReceiverCapability> getEmberReceiverCapability(final BlockEntity blockEntity) {
		return blockEntity.getCapability(EMBER_RECEIVER_CAPABILITY);
	}
}