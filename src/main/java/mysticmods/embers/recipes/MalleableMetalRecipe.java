package mysticmods.embers.recipes;

import mysticmods.embers.init.EmbersRecipeTypes;
import mysticmods.embers.registries.MalleableMetal;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class MalleableMetalRecipe implements Recipe<RecipeInput> {

    public final Ingredient input;
    public final ItemStack output;
    public final MalleableMetal malleableMetal;
    public final float experience;
    public final int processingTime;

    public MalleableMetalRecipe(Ingredient input, ItemStack output, MalleableMetal malleableMetal, float experience, int processingTime) {
        this.input = input;
        this.output = output;
        this.malleableMetal = malleableMetal;
        this.experience = experience;
        this.processingTime = processingTime;
    }

    @Override
    public boolean matches(RecipeInput input, @NotNull Level level) {
        return this.input.test(input.getItem(0));
    }

    @Override
    public @NotNull ItemStack assemble(@NotNull RecipeInput input, HolderLookup.@NotNull Provider registries) {
        return output.copy();
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return true;
    }

    @Override
    public @NotNull ItemStack getResultItem(HolderLookup.@NotNull Provider registries) {
        return output;
    }

    @Override
    public @NotNull RecipeSerializer<?> getSerializer() {
        return EmbersRecipeTypes.MALLEABLE_METAL_SERIALIZER.get();
    }

    @Override
    public @NotNull RecipeType<?> getType() {
        return EmbersRecipeTypes.MALLEABLE_METAL.get();
    }

    public Ingredient getInput() {
        return input;
    }

    public ItemStack getOutput() {
        return output;
    }

    public float getExperience() {
        return experience;
    }

    public int getProcessingTime() {
        return processingTime;
    }

    public MalleableMetal getMalleableMetal() {
        return malleableMetal;
    }
}
