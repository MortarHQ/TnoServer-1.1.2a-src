package artifacts.mixin.ability.phantomrepellent;

import artifacts.equipment.EquipmentHelper;
import artifacts.registry.ModDataComponents;
import com.llamalad7.mixinextras.injector.ModifyReceiver;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Phantom;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(targets = "net/minecraft/world/entity/monster/Phantom$PhantomSweepAttackGoal")
public class PhantomSweepAttackGoalMixin {
   @Unique
   private Phantom artifacts$phantom;

   @ModifyReceiver(
      method = "canContinueToUse()Z",
      at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/monster/Phantom;getTarget()Lnet/minecraft/world/entity/LivingEntity;")
   )
   private Phantom canContinueToUse(Phantom instance) {
      this.artifacts$phantom = instance;
      return instance;
   }

   @ModifyReturnValue(method = "canContinueToUse()Z", at = @At("RETURN"))
   private boolean hissAtPhantom(boolean original) {
      if (this.artifacts$phantom != null && original) {
         LivingEntity target = this.artifacts$phantom.getTarget();
         if (target != null
            && this.artifacts$phantom.distanceToSqr(target) < 256.0
            && EquipmentHelper.hasAbilityActive(ModDataComponents.PHANTOM_REPELLENT.get(), target, true)) {
            this.artifacts$phantom
               .level()
               .playSound(null, target.getX(), target.getY(), target.getZ(), SoundEvents.CAT_HISS, target.getSoundSource(), 1.0F, 1.0F);
            return false;
         }
      }

      return original;
   }
}
