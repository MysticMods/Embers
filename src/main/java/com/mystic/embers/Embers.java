package com.mystic.embers;

import com.mystic.embers.init.*;
import com.mystic.embers.network.NetworkHandler;
import com.mystic.embers.setup.ModSetup;
import com.tterrag.registrate.Registrate;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.Lazy;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import javax.annotation.Nonnull;
import java.util.logging.LogManager;
import java.util.logging.Logger;

@Mod(Embers.MODID)
public class Embers {

    public static final String MODID = "embers";
    public static final Logger LOGGER = LogManager.getLogManager().getLogger(MODID);
    private static final Lazy<Registrate> REGISTRATE = Lazy.of(() -> Registrate.create(MODID));

    public static final CreativeModeTab ITEM_GROUP = new CreativeModeTab(Embers.MODID) {
        @Override
        @Nonnull
        public ItemStack makeIcon() {
            return new ItemStack(ModItems.EMBER_CRYSTAL.get());
        }
    };

    public Embers(){
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        bus.addListener(this::setup);
        bus.addListener(this::sendImc);
        //bus.addListener(this::registerCaps);
        MinecraftForge.EVENT_BUS.register(this);

        ModBlocks.classload();
        ModItems.classload();
        ModLang.classload();
        ModBlockEntity.classload();
        ModTags.classload();
        ModMenus.classload();
        ModFluids.classload();
        ModRecipes.Serializers.load();
        ModRecipes.Types.register(bus);
    }

    public void setup(FMLCommonSetupEvent event) {
        //MinecraftForge.EVENT_BUS.register(new EventManager());
        NetworkHandler.register();
    }

    public void sendImc(InterModEnqueueEvent event) {
        ModSetup.sendImc();
    }

    public static Registrate registrate() {
        return REGISTRATE.get();
    }

    @Mod.EventBusSubscriber(modid = MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientProxy {

    }



}
