package com.mystic.embers.core.machines.forge;

import com.mystic.embers.init.EmbersRecipes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraftforge.fluids.FluidStack;

public class SmelterRecipe implements Recipe<Container> {

	protected final Ingredient ingriedient;
	protected final FluidStack result;
	protected final ResourceLocation recipeId;

	public SmelterRecipe(Ingredient ingredients, FluidStack result, ResourceLocation recipeId) {
		this.ingriedient = ingredients;
		this.result = result;
		this.recipeId = recipeId;
	}

	@Override
	public boolean matches(Container pContainer, Level pLevel) {
		if (pContainer.getContainerSize() <= 0) {
			return false;
		}
		return this.ingriedient.test(pContainer.getItem(0));
	}

	@Override
	public ItemStack assemble(Container pContainer) {
		return null;
	}

	@Override
	public boolean canCraftInDimensions(int pWidth, int pHeight) {
		return true;
	}

	@Override
	public ItemStack getResultItem() {
		return null;
	}

	@Override
	public ResourceLocation getId() {
		return this.recipeId;
	}

	@Override
	public RecipeSerializer<?> getSerializer() {
		return new SmelterSerializer();
	}

	@Override
	public RecipeType<?> getType() {
		return EmbersRecipes.Types.SMELTER.get();
	}

	public FluidStack getResult() {
		return result;
	}
}
