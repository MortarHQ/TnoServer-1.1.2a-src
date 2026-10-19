package com.github.wal_bos.moreskills.handler.Istaroth;

import com.github.wal_bos.moreskills.ability.skill.shades.IstarothSkill;
import com.github.wal_bos.moreskills.configs.MoreSkillsConfigs;
import com.github.wal_bos.moreskills.registry.effect.ModMobEffects;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.tensura.entity.variant.MagicCircleVariant;
import io.github.manasmods.tensura.registry.effect.TensuraMobEffects;
import io.github.manasmods.tensura.util.ObjectSelectionHelper;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;
import java.util.Map.Entry;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.ModConfigSpec.DoubleValue;
import net.neoforged.neoforge.common.ModConfigSpec.IntValue;

public final class IstarothActions {
   private static final MoreSkillsConfigs.IstarothConfig CONFIG = MoreSkillsConfigs.GRAND.istaroth;
   private static final Map<UUID, IstarothActions.LoopState> LOOP_STATES = new HashMap<>();
   private static final Map<UUID, IstarothActions.RepeatState> REPEAT_STATES = new HashMap<>();
   private static final Map<UUID, IstarothActions.ExileState> EXILES = new HashMap<>();
   private static final Map<UUID, IstarothActions.VerdictMarker> VERDICT_MARKERS = new HashMap<>();
   private static final Map<UUID, Integer> ACTION_LOCKS = new HashMap<>();
   private static final Map<UUID, Integer> ETERNAL_MOMENT = new HashMap<>();
   private static final Map<UUID, IstarothActions.StasisState> STASIS_STATES = new HashMap<>();

   private IstarothActions() {
   }

   public static void init() {
   }

   private static float f(DoubleValue value) {
      return ((Double)value.get()).floatValue();
   }

   private static double d(DoubleValue value) {
      return (Double)value.get();
   }

   private static float f(IntValue value) {
      return ((Integer)value.get()).floatValue();
   }

   public static void tick(Level level) {
      tickStasis(level);
      tickLoops(level);
      tickRepeats(level);
      tickExiles(level);
      tickVerdictMarkers(level);
      tickActionLocks(level);
      tickEternalMoment(level);
   }

   public static boolean hasActiveState(LivingEntity entity) {
      UUID id = entity.getUUID();
      return LOOP_STATES.containsKey(id)
         || REPEAT_STATES.containsKey(id)
         || EXILES.containsKey(id)
         || VERDICT_MARKERS.containsKey(id)
         || ACTION_LOCKS.containsKey(id)
         || ETERNAL_MOMENT.containsKey(id)
         || STASIS_STATES.containsKey(id);
   }

   public static void clearOwner(LivingEntity entity) {
      UUID owner = entity.getUUID();
      ETERNAL_MOMENT.remove(owner);
      LOOP_STATES.entrySet().removeIf(entryx -> owner.equals(((IstarothActions.LoopState)entryx.getValue()).owner));
      REPEAT_STATES.entrySet().removeIf(entryx -> owner.equals(((IstarothActions.RepeatState)entryx.getValue()).owner));
      VERDICT_MARKERS.entrySet().removeIf(entryx -> owner.equals(((IstarothActions.VerdictMarker)entryx.getValue()).owner));
      Iterator<Entry<UUID, IstarothActions.StasisState>> stasisIterator = STASIS_STATES.entrySet().iterator();

      while (stasisIterator.hasNext()) {
         Entry<UUID, IstarothActions.StasisState> entry = stasisIterator.next();
         IstarothActions.StasisState state = entry.getValue();
         if (owner.equals(state.owner)) {
            if (IstarothCommon.entity(entity.level(), entry.getKey()) instanceof LivingEntity living) {
               releaseStasisWithoutDamage(living);
            }

            stasisIterator.remove();
         }
      }

      Iterator<Entry<UUID, IstarothActions.ExileState>> exileIterator = EXILES.entrySet().iterator();

      while (exileIterator.hasNext()) {
         Entry<UUID, IstarothActions.ExileState> entry = exileIterator.next();
         IstarothActions.ExileState state = entry.getValue();
         if (owner.equals(state.owner)) {
            if (IstarothCommon.entity(entity.level(), entry.getKey()) instanceof LivingEntity living) {
               forceReturnFromBanishment(living, state, true);
            }

            exileIterator.remove();
         }
      }
   }

   public static void chronicle(ManasSkillInstance instance, LivingEntity entity) {
      float debt = 0.0F;
      int marked = 0;
      int looped = 0;
      int banished = 0;
      int stasis = 0;

      for (LivingEntity target : IstarothCommon.nearbyLiving(entity, d(CONFIG.chronicleRadius))) {
         if (IstarothCommon.hasDebt(target)) {
            debt += IstarothCommon.getDebt(target);
            marked++;
         }

         if (LOOP_STATES.containsKey(target.getUUID()) || REPEAT_STATES.containsKey(target.getUUID())) {
            looped++;
         }

         if (EXILES.containsKey(target.getUUID())) {
            banished++;
         }

         if (STASIS_STATES.containsKey(target.getUUID())) {
            stasis++;
         }
      }

      entity.sendSystemMessage(
         Component.literal(
               "Chronicle opened. Debt targets: "
                  + marked
                  + " | Debt: "
                  + (int)debt
                  + " | Looped: "
                  + looped
                  + " | Banished: "
                  + banished
                  + " | Stasis: "
                  + stasis
            )
            .withStyle(ChatFormatting.GRAY)
      );
      IstarothFx.fxChronicle(entity);
   }

   public static void timeSkip(IstarothSkill skill, ManasSkillInstance instance, LivingEntity entity) {
      Level level = entity.level();
      Vec3 start = entity.position();
      Vec3 look = entity.getLookAngle().normalize();
      boolean mastered = instance.isMastered(entity);
      double distance = mastered ? d(CONFIG.timeSkipDistanceMastered) : d(CONFIG.timeSkipDistance);
      Vec3 finalPos = IstarothCommon.findSafeTeleport(level, entity, start, start.add(look.scale(distance)));
      AABB pathBox = entity.getBoundingBox()
         .expandTowards(finalPos.subtract(start))
         .inflate(mastered ? d(CONFIG.timeSkipPathInflateMastered) : d(CONFIG.timeSkipPathInflate));
      DamageSource source = IstarothCommon.damageSource(entity);

      for (int i = 1; i <= 6; i++) {
         IstarothFx.afterimage(entity, start.lerp(finalPos, i / 7.0));
      }

      entity.teleportTo(finalPos.x, finalPos.y, finalPos.z);
      entity.hurtMarked = true;
      IstarothPassives.freezeProjectiles(
         level,
         pathBox.inflate(1.5),
         mastered ? (Integer)CONFIG.timeSkipProjectileFreezeTicksMastered.get() : (Integer)CONFIG.timeSkipProjectileFreezeTicks.get(),
         entity,
         true
      );

      for (LivingEntity target : level.getEntitiesOfClass(LivingEntity.class, pathBox, e -> IstarothCommon.validEnemy(entity, e))) {
         float damage = mastered ? f(CONFIG.timeSkipDamageMastered) : f(CONFIG.timeSkipDamage);
         float velocityBonus = (float)Math.min(
            d(CONFIG.timeSkipVelocityBonusCap), target.getDeltaMovement().length() * d(CONFIG.timeSkipVelocityBonusMultiplier)
         );
         damage += velocityBonus;
         IstarothCommon.addTemporalDebt(target, damage * f(CONFIG.timeSkipDebtMultiplier), entity.getUUID());
         ACTION_LOCKS.put(
            target.getUUID(),
            Math.max(
               ACTION_LOCKS.getOrDefault(target.getUUID(), 0),
               mastered ? (Integer)CONFIG.timeSkipActionLockTicksMastered.get() : (Integer)CONFIG.timeSkipActionLockTicks.get()
            )
         );
         target.invulnerableTime = 0;
         target.hurt(source, target instanceof Player ? damage * f(CONFIG.timeSkipPlayerDamageMultiplier) : damage);
         target.setDeltaMovement(target.getDeltaMovement().scale(0.12));
         target.addEffect(
            new MobEffectInstance(ModMobEffects.getReference(ModMobEffects.TEMPORAL_STUTTER), (Integer)CONFIG.timeSkipStutterTicks.get(), 3, false, false, true)
         );
         target.addEffect(
            new MobEffectInstance(
               ModMobEffects.getReference(ModMobEffects.FUTURE_REJECTION), (Integer)CONFIG.timeSkipFutureRejectionTicks.get(), 1, false, false, true
            )
         );
         IstarothFx.clockCrackSound(target);
         IstarothFx.fxTimeSkipHit(entity, target);
      }

      IstarothFx.timeSkipTrail(level, start.add(0.0, entity.getBbHeight() * 0.5, 0.0), finalPos.add(0.0, entity.getBbHeight() * 0.5, 0.0));
      IstarothFx.teleportSound(entity);
      IstarothFx.timeStopScreen(entity, 18.0, 24, 1);
      IstarothFx.cameraShake(entity, 16.0, 0.05F, 10);
      IstarothFx.fxTimeSkip(entity, start, finalPos);
   }

