package com.mystic.embers.init;

import com.mystic.embers.Embers;
import com.tterrag.registrate.Registrate;
import net.minecraft.network.chat.TranslatableComponent;

public class ModLang {

    private static final Registrate REGISTRATE = Embers.registrate();

    public static final TranslatableComponent CREATIVE_TAB = REGISTRATE.addRawLang("itemGroup." + Embers.MODID, "Embers");

    public static void classload() {

    }

}