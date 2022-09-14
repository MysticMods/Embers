package mysticmods.embers.core.machines.forge;

import com.google.gson.JsonObject;
import mysticmods.embers.Embers;
import mysticmods.embers.core.molten_metal.MoltenMetal;
import mysticmods.embers.init.EmbersMoltenMetals;
import mysticmods.embers.init.EmbersRecipes;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public class SmelterRecipeProvider extends RecipeProvider {
	public SmelterRecipeProvider(DataGenerator pGenerator) {
		super(pGenerator);
	}

	@Override
	protected void buildCraftingRecipes(Consumer<FinishedRecipe> pFinishedRecipeConsumer) {
		pFinishedRecipeConsumer.accept(new SmelterFinishedRecipe(new ResourceLocation(Embers.MOD_ID, "molten_iron"), Ingredient.of(Items.RAW_IRON), EmbersMoltenMetals.MOLTEN_IRON.get()));
		super.buildCraftingRecipes(pFinishedRecipeConsumer);
	}

	public static class SmelterFinishedRecipe implements FinishedRecipe {

		private final ResourceLocation id;
		private final Ingredient input;
		private final MoltenMetal moltenMetal;

		public SmelterFinishedRecipe(ResourceLocation id, Ingredient input, MoltenMetal moltenMetal) {
			this.id = id;
			this.input = input;
			this.moltenMetal = moltenMetal;
		}

		@Override
		public void serializeRecipeData(JsonObject pJson) {
			pJson.add("input", this.input.toJson());

			JsonObject jsonobject = new JsonObject();
			jsonobject.addProperty("molten_metal", this.moltenMetal.getId().toString());
			pJson.add("result", jsonobject);
		}

		@Override
		public ResourceLocation getId() {
			return id;
		}

		@Override
		public RecipeSerializer<?> getType() {
			return EmbersRecipes.Serializers.SMELTER.get();
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
