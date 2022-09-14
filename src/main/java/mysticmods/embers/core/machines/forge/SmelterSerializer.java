package mysticmods.embers.core.machines.forge;

import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import mysticmods.embers.core.molten_metal.MoltenMetal;
import mysticmods.embers.core.molten_metal.MoltenMetalRegistry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;

public class SmelterSerializer implements RecipeSerializer<SmelterRecipe> {

	@Override
	@Nonnull
	public SmelterRecipe fromJson(@Nonnull ResourceLocation pRecipeId, JsonObject pSerializedRecipe) {
		Ingredient input;
		if (pSerializedRecipe.has("input")) {
			input = Ingredient.fromJson(pSerializedRecipe.get("input"));
		} else {
			throw new JsonSyntaxException("Missing input for recipe " + pRecipeId);
		}


		MoltenMetal moltenMetal;
		if (pSerializedRecipe.has("result")) {
			JsonObject result = pSerializedRecipe.getAsJsonObject("result");
			String moltenMetalId;

			if (result.has("molten_metal")) {
				moltenMetalId = GsonHelper.getAsString(pSerializedRecipe.getAsJsonObject("result"), "molten_metal");
			} else {
				throw new JsonSyntaxException("Missing fluid in result object for recipe " + pRecipeId);
			}
//			int amount;
//			if (result.has("amount")) {
//				amount = GsonHelper.getAsInt(pSerializedRecipe.getAsJsonObject("result"), "amount");
//			} else {
//				throw new JsonSyntaxException("Missing amount in result object for recipe " + pRecipeId);
//			}

			moltenMetal = MoltenMetalRegistry.REGISTRY.get().getValue(new ResourceLocation(moltenMetalId));

		} else {
			throw new JsonSyntaxException("Missing result object for recipe " + pRecipeId);
		}

		return new SmelterRecipe(input, moltenMetal, pRecipeId);
	}

	@Nullable
	@Override
	public SmelterRecipe fromNetwork(@Nonnull ResourceLocation pRecipeId, @Nonnull FriendlyByteBuf pBuffer) {
		Ingredient input = Ingredient.fromNetwork(pBuffer);
		MoltenMetal stack = MoltenMetalRegistry.REGISTRY.get().getValue(pBuffer.readResourceLocation());
		return new SmelterRecipe(input, stack, pRecipeId);
	}

	@Override
	public void toNetwork(@Nonnull FriendlyByteBuf pBuffer, SmelterRecipe pRecipe) {
		pRecipe.ingriedient.toNetwork(pBuffer);
		pBuffer.writeResourceLocation(pRecipe.result.getId());

	}
	// Implement methods here
}