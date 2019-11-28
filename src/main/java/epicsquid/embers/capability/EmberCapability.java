package epicsquid.embers.capability;

import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.util.INBTSerializable;

public class EmberCapability implements IEmberCapability, INBTSerializable<CompoundNBT> {

    @CapabilityInject(IEmberCapability.class)
    public static Capability<IEmberCapability> EMBER_CAPABILITY = null;

    private int capacity;
    private int ember;

    public EmberCapability() {
        this.capacity = 0;
        this.ember = 0;
    }

    public EmberCapability(int capacity, int ember) {
        this.capacity = capacity;
        this.ember = ember;
    }

    @Override
    public int getCapacity() {
        return capacity;
    }

    @Override
    public int getEmber() {
        return ember;
    }

    @Override
    public int addEmber(int amount, boolean simulate) {
        int currentAmount = ember;
        int leftover = 0;

        currentAmount += amount;
        if (currentAmount > capacity) {
            leftover = currentAmount - capacity;
            currentAmount = capacity;
        }

        if (!simulate) {
            ember = currentAmount;
        }
        return leftover;
    }

    @Override
    public int removeEmber(int amount, boolean simulate) {
        int currentAmount = ember;
        int removed = amount;

        currentAmount -= amount;
        if (currentAmount < 0) {
            removed = amount + currentAmount;
            currentAmount = 0;
        }

        if (!simulate) {
            ember = currentAmount;
        }
        return removed;
    }

    @Override
    public CompoundNBT serializeNBT() {
        CompoundNBT nbt = new CompoundNBT();
        nbt.putInt("capacity", this.capacity);
        nbt.putInt("ember", this.ember);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        this.capacity = nbt.getInt("capacity");
        this.ember = nbt.getInt("ember");
    }
}