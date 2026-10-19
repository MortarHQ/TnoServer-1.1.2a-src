package io.github.Memoires.mysticism.mixin.effect;

import io.github.Memoires.mysticism.registry.effects.MysticismMobEffects;
import io.github.manasmods.tensura.effect.ability.InstantRegenerationEffect;
import io.github.manasmods.tensura.registry.effect.TensuraMobEffects;
import io.github.manasmods.tensura.storage.ep.IExistence;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(InstantRegenerationEffect.class)
public class MixinInstantRegenerationEffect {
   @Inject(method = "applyEffectTick(Lnet/minecraft/world/entity/LivingEntity;I)Z", at = @At("HEAD"), cancellable = true)
   private void slowRegen(LivingEntity entity, int amplifier, CallbackInfoReturnable<Boolean> cir) {
      if (entity.hasEffect(TensuraMobEffects.getReference(MysticismMobEffects.BRIMSTONE_FLAMES))) {
         MobEffectInstance regen = entity.getEffect(TensuraMobEffects.getReference(TensuraMobEffects.INSTANT_REGENERATION));
         if (regen != null && regen.getDuration() % 100 != 0) {
            cir.setReturnValue(true);
         }
      }
   }

   @Redirect(
      method = "applyEffectTick(Lnet/minecraft/world/entity/LivingEntity;I)Z",
      at = @At(
         value = "INVOKE",
         target = "Lio/github/manasmods/tensura/effect/ability/InstantRegenerationEffect;heal(Lnet/minecraft/world/entity/LivingEntity;F)V"
      )
   )
   private void mysticism$halveHealthRegen(InstantRegenerationEffect instance, LivingEntity entity, float lackedHealth) {
      if (entity.hasEffect(TensuraMobEffects.getReference(MysticismMobEffects.BRIMSTONE_FLAMES))) {
         lackedHealth *= 0.2F;
      }

      ((InstantRegenerationEffectAccessor)instance).mysticism$heal(entity, lackedHealth);
   }

   @Redirect(
      method = "applyEffectTick(Lnet/minecraft/world/entity/LivingEntity;I)Z",
      at = @At(
         value = "INVOKE",
         target = "Lio/github/manasmods/tensura/effect/ability/InstantRegenerationEffect;healSHP(Lnet/minecraft/world/entity/LivingEntity;Lio/github/manasmods/tensura/storage/ep/IExistence;DDD)V"
      )
   )
   private void mysticism$halveSHPRegen(
      InstantRegenerationEffect instance, LivingEntity entity, IExistence existence, double currentSHP, double lackedSHP, double maxSHP
   ) {
      if (entity.hasEffect(TensuraMobEffects.getReference(MysticismMobEffects.BRIMSTONE_FLAMES))) {
         lackedSHP *= 0.2;
      }

      ((InstantRegenerationEffectAccessor)instance).mysticism$healSHP(entity, existence, currentSHP, lackedSHP, maxSHP);
   }
}
