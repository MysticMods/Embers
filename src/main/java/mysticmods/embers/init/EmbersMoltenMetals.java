package mysticmods.embers.init;

import mysticmods.embers.Embers;
import mysticmods.embers.core.molten_metal.MoltenMetal;
import mysticmods.embers.core.molten_metal.MoltenMetalRegistry;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class EmbersMoltenMetals {
    public static final DeferredRegister<MoltenMetal> MOLTEN_METALS = DeferredRegister.create(MoltenMetalRegistry.RESOURCE_KEY, Embers.MOD_ID);

    public static final RegistryObject<MoltenMetal> MOLTEN_IRON = MOLTEN_METALS.register("molten_iron", () -> new MoltenMetal(new ResourceLocation(Embers.MOD_ID, "molten_iron")));
}
