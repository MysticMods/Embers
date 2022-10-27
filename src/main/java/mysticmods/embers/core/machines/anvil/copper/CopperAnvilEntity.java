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
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import org.jetbrains.annotations.NotNull;

public class CopperAnvilEntity extends EmberIntensityBlockEntity {

	private final IEmberIntensity ember = new EmberIntensity(100, 100);
	public final AnvilItemHandler itemHandler = new AnvilItemHandler(6, this) {
		@Override
		protected int getStackLimit(int slot, @NotNull ItemStack stack) {
			return 32;
		}
	};
	private final LazyOptional<IItemHandler> handler = LazyOptional.of(() -> itemHandler);
	private long lastForgedTick = 0;

	public CopperAnvilEntity(BlockEntityType<? extends EmberIntensityBlockEntity> type, BlockPos pos, BlockState state) {
		super(type, pos, state);
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


		if (playerStack.getItem() == EmbersItems.IRON_HAMMER.get()) {
			if (this.itemHandler.getFilledSlotAmount() == 1 && this.itemHandler.getStackInSlot(0).getCapability(EmbersCaps.HEATED_METAL).isPresent()) {
				this.itemHandler.getStackInSlot(0).getCapability(EmbersCaps.HEATED_METAL).ifPresent(cap -> smithIngot(cap, level, player));
			}

			return InteractionResult.SUCCESS;
		}

        if(playerStack.isEmpty()){
			player.addItem(this.itemHandler.getLastFilledSlot());
        }

        player.setItemInHand(hand, this.itemHandler.addItemStack(playerStack));
        return InteractionResult.SUCCESS;
    }

    public void smithIngot(IHeatedMetal cap, Level level, Player player){
        if(MetalFamilyRegistry.getInstance().getByRaw(cap.getMetal().getItem()).isPresent()){
            MetalFamilyRegistry.MetalFamily family = MetalFamilyRegistry.getInstance().getByRaw(cap.getMetal().getItem()).get();
            float f = EntityType.ITEM.getHeight() / 2.0F;
            int heatProcentage = cap.getStackHeat() / (cap.getMaxHeat() / 100);

			//Calculate nuggets
			int nuggets = 0;
			if(player.getAttackStrengthScale(0.0F) == 1.0F){
				for(int i= heatProcentage; i > 25; i -= 25 ){
					nuggets++;
				}
			}
			this.lastForgedTick = this.level.getGameTime();
            for(int i = nuggets; i > 0; i--){
                ItemStack nuggetStack = new ItemStack(family.nugget);
                double d0 = (double)((float)getBlockPos().getX() + 0.5F) + Mth.nextDouble(level.random, -0.25D, 0.25D);
                double d1 = (double)((float)getBlockPos().getY() + 1.1F) + Mth.nextDouble(level.random, -0.25D, 0.25D) - (double)f;
                double d2 = (double)((float)getBlockPos().getZ() + 0.5F) + Mth.nextDouble(level.random, -0.25D, 0.25D);
                ItemEntity entity = new ItemEntity(level, d0, d1, d2, nuggetStack);
                entity.setDefaultPickUpDelay();
                level.addFreshEntity(entity);
            }

			double d0 = (double)((float)getBlockPos().getX() + 0.5F) + Mth.nextDouble(level.random, -0.25D, 0.25D);
			double d1 = (double)((float)getBlockPos().getY() + 1.1F) + Mth.nextDouble(level.random, -0.25D, 0.25D) - (double)f;
			double d2 = (double)((float)getBlockPos().getZ() + 0.5F) + Mth.nextDouble(level.random, -0.25D, 0.25D);
			ItemEntity entity = new ItemEntity(level, d0, d1, d2, new ItemStack(family.ingot));
			entity.setDefaultPickUpDelay();
			level.addFreshEntity(entity);

			this.itemHandler.getStackInSlot(0).shrink(1);
			player.resetAttackStrengthTicker();
			//todo:Spawn spark particles
		}
	}





    @Override
    public void serverTick() {
        for(int i = 0; i < this.itemHandler.getFilledSlotAmount(); i++){
                    }
    }

	@NotNull
	@Override
	public IEmberIntensity getEmberIntensity() {
		return ember;
	}
}