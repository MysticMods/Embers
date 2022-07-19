package com.mystic.embers.setup;

import net.minecraftforge.fml.InterModComms;

public class ModSetup {
    public static void sendImc() {
        //InterModComms.sendTo(CuriosApi.MODID, SlotTypeMessage.REGISTER_TYPE, () -> (new SlotTypeMessage.Builder(SuperiorShields.SHIELD_CURIO).priority(220).icon(new ResourceLocation(SuperiorShields.MODID, "item/empty_shield_slot"))).build());
    }
}
