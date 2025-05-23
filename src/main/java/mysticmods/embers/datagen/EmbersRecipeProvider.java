package mysticmods.embers.datagen;

import mysticmods.embers.init.EmbersItems;
import mysticmods.embers.init.EmbersMalleableMetals;
import mysticmods.embers.init.EmbersTags;
import mysticmods.embers.recipes.alloy.AlloyRecipeBuilder;
import mysticmods.embers.recipes.malleable_metal.MalleableMetalRecipeBuilder;
import mysticmods.embers.recipes.mold.MoldRecipeBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.NotNull;

import java.util.List;
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

        //Smoldering Crystal Blend
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, EmbersItems.SMOLDERING_CRYSTAL_BLEND)
                .requires(Items.AMETHYST_SHARD, 3)
                .requires(EmbersItems.EMBER_SHARD)
                .unlockedBy("has_amethyst_shard", has(Items.AMETHYST_SHARD))
                .unlockedBy("had_ember_shard", has(EmbersItems.EMBER_SHARD))
                .save(recipeOutput);

        //Metals
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Items.COPPER_INGOT)
                .pattern("###")
                .pattern("###")
                .pattern("###")
                .define('#', Ingredient.of(EmbersTags.COPPER_NUGGETS_TAG))
                .unlockedBy("has_copper_nugget", has(EmbersTags.COPPER_NUGGETS_TAG))
                .unlockedBy("has_copper_ingot", has(EmbersTags.COPPER_INGOTS_TAG))
                .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, EmbersItems.COPPER_NUGGET, 9)
                .requires(Items.COPPER_INGOT)
                .unlockedBy("has_copper_nugget", has(EmbersTags.COPPER_NUGGETS_TAG))
                .unlockedBy("has_copper_ingot", has(EmbersTags.COPPER_INGOTS_TAG))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, EmbersItems.DAWNSTONE_INGOT)
                .pattern("###")
                .pattern("###")
                .pattern("###")
                .define('#', Ingredient.of(EmbersItems.DAWNSTONE_NUGGET))
                .unlockedBy("has_dawnstone_nugget", has(EmbersItems.DAWNSTONE_NUGGET))
                .unlockedBy("has_dawnstone_ingot", has(EmbersItems.DAWNSTONE_INGOT))
                .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, EmbersItems.DAWNSTONE_NUGGET, 9)
                .requires(EmbersItems.DAWNSTONE_INGOT)
                .unlockedBy("has_dawnstone_nugget", has(EmbersItems.DAWNSTONE_NUGGET))
                .unlockedBy("has_dawnstone_ingot", has(EmbersItems.DAWNSTONE_INGOT))
                .save(recipeOutput);

        // ## Blocks ## //

        //Caminite Brick Block
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, EmbersItems.CAMINITE_BRICK_BLOCK_ITEM)
                .pattern("##")
                .pattern("##")
                .define('#', EmbersItems.CAMINITE_BRICK)
                .unlockedBy("has_caminite_brick", has(EmbersItems.CAMINITE_BRICK))
                .save(recipeOutput);

        //Caminite Mold
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, EmbersItems.CAMINITE_MOLD_BLOCK_ITEM)
                .pattern("# #")
                .pattern("# #")
                .pattern("BBB")
                .define('B', EmbersItems.CAMINITE_BRICK)
                .define('#', EmbersItems.CAMINITE_BLEND)
                .unlockedBy("has_caminite_brick", has(EmbersItems.CAMINITE_BRICK))
                .unlockedBy("has_caminite_blend", has(EmbersItems.CAMINITE_BLEND))
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

        //Copper Anvil
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, EmbersItems.COPPER_ANVIL_BLOCK_ITEM)
                .pattern("OOO")
                .pattern(" # ")
                .pattern("###")
                .define('#', EmbersItems.CAMINITE_BRICK_BLOCK_ITEM)
                .define('O', Items.COPPER_BLOCK)
                .unlockedBy("has_caminite_bricks", has(EmbersItems.CAMINITE_BRICK_BLOCK_ITEM))
                .unlockedBy("has_copper_block", has(Items.COPPER_BLOCK))
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
                EmbersMalleableMetals.MALLEABLE_IRON.get(),
                0.5f,
                200
        )
                .unlockedBy("has_iron_ore", has(Items.IRON_ORE))
                .unlockedBy("has_iron_deepslate_ore", has(Items.DEEPSLATE_IRON_ORE))
                .unlockedBy("has_iron_nugget", has(EmbersTags.IRON_NUGGETS_TAG))
                .unlockedBy("has_iron_ingot", has(EmbersTags.IRON_INGOTS_TAG))
                .save(recipeOutput, "malleable_iron");

        new MalleableMetalRecipeBuilder(
                EmbersMalleableMetals.MALLEABLE_COPPER.get(),
                0.5f,
                200
        )
                .unlockedBy("has_copper_ore", has(Items.COPPER_ORE))
                .unlockedBy("has_copper_deepslate_ore", has(Items.DEEPSLATE_COPPER_ORE))
                .unlockedBy("has_copper_nugget", has(EmbersTags.COPPER_NUGGETS_TAG))
                .unlockedBy("has_copper_ingot", has(EmbersTags.COPPER_INGOTS_TAG))
                .save(recipeOutput, "malleable_copper");

        new MalleableMetalRecipeBuilder(
                EmbersMalleableMetals.MALLEABLE_GOLD.get(),
                0.5f,
                200
        )
                .unlockedBy("has_gold_ore", has(Items.GOLD_ORE))
                .unlockedBy("has_gold_deepslate_ore", has(Items.DEEPSLATE_GOLD_ORE))
                .unlockedBy("has_gold_nugget", has(EmbersTags.GOLD_NUGGETS_TAG))
                .unlockedBy("has_gold_ingot", has(EmbersTags.GOLD_INGOTS_TAG))
                .save(recipeOutput, "malleable_gold");

        // ## Alloys ## //
        new AlloyRecipeBuilder(
                Ingredient.of(EmbersTags.COPPER_INGOTS_TAG),
                Ingredient.of(EmbersTags.GOLD_INGOTS_TAG),
                new ItemStack(EmbersItems.DAWNSTONE_INGOT.get(), 2)
        )
                .unlockedBy("has_copper_ingot", has(EmbersTags.COPPER_INGOTS_TAG))
                .unlockedBy("has_gold_ingot", has(EmbersTags.GOLD_INGOTS_TAG))
                .unlockedBy("has_dawnstone_ingot", has(EmbersItems.DAWNSTONE_INGOT))
                .save(recipeOutput, "dawnstone_alloy");

        // ## Machine Molds ## //

        //Caminite Smelter
        new MoldRecipeBuilder(
                List.of(Ingredient.of( Items.COPPER_INGOT),
                        Ingredient.of(Items.COPPER_INGOT),
                        Ingredient.of(Items.COPPER_INGOT),
                        Ingredient.of(Items.FURNACE)
                ),
                new ItemStack(EmbersItems.CAMINITE_FORGE_BLOCK_ITEM.get())
        )
                .unlockedBy("has_copper_ingot", has(EmbersTags.COPPER_INGOTS_TAG))
                .unlockedBy("has_furnace", has(Items.FURNACE))
                .save(recipeOutput, "caminite_smelter");

        //Ember Crystallizer
        new MoldRecipeBuilder(
                List.of(Ingredient.of(EmbersItems.EMBER_SHARD),
                        Ingredient.of(EmbersItems.CAMINITE_BRICK),
                        Ingredient.of(EmbersItems.CAMINITE_BRICK),
                        Ingredient.of(Items.GLASS)
                ),
                new ItemStack(EmbersItems.EMBER_CRYSTALLIZER_BLOCK_ITEM.get())
        )
                .unlockedBy("has_caminite_brick", has(EmbersItems.CAMINITE_BRICK))
                .unlockedBy("has_glass", has(Items.GLASS))
                .unlockedBy("has_ember_shard", has(EmbersItems.EMBER_SHARD))
                .save(recipeOutput, "ember_crystallizer");
    }

}