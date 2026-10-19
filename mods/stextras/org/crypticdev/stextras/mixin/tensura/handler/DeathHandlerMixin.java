package org.crypticdev.stextras.mixin.tensura.handler;

import dev.architectury.event.EventResult;
import io.github.manasmods.tensura.handler.DeathHandler;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.crypticdev.stextras.event.STPlayerEvents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(DeathHandler.class)
public class DeathHandlerMixin {
   @Inject(
      method = "lambda$init$0(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/damagesource/DamageSource;)Ldev/architectury/event/EventResult;",
      at = @At(value = "INVOKE", target = "Lio/github/manasmods/tensura/storage/ep/IExistence;setPermanentOwner(Ljava/util/UUID;)V", shift = Shift.BEFORE)
   )
   private static void captureOwnerBeforeDeletion(LivingEntity entity, DamageSource source, CallbackInfoReturnable<EventResult> cir) {
      entity.addTag("stextras_had_owner");
   }

   @Inject(
      method = "gearGetEP",
      at = @At(
         value = "INVOKE",
         target = "Lio/github/manasmods/tensura/handler/GearHandler;initiateGearEvolution(Lnet/minecraft/world/level/Level;Lnet/minecraft/world/item/ItemStack;)V",
         shift = Shift.AFTER
      ),
      locals = LocalCapture.CAPTURE_FAILHARD
   )
   private static void stextras$afterInitiateGearEvolution(
      LivingEntity entity,
      EquipmentSlot slot,
      double totalEP,
      CallbackInfo ci,
      ItemStack stack,
      double maxEP,
      double currentEP,
      double newEP,
      ResourceLocation location,
      ItemStack evolution
   ) {
      STPlayerEvents.fireEquipEvolve(entity, evolution.copy());
   }
}
