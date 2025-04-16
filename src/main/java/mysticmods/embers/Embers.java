package mysticmods.embers;

import com.mojang.logging.LogUtils;
import mysticmods.embers.client.CaminiteMoldRenderer;
import mysticmods.embers.client.CopperAnvilRenderer;
import mysticmods.embers.client.CrystallizerRenderer;
import mysticmods.embers.datagen.*;
import mysticmods.embers.init.*;
import mysticmods.embers.machines.caminite_forge.menu.CaminiteForgeAlloyScreen;
import mysticmods.embers.machines.caminite_smelter.menu.CaminiteSmelterScreen;
import mysticmods.embers.network.CaminiteForgeToggleAlloyData;
import mysticmods.embers.network.EmbersNetworkHandler;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import org.slf4j.Logger;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

@Mod(Embers.MODID)
public class Embers {
    public static final String MODID = "embers";
    public static final Logger LOGGER = LogUtils.getLogger();


    public Embers(IEventBus modEventBus, ModContainer modContainer) {
        modEventBus.addListener(this::commonSetup);

        ModTags.init();

        ModBlocks.register(modEventBus);
        ModBlockEntities.register(modEventBus);
        ModDataComponents.register(modEventBus);
        ModItems.register(modEventBus);
        ModSerializers.register(modEventBus);
        ModMalleableMetals.register(modEventBus);
        ModRecipeTypes.register(modEventBus);
        ModParticles.register(modEventBus);
        ModTabs.register(modEventBus);
        ModMenuTypes.register(modEventBus);

        ModCapabilities.init();

        NeoForge.EVENT_BUS.register(this);

        modEventBus.addListener(this::addCreative);

        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
    }

    private void addCreative(BuildCreativeModeTabContentsEvent event) {

    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {

    }


    @EventBusSubscriber(modid = MODID, bus = EventBusSubscriber.Bus.MOD)
    public static class ServerModEvents {

        @SubscribeEvent
        private static void registerCapabilities(RegisterCapabilitiesEvent event) {
            ModCapabilities.register(event);
        }

        @SubscribeEvent
        private static void registerScreens(RegisterMenuScreensEvent event) {
            event.register(ModMenuTypes.CAMINITE_FORGE.get(), CaminiteSmelterScreen::new);
            event.register(ModMenuTypes.CAMINITE_FORGE_ALLOY.get(), CaminiteForgeAlloyScreen::new);
        }

        @SubscribeEvent
        public static void register(final RegisterPayloadHandlersEvent event) {
            final PayloadRegistrar registrar = event.registrar("1");
            registrar.playToServer(
                    CaminiteForgeToggleAlloyData.TYPE,
                    CaminiteForgeToggleAlloyData.STREAM_CODEC,
                    EmbersNetworkHandler::handleCaminiteForgePayload
            );
        }

        @SubscribeEvent
        public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
            event.registerBlockEntityRenderer(
                    ModBlockEntities.COPPER_ANVIL.get(),
                    CopperAnvilRenderer::new
            );

            event.registerBlockEntityRenderer(
                    ModBlockEntities.CAMINITE_MOLD.get(),
                    CaminiteMoldRenderer::new
            );

            event.registerBlockEntityRenderer(
                    ModBlockEntities.EMBER_CRYSTALLIZER.get(),
                    CrystallizerRenderer::new
            );
        }
    }

    @EventBusSubscriber(modid = MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            // TODO: Register screens for the Caminite Forge GUI
            // The MenuScreens.register method has private access in NeoForge
            // Need to find the correct way to register screens in NeoForge
        }

        @SubscribeEvent
        public static void gatherData(GatherDataEvent event) {
            DataGenerator generator = event.getGenerator();
            PackOutput output = generator.getPackOutput();
            ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
            CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

            ModBlockTagProvider blockTagProvider = new ModBlockTagProvider(output, lookupProvider, existingFileHelper);

            generator.addProvider(
                    event.includeClient(),
                    new ModBlockStateProvider(output, existingFileHelper)
            );

            generator.addProvider(
                    event.includeClient(),
                    new ModItemModelProvider(output, existingFileHelper)
            );
            generator.addProvider(
                    event.includeClient(),
                    new ModParticleDescriptionProvider(output, existingFileHelper)
            );
            generator.addProvider(
                    event.includeServer(),
                    new ModRecipeProvider(output, lookupProvider)
            );
            generator.addProvider(
                    event.includeServer(),
                    blockTagProvider
            );
            generator.addProvider(
                    event.includeServer(),
                    new ModItemTagProvider(output, lookupProvider, blockTagProvider.contentsGetter(), existingFileHelper)
            );

            event.getGenerator().addProvider(
                    event.includeServer(),
                    new ModLootTableProvider(
                            output,
                            Set.of(),
                            List.of(new LootTableProvider.SubProviderEntry(
                                    ModBlockLootSubProvider::new,
                                    LootContextParamSets.BLOCK
                            )),
                            lookupProvider
                    )
            );

        }

        @SubscribeEvent
        public static void registerParticleProviders(RegisterParticleProvidersEvent event) {
            ModParticles.registerParticleFactory(event);
        }
    }
}
