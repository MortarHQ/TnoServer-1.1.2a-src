package com.tensura_tno.mixin.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.tensura_tno.compat.MysticismSkillRefundGuard;
import io.github.manasmods.tensura.storage.ep.IExistence;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;

@Pseudo
@Mixin(targets = "io.github.Memoires.mysticism.handler.AbilityHandler", remap = false)
public abstract class MysticismSkillRefundMixin {
   @WrapOperation(
      method = "*",
      at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/ai/attributes/AttributeInstance;setBaseValue(D)V"),
      remap = false,
      require = 1
   )
   private static void tensuraTno$limitMaxMagiculeRefund(AttributeInstance attribute, double value, Operation<Void> original) {
      MysticismSkillRefundGuard.refundMaxMagicule(attribute, attribute.getBaseValue(), value, allowed -> original.call(new Object[]{attribute, allowed}));
   }

   @WrapOperation(
      method = "*",
      at = @At(value = "INVOKE", target = "Lio/github/manasmods/tensura/storage/ep/IExistence;setMagicule(D)V"),
      remap = false,
      require = 1
   )
   private static void tensuraTno$limitCurrentMagiculeRefund(IExistence existence, double value, Operation<Void> original) {
      MysticismSkillRefundGuard.refundMagicule(existence, existence.getMagicule(), value, allowed -> original.call(new Object[]{existence, allowed}));
   }
}
