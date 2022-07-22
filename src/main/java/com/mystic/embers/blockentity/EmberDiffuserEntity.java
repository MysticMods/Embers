package com.mystic.embers.blockentity;

import com.mystic.embers.api.BaseBlockEntity;
import com.mystic.embers.api.EmbersTags;
import com.mystic.embers.api.TickBlockEntity;
import com.mystic.embers.client.particle.ParticleUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import noobanidus.libs.noobutil.util.BlockEntityUtil;
import noobanidus.libs.particleslib.client.particle.Particles;
import noobanidus.libs.particleslib.init.ModParticles;

import java.util.List;
import java.util.Random;

public class EmberDiffuserEntity extends BaseBlockEntity implements TickBlockEntity {

    public boolean state = false;

    public EmberDiffuserEntity(BlockEntityType<?> pType, BlockPos pWorldPosition, BlockState pBlockState) {
        super(pType, pWorldPosition, pBlockState);
    }

    @Override
    public <T extends BlockEntity> void clientTick(Level level, BlockPos blockPos, BlockState blockState ) {

        if(state){
            if(level.getGameTime() % 20 == 0){
                ParticleUtil.spawnSpark(level, blockPos.above());
            }
        }
    }

    @Override
    public <T extends BlockEntity> void serverTick(Level level, BlockPos blockPos, BlockState blockState ) {
        if(level.getGameTime() % 20 == 0){
            List<BlockPos> blockPosList = findBlocksWithTagInRadius(EmbersTags.Blocks.EMBER_HEAT_BLOCK, blockPos, level, 1, 0, 2);
            System.out.println(blockPosList.size());
            if(level.getBlockState(blockPos.below()).getBlock() == Blocks.LAVA){
                state = true;
            } else {
                state = false;
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
            state = false;
        }
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);
        pTag.putBoolean("state", this.state);
        //pTag.put("heat_sources")
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        this.state = pTag.getBoolean("state");
    }
}
