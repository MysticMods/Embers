package com.mystic.embers.blockentity;

import com.mystic.embers.api.TickBlockEntity;
import com.mystic.embers.blockentity.base.EmberRecievingBlockEntity;
import com.mystic.embers.blocks.CaminiteForgeBlock;
import com.mystic.embers.blocks.CaminiteForgeUnfiredBlock;
import com.mystic.embers.client.particle.ParticleUtil;
import com.mystic.embers.init.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class CaminiteUnfiredForgeEntity extends EmberRecievingBlockEntity implements TickBlockEntity {

//    private BlockPos generatorPosition = null;
    private int progress = 0;

    public CaminiteUnfiredForgeEntity(BlockEntityType<?> pType, BlockPos pWorldPosition, BlockState pBlockState) {
        super(pType, pWorldPosition, pBlockState);
    }

    @Override
    public <T extends BlockEntity> void clientTick(Level level, BlockPos blockPos, BlockState blockState) {
        if(progress >= 100){
            ParticleUtil.spawnSpark(level, blockPos.offset(0, 1, 0));
        }
    }

    @Override
    public <T extends BlockEntity> void serverTick(Level level, BlockPos blockPos, BlockState blockState) {
        if (level.getGameTime() % 20 == 0) {
            findGenerator(blockPos);
        }

        int emberOutput = getGeneratorEmberOutput();
        if(emberOutput >= 40){
            this.progress++;
            if(progress > 100){
                level.setBlock(this.getBlockPos(), ModBlocks.CAMINITE_FORGE.getDefaultState()
                        .setValue(CaminiteForgeBlock.FACING, level.getBlockState(this.getBlockPos()).getValue(CaminiteForgeUnfiredBlock.FACING)), 2);
            }
            updateViaState();
        }

    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
        super.onDataPacket(net, pkt);
        CompoundTag tag = pkt.getTag();
        if (tag != null) {
            load(tag);
        } else {
            this.generatorPosition = null;
        }
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag pTag) {
        super.saveAdditional(pTag);
        this.saveGenerator(pTag);
        pTag.putInt("progress",this.progress);

    }

    @Override
    public void load(@NotNull CompoundTag pTag) {
        super.load(pTag);
        this.loadGenerator(pTag);

        this.progress = pTag.getInt("progress");

    }
}