package mysticmods.embers.client;

import mysticmods.embers.init.EmbersItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.concurrent.CompletableFuture;

public class EmbersRecipeProvider extends RecipeProvider {

    // Construct the provider to run
    protected EmbersRecipeProvider(HolderLookup.Provider provider, RecipeOutput output) {
        super(provider, output);
    }

    @Override
    protected void buildRecipes() {
        //Caminite Blend
        ShapelessRecipeBuilder.shapeless(this.registries.lookupOrThrow(Registries.ITEM), RecipeCategory.MISC, EmbersItems.CAMINITE_BLEND)
                .requires(Items.CLAY_BALL)
                .requires(Items.SAND)
                .unlockedBy("has_caminite_blend", has(EmbersItems.CAMINITE_BLEND))
                .unlockedBy("has_clay_ball", has(Items.CLAY_BALL))
                .unlockedBy("has_sand", has(Items.SAND))
                .save(this.output);



        // ## Blocks ## //

        //Caminite Brick Block
        ShapedRecipeBuilder.shaped(this.registries.lookupOrThrow(Registries.ITEM), RecipeCategory.BUILDING_BLOCKS, EmbersItems.CAMINITE_BRICK_BLOCK_ITEM)
                .pattern("##")
                .pattern("##")
                .define('#', EmbersItems.CAMINITE_BRICK)
                .unlockedBy("has_caminite_brick", has(EmbersItems.CAMINITE_BRICK))
                .save(this.output);

        //Brazier
        ShapedRecipeBuilder.shaped(this.registries.lookupOrThrow(Registries.ITEM), RecipeCategory.MISC, EmbersItems.BRAZIER_BLOCK_ITEM)
                .pattern("CFC")
                .pattern("C#C")
                .define('#', EmbersItems.CAMINITE_BRICK_BLOCK_ITEM)
                .define('C', Items.COPPER_INGOT)
                .define('F', Items.CAMPFIRE)
                .unlockedBy("has_caminite_bricks", has(EmbersItems.CAMINITE_BRICK_BLOCK_ITEM))
                .unlockedBy("has_copper_ingot", has(Items.COPPER_INGOT))
                .unlockedBy("has_campfire", has(Items.CAMPFIRE))
                .save(this.output);

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
                .save(this.output, "caminite_brick_smelting");

    }

    // The runner to add to the data generator
    public static class Runner extends RecipeProvider.Runner {
        public Runner(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
            super(output, lookupProvider);
        }

        @Override
        protected RecipeProvider createRecipeProvider(HolderLookup.Provider provider, RecipeOutput output) {
            return new EmbersRecipeProvider(provider, output);
        }

        @Override
        public String getName() {
            return "embers_recipe_provider";
        }
    }
}