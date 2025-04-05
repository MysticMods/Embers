package mysticmods.embers.recipes;

import mysticmods.embers.Embers;
import mysticmods.embers.registries.MalleableMetal;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.LinkedHashMap;
import java.util.Map;

public class MalleableMetalRecipeBuilder implements RecipeBuilder {

    protected final ItemStack result;
    protected final Ingredient input;
    protected final MalleableMetal malleableMetal;
    public final float experience;
    public final int processingTime;

    protected final Map<String, Criterion<?>> criteria = new LinkedHashMap<>();
    @Nullable
    protected String group;

    public MalleableMetalRecipeBuilder(Ingredient input, ItemStack result, MalleableMetal malleableMetal, float experience, int processingTime) {
        this.result = result;
        this.input = input;
        this.malleableMetal = malleableMetal;
        this.experience = experience;
        this.processingTime = processingTime;
    }

    @Override
    public @NotNull MalleableMetalRecipeBuilder unlockedBy(@NotNull String name, @NotNull Criterion<?> criterion) {
        this.criteria.put(name, criterion);
        return this;
    }

    @Override
    public @NotNull MalleableMetalRecipeBuilder group(@Nullable String group) {
        this.group = group;
        return this;
    }

    @Override
    public @NotNull Item getResult() {
        return this.result.getItem();
    }

    @Override
    public void save(RecipeOutput recipeOutput, @NotNull ResourceLocation id) {
        ResourceLocation moddedId = ResourceLocation.fromNamespaceAndPath(Embers.MODID, id.getPath());

        Advancement.Builder advancement = recipeOutput.advancement()
                .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(id))
                .rewards(AdvancementRewards.Builder.recipe(id))
                .requirements(AdvancementRequirements.Strategy.OR);
        this.criteria.forEach(advancement::addCriterion);

        MalleableMetalRecipe recipe = new MalleableMetalRecipe(input, result, malleableMetal, experience, processingTime);

        recipeOutput.accept(id, recipe, advancement.build(moddedId.withPrefix("recipes/")));
    }
}