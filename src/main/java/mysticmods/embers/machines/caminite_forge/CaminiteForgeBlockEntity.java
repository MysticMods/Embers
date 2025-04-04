package mysticmods.embers.machines.caminite_forge;

import mysticmods.embers.base.IEmberIntesityEntity;
import mysticmods.embers.capabilities.emberintensity.EmberIntensity;
import mysticmods.embers.capabilities.heated_metal.IHeatedMetalCap;
import mysticmods.embers.init.EmbersBlockEntities;
import mysticmods.embers.init.EmbersBlocks;
import mysticmods.embers.init.EmbersCapabilities;
import mysticmods.embers.init.EmbersItems;
import mysticmods.embers.particles.options.EmbersParticleOptions;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import team.lodestar.lodestone.systems.multiblock.MultiBlockCoreEntity;
import team.lodestar.lodestone.systems.multiblock.MultiBlockStructure;

import java.util.function.Supplier;

import static mysticmods.embers.utils.BEUtil.updateViaState;

public class CaminiteForgeBlockEntity extends MultiBlockCoreEntity implements IEmberIntesityEntity {

    public static final Supplier<MultiBlockStructure> STRUCTURE = () -> MultiBlockStructure.of(new MultiBlockStructure.StructurePiece(0, 1, 0, EmbersBlocks.CAMINITE_FORGE_COMPONENT.get().defaultBlockState()));

    private final EmberIntensity intensity;
    private final ItemStackHandler itemHandler = new ForgeItemHandler(2, this) {
        @Override
        protected int getStackLimit(int slot, @NotNull ItemStack stack) {
            return 32;
        }
    };

    private float progress = 0;
    private boolean isLit = false;
    private boolean hasHotMetals = false;
    private final int PROGRESS_PER_ITEM = 20 * 5;
    public int progressTimer = 0;

    public CaminiteForgeBlockEntity(BlockPos pos, BlockState blockState) {
        super(EmbersBlockEntities.CAMINITE_FORGE.get(), STRUCTURE.get(), pos, blockState);
        this.intensity = new EmberIntensity(100, 100);
    }

    @Override
    public void tick() {
        if(level.isClientSide()) {
            clientTick();
        } else {
            serverTick();
        }
    }

    public void serverTick() {
        if (this.itemHandler.getStackInSlot(0).isEmpty() && this.progress > 0) {
            this.progress = 0;
            updateViaState(this);
            return;
        }

        if (!this.itemHandler.getStackInSlot(0).isEmpty()) {
            //hasEmberForOperation() &&
            progress++;

            if (progress >= this.PROGRESS_PER_ITEM) {
                ItemStack hotMetalStack = this.itemHandler.getStackInSlot(1);
                if (!hotMetalStack.isEmpty()) {
                    hotMetalStack.setCount(hotMetalStack.getCount() + 1);
                } else {
                    this.itemHandler.setStackInSlot(1, new ItemStack(EmbersItems.HEATED_METAL.get(), 1));
                    IHeatedMetalCap cap = this.itemHandler.getStackInSlot(1).getCapability(EmbersCapabilities.HEATED_METAL);
                    if (cap != null) {
                        cap.setMetalStack(new ItemStack(this.itemHandler.getStackInSlot(0).getItem(), 1));
                    }
                }
                this.itemHandler.getStackInSlot(0).shrink(1);
                progress = 0;
                this.hasHotMetals = true;
                updateViaState(this);
            }
        }
    }

    public void clientTick() {
        if (this.hasHotMetals && this.level != null) {
            var random = this.level.getRandom();
            level.addParticle(new EmbersParticleOptions(1, 0.5f, 0),
                    getBlockPos().getX() + (random.nextFloat()), getBlockPos().getY() + 2, getBlockPos().getZ() + random.nextFloat(),
                    0, 0.25d * (random.nextDouble() * 0.1d), 0);
        }
    }

    @Override
    public ItemInteractionResult onUseWithItem(Player player, ItemStack pStack, InteractionHand hand) {
        if (level.isClientSide()) {
            return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
        }
        if (this.itemHandler.isItemValid(0, pStack)) {
            ItemStack returnStack = this.itemHandler.insertItem(0, pStack, false);
            player.setItemInHand(hand, returnStack);
            setProgressNeeded();
            updateViaState(this);
            return ItemInteractionResult.CONSUME;
        } else {
            if (this.hasHotMetals) {
                ItemStack hotMetal = this.itemHandler.getStackInSlot(1).copy();
                IHeatedMetalCap cap = hotMetal.getCapability(EmbersCapabilities.HEATED_METAL);
                if (cap != null) {
                    transferHeatedMetal(cap, player, hotMetal);
                }
            }
        }
        return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
    }

    public void transferHeatedMetal(IHeatedMetalCap cap, Player player, ItemStack stack) {
        cap.setMaxHeat(100 * stack.getCount());
        cap.setStackHeat(100 * stack.getCount());
        this.itemHandler.setStackInSlot(1, ItemStack.EMPTY);
        this.hasHotMetals = false;

        player.addItem(stack);
        updateViaState(this);
    }

    @Override
    public EmberIntensity getEmberIntensity() {
        return intensity;
    }

    public float getProgress() {
        return this.progress / this.progressTimer;
    }

    public void setProgressNeeded() {
        this.progressTimer = this.itemHandler.getStackInSlot(0).getCount() * PROGRESS_PER_ITEM;
    }
}
