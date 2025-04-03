package mysticmods.embers.machines.brazier;

import mysticmods.embers.base.IEmberEmitterEntity;
import mysticmods.embers.capabilities.emberemitter.EmberEmitter;
import mysticmods.embers.capabilities.emberlevel.EmberLevel;
import mysticmods.embers.particles.options.EmbersParticleOptions;
import mysticmods.embers.utils.SDUtil;
import mysticmods.embers.init.EmbersBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import team.lodestar.lodestone.systems.blockentity.LodestoneBlockEntity;

import static mysticmods.embers.utils.BEUtil.dropItemHandler;
import static mysticmods.embers.utils.BEUtil.updateViaState;

public class BrazierBlockEntity extends LodestoneBlockEntity implements IEmberEmitterEntity {

    private final EmberEmitter emitter;
    private final ItemStackHandler itemHandler;
    private static final RandomSource random = RandomSource.create();

    public boolean running = false;
    public final int maxBurnTime = 1200;
    public int ticksToBurn = 0;

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

    @Override
    public void tick() {
        if(!level.isClientSide()) {
            if(running){
                ticksToBurn++;
                if (ticksToBurn >= maxBurnTime) {
                    ticksToBurn = 0;
                    ItemStack stack = itemHandler.getStackInSlot(0);
                    if (!stack.isEmpty()) {
                        stack.shrink(1);
                        updateViaState(this);
                    } else {
                        onFuelChange();
                    }
                }
            }
        } else {
            if (level.getGameTime() % 40 == 0 && running) {
                level.addParticle(new EmbersParticleOptions(1, 0.5f, 0),
                        getBlockPos().getX()  + 0.5f + Mth.nextFloat(random, -0.3f, 0.3f),
                        getBlockPos().getY() + 0.6f,
                        getBlockPos().getZ() + 0.5f + Mth.nextFloat(random, -0.3f, 0.3f),
                        0, 0.25d * (random.nextDouble() * 0.1d), 0);
            }
        }
    }

    @Override
    public ItemInteractionResult onUseWithItem(Player player, ItemStack stack, InteractionHand hand) {
        ItemStack playerStack = player.getItemInHand(hand);

        if (this.itemHandler.isItemValid(0, playerStack)) {
            ItemStack returnStack = this.itemHandler.insertItem(0, playerStack, false);
            player.setItemInHand(hand, returnStack);
            updateViaState(this);
        }

        onFuelChange();
        return ItemInteractionResult.CONSUME;
    }

    @Override
    public void onBreak(@Nullable Player player) {
        super.onBreak(player);
        EmberLevel emberLevel = SDUtil.getLevelEmbersData(level);
        if(emberLevel != null){
            emberLevel.removeEmitterListener(this.emitter);
        }
        dropItemHandler(this, itemHandler);
    }

    /*
     * This method is called when the item handler is changed. It checks if the item handler is empty or not and sets the running state accordingly.
     */
    private void onFuelChange(){
        //If the item handler is empty and we are running, stop running
        if (this.itemHandler.getStackInSlot(0).isEmpty() && running) {
            this.running = false;
            level.setBlock(getBlockPos(), getBlockState().setValue(BrazierBlock.LIT, false), Block.UPDATE_ALL);
            updateViaState(this);
        }
        //If the item handler is not empty and we are not running, start running
        else if(!this.itemHandler.getStackInSlot(0).isEmpty() && !running){
            this.running = true;
            level.setBlock(getBlockPos(), getBlockState().setValue(BrazierBlock.LIT, true), Block.UPDATE_ALL);

            //Remove one item from the item handler
            ItemStack stack = itemHandler.getStackInSlot(0);
            if (!stack.isEmpty()) {
                stack.shrink(1);
            }
            updateViaState(this);
        }
    }

    //Loading and saving
    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        running = tag.getBoolean("running");
        ticksToBurn = tag.getInt("ticksToBurn");
        itemHandler.deserializeNBT(registries, tag.getCompound("inventory"));
        emitter.deserializeNBT(registries, tag.getCompound("emitter"));
        super.loadAdditional(tag, registries);
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        tag.putBoolean("running", running);
        tag.putInt("ticksToBurn", ticksToBurn);
        tag.put("inventory", itemHandler.serializeNBT(registries));
        tag.put("emitter", emitter.serializeNBT(registries));
        super.saveAdditional(tag, registries);
    }

    @Override
    public @NotNull CompoundTag getUpdateTag(HolderLookup.@NotNull Provider registries) {
        CompoundTag tag = super.getUpdateTag(registries);
        this.saveAdditional(tag, registries);
        return tag;
    }

    //Emitters
    @Override
    public @NotNull EmberEmitter getEmitter() {
        return emitter;
    }

    public ItemStackHandler getItemHandler() {
        return itemHandler;
    }
}
