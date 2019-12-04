package epicsquid.embers.client;

import epicsquid.embers.Embers;
import epicsquid.embers.setup.ModBlocks;
import epicsquid.mysticallib.client.data.DeferredItemModelProvider;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.ExistingFileHelper;

public class EmbersItemModelProvider extends DeferredItemModelProvider {

    public EmbersItemModelProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super("Embers Item Model Generator", generator, Embers.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        blockItem(ModBlocks.CAMINITE_BRICK);
        blockItem(ModBlocks.CHISELED_CAMINITE_BRICK);
        blockItem(ModBlocks.ARCAIC_BRICK);
        blockItem(ModBlocks.CINDERSTEEL_BLOCK);
        blockItem(ModBlocks.DAWNSTONE_BLOCK);
        blockItem(ModBlocks.EMBER_BLOCK);
        blockItem(ModBlocks.EMBER_ORE);
    }
}
