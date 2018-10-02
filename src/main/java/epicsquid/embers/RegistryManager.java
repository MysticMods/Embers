package epicsquid.embers;

import javax.annotation.Nonnull;

import epicsquid.mysticallib.LibRegistry;
import epicsquid.mysticallib.event.RegisterContentEvent;
import epicsquid.mysticallib.event.RegisterModRecipesEvent;
import epicsquid.embers.init.ModBlocks;
import epicsquid.embers.init.ModEntities;
import epicsquid.embers.init.ModItems;
import epicsquid.embers.init.ModRecipes;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class RegistryManager {

  @SubscribeEvent
  public void init(@Nonnull RegisterContentEvent event) {
    LibRegistry.setActiveMod(Embers.MODID, Embers.CONTAINER);

    ModBlocks.registerBlocks(event);

    ModItems.registerItems(event);

    ModEntities.registerMobs();
    ModEntities.registerMobSpawn();

  }

  @SubscribeEvent
  public void initRecipes(@Nonnull RegisterModRecipesEvent event) {
    LibRegistry.setActiveMod(Embers.MODID, Embers.CONTAINER);

    ModRecipes.initRecipes(event);
  }

  @SubscribeEvent(priority = EventPriority.LOWEST)
  public void registerOredict(@Nonnull RegistryEvent.Register<Item> event) {
    LibRegistry.setActiveMod(Embers.MODID, Embers.CONTAINER);

    ModItems.registerOredict();
  }
}
