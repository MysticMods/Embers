package mysticmods.embers.capabilities.heated_metal;

import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.common.util.INBTSerializable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.UnknownNullability;

public class HeatedMetalCap implements IHeatedMetalCap, INBTSerializable<CompoundTag> {

    private int stackHeat;
    private int maximumStackHeat;
    private ItemStack stack;

    public HeatedMetalCap(int stackHeat, int maximumStackHeat, ItemStack stack) {
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
    public void copyFromCapability(IHeatedMetalCap cap) {
        this.stackHeat = cap.getStackHeat();
        this.maximumStackHeat = cap.getMaxHeat();
    }


    @Override
    public void clearCap() {
        this.stackHeat = 0;
        this.maximumStackHeat = 0;
        this.stack = ItemStack.EMPTY;
    }

    @Override
    public @UnknownNullability CompoundTag serializeNBT(HolderLookup.@NotNull Provider provider) {
        CompoundTag tag = new CompoundTag();
        tag.putInt("stackHeat", this.stackHeat);
        tag.putInt("maximumStackHeat", this.maximumStackHeat);
        tag.put("itemStack", this.stack.save(provider));
        return tag;
    }

    @Override
    public void deserializeNBT(HolderLookup.@NotNull Provider provider, CompoundTag nbt) {
        this.stackHeat = nbt.getInt("stackHeat");
        this.maximumStackHeat = nbt.getInt("maximumStackHeat");
        this.stack = ItemStack.parse(provider, nbt.getCompound("itemStack")).orElse(ItemStack.EMPTY);
    }
}