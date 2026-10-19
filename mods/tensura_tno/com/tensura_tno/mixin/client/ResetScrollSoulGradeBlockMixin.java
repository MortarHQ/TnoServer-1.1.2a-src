package com.tensura_tno.mixin.client;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.tensura_tno.util.PrestigeGuard;
import io.github.manasmods.tensura.item.misc.ResetScrollItem.ResetType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(targets = "io.github.manasmods.tensura.item.misc.ResetScrollItem", priority = 500)
public abstract class ResetScrollSoulGradeBlockMixin {
   @Shadow(remap = false)
   public abstract ResetType getResetType();

   @WrapMethod(method = "releaseUsing(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/level/Level;Lnet/minecraft/world/entity/LivingEntity;I)V")
   private void tno$withScrollType(ItemStack stack, Level level, LivingEntity entity, int timeLeft, Operation<Void> original) {
      boolean wasBlocked = PrestigeGuard.BLOCKED.get();
      PrestigeGuard.BLOCKED.set(this.getResetType() != ResetType.RESET_ALL);

      try {
         original.call(new Object[]{stack, level, entity, timeLeft});
      } finally {
         PrestigeGuard.BLOCKED.set(wasBlocked);
      }
   }
}
