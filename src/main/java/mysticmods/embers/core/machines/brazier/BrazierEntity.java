package mysticmods.embers.core.machines.brazier;

import mysticmods.embers.core.base.EmberBlockEntity;
import mysticmods.embers.core.utils.TickBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.items.ItemStackHandler;
import noobanidus.libs.noobutil.util.BlockEntityUtil;
import org.jetbrains.annotations.NotNull;
import team.lodestar.lodestone.setup.LodestoneParticleRegistry;
import team.lodestar.lodestone.systems.rendering.particle.ParticleBuilders;

import javax.annotation.Nonnull;

public class BrazierEntity extends EmberBlockEntity implements TickBlockEntity {

	public boolean running = false;
	private int emberOutput = 0;

	private final ItemStackHandler itemHandler;

	public BrazierEntity(BlockEntityType<?> pType, BlockPos pWorldPosition, BlockState pBlockState) {
		super(pType, pWorldPosition, pBlockState);

		itemHandler =  new ItemStackHandler(1){
			@Override
			public int getSlotLimit(int slot) {
				return 16;
			}

			@Override
			public boolean isItemValid(int slot, @NotNull ItemStack stack) {
				return stack.getItem() == Items.COAL;
			}
		};
	}

	@Override
	public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
		super.onDataPacket(net, pkt);
		CompoundTag tag = pkt.getTag();
		if (tag != null) {
			load(tag);
		} else {
			running = false;
			emberOutput = 0;
		}
	}

	@Override
	protected void saveAdditional(@Nonnull CompoundTag pTag) {
		super.saveAdditional(pTag);
		pTag.putBoolean("running", this.running);
		pTag.putInt("emberOutput", this.emberOutput);
		pTag.put("inventory", this.itemHandler.serializeNBT());
	}

	@Override
	public void load(@Nonnull CompoundTag pTag) {
		super.load(pTag);
		this.running = pTag.getBoolean("running");
		this.emberOutput = pTag.getInt("emberOutput");
		this.itemHandler.deserializeNBT(pTag.getCompound("inventory"));

	}

	public void updateViaState() {
		setChanged();
		BlockEntityUtil.updateViaState(this);
	}

	@Override
	public void clientTick(Level level, BlockPos blockPos, BlockState blockState) {
		if (running) {
			var random = level.getRandom();
			ParticleBuilders.create(LodestoneParticleRegistry.WISP_PARTICLE)
							.addMotion(0, 0.0525d * (random.nextDouble() * 0.1d), 0)
							.setAlpha(0.5f, 0.2f)
							.setScale(0.1f)
							.setColor(230 / 255.0f, 55 / 255.0f, 16 / 255.0f, 230 / 255.0f, 83 / 255.0f, 16 / 255.0f)
							.setLifetime(Math.round(random.nextFloat() * 100))
							.disableForcedMotion()
							.setSpin(0)
							.spawn(level, blockPos.getX() + (random.nextFloat()), blockPos.getY() + 1, blockPos.getZ() + random.nextFloat());
		}
	}

	@Override
	public void serverTick(Level level, BlockPos blockPos, BlockState blockState) {
		if (level.getGameTime() % 20 == 0) {
			if (!this.itemHandler.getStackInSlot(0).isEmpty()) {
				if (!this.running) {
					this.running = true;
				}
			} else {
				if (this.running) {
					running = false;
				}
			}

			if (running) {
				this.emberOutput = 100;
			}
			updateViaState();
		}
	}

	@Override
	public void use(Player player, InteractionHand hand) {
		System.out.println(this.running);
		ItemStack playerStack = player.getItemInHand(hand);
		if(this.itemHandler.isItemValid(0, playerStack)){
			ItemStack returnStack = this.itemHandler.insertItem(0, playerStack, false);
			player.setItemInHand(hand, returnStack);
			updateViaState();
		}
	}

	public int getEmberOutputForMachine(BlockPos targetPos) {
		int xDistance = Math.abs(this.getBlockPos().getX() - targetPos.getX());
		int zDistance = Math.abs(this.getBlockPos().getZ() - targetPos.getZ());
		int highestDistance = Math.max(xDistance, zDistance);
		highestDistance += Math.abs(this.getBlockPos().getY() - targetPos.getY());
		return this.emberOutput - (20 * (highestDistance - 1));
	}
}
