package mysticmods.embers.datagen;

import mysticmods.embers.Embers;
import mysticmods.embers.init.EmbersBlocks;
import net.minecraft.core.Direction;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ConfiguredModel;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class ModBlockStateProvider extends BlockStateProvider {

    public ModBlockStateProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, Embers.MODID, existingFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        simpleBlock(EmbersBlocks.CAMINITE_BRICK.get());
        simpleBlock(EmbersBlocks.BUDDING_EMBER.get());

        getVariantBuilder(EmbersBlocks.EMBER_CLUSTER.get()).forAllStates(state ->
                ConfiguredModel.builder()
                    .modelFile(models().getExistingFile(modLoc("block/ember_cluster")))
                    .rotationX(getXRot(state.getValue(BlockStateProperties.FACING)))
                    .rotationY(getYRot(state.getValue(BlockStateProperties.FACING)))
                    .build());

        getVariantBuilder(EmbersBlocks.LARGE_EMBER_BUD.get()).forAllStates(state ->
                ConfiguredModel.builder()
                    .modelFile(models().getExistingFile(modLoc("block/large_ember_bud")))
                    .rotationX(getXRot(state.getValue(BlockStateProperties.FACING)))
                    .rotationY(getYRot(state.getValue(BlockStateProperties.FACING)))
                    .build());

        getVariantBuilder(EmbersBlocks.MEDIUM_EMBER_BUD.get()).forAllStates(state ->
                ConfiguredModel.builder()
                    .modelFile(models().getExistingFile(modLoc("block/medium_ember_bud")))
                    .rotationX(getXRot(state.getValue(BlockStateProperties.FACING)))
                    .rotationY(getYRot(state.getValue(BlockStateProperties.FACING)))
                    .build());

        getVariantBuilder(EmbersBlocks.SMALL_EMBER_BUD.get()).forAllStates(state ->
                ConfiguredModel.builder()
                    .modelFile(models().getExistingFile(modLoc("block/small_ember_bud")))
                    .rotationX(getXRot(state.getValue(BlockStateProperties.FACING)))
                    .rotationY(getYRot(state.getValue(BlockStateProperties.FACING)))
                    .build());

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

        getVariantBuilder(EmbersBlocks.CAMINITE_MOLD.get()).forAllStates(state ->
                ConfiguredModel.builder()
                        .modelFile(models().getExistingFile(modLoc("block/caminite_mold")))
                        .build()
        );

        getVariantBuilder(EmbersBlocks.EMBER_CRYSTALLIZER.get()).forAllStates(state ->
                ConfiguredModel.builder()
                        .modelFile(models().getExistingFile(modLoc("block/ember_crystallizer")))
                        .build()
        );
    }

    public int getYRot(Direction facing){
        int yRot = switch (facing) {
            case NORTH -> 0;
            case SOUTH -> 180;
            case WEST -> 270;
            case EAST -> 90;
            case UP, DOWN -> 0;
        };
        return yRot;
    }

    public int getXRot(Direction facing){
        int xRot = switch (facing) {
            case DOWN -> 180;
            case UP -> 0;
            default -> 90;
        };
        return xRot;
    }
}