   public static void momentumTheft(IstarothSkill skill, ManasSkillInstance instance, LivingEntity entity) {
      Level level = entity.level();
      Vec3 eye = entity.getEyePosition();
      Vec3 look = entity.getLookAngle().normalize();
      boolean mastered = instance.isMastered(entity);
      double range = mastered ? d(CONFIG.momentumTheftRangeMastered) : d(CONFIG.momentumTheftRange);
      AABB box = entity.getBoundingBox()
         .expandTowards(look.scale(range))
         .inflate(mastered ? d(CONFIG.momentumTheftBoxInflateMastered) : d(CONFIG.momentumTheftBoxInflate));
      double stolen = 0.0;
      int hits = 0;

      for (Entity target : level.getEntities(entity, box, e -> e instanceof Projectile || e instanceof LivingEntity || e instanceof FallingBlockEntity)) {
         Vec3 to = target.position().subtract(eye);
         if (!(to.lengthSqr() < 0.01) && !(to.normalize().dot(look) < d(CONFIG.momentumTheftViewDot))) {
            double speed = target.getDeltaMovement().length();
            stolen += Math.max(0.65, speed + target.getBbWidth() * 0.35);
            target.setDeltaMovement(Vec3.ZERO);
            target.hurtMarked = true;
            hits++;
            if (target instanceof Projectile projectile) {
               IstarothPassives.freezeProjectileHard(
                  projectile,
                  entity,
                  mastered ? (Integer)CONFIG.momentumTheftProjectileFreezeTicksMastered.get() : (Integer)CONFIG.momentumTheftProjectileFreezeTicks.get(),
                  true,
                  true
               );
            }

            if (target instanceof FallingBlockEntity falling) {
               falling.setDeltaMovement(Vec3.ZERO);
               falling.setNoGravity(true);
               falling.hurtMarked = true;
            }

            if (target instanceof LivingEntity living && IstarothCommon.validEnemy(entity, living)) {
               float damage = f(CONFIG.momentumTheftBaseDamage)
                  + (float)Math.min(
                     mastered ? d(CONFIG.momentumTheftDamageCapMastered) : d(CONFIG.momentumTheftDamageCap), stolen * d(CONFIG.momentumTheftDamageMultiplier)
                  );
               living.invulnerableTime = 0;
               living.hurt(IstarothCommon.damageSource(entity), living instanceof Player ? damage * f(CONFIG.momentumTheftPlayerDamageMultiplier) : damage);
               living.addEffect(
                  new MobEffectInstance(
                     ModMobEffects.getReference(ModMobEffects.TEMPORAL_STUTTER), (Integer)CONFIG.momentumTheftStutterTicks.get(), 3, false, false, true
                  )
               );
               living.addEffect(
                  new MobEffectInstance(
                     TensuraMobEffects.getReference(TensuraMobEffects.MOVEMENT_INTERFERENCE),
                     (Integer)CONFIG.momentumTheftMovementInterferenceTicks.get(),
                     1,
                     false,
                     false,
                     true
                  )
               );
               ACTION_LOCKS.put(living.getUUID(), Math.max(ACTION_LOCKS.getOrDefault(living.getUUID(), 0), (Integer)CONFIG.momentumTheftActionLockTicks.get()));
               IstarothCommon.addTemporalDebt(
                  living, damage * f(CONFIG.momentumTheftDebtMultiplier) + (float)stolen * f(CONFIG.momentumTheftDebtBonusMultiplier), entity.getUUID()
               );
            }

            IstarothFx.line(level, target.position().add(0.0, target.getBbHeight() * 0.5, 0.0), entity.getEyePosition(), ParticleTypes.END_ROD);
            IstarothFx.fxMomentumTheftTarget(entity, target);
         }
      }

      instance.getOrCreateTag().putDouble("IstarothStolenMomentum", Math.min(12.0, stolen));
      entity.setDeltaMovement(entity.getDeltaMovement().add(look.scale(Math.min(5.2, stolen * 0.48))));
      entity.hurtMarked = true;
      IstarothFx.line(level, eye, eye.add(look.scale(range)), ParticleTypes.WHITE_ASH);
      IstarothFx.grayPulse(entity, 3.6F);
      IstarothFx.cameraShake(entity, 16.0, 0.05F, 12);
      IstarothFx.fxMomentumTheft(entity, range);
      entity.sendSystemMessage(Component.literal("Momentum stolen from " + hits + " events.").withStyle(ChatFormatting.GRAY));
   }

