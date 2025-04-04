package mysticmods.embers.init;

import mysticmods.embers.Embers;
import mysticmods.embers.capabilities.emberemitter.IEmberEmitter;
import mysticmods.embers.capabilities.emberintensity.IEmberIntensity;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.capabilities.BlockCapability;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;

public class EmbersCapabilities {

    public static final BlockCapability<IEmberIntensity, Void> EMBER_INTENSITY =
            BlockCapability.createVoid(
                    ResourceLocation.fromNamespaceAndPath(Embers.MODID, "ember_intensity"),
                    IEmberIntensity.class);

    public static final BlockCapability<IEmberEmitter, Void> EMBER_EMITTER =
            BlockCapability.createVoid(
                    ResourceLocation.fromNamespaceAndPath(Embers.MODID, "ember_emitter"),
                    IEmberEmitter.class);




    public static void register(RegisterCapabilitiesEvent event) {
        //Brazier Ember Emitter
        event.registerBlockEntity(
                EmbersCapabilities.EMBER_EMITTER,
                EmbersBlockEntities.BRAZIER.get(),
                (be, o) -> be.getEmitter()
        );

        //Brazier ItemHandler
        event.registerBlockEntity(
                Capabilities.ItemHandler.BLOCK,
                EmbersBlockEntities.BRAZIER.get(),
                (myBlockEntity, side) -> myBlockEntity.getItemHandler()
        );
    }

    public static void init() {
    }
}
