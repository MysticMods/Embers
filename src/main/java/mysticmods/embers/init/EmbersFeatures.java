package mysticmods.embers.init;

import mysticmods.embers.Embers;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.data.worldgen.features.OreFeatures;
import net.minecraft.data.worldgen.placement.OrePlacements;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;

public class EmbersFeatures {

	public static final DeferredRegister<PlacedFeature> PLACED_FEATURE = DeferredRegister.create(Registry.PLACED_FEATURE_REGISTRY, Embers.MOD_ID);

	public static final RegistryObject<PlacedFeature> CINNABAR_ORE = PLACED_FEATURE.register("cinnabar_ore", () -> new PlacedFeature(Holder.direct(new ConfiguredFeature<>(Feature.ORE,
			new OreConfiguration(List.of(OreConfiguration.target(OreFeatures.STONE_ORE_REPLACEABLES, EmbersBlocks.CAMINITE_BRICK.getDefaultState())), 10))),
			OrePlacements.commonOrePlacement(10, HeightRangePlacement.triangle(VerticalAnchor.BOTTOM, VerticalAnchor.TOP))));

}
