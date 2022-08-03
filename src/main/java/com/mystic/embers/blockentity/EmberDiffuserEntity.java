package com.mystic.embers.blockentity;

import com.mystic.embers.blockentity.base.BaseBlockEntity;
import com.mystic.embers.api.EmbersTags;
import com.mystic.embers.api.TickBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import noobanidus.libs.particleslib.client.particle.Particles;
import noobanidus.libs.particleslib.init.ModParticles;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class EmberDiffuserEntity extends BaseBlockEntity implements TickBlockEntity {

    public boolean running = false;
    private int emberOutput = 0;

    public EmberDiffuserEntity(BlockEntityType<?> pType, BlockPos pWorldPosition, BlockState pBlockState) {
        super(pType, pWorldPosition, pBlockState);
    }

    @Override
    public void clientTick(Level level, BlockPos blockPos, BlockState blockState) {
        if (running) {
            Random pRandom = level.getRandom();
            Particles.create(ModParticles.GLOW_PARTICLE.get())
                    .addVelocity(0, 0.0525f * (pRandom.nextFloat() * 0.1f), 0)
                    .setAlpha(0.5f, 0.2f)
                    .setScale(0.1f)
                    .setColor(230 / 255.0f, 55 / 255.0f, 16 / 255.0f, 230 / 255.0f, 83 / 255.0f, 16 / 255.0f)
                    .setLifetime(Math.round(pRandom.nextFloat() * 100))
                    .disableGravity()
                    .setSpin(0)
                    .spawn(level, blockPos.getX() + (pRandom.nextFloat()), blockPos.getY() + 1, blockPos.getZ() + pRandom.nextFloat());

        }
    }

    @Override
    public void serverTick(Level level, BlockPos blockPos, BlockState blockState) {
        if (level.getGameTime() % 20 == 0) {
            if (level.getBlockState(blockPos.below()).is(EmbersTags.Blocks.EMBER_HEAT_BLOCK)) {
                if (!this.running) {
                    this.running = true;
                    updateViaState();
                }
            } else {
                if (this.running) {
                    running = false;
                    updateViaState();
                }
            }

            if (running) {
                int newEmberOutput = 0;
                Block block = level.getBlockState(blockPos.below()).getBlock();
                if (block == Blocks.LAVA) {
                    newEmberOutput = 100;
                }
                if (this.emberOutput != newEmberOutput) {
                    this.emberOutput = newEmberOutput;
                    updateViaState();
                }
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
            running = false;
            emberOutput = 0;
        }
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag pTag) {
        super.saveAdditional(pTag);
        pTag.putBoolean("running", this.running);
        pTag.putInt("emberOutput", this.emberOutput);





        //pTag.put("heat_sources")
    }

    @Override
    public void load(@NotNull CompoundTag pTag) {
        super.load(pTag);
        this.running = pTag.getBoolean("running");
        this.emberOutput = pTag.getInt("emberOutput");

    }

    public int getEmberOutputForMachine(BlockPos targetPos){
        int xDistance = Math.abs(this.getBlockPos().getX() - targetPos.getX());
        int zDistance = Math.abs(this.getBlockPos().getZ() - targetPos.getZ());
        int highestDistance = Math.max(xDistance, zDistance);
        highestDistance += Math.abs(this.getBlockPos().getY() - targetPos.getY());
        return this.emberOutput - (20 * (highestDistance-1));
    }
}