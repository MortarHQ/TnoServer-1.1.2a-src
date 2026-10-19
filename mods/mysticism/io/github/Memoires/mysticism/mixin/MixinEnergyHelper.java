package io.github.Memoires.mysticism.mixin;

import io.github.Memoires.mysticism.registry.skill.UniqueMysticismSkills;
import io.github.Memoires.mysticism.util.MysticismEnergyCostHelper;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.tensura.ability.SkillUtils;
import io.github.manasmods.tensura.storage.TensuraStorages;
import io.github.manasmods.tensura.storage.ep.IExistence;
import io.github.manasmods.tensura.util.EnergyHelper;
import io.github.manasmods.tensura.util.EnergyHelper.DrainType;
import io.github.manasmods.tensura.util.EnergyHelper.GainType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EnergyHelper.class)
public class MixinEnergyHelper {
   @ModifyVariable(method = "isOutOfEnergy(Lnet/minecraft/world/entity/LivingEntity;DD)Z", at = @At("HEAD"), argsOnly = true, index = 1)
   private static double mysticism$modifyApCost(double original, LivingEntity entity, double apCost, double mpCost) {
      return MysticismEnergyCostHelper.modifyApEnergyCost(entity, original);
   }

   @ModifyVariable(method = "isOutOfEnergy(Lnet/minecraft/world/entity/LivingEntity;DD)Z", at = @At("HEAD"), argsOnly = true, index = 3)
   private static double mysticism$modifyMpCost(double original, LivingEntity entity, double apCost, double mpCost) {
      return MysticismEnergyCostHelper.modifyMpEnergyCost(entity, original);
   }

   @Inject(
      method = "drainEnergy(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/entity/Entity;DZLio/github/manasmods/tensura/util/EnergyHelper$DrainType;Lio/github/manasmods/tensura/util/EnergyHelper$GainType;)Z",
      at = @At(
         value = "INVOKE",
         target = "Lio/github/manasmods/tensura/storage/TensuraStorages;getExistenceFrom(Lnet/minecraft/world/entity/LivingEntity;)Lio/github/manasmods/tensura/storage/ep/IExistence;",
         shift = Shift.AFTER
      ),
      cancellable = true
   )
   private static void replaceMagiculeDrain(
      LivingEntity source, Entity attacker, double amount, boolean percentage, DrainType drainType, GainType gainType, CallbackInfoReturnable<Boolean> cir
   ) {
      if (SkillUtils.hasSkillFully(source, (ManasSkill)UniqueMysticismSkills.RESTRICTED.get())) {
         if (drainType == DrainType.MAGICULE || drainType == DrainType.EP) {
            IExistence existence = TensuraStorages.getExistenceFrom(source);
            if (existence != null) {
               double currentAP = existence.getAura();
               double drain = percentage ? currentAP * amount : amount;
               drain = Math.min(currentAP, drain);
               if (attacker instanceof LivingEntity living) {
                  gainAP(living, drain, gainType);
               }

               existence.setAura(currentAP - drain);
               existence.markDirty();
               cir.setReturnValue(true);
               cir.cancel();
            }
         }
      }
   }

   private static void gainAP(LivingEntity entity, double amount, GainType type) {
      if (type != GainType.NONE) {
         IExistence existence = TensuraStorages.getExistenceFrom(entity);
         if (existence != null) {
            double before = existence.getAura();
            existence.setAura(before + amount);
            existence.markDirty();
         }
      }
   }
}
