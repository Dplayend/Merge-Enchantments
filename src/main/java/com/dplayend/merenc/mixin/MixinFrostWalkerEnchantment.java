package com.dplayend.merenc.mixin;

import com.dplayend.merenc.MergeEnchantments;
import net.minecraft.block.*;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.enchantment.FrostWalkerEnchantment;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FrostWalkerEnchantment.class)
public class MixinFrostWalkerEnchantment extends Enchantment{
    public MixinFrostWalkerEnchantment(Enchantment.Rarity weight, EquipmentSlot... slotTypes) {
        super(weight, EnchantmentTarget.ARMOR_FEET, slotTypes);
    }

    @Inject(method = "freezeWater", at = @At("HEAD"), cancellable = true)
    private static void freezeWater(LivingEntity entity, World world, BlockPos blockPos, int level, CallbackInfo ci) {
        ci.cancel();

        if (entity instanceof PlayerEntity player) {
            if (player.isOnGround() && !player.getInventory().getArmorStack(0).getOrCreateNbt().getBoolean("isFrostWalker")) {
                frostGround(entity, world, blockPos,level);
            }
        } else {
            if (entity.isOnGround()) {
                frostGround(entity, world, blockPos,level);
            }
        }
    }

    @Inject(method = "canAccept", at = @At("HEAD"), cancellable = true)
    private void canAccept(Enchantment other, CallbackInfoReturnable<Boolean> info) {
        if (MergeEnchantments.CONFIG.boots) {
            info.cancel();
            info.setReturnValue(super.canAccept(other));
        }
    }

    private static void frostGround(LivingEntity entity, World world, BlockPos blockPos, int level) {
        BlockState blockState = Blocks.FROSTED_ICE.getDefaultState();
        float f = (float) Math.min(16, 2 + level);
        BlockPos.Mutable mutable = new BlockPos.Mutable();

        for (BlockPos blockPos2 : BlockPos.iterate(blockPos.add((-f), -1.0, (-f)), blockPos.add(f, -1.0, f))) {
            if (blockPos2.isWithinDistance(entity.getPos(), f)) {
                mutable.set(blockPos2.getX(), blockPos2.getY() + 1, blockPos2.getZ());
                BlockState blockState2 = world.getBlockState(mutable);
                if (blockState2.isAir()) {
                    BlockState blockState3 = world.getBlockState(blockPos2);
                    if (blockState3.getMaterial() == Material.WATER && blockState3.get(FluidBlock.LEVEL) == 0 && blockState.canPlaceAt(world, blockPos2) && world.canPlace(blockState, blockPos2, ShapeContext.absent())) {
                        world.setBlockState(blockPos2, blockState);
                        world.createAndScheduleBlockTick(blockPos2, Blocks.FROSTED_ICE, MathHelper.nextInt(entity.getRandom(), 60, 120));
                    }
                }
            }
        }
    }
}
