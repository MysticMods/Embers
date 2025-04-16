package mysticmods.embers.items;

import mysticmods.embers.data.components.MalleableMetalDataComponent;
import mysticmods.embers.init.ModDataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class MalleableMetalItem extends Item {

    public MalleableMetalItem(Properties properties) {
        super(properties);
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @NotNull TooltipContext context, @NotNull List<Component> tooltipComponents, @NotNull TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
        MalleableMetalDataComponent data = stack.get(ModDataComponents.MALLEABLE_METAL);
        data.addToTooltip(context, tooltipComponents::add, tooltipFlag);
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
        if(level.getGameTime() % 2 == 0) {
            MalleableMetalDataComponent data = stack.get(ModDataComponents.MALLEABLE_METAL);
            data = data.removeHeat(1);
            stack.set(ModDataComponents.MALLEABLE_METAL, data);
        }
    }


}
