package mysticmods.embers.core.capability.heatedmetals;

import mysticmods.embers.api.capability.IHeatedMetal;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class HeatedMetalProvider implements ICapabilitySerializable<CompoundTag> {

    private final IHeatedMetal heatedMetal;
    private final LazyOptional<IHeatedMetal> op;

    public HeatedMetalProvider() {
        this(0, 0, ItemStack.EMPTY);
    }

    public HeatedMetalProvider(int stackHeat, int maximumStackHeat, ItemStack stack) {
        this.heatedMetal = new HeatedMetal(stackHeat, maximumStackHeat, stack);
        this.op = LazyOptional.of(() -> heatedMetal);
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        return null;
    }

    @Override
    public CompoundTag serializeNBT() {
        return null;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {

    }
}
