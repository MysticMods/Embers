package mysticmods.embers.machines.caminite_smelter;

import mysticmods.embers.api.base.IEmberIntesityEntity;
import mysticmods.embers.api.base.block_entities.IntensityBlockEntity;
import mysticmods.embers.capabilities.emberintensity.EmberIntensity;
import mysticmods.embers.capabilities.emberlevel.EmberLevel;
import mysticmods.embers.data.components.MalleableMetalDataComponent;
import mysticmods.embers.init.*;
import mysticmods.embers.machines.caminite_smelter.menu.CaminiteSmelterMenu;
import mysticmods.embers.recipes.malleable_metal.MalleableMetalRecipe;
import mysticmods.embers.utils.SDUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.IntTag;
import net.minecraft.network.Connection;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.level.block.state.BlockState;
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
import java.util.Optional;

import static mysticmods.embers.utils.BEUtil.dropItemHandler;
import static mysticmods.embers.utils.BEUtil.updateViaState;

public class CaminiteSmelterBlockEntity extends IntensityBlockEntity{

    private final ItemStackHandler itemHandler = new ItemStackHandler(3) {
        @Override
        protected int getStackLimit(int slot, @NotNull ItemStack stack) {
            return 32;
        }

        @Override
        protected void onContentsChanged(int slot) {
            super.onContentsChanged(slot);
            CaminiteSmelterBlockEntity.this.itemHandlerUpdate();
        }
    };

    private float progress = 0;
    private boolean isLit = false;
    private static final int PROGRESS_PER_ITEM = 20 * 5;

