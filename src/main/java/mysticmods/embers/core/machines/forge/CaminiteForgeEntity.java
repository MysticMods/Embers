package mysticmods.embers.core.machines.forge;

import mysticmods.embers.api.capability.IEmberIntensity;
import mysticmods.embers.core.base.EmberIntensityBlockEntity;
import mysticmods.embers.core.capability.ember.EmberIntensity;
import mysticmods.embers.core.utils.TickBlockEntity;
import mysticmods.embers.init.EmbersRecipes;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;

public class CaminiteForgeEntity extends EmberIntensityBlockEntity implements TickBlockEntity {

	private float progress = 0;
	private boolean isLit = false;
	private final ItemStackHandler itemHandler = new SmelterItemHandler(1, 1, this);
	private final FluidTank outputTank;
	private final LazyOptional<IItemHandler> handler = LazyOptional.of(() -> itemHandler);

	private IEmberIntensity ember;
	private SmelterRecipe currentRecipe = null;

	public int progressTimer = 200;

	public CaminiteForgeEntity(BlockEntityType<? extends CaminiteForgeEntity> pType, BlockPos pWorldPosition, BlockState pBlockState) {
		super(pType, pWorldPosition, pBlockState);

		this.outputTank = new FluidTank(10000);

	}

	@Override
	public void clientTick(Level level, BlockPos blockPos, BlockState blockState) {

	}

	@Override
	public void serverTick(Level level, BlockPos blockPos, BlockState blockState) {
		if (level.getGameTime() % 20 == 0) {
			findEmitter(blockPos);
			if (getGeneratorEmberOutput() >= 40 && !this.isLit) {
				this.isLit = true;
				updateViaState();
			} else if (getGeneratorEmberOutput() < 40 && this.isLit) {
				this.isLit = false;
				updateViaState();
			}
		}

		if (isLit) {
			if (this.itemHandler.getStackInSlot(0) == ItemStack.EMPTY && this.progress > 0) {
				this.progress = 0;
				updateViaState();
			}


			if (this.outputTank.getFluidAmount() < this.outputTank.getCapacity()) {
				if (this.itemHandler.getStackInSlot(0) != ItemStack.EMPTY) {
					var smeltingRecipe = level.getRecipeManager().getRecipeFor(EmbersRecipes.Types.SMELTER.get(), new SimpleContainer(this.itemHandler.getStackInSlot(0)), level);
					if (smeltingRecipe.isPresent() && this.currentRecipe != smeltingRecipe.get()) {
						this.currentRecipe = smeltingRecipe.get();
					}
				}

				if (this.currentRecipe != null) {
					if (this.outputTank.getFluidInTank(0).getFluid() != this.currentRecipe.getResult().getFluid()) {
						if (this.outputTank.getFluidAmount() == 0) {
							this.progress++;
						} else {
							this.progress = 0;
						}
					} else {
						this.progress++;
					}
				}

				updateViaState();
			}

			if (this.progress >= this.progressTimer) {
				this.itemHandler.getStackInSlot(0).shrink(1);
				this.outputTank.fill(this.currentRecipe.getResult(), IFluidHandler.FluidAction.EXECUTE);
				this.progress = 0;
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
			this.progress = 0;
			this.isLit = false;
		}
	}

	@Override
	protected void saveAdditional(@NotNull CompoundTag tag) {
		super.saveAdditional(tag);
		tag.putFloat("progress", this.progress);
		tag.putBoolean("isLit", this.isLit);
		tag.put("inventory", this.itemHandler.serializeNBT());

		CompoundTag fluidTankTag = new CompoundTag();
		this.outputTank.writeToNBT(fluidTankTag);
		tag.put("fluidTank", fluidTankTag);
	}

	@Override
	public void load(@NotNull CompoundTag tag) {
		super.load(tag);
		this.progress = tag.getFloat("progress");
		this.isLit = tag.getBoolean("isLit");
		this.itemHandler.deserializeNBT(tag.getCompound("inventory"));
		this.outputTank.readFromNBT(tag.getCompound("fluidTank"));
	}

	@NotNull
	@Override
	public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap) {
		if (cap == ForgeCapabilities.ITEM_HANDLER) {
			return handler.cast();
		}
		return super.getCapability(cap);
	}

	@Nonnull
	@Override
	public IEmberIntensity getEmberIntensity() {
		if (ember == null) {
			ember = new EmberIntensity(100, 100);
		}
		return ember;
	}

	public float getProgress() {
		return this.progress / this.progressTimer;
	}

	public FluidTank getOutputTank() {
		return outputTank;
	}
}
