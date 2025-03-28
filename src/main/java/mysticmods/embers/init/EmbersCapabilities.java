package mysticmods.embers.init;

import mysticmods.embers.Embers;
import mysticmods.embers.core.capabilities.emberintensity.IEmberIntensity;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.capabilities.BlockCapability;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;

public class EmbersCapabilities {

    public static final BlockCapability<IEmberIntensity, Void> EMBER_INTENSITY =
            BlockCapability.createVoid(
                    ResourceLocation.fromNamespaceAndPath(Embers.MODID, "ember_intensity"),
                    IEmberIntensity.class);


    public static void register(RegisterCapabilitiesEvent event) {
//        event.registerBlockEntity(
//                EmbersCapabilities.EMBER_INTENSITY,
//                EmbersBlockEntities.BRAZIER,
//                (be) -> be.getCapability(EmbersCapabilities.EMBER_INTENSITY),
//        );
    }

    public static void init() {
    }
}