    public CaminiteSmelterBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.CAMINITE_SMELTER.get(), pos, state, 100, 100);
    }

    @Override
    public void tick() {
        if (level.isClientSide()) {
            clientTick();
        } else {
            serverTick();
        }
    }

    public void serverTick() {
        if (this.isLit && this.intensity.hasEmberForOperation()) {
            this.progress++;

            if (this.progress >= PROGRESS_PER_ITEM) {
                ItemStack hotMetalStack = this.itemHandler.getStackInSlot(2);
                ItemStack inputStack = this.itemHandler.getStackInSlot(0);
                RecipeManager recipes = level.getRecipeManager();
                Optional<RecipeHolder<MalleableMetalRecipe>> optional = recipes.getRecipeFor(
                        ModRecipeTypes.MALLEABLE_METAL.get(),
                        new SingleRecipeInput(this.itemHandler.getStackInSlot(0)),
                        level
                );

                if (optional.isPresent()) {
                    MalleableMetalRecipe recipe = optional.get().value();
                    if (hotMetalStack.isEmpty()) {
                        this.itemHandler.setStackInSlot(2, new ItemStack(ModItems.HEATED_METAL.get(), 1));
                        hotMetalStack = this.itemHandler.getStackInSlot(2);

                        MalleableMetalDataComponent data = hotMetalStack.get(ModDataComponents.MALLEABLE_METAL);
                        data = data.setMalleableMetal(recipe.malleableMetal).setMaxHeat();
                        hotMetalStack.set(ModDataComponents.MALLEABLE_METAL, data);
                    }

                    MalleableMetalDataComponent data = hotMetalStack.get(ModDataComponents.MALLEABLE_METAL);
                    data = data.addIngots(recipe.getResultIngotAmount(inputStack))
                            .addNuggets(recipe.getResultNuggetAmount(inputStack));
                    hotMetalStack.set(ModDataComponents.MALLEABLE_METAL, data);

                    inputStack.shrink(1);
                    this.progress = 0;
                    this.itemHandlerUpdate();
                }
            }

            updateViaState(this);
        }
    }


    public void clientTick() {
        if (this.hasHotMetals() && this.level != null) {
            if (level.getGameTime() % 5 == 0) {
                var random = this.level.getRandom();
                int lifetime = RandomHelper.randomBetween(random, 60, 120);
                var options = new WorldParticleOptions(ModParticles.PARTICLE_GLOW);
                final float scale = 0.2f;
                WorldParticleBuilder.create(options)
                        .setTransparencyData(GenericParticleData.create(0.1f, 0.4f, 0).build())
                        .setColorData(ColorParticleData.create(Color.RED).setCoefficient(4f).build())
                        .setScaleData(GenericParticleData.create(scale / 2f, scale, 0.2f).setCoefficient(1.25f).setEasing(Easing.EXPO_OUT, Easing.EXPO_IN).build())
                        .setRenderType(LodestoneWorldParticleRenderType.ADDITIVE)
                        .setRandomOffset(0, 0.5f)
                        .setMotion(0, 0.25d * (random.nextDouble() * 0.1d), 0)
                        .setLifetime(lifetime)
                        .enableNoClip()
                        .repeat(level,
                                getBlockPos().getX() + (0.25f + random.nextFloat() * 0.5f),
                                getBlockPos().getY() + 1,
                                getBlockPos().getZ() + (0.25f + random.nextFloat() * 0.5f),
                                1);
            }
        }
    }

    public void itemHandlerUpdate() {
        if (this.itemHandler.getStackInSlot(0).isEmpty()) {
            this.progress = 0;
            this.isLit = false;
        } else {
            ItemStack inputStack = this.itemHandler.getStackInSlot(0);
            ItemStack outputStack = this.itemHandler.getStackInSlot(2);
            RecipeManager recipes = level.getRecipeManager();
            Optional<RecipeHolder<MalleableMetalRecipe>> optional = recipes.getRecipeFor(
                    ModRecipeTypes.MALLEABLE_METAL.get(),
                    new SingleRecipeInput(inputStack),
                    level
            );

            if (optional.isPresent() && (optional.get().value().getResultItem(null).is(outputStack.getItem()) || outputStack.isEmpty())) {
                this.isLit = true;
            } else {
                this.isLit = false;
                this.progress = 0;
            }
        }

        updateViaState(this);
    }

    @Override
    public ItemInteractionResult onUse(Player pPlayer, InteractionHand pHand) {
        if (pHand == InteractionHand.MAIN_HAND) {
            this.openMenu(pPlayer);
        }
        return ItemInteractionResult.SUCCESS;
    }

    public boolean hasHotMetals() {
        return !this.itemHandler.getStackInSlot(2).isEmpty();
    }

    public ItemStackHandler getItemHandler() {
        return itemHandler;
    }

    public float getProgress() {
        if (this.progress > 0) {
            return this.progress / PROGRESS_PER_ITEM;
        }
        return 0;
    }

    public void openMenu(Player player) {
        if (player instanceof ServerPlayer serverPlayer) {
            serverPlayer.openMenu(new MenuProvider() {
                @Override
                public @NotNull net.minecraft.network.chat.Component getDisplayName() {
                    return Component.translatable("block.embers.caminite_forge");
                }

                @Override
                public AbstractContainerMenu createMenu(int windowId, @NotNull Inventory playerInventory, @NotNull Player player) {
                    return new CaminiteSmelterMenu(windowId, playerInventory, CaminiteSmelterBlockEntity.this);
                }
            }, buf -> buf.writeBlockPos(getBlockPos()));
        }
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag tag, HolderLookup.@NotNull Provider registries) {
        super.saveAdditional(tag, registries);
        tag.putFloat("progress", this.progress);
        tag.putBoolean("isLit", this.isLit);
        tag.put("inventory", this.itemHandler.serializeNBT(registries));
        tag.put("emberIntensity", this.intensity.serializeNBT(registries));
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        this.progress = tag.getFloat("progress");
        this.isLit = tag.getBoolean("isLit");
        this.itemHandler.deserializeNBT(registries, tag.getCompound("inventory"));
        this.intensity.deserializeNBT(registries, IntTag.valueOf(tag.getInt("emberIntensity")));
    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt, HolderLookup.Provider lookupProvider) {
        super.onDataPacket(net, pkt, lookupProvider);
        CompoundTag tag = pkt.getTag();
        loadAdditional(tag, lookupProvider);
    }

    @Override
    public void onBreak(@Nullable Player player) {
        super.onBreak(player);

        dropItemHandler(this, itemHandler);
    }
}
