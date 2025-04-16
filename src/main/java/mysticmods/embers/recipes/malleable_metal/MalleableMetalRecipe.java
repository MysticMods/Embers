package mysticmods.embers.recipes.malleable_metal;

import mysticmods.embers.init.ModItems;
import mysticmods.embers.init.ModRecipeTypes;
import mysticmods.embers.init.ModSerializers;
import mysticmods.embers.registries.MalleableMetal;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;

public class MalleableMetalRecipe implements Recipe<RecipeInput> {

    public final MalleableMetal malleableMetal;
    public final float experience;
    public final int processingTime;

    public MalleableMetalRecipe(MalleableMetal malleableMetal, float experience, int processingTime) {
        this.malleableMetal = malleableMetal;
        this.experience = experience;
        this.processingTime = processingTime;
    }

    public int getResultIngotAmount(ItemStack stack) {
        if (stack.getItem() instanceof BlockItem blockItem) {
            Block block = blockItem.getBlock();
            for (Holder<Block> holder : BuiltInRegistries.BLOCK.getTagOrEmpty(this.malleableMetal.getOreTag())) {
                if (holder.value() == block) {
                    return 1;
                }
            }
        }

        if(this.malleableMetal.getIngot().test(stack)){
            return 1;
        }

        return 0;
    }

    public int getResultNuggetAmount(ItemStack stack) {
        if (stack.getItem() instanceof BlockItem blockItem) {
            Block block = blockItem.getBlock();
            for (Holder<Block> holder : BuiltInRegistries.BLOCK.getTagOrEmpty(this.malleableMetal.getOreTag())) {
                if (holder.value() == block) {
                    return 3;
                }
            }
        }

        if(this.malleableMetal.getNugget().test(stack)){
            return 1;
        }

        return 0;
    }

    @Override
    public boolean matches(RecipeInput input, @NotNull Level level) {
        return this.malleableMetal.matches(input.getItem(0));
    }

    @Override
    public @NotNull ItemStack assemble(@NotNull RecipeInput input, HolderLookup.@NotNull Provider registries) {
        return new ItemStack(ModItems.HEATED_METAL.get());
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return true;
    }

    @Override
    public @NotNull ItemStack getResultItem(HolderLookup.@NotNull Provider registries) {
        return new ItemStack(ModItems.HEATED_METAL.get());
    }

    @Override
    public @NotNull RecipeSerializer<?> getSerializer() {
        return ModSerializers.MALLEABLE_METAL_SERIALIZER.get();
    }

    @Override
    public @NotNull RecipeType<?> getType() {
        return ModRecipeTypes.MALLEABLE_METAL.get();
    }

    public float getExperience() {
        return experience;
    }

    public int getProcessingTime() {
        return processingTime;
    }

    public MalleableMetal getMalleableMetal() {
        return malleableMetal;
    }
}
