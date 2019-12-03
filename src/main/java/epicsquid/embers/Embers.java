package epicsquid.embers;

import epicsquid.embers.blocks.ModBlocks;
import epicsquid.embers.setup.ModSetup;
import epicsquid.mysticallib.registry.ModRegistry;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod("embers")
public class Embers {
	public static ItemGroup GROUP = new ItemGroup(Embers.MODID) {
		@Override
		public ItemStack createIcon() {
			return new ItemStack(Items.PUMPKIN);
		}
	};

	public static final String MODID = "embers";

	public static ModRegistry REGISTRY = new ModRegistry(MODID);
	public static ModSetup setup = new ModSetup();

	public Embers() {
		IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
		modBus.addListener(this::setup);
		modBus.addListener(setup::gatherData);


		ModBlocks.load();

		REGISTRY.registerEventBus(modBus);


	}

	private void setup(FMLCommonSetupEvent event) {
		setup.init(event);
	}
}
