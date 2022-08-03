package com.mystic.embers;

import com.mystic.embers.recipe.smelter.SmelterRecipeProvider;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;

@Mod.EventBusSubscriber(modid = Embers.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class EventManager {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        if (event.includeServer()) {
            generator.addProvider(new SmelterRecipeProvider(generator));
        }
    }
}
