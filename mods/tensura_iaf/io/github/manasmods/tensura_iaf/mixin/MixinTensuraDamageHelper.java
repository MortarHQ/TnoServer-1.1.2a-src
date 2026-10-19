package io.github.manasmods.tensura_iaf.mixin;

import com.iafenvoy.iceandfire.registry.IafDamageTypes;
import io.github.manasmods.tensura.damage.TensuraDamageHelper;
import net.minecraft.world.damagesource.DamageSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(TensuraDamageHelper.class)
public class MixinTensuraDamageHelper {
   @Inject(at = @At("HEAD"), method = "isAbnormal(Lnet/minecraft/world/damagesource/DamageSource;)Z", cancellable = true)
   private static void isAbnormal(DamageSource damageSource, CallbackInfoReturnable<Boolean> cir) {
      if (damageSource.is(IafDamageTypes.GORGON_DMG_TYPE)) {
         cir.setReturnValue(true);
      }
   }

   @Inject(at = @At("HEAD"), method = "isCold(Lnet/minecraft/world/damagesource/DamageSource;)Z", cancellable = true)
   private static void isCold(DamageSource damageSource, CallbackInfoReturnable<Boolean> cir) {
      if (damageSource.is(IafDamageTypes.DRAGON_ICE_TYPE)) {
         cir.setReturnValue(true);
      }
   }

   @Inject(at = @At("HEAD"), method = "isFireDamage(Lnet/minecraft/world/damagesource/DamageSource;)Z", cancellable = true)
   private static void isFireDamage(DamageSource damageSource, CallbackInfoReturnable<Boolean> cir) {
      if (damageSource.is(IafDamageTypes.DRAGON_FIRE_TYPE)) {
         cir.setReturnValue(true);
      }
   }

   @Inject(at = @At("HEAD"), method = "isLightningDamage(Lnet/minecraft/world/damagesource/DamageSource;)Z", cancellable = true)
   private static void isLightningDamage(DamageSource damageSource, CallbackInfoReturnable<Boolean> cir) {
      if (damageSource.is(IafDamageTypes.DRAGON_LIGHTNING_TYPE)) {
         cir.setReturnValue(true);
      }
   }

   @Inject(at = @At("HEAD"), method = "isPhysicalAttack(Lnet/minecraft/world/damagesource/DamageSource;)Z", cancellable = true)
   private static void isPhysicalAttack(DamageSource damageSource, CallbackInfoReturnable<Boolean> cir) {
      if (damageSource.is(IafDamageTypes.GORGON_DMG_TYPE)
         || damageSource.is(IafDamageTypes.DRAGON_ICE_TYPE)
         || damageSource.is(IafDamageTypes.DRAGON_FIRE_TYPE)
         || damageSource.is(IafDamageTypes.DRAGON_LIGHTNING_TYPE)) {
         cir.setReturnValue(false);
      }
   }
}
