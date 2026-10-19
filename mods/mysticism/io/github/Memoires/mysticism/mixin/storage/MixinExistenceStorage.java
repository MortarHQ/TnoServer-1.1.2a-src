package io.github.Memoires.mysticism.mixin.storage;

import io.github.Memoires.mysticism.race.forgotten.ForgottenRace;
import io.github.Memoires.mysticism.race.forgotten.RemnantRace;
import io.github.Memoires.mysticism.race.forgotten.empty.DivineInferiusRace;
import io.github.Memoires.mysticism.race.forgotten.empty.EmptyRace;
import io.github.Memoires.mysticism.race.forgotten.empty.RevenantRace;
import io.github.Memoires.mysticism.race.forgotten.whole.AscendedRace;
import io.github.Memoires.mysticism.race.forgotten.whole.DivineExcelsiusRace;
import io.github.Memoires.mysticism.race.forgotten.whole.WholeRace;
import io.github.Memoires.mysticism.registry.effects.MysticismMobEffects;
import io.github.Memoires.mysticism.registry.skill.UniqueMysticismSkills;
import io.github.manasmods.manascore.race.api.ManasRace;
import io.github.manasmods.manascore.race.api.ManasRaceInstance;
import io.github.manasmods.manascore.race.api.RaceAPI;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.tensura.ability.SkillUtils;
import io.github.manasmods.tensura.registry.effect.TensuraMobEffects;
import io.github.manasmods.tensura.storage.ep.ExistenceStorage;
import io.github.manasmods.tensura.storage.ep.IExistence;
import java.util.Optional;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ExistenceStorage.class)
public class MixinExistenceStorage {
   @Inject(method = "isAffectedByEnergyLost(Lnet/minecraft/world/entity/LivingEntity;)Z", at = @At("HEAD"), cancellable = true)
   private static void isForgottenRace(LivingEntity entity, CallbackInfoReturnable<Boolean> cir) {
      Optional<ManasRaceInstance> race = RaceAPI.getRaceFrom(entity).getRace();
      if (race.isEmpty()) {
         cir.setReturnValue(true);
      } else {
         ManasRace playerRace = race.get().getRace();
         if (mysticism$isImmuneRace(playerRace)) {
            cir.setReturnValue(false);
         }
      }
   }

   @Inject(
      method = "handleMagiculeRegen(Lio/github/manasmods/tensura/storage/ep/IExistence;Lnet/minecraft/world/entity/LivingEntity;DD)V",
      at = @At("HEAD"),
      cancellable = true
   )
   private static void haltMagiculeRegen(IExistence storage, LivingEntity entity, double areaMagicule, double maxMagicule, CallbackInfo ci) {
      if (entity.hasEffect(TensuraMobEffects.getReference(MysticismMobEffects.CONSTANT_ENERGY))
         || entity.hasEffect(TensuraMobEffects.getReference(MysticismMobEffects.CONSTANT_ENERGY_DEBUFF))) {
         ci.cancel();
      }
   }

   @Inject(
      method = "handleAuraRegen(Lio/github/manasmods/tensura/storage/ep/IExistence;Lnet/minecraft/world/entity/LivingEntity;D)V",
      at = @At("HEAD"),
      cancellable = true
   )
   private static void haltAuraRegen(IExistence storage, LivingEntity entity, double maxAura, CallbackInfo ci) {
      if (entity.hasEffect(TensuraMobEffects.getReference(MysticismMobEffects.CONSTANT_ENERGY))
         || entity.hasEffect(TensuraMobEffects.getReference(MysticismMobEffects.CONSTANT_ENERGY_DEBUFF))) {
         ci.cancel();
      }
   }

   @Inject(
      method = "applyDeathPenalty(Lnet/minecraft/world/entity/LivingEntity;Lio/github/manasmods/tensura/storage/ep/IExistence;Lnet/minecraft/world/damagesource/DamageSource;)V",
      at = @At("HEAD"),
      cancellable = true
   )
   private static void mysticism$avoidDeathPenalty(LivingEntity entity, IExistence storage, DamageSource source, CallbackInfo ci) {
      if (SkillUtils.hasSkillFully(entity, (ManasSkill)UniqueMysticismSkills.SCHRODINGER.get())) {
         float chance = 0.5F;
         ManasSkillInstance skill = SkillUtils.getSkillOrNull(entity, (ManasSkill)UniqueMysticismSkills.SCHRODINGER.get());
         if (skill != null && skill.isMastered(entity)) {
            chance = 1.0F;
         }

         if (entity.getRandom().nextFloat() < chance) {
            ci.cancel();
         }
      }
   }

   @Unique
   private static boolean mysticism$isImmuneRace(ManasRace race) {
      return race instanceof ForgottenRace
         || race instanceof RemnantRace
         || race instanceof WholeRace
         || race instanceof AscendedRace
         || race instanceof DivineExcelsiusRace
         || race instanceof EmptyRace
         || race instanceof RevenantRace
         || race instanceof DivineInferiusRace;
   }
}
