package mysticmods.embers.api.helpers;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MetalFamilyRegistry {

	private static MetalFamilyRegistry INSTANCE;
	private List<MetalFamily> families = new ArrayList<>();

	private MetalFamilyRegistry() {
		this.families.add(new MetalFamily(Blocks.IRON_ORE, Items.IRON_INGOT, Items.IRON_NUGGET, Items.RAW_IRON));
		this.families.add(new MetalFamily(Blocks.GOLD_ORE, Items.GOLD_INGOT, Items.GOLD_NUGGET, Items.RAW_GOLD));
	}

	public Optional<MetalFamily> getByIngot(Item ingot) {
		for (MetalFamily m : this.families) {
			if (m.ingot == ingot) {
				return Optional.of(m);
			}
		}

		return Optional.empty();
	}

	public Optional<MetalFamily> getByRaw(Item raw) {
		for (MetalFamily m : this.families) {
			if (m.raw_ore == raw) {
				return Optional.of(m);
			}
		}

		return Optional.empty();
	}

	public static MetalFamilyRegistry getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new MetalFamilyRegistry();
		}

		return INSTANCE;
	}

	public class MetalFamily {
		public final Block ore;
		public final Item ingot;
		public final Item nugget;
		public final Item raw_ore;

		public MetalFamily(Block ore, Item ingot, Item nugget, Item raw_ore) {
			this.ore = ore;
			this.ingot = ingot;
			this.nugget = nugget;
			this.raw_ore = raw_ore;
		}
	}
}
