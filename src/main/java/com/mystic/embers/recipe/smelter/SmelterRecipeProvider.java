package com.mystic.embers.recipe.smelter;

import com.google.gson.JsonObject;
import com.mystic.embers.init.ModFluids;
import com.mystic.embers.init.ModRecipes;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public class SmelterRecipeProvider extends RecipeProvider {
    public SmelterRecipeProvider(DataGenerator pGenerator) {
        super(pGenerator);
    }

    @Override
    protected void buildCraftingRecipes(Consumer<FinishedRecipe> pFinishedRecipeConsumer) {
        super.buildCraftingRecipes(pFinishedRecipeConsumer);
    }

    public static class SmelterFinishedRecipe implements FinishedRecipe{

        private final ResourceLocation id;
        private final Ingredient input;
        private final FluidStack fluidStack;

        public SmelterFinishedRecipe(ResourceLocation id, Ingredient input, FluidStack fluidStack) {
            this.id = id;
            this.input = input;
            this.fluidStack = fluidStack;
        }

        @Override
        public void serializeRecipeData(JsonObject pJson) {
            pJson.add("input", this.input.toJson());

            JsonObject jsonobject = new JsonObject();
            jsonobject.addProperty("fluid", ForgeRegistries.FLUIDS.getKey(this.fluidStack.getFluid()).toString());
            jsonobject.addProperty("amount", this.fluidStack.getAmount());
            pJson.add("result", jsonobject);
        }

        @Override
        public ResourceLocation getId() {
            return id;
        }

        @Override
        public RecipeSerializer<?> getType() {
            return ModRecipes.Serializers.SMELTER.get();
        }

        @Nullable
        @Override
        public JsonObject serializeAdvancement() {
            return null;
        }

        @Nullable
        @Override
        public ResourceLocation getAdvancementId() {
            return null;
        }
    }
}
