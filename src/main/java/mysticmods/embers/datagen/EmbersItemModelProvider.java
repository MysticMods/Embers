package mysticmods.embers.datagen;

import mysticmods.embers.Embers;
import mysticmods.embers.init.EmbersItems;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class EmbersItemModelProvider extends ItemModelProvider {
    public EmbersItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, Embers.MODID, existingFileHelper);

    }

    @Override
    protected void registerModels() {

        withExistingParent(EmbersItems.CAMINITE_BRICK_BLOCK_ITEM.getId().toString(), modLoc("block/caminite_bricks"));
        withExistingParent(EmbersItems.BRAZIER_BLOCK_ITEM.getId().toString(), modLoc("block/brazier"));
        withExistingParent(EmbersItems.CAMINITE_FORGE_BLOCK_ITEM.getId().toString(), modLoc("block/caminite_forge"));

        basicItem(EmbersItems.ASHEN_STEEL_INGOT.get());
        basicItem(EmbersItems.CAMINITE_BLEND.get());
        basicItem(EmbersItems.CAMINITE_BRICK.get());
        basicItem(EmbersItems.CINDERSTEEL_INGOT.get());
        basicItem(EmbersItems.CINNABAR.get());
        basicItem(EmbersItems.DAWNSTONE_INGOT.get());
        basicItem(EmbersItems.EMBER_CRYSTAL.get());
        basicItem(EmbersItems.QUICKSILVER.get());
        basicItem(EmbersItems.VERMILLIONITE_CHUNK.get());
    }
}