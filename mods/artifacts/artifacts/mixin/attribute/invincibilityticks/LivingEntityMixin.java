package artifacts.mixin.attribute.invincibilityticks;

import artifacts.registry.ModAttributes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {
   @Inject(
      method = "hurt(Lnet/minecraft/world/damagesource/DamageSource;F)Z",
      at = @At(value = "FIELD", target = "Lnet/minecraft/world/entity/LivingEntity;invulnerableTime:I", opcode = 181, shift = Shift.AFTER)
   )
   private void hurt(DamageSource damageSource, float f, CallbackInfoReturnable<Boolean> cir) {
      this.artifacts$applyBonusTicks();
   }

   @Inject(
      method = "handleDamageEvent(Lnet/minecraft/world/damagesource/DamageSource;)V",
      at = @At(value = "FIELD", target = "Lnet/minecraft/world/entity/LivingEntity;invulnerableTime:I", opcode = 181, shift = Shift.AFTER)
   )
   private void handleDamageEvent(DamageSource damageSource, CallbackInfo ci) {
      this.artifacts$applyBonusTicks();
   }

   @Unique
   private void artifacts$applyBonusTicks() {
      LivingEntity entity = (LivingEntity)this;
      int bonusTicks = (int)entity.getAttributeValue(ModAttributes.INVINCIBILITY_TICKS);
      entity.invulnerableTime += bonusTicks;
   }
}
