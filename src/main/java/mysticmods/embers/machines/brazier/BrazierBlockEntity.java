package mysticmods.embers.machines.brazier;

import mysticmods.embers.api.base.IEmberEmitterEntity;
import mysticmods.embers.capabilities.emberemitter.EmberEmitter;
import mysticmods.embers.capabilities.emberlevel.EmberLevel;
import mysticmods.embers.init.ModBlockEntities;
import mysticmods.embers.init.ModParticles;
import mysticmods.embers.utils.SDUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import team.lodestar.lodestone.helpers.RandomHelper;
import team.lodestar.lodestone.systems.blockentity.LodestoneBlockEntity;
import team.lodestar.lodestone.systems.easing.Easing;
import team.lodestar.lodestone.systems.particle.builder.WorldParticleBuilder;
import team.lodestar.lodestone.systems.particle.data.GenericParticleData;
import team.lodestar.lodestone.systems.particle.data.color.ColorParticleData;
import team.lodestar.lodestone.systems.particle.render_types.LodestoneWorldParticleRenderType;
import team.lodestar.lodestone.systems.particle.world.options.WorldParticleOptions;

import java.awt.*;

import static mysticmods.embers.utils.BEUtil.dropItemHandler;
import static mysticmods.embers.utils.BEUtil.updateViaState;

public class BrazierBlockEntity extends LodestoneBlockEntity implements IEmberEmitterEntity {

    private final EmberEmitter emitter;
    private final ItemStackHandler itemHandler;
    private static final RandomSource random = RandomSource.create();
    private EmberLevel emberLevel;

    public boolean running = false;
    public final int maxBurnTime = 1200;
    public int ticksToBurn = 0;

    public BrazierBlockEntity(BlockPos pos, BlockState blockState) {
        super(ModBlockEntities.BRAZIER.get(), pos, blockState);

        itemHandler = new ItemStackHandler(1) {
            @Override
            public int getSlotLimit(int slot) {
                return 16;
            }
            @Override
            public boolean isItemValid(int slot, @NotNull ItemStack stack) {
                return stack.getItem() == Items.COAL || stack.getItem() == Items.CHARCOAL;
            }
        };

        BlockPos lowerBound = getBlockPos().offset(-3, -3, -3);
        BlockPos upperBound = getBlockPos().offset(3, 3, 3);
        emitter = new EmberEmitter(new int[]{100, 100, 100, 50}, getBlockPos(), new BoundingBox(lowerBound.getX(), lowerBound.getY(), lowerBound.getZ(), upperBound.getX(), upperBound.getY(), upperBound.getZ()), () -> running);
    }

    @Override
    public void onLoad() {
        super.onLoad();

        emberLevel = SDUtil.getLevelEmbersData(level);
        if(emberLevel != null){
            emitter.initEmitter(emberLevel);
        }
        updateViaState(this);
    }

    @Override
    public void tick() {
        if(!level.isClientSide()) {
            if(running){
                ticksToBurn++;
                if (ticksToBurn >= maxBurnTime) {
                    ticksToBurn = 0;
                    ItemStack stack = itemHandler.getStackInSlot(0);
                    if (!stack.isEmpty()) {
                        stack.shrink(1);
                        updateViaState(this);
                    } else {
                        onFuelChange();
                    }
                }
            }
        } else {
            if (level.getGameTime() % 40 == 0 && running) {
                var random = this.level.getRandom();
                int lifetime = RandomHelper.randomBetween(random, 60, 120);
                var options = new WorldParticleOptions(ModParticles.PARTICLE_GLOW);
                final float scale = 0.2f;
                WorldParticleBuilder.create(options)
                        .setTransparencyData(GenericParticleData.create(0.1f, 0.4f, 0).build())
                        .setColorData(ColorParticleData.create(Color.RED).setCoefficient(4f).build())
                        .setScaleData(GenericParticleData.create( scale / 2f, scale, 0.2f).setCoefficient(1.25f).setEasing(Easing.EXPO_OUT, Easing.EXPO_IN).build())
                        .setRenderType(LodestoneWorldParticleRenderType.ADDITIVE)
                        .setRandomOffset(0, 0.5f)
                        .setMotion(0, 0.25d * (random.nextDouble() * 0.1d), 0)
                        .setLifetime(lifetime)
                        .enableNoClip()
                        .repeat(level,
                                getBlockPos().getX()  + 0.5f + Mth.nextFloat(random, -0.3f, 0.3f),
                                getBlockPos().getY() + 0.6f,
                                getBlockPos().getZ() + 0.5f + Mth.nextFloat(random, -0.3f, 0.3f),
                                1);
            }
        }
    }

