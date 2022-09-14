package mysticmods.embers.core.machines.forge;

import mysticmods.embers.init.EmbersRecipes;
import net.minecraft.core.NonNullList;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;

public class SmelterItemHandler extends ItemStackHandler {

	private final BlockEntity entity;


	public SmelterItemHandler(int size,BlockEntity blockEntity) {
		super(size);
		this.entity = blockEntity;
	}

	public SmelterItemHandler(NonNullList<ItemStack> stacks, int outputs, BlockEntity blockEntity) {
		super(stacks);
		this.entity = blockEntity;
	}

	@Override
	protected void onContentsChanged(int slot) {
		this.entity.setChanged();
	}

	@Override
	public boolean isItemValid(int slot, @NotNull ItemStack stack) {
		var s = entity.getLevel().getRecipeManager().getRecipeFor(EmbersRecipes.Types.SMELTER.get(), new SimpleContainer(stack), entity.getLevel());
		return s.isPresent();
	}

}
