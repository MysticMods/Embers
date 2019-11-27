package epicsquid.embers.tile;

import epicsquid.embers.capability.EmberCapability;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;

public class EmberFilterTile extends TileEntity {

    public LazyOptional<EmberCapability> emberCapability = LazyOptional.of(this::createHandler);

    public EmberFilterTile() {
        super(ModTiles.EMBER_FILTER);
    }

    public void onActivated(){
        emberCapability.ifPresent(cap -> {
            System.out.println(cap.getCapacity());
        });
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap) {
        if (cap == EmberCapability.EMBER_CAPABILITY) {
            return emberCapability.cast();
        }
        return super.getCapability(cap);
    }

    public EmberCapability createHandler(){
        return new EmberCapability();
    }
}
