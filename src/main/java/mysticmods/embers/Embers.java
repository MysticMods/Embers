package mysticmods.embers;

import com.mojang.logging.LogUtils;
import mysticmods.embers.datagen.*;
import mysticmods.embers.init.*;
import mysticmods.embers.machines.caminite_forge.CaminiteForgeScreen;
import mysticmods.embers.particles.GlowParticleProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
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
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import org.slf4j.Logger;

import java.util.concurrent.CompletableFuture;

@Mod(Embers.MODID)
public class Embers
{
    public static final String MODID = "embers";
    public static final Logger LOGGER = LogUtils.getLogger();


    public Embers(IEventBus modEventBus, ModContainer modContainer)
    {
        modEventBus.addListener(this::commonSetup);

        EmbersTags.init();

        EmbersBlocks.register(modEventBus);
        EmbersBlockEntities.register(modEventBus);
        EmbersItems.register(modEventBus);
        EmbersSerializers.register(modEventBus);
        EmbersMalleableMetals.register(modEventBus);
        EmbersRecipeTypes.register(modEventBus);
        EmbersParticles.register(modEventBus);
        EmbersTabs.register(modEventBus);
        EmbersMenuTypes.register(modEventBus);

        EmbersCapabilities.init();

        NeoForge.EVENT_BUS.register(this);

        modEventBus.addListener(this::addCreative);

        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
    }

    private void addCreative(BuildCreativeModeTabContentsEvent event)
    {

    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {

    }



    @EventBusSubscriber(modid = MODID, bus = EventBusSubscriber.Bus.MOD)
    public static class ServerModEvents
    {

        @SubscribeEvent
        private static void registerCapabilities(RegisterCapabilitiesEvent event) {
            EmbersCapabilities.register(event);
        }

        @SubscribeEvent
        private static void registerScreens(RegisterMenuScreensEvent event) {
            event.register(EmbersMenuTypes.CAMINITE_FORGE.get(), CaminiteForgeScreen::new);
        }
    }

    @EventBusSubscriber(modid = MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {
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

            EmbersBlockTagProvider blockTagProvider = new EmbersBlockTagProvider(output, lookupProvider, existingFileHelper);

            generator.addProvider(
                    event.includeClient(),
                    new EmbersBlockStateProvider(output, existingFileHelper)
            );

            generator.addProvider(
                    event.includeClient(),
                    new EmbersItemModelProvider(output, existingFileHelper)
            );
            generator.addProvider(
                    event.includeClient(),
                    new EmbersParticleDescriptionProvider(output, existingFileHelper)
            );
            generator.addProvider(
                    event.includeServer(),
                    new EmbersRecipeProvider(output, lookupProvider)
            );
            generator.addProvider(
                    event.includeServer(),
                    blockTagProvider
            );
            generator.addProvider(
                    event.includeServer(),
                    new EmbersItemTagProvider(output, lookupProvider, blockTagProvider.contentsGetter(), existingFileHelper)
            );
        }

        @SubscribeEvent
        public static void registerParticleProviders(RegisterParticleProvidersEvent event) {
            event.registerSpriteSet(EmbersParticles.PARTICLE_GLOW.get(), GlowParticleProvider::new);
        }
    }
}
