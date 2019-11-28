package epicsquid.embers.setup;

import epicsquid.embers.Embers;
import epicsquid.embers.blocks.ITile;
import epicsquid.embers.blocks.ModBlocks;
import epicsquid.embers.capability.EmberCapability;
import epicsquid.embers.capability.EmberCapabilityProvider;
import epicsquid.embers.capability.EmberCapabilityStorage;
import epicsquid.embers.capability.IEmberCapability;
import epicsquid.embers.entity.EntityEmber;
import epicsquid.embers.tile.EmberFilterTile;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Mod.EventBusSubscriber(modid = Embers.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class RegistryManager {

	private static List<Block> blocks = new ArrayList<>();

	@SubscribeEvent
	public static void registerBlocks(RegistryEvent.Register<Block> event) {
		blocks.add(ModBlocks.EMBER_FILTER.get());
	}

	@SubscribeEvent
	public static void registerItems(RegistryEvent.Register<Item> event) {
	}

	@SubscribeEvent
	public static void onTileRegistry(final RegistryEvent.Register<TileEntityType<?>> event){
		for(Block block : blocks){
			if(block instanceof ITile){
				event.getRegistry().register((TileEntityType<?>) TileEntityType.Builder.create(((ITile) block).getTile(), block).build(null)
						.setRegistryName(new ResourceLocation(Embers.MODID, Objects.requireNonNull(block.getRegistryName()).getPath() + "_tile")));
				System.out.println("REGISTERINGNGNG");
			}
		}
	}

	@SubscribeEvent
	public static void registerEntityTypes(final RegistryEvent.Register<EntityType<?>> event)
	{
		event.getRegistry().register(EntityType.Builder.create(EntityEmber::new, EntityClassification.AMBIENT).size(0.5F, 0.5F).build("ember_entity")
				.setRegistryName(new ResourceLocation(Embers.MODID, "ember_entity")));
	}

	@SubscribeEvent
	public static void attachCapabilities(AttachCapabilitiesEvent<TileEntity> event) {
		if(event.getObject() instanceof EmberFilterTile){
			event.addCapability(EmberCapabilityProvider.IDENTIFIER, new EmberCapabilityProvider());
		}

	}

	@SubscribeEvent
	public static void init(FMLCommonSetupEvent event) {
		CapabilityManager.INSTANCE.register(IEmberCapability.class, new EmberCapabilityStorage(), EmberCapability::new);
	}
}
