package mysticmods.embers.core.machines.anvil.copper;

import mysticmods.embers.api.capability.IEmberIntensity;
import mysticmods.embers.api.capability.IHeatedMetal;
import mysticmods.embers.api.helpers.MetalFamilyRegistry;
import mysticmods.embers.core.base.EmberIntensityBlockEntity;
import mysticmods.embers.core.capability.intensity.EmberIntensity;
import mysticmods.embers.init.EmbersCaps;
import mysticmods.embers.init.EmbersItems;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import org.jetbrains.annotations.NotNull;

public class CopperAnvilEntity extends EmberIntensityBlockEntity {

    private final IEmberIntensity ember = new EmberIntensity(100, 100);
    public final AnvilItemHandler itemHandler = new AnvilItemHandler(6, this){
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


        if(playerStack.getItem() == EmbersItems.IRON_HAMMER.get()){
            if(this.itemHandler.getFilledSlotAmount() == 1 && this.itemHandler.getStackInSlot(0).getCapability(EmbersCaps.HEATED_METAL).isPresent()){
                this.itemHandler.getStackInSlot(0).getCapability(EmbersCaps.HEATED_METAL).ifPresent(cap -> smithIngot(cap, level));
                return InteractionResult.SUCCESS;
            }
        }

        player.setItemInHand(hand, this.itemHandler.addItemStack(playerStack));
        return InteractionResult.SUCCESS;
    }

    public void smithIngot(IHeatedMetal cap, Level level){
        System.out.println(cap.getMetal().getItem());
        if(MetalFamilyRegistry.getInstance().getByRaw(cap.getMetal().getItem()).isPresent()){
            MetalFamilyRegistry.MetalFamily family = MetalFamilyRegistry.getInstance().getByRaw(cap.getMetal().getItem()).get();
            int nuggets = 0;
            float f = EntityType.ITEM.getHeight() / 2.0F;
            int heatProcentage = cap.getStackHeat() / (cap.getMaxHeat() / 100);
            for(int i= heatProcentage; i > 25; i -= 25 ){
                nuggets++;
            }
            for(int i = nuggets; i > 0; i--){
                ItemStack nuggetStack = new ItemStack(family.nugget);
                double d0 = (double)((float)getBlockPos().getX() + 0.5F) + Mth.nextDouble(level.random, -0.25D, 0.25D);
                double d1 = (double)((float)getBlockPos().getY() + 1.1F) + Mth.nextDouble(level.random, -0.25D, 0.25D) - (double)f;
                double d2 = (double)((float)getBlockPos().getZ() + 0.5F) + Mth.nextDouble(level.random, -0.25D, 0.25D);
                ItemEntity entity = new ItemEntity(level, d0, d1, d2, nuggetStack);
                entity.setDefaultPickUpDelay();
                level.addFreshEntity(entity);
            }

            ItemStack ingotStacl = new ItemStack(family.ingot);
            double d0 = (double)((float)getBlockPos().getX() + 0.5F) + Mth.nextDouble(level.random, -0.25D, 0.25D);
            double d1 = (double)((float)getBlockPos().getY() + 1.1F) + Mth.nextDouble(level.random, -0.25D, 0.25D) - (double)f;
            double d2 = (double)((float)getBlockPos().getZ() + 0.5F) + Mth.nextDouble(level.random, -0.25D, 0.25D);
            ItemEntity entity = new ItemEntity(level, d0, d1, d2, ingotStacl);
            entity.setDefaultPickUpDelay();
            level.addFreshEntity(entity);

            this.itemHandler.getStackInSlot(0).shrink(1);
        }
    }
    @Override
    public void serverTick() {
        for(int i = 0; i < this.itemHandler.getFilledSlotAmount(); i++){
                    }
    }

    @Override
    public void clientTick() {
        for(int i = 0; i < this.itemHandler.getFilledSlotAmount(); i++){

        }
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
