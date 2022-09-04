package mysticmods.embers.core.molten_metal;

import mysticmods.embers.Embers;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.NewRegistryEvent;
import net.minecraftforge.registries.RegistryBuilder;

import java.util.function.Supplier;

@Mod.EventBusSubscriber(modid = Embers.MOD_ID, bus= Mod.EventBusSubscriber.Bus.MOD)
public class MoltenMetalRegistry {
    private static final ResourceLocation NAME = new ResourceLocation(Embers.MOD_ID, "molten_metal");
    private static final ResourceKey<Registry<MoltenMetal>> RESOURCE_KEY = ResourceKey.createRegistryKey(NAME);
    private static final DeferredRegister<MoltenMetal> DEFERRED_REGISTRY = DeferredRegister.create(RESOURCE_KEY, RESOURCE_KEY.location().getNamespace());
    private static final RegistryBuilder<MoltenMetal> MOLTEN_METAL = new RegistryBuilder<MoltenMetal>().setName(NAME);
    public static final Supplier<IForgeRegistry<MoltenMetal>> REGISTRY = DEFERRED_REGISTRY.makeRegistry(() -> MOLTEN_METAL);


    @SubscribeEvent
    static void onRegistryCreate(final NewRegistryEvent event) {
        event.create(MOLTEN_METAL);
    }

}