   public static void borrowedSeconds(IstarothSkill skill, ManasSkillInstance instance, LivingEntity entity) {
      boolean mastered = instance.isMastered(entity);
      int enemies = 0;
      int amount = mastered ? (Integer)CONFIG.borrowedSecondsDecayAmountMastered.get() : (Integer)CONFIG.borrowedSecondsDecayAmount.get();
      double radius = mastered ? d(CONFIG.borrowedSecondsRadiusMastered) : d(CONFIG.borrowedSecondsRadius);
      DamageSource source = IstarothCommon.damageSource(entity);

      for (LivingEntity target : IstarothCommon.nearbyLiving(entity, radius)) {
         if (IstarothCommon.validEnemy(entity, target)) {
            IstarothCommon.decayBeneficialEffects(target, amount);
            IstarothCommon.extendHarmfulEffects(target, amount / 2);
            float damage = mastered ? f(CONFIG.borrowedSecondsDamageMastered) : f(CONFIG.borrowedSecondsDamage);
            float percent = target instanceof Player
               ? target.getMaxHealth() * f(CONFIG.borrowedSecondsPlayerPercentDamage)
               : target.getMaxHealth() * f(CONFIG.borrowedSecondsMobPercentDamage);
            target.invulnerableTime = 0;
            target.hurt(source, damage + percent);
            target.addEffect(
               new MobEffectInstance(
                  ModMobEffects.getReference(ModMobEffects.BORROWED_SECONDS),
                  (Integer)CONFIG.borrowedSecondsEffectTicks.get(),
                  mastered ? 3 : 2,
                  false,
                  false,
                  true
               )
            );
            target.addEffect(
               new MobEffectInstance(
                  ModMobEffects.getReference(ModMobEffects.EVENT_SILENCE), (Integer)CONFIG.borrowedSecondsEventSilenceTicks.get(), 1, false, false, true
               )
            );
            IstarothCommon.addTemporalDebt(
               target, damage + percent + (mastered ? f(CONFIG.borrowedSecondsDebtBonusMastered) : f(CONFIG.borrowedSecondsDebtBonus)), entity.getUUID()
            );
            IstarothFx.line(entity.level(), target.getEyePosition(), entity.getEyePosition(), ParticleTypes.WHITE_ASH);
            IstarothFx.fxBorrowedSecondsTarget(entity, target);
            enemies++;
         }
      }

      IstarothCommon.extendBeneficialEffects(
         entity, mastered ? (Integer)CONFIG.borrowedSecondsSelfExtendTicksMastered.get() : (Integer)CONFIG.borrowedSecondsSelfExtendTicks.get()
      );
      IstarothCommon.removeRecentHarmfulEffects(
         entity, mastered ? (Integer)CONFIG.borrowedSecondsRemoveHarmfulMastered.get() : (Integer)CONFIG.borrowedSecondsRemoveHarmful.get()
      );
      IstarothCommon.reduceCooldowns(
         instance, mastered ? (Integer)CONFIG.borrowedSecondsCooldownReductionMastered.get() : (Integer)CONFIG.borrowedSecondsCooldownReduction.get()
      );
      entity.sendSystemMessage(Component.literal("Borrowed Seconds stole duration from " + enemies + " timelines.").withStyle(ChatFormatting.GRAY));
      IstarothFx.grayPulse(entity, 7.5F);
      IstarothFx.clockCircle(entity, 6.3F, 85, MagicCircleVariant.LIGHT);
      IstarothFx.cameraShake(entity, 16.0, 0.04F, 12);
      IstarothFx.fxBorrowedSeconds(entity);
   }

   public static void eternalMoment(ManasSkillInstance instance, LivingEntity entity) {
      boolean mastered = instance.isMastered(entity);
      int ticks = mastered ? (Integer)CONFIG.eternalMomentTicksMastered.get() : (Integer)CONFIG.eternalMomentTicks.get();
      ETERNAL_MOMENT.put(entity.getUUID(), ticks);
      entity.addEffect(new MobEffectInstance(ModMobEffects.getReference(ModMobEffects.ETERNAL_MOMENT), ticks, mastered ? 3 : 2, false, false, true));
      IstarothCommon.extendBeneficialEffects(
         entity, mastered ? (Integer)CONFIG.eternalMomentSelfExtendTicksMastered.get() : (Integer)CONFIG.eternalMomentSelfExtendTicks.get()
      );
      IstarothCommon.removeRecentHarmfulEffects(
         entity, mastered ? (Integer)CONFIG.eternalMomentRemoveHarmfulMastered.get() : (Integer)CONFIG.eternalMomentRemoveHarmful.get()
      );

      for (LivingEntity target : IstarothCommon.nearbyLiving(entity, mastered ? d(CONFIG.eternalMomentRadiusMastered) : d(CONFIG.eternalMomentRadius))) {
         if (IstarothCommon.validEnemy(entity, target)) {
            float damage = mastered ? f(CONFIG.eternalMomentDamageMastered) : f(CONFIG.eternalMomentDamage);
            target.invulnerableTime = 0;
            target.hurt(IstarothCommon.damageSource(entity), target instanceof Player ? damage * f(CONFIG.eternalMomentPlayerDamageMultiplier) : damage);
            target.addEffect(
               new MobEffectInstance(
                  ModMobEffects.getReference(ModMobEffects.TEMPORAL_STUTTER), (Integer)CONFIG.eternalMomentStutterTicks.get(), 2, false, false, true
               )
            );
            IstarothCommon.addTemporalDebt(target, damage, entity.getUUID());
            IstarothFx.fxEternalMomentTarget(entity, target);
         }
      }

      IstarothFx.clockCircle(entity, 5.3F, ticks, MagicCircleVariant.LIGHT);
      IstarothFx.followClockCircle(entity, 2.7F, ticks, MagicCircleVariant.LIGHT);
      IstarothFx.timeStopScreen(entity, 16.0, 38, 1);
      IstarothFx.sound(entity, SoundEvents.BEACON_ACTIVATE, 1.2F, 0.5F);
      IstarothFx.cameraShake(entity, 13.0, 0.03F, 10);
      IstarothFx.fxEternalMoment(entity);
   }

   public static void objectPermanence(IstarothSkill skill, ManasSkillInstance instance, LivingEntity entity) {
      boolean mastered = instance.isMastered(entity);
      Entity target = ObjectSelectionHelper.getTargetingEntity(
         entity, mastered ? d(CONFIG.objectPermanenceRangeMastered) : d(CONFIG.objectPermanenceRange), false, true
      );
      if (target != null) {
         int duration = target instanceof Player
            ? (Integer)CONFIG.objectPermanencePlayerTicks.get()
            : (mastered ? (Integer)CONFIG.objectPermanenceTicksMastered.get() : (Integer)CONFIG.objectPermanenceTicks.get());
         target.setDeltaMovement(Vec3.ZERO);
         target.hurtMarked = true;
         if (target instanceof Projectile projectile) {
            IstarothPassives.freezeProjectileHard(projectile, entity, duration, false, mastered);
            IstarothFx.fxObjectPermanence(entity, projectile);
         } else if (target instanceof FallingBlockEntity falling) {
            falling.setDeltaMovement(Vec3.ZERO);
            falling.setNoGravity(true);
            falling.hurtMarked = true;
            IstarothFx.fxObjectPermanence(entity, falling);
         } else {
            if (target instanceof LivingEntity living) {
               STASIS_STATES.put(
                  living.getUUID(), new IstarothActions.StasisState(entity.getUUID(), duration, living.position(), living.getHealth(), 0.0F, mastered, 0)
               );
               living.addEffect(
                  new MobEffectInstance(ModMobEffects.getReference(ModMobEffects.OBJECT_PERMANENCE), duration, mastered ? 5 : 3, false, false, true)
               );
               living.addEffect(new MobEffectInstance(TensuraMobEffects.getReference(TensuraMobEffects.MOVEMENT_INTERFERENCE), duration, 3, false, false, true));
               living.addEffect(new MobEffectInstance(TensuraMobEffects.getReference(TensuraMobEffects.SPATIAL_BLOCKADE), duration, 2, false, false, true));
               living.addEffect(new MobEffectInstance(TensuraMobEffects.getReference(TensuraMobEffects.MAGIC_INTERFERENCE), duration, 1, false, false, true));
               ACTION_LOCKS.put(
                  living.getUUID(),
                  Math.max(ACTION_LOCKS.getOrDefault(living.getUUID(), 0), Math.min(duration, (Integer)CONFIG.objectPermanenceActionLockCap.get()))
               );
               IstarothCommon.addTemporalDebt(living, mastered ? f(CONFIG.objectPermanenceDebtMastered) : f(CONFIG.objectPermanenceDebt), entity.getUUID());
               IstarothFx.fxObjectPermanence(entity, living);
            }

            IstarothFx.grayPulse(target, 4.8F);
            IstarothFx.clockCircle(target, Math.max(3.5F, target.getBbWidth() * 5.0F), duration, MagicCircleVariant.SPACE);
            IstarothFx.glassSound(target);
         }
      }
   }

