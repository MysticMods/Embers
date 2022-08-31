package mysticmods.embers;

import com.tterrag.registrate.Registrate;
import mysticmods.embers.core.config.EmbersConfig;
import mysticmods.embers.core.gen.Providers;
import mysticmods.embers.core.machines.forge.SmelterRecipeProvider;
import mysticmods.embers.core.network.NetworkHandler;
import mysticmods.embers.init.*;
import net.minecraft.core.Holder;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.worldgen.features.OreFeatures;
import net.minecraft.data.worldgen.placement.OrePlacements;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.Lazy;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import javax.annotation.Nonnull;
import java.util.List;
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
		ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, EmbersConfig.SERVER_CONFIG_SPEC);
		IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
		bus.addListener(this::setup);
		bus.addListener(this::gatherData);
		//bus.addListener(this::registerCaps);
		MinecraftForge.EVENT_BUS.register(this);

		registrate().creativeModeTab(() -> ITEM_GROUP);

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

		// Features
		generator.addProvider(event.includeServer(), Providers.placedFeature(Embers.MOD_ID)
				.add("cinnabar_ore", new PlacedFeature(Holder.direct(new ConfiguredFeature<>(Feature.ORE,
						new OreConfiguration(List.of(OreConfiguration.target(OreFeatures.STONE_ORE_REPLACEABLES, EmbersBlocks.CAMINITE_BRICK.getDefaultState())), 10))),
						OrePlacements.commonOrePlacement(10, HeightRangePlacement.triangle(VerticalAnchor.BOTTOM, VerticalAnchor.TOP))))
				.build(generator, event.getExistingFileHelper())
		);

		// Biome Modifiers
//		generator.addProvider(event.includeServer(), Providers.biomeModifer(Embers.MOD_ID)
//				.add("cinnabar_ore", new ForgeBiomeModifiers.AddFeaturesBiomeModifier(HolderSet.direct()))
//		);

	}

	public static Registrate registrate() {
		return REGISTRATE.get();
	}
}
