package com.dplayend.merenc.handler;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.enchantment.*;

public class HandlerImprovedTridentEnchantments {
    public static boolean isModLoaded() {
        return FabricLoader.getInstance().isModLoaded("ite");
    }

    public static boolean check(Enchantment other) {
        if (isModLoaded()) return false;
        return (other == Enchantments.RIPTIDE || other == Enchantments.CHANNELING || other == Enchantments.LOYALTY);
    }
}