   public static void rewindTarget(ManasSkillInstance instance, LivingEntity entity) {
      boolean mastered = instance.isMastered(entity);
      Entity target = ObjectSelectionHelper.getTargetingEntity(
         entity, mastered ? d(CONFIG.rewindTargetRangeMastered) : d(CONFIG.rewindTargetRange), false, true
      );
      if (target instanceof LivingEntity living) {
         IstarothCommon.Snapshot snapshot = IstarothCommon.getOldSnapshot(
            living, mastered ? (Integer)CONFIG.rewindTargetSnapshotTicksMastered.get() : (Integer)CONFIG.rewindTargetSnapshotTicks.get()
         );
         if (snapshot != null) {
            Vec3 old = living.position();
            boolean enemy = IstarothCommon.validEnemy(entity, living);
            snapshot.restore(living, !enemy);
            if (enemy) {
               float damage = mastered ? f(CONFIG.rewindTargetDamageMastered) : f(CONFIG.rewindTargetDamage);
               living.invulnerableTime = 0;
               living.hurt(IstarothCommon.damageSource(entity), living instanceof Player ? damage * f(CONFIG.rewindTargetPlayerDamageMultiplier) : damage);
               living.addEffect(
                  new MobEffectInstance(
                     ModMobEffects.getReference(ModMobEffects.TEMPORAL_STUTTER), (Integer)CONFIG.rewindTargetStutterTicks.get(), 3, false, false, true
                  )
               );
               ACTION_LOCKS.put(living.getUUID(), Math.max(ACTION_LOCKS.getOrDefault(living.getUUID(), 0), (Integer)CONFIG.rewindTargetActionLockTicks.get()));
               IstarothCommon.addTemporalDebt(living, damage + f(CONFIG.rewindTargetDebtBonus), entity.getUUID());
            } else {
               IstarothCommon.removeRecentHarmfulEffects(living, 3);
            }

            IstarothFx.timeSkipTrail(entity.level(), old.add(0.0, living.getBbHeight() * 0.5, 0.0), living.position().add(0.0, living.getBbHeight() * 0.5, 0.0));
            IstarothFx.afterimage(living, old);
            IstarothFx.loopSnapScreen(living);
            IstarothFx.clockCrackSound(living);
            IstarothFx.cameraShake(living, 14.0, 0.045F, 9);
            IstarothFx.fxRewind(entity, living, old);
         }
      }
   }

   public static void delayedVerdict(IstarothSkill skill, ManasSkillInstance instance, LivingEntity entity) {
      boolean mastered = instance.isMastered(entity);
      Entity target = ObjectSelectionHelper.getTargetingEntity(
         entity, mastered ? d(CONFIG.delayedVerdictRangeMastered) : d(CONFIG.delayedVerdictRange), false, true
      );
      if (target instanceof LivingEntity living) {
         int ticks = mastered ? (Integer)CONFIG.delayedVerdictTicksMastered.get() : (Integer)CONFIG.delayedVerdictTicks.get();
         float openingDamage = mastered ? f(CONFIG.delayedVerdictDamageMastered) : f(CONFIG.delayedVerdictDamage);
         if (living instanceof Player) {
            openingDamage *= f(CONFIG.delayedVerdictPlayerDamageMultiplier);
         }

         VERDICT_MARKERS.put(
            living.getUUID(),
            new IstarothActions.VerdictMarker(entity.getUUID(), ticks, living.getHealth(), openingDamage * f(CONFIG.delayedVerdictStoredMultiplier))
         );
         living.invulnerableTime = 0;
         living.hurt(IstarothCommon.damageSource(entity), openingDamage);
         living.addEffect(new MobEffectInstance(ModMobEffects.getReference(ModMobEffects.DELAYED_VERDICT), ticks, 4, false, false, true));
         living.addEffect(new MobEffectInstance(ModMobEffects.getReference(ModMobEffects.TIMELINE_VERDICT), 70, 1, false, false, true));
         IstarothCommon.addTemporalDebt(
            living, openingDamage + (mastered ? f(CONFIG.delayedVerdictDebtBonusMastered) : f(CONFIG.delayedVerdictDebtBonus)), entity.getUUID()
         );
         IstarothFx.verdictClock(living, living.getBbWidth() * 5.5F, ticks);
         IstarothFx.timeStopScreen(living, 18.0, 35, 1);
         IstarothFx.sound(living, SoundEvents.TRIAL_SPAWNER_DETECT_PLAYER, 1.3F, 0.25F);
         IstarothFx.judgementStreak(living, 13.0F, mastered ? 22 : 15);
         IstarothFx.fxDelayedVerdict(entity, living);
      }
   }

   public static void effectBeforeCause(IstarothSkill skill, ManasSkillInstance instance, LivingEntity entity) {
      boolean mastered = instance.isMastered(entity);
      Entity target = ObjectSelectionHelper.getTargetingEntity(
         entity, mastered ? d(CONFIG.effectBeforeCauseRangeMastered) : d(CONFIG.effectBeforeCauseRange), false, true
      );
      if (target instanceof LivingEntity living) {
         DamageSource source = IstarothCommon.damageSource(entity);
         double resistance = IstarothCommon.resistanceFactor(living, entity);
         float base = mastered ? f(CONFIG.effectBeforeCauseBaseDamageMastered) : f(CONFIG.effectBeforeCauseBaseDamage);
         float percent = living instanceof Player
            ? living.getMaxHealth() * f(CONFIG.effectBeforeCausePlayerPercentDamage)
            : living.getMaxHealth() * f(CONFIG.effectBeforeCauseMobPercentDamage);
         float damage = (base + percent) * (float)(d(CONFIG.effectBeforeCauseResistanceBase) - resistance * d(CONFIG.effectBeforeCauseResistanceMultiplier));
         if (living instanceof Player) {
            damage *= f(CONFIG.effectBeforeCausePlayerDamageMultiplier);
         }

         living.invulnerableTime = 0;
         living.hurt(source, damage);
         living.addEffect(new MobEffectInstance(ModMobEffects.getReference(ModMobEffects.FUTURE_REJECTION), 120, mastered ? 4 : 3, false, false, true));
         living.addEffect(new MobEffectInstance(ModMobEffects.getReference(ModMobEffects.TIMELINE_VERDICT), 80, 2, false, false, true));
         IstarothCommon.addTemporalDebt(living, damage * f(CONFIG.effectBeforeCauseDebtMultiplier), entity.getUUID());
         IstarothUltimate.applyFutureRejection(entity, living, 120, mastered ? 5 : 3);
         IstarothUltimate.applyEventSilence(entity, living, 70, mastered ? 4 : 2);

         for (LivingEntity nearby : IstarothCommon.nearbyLiving(
            living, mastered ? d(CONFIG.effectBeforeCauseSplashRadiusMastered) : d(CONFIG.effectBeforeCauseSplashRadius)
         )) {
            if (IstarothCommon.validEnemy(entity, nearby) && nearby != living) {
               float splash = damage
                  * (nearby instanceof Player ? f(CONFIG.effectBeforeCauseSplashPlayerMultiplier) : f(CONFIG.effectBeforeCauseSplashMobMultiplier));
               nearby.invulnerableTime = 0;
               nearby.hurt(source, splash);
               nearby.addEffect(new MobEffectInstance(ModMobEffects.getReference(ModMobEffects.TEMPORAL_STUTTER), 85, 2, false, false, true));
               IstarothCommon.addTemporalDebt(nearby, splash, entity.getUUID());
               IstarothFx.fxEffectBeforeCauseTarget(entity, nearby);
            }
         }

         IstarothFx.line(entity.level(), entity.getEyePosition(), living.getEyePosition(), ParticleTypes.END_ROD);
         IstarothFx.verdictClock(living, living.getBbWidth() * 4.2F, 75);
         IstarothFx.finalRuptureExplosion(living, Math.max(4.5F, living.getBbWidth() * 3.5F));
         IstarothFx.sound(living, SoundEvents.PLAYER_ATTACK_SWEEP, 1.3F, 0.14F);
         IstarothFx.fxEffectBeforeCause(entity, living);
      }
   }

