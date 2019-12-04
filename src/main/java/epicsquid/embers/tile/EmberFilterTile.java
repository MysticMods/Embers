package epicsquid.embers.tile;

import epicsquid.embers.capability.EmberCapability;
import epicsquid.embers.capability.EmberCapabilityProvider;
import epicsquid.embers.entity.EmberEntity;
import epicsquid.embers.setup.ModEntities;
import epicsquid.embers.setup.ModTiles;
import epicsquid.mysticallib.util.Util;
import net.minecraft.block.Blocks;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import java.util.List;

public class EmberFilterTile extends TileEntity implements ITickableTileEntity {

    private int ticks = 0;
    private int transferAmount = 20;
    private LazyOptional<EmberCapability> emberCapability = LazyOptional.of(this::createHandler);
    private List<BlockPos> heatSources;
    private EmberEntity emberEntity;

    public EmberFilterTile() {
        super(ModTiles.EMBER_FILTER_TILE);
    }


    public void onActivated(){
        emberCapability.ifPresent(cap -> System.out.println(cap.getEmber()));
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

    public void prepareEmberEntity(){
        List<EmberEntity> emberEntities =  getWorld().getEntitiesWithinAABB(EmberEntity.class, new AxisAlignedBB(pos.getX() - 3, pos.getY(), pos.getZ() - 3, pos.getX() + 3, pos.getY() + 4, pos.getZ() + 3));
        if(emberEntities.size() == 0){
            EmberEntity emberEntity = ModEntities.EMBER_ENTITY.create(world);
            emberEntity.setPosition(pos.getX(), pos.getY() + 2, pos.getZ());
            if(!world.isRemote){
                world.addEntity(emberEntity);
            }
            this.emberEntity = emberEntity;
        }
    }

    @Override
    public void tick() {
        //Create the ember entity on first tick
        if(ticks == 1){
            prepareEmberEntity();
        }

        //Check if firepit exists every 2.5 seconds.
        if(this.ticks % 50 == 0 || heatSources == null){
            heatSources = Util.getBlocksWithinRadius(world, getPos().down(), 1, 0, 1, Blocks.LAVA, Blocks.FIRE, Blocks.MAGMA_BLOCK, Blocks.CAMPFIRE);
        }

        //Transfer heat into ember every second.
        if(this.ticks % 20 == 0){
            emberCapability.ifPresent(c -> {
                c.addEmber(heatSources.size(), false);
                markDirty();
            });

            if(this.emberEntity != null){
                emberEntity.getCapability(EmberCapabilityProvider.EMBER_CAPABILITY).ifPresent(entityCap ->{
                    emberCapability.ifPresent(cap -> {
                        if(cap.getEmber() >= transferAmount){
                            cap.removeEmber(transferAmount, false);
                            entityCap.addEmber(transferAmount, false);
                        }
                    });
                });
            }
            else{
                prepareEmberEntity();
            }
        }

        ticks++;
    }
}
