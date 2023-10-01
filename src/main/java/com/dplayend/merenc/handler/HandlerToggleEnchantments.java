package com.dplayend.merenc.handler;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.enchantment.*;

public class HandlerToggleEnchantments {
    public static boolean isModLoaded() {
        return FabricLoader.getInstance().isModLoaded("togenc");
    }

    public static boolean check(Enchantment other) {
        if (isModLoaded()) return false;
        return (other == Enchantments.SILK_TOUCH || other == Enchantments.FORTUNE);
    }
}
