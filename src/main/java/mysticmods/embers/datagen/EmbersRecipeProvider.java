package mysticmods.embers.datagen;

import mysticmods.embers.init.EmbersItems;
import mysticmods.embers.init.EmbersMalleableMetals;
import mysticmods.embers.recipes.MalleableMetalRecipeBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public class EmbersRecipeProvider extends RecipeProvider {

    // Construct the provider to run
    public EmbersRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider);
    }

    @Override
    protected void buildRecipes(@NotNull RecipeOutput recipeOutput) {
        //Caminite Blend
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, EmbersItems.CAMINITE_BLEND)
                .requires(Items.CLAY_BALL)
                .requires(Items.SAND)
                .unlockedBy("has_caminite_blend", has(EmbersItems.CAMINITE_BLEND))
                .unlockedBy("has_clay_ball", has(Items.CLAY_BALL))
                .unlockedBy("has_sand", has(Items.SAND))
                .save(recipeOutput);



        // ## Blocks ## //

        //Caminite Brick Block
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, EmbersItems.CAMINITE_BRICK_BLOCK_ITEM)
                .pattern("##")
                .pattern("##")
                .define('#', EmbersItems.CAMINITE_BRICK)
                .unlockedBy("has_caminite_brick", has(EmbersItems.CAMINITE_BRICK))
                .save(recipeOutput);

        //Brazier
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, EmbersItems.BRAZIER_BLOCK_ITEM)
                .pattern("CFC")
                .pattern("C#C")
                .define('#', EmbersItems.CAMINITE_BRICK_BLOCK_ITEM)
                .define('C', Items.COPPER_INGOT)
                .define('F', Items.CAMPFIRE)
                .unlockedBy("has_caminite_bricks", has(EmbersItems.CAMINITE_BRICK_BLOCK_ITEM))
                .unlockedBy("has_copper_ingot", has(Items.COPPER_INGOT))
                .unlockedBy("has_campfire", has(Items.CAMPFIRE))
                .save(recipeOutput);

        // ## Smelting ## //

        //Caminite Brick
        SimpleCookingRecipeBuilder.smelting(
                Ingredient.of(EmbersItems.CAMINITE_BLEND),
                RecipeCategory.MISC,
                EmbersItems.CAMINITE_BRICK,
                0.1f,
                200
        )
                .unlockedBy("has_caminite_blend", has(EmbersItems.CAMINITE_BLEND))
                .save(recipeOutput, "caminite_brick_smelting");


        // ## Malleable ## //
        new MalleableMetalRecipeBuilder(
                Ingredient.of(Items.IRON_ORE),
                new ItemStack(EmbersItems.HEATED_METAL.get()),
                EmbersMalleableMetals.MALLEABLE_IRON.get(),
                0.5f,
                200
        )
                .unlockedBy("has_iron_ore", has(Items.IRON_ORE))
                .save(recipeOutput, "malleable_iron");
    }

}