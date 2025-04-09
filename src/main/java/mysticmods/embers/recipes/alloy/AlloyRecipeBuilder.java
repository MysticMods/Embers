package mysticmods.embers.recipes.alloy;

import mysticmods.embers.Embers;
import mysticmods.embers.recipes.malleable_metal.MalleableMetalRecipe;
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
import java.util.Map;

public class AlloyRecipeBuilder implements RecipeBuilder {

    private final Ingredient metalOne;
    private final Ingredient metalTwo;
    private final ItemStack output;

    protected final Map<String, Criterion<?>> criteria = new LinkedHashMap<>();
    @javax.annotation.Nullable
    protected String group;

    public AlloyRecipeBuilder(Ingredient metalOne, Ingredient metalTwo, ItemStack output) {
        this.metalOne = metalOne;
        this.metalTwo = metalTwo;
        this.output = output;
    }

    @Override
    public @NotNull AlloyRecipeBuilder unlockedBy(@NotNull String name, @NotNull Criterion<?> criterion) {
        this.criteria.put(name, criterion);
        return this;
    }

    @Override
    public @NotNull AlloyRecipeBuilder group(@Nullable String groupName) {
        this.group = groupName;
        return this;
    }

    @Override
    public @NotNull Item getResult() {
        return output.getItem();
    }

    @Override
    public void save(@NotNull RecipeOutput recipeOutput, @NotNull ResourceLocation id) {
        ResourceLocation moddedId = ResourceLocation.fromNamespaceAndPath(Embers.MODID, id.getPath());

        Advancement.Builder advancement = recipeOutput.advancement()
                .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(id))
                .rewards(AdvancementRewards.Builder.recipe(id))
                .requirements(AdvancementRequirements.Strategy.OR);
        this.criteria.forEach(advancement::addCriterion);

        AlloyRecipe recipe = new AlloyRecipe(metalOne, metalTwo, output);

        recipeOutput.accept(id, recipe, advancement.build(moddedId.withPrefix("recipes/")));
    }
}
