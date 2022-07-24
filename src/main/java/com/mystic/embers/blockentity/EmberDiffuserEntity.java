package com.mystic.embers.blockentity;

import com.mystic.embers.api.BaseBlockEntity;
import com.mystic.embers.api.TickBlockEntity;
import com.mystic.embers.capability.EmbersCapabilities;
import com.mystic.embers.capability.reciever.IEmberReceiverCapability;
import com.mystic.embers.client.particle.ParticleUtil;
import com.mystic.embers.init.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class EmberDiffuserEntity extends BaseBlockEntity implements TickBlockEntity {

    public boolean state = false;
    public List<BlockPos> emberMachines = new ArrayList<>();

    public EmberDiffuserEntity(BlockEntityType<?> pType, BlockPos pWorldPosition, BlockState pBlockState) {
        super(pType, pWorldPosition, pBlockState);
    }

    @Override
    public <T extends BlockEntity> void clientTick(Level level, BlockPos blockPos, BlockState blockState ) {

        if(state){
            if(level.getGameTime() % 20 == 0){
                for(BlockPos p : this.emberMachines){
                    ParticleUtil.spawnSpark(level, p);
                }
            }
        }
    }

    @Override
    public <T extends BlockEntity> void serverTick(Level level, BlockPos blockPos, BlockState blockState ) {
        if(level.getGameTime() % 20 == 0){
            this.emberMachines = new ArrayList<>();
            List<BlockPos> blockPosList = findBlocksInRadius(ModBlocks.CAMINITE_FORGE.get(), blockPos, level, 3, 2, 2);
            for(BlockPos p : blockPosList){
                var emberOp = EmbersCapabilities.getEmberReceiverCapability(Objects.requireNonNull(level.getBlockEntity(p))).resolve();
                if(emberOp.isPresent()){
                    IEmberReceiverCapability capability = emberOp.get();
                    this.emberMachines.add(p);
                }
            }
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
        ListTag blockList = new ListTag();

        for(BlockPos p : this.emberMachines){
            CompoundTag t = new CompoundTag();
            t.putInt("x", p.getX());
            t.putInt("y", p.getY());
            t.putInt("z", p.getZ());
            blockList.add(t);
        }

        pTag.put("ember_machines", blockList);

        //pTag.put("heat_sources")
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        this.state = pTag.getBoolean("state");
        this.emberMachines = new ArrayList<>();

        ListTag emberMachinesTag = (ListTag) pTag.get("ember_machines");
        for(Tag t : Objects.requireNonNull(emberMachinesTag)){
            CompoundTag compoundTag = (CompoundTag) t;
            emberMachines.add(new BlockPos(compoundTag.getInt("x"), compoundTag.getInt("y"), compoundTag.getInt("z")));
        }
    }
}
