package epicsquid.embers.client;

import epicsquid.embers.setup.ModBlocks;
import epicsquid.mysticallib.client.data.DeferredLanguageProvider;
import epicsquid.mysticalworld.MysticalWorld;
import net.minecraft.data.DataGenerator;

public class EmbersLangProvider extends DeferredLanguageProvider {

    public EmbersLangProvider(DataGenerator gen) {
        super(gen, MysticalWorld.MODID);
    }

    @Override
    protected void addTranslations() {
        addBlock(ModBlocks.CAMINITE_BRICK);
        addBlock(ModBlocks.CHISELED_CAMINITE_BRICK);
        addBlock(ModBlocks.ARCAIC_BRICK);
        addBlock(ModBlocks.CINDERSTEEL_BLOCK);
        addBlock(ModBlocks.DAWNSTONE_BLOCK);
        addBlock(ModBlocks.EMBER_BLOCK);
        addBlock(ModBlocks.EMBER_ORE);

        //addBlock(ModBlocks.BRAZIER);
        super.addTranslations();
    }
}