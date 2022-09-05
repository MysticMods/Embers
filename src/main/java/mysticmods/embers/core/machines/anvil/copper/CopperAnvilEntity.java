package mysticmods.embers.core.machines.anvil.copper;

import mysticmods.embers.api.capability.IEmberIntensity;
import mysticmods.embers.api.capability.IHeatedMetal;
import mysticmods.embers.core.base.EmberIntensityBlockEntity;
import mysticmods.embers.core.capability.intensity.EmberIntensity;
import mysticmods.embers.init.EmbersCaps;
import mysticmods.embers.init.EmbersItems;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import org.jetbrains.annotations.NotNull;

public class CopperAnvilEntity extends EmberIntensityBlockEntity {

    private final IEmberIntensity ember = new EmberIntensity(100, 100);
    private final AnvilItemHandler itemHandler = new AnvilItemHandler(6, this){
        @Override
        protected int getStackLimit(int slot, @NotNull ItemStack stack) {
            return 32;
        }
    };
    private final LazyOptional<IItemHandler> handler = LazyOptional.of(() -> itemHandler);

    public CopperAnvilEntity(BlockEntityType<? extends EmberIntensityBlockEntity> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
        super.onDataPacket(net, pkt);
        CompoundTag tag = pkt.getTag();
        if (tag != null) {
            load(tag);
        } else {
        }
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag tag) {
        super.saveAdditional(tag);
        tag.put("inventory", this.itemHandler.serializeNBT());
    }

    @Override
    public void load(@NotNull CompoundTag tag) {
        super.load(tag);
        this.itemHandler.deserializeNBT(tag.getCompound("inventory"));
    }

    @Override
    @NotNull
    public InteractionResult onUse(Player player, @NotNull InteractionHand hand) {
        ItemStack playerStack = player.getItemInHand(hand);
        if(playerStack.getItem() == EmbersItems.FORGING_GLOVE.get()){
            playerStack.getCapability(EmbersCaps.HEATED_METAL).ifPresent(this::transferHeatedMetal);
        }
        return InteractionResult.SUCCESS;
    }

    public void transferHeatedMetal(IHeatedMetal cap){
        if(!cap.getMetal().isEmpty()){
            return;
        }

        ItemStack metalStack = cap.getMetal();
        metalStack.getCapability(EmbersCaps.HEATED_METAL).ifPresent(returnCap -> returnCap.copyFromCapability(cap));
        this.itemHandler.addItemStack(metalStack);
        updateViaState();
    }

    @Override
    public void serverTick() {
    }

    @Override
    public void clientTick() {
    }

    @NotNull
    @Override
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap) {
        if (cap == ForgeCapabilities.ITEM_HANDLER) {
            return handler.cast();
        }
        return super.getCapability(cap);
    }

    @NotNull
    @Override
    public IEmberIntensity getEmberIntensity() {
        return ember;
    }
}