   public static void twentySecondLoop(IstarothSkill skill, ManasSkillInstance instance, LivingEntity entity) {
      boolean mastered = instance.isMastered(entity);
      int count = 0;
      int recordTicks = mastered ? (Integer)CONFIG.loopRecordTicksMastered.get() : (Integer)CONFIG.loopRecordTicks.get();
      DamageSource source = IstarothCommon.damageSource(entity);

      for (LivingEntity target : IstarothCommon.nearbyLiving(entity, d(CONFIG.loopRadius))) {
         if (IstarothCommon.validEnemy(entity, target)) {
            LOOP_STATES.put(
               target.getUUID(),
               new IstarothActions.LoopState(
                  entity.getUUID(),
                  recordTicks,
                  new ArrayDeque<>(),
                  false,
                  mastered ? (Integer)CONFIG.loopPlaybackTicksMastered.get() : (Integer)CONFIG.loopPlaybackTicks.get()
               )
            );
            target.addEffect(new MobEffectInstance(ModMobEffects.getReference(ModMobEffects.CAUSALITY_LOOP), recordTicks + 180, 4, false, false, true));
            IstarothUltimate.applyEventSilence(entity, target, 110, 3);
            float damage = mastered ? f(CONFIG.loopDamageMastered) : f(CONFIG.loopDamage);
            target.invulnerableTime = 0;
            target.hurt(source, target instanceof Player ? damage * f(CONFIG.loopPlayerDamageMultiplier) : damage);
            IstarothCommon.addTemporalDebt(target, damage + f(CONFIG.loopDebtBonus), entity.getUUID());
            IstarothFx.fxLoopTarget(entity, target);
            count++;
         }
      }

      entity.sendSystemMessage(Component.literal("Twenty-Second Causality Loop recorded " + count + " timelines.").withStyle(ChatFormatting.DARK_AQUA));
      IstarothFx.grayPulse(entity, 24.0F);
      IstarothFx.timeStopScreen(entity, 30.0, 70, 2);
      IstarothFx.clockCircle(entity, 26.0F, 100, MagicCircleVariant.SPACE);
      IstarothFx.sound(entity, SoundEvents.END_PORTAL_SPAWN, 1.1F, 0.4F);
      IstarothFx.cameraShake(entity, 28.0, 0.075F, 20);
      IstarothFx.fxLoop(entity);
   }

   public static void condemnedRepeat(IstarothSkill skill, ManasSkillInstance instance, LivingEntity entity) {
      boolean mastered = instance.isMastered(entity);
      Entity target = ObjectSelectionHelper.getTargetingEntity(
         entity, mastered ? d(CONFIG.condemnedRepeatRangeMastered) : d(CONFIG.condemnedRepeatRange), false, true
      );
      if (target instanceof LivingEntity living) {
         int ticks = mastered ? (Integer)CONFIG.condemnedRepeatTicksMastered.get() : (Integer)CONFIG.condemnedRepeatTicks.get();
         REPEAT_STATES.put(
            living.getUUID(),
            new IstarothActions.RepeatState(
               entity.getUUID(),
               IstarothCommon.Snapshot.of(living),
               mastered ? (Integer)CONFIG.condemnedRepeatChargesMastered.get() : (Integer)CONFIG.condemnedRepeatCharges.get(),
               ticks
            )
         );
         float damage = mastered ? f(CONFIG.condemnedRepeatDamageMastered) : f(CONFIG.condemnedRepeatDamage);
         living.invulnerableTime = 0;
         living.hurt(IstarothCommon.damageSource(entity), living instanceof Player ? damage * f(CONFIG.condemnedRepeatPlayerDamageMultiplier) : damage);
         living.addEffect(new MobEffectInstance(ModMobEffects.getReference(ModMobEffects.CONDEMNED_REPEAT), ticks, 4, false, false, true));
         IstarothUltimate.applyFutureRejection(entity, living, 150, 3);
         IstarothCommon.addTemporalDebt(living, damage + f(CONFIG.condemnedRepeatDebtBonus), entity.getUUID());
         IstarothFx.grayPulse(living, 4.6F);
         IstarothFx.clockCircle(living, living.getBbWidth() * 4.8F, 145, MagicCircleVariant.SPACE);
         IstarothFx.sound(living, SoundEvents.ANVIL_LAND, 1.1F, 0.18F);
         IstarothFx.fxCondemnedRepeat(entity, living);
      }
   }

   public static void futureExile(IstarothSkill skill, ManasSkillInstance instance, LivingEntity entity) {
      boolean mastered = instance.isMastered(entity);
      Entity target = ObjectSelectionHelper.getTargetingEntity(entity, mastered ? d(CONFIG.futureExileRangeMastered) : d(CONFIG.futureExileRange), false, true);
      if (target instanceof LivingEntity living) {
         int duration = living instanceof Player
            ? (Integer)CONFIG.futureExilePlayerTicks.get()
            : (mastered ? (Integer)CONFIG.futureExileTicksMastered.get() : (Integer)CONFIG.futureExileTicks.get());
         double resistance = IstarothCommon.resistanceFactor(living, entity);
         if (resistance > d(CONFIG.futureExileResistanceThreshold) && !(living instanceof Player)) {
            duration /= 2;
            living.addEffect(new MobEffectInstance(ModMobEffects.getReference(ModMobEffects.TEMPORAL_STUTTER), duration + 60, 4, false, false, true));
            IstarothUltimate.applyFutureRejection(entity, living, duration + 60, 3);
            IstarothCommon.addTemporalDebt(living, 85.0F, entity.getUUID());
            IstarothFx.fxFutureExileResisted(entity, living);
         } else {
            Vec3 returnPos = living.position();
            Vec3 erasedPos = returnPos.add(0.0, -4096.0, 0.0);
            EXILES.put(
               living.getUUID(),
               new IstarothActions.ExileState(entity.getUUID(), IstarothCommon.Snapshot.of(living), duration, false, returnPos, living.getHealth(), erasedPos)
            );
            beginBanishment(living, duration, 3, erasedPos);
            IstarothCommon.addTemporalDebt(living, mastered ? f(CONFIG.futureExileDebtMastered) : f(CONFIG.futureExileDebt), entity.getUUID());
            IstarothFx.grayPulse(living, 5.5F);
            IstarothFx.clockCircle(living, living.getBbWidth() * 4.4F, duration, MagicCircleVariant.SPACE);
            IstarothFx.timeStopScreen(living, 18.0, 34, 1);
            IstarothFx.teleportSound(living);
            IstarothFx.fxFutureExile(entity, living);
         }
      }
   }

