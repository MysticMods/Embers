package mysticmods.embers.items;

import mysticmods.embers.init.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

public class CrystalEmberSeedItem extends Item {

    public CrystalEmberSeedItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult onItemUseFirst(ItemStack stack, UseOnContext context) {
        BlockPos pos = context.getClickedPos();
        Block block = context.getLevel().getBlockState(pos).getBlock();
        if(block == Blocks.BUDDING_AMETHYST){
            ItemStack drop = new ItemStack(ModItems.BUDDING_EMBER_BLOCK_ITEM.get());
            context.getLevel().addFreshEntity(new ItemEntity(context.getLevel(), pos.getX(), pos.getY(), pos.getZ(), drop));
            if(!context.getPlayer().isCreative()){
                stack.shrink(1);
            }
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }
}
