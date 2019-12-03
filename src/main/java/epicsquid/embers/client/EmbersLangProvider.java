package epicsquid.embers.client;

import epicsquid.embers.blocks.ModBlocks;
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
        super.addTranslations();
    }
}