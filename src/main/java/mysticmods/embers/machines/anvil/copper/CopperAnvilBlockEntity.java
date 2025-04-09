package mysticmods.embers.machines.anvil.copper;

import mysticmods.embers.capabilities.emberintensity.EmberIntensity;
import mysticmods.embers.data.components.MalleableMetalDataComponent;
import mysticmods.embers.init.EmbersBlockEntities;
import mysticmods.embers.init.EmbersDataComponents;
import mysticmods.embers.init.EmbersItems;
import mysticmods.embers.init.EmbersParticles;
import mysticmods.embers.machines.anvil.AnvilItemHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import team.lodestar.lodestone.helpers.RandomHelper;
import team.lodestar.lodestone.systems.blockentity.LodestoneBlockEntity;
import team.lodestar.lodestone.systems.easing.Easing;
import team.lodestar.lodestone.systems.particle.builder.WorldParticleBuilder;
import team.lodestar.lodestone.systems.particle.data.GenericParticleData;
import team.lodestar.lodestone.systems.particle.data.color.ColorParticleData;
import team.lodestar.lodestone.systems.particle.render_types.LodestoneWorldParticleRenderType;
import team.lodestar.lodestone.systems.particle.world.options.WorldParticleOptions;

import java.awt.*;

import static mysticmods.embers.utils.BEUtil.updateViaState;

public class CopperAnvilBlockEntity extends LodestoneBlockEntity {

    private final EmberIntensity ember = new EmberIntensity(100, 100,null);
    public final AnvilItemHandler itemHandler = new AnvilItemHandler(6, this) {
        @Override
        protected int getStackLimit(int slot, @NotNull ItemStack stack) {
            return 1;
        }
    };

    public CopperAnvilBlockEntity(BlockPos pos, BlockState state) {
        super(EmbersBlockEntities.COPPER_ANVIL.get(), pos, state);
    }

    @Override
    public ItemInteractionResult onUse(Player player, InteractionHand hand) {
        ItemStack playerStack = player.getItemInHand(hand);

        if (playerStack.getItem() == EmbersItems.IRON_HAMMER.get()) {
            if (this.itemHandler.getFilledSlotAmount() == 1) {
                MalleableMetalDataComponent data = this.itemHandler.getStackInSlot(0).get(EmbersDataComponents.MALLEABLE_METAL);
                if(data != null){
                    smithIngot(this.itemHandler.getStackInSlot(0), data, player);
                    data = this.itemHandler.getStackInSlot(0).get(EmbersDataComponents.MALLEABLE_METAL);
                    if(data != null){
                        if(data.getIngots() == 0 && data.getNuggets() == 0){
                            this.itemHandler.setStackInSlot(0, ItemStack.EMPTY);
                        }
                    }
                    updateViaState(this);
                }
                return ItemInteractionResult.CONSUME;
            }
        }

        //If hand is empty return the itemstack from itemhandler to the players hand
        if(playerStack == ItemStack.EMPTY){
            player.setItemInHand(hand, this.itemHandler.extractItem(0, 1, false));
            updateViaState(this);
            return ItemInteractionResult.SUCCESS;
        }

        player.setItemInHand(hand, this.itemHandler.addItemStack(playerStack));
        updateViaState(this);
        return ItemInteractionResult.SUCCESS;
    }

    public void smithIngot(ItemStack stack, MalleableMetalDataComponent metalData, Player player) {
        if(metalData.getIngots() > 0){
            player.addItem(metalData.getMalleableMetal().ingot.getItems()[0].copy());
            metalData = metalData.removeIngots(1);
            stack.set(EmbersDataComponents.MALLEABLE_METAL, metalData);
        } else {
            ItemStack nuggets = metalData.getMalleableMetal().nugget.getItems()[0].copy();
            nuggets.grow(metalData.getNuggets() - 1);
            player.addItem(nuggets);
            metalData = metalData.removeNuggets(metalData.getNuggets());
            stack.set(EmbersDataComponents.MALLEABLE_METAL, metalData);
        }

        player.swing(InteractionHand.MAIN_HAND, true);player.level().playSound(
                null,
                player.getX(),
                player.getY(),
                player.getZ(),
                SoundEvents.ANVIL_BREAK,
                SoundSource.PLAYERS,
                1.0f,
                1.0f
        );

        if(level.isClientSide()){
            for (int i = 0; i < 20; i++) {
                double speedX = (level.random.nextDouble() - 0.5) * 0.4;
                double speedY = level.random.nextDouble() * 0.1 + 0.08f;
                double speedZ = (level.random.nextDouble() - 0.5) * 0.4;

                var random = this.level.getRandom();
                int lifetime = RandomHelper.randomBetween(random, 10, 30);
                var options = new WorldParticleOptions(EmbersParticles.PARTICLE_GLOW);
                final float scale = 0.05f;
                WorldParticleBuilder.create(options)
                        .setTransparencyData(GenericParticleData.create(1f, 0.2f, 0).build())
                        .setColorData(ColorParticleData.create(Color.RED).setCoefficient(4f).build())
                        .setScaleData(GenericParticleData.create( scale / 2f, scale, 0.2f).setCoefficient(1.25f).setEasing(Easing.EXPO_OUT, Easing.EXPO_IN).build())
                        .setRenderType(LodestoneWorldParticleRenderType.ADDITIVE)
                        .setRandomOffset(0, 0)
                        .setMotion(speedX, speedY, speedZ)
                        .setLifetime(lifetime)
                        .enableNoClip()
                        .setGravityStrength(0.15f)
                        .repeat(level,
                                getBlockPos().getX() + 0.5f,
                                getBlockPos().getY() + 0.9f,
                                getBlockPos().getZ() + 0.5f,
                                1);
            }
        }
    }

    public EmberIntensity getEmber() {
        return ember;
    }

    public AnvilItemHandler getItemHandler() {
        return itemHandler;
    }
}
