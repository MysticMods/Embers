package mysticmods.embers.datagen;

import mysticmods.embers.Embers;
import mysticmods.embers.init.ModItems;
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
        withExistingParent(ModItems.CAMINITE_BRICK_BLOCK_ITEM.getId().toString(), modLoc("block/caminite_bricks"));
        withExistingParent(ModItems.BRAZIER_BLOCK_ITEM.getId().toString(), modLoc("block/brazier"));
        withExistingParent(ModItems.CAMINITE_FORGE_BLOCK_ITEM.getId().toString(), modLoc("block/caminite_forge"));
        withExistingParent(ModItems.CAMINITE_SMELTER_BLOCK_ITEM.getId().toString(), modLoc("block/caminite_smelter"));
        withExistingParent(ModItems.COPPER_ANVIL_BLOCK_ITEM.getId().toString(), modLoc("block/copper_anvil"));
        withExistingParent(ModItems.CAMINITE_MOLD_BLOCK_ITEM.getId().toString(), modLoc("block/caminite_mold"));
        withExistingParent(ModItems.EMBER_CRYSTALLIZER_BLOCK_ITEM.getId().toString(), modLoc("block/ember_crystallizer"));
        withExistingParent(ModItems.BUDDING_EMBER_BLOCK_ITEM.getId().toString(), modLoc("block/budding_ember"));
        withExistingParent(ModItems.EMBER_CLUSTER_BLOCK_ITEM.getId().toString(), modLoc("block/ember_cluster"));

        //Stones
        basicItem(ModItems.CAMINITE_BLEND.get());
        basicItem(ModItems.CAMINITE_BRICK.get());

        //Metals
        basicItem(ModItems.ASHEN_STEEL_INGOT.get());
        basicItem(ModItems.CINDERSTEEL_INGOT.get());
        basicItem(ModItems.CINNABAR.get());
        basicItem(ModItems.COPPER_NUGGET.get());
        basicItem(ModItems.CRYSTAL_EMBER_SEED.get());
        basicItem(ModItems.DAWNSTONE_INGOT.get());
        basicItem(ModItems.DAWNSTONE_NUGGET.get());
        basicItem(ModItems.EMBER_CRYSTAL.get());
        basicItem(ModItems.EMBER_SHARD.get());
        basicItem(ModItems.QUICKSILVER.get());
        basicItem(ModItems.SMOLDERING_CRYSTAL_BLEND.get());
        basicItem(ModItems.VERMILLIONITE_CHUNK.get());

        //Misc
        basicItem(ModItems.HEATED_METAL.get());

        //Tools
        basicItem(ModItems.IRON_HAMMER.get());
    }
}