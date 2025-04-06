package mysticmods.embers.machines.caminite_forge;

import mysticmods.embers.base.IEmberIntesityEntity;
import mysticmods.embers.capabilities.emberintensity.EmberIntensity;
import mysticmods.embers.capabilities.emberlevel.EmberLevel;
import mysticmods.embers.capabilities.heated_metal.IHeatedMetalCap;
import mysticmods.embers.init.EmbersBlockEntities;
import mysticmods.embers.init.EmbersBlocks;
import mysticmods.embers.init.EmbersCapabilities;
import mysticmods.embers.init.EmbersItems;
import mysticmods.embers.particles.options.EmbersParticleOptions;
import mysticmods.embers.utils.SDUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.IntTag;
import net.minecraft.network.Connection;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import team.lodestar.lodestone.systems.multiblock.MultiBlockCoreEntity;
import team.lodestar.lodestone.systems.multiblock.MultiBlockStructure;

import java.util.function.Supplier;

import static mysticmods.embers.utils.BEUtil.dropItemHandler;
import static mysticmods.embers.utils.BEUtil.updateViaState;

public class CaminiteForgeBlockEntity extends MultiBlockCoreEntity implements IEmberIntesityEntity {

    public static final Supplier<MultiBlockStructure> STRUCTURE = () -> MultiBlockStructure.of(new MultiBlockStructure.StructurePiece(0, 1, 0, EmbersBlocks.CAMINITE_FORGE_COMPONENT.get().defaultBlockState()));

    private final EmberIntensity intensity;
    private final ItemStackHandler itemHandler = new ForgeItemHandler(3, this) {
        @Override
        protected int getStackLimit(int slot, @NotNull ItemStack stack) {
            return 32;
        }
    };
    private EmberLevel emberLevel;

    private float progress = 0;
    private boolean isLit = false;
    private static final int PROGRESS_PER_ITEM = 20 * 5;

    public CaminiteForgeBlockEntity(BlockPos pos, BlockState blockState) {
        super(EmbersBlockEntities.CAMINITE_FORGE.get(), STRUCTURE.get(), pos, blockState);
        this.intensity = new EmberIntensity(100, 100, this::updateToClient);
    }

    @Override
    public void onLoad() {
        emberLevel = SDUtil.getLevelEmbersData(level);
        if (emberLevel != null) {
            emberLevel.addEmberListener(getBlockPos(), this.intensity);
        }
    }

    public void updateToClient() {
        if (!level.isClientSide()) {
            updateViaState(this);
        }
    }

    @Override
    public void tick() {
        if (level.isClientSide()) {
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
            if (this.intensity.hasEmberForOperation()) {
                progress++;

                if (progress >= this.PROGRESS_PER_ITEM) {
                    ItemStack hotMetalStack = this.itemHandler.getStackInSlot(2);
                    if (!hotMetalStack.isEmpty()) {
                        hotMetalStack.setCount(hotMetalStack.getCount() + 1);
                    } else {
                        this.itemHandler.setStackInSlot(2, new ItemStack(EmbersItems.HEATED_METAL.get(), 1));
                        IHeatedMetalCap cap = this.itemHandler.getStackInSlot(2).getCapability(EmbersCapabilities.HEATED_METAL);
                        if (cap != null) {
                            cap.setMetalStack(new ItemStack(this.itemHandler.getStackInSlot(0).getItem(), 1));
                        }
                    }
                    this.itemHandler.getStackInSlot(0).shrink(1);
                    progress = 0;
                }

                updateViaState(this);
            }
        }
    }

    public void clientTick() {
        if (this.hasHotMetals() && this.level != null) {
            if (level.getGameTime() % 5 == 0) {
                var random = this.level.getRandom();
                level.addParticle(new EmbersParticleOptions(1, 0.5f, 0),
                        getBlockPos().getX() + (0.25f + random.nextFloat() * 0.5f),
                        getBlockPos().getY() + 2,
                        getBlockPos().getZ() + (0.25f + random.nextFloat() * 0.5f),
                        0, 0.25d * (random.nextDouble() * 0.1d), 0);
            }
        }
    }

    @Override
    public ItemInteractionResult onUseWithItem(Player player, ItemStack pStack, InteractionHand hand) {
        if (level.isClientSide()) {
            return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
        }

        // Open the GUI
        if (player instanceof ServerPlayer serverPlayer) {
            serverPlayer.openMenu(new MenuProvider() {
                @Override
                public Component getDisplayName() {
                    return Component.translatable("block.embers.caminite_forge");
                }

                @Override
                public AbstractContainerMenu createMenu(int windowId, Inventory playerInventory, Player player) {
                    return new CaminiteForgeMenu(windowId, playerInventory, CaminiteForgeBlockEntity.this);
                }
            }, buf -> buf.writeBlockPos(getBlockPos()));
            return ItemInteractionResult.CONSUME;
        }

        return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
    }

    public void transferHeatedMetal(IHeatedMetalCap cap, Player player, ItemStack stack) {
        cap.setMaxHeat(100 * stack.getCount());
        cap.setStackHeat(100 * stack.getCount());
        this.itemHandler.setStackInSlot(2, ItemStack.EMPTY);

        player.addItem(stack);
        updateViaState(this);
    }

    public boolean hasHotMetals() {
        return !this.itemHandler.getStackInSlot(2).isEmpty();
    }

    @Override
    public EmberIntensity getEmberIntensity() {
        return intensity;
    }

    public ItemStackHandler getItemHandler() {
        return itemHandler;
    }

    public float getProgress() {
        if (this.progress > 0) {
            return this.progress / this.PROGRESS_PER_ITEM;
        }
        return 0;
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.putFloat("progress", this.progress);
        tag.putBoolean("isLit", this.isLit);
        tag.put("inventory", this.itemHandler.serializeNBT(registries));
        tag.put("emberIntensity", this.intensity.serializeNBT(registries));
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        this.progress = tag.getFloat("progress");
        this.isLit = tag.getBoolean("isLit");
        this.itemHandler.deserializeNBT(registries, tag.getCompound("inventory"));
        this.intensity.deserializeNBT(registries, IntTag.valueOf(tag.getInt("emberIntensity")));
    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt, HolderLookup.Provider lookupProvider) {
        super.onDataPacket(net, pkt, lookupProvider);
        CompoundTag tag = pkt.getTag();
        loadAdditional(tag, lookupProvider);
    }

    @Override
    public void onBreak(@Nullable Player player) {
        super.onBreak(player);
        if (emberLevel != null) {
            emberLevel.removeEmberListener(getBlockPos(), this.intensity);
        }

        dropItemHandler(this, itemHandler);
    }
}
