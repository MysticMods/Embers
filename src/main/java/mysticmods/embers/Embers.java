package mysticmods.embers;

import com.tterrag.registrate.Registrate;
import mysticmods.embers.core.machines.forge.SmelterRecipeProvider;
import mysticmods.embers.core.network.NetworkHandler;
import mysticmods.embers.init.*;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.Lazy;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import javax.annotation.Nonnull;
import java.util.logging.LogManager;
import java.util.logging.Logger;

@Mod(Embers.MOD_ID)
public class Embers {

	public static final String MOD_ID = "embers";
	public static final Logger LOGGER = LogManager.getLogManager().getLogger(MOD_ID);
	private static final Lazy<Registrate> REGISTRATE = Lazy.of(() -> Registrate.create(MOD_ID));

	public static final CreativeModeTab ITEM_GROUP = new CreativeModeTab(Embers.MOD_ID) {
		@Override
		@Nonnull
		public ItemStack makeIcon() {
			return new ItemStack(EmbersItems.EMBER_CRYSTAL.get());
		}
	};

	public Embers() {
		IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
		bus.addListener(this::setup);
		bus.addListener(this::gatherData);
		//bus.addListener(this::registerCaps);
		MinecraftForge.EVENT_BUS.register(this);

		EmbersBlocks.init();
		EmbersItems.init();
		EmbersLang.init();
		EmbersBlockEntities.init();
		EmbersTags.init();
		EmbersMenus.init();
		EmbersFluids.init();
		EmbersRecipes.Serializers.load();
		EmbersRecipes.Types.register(bus);
	}

	public void setup(FMLCommonSetupEvent event) {
		NetworkHandler.register();
	}

	public void gatherData(GatherDataEvent event) {
		DataGenerator generator = event.getGenerator();
		generator.addProvider(event.includeServer(), new SmelterRecipeProvider(generator));
	}

	public static Registrate registrate() {
		return REGISTRATE.get();
	}

}