   public static void pastBurial(IstarothSkill skill, ManasSkillInstance instance, LivingEntity entity) {
      boolean mastered = instance.isMastered(entity);
      Entity target = ObjectSelectionHelper.getTargetingEntity(entity, mastered ? d(CONFIG.pastBurialRangeMastered) : d(CONFIG.pastBurialRange), false, true);
      if (target instanceof LivingEntity living) {
         int ticks = (Integer)CONFIG.pastBurialTicks.get();
         Vec3 returnPos = living.position();
         Vec3 erasedPos = returnPos.add(0.0, -4096.0, 0.0);
         EXILES.put(
            living.getUUID(),
            new IstarothActions.ExileState(entity.getUUID(), IstarothCommon.Snapshot.of(living), ticks, true, returnPos, living.getHealth(), erasedPos)
         );
         beginBanishment(living, ticks, mastered ? (Integer)CONFIG.pastBurialPowerMastered.get() : (Integer)CONFIG.pastBurialPower.get(), erasedPos);
         ACTION_LOCKS.put(living.getUUID(), Math.max(ACTION_LOCKS.getOrDefault(living.getUUID(), 0), (Integer)CONFIG.pastBurialActionLockTicks.get()));
         IstarothCommon.addTemporalDebt(living, mastered ? f(CONFIG.pastBurialDebtMastered) : f(CONFIG.pastBurialDebt), entity.getUUID());
         IstarothUltimate.applyEventSilence(entity, living, ticks, 5);
         IstarothUltimate.applyFutureRejection(entity, living, ticks, 4);
         IstarothFx.line(entity.level(), entity.getEyePosition(), returnPos.add(0.0, living.getBbHeight() * 0.5, 0.0), ParticleTypes.WHITE_ASH);
         IstarothFx.clockCircle(living, living.getBbWidth() * 4.8F, ticks, MagicCircleVariant.SPACE);
         IstarothFx.timeStopScreen(living, 20.0, 42, 2);
         IstarothFx.sound(living, (SoundEvent)SoundEvents.RESPAWN_ANCHOR_DEPLETE.value(), 1.6F, 0.15F);
         IstarothFx.fxPastBurial(entity, living);
      }
   }

   public static void onIstarothHitTarget(LivingEntity owner, LivingEntity target, float damage) {
      IstarothActions.VerdictMarker marker = VERDICT_MARKERS.get(target.getUUID());
      if (marker != null && marker.owner.equals(owner.getUUID())) {
         marker.stored += damage * 1.25F;
         IstarothCommon.addTemporalDebt(target, damage * 0.65F, owner.getUUID());
         IstarothFx.clockDust(target, 1.8F, 18);
      }

      IstarothActions.StasisState stasis = STASIS_STATES.get(target.getUUID());
      if (stasis != null && stasis.owner.equals(owner.getUUID())) {
         stasis.storedDamage = stasis.storedDamage + damage * (stasis.mastered ? 0.95F : 0.7F);
         stasis.hits++;
         IstarothFx.clockDust(target, 1.6F, 14);
      }
   }

   private static void beginBanishment(LivingEntity living, int ticks, int power, Vec3 erasedPos) {
      living.stopRiding();
      living.ejectPassengers();
      living.setDeltaMovement(Vec3.ZERO);
      living.setNoGravity(true);
      living.noPhysics = true;
      living.setInvisible(true);
      living.setSilent(true);
      living.invulnerableTime = Math.max(living.invulnerableTime, 20);
      living.hurtMarked = true;
      living.teleportTo(erasedPos.x, erasedPos.y, erasedPos.z);
      living.addEffect(new MobEffectInstance(ModMobEffects.getReference(ModMobEffects.FUTURE_EXILE), ticks, power, false, false, true));
      living.addEffect(new MobEffectInstance(TensuraMobEffects.getReference(TensuraMobEffects.MOVEMENT_INTERFERENCE), ticks, power, false, false, true));
      living.addEffect(new MobEffectInstance(TensuraMobEffects.getReference(TensuraMobEffects.SPATIAL_BLOCKADE), ticks, power, false, false, true));
      living.addEffect(new MobEffectInstance(TensuraMobEffects.getReference(TensuraMobEffects.MAGIC_INTERFERENCE), ticks, power, false, false, true));
      living.addEffect(new MobEffectInstance(TensuraMobEffects.getReference(TensuraMobEffects.ENERGY_BLOCKADE), ticks, power, false, false, true));
      if (living instanceof ServerPlayer player) {
         player.addEffect(new MobEffectInstance(ModMobEffects.getReference(ModMobEffects.TEMPORAL_SCREEN), ticks, 8, false, false, true));
         player.playNotifySound(SoundEvents.END_PORTAL_SPAWN, SoundSource.PLAYERS, 0.55F, 0.25F);
      }
   }

   private static void forceReturnFromBanishment(LivingEntity living, IstarothActions.ExileState state, boolean ownerDied) {
      living.noPhysics = false;
      living.setNoGravity(false);
      living.setInvisible(false);
      living.setSilent(false);
      living.hurtMarked = true;
      living.removeEffect(ModMobEffects.getReference(ModMobEffects.FUTURE_EXILE));
      living.removeEffect(TensuraMobEffects.getReference(TensuraMobEffects.MOVEMENT_INTERFERENCE));
      living.removeEffect(TensuraMobEffects.getReference(TensuraMobEffects.SPATIAL_BLOCKADE));
      living.removeEffect(TensuraMobEffects.getReference(TensuraMobEffects.MAGIC_INTERFERENCE));
      living.removeEffect(TensuraMobEffects.getReference(TensuraMobEffects.ENERGY_BLOCKADE));
      living.removeEffect(ModMobEffects.getReference(ModMobEffects.TEMPORAL_SCREEN));
      living.teleportTo(state.returnPos.x, state.returnPos.y, state.returnPos.z);
      living.setDeltaMovement(Vec3.ZERO);
      if (ownerDied) {
         IstarothFx.grayPulse(living, 2.5F);
      }
   }

   private static void releaseStasisWithoutDamage(LivingEntity living) {
      living.removeEffect(ModMobEffects.getReference(ModMobEffects.OBJECT_PERMANENCE));
      living.removeEffect(TensuraMobEffects.getReference(TensuraMobEffects.MOVEMENT_INTERFERENCE));
      living.removeEffect(TensuraMobEffects.getReference(TensuraMobEffects.SPATIAL_BLOCKADE));
      living.removeEffect(TensuraMobEffects.getReference(TensuraMobEffects.MAGIC_INTERFERENCE));
      living.setDeltaMovement(Vec3.ZERO);
      living.hurtMarked = true;
      IstarothFx.grayPulse(living, 2.4F);
   }

   private static void tickStasis(Level level) {
      if (level instanceof ServerLevel serverLevel) {
         Iterator iterator = STASIS_STATES.entrySet().iterator();

         while (iterator.hasNext()) {
            Entry<UUID, IstarothActions.StasisState> entry = (Entry<UUID, IstarothActions.StasisState>)iterator.next();
            if (serverLevel.getEntity(entry.getKey()) instanceof LivingEntity living && living.isAlive()) {
               IstarothActions.StasisState state = entry.getValue();
               LivingEntity owner = serverLevel.getEntity(state.owner) instanceof LivingEntity o && o.isAlive() ? o : null;
               if (owner == null) {
                  releaseStasisWithoutDamage(living);
                  iterator.remove();
               } else {
                  if (living.getHealth() < state.lastHealth) {
                     float lost = state.lastHealth - living.getHealth();
                     state.storedDamage = state.storedDamage + lost * (state.mastered ? 1.55F : 1.05F);
                     state.lastHealth = living.getHealth();
                     IstarothCommon.addTemporalDebt(living, lost * 0.75F, state.owner);
                  } else {
                     state.lastHealth = living.getHealth();
                  }

                  living.teleportTo(state.anchor.x, state.anchor.y, state.anchor.z);
                  living.setDeltaMovement(Vec3.ZERO);
                  living.hurtMarked = true;
                  living.invulnerableTime = Math.max(living.invulnerableTime, 2);
                  if (living.tickCount % 6 == 0) {
                     IstarothFx.clockDust(living, state.mastered ? 2.0F : 1.4F, state.mastered ? 22 : 14);
                  }

                  if (living.tickCount % 18 == 0) {
                     IstarothFx.fxObjectPermanence(owner, living);
                  }

                  state.ticks--;
                  if (state.ticks <= 0) {
                     float burst = f(CONFIG.stasisReleaseBaseDamage)
                        + state.storedDamage * (state.mastered ? f(CONFIG.stasisStoredDamageMultiplierMastered) : f(CONFIG.stasisStoredDamageMultiplier))
                        + state.hits * (state.mastered ? f(CONFIG.stasisHitDamageMastered) : f(CONFIG.stasisHitDamage));
                     if (living instanceof Player) {
                        burst *= f(CONFIG.stasisPlayerDamageMultiplier);
                     }

                     living.invulnerableTime = 0;
                     living.hurt(IstarothCommon.damageSource(owner), burst);
                     living.addEffect(new MobEffectInstance(ModMobEffects.getReference(ModMobEffects.TEMPORAL_STUTTER), 110, 3, false, false, true));
                     living.addEffect(new MobEffectInstance(ModMobEffects.getReference(ModMobEffects.TIMELINE_VERDICT), 70, 1, false, false, true));
                     IstarothCommon.addTemporalDebt(living, burst * 0.8F, state.owner);
                     IstarothFx.clockShatter(living, state.mastered ? 6.0F : 4.2F);
                     IstarothFx.fxStasisRelease(owner, living);
                     iterator.remove();
                  }
               }
            } else {
               iterator.remove();
            }
         }
      }
   }

   private static void tickEternalMoment(Level level) {
      if (level instanceof ServerLevel serverLevel) {
         Iterator iterator = ETERNAL_MOMENT.entrySet().iterator();

         while (iterator.hasNext()) {
            Entry<UUID, Integer> entry = (Entry<UUID, Integer>)iterator.next();
            if (serverLevel.getEntity(entry.getKey()) instanceof LivingEntity living && living.isAlive()) {
               int ticks = entry.getValue();
               if (ticks <= 0) {
                  iterator.remove();
               } else {
                  entry.setValue(ticks - 1);
                  if (living.tickCount % 20 == 0) {
                     for (MobEffectInstance effect : living.getActiveEffects().stream().toList()) {
                        if (((MobEffect)effect.getEffect().value()).getCategory() == MobEffectCategory.BENEFICIAL
                           && !effect.is(ModMobEffects.getReference(ModMobEffects.ETERNAL_MOMENT))) {
                           living.removeEffect(effect.getEffect());
                           living.addEffect(
                              new MobEffectInstance(
                                 effect.getEffect(),
                                 effect.getDuration() + 32,
                                 effect.getAmplifier(),
                                 effect.isAmbient(),
                                 effect.isVisible(),
                                 effect.showIcon()
                              )
                           );
                        }
                     }

                     IstarothFx.grayPulse(living, 1.8F);
                     IstarothFx.fxEternalMoment(living);
                  }
               }
            } else {
               iterator.remove();
            }
         }
      }
   }

   private static void tickLoops(Level level) {
      Iterator<Entry<UUID, IstarothActions.LoopState>> iterator = LOOP_STATES.entrySet().iterator();

      while (iterator.hasNext()) {
         Entry<UUID, IstarothActions.LoopState> entry = iterator.next();
         if (IstarothCommon.entity(level, entry.getKey()) instanceof LivingEntity living && living.isAlive()) {
            IstarothActions.LoopState state = entry.getValue();
            LivingEntity owner = IstarothCommon.entity(level, state.owner) instanceof LivingEntity o && o.isAlive() ? o : null;
            if (owner == null) {
               iterator.remove();
            } else if (state.playback) {
               IstarothCommon.Snapshot snapshot = state.frames.pollLast();
               if (snapshot != null && state.ticks-- > 0) {
                  snapshot.restorePositionOnly(living);
                  living.setDeltaMovement(Vec3.ZERO);
                  living.addEffect(new MobEffectInstance(ModMobEffects.getReference(ModMobEffects.CAUSALITY_LOOP), 35, 4, false, false, true));
                  ACTION_LOCKS.put(living.getUUID(), Math.max(ACTION_LOCKS.getOrDefault(living.getUUID(), 0), 14));
                  if (state.ticks % 8 == 0) {
                     float damage = living instanceof Player ? 10.0F : 20.0F;
                     IstarothCommon.addTemporalDebt(living, damage + 12.0F, state.owner);
                     living.invulnerableTime = 0;
                     living.hurt(IstarothCommon.damageSource(owner), damage);
                  }

                  if (state.ticks % 5 == 0) {
                     IstarothFx.loopSnapScreen(living);
                  }
               } else {
                  iterator.remove();
               }
            } else {
               state.frames.addLast(IstarothCommon.Snapshot.of(living));

               while (state.frames.size() > 140) {
                  state.frames.removeFirst();
               }

               state.ticks--;
               if (state.ticks <= 0) {
                  state.playback = true;
                  state.ticks = Math.min(state.playbackTicks, state.frames.size());
                  IstarothFx.finalRuptureExplosion(living, 4.5F);
               }
            }
         } else {
            iterator.remove();
         }
      }
   }

   private static void tickRepeats(Level level) {
      Iterator<Entry<UUID, IstarothActions.RepeatState>> iterator = REPEAT_STATES.entrySet().iterator();

      while (iterator.hasNext()) {
         Entry<UUID, IstarothActions.RepeatState> entry = iterator.next();
         if (IstarothCommon.entity(level, entry.getKey()) instanceof LivingEntity living && living.isAlive()) {
            IstarothActions.RepeatState state = entry.getValue();
            LivingEntity owner = IstarothCommon.entity(level, state.owner) instanceof LivingEntity o && o.isAlive() ? o : null;
            if (owner == null) {
               iterator.remove();
            } else {
               state.ticks--;
               if (state.ticks > 0 && state.charges > 0) {
                  boolean brokeLoop = living.position().distanceTo(state.anchor.pos) > 5.5
                     || living.getDeltaMovement().length() > 1.15
                     || living.isFallFlying();
                  if (brokeLoop) {
                     state.anchor.restorePositionOnly(living);
                     living.setDeltaMovement(Vec3.ZERO);
                     living.addEffect(new MobEffectInstance(ModMobEffects.getReference(ModMobEffects.TEMPORAL_STUTTER), 60, 3, false, false, true));
                     float damage = living instanceof Player ? 22.0F : 44.0F;
                     living.invulnerableTime = 0;
                     living.hurt(IstarothCommon.damageSource(owner), damage);
                     IstarothCommon.addTemporalDebt(living, damage + 28.0F, state.owner);
                     state.charges--;
                     IstarothFx.loopSnapScreen(living);
                     IstarothFx.finalRuptureExplosion(living, 3.5F);
                     ACTION_LOCKS.put(living.getUUID(), Math.max(ACTION_LOCKS.getOrDefault(living.getUUID(), 0), 26));
                  }
               } else {
                  iterator.remove();
               }
            }
         } else {
            iterator.remove();
         }
      }
   }

   private static void tickExiles(Level level) {
      Iterator<Entry<UUID, IstarothActions.ExileState>> iterator = EXILES.entrySet().iterator();

      while (iterator.hasNext()) {
         Entry<UUID, IstarothActions.ExileState> entry = iterator.next();
         if (IstarothCommon.entity(level, entry.getKey()) instanceof LivingEntity living && living.isAlive()) {
            IstarothActions.ExileState state = entry.getValue();
            LivingEntity owner = IstarothCommon.entity(level, state.owner) instanceof LivingEntity o && o.isAlive() ? o : null;
            if (owner == null) {
               forceReturnFromBanishment(living, state, true);
               iterator.remove();
            } else {
               living.teleportTo(state.erasedPos.x, state.erasedPos.y, state.erasedPos.z);
               living.setDeltaMovement(Vec3.ZERO);
               living.hurtMarked = true;
               living.setInvisible(true);
               living.setNoGravity(true);
               living.setSilent(true);
               living.noPhysics = true;
               living.invulnerableTime = Math.max(living.invulnerableTime, 20);
               if (living.tickCount % 8 == 0) {
                  IstarothFx.fxBanishmentHold(owner, living, state.absolute);
               }

               state.ticks--;
               if (state.ticks <= 0) {
                  forceReturnFromBanishment(living, state, false);
                  float debt = IstarothCommon.consumeDebt(living, state.owner);
                  float missing = Math.max(0.0F, state.startHealth - living.getHealth());
                  float damage = state.absolute ? 125.0F + debt * 1.35F + missing : 75.0F + debt * 0.9F + missing * 0.45F;
                  if (living instanceof Player) {
                     damage *= state.absolute ? 0.7F : 0.55F;
                  }

                  living.addEffect(
                     new MobEffectInstance(
                        ModMobEffects.getReference(ModMobEffects.TEMPORAL_STUTTER), state.absolute ? 150 : 110, state.absolute ? 4 : 3, false, false, true
                     )
                  );
                  living.addEffect(
                     new MobEffectInstance(ModMobEffects.getReference(ModMobEffects.TIMELINE_VERDICT), 100, state.absolute ? 3 : 2, false, false, true)
                  );
                  living.invulnerableTime = 0;
                  living.hurt(IstarothCommon.damageSource(owner), damage);
                  IstarothFx.grayPulse(living, state.absolute ? 6.0F : 4.2F);
                  IstarothFx.clockShatter(living, state.absolute ? 6.5F : 4.2F);
                  IstarothFx.fxBanishmentReturn(owner, living, state.absolute);
                  iterator.remove();
               }
            }
         } else {
            iterator.remove();
         }
      }
   }

   private static void tickVerdictMarkers(Level level) {
      if (level instanceof ServerLevel serverLevel) {
         Iterator iterator = VERDICT_MARKERS.entrySet().iterator();

         while (iterator.hasNext()) {
            Entry<UUID, IstarothActions.VerdictMarker> entry = (Entry<UUID, IstarothActions.VerdictMarker>)iterator.next();
            if (serverLevel.getEntity(entry.getKey()) instanceof LivingEntity living && living.isAlive()) {
               IstarothActions.VerdictMarker marker = entry.getValue();
               LivingEntity owner = serverLevel.getEntity(marker.owner) instanceof LivingEntity o && o.isAlive() ? o : null;
               if (owner == null) {
                  iterator.remove();
               } else {
                  if (living.getHealth() < marker.health) {
                     float lost = marker.health - living.getHealth();
                     marker.stored += lost * 1.0F;
                     IstarothCommon.addTemporalDebt(living, lost * 0.45F, owner.getUUID());
                     marker.health = living.getHealth();
                  } else if (living.getHealth() > marker.health + 1.0F) {
                     float healed = living.getHealth() - marker.health;
                     marker.stored += healed * 1.35F;
                     marker.health = living.getHealth();
                  } else {
                     marker.health = living.getHealth();
                  }

                  if (living.tickCount % 10 == 0) {
                     IstarothFx.clockDust(living, 1.4F, 16);
                     IstarothFx.fxDelayedVerdict(owner, living);
                  }

                  marker.ticks--;
                  if (marker.ticks <= 0) {
                     float debt = IstarothCommon.consumeDebt(living, owner.getUUID());
                     float percent = living instanceof Player ? living.getMaxHealth() * 0.14F : living.getMaxHealth() * 0.32F;
                     float damage = 105.0F + marker.stored * 1.45F + debt * 1.55F + percent;
                     if (living instanceof Player) {
                        damage *= 0.78F;
                     }

                     living.invulnerableTime = 0;
                     living.hurt(IstarothCommon.damageSource(owner), damage);
                     living.addEffect(new MobEffectInstance(ModMobEffects.getReference(ModMobEffects.TIMELINE_VERDICT), 130, 4, false, false, true));
                     ACTION_LOCKS.put(living.getUUID(), Math.max(ACTION_LOCKS.getOrDefault(living.getUUID(), 0), 55));
                     IstarothFx.clockShatter(living, 5.8F);
                     IstarothFx.judgementStreak(living, 15.0F, 22);
                     IstarothFx.fxVerdictDetonate(owner, living);
                     iterator.remove();
                  }
               }
            } else {
               iterator.remove();
            }
         }
      }
   }

   private static void tickActionLocks(Level level) {
      if (level instanceof ServerLevel serverLevel) {
         Iterator iterator = ACTION_LOCKS.entrySet().iterator();

         while (iterator.hasNext()) {
            Entry<UUID, Integer> entry = (Entry<UUID, Integer>)iterator.next();
            if (serverLevel.getEntity(entry.getKey()) instanceof LivingEntity living && living.isAlive()) {
               int ticks = entry.getValue();
               living.setDeltaMovement(living.getDeltaMovement().scale(0.05));
               living.hurtMarked = true;
               if (living.tickCount % 6 == 0) {
                  IstarothFx.clockDust(living, 1.0F, 7);
               }

               if (ticks <= 0) {
                  iterator.remove();
               } else {
                  entry.setValue(ticks - 1);
               }
            } else {
               iterator.remove();
            }
         }
      }
   }

   private static final class ExileState {
      private final UUID owner;
      private final IstarothCommon.Snapshot snapshot;
      private int ticks;
      private final boolean absolute;
      private final Vec3 returnPos;
      private final float startHealth;
      private final Vec3 erasedPos;

      private ExileState(UUID owner, IstarothCommon.Snapshot snapshot, int ticks, boolean absolute, Vec3 returnPos, float startHealth, Vec3 erasedPos) {
         this.owner = owner;
         this.snapshot = snapshot;
         this.ticks = ticks;
         this.absolute = absolute;
         this.returnPos = returnPos;
         this.startHealth = startHealth;
         this.erasedPos = erasedPos;
      }
   }

   private static final class LoopState {
      private final UUID owner;
      private int ticks;
      private final ArrayDeque<IstarothCommon.Snapshot> frames;
      private boolean playback;
      private final int playbackTicks;

      private LoopState(UUID owner, int ticks, ArrayDeque<IstarothCommon.Snapshot> frames, boolean playback, int playbackTicks) {
         this.owner = owner;
         this.ticks = ticks;
         this.frames = frames;
         this.playback = playback;
         this.playbackTicks = playbackTicks;
      }
   }

   private static final class RepeatState {
      private final UUID owner;
      private final IstarothCommon.Snapshot anchor;
      private int charges;
      private int ticks;

      private RepeatState(UUID owner, IstarothCommon.Snapshot anchor, int charges, int ticks) {
         this.owner = owner;
         this.anchor = anchor;
         this.charges = charges;
         this.ticks = ticks;
      }
   }

   private static final class StasisState {
      private final UUID owner;
      private int ticks;
      private final Vec3 anchor;
      private float lastHealth;
      private float storedDamage;
      private final boolean mastered;
      private int hits;

      private StasisState(UUID owner, int ticks, Vec3 anchor, float lastHealth, float storedDamage, boolean mastered, int hits) {
         this.owner = owner;
         this.ticks = ticks;
         this.anchor = anchor;
         this.lastHealth = lastHealth;
         this.storedDamage = storedDamage;
         this.mastered = mastered;
         this.hits = hits;
      }
   }

   private static final class VerdictMarker {
      private final UUID owner;
      private int ticks;
      private float health;
      private float stored;

      private VerdictMarker(UUID owner, int ticks, float health, float stored) {
         this.owner = owner;
         this.ticks = ticks;
         this.health = health;
         this.stored = stored;
      }
   }
}
