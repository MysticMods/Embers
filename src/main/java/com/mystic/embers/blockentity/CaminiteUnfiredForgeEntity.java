package com.mystic.embers.blockentity;

import com.mystic.embers.api.BaseBlockEntity;
import com.mystic.embers.api.EmbersTags;
import com.mystic.embers.api.TickBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class CaminiteUnfiredForgeEntity extends BaseBlockEntity implements TickBlockEntity {

    private BlockPos generatorPosition = null;

    public CaminiteUnfiredForgeEntity(BlockEntityType<?> pType, BlockPos pWorldPosition, BlockState pBlockState) {
        super(pType, pWorldPosition, pBlockState);
    }

    @Override
    public <T extends BlockEntity> void clientTick(Level level, BlockPos blockPos, BlockState blockState) {

    }

    @Override
    public <T extends BlockEntity> void serverTick(Level level, BlockPos blockPos, BlockState blockState) {
        if (level.getGameTime() % 20 == 0) {
            List<BlockPos> generators = findBlocksWithTagInRadius(EmbersTags.Blocks.EMBER_GENERATOR, blockPos, level, 10, 3, 3);
            BlockPos bestGenerator = null;
            int bestEmberSource = 0;
            for (BlockPos pos : generators) {
                BlockEntity blockEntity = level.getBlockEntity(pos);
                if (blockEntity instanceof EmberDiffuserEntity entity) {
                    int emberSource = entity.getEmberOutputForMachine(blockPos);
                    if (emberSource > bestEmberSource) { //TODO: Add distance check for closest generator
                        bestEmberSource = emberSource;
                        bestGenerator = pos;
                    }
                }
            }
            System.out.println(bestEmberSource);
            if (bestGenerator != null) {
                this.generatorPosition = bestGenerator;
                updateViaState();
            }
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
        if (this.generatorPosition != null) {
            CompoundTag t = new CompoundTag();
            t.putInt("x", this.generatorPosition.getX());
            t.putInt("y", this.generatorPosition.getY());
            t.putInt("z", this.generatorPosition.getZ());
            pTag.put("generator_position", t);
        }

    }

    @Override
    public void load(@NotNull CompoundTag pTag) {
        super.load(pTag);
        CompoundTag generator_position = (CompoundTag) pTag.get("generator_position");
        if (generator_position != null) {
            this.generatorPosition = new BlockPos(generator_position.getInt("x"), generator_position.getInt("y"), generator_position.getInt("z"));
        }

    }
}