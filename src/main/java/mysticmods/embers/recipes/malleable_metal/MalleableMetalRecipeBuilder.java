package mysticmods.embers.recipes.malleable_metal;

import mysticmods.embers.Embers;
import mysticmods.embers.init.EmbersItems;
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
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.LinkedHashMap;
import java.util.Map;

public class MalleableMetalRecipeBuilder implements RecipeBuilder {

    protected final MalleableMetal malleableMetal;
    public final float experience;
    public final int processingTime;

    protected final Map<String, Criterion<?>> criteria = new LinkedHashMap<>();
    @Nullable
    protected String group;

    public MalleableMetalRecipeBuilder(MalleableMetal malleableMetal, float experience, int processingTime) {
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
    public @NotNull MalleableMetalRecipeBuilder group(@Nullable String groupName) {
        this.group = groupName;
        return this;
    }

    @Override
    public @NotNull Item getResult() {
        return EmbersItems.HEATED_METAL.get();
    }

    @Override
    public void save(RecipeOutput recipeOutput, @NotNull ResourceLocation id) {
        ResourceLocation moddedId = ResourceLocation.fromNamespaceAndPath(Embers.MODID, id.getPath());

        Advancement.Builder advancement = recipeOutput.advancement()
                .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(id))
                .rewards(AdvancementRewards.Builder.recipe(id))
                .requirements(AdvancementRequirements.Strategy.OR);
        this.criteria.forEach(advancement::addCriterion);

        MalleableMetalRecipe recipe = new MalleableMetalRecipe(malleableMetal, experience, processingTime);

        recipeOutput.accept(id, recipe, advancement.build(moddedId.withPrefix("recipes/")));
    }
}