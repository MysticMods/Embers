package mysticmods.embers.datagen;

import mysticmods.embers.init.ModItems;
import mysticmods.embers.init.ModMalleableMetals;
import mysticmods.embers.init.ModTags;
import mysticmods.embers.recipes.alloy.AlloyRecipeBuilder;
import mysticmods.embers.recipes.crystallizer.CrystallizerRecipeBuilder;
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

public class ModRecipeProvider extends RecipeProvider {

    // Construct the provider to run
    public ModRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider);
    }

    @Override
    protected void buildRecipes(@NotNull RecipeOutput recipeOutput) {
        //Items
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.IRON_HAMMER)
                .pattern("III")
                .pattern("ISI")
                .pattern(" S ")
                .define('I', Ingredient.of(Items.IRON_INGOT))
                .define('S', Ingredient.of(Items.STICK))
                .unlockedBy("has_stick", has(Items.STICK))
                .unlockedBy("has_iron_ingot", has(Items.IRON_INGOT))
                .save(recipeOutput);

        //Caminite Blend
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.CAMINITE_BLEND)
                .requires(Items.CLAY_BALL)
                .requires(Items.SAND)
                .unlockedBy("has_caminite_blend", has(ModItems.CAMINITE_BLEND))
                .unlockedBy("has_clay_ball", has(Items.CLAY_BALL))
                .unlockedBy("has_sand", has(Items.SAND))
                .save(recipeOutput);

        //Smoldering Crystal Blend
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.SMOLDERING_CRYSTAL_BLEND)
                .requires(Items.AMETHYST_SHARD, 3)
                .requires(ModItems.EMBER_SHARD)
                .unlockedBy("has_amethyst_shard", has(Items.AMETHYST_SHARD))
                .unlockedBy("had_ember_shard", has(ModItems.EMBER_SHARD))
                .save(recipeOutput);

        //Metals
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Items.COPPER_INGOT)
                .pattern("###")
                .pattern("###")
                .pattern("###")
                .define('#', Ingredient.of(ModTags.COPPER_NUGGETS_TAG))
                .unlockedBy("has_copper_nugget", has(ModTags.COPPER_NUGGETS_TAG))
                .unlockedBy("has_copper_ingot", has(ModTags.COPPER_INGOTS_TAG))
                .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.COPPER_NUGGET, 9)
                .requires(Items.COPPER_INGOT)
                .unlockedBy("has_copper_nugget", has(ModTags.COPPER_NUGGETS_TAG))
                .unlockedBy("has_copper_ingot", has(ModTags.COPPER_INGOTS_TAG))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.DAWNSTONE_INGOT)
                .pattern("###")
                .pattern("###")
                .pattern("###")
                .define('#', Ingredient.of(ModItems.DAWNSTONE_NUGGET))
                .unlockedBy("has_dawnstone_nugget", has(ModItems.DAWNSTONE_NUGGET))
                .unlockedBy("has_dawnstone_ingot", has(ModItems.DAWNSTONE_INGOT))
                .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.DAWNSTONE_NUGGET, 9)
                .requires(ModItems.DAWNSTONE_INGOT)
                .unlockedBy("has_dawnstone_nugget", has(ModItems.DAWNSTONE_NUGGET))
                .unlockedBy("has_dawnstone_ingot", has(ModItems.DAWNSTONE_INGOT))
                .save(recipeOutput);

        // ## Blocks ## //

        //Caminite Brick Block
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModItems.CAMINITE_BRICK_BLOCK_ITEM)
                .pattern("##")
                .pattern("##")
                .define('#', ModItems.CAMINITE_BRICK)
                .unlockedBy("has_caminite_brick", has(ModItems.CAMINITE_BRICK))
                .save(recipeOutput);

        //Caminite Mold
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.CAMINITE_MOLD_BLOCK_ITEM)
                .pattern("# #")
                .pattern("# #")
                .pattern("BBB")
                .define('B', ModItems.CAMINITE_BRICK)
                .define('#', ModItems.CAMINITE_BLEND)
                .unlockedBy("has_caminite_brick", has(ModItems.CAMINITE_BRICK))
                .unlockedBy("has_caminite_blend", has(ModItems.CAMINITE_BLEND))
                .save(recipeOutput);

        //Brazier
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.BRAZIER_BLOCK_ITEM)
                .pattern("CFC")
                .pattern("C#C")
                .define('#', ModItems.CAMINITE_BRICK_BLOCK_ITEM)
                .define('C', Items.COPPER_INGOT)
                .define('F', Items.CAMPFIRE)
                .unlockedBy("has_caminite_bricks", has(ModItems.CAMINITE_BRICK_BLOCK_ITEM))
                .unlockedBy("has_copper_ingot", has(Items.COPPER_INGOT))
                .unlockedBy("has_campfire", has(Items.CAMPFIRE))
                .save(recipeOutput);

        //Copper Anvil
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.COPPER_ANVIL_BLOCK_ITEM)
                .pattern("OOO")
                .pattern(" # ")
                .pattern("###")
                .define('#', ModItems.CAMINITE_BRICK_BLOCK_ITEM)
                .define('O', Items.COPPER_BLOCK)
                .unlockedBy("has_caminite_bricks", has(ModItems.CAMINITE_BRICK_BLOCK_ITEM))
                .unlockedBy("has_copper_block", has(Items.COPPER_BLOCK))
                .save(recipeOutput);

        // ## Smelting ## //

        //Caminite Brick
        SimpleCookingRecipeBuilder.smelting(
                Ingredient.of(ModItems.CAMINITE_BLEND),
                RecipeCategory.MISC,
                ModItems.CAMINITE_BRICK,
                0.1f,
                200
        )
                .unlockedBy("has_caminite_blend", has(ModItems.CAMINITE_BLEND))
                .save(recipeOutput, "caminite_brick_smelting");


        // ## Malleable ## //
        new MalleableMetalRecipeBuilder(
                ModMalleableMetals.MALLEABLE_IRON.get(),
                0.5f,
                200
        )
                .unlockedBy("has_iron_ore", has(Items.IRON_ORE))
                .unlockedBy("has_iron_deepslate_ore", has(Items.DEEPSLATE_IRON_ORE))
                .unlockedBy("has_iron_nugget", has(ModTags.IRON_NUGGETS_TAG))
                .unlockedBy("has_iron_ingot", has(ModTags.IRON_INGOTS_TAG))
                .save(recipeOutput, "malleable_iron");

        new MalleableMetalRecipeBuilder(
                ModMalleableMetals.MALLEABLE_COPPER.get(),
                0.5f,
                200
        )
                .unlockedBy("has_copper_ore", has(Items.COPPER_ORE))
                .unlockedBy("has_copper_deepslate_ore", has(Items.DEEPSLATE_COPPER_ORE))
                .unlockedBy("has_copper_nugget", has(ModTags.COPPER_NUGGETS_TAG))
                .unlockedBy("has_copper_ingot", has(ModTags.COPPER_INGOTS_TAG))
                .save(recipeOutput, "malleable_copper");

        new MalleableMetalRecipeBuilder(
                ModMalleableMetals.MALLEABLE_GOLD.get(),
                0.5f,
                200
        )
                .unlockedBy("has_gold_ore", has(Items.GOLD_ORE))
                .unlockedBy("has_gold_deepslate_ore", has(Items.DEEPSLATE_GOLD_ORE))
                .unlockedBy("has_gold_nugget", has(ModTags.GOLD_NUGGETS_TAG))
                .unlockedBy("has_gold_ingot", has(ModTags.GOLD_INGOTS_TAG))
                .save(recipeOutput, "malleable_gold");

        // ## Alloys ## //
        new AlloyRecipeBuilder(
                Ingredient.of(ModTags.COPPER_INGOTS_TAG),
                Ingredient.of(ModTags.GOLD_INGOTS_TAG),
                new ItemStack(ModItems.DAWNSTONE_INGOT.get(), 2)
        )
                .unlockedBy("has_copper_ingot", has(ModTags.COPPER_INGOTS_TAG))
                .unlockedBy("has_gold_ingot", has(ModTags.GOLD_INGOTS_TAG))
                .unlockedBy("has_dawnstone_ingot", has(ModItems.DAWNSTONE_INGOT))
                .save(recipeOutput, "dawnstone_alloy");

        // ## Machine Molds ## //
        buildMoldRecipes(recipeOutput);


        // ## Crystallizer Recipes ## //
        new CrystallizerRecipeBuilder(
                Ingredient.of(ModItems.SMOLDERING_CRYSTAL_BLEND),
                new ItemStack(ModItems.CRYSTAL_EMBER_SEED.get())
        )
                .unlockedBy("has_smoldering_crystal_blend", has(ModItems.SMOLDERING_CRYSTAL_BLEND))
                .unlockedBy("has_crystal_ember_seed", has(ModItems.CRYSTAL_EMBER_SEED.get()))
                .save(recipeOutput, "crystal_ember_seed");
    }

    public void buildMoldRecipes(RecipeOutput recipeOutput) {
        //Caminite Forge
        new MoldRecipeBuilder(
                List.of(Ingredient.of( Items.COPPER_INGOT),
                        Ingredient.of(Items.COPPER_INGOT),
                        Ingredient.of(Items.COPPER_INGOT),
                        Ingredient.of(Items.COPPER_INGOT),
                        Ingredient.of(Items.FURNACE)
                ),
                new ItemStack(ModItems.CAMINITE_FORGE_BLOCK_ITEM.get())
        )
                .unlockedBy("has_copper_ingot", has(ModTags.COPPER_INGOTS_TAG))
                .unlockedBy("has_furnace", has(Items.FURNACE))
                .save(recipeOutput, "caminite_forge");

        //Ember Crystallizer
        new MoldRecipeBuilder(
                List.of(Ingredient.of(ModItems.EMBER_SHARD),
                        Ingredient.of(ModItems.CAMINITE_BRICK),
                        Ingredient.of(ModItems.CAMINITE_BRICK),
                        Ingredient.of(Items.GLASS)
                ),
                new ItemStack(ModItems.EMBER_CRYSTALLIZER_BLOCK_ITEM.get())
        )
                .unlockedBy("has_caminite_brick", has(ModItems.CAMINITE_BRICK))
                .unlockedBy("has_glass", has(Items.GLASS))
                .unlockedBy("has_ember_shard", has(ModItems.EMBER_SHARD))
                .save(recipeOutput, "ember_crystallizer");

        //Caminite Smelter
        new MoldRecipeBuilder(
                List.of(Ingredient.of( Items.COPPER_INGOT),
                        Ingredient.of(Items.COPPER_INGOT),
                        Ingredient.of(Items.COPPER_INGOT),
                        Ingredient.of(Items.FURNACE)
                ),
                new ItemStack(ModItems.CAMINITE_SMELTER_BLOCK_ITEM.get())
        )
                .unlockedBy("has_copper_ingot", has(ModTags.COPPER_INGOTS_TAG))
                .unlockedBy("has_furnace", has(Items.FURNACE))
                .save(recipeOutput, "caminite_smelter");
    }

}