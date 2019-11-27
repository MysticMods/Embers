package epicsquid.embers.capability;

import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nullable;

public class EmberCapabilityStorage  implements Capability.IStorage<IEmberCapability> {

    @Nullable
    @Override
    public INBT writeNBT(Capability<IEmberCapability> capability, IEmberCapability instance, Direction side) {
        return capability.writeNBT(instance, side);
    }

    @Override
    public void readNBT(Capability<IEmberCapability> capability, IEmberCapability instance, Direction side, INBT nbt) {
        capability.readNBT(instance, side, nbt);
    }
}