package mysticmods.embers.datagen;

import mysticmods.embers.Embers;
import mysticmods.embers.init.EmbersBlocks;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ConfiguredModel;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class EmbersBlockStateProvider extends BlockStateProvider {

    public EmbersBlockStateProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, Embers.MODID, existingFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        simpleBlock(EmbersBlocks.CAMINITE_BRICK.get());

        getVariantBuilder(EmbersBlocks.BRAZIER.get()).forAllStates(state ->
                ConfiguredModel.builder()
                        .modelFile(state.getValue(BlockStateProperties.LIT) ? models().getExistingFile(modLoc("block/brazier_on")) : models().getExistingFile(modLoc("block/brazier")))
                        .build()
        );

        getVariantBuilder(EmbersBlocks.CAMINITE_FORGE.get()).forAllStates(state ->
                ConfiguredModel.builder()
                        .modelFile(state.getValue(BlockStateProperties.LIT) ? models().getExistingFile(modLoc("block/caminite_forge")) : models().getExistingFile(modLoc("block/caminite_forge")))
                        .build()
        );

        getVariantBuilder(EmbersBlocks.CAMINITE_FORGE_COMPONENT.get()).forAllStates(state ->
                ConfiguredModel.builder()
                        .modelFile(models().getExistingFile(modLoc("block/caminite_forge_component")))
                        .build()
        );

        getVariantBuilder(EmbersBlocks.COPPER_ANVIL.get()).forAllStates(state ->
                ConfiguredModel.builder()
                        .modelFile(models().getExistingFile(modLoc("block/copper_anvil")))
                        .build()
        );
    }
}