package com.github.wal_bos.moreskills.handler.michael;

import com.github.wal_bos.moreskills.configs.MoreSkillsConfigs;
import com.github.wal_bos.moreskills.net.msg.MichaelAuraVfxPayload;
import com.github.wal_bos.moreskills.net.msg.MichaelSegvaVfxPayload;
import com.github.wal_bos.moreskills.registry.skill.AllSkills;
import dev.architectury.event.EventResult;
import io.github.manasmods.manascore.network.api.util.Changeable;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import io.github.manasmods.tensura.event.TensuraEntityEvents;
import io.github.manasmods.tensura.event.TensuraEntityEvents.ForceMovementEvent;
import io.github.manasmods.tensura.event.TensuraEntityEvents.SpatialMovementEvent;
import io.github.manasmods.tensura.registry.effect.TensuraMobEffects;
import io.github.manasmods.tensura.util.AttributeHelper;
import java.util.Optional;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.phys.AABB;

public final class MichaelPassives {
   private static boolean initialized;

   private MichaelPassives() {
   }

   public static void init() {
      if (!initialized) {
         initialized = true;
         TensuraEntityEvents.INSTANT_TRANSMISSION_EVENT
            .register(
               (SpatialMovementEvent)(target, destination, dimension, changeable) -> target instanceof LivingEntity living
                     && MichaelSegvaSealing.hasMarks(living)
                  ? EventResult.interruptFalse()
                  : EventResult.pass()
            );
         TensuraEntityEvents.FORCE_MOVEMENT_EVENT
            .register(
               (ForceMovementEvent)(target, source, instance, motion) -> target instanceof LivingEntity living && MichaelSegvaSealing.hasMarks(living)
                  ? EventResult.interruptFalse()
                  : EventResult.pass()
            );
      }
   }

   public static void onToggleOn(ManasSkillInstance instance, LivingEntity entity) {
      AttributeHelper.addPermanentAttribute(
         entity, Attributes.ARMOR, MichaelActions.ARMOR_ELOAH, (Double)MoreSkillsConfigs.GRAND.michaelAngelOfLocks.eloahArmor.get(), Operation.ADD_VALUE
      );
      MichaelAuraVfxPayload.sendTrail(entity);
   }

   public static void onToggleOff(ManasSkillInstance instance, LivingEntity entity) {
      AttributeHelper.removeAttribute(entity, Attributes.ARMOR, MichaelActions.ARMOR_ELOAH);
      MichaelAuraVfxPayload.stop(entity);
   }

   public static boolean hasActiveState(LivingEntity entity) {
      return MichaelSegvaSealing.hasMarks(entity);
   }

   public static void onTick(ManasSkillInstance instance, LivingEntity entity) {
      if (entity.level() instanceof ServerLevel level) {
         MichaelSegvaSealing.tickMarks(entity);
         MichaelSegvaSealing.tickWatcher(entity);
         if (instance.isToggled()) {
            if (entity.tickCount % (Integer)MoreSkillsConfigs.GRAND.michaelAngelOfLocks.passiveRefreshTicks.get() == 0) {
               applyPresence(entity, level);
            }

            MichaelAuraVfxPayload.sendTrail(entity);
         }
      }
   }

   private static void applyPresence(LivingEntity entity, ServerLevel level) {
      double radius = (Double)MoreSkillsConfigs.GRAND.michaelAngelOfLocks.kingsPresenceRadius.get();
      AABB box = entity.getBoundingBox().inflate(radius);

      for (LivingEntity target : level.getEntitiesOfClass(LivingEntity.class, box, LivingEntity::isAlive)) {
         if (target != entity) {
            if (!target.isAlliedTo(entity) && !entity.isAlliedTo(target)) {
               target.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 120, 0, true, false, false), entity);
               target.addEffect(
                  new MobEffectInstance(TensuraMobEffects.getReference(TensuraMobEffects.MOVEMENT_INTERFERENCE), 120, 0, true, false, true), entity
               );
               target.addEffect(new MobEffectInstance(TensuraMobEffects.getReference(TensuraMobEffects.TRUE_BLINDNESS), 120, 0, true, false, true), entity);
            } else {
               target.addEffect(new MobEffectInstance(TensuraMobEffects.getReference(TensuraMobEffects.STRENGTHEN), 120, 0, true, false, true), entity);
               target.addEffect(new MobEffectInstance(TensuraMobEffects.getReference(TensuraMobEffects.GUARDED), 120, 1, true, false, true), entity);
            }
         }
      }
   }

   public static void onDamageEntity(ManasSkillInstance instance, LivingEntity attacker, LivingEntity target, DamageSource source, Changeable<Float> amount) {
      if (instance.isToggled()) {
         target.addEffect(
            new MobEffectInstance(
               TensuraMobEffects.getReference(TensuraMobEffects.SPATIAL_BLOCKADE),
               (Integer)MoreSkillsConfigs.GRAND.michaelAngelOfLocks.spatialSuppressionTicks.get(),
               0,
               true,
               false,
               true
            ),
            attacker
         );
         MichaelSegvaVfxPayload.sendLock(attacker, target, 70);
      }
   }

   public static boolean cancelIncomingIfNeeded(LivingEntity entity, DamageSource source) {
      Optional<ManasSkillInstance> optional = SkillAPI.getSkillsFrom(entity).getSkill((ManasSkill)AllSkills.MICHAEL_ANGEL_OF_LOCKS.get());
      return !optional.isEmpty() && optional.get().isToggled()
         ? MichaelActions.isSeverance(source) && !source.is(DamageTypeTags.BYPASSES_INVULNERABILITY)
         : false;
   }
}