    @Override
    public ItemInteractionResult onUseWithItem(Player player, ItemStack stack, InteractionHand hand) {
        ItemStack playerStack = player.getItemInHand(hand);

        if (this.itemHandler.isItemValid(0, playerStack)) {
            ItemStack returnStack = this.itemHandler.insertItem(0, playerStack, false);
            player.setItemInHand(hand, returnStack);
            updateViaState(this);

            onFuelChange();
            return ItemInteractionResult.CONSUME;
        }
        return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
    }

    @Override
    public void onBreak(@Nullable Player player) {
        super.onBreak(player);
        if(this.emberLevel != null){
            this.emberLevel.removeEmitterListener(this.emitter);
        }
        dropItemHandler(this, itemHandler);
    }

    /*
     * This method is called when the item handler is changed. It checks if the item handler is empty or not and sets the running state accordingly.
     */
    private void onFuelChange(){
        if (this.itemHandler.getStackInSlot(0).isEmpty() && running) {
            this.running = false;
            this.emitter.deactivate(emberLevel);
            level.setBlock(getBlockPos(), getBlockState().setValue(BrazierBlock.LIT, false), Block.UPDATE_ALL);
            emberLevel.clearEmberInBoundingBox(this.emitter.getBoundingBox());
            updateViaState(this);
        }

        else if(!this.itemHandler.getStackInSlot(0).isEmpty() && !running){
            this.running = true;
            level.setBlock(getBlockPos(), getBlockState().setValue(BrazierBlock.LIT, true), Block.UPDATE_ALL);

            ItemStack stack = itemHandler.getStackInSlot(0);
            if (!stack.isEmpty()) {
                stack.shrink(1);
            }

            if(emberLevel != null){
                this.emitter.initEmitter(emberLevel);
            }
            updateViaState(this);
        }
    }

    //Loading and saving
    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        running = tag.getBoolean("running");
        ticksToBurn = tag.getInt("ticksToBurn");
        itemHandler.deserializeNBT(registries, tag.getCompound("inventory"));
        emitter.deserializeNBT(registries, tag.getCompound("emitter"));
        if (emberLevel != null) {
           emberLevel.deserializeNBT(registries, tag.getList("emberLevel", Tag.TAG_LIST));
        }
        super.loadAdditional(tag, registries);
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.@NotNull Provider registries) {
        tag.putBoolean("running", running);
        tag.putInt("ticksToBurn", ticksToBurn);
        tag.put("inventory", itemHandler.serializeNBT(registries));
        tag.put("emitter", emitter.serializeNBT(registries));

        if (emberLevel != null) {
            tag.put("emberLevel", emberLevel.serializeNBT(registries));
        }

        super.saveAdditional(tag, registries);
    }

    @Override
    public @NotNull CompoundTag getUpdateTag(HolderLookup.@NotNull Provider registries) {
        CompoundTag tag = super.getUpdateTag(registries);
        this.saveAdditional(tag, registries);
        return tag;
    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt, HolderLookup.Provider lookupProvider) {
        super.onDataPacket(net, pkt, lookupProvider);
        CompoundTag tag = pkt.getTag();
        loadAdditional(tag, lookupProvider);
    }

    //Emitters
    @Override
    public @NotNull EmberEmitter getEmitter() {
        return emitter;
    }

    public ItemStackHandler getItemHandler() {
        return itemHandler;
    }
}
