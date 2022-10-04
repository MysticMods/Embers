package mysticmods.embers.core.capability.heatedmetals;

import mysticmods.embers.api.capability.IHeatedMetal;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

public class HeatedMetal implements IHeatedMetal {

	private int stackHeat;
	private int maximumStackHeat;
	private ItemStack stack;

	public HeatedMetal(int stackHeat, int maximumStackHeat, ItemStack stack) {
		this.stackHeat = stackHeat;
		this.maximumStackHeat = maximumStackHeat;
		this.stack = stack;
	}

	@Override
	public int getStackHeat() {
		return this.stackHeat;
	}

	@Override
	public void setStackHeat(int stackHeat) {
		this.stackHeat = stackHeat;
	}

	@Override
	public void removeStackHeat(int amount) {
		if (this.stackHeat == 0) {
			return;
		} else if (amount < 0) {
			this.stackHeat = 0;
			return;
		}
		this.stackHeat -= amount;
	}

	@Override
	public int getMaxHeat() {
		return this.maximumStackHeat;
	}

	@Override
	public void setMaxHeat(int maxHeat) {
		this.maximumStackHeat = maxHeat;
	}

	@Override
	public ItemStack getMetal() {
		return this.stack;
	}

	@Override
	public void setMetalStack(ItemStack stack) {
		this.stack = stack;

	}

	@Override
	public void copyFromCapability(IHeatedMetal cap) {
		this.stackHeat = cap.getStackHeat();
		this.maximumStackHeat = cap.getMaxHeat();
	}

	@Override
	public CompoundTag serializeNBT() {
		CompoundTag tag = new CompoundTag();
		tag.putInt("stackHeat", this.stackHeat);
		tag.putInt("maximumStackHeat", this.maximumStackHeat);
		CompoundTag stackTag = new CompoundTag();
		this.stack.save(stackTag);
		tag.put("itemStack", stackTag);
		return tag;
	}

	@Override
	public void deserializeNBT(CompoundTag nbt) {
		this.stackHeat = nbt.getInt("stackHeat");
		this.maximumStackHeat = nbt.getInt("maximumStackHeat");
		this.stack = ItemStack.of(nbt.getCompound("itemStack"));
	}

	@Override
	public void clearCap() {
		this.stackHeat = 0;
		this.maximumStackHeat = 0;
		this.stack = ItemStack.EMPTY;
	}
}
