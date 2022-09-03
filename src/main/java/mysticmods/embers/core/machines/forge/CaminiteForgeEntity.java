package mysticmods.embers.core.machines.forge;

import mysticmods.embers.api.capability.IEmberIntensity;
import mysticmods.embers.core.base.EmberIntensityBlockEntity;
import mysticmods.embers.core.capability.intensity.EmberIntensity;
import mysticmods.embers.core.utils.TickBlockEntity;
import mysticmods.embers.init.EmbersBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import team.lodestar.lodestone.systems.multiblock.IMultiBlockCore;
import team.lodestar.lodestone.systems.multiblock.MultiBlockStructure;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.function.Supplier;

public class CaminiteForgeEntity extends EmberIntensityBlockEntity implements TickBlockEntity, IMultiBlockCore {
	public static final Supplier<MultiBlockStructure> STRUCTURE = () -> (MultiBlockStructure.of(new MultiBlockStructure.StructurePiece(0, 1, 0, EmbersBlocks.CAMINITE_FORGE_COMPONENT.get().defaultBlockState())));
	ArrayList<BlockPos> componentPositions = new ArrayList<>();

	public final MultiBlockStructure structure;

	private float progress = 0;
	private boolean isLit = false;
	private final ItemStackHandler itemHandler = new SmelterItemHandler(1, this);

	private final LazyOptional<IItemHandler> handler = LazyOptional.of(() -> itemHandler);

	private final int PROGRESS_PER_ITEM = 20 * 5;

	private IEmberIntensity ember;
	private SmelterRecipe currentRecipe = null;

	public int progressTimer = 0;

	public CaminiteForgeEntity(BlockEntityType<? extends CaminiteForgeEntity> type, BlockPos pos, BlockState state) {
		super(type, pos, state);
		this.structure = STRUCTURE.get();
		setupMultiblock(pos);
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

		//CompoundTag fluidTankTag = new CompoundTag();
		//this.outputTank.writeToNBT(fluidTankTag);
		//tag.put("fluidTank", fluidTankTag);
	}

	@Override
	public void load(@NotNull CompoundTag tag) {
		super.load(tag);
		this.progress = tag.getFloat("progress");
		this.isLit = tag.getBoolean("isLit");
		this.itemHandler.deserializeNBT(tag.getCompound("inventory"));
		//this.outputTank.readFromNBT(tag.getCompound("fluidTank"));
	}

	@Override
	public void serverTick(Level level, BlockPos blockPos, BlockState blockState) {
		if (level.getGameTime() % 20 == 0) {
//			findEmitter(blockPos);
//			if (getEmberIntensity().getIntensity() >= 40 && !this.isLit) {
//				this.isLit = true;
//				updateViaState();
//			} else if (getEmberIntensity().getIntensity() < 40 && this.isLit) {
//				this.isLit = false;
//				this.progress = 0;
//				updateViaState();
//			}
			System.out.println(this.progress);
		}

		if (true) {
			if (this.itemHandler.getStackInSlot(0) == ItemStack.EMPTY && this.progress > 0) {
				this.progress = 0;
				updateViaState();
				return;
			}

			if (this.itemHandler.getStackInSlot(0) != ItemStack.EMPTY) {
				if (this.progress < this.progressTimer) {
					progress++;
				}
			}


//			if (this.outputTank.getFluidAmount() < this.outputTank.getCapacity()) {
//				if (this.itemHandler.getStackInSlot(0) != ItemStack.EMPTY) {
//					var smeltingRecipe = level.getRecipeManager().getRecipeFor(EmbersRecipes.Types.SMELTER.get(), new SimpleContainer(this.itemHandler.getStackInSlot(0)), level);
//					if (smeltingRecipe.isPresent() && this.currentRecipe != smeltingRecipe.get()) {
//						this.currentRecipe = smeltingRecipe.get();
//					}
//				}
//
//				if (this.currentRecipe != null) {
//					if (this.outputTank.getFluidInTank(0).getFluid() != this.currentRecipe.getResult().getFluid()) {
//						if (this.outputTank.getFluidAmount() == 0) {
//							this.progress++;
//						} else {
//							this.progress = 0;
//						}
//					} else {
//						this.progress++;
//					}
//				}
//
//				updateViaState();
//			}


		}
	}

	@Override
	public void use(Player player, InteractionHand hand){
		ItemStack stack = player.getItemInHand(hand);
		if(this.itemHandler.isItemValid(0, stack)){
			ItemStack inputStack = stack.copy();
			if(stack.getCount() > 32){
				inputStack.setCount(32);
				stack.setCount(stack.getCount() - 32);
				this.itemHandler.insertItem(0, inputStack, false);
				setProgressNeeded();
				updateViaState();
			} else {
				this.itemHandler.insertItem(0, inputStack, false);
				player.setItemInHand(hand, ItemStack.EMPTY);
			}
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

	public void setProgressNeeded(){
		this.progressTimer = this.itemHandler.getStackInSlot(0).getCount() * PROGRESS_PER_ITEM;
	}

//	public FluidTank getOutputTank() {
//		return outputTank;
//	}

	@Override
	public MultiBlockStructure getStructure() {
		return structure;
	}

	@Override
	public ArrayList<BlockPos> getComponentPositions() {
		return componentPositions;
	}

	public void onBreak(@Nullable Player player) {
		destroyMultiblock(player, level, worldPosition);
	}
}
