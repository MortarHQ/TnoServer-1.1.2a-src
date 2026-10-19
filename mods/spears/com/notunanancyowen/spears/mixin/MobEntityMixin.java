package com.notunanancyowen.spears.mixin;

import com.notunanancyowen.spears.Spears;
import com.notunanancyowen.spears.components.AttackRange;
import com.notunanancyowen.spears.components.PiercingWeapon;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Mob.class)
public class MobEntityMixin {
   @Inject(method = "isWithinMeleeAttackRange(Lnet/minecraft/world/entity/LivingEntity;)Z", at = @At("HEAD"), cancellable = true)
   private void canAttackWithSpear(LivingEntity entity, CallbackInfoReturnable<Boolean> cir) {
      Mob me = (Mob)this;
      if (me.getMainHandItem().get(Spears.ATTACK_RANGE) instanceof AttackRange(float minReach, float maxReach)
         && me.getMainHandItem().get(Spears.PIERCING_WEAPON) instanceof PiercingWeapon p) {
         double d = me.distanceTo(entity);
         cir.setReturnValue(
            d >= minReach
               && d <= maxReach
               && me.calculateViewVector(me.getXRot(), me.getVisualRotationYInDegrees()).dot(entity.position().subtract(me.position()).normalize())
                  > p.hitboxMargin()
         );
      }
   }

   @Inject(method = "doHurtTarget(Lnet/minecraft/world/entity/Entity;)Z", at = @At("HEAD"), cancellable = true)
   private void attackWithSpear(Entity target, CallbackInfoReturnable<Boolean> cir) {
      Mob me = (Mob)this;
      if (me.getMainHandItem().get(Spears.PIERCING_WEAPON) instanceof PiercingWeapon p) {
         me.lookAt(target, 30.0F, 30.0F);
         me.setYBodyRot(me.getYHeadRot());
         cir.setReturnValue(p.stab(me, EquipmentSlot.MAINHAND));
      }
   }
}
