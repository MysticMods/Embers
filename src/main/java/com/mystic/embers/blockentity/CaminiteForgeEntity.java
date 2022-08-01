package com.mystic.embers.blockentity;

import com.mystic.embers.api.TickBlockEntity;
import com.mystic.embers.blockentity.base.EmberRecievingBlockEntity;
import com.mystic.embers.init.ModFluids;
import com.mystic.embers.item.handlers.SmelterItemHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;

public class CaminiteForgeEntity extends EmberRecievingBlockEntity implements TickBlockEntity {

    private float progress = 0;
    private boolean isLit = false;
    private final ItemStackHandler itemHandler = new SmelterItemHandler(1, 1,this);
    private final FluidTank outputTank;
    private final LazyOptional<IItemHandler> handler = LazyOptional.of(() -> itemHandler);

    public CaminiteForgeEntity(BlockEntityType<?> pType, BlockPos pWorldPosition, BlockState pBlockState) {
        super(pType, pWorldPosition, pBlockState);

        this.outputTank = new FluidTank(2000);

    }

    @Override
    public <T extends BlockEntity> void clientTick(Level level, BlockPos blockPos, BlockState blockState ) {

    }

    @Override
    public <T extends BlockEntity> void serverTick(Level level, BlockPos blockPos, BlockState blockState ) {
        if (level.getGameTime() % 20 == 0) {
            findGenerator(blockPos);
            if(getGeneratorEmberOutput() >= 40 && !this.isLit){
                this.isLit = true;
                updateViaState();
            } else if(getGeneratorEmberOutput() < 40 && this.isLit){
                this.isLit = false;
                updateViaState();
            }
        }

        if(isLit){
            progress++;
            if(progress >= 100){
                progress = 0;
            }

            this.outputTank.fill(new FluidStack(ModFluids.MOLTEN_IRON.get().getSource(), 10), IFluidHandler.FluidAction.EXECUTE);

            updateViaState();
        }

        System.out.println("Fluids: " + this.outputTank.getFluidAmount());
        System.out.println("Fluids tank: " + this.outputTank.getFluidInTank(0));
    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
        super.onDataPacket(net, pkt);
        CompoundTag tag = pkt.getTag();
        if (tag != null) {
            load(tag);
        } else {
            this.progress = 0;
            this.isLit = false;
        }
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putFloat("progress", this.progress);
        tag.putBoolean("isLit", this.isLit);

        CompoundTag fluidTankTag = new CompoundTag();
        this.outputTank.writeToNBT(fluidTankTag);
        tag.put("fluidTank", fluidTankTag);
    }

    @Override
    public void load(@NotNull CompoundTag tag) {
        super.load(tag);
        this.progress = tag.getFloat("progress");
        this.isLit = tag.getBoolean("isLit");
        this.outputTank.readFromNBT(tag.getCompound("fluidTank"));
    }

    @NotNull
    @Override
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap) {
        if(cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY){
            return handler.cast();
        }
        return super.getCapability(cap);
    }

    public float getProgress() {
        return this.progress / 100;
    }
}
