package mysticmods.embers.core.machines.forge;

import mysticmods.embers.api.capability.IEmberIntensity;
import mysticmods.embers.api.capability.IHeatedMetal;
import mysticmods.embers.core.base.EmberIntensityBlockEntity;
import mysticmods.embers.core.capability.intensity.EmberIntensity;
import mysticmods.embers.init.EmbersBlocks;
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
import org.jetbrains.annotations.Nullable;
import team.lodestar.lodestone.setup.LodestoneParticleRegistry;
import team.lodestar.lodestone.systems.multiblock.IMultiBlockCore;
import team.lodestar.lodestone.systems.multiblock.MultiBlockStructure;
import team.lodestar.lodestone.systems.rendering.particle.ParticleBuilders;

import java.util.ArrayList;
import java.util.function.Supplier;

public class CaminiteForgeEntity extends EmberIntensityBlockEntity implements IMultiBlockCore {
	public static final Supplier<MultiBlockStructure> STRUCTURE = () -> MultiBlockStructure.of(new MultiBlockStructure.StructurePiece(0, 1, 0, EmbersBlocks.CAMINITE_FORGE_COMPONENT.get().defaultBlockState()));
	ArrayList<BlockPos> componentPositions = new ArrayList<>();

	public final MultiBlockStructure structure;

	private float progress = 0;
	private boolean isLit = false;
	private boolean hasHotMetals = false;
	private final int PROGRESS_PER_ITEM = 20 * 5;
	public int progressTimer = 0;

	private final IEmberIntensity ember = new EmberIntensity(100, 100);
	private final SmelterItemHandler itemHandler = new SmelterItemHandler(2, this) {
		@Override
		protected int getStackLimit(int slot, @NotNull ItemStack stack) {
			return 32;
		}
	};
	private final LazyOptional<IItemHandler> handler = LazyOptional.of(() -> itemHandler);


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
			this.hasHotMetals = false;
		}
	}

	@Override
	protected void saveAdditional(@NotNull CompoundTag tag) {
		super.saveAdditional(tag);
		tag.putFloat("progress", this.progress);
		tag.putBoolean("isLit", this.isLit);
		tag.putBoolean("hasHotMetals", this.hasHotMetals);
		tag.put("inventory", this.itemHandler.serializeNBT());
	}

	@Override
	public void load(@NotNull CompoundTag tag) {
		super.load(tag);
		this.progress = tag.getFloat("progress");
		this.isLit = tag.getBoolean("isLit");
		this.hasHotMetals = tag.getBoolean("hasHotMetals");
		this.itemHandler.deserializeNBT(tag.getCompound("inventory"));
	}

	@Override
	@NotNull
	public InteractionResult onUse(Player player, @NotNull InteractionHand hand) {
		ItemStack playerStack = player.getItemInHand(hand);
		if (this.itemHandler.isItemValid(0, playerStack)) {
			ItemStack returnStack = this.itemHandler.insertItem(0, playerStack, false);
			player.setItemInHand(hand, returnStack);
			setProgressNeeded();
			updateViaState();
		} else {
			if (this.hasHotMetals) {
				ItemStack hotMetal = this.itemHandler.getStackInSlot(1).copy();
				hotMetal.getCapability(EmbersCaps.HEATED_METAL).ifPresent(cap -> transferHeatedMetal(cap, player, hotMetal));
			}
		}
		return InteractionResult.SUCCESS;
	}

	public void transferHeatedMetal(IHeatedMetal cap, Player player, ItemStack stack) {
		System.out.println("transfer");
		System.out.println(cap.getMetal());

		cap.setMaxHeat(100 * stack.getCount());
		cap.setStackHeat(100 * stack.getCount());
		this.itemHandler.setStackInSlot(1, ItemStack.EMPTY);
		this.hasHotMetals = false;

		player.addItem(stack);
		updateViaState();
	}

	@Override
	public void serverTick() {
		if (this.itemHandler.getStackInSlot(0).isEmpty() && this.progress > 0) {
			this.progress = 0;
			updateViaState();
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
					this.itemHandler.getStackInSlot(1).getCapability(EmbersCaps.HEATED_METAL).ifPresent(cap -> cap.setMetalStack(new ItemStack(this.itemHandler.getStackInSlot(0).getItem(), 1)));
				}
				this.itemHandler.getStackInSlot(0).shrink(1);
				progress = 0;
				this.hasHotMetals = true;
				updateViaState();
			}
		}
	}

	@Override
	public void clientTick() {
		if (this.hasHotMetals && this.level != null) {
			var random = this.level.getRandom();
			ParticleBuilders.create(LodestoneParticleRegistry.WISP_PARTICLE)
					.addMotion(0, 0.0525d * (random.nextDouble() * 0.1d), 0)
					.setAlpha(0.5f, 0.2f)
					.setScale(0.1f)
					.setColor(230 / 255.0f, 55 / 255.0f, 16 / 255.0f, 230 / 255.0f, 83 / 255.0f, 16 / 255.0f)
					.setLifetime(Math.round(random.nextFloat() * 100))
					.disableForcedMotion()
					.setSpin(0)
					.spawn(this.level, getBlockPos().getX() + (random.nextFloat()), getBlockPos().getY() + 2, getBlockPos().getZ() + random.nextFloat());
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

	public float getProgress() {
		return this.progress / this.progressTimer;
	}

	public void setProgressNeeded() {
		this.progressTimer = this.itemHandler.getStackInSlot(0).getCount() * PROGRESS_PER_ITEM;
	}

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

	public SmelterItemHandler getItemHandler() {
		return itemHandler;
	}
}
