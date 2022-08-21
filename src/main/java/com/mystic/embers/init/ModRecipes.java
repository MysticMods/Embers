package com.mystic.embers.init;

import com.mystic.embers.Embers;
import com.mystic.embers.recipe.smelter.SmelterRecipe;
import com.mystic.embers.recipe.smelter.SmelterSerializer;
import com.tterrag.registrate.Registrate;
import com.tterrag.registrate.util.entry.RegistryEntry;
import net.minecraft.core.Registry;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModRecipes {

	private static final Registrate REGISTRATE = Embers.registrate();

	public static class Serializers {

		public static final RegistryEntry<SmelterSerializer> SMELTER = REGISTRATE.simple("smelter", ForgeRegistries.RECIPE_SERIALIZERS.getRegistryKey(), SmelterSerializer::new);

		public static void load() {
		}
	}

	public static class Types {
		private static final DeferredRegister<RecipeType<?>> SERIALIZER = DeferredRegister.create(Registry.RECIPE_TYPE_REGISTRY, Embers.MODID);


		public static RegistryObject<RecipeType<SmelterRecipe>> SMELTER = SERIALIZER.register("smelter", () -> new RecipeType<>() {
			@Override
			public String toString() {
				return "embers:smelter";
			}
		});

		public static void register(IEventBus bus) {
			SERIALIZER.register(bus);
		}
	}
}
