package mysticmods.embers.datagen;

import mysticmods.embers.Embers;
import mysticmods.embers.init.EmbersItems;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, Embers.MODID, existingFileHelper);

    }

    @Override
    protected void registerModels() {

        //Blocks
        withExistingParent(EmbersItems.CAMINITE_BRICK_BLOCK_ITEM.getId().toString(), modLoc("block/caminite_bricks"));
        withExistingParent(EmbersItems.BRAZIER_BLOCK_ITEM.getId().toString(), modLoc("block/brazier"));
        withExistingParent(EmbersItems.CAMINITE_FORGE_BLOCK_ITEM.getId().toString(), modLoc("block/caminite_forge"));
        withExistingParent(EmbersItems.CAMINITE_SMELTER_BLOCK_ITEM.getId().toString(), modLoc("block/caminite_smelter"));
        withExistingParent(EmbersItems.COPPER_ANVIL_BLOCK_ITEM.getId().toString(), modLoc("block/copper_anvil"));
        withExistingParent(EmbersItems.CAMINITE_MOLD_BLOCK_ITEM.getId().toString(), modLoc("block/caminite_mold"));
        withExistingParent(EmbersItems.EMBER_CRYSTALLIZER_BLOCK_ITEM.getId().toString(), modLoc("block/ember_crystallizer"));
        withExistingParent(EmbersItems.BUDDING_EMBER_BLOCK_ITEM.getId().toString(), modLoc("block/budding_ember"));
        withExistingParent(EmbersItems.EMBER_CLUSTER_BLOCK_ITEM.getId().toString(), modLoc("block/ember_cluster"));

        //Stones
        basicItem(EmbersItems.CAMINITE_BLEND.get());
        basicItem(EmbersItems.CAMINITE_BRICK.get());

        //Metals
        basicItem(EmbersItems.ASHEN_STEEL_INGOT.get());
        basicItem(EmbersItems.CINDERSTEEL_INGOT.get());
        basicItem(EmbersItems.CINNABAR.get());
        basicItem(EmbersItems.COPPER_NUGGET.get());
        basicItem(EmbersItems.CRYSTAL_EMBER_SEED.get());
        basicItem(EmbersItems.DAWNSTONE_INGOT.get());
        basicItem(EmbersItems.DAWNSTONE_NUGGET.get());
        basicItem(EmbersItems.EMBER_CRYSTAL.get());
        basicItem(EmbersItems.EMBER_SHARD.get());
        basicItem(EmbersItems.QUICKSILVER.get());
        basicItem(EmbersItems.SMOLDERING_CRYSTAL_BLEND.get());
        basicItem(EmbersItems.VERMILLIONITE_CHUNK.get());

        //Misc
        basicItem(EmbersItems.HEATED_METAL.get());

        //Tools
        basicItem(EmbersItems.IRON_HAMMER.get());
    }
}