package mysticmods.embers.capabilities.heated_metal;

import mysticmods.embers.registries.MalleableMetal;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.common.util.INBTSerializable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.UnknownNullability;

public class HeatedMetalCap implements IHeatedMetalCap, INBTSerializable<CompoundTag> {

    private int stackHeat;
    private int maximumStackHeat;
    private int ingots = 0;
    private int nuggets = 0;
    private ItemStack stack;
    private MalleableMetal malleableMetal;

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
    public MalleableMetal getMalleableMetal() {
        return malleableMetal;
    }

    @Override
    public void setMalleableMetal(MalleableMetal malleableMetal) {
        this.malleableMetal = malleableMetal;
    }

    @Override
    public int getIngots() {
        return this.ingots;
    }

    @Override
    public void addIngots(int ingotAmount) {
        this.ingots += ingotAmount;
    }

    @Override
    public int getNuggets() {
        return this.nuggets;
    }

    @Override
    public void addNuggets(int nuggetAmount) {
        this.nuggets += nuggetAmount;
        if (this.nuggets >= 9) {
            this.nuggets -= 9;
            this.ingots += 1;
        }
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
        this.ingots = 0;
        this.nuggets = 0;
        this.stack = ItemStack.EMPTY;
    }

    @Override
    public @UnknownNullability CompoundTag serializeNBT(HolderLookup.@NotNull Provider provider) {
        CompoundTag tag = new CompoundTag();
        tag.putInt("stackHeat", this.stackHeat);
        tag.putInt("maximumStackHeat", this.maximumStackHeat);
        tag.putInt("ingots", this.ingots);
        tag.putInt("nuggets", this.nuggets);
        tag.put("itemStack", this.stack.save(provider));
        return tag;
    }

    @Override
    public void deserializeNBT(HolderLookup.@NotNull Provider provider, CompoundTag nbt) {
        this.stackHeat = nbt.getInt("stackHeat");
        this.maximumStackHeat = nbt.getInt("maximumStackHeat");
        this.ingots = nbt.getInt("ingots");
        this.nuggets = nbt.getInt("nuggets");
        this.stack = ItemStack.parse(provider, nbt.getCompound("itemStack")).orElse(ItemStack.EMPTY);
    }
}