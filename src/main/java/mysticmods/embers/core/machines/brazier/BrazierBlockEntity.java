package mysticmods.embers.core.machines.brazier;

import mysticmods.embers.core.base.EmberEmitterBlockEntity;
import mysticmods.embers.core.capabilities.emberemitter.EmberEmitter;
import mysticmods.embers.core.capabilities.emberlevel.EmberLevel;
import mysticmods.embers.core.particles.options.EmbersParticleOptions;
import mysticmods.embers.core.utils.BlockEntityUtil;
import mysticmods.embers.core.utils.SDUtil;
import mysticmods.embers.init.EmbersBlockEntities;
import mysticmods.embers.init.EmbersParticles;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class BrazierBlockEntity extends EmberEmitterBlockEntity {

    private final EmberEmitter emitter;
    private final ItemStackHandler itemHandler;
    private static final RandomSource random = RandomSource.create();

    public boolean running = false;

    public BrazierBlockEntity(BlockPos pos, BlockState blockState) {
        super(EmbersBlockEntities.BRAZIER.get(), pos, blockState);

        itemHandler = new ItemStackHandler(1) {
            @Override
            public int getSlotLimit(int slot) {
                return 16;
            }
            @Override
            public boolean isItemValid(int slot, @NotNull ItemStack stack) {
                return stack.getItem() == Items.COAL || stack.getItem() == Items.CHARCOAL;
            }
        };


        // TODO make a helper method for this
        BlockPos lowerBound = getBlockPos().offset(-3, -3, -3);
        BlockPos upperBound = getBlockPos().offset(3, 3, 3);
        emitter = new EmberEmitter(new int[]{100, 100, 100, 50}, getBlockPos(), new BoundingBox(lowerBound.getX(), lowerBound.getY(), lowerBound.getZ(), upperBound.getX(), upperBound.getY(), upperBound.getZ()), () -> running);
    }

    @Override
    public void onLoad() {
        super.onLoad();

        EmberLevel emberLevel = SDUtil.getLevelEmbersData(level);
        if(emberLevel != null){
            emitter.initEmitter(emberLevel);
        }
    }

    public static void tick(Level level, BlockPos pos, BlockState state, BrazierBlockEntity blockEntity) {
        if(!level.isClientSide()) {
            if (level.getGameTime() % 20 == 0) {
                if (!blockEntity.itemHandler.getStackInSlot(0).isEmpty()) {
                    if (!blockEntity.running) {
                        blockEntity.running = true;
                        level.setBlock(blockEntity.getBlockPos(), state.setValue(BrazierBlock.LIT, true), Block.UPDATE_ALL);
                        blockEntity.updateViaState();
                    }
                } else {
                    blockEntity.running = false;
                    blockEntity.updateViaState();
                }
            }
        } else {
            if (level.getGameTime() % 40 == 0) {
                level.addParticle(new EmbersParticleOptions(1, 0.5f, 0),
                        blockEntity.getBlockPos().getX()  + 0.5f + Mth.nextFloat(random, -0.3f, 0.3f),
                        blockEntity.getBlockPos().getY() + 0.6f,
                        blockEntity.getBlockPos().getZ() + 0.5f + Mth.nextFloat(random, -0.3f, 0.3f),
                        0, 0.25d * (random.nextDouble() * 0.1d), 0);
            }
        }
    }

    public void updateViaState() {
        setChanged();
        BlockEntityUtil.updateViaState(this);
    }

    public InteractionResult onUse(Player pPlayer, InteractionHand pHand) {
        return InteractionResult.PASS;
    }

    public InteractionResult onUseWithoutItem(Player pPlayer) {
        return InteractionResult.PASS;
    }

    public InteractionResult onUseWithItem(Player player, ItemStack stack, InteractionHand hand) {
        ItemStack playerStack = player.getItemInHand(hand);
        //sys out itemstack name and size first
        System.out.println("ItemStack: " + this.itemHandler.getStackInSlot(0).getItem().getName(playerStack).getString() + " Size: " + this.itemHandler.getStackInSlot(0).getCount());
        if (this.itemHandler.isItemValid(0, playerStack)) {
            ItemStack returnStack = this.itemHandler.insertItem(0, playerStack, false);
            player.setItemInHand(hand, returnStack);
            updateViaState();
        }
        return InteractionResult.CONSUME;
    }

    @Override
    public @NotNull EmberEmitter getEmitter() {
        return emitter;
    }

    public ItemStackHandler getItemHandler() {
        return itemHandler;
    }

    public void onBlockBroken() {
        EmberLevel emberLevel = SDUtil.getLevelEmbersData(level);
        if(emberLevel != null){
            emberLevel.removeEmitterListener(this.emitter);
        }
    }


    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        running = tag.getBoolean("running");
        if(tag.contains("inventory")){
            itemHandler.deserializeNBT(registries, tag.getCompound("inventory"));
        }
        super.loadAdditional(tag, registries);
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        tag.putBoolean("running", running);
        tag.put("inventory", itemHandler.serializeNBT(registries));
        super.saveAdditional(tag, registries);
    }

    @Override
    public @NotNull CompoundTag getUpdateTag(HolderLookup.@NotNull Provider registries) {
        CompoundTag tag = super.getUpdateTag(registries);
        this.saveAdditional(tag, registries);
        return tag;
    }

}
