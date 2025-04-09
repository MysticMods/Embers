package mysticmods.embers.machines.caminite_forge;

import mysticmods.embers.base.IEmberIntesityEntity;
import mysticmods.embers.capabilities.emberintensity.EmberIntensity;
import mysticmods.embers.capabilities.emberlevel.EmberLevel;
import mysticmods.embers.capabilities.heated_metal.IHeatedMetalCap;
import mysticmods.embers.data.components.MalleableMetalDataComponent;
import mysticmods.embers.init.*;
import mysticmods.embers.machines.caminite_forge.menu.CaminiteForgeAlloyMenu;
import mysticmods.embers.machines.caminite_forge.menu.CaminiteForgeMenu;
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
import team.lodestar.lodestone.systems.easing.Easing;
import team.lodestar.lodestone.systems.multiblock.MultiBlockCoreEntity;
import team.lodestar.lodestone.systems.multiblock.MultiBlockStructure;
import team.lodestar.lodestone.systems.particle.builder.WorldParticleBuilder;
import team.lodestar.lodestone.systems.particle.data.GenericParticleData;
import team.lodestar.lodestone.systems.particle.data.color.ColorParticleData;
import team.lodestar.lodestone.systems.particle.render_types.LodestoneWorldParticleRenderType;
import team.lodestar.lodestone.systems.particle.world.options.WorldParticleOptions;

import java.awt.*;
import java.util.Optional;
import java.util.function.Supplier;

import static mysticmods.embers.utils.BEUtil.dropItemHandler;
import static mysticmods.embers.utils.BEUtil.updateViaState;

public class CaminiteForgeBlockEntity extends MultiBlockCoreEntity implements IEmberIntesityEntity {

    public static final Supplier<MultiBlockStructure> STRUCTURE = () -> MultiBlockStructure.of(new MultiBlockStructure.StructurePiece(0, 1, 0, EmbersBlocks.CAMINITE_FORGE_COMPONENT.get().defaultBlockState()));

    private final EmberIntensity intensity;
    private final ItemStackHandler itemHandler = new ForgeItemHandler(3, this) {
        @Override
        protected int getStackLimit(int slot, @NotNull ItemStack stack) {
            return 32;
        }
    };
    private EmberLevel emberLevel;

    private float progress = 0;
    private boolean isLit = false;
    private boolean alloyMode = false;
    private static final int PROGRESS_PER_ITEM = 20 * 5;

    public CaminiteForgeBlockEntity(BlockPos pos, BlockState blockState) {
        super(EmbersBlockEntities.CAMINITE_FORGE.get(), STRUCTURE.get(), pos, blockState);
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

    public void updateToClient() {
        if (!level.isClientSide()) {
            updateViaState(this);
        }
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
        if (this.itemHandler.getStackInSlot(0).isEmpty() && this.progress > 0) {
            this.progress = 0;
            updateViaState(this);
            return;
        }

        if(!this.alloyMode){
            if (!this.itemHandler.getStackInSlot(0).isEmpty()) {
                if (this.intensity.hasEmberForOperation()) {
                    progress++;

                    if (progress >= PROGRESS_PER_ITEM) {
                        ItemStack hotMetalStack = this.itemHandler.getStackInSlot(2);
                        ItemStack inputStack = this.itemHandler.getStackInSlot(0);
                        RecipeManager recipes = level.getRecipeManager();
                        Optional<RecipeHolder<MalleableMetalRecipe>> optional = recipes.getRecipeFor(
                                EmbersRecipeTypes.MALLEABLE_METAL.get(),
                                new SingleRecipeInput(this.itemHandler.getStackInSlot(0)),
                                level
                        );

                        if(optional.isPresent()){
                            MalleableMetalRecipe recipe = optional.get().value();
                            if (hotMetalStack.isEmpty()) {
                                this.itemHandler.setStackInSlot(2, new ItemStack(EmbersItems.HEATED_METAL.get(), 1));
                                hotMetalStack = this.itemHandler.getStackInSlot(2);

                                MalleableMetalDataComponent data = hotMetalStack.get(EmbersDataComponents.MALLEABLE_METAL);
                                data = data.setMalleableMetal(recipe.malleableMetal).setMaxHeat();
                                hotMetalStack.set(EmbersDataComponents.MALLEABLE_METAL, data);
                            }

                            MalleableMetalDataComponent data = hotMetalStack.get(EmbersDataComponents.MALLEABLE_METAL);
                            data = data.addIngots(recipe.getResultIngotAmount(inputStack))
                                    .addNuggets(recipe.getResultNuggetAmount(inputStack));
                            hotMetalStack.set(EmbersDataComponents.MALLEABLE_METAL, data);

                            inputStack.shrink(1);
                            progress = 0;
                        }
                    }

                    updateViaState(this);
                }
            }
        }
    }

    public void clientTick() {
        if (this.hasHotMetals() && this.level != null) {
            if (level.getGameTime() % 5 == 0) {
                var random = this.level.getRandom();
                int lifetime = RandomHelper.randomBetween(random, 60, 120);
                var options = new WorldParticleOptions(EmbersParticles.PARTICLE_GLOW);
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
                                getBlockPos().getX() + (0.25f + random.nextFloat() * 0.5f),
                                getBlockPos().getY() + 2,
                                getBlockPos().getZ() + (0.25f + random.nextFloat() * 0.5f),
                                1);
            }
        }
    }

