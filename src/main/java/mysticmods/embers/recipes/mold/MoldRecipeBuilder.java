package mysticmods.embers.recipes.mold;

import mysticmods.embers.Embers;
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
import org.jetbrains.annotations.Nullable;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class MoldRecipeBuilder implements RecipeBuilder {

    private final List<Ingredient> inputIngredients;
    private final ItemStack output;

    protected final Map<String, Criterion<?>> criteria = new LinkedHashMap<>();
    @javax.annotation.Nullable
    protected String group;

    public MoldRecipeBuilder(List<Ingredient> inputIngredients, ItemStack output) {
        this.inputIngredients = inputIngredients;
        this.output = output;
    }

    @Override
    public @NotNull MoldRecipeBuilder unlockedBy(@NotNull String name, @NotNull Criterion<?> criterion) {
        this.criteria.put(name, criterion);
        return this;
    }

    @Override
    public RecipeBuilder group(@Nullable String groupName) {
        this.group = groupName;
        return this;
    }

    @Override
    public Item getResult() {
        return output.getItem();
    }

    @Override
    public void save(RecipeOutput recipeOutput, ResourceLocation id) {
        ResourceLocation moddedId = ResourceLocation.fromNamespaceAndPath(Embers.MODID, id.getPath());

        Advancement.Builder advancement = recipeOutput.advancement()
                .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(id))
                .rewards(AdvancementRewards.Builder.recipe(id))
                .requirements(AdvancementRequirements.Strategy.OR);
        this.criteria.forEach(advancement::addCriterion);

        MoldRecipe recipe = new MoldRecipe(this.inputIngredients, this.output);

        recipeOutput.accept(id, recipe, advancement.build(moddedId.withPrefix("recipes/")));
    }
}
