package mysticmods.embers;

import com.mojang.logging.LogUtils;
import mysticmods.embers.datagen.*;
import mysticmods.embers.init.*;
import mysticmods.embers.particles.GlowParticleProvider;
import mysticmods.embers.registries.MalleableMetalRegistry;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.level.block.entity.BlockEntityType;
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
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NewRegistryEvent;
import org.slf4j.Logger;

import java.util.concurrent.CompletableFuture;

@Mod(Embers.MODID)
public class Embers
{
    public static final String MODID = "embers";
    public static final Logger LOGGER = LogUtils.getLogger();
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(MODID);
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MODID);
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, MODID);
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);


    public Embers(IEventBus modEventBus, ModContainer modContainer)
    {
        modEventBus.addListener(this::commonSetup);

        EmbersBlocks.init();
        EmbersItems.init();
        EmbersBlockEntities.init();
        EmbersCapabilities.init();
        EmbersTabs.init();
        EmbersParticles.init();
        EmbersTags.init();
        EmbersMalleableMetals.init();
        EmbersRecipeTypes.init();

        BLOCKS.register(modEventBus);
        ITEMS.register(modEventBus);
        BLOCK_ENTITY_TYPES.register(modEventBus);
        CREATIVE_MODE_TABS.register(modEventBus);
        EmbersParticles.PARTICLE_TYPES.register(modEventBus);
        EmbersMalleableMetals.MALLEABLE_METAL.register(modEventBus);


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
        static void registerRegistries(NewRegistryEvent event) {
            event.register(MalleableMetalRegistry.MALLEABLE_METAL_REGISTRY);
        }
    }

    @EventBusSubscriber(modid = MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {

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