    @Override
    public ItemInteractionResult onUse(Player pPlayer, InteractionHand pHand) {
        if(pHand == InteractionHand.MAIN_HAND) {
            this.openMenu(pPlayer);
        }
        return ItemInteractionResult.SUCCESS;
    }

    public void transferHeatedMetal(IHeatedMetalCap cap, Player player, ItemStack stack) {
        cap.setMaxHeat(100 * stack.getCount());
        cap.setStackHeat(100 * stack.getCount());
        this.itemHandler.setStackInSlot(2, ItemStack.EMPTY);

        player.addItem(stack);
        updateViaState(this);
    }

    public boolean hasHotMetals() {
        return !this.itemHandler.getStackInSlot(2).isEmpty();
    }

    @Override
    public EmberIntensity getEmberIntensity() {
        return intensity;
    }

    public ItemStackHandler getItemHandler() {
        return itemHandler;
    }

    public float getProgress() {
        if (this.progress > 0) {
            return this.progress / this.PROGRESS_PER_ITEM;
        }
        return 0;
    }

    public void toggleAlloyMode(Player player) {
        this.alloyMode = !this.alloyMode;
        updateViaState(this);

        openMenu(player);
    }

    public void openMenu(Player player){
        if (player instanceof ServerPlayer serverPlayer) {
            if(this.alloyMode){
                serverPlayer.openMenu(new MenuProvider() {
                    @Override
                    public @NotNull Component getDisplayName() {
                        return Component.translatable("block.embers.caminite_forge");
                    }

                    @Override
                    public AbstractContainerMenu createMenu(int windowId, @NotNull Inventory playerInventory, @NotNull Player player) {
                        return new CaminiteForgeAlloyMenu(windowId, playerInventory, CaminiteForgeBlockEntity.this);
                    }
                }, buf -> buf.writeBlockPos(getBlockPos()));
            } else {
                serverPlayer.openMenu(new MenuProvider() {
                    @Override
                    public @NotNull Component getDisplayName() {
                        return Component.translatable("block.embers.caminite_forge");
                    }

                    @Override
                    public AbstractContainerMenu createMenu(int windowId, @NotNull Inventory playerInventory, @NotNull Player player) {
                        return new CaminiteForgeMenu(windowId, playerInventory, CaminiteForgeBlockEntity.this);
                    }
                }, buf -> buf.writeBlockPos(getBlockPos()));
            }
        }
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag tag, HolderLookup.@NotNull Provider registries) {
        super.saveAdditional(tag, registries);
        tag.putFloat("progress", this.progress);
        tag.putBoolean("isLit", this.isLit);
        tag.putBoolean("alloyMode", this.alloyMode);
        tag.put("inventory", this.itemHandler.serializeNBT(registries));
        tag.put("emberIntensity", this.intensity.serializeNBT(registries));
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        this.progress = tag.getFloat("progress");
        this.isLit = tag.getBoolean("isLit");
        this.alloyMode = tag.contains("alloyMode") ? tag.getBoolean("alloyMode") : false;
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
        if (emberLevel != null) {
            emberLevel.removeEmberListener(getBlockPos(), this.intensity);
        }

        dropItemHandler(this, itemHandler);
    }
}
