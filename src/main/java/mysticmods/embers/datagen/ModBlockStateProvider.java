package mysticmods.embers.datagen;

import mysticmods.embers.Embers;
import mysticmods.embers.init.ModBlocks;
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
        simpleBlock(ModBlocks.CAMINITE_BRICK.get());
        simpleBlock(ModBlocks.BUDDING_EMBER.get());

        getVariantBuilder(ModBlocks.EMBER_CLUSTER.get()).forAllStates(state ->
                ConfiguredModel.builder()
                    .modelFile(models().getExistingFile(modLoc("block/ember_cluster")))
                    .rotationX(getXRot(state.getValue(BlockStateProperties.FACING)))
                    .rotationY(getYRot(state.getValue(BlockStateProperties.FACING)))
                    .build());

        getVariantBuilder(ModBlocks.LARGE_EMBER_BUD.get()).forAllStates(state ->
                ConfiguredModel.builder()
                    .modelFile(models().getExistingFile(modLoc("block/large_ember_bud")))
                    .rotationX(getXRot(state.getValue(BlockStateProperties.FACING)))
                    .rotationY(getYRot(state.getValue(BlockStateProperties.FACING)))
                    .build());

        getVariantBuilder(ModBlocks.MEDIUM_EMBER_BUD.get()).forAllStates(state ->
                ConfiguredModel.builder()
                    .modelFile(models().getExistingFile(modLoc("block/medium_ember_bud")))
                    .rotationX(getXRot(state.getValue(BlockStateProperties.FACING)))
                    .rotationY(getYRot(state.getValue(BlockStateProperties.FACING)))
                    .build());

        getVariantBuilder(ModBlocks.SMALL_EMBER_BUD.get()).forAllStates(state ->
                ConfiguredModel.builder()
                    .modelFile(models().getExistingFile(modLoc("block/small_ember_bud")))
                    .rotationX(getXRot(state.getValue(BlockStateProperties.FACING)))
                    .rotationY(getYRot(state.getValue(BlockStateProperties.FACING)))
                    .build());

        getVariantBuilder(ModBlocks.BRAZIER.get()).forAllStates(state ->
                ConfiguredModel.builder()
                        .modelFile(state.getValue(BlockStateProperties.LIT) ? models().getExistingFile(modLoc("block/brazier_on")) : models().getExistingFile(modLoc("block/brazier")))
                        .build()
        );

        getVariantBuilder(ModBlocks.CAMINITE_SMELTER.get()).forAllStates(state -> {
            var facing = state.getValue(BlockStateProperties.HORIZONTAL_FACING);
            return ConfiguredModel.builder()
                    .modelFile(models().getExistingFile(modLoc("block/caminite_smelter")))
                    .rotationY(facing.get2DDataValue() * 90)
                    .build();
        });

        getVariantBuilder(ModBlocks.CAMINITE_FORGE.get()).forAllStates(state ->
                ConfiguredModel.builder()
                        .modelFile(state.getValue(BlockStateProperties.LIT) ? models().getExistingFile(modLoc("block/caminite_forge")) : models().getExistingFile(modLoc("block/caminite_forge")))
                        .build()
        );

        getVariantBuilder(ModBlocks.CAMINITE_FORGE_COMPONENT.get()).forAllStates(state ->
                ConfiguredModel.builder()
                        .modelFile(models().getExistingFile(modLoc("block/caminite_forge_component")))
                        .build()
        );

        getVariantBuilder(ModBlocks.COPPER_ANVIL.get()).forAllStates(state ->
                ConfiguredModel.builder()
                        .modelFile(models().getExistingFile(modLoc("block/copper_anvil")))
                        .build()
        );

        getVariantBuilder(ModBlocks.CAMINITE_MOLD.get()).forAllStates(state ->
                ConfiguredModel.builder()
                        .modelFile(models().getExistingFile(modLoc("block/caminite_mold")))
                        .build()
        );

        getVariantBuilder(ModBlocks.EMBER_CRYSTALLIZER.get()).forAllStates(state ->
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