package mysticmods.embers.init;

import mysticmods.embers.Embers;
import mysticmods.embers.registries.MalleableMetal;
import mysticmods.embers.registries.MalleableMetalRegistry;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class EmbersMalleableMetals {
    public static final DeferredRegister<MalleableMetal> MALLEABLE_METAL = DeferredRegister.create(MalleableMetalRegistry.MALLEABLE_METAL_REGISTRY, Embers.MODID);

    public static final Supplier<MalleableMetal> MALLEABLE_IRON = MALLEABLE_METAL.register("malleable_iron", () -> new MalleableMetal(null));

}
