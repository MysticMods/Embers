package mysticmods.embers.core.machines.brazier;

import mysticmods.embers.api.capability.IEmberEmitter;
import mysticmods.embers.core.base.EmberEmitterBlockEntity;
import mysticmods.embers.core.capability.emitter.EmberEmitter;
import mysticmods.embers.init.EmbersCaps;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraftforge.items.ItemStackHandler;
import noobanidus.libs.noobutil.util.BlockEntityUtil;
import org.jetbrains.annotations.NotNull;
import team.lodestar.lodestone.setup.LodestoneParticleRegistry;
import team.lodestar.lodestone.systems.rendering.particle.ParticleBuilders;

import javax.annotation.Nonnull;

public class BrazierEntity extends EmberEmitterBlockEntity {

	public boolean running = false;

	private final ItemStackHandler itemHandler;
	private final IEmberEmitter emitter;

	public BrazierEntity(BlockEntityType<? extends EmberEmitterBlockEntity> pType, BlockPos pWorldPosition, BlockState pBlockState) {
		super(pType, pWorldPosition, pBlockState);
		itemHandler = new ItemStackHandler(1) {
			@Override
			public int getSlotLimit(int slot) {
				return 16;
			}

			@Override
			public boolean isItemValid(int slot, @NotNull ItemStack stack) {
				return stack.getItem() == Items.COAL;
			}
		};

		// TODO make a helper method for this
		BlockPos lowerBound = getBlockPos().offset(-3, -3, -3);
		BlockPos upperBound = getBlockPos().offset(3, 3, 3);
		emitter = new EmberEmitter(new int[]{100, 100, 100, 50}, getBlockPos(), new BoundingBox(lowerBound.getX(), lowerBound.getY(), lowerBound.getZ(), upperBound.getX(), upperBound.getY(), upperBound.getZ()));
	}

	@Override
	protected void saveAdditional(@Nonnull CompoundTag pTag) {
		super.saveAdditional(pTag);
		pTag.putBoolean("running", this.running);
		pTag.put("inventory", this.itemHandler.serializeNBT());
	}

	@Override
	public void load(@Nonnull CompoundTag pTag) {
		super.load(pTag);
		this.running = pTag.getBoolean("running");
		this.itemHandler.deserializeNBT(pTag.getCompound("inventory"));
	}

	@Override
	@NotNull
	protected IEmberEmitter getEmitter() {
		return emitter;
	}

	public void updateViaState() {
		setChanged();
		BlockEntityUtil.updateViaState(this);
	}

	@Override
	public void clientTick() {
		if (running && level != null) {
			var random = level.getRandom();
			ParticleBuilders.create(LodestoneParticleRegistry.WISP_PARTICLE)
					.addMotion(0, 0.0525d * (random.nextDouble() * 0.1d), 0)
					.setAlpha(0.5f, 0.2f)
					.setScale(0.1f)
					.setColor(230 / 255.0f, 55 / 255.0f, 16 / 255.0f, 230 / 255.0f, 83 / 255.0f, 16 / 255.0f)
					.setLifetime(Math.round(random.nextFloat() * 100))
					.disableForcedMotion()
					.setSpin(0)
					.spawn(level, getBlockPos().getX() + (random.nextFloat()), getBlockPos().getY() + 1, getBlockPos().getZ() + random.nextFloat());
		}
	}

	@Override
	public void serverTick() {
		if (level != null && level.getGameTime() % 20 == 0) {
			if (!this.itemHandler.getStackInSlot(0).isEmpty()) {
				if (!this.running) {
					this.running = true;
					level.getCapability(EmbersCaps.EMBER).ifPresent(emitter::initEmitter);
				}
			}
			updateViaState();
		}
	}

	@Override
	public InteractionResult onUse(Player player, @NotNull InteractionHand hand) {
		ItemStack playerStack = player.getItemInHand(hand);
		if (this.itemHandler.isItemValid(0, playerStack)) {
			ItemStack returnStack = this.itemHandler.insertItem(0, playerStack, false);
			player.setItemInHand(hand, returnStack);
			updateViaState();
		}
		return InteractionResult.SUCCESS;
	}
}
