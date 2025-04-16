package mysticmods.embers.machines.caminite_mold;

import mysticmods.embers.Embers;
import mysticmods.embers.capabilities.emberintensity.EmberIntensity;
import mysticmods.embers.capabilities.emberlevel.EmberLevel;
import mysticmods.embers.init.ModBlockEntities;
import mysticmods.embers.init.ModParticles;
import mysticmods.embers.init.ModRecipeTypes;
import mysticmods.embers.recipes.mold.MoldRecipe;
import mysticmods.embers.recipes.mold.MoldRecipeInput;
import mysticmods.embers.utils.SDUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemStackHandler;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

import static mysticmods.embers.utils.BEUtil.dropItemHandler;
import static mysticmods.embers.utils.BEUtil.updateViaState;

public class CaminiteMoldBlockEntity extends LodestoneBlockEntity {

    private final EmberIntensity intensity;
    private EmberLevel emberLevel;

    private final ItemStackHandler itemHandler;
    private MoldRecipe currentRecipe;

    private int progress = 0;
    private final int maxProgress = 15 * 20;

    public CaminiteMoldBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.CAMINITE_MOLD.get(), pos, state);

        itemHandler = new ItemStackHandler(9) {

            @Override
            public int getSlotLimit(int slot) {
                return 1;
            }

            @Override
            protected void onContentsChanged(int slot) {
                CaminiteMoldBlockEntity.this.onContentsChanged();
                super.onContentsChanged(slot);
            }
        };
        this.intensity = new EmberIntensity(100, 100, this::updateToClient);
    }

    @Override
    public void onLoad() {
        emberLevel = SDUtil.getLevelEmbersData(level);
        if (emberLevel != null) {
            emberLevel.addEmberListener(getBlockPos(), this.intensity);
        }

        updateViaState(this);
    }

    @Override
    public void tick() {
        if(level.isClientSide) {
            if(this.currentRecipe != null && level.getGameTime() % 5 == 0) {
                //Spawn particles
                var random = this.level.getRandom();
                int lifetime = RandomHelper.randomBetween(random, 20, 40);
                var options = new WorldParticleOptions(ModParticles.PARTICLE_GLOW);
                final float scale = 0.2f;
                final double SPEED = 0.05f;

                float offsetX = random.nextFloat() * 4.0f - 2.0f;
                float offsetZ = random.nextFloat() * 4.0f - 2.0f;
                float offsetY = random.nextFloat() * 4.0f - 2.0f;

                double spawnX = getBlockPos().getX() + offsetX;
                double spawnY = getBlockPos().getY() + offsetY;
                double spawnZ = getBlockPos().getZ() + offsetZ;

                double centerX = getBlockPos().getX() + 0.5;
                double centerY = getBlockPos().getY() + 0.5;
                double centerZ = getBlockPos().getZ() + 0.5;

                Consumer<WorldParticleBuilder> behavior = b -> b.addTickActor(p -> {

                    double dx = centerX - p.getX();
                    double dy = centerY - p.getY();
                    double dz = centerZ - p.getZ();

                    double distance = Math.sqrt(dx * dx + dy * dy + dz * dz);


                    if (distance <= SPEED) {
                        p.setPos(centerX, centerY, centerZ);
                        return;
                    }

                    double moveX = dx / distance * SPEED;
                    double moveY = dy / distance * SPEED;
                    double moveZ = dz / distance * SPEED;

                    // Move particle manually
                    p.setParticleSpeed(moveX, moveY, moveZ);
                });

                WorldParticleBuilder.create(options)
                        .act(behavior)
                        .setTransparencyData(GenericParticleData.create(0.8f, 0.7f, 0).build())
                        .setColorData(ColorParticleData.create(Color.RED).setCoefficient(4f).build())
                        .setScaleData(GenericParticleData.create(scale / 2f, scale, 0.2f).setCoefficient(1.25f).setEasing(Easing.EXPO_OUT, Easing.EXPO_IN).build())
                        .setRenderType(LodestoneWorldParticleRenderType.ADDITIVE)
                        .setLifetime(lifetime)
                        .enableNoClip()
                        .repeat(level,
                                spawnX,
                                spawnY,
                                spawnZ,
                                1);
            }
        } else {
            if(this.currentRecipe != null) {
                if (this.progress < this.maxProgress) {
                    this.progress++;
                } else {

                    ItemStack output = this.currentRecipe.getOutput().copy();
                    level.addFreshEntity(new ItemEntity(level, getBlockPos().getX(), getBlockPos().getY(), getBlockPos().getZ(), output));

                    for (int i = 0; i < this.itemHandler.getSlots(); i++) {
                        ItemStack itemStack = this.itemHandler.getStackInSlot(i);
                        if (!itemStack.isEmpty()) {
                            itemStack.shrink(1);
                        }
                    }

                    this.currentRecipe = null;
                    this.progress = 0;
                    level.destroyBlock(getBlockPos(), false);
                }
            }
        }

    }

    @Override
    public ItemInteractionResult onUseWithItem(Player pPlayer, ItemStack pStack, InteractionHand pHand) {
        if (pStack.isEmpty()) {
            return super.onUseWithItem(pPlayer, pStack, pHand);
        }
        //Add the item to the first avilable slot
        for (int i = 0; i < this.itemHandler.getSlots(); i++) {
            ItemStack itemStack = this.itemHandler.getStackInSlot(i);
            if (itemStack.isEmpty()) {
                this.itemHandler.setStackInSlot(i, pStack.split(1));
                return ItemInteractionResult.SUCCESS;
            }
        }
        return super.onUseWithItem(pPlayer, pStack, pHand);
    }

    @Override
    public InteractionResult onUseWithoutItem(Player pPlayer) {
        if(!pPlayer.isCrouching()){
            return super.onUseWithoutItem(pPlayer);
        }

        for (int i = this.itemHandler.getSlots() -1; i >= 0; i--) {
            ItemStack returnItem = this.itemHandler.getStackInSlot(i);
            if(!returnItem.isEmpty()) {
                if (!pPlayer.getInventory().add(returnItem)) {
                    pPlayer.drop(returnItem, false);
                }
                this.itemHandler.setStackInSlot(i, ItemStack.EMPTY);
                return InteractionResult.SUCCESS;
            }
        }

        return super.onUseWithoutItem(pPlayer);
    }

    public void onContentsChanged(){
        this.currentRecipe = null;

        List<ItemStack> inputs = new ArrayList<>();
        for (int i = 0; i < this.itemHandler.getSlots(); i++) {
            ItemStack itemStack = this.itemHandler.getStackInSlot(i);
            if (!itemStack.isEmpty()) {
                inputs.add(itemStack
                );
            }
        }

        RecipeManager recipes = level.getRecipeManager();
        Optional<RecipeHolder<MoldRecipe>> optional = recipes.getRecipeFor(
                ModRecipeTypes.MOLD.get(),
                new MoldRecipeInput(inputs),
                level
        );

        if(optional.isPresent()) {
            currentRecipe = optional.get().value();
        } else {
            currentRecipe = null;
        }

        updateViaState(this);
    }


    public void updateToClient() {
        if (!level.isClientSide()) {
            updateViaState(this);
        }
    }

    public @Nullable IItemHandler getItemHandler() {
        return this.itemHandler;
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.put("itemHandler", itemHandler.serializeNBT(registries));
        tag.putInt("progress", this.progress);
        if(this.currentRecipe != null) {
            MoldRecipe.CODEC.encodeStart(NbtOps.INSTANCE, this.currentRecipe)
                    .resultOrPartial(error -> Embers.LOGGER.warn("Failed to save recipe: {}", error))
                    .ifPresent(recipeTag -> tag.put("currentRecipe", recipeTag));
        }
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        if (tag.contains("itemHandler")) {
            itemHandler.deserializeNBT(registries, tag.getCompound("itemHandler"));
        }
        if (tag.contains("progress")) {
            this.progress = tag.getInt("progress");
        }
        if (tag.contains("currentRecipe")) {
            MoldRecipe.CODEC.parse(NbtOps.INSTANCE, tag.get("currentRecipe"))
                    .resultOrPartial(error -> Embers.LOGGER.warn("Failed to load recipe: {}", error))
                    .ifPresent(recipe -> this.currentRecipe = (MoldRecipe) recipe);
        }
    }

    @Override
    public void onBreak(@Nullable Player player) {
        super.onBreak(player);
        if (emberLevel != null) {
            emberLevel.removeEmberListener(getBlockPos(), this.intensity);
        }

        dropItemHandler(this, itemHandler);
    }
}
