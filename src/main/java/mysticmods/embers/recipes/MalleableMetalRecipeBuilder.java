package mysticmods.embers.recipes;

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
    public MalleableMetalRecipeBuilder unlockedBy(String name, Criterion<?> criterion) {
        this.criteria.put(name, criterion);
        return this;
    }

    @Override
    public MalleableMetalRecipeBuilder group(@Nullable String group) {
        this.group = group;
        return this;
    }

    @Override
    public Item getResult() {
        return this.result.getItem();
    }

    @Override
    public void save(RecipeOutput recipeOutput, ResourceLocation id) {
        Advancement.Builder advancement = recipeOutput.advancement()
                .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(id))
                .rewards(AdvancementRewards.Builder.recipe(id))
                .requirements(AdvancementRequirements.Strategy.OR);
        this.criteria.forEach(advancement::addCriterion);

        MalleableMetalRecipe recipe = new MalleableMetalRecipe(input, result, malleableMetal, experience, processingTime);

        recipeOutput.accept(id, recipe, advancement.build(id.withPrefix("recipes/")));
    }
}