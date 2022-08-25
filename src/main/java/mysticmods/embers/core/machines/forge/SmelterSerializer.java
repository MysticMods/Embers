package mysticmods.embers.core.machines.forge;

import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import java.util.Objects;

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


		FluidStack fluidStack;
		if (pSerializedRecipe.has("result")) {
			JsonObject result = pSerializedRecipe.getAsJsonObject("result");
			String fluid;
			int amount;
			if (result.has("fluid")) {
				fluid = GsonHelper.getAsString(pSerializedRecipe.getAsJsonObject("result"), "fluid");
			} else {
				throw new JsonSyntaxException("Missing fluid in result object for recipe " + pRecipeId);
			}
			if (result.has("amount")) {
				amount = GsonHelper.getAsInt(pSerializedRecipe.getAsJsonObject("result"), "amount");
			} else {
				throw new JsonSyntaxException("Missing amount in result object for recipe " + pRecipeId);
			}

			fluidStack = new FluidStack(Objects.requireNonNull(ForgeRegistries.FLUIDS.getValue(new ResourceLocation(fluid))), amount);

		} else {
			throw new JsonSyntaxException("Missing result object for recipe " + pRecipeId);
		}

		return new SmelterRecipe(input, fluidStack, pRecipeId);
	}

	@Nullable
	@Override
	public SmelterRecipe fromNetwork(@Nonnull ResourceLocation pRecipeId, @Nonnull FriendlyByteBuf pBuffer) {
		Ingredient input = Ingredient.fromNetwork(pBuffer);
		FluidStack stack = FluidStack.readFromPacket(pBuffer);
		return new SmelterRecipe(input, stack, pRecipeId);
	}

	@Override
	public void toNetwork(@Nonnull FriendlyByteBuf pBuffer, SmelterRecipe pRecipe) {
		pRecipe.ingriedient.toNetwork(pBuffer);
		pBuffer.writeFluidStack(pRecipe.result);

	}
	// Implement methods here
}