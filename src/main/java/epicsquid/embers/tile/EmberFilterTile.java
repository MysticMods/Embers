package epicsquid.embers.tile;

import epicsquid.embers.capability.EmberCapability;
import epicsquid.mysticallib.util.Util;
import net.minecraft.block.Blocks;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import java.util.List;

public class EmberFilterTile extends TileEntity implements ITickableTileEntity {

    public LazyOptional<EmberCapability> emberCapability = LazyOptional.of(this::createHandler);
    private List<BlockPos> heatSources;

    public EmberFilterTile() {
        super(ModTiles.EMBER_FILTER_TILE);
    }

    public void onActivated(){
        emberCapability.ifPresent(cap -> {
            System.out.println(cap.getEmber());
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
        return new EmberCapability(1000, 0);
    }

    @Override
    public void read(CompoundNBT tag) {
        CompoundNBT invTag = tag.getCompound("emberCap");
        emberCapability.ifPresent(h -> ((INBTSerializable<CompoundNBT>) h).deserializeNBT(invTag));
        super.read(tag);
    }

    @Override
    public CompoundNBT write(CompoundNBT tag) {
        emberCapability.ifPresent(h -> {
            CompoundNBT compound = ((INBTSerializable<CompoundNBT>) h).serializeNBT();
            tag.put("emberCap", compound);
        });
        return super.write(tag);
    }

    @Override
    public void tick() {
        if(heatSources == null){
            heatSources = Util.getBlocksWithinRadius(world, getPos(), 10, 5, 10, Blocks.LAVA, Blocks.FIRE, Blocks.MAGMA_BLOCK, Blocks.CAMPFIRE);
        }

        emberCapability.ifPresent(c -> {
            c.addEmber(heatSources.size(), false);
            markDirty();
        });
    }
}
