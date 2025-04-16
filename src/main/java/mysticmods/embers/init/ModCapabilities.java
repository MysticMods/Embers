package mysticmods.embers.init;

import mysticmods.embers.Embers;
import mysticmods.embers.capabilities.emberemitter.IEmberEmitter;
import mysticmods.embers.capabilities.emberintensity.IEmberIntensity;
import mysticmods.embers.capabilities.heated_metal.HeatedMetalCap;
import mysticmods.embers.capabilities.heated_metal.IHeatedMetalCap;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.capabilities.BlockCapability;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.ItemCapability;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;

public class ModCapabilities {

    public static final BlockCapability<IEmberIntensity, Void> EMBER_INTENSITY =
            BlockCapability.createVoid(
                    ResourceLocation.fromNamespaceAndPath(Embers.MODID, "ember_intensity"),
                    IEmberIntensity.class);

    public static final BlockCapability<IEmberEmitter, Void> EMBER_EMITTER =
            BlockCapability.createVoid(
                    ResourceLocation.fromNamespaceAndPath(Embers.MODID, "ember_emitter"),
                    IEmberEmitter.class);

    public static final ItemCapability<IHeatedMetalCap, Void> HEATED_METAL =
            ItemCapability.createVoid(
                    ResourceLocation.fromNamespaceAndPath(Embers.MODID, "heated_metal"),
                    IHeatedMetalCap.class);




    public static void register(RegisterCapabilitiesEvent event) {
        //Brazier Ember Emitter
        event.registerBlockEntity(
                ModCapabilities.EMBER_EMITTER,
                ModBlockEntities.BRAZIER.get(),
                (be, o) -> be.getEmitter()
        );

        //Brazier ItemHandler
        event.registerBlockEntity(
                Capabilities.ItemHandler.BLOCK,
                ModBlockEntities.BRAZIER.get(),
                (myBlockEntity, side) -> myBlockEntity.getItemHandler()
        );

        //Caminite Forge Ember Intensity
        event.registerBlockEntity(
                ModCapabilities.EMBER_INTENSITY,
                ModBlockEntities.CAMINITE_FORGE.get(),
                (be, o) -> be.getEmberIntensity()
        );

        //Caminite Forge ItemHandler
        event.registerBlockEntity(
                Capabilities.ItemHandler.BLOCK,
                ModBlockEntities.CAMINITE_FORGE.get(),
                (myBlockEntity, side) -> myBlockEntity.getItemHandler()
        );

        //Caminite Mold ItemHandler
        event.registerBlockEntity(
                Capabilities.ItemHandler.BLOCK,
                ModBlockEntities.CAMINITE_MOLD.get(),
                (myBlockEntity, side) -> myBlockEntity.getItemHandler()
        );

        //Heated Metal Capability
        event.registerItem(
                ModCapabilities.HEATED_METAL,
                (itemStack, context) -> new HeatedMetalCap(0, 100, ItemStack.EMPTY),
                ModItems.HEATED_METAL.get()
        );
    }

    public static void init() {
    }
}
