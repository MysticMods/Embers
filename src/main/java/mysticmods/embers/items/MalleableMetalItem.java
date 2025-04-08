package mysticmods.embers.items;

import com.google.common.collect.Lists;
import mysticmods.embers.capabilities.heated_metal.IHeatedMetalCap;
import mysticmods.embers.data.components.MalleableMetalDataComponent;
import mysticmods.embers.init.EmbersCapabilities;
import mysticmods.embers.init.EmbersDataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;
import java.util.function.Consumer;

public class MalleableMetalItem extends Item {

    public MalleableMetalItem(Properties properties) {
        super(properties);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
        MalleableMetalDataComponent data = stack.get(EmbersDataComponents.MALLEABLE_METAL);
        data.addToTooltip(context, tooltipComponents::add, tooltipFlag);
    }
}
