package com.dplayend.merenc.mixin;

import com.dplayend.merenc.MergeEnchantments;
import com.dplayend.merenc.handler.HandlerImprovedTridentEnchantments;
import com.dplayend.merenc.handler.HandlerToggleEnchantments;
import net.minecraft.enchantment.Enchantment;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({Enchantment.class})
public class MixEnchantment {
    @Unique Enchantment that = (Enchantment) (Object)this;
    @Inject(method = "canCombine", at = @At("HEAD"), cancellable = true)
    private void canCombine(Enchantment other, CallbackInfoReturnable<Boolean> info) {
        String string = that.getTranslationKey().split("\\.")[2];
        boolean check = true;
        for (int i = 0; i < MergeEnchantments.CONFIG.blackList.size(); i++) {
            if (string.equals(MergeEnchantments.CONFIG.blackList.get(i))) {
                check = false;
                break;
            }
        }

        if (HandlerToggleEnchantments.check(other)) check = false;
        if (HandlerImprovedTridentEnchantments.check(other)) check = false;

        if (check) {
            info.cancel();
            info.setReturnValue(that != other);
        }
    }
}
