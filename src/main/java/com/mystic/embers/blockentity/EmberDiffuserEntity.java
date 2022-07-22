package com.mystic.embers.blockentity;

import com.mystic.embers.api.BaseBlockEntity;
import com.mystic.embers.api.TickBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import noobanidus.libs.noobutil.util.BlockEntityUtil;
import noobanidus.libs.particleslib.client.particle.Particles;
import noobanidus.libs.particleslib.init.ModParticles;

import java.util.Random;

public class EmberDiffuserEntity extends BaseBlockEntity implements TickBlockEntity {

    public boolean state = false;

    public EmberDiffuserEntity(BlockEntityType<?> pType, BlockPos pWorldPosition, BlockState pBlockState) {
        super(pType, pWorldPosition, pBlockState);
    }

    @Override
    public <T extends BlockEntity> void clientTick(Level level, BlockPos blockPos, BlockState blockState ) {

        if(state){
            if(level.getGameTime() % 5 == 0){
                Random pRandom = level.getRandom();
                Particles.create(ModParticles.GLOW_PARTICLE.get())
                        .addVelocity(0, 0.0525f * (pRandom.nextFloat() * 0.1f), 0)
                        .setAlpha(0.5f, 0.2f)
                        .setScale(0.1f)
                        .setColor(230 / 255.0f, 55 / 255.0f, 16 / 255.0f, 230 / 255.0f, 83 / 255.0f, 16 / 255.0f)
                        .setLifetime(80)
                        .disableGravity()
                        .setSpin(0)
                        .spawn(level, blockPos.getX() + (pRandom.nextFloat()), blockPos.getY() + 1 + (pRandom.nextFloat()- 0.5f), blockPos.getZ() + 0.5f + 0.3f * (pRandom.nextFloat()));

            }
        }
    }

    @Override
    public <T extends BlockEntity> void serverTick(Level level, BlockPos blockPos, BlockState blockState ) {
        if(level.getGameTime() % 20 == 0){
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
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        this.state = pTag.getBoolean("state");
    }
}
