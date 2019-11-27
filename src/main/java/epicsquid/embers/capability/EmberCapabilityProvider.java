package epicsquid.embers.capability;

import epicsquid.mysticallib.MysticalLib;
import net.minecraft.nbt.LongNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class EmberCapabilityProvider implements ICapabilityProvider, ICapabilitySerializable<LongNBT> {
    public static final ResourceLocation IDENTIFIER = new ResourceLocation(MysticalLib.MODID, "ember_capability");

    @CapabilityInject(EmberCapability.class)
    public static final Capability<EmberCapability> EMBER_CAPABILITY = injected();

    private final EmberCapability instance = EMBER_CAPABILITY.getDefaultInstance();

    @Override
    public LongNBT serializeNBT() {
        return (LongNBT) EMBER_CAPABILITY.getStorage().writeNBT(EMBER_CAPABILITY, this.instance, null);
    }

    @Override
    public void deserializeNBT(LongNBT nbt) {
        EMBER_CAPABILITY.getStorage().readNBT(EMBER_CAPABILITY, this.instance, null, nbt);
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap) {
        return cap == EMBER_CAPABILITY ? LazyOptional.of(() -> (T) this.instance) : LazyOptional.empty();
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        return getCapability(cap);
    }

    //This is here to get rid of all the ugly PLAYER_GROVE might be null
    private static final Object NULL = null;

    @SuppressWarnings("unchecked")
    private static <T> T injected() {
        return (T) NULL;
    }
}
