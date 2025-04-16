package mysticmods.embers.init;

import mysticmods.embers.Embers;
import mysticmods.embers.api.registries.EmbersRegistries;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.registries.NewRegistryEvent;

@EventBusSubscriber(modid = Embers.MODID, bus = EventBusSubscriber.Bus.MOD)
public class ModRegistries {
    @SubscribeEvent
    public static void onNewRegistries(NewRegistryEvent event) {
        event.register(EmbersRegistries.MALLEABLE_METALS);
    }
}