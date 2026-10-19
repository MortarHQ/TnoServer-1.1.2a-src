package com.tensura_tno.mixin.client;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.tensura_tno.compat.stextras.STExtrasConsumptionCompat;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ItemStack.class)
public class STExtrasConsumeFixMixin {
   @WrapMethod(method = "finishUsingItem")
   private ItemStack tensuraTno$consumeOnce(Level level, LivingEntity entity, Operation<ItemStack> original) {
      return !level.isClientSide() && entity instanceof ServerPlayer player
         ? STExtrasConsumptionCompat.finishUsing(player, (ItemStack)this, () -> (ItemStack)original.call(new Object[]{level, entity}))
         : (ItemStack)original.call(new Object[]{level, entity});
   }
}
