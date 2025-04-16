package mysticmods.embers.init;

import mysticmods.embers.Embers;
import mysticmods.embers.data.components.MalleableMetalDataComponent;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModDataComponents {

    public static final DeferredRegister.DataComponents REGISTRAR = DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE, Embers.MODID);

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<MalleableMetalDataComponent>> MALLEABLE_METAL = REGISTRAR.registerComponentType(
            "malleable_metal",
            builder -> builder
                    .persistent(MalleableMetalDataComponent.CODEC)
                    .networkSynchronized(MalleableMetalDataComponent.STREAM_CODEC)
    );


    public static void register(IEventBus modEventBus) {
        REGISTRAR.register(modEventBus);
    }

}
