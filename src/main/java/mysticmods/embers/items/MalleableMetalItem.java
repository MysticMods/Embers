package mysticmods.embers.items;

import mysticmods.embers.capabilities.heated_metal.IHeatedMetalCap;
import mysticmods.embers.init.EmbersCapabilities;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public class MalleableMetalItem extends Item {

    public MalleableMetalItem(Properties properties) {
        super(properties);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);

        IHeatedMetalCap cap = stack.getCapability(EmbersCapabilities.HEATED_METAL);
        if (cap != null) {
            tooltipComponents.add(Component.translatable("tooltip.embers.heat", cap.getStackHeat(), cap.getMaxHeat()));
            tooltipComponents.add(Component.translatable("tooltip.embers.ingots", cap.getIngots()));
            tooltipComponents.add(Component.translatable("tooltip.embers.nuggets", cap.getNuggets()));
        }
    }
}
