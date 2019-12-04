package epicsquid.embers.client;

import epicsquid.embers.Embers;
import epicsquid.embers.setup.ModBlocks;
import epicsquid.mysticallib.client.data.DeferredBlockStateProvider;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.ExistingFileHelper;

public class EmbersBlockstateProvider extends DeferredBlockStateProvider {

    public EmbersBlockstateProvider(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super("Embers Blockstate and Block Model provider", gen, Embers.MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        simpleBlock(ModBlocks.CAMINITE_BRICK);
    }
}