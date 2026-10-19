package com.github.wal_bos.moreskills.handler;

import com.github.wal_bos.moreskills.ability.skill.ultimate.WitchOfEnvySatellaSkill;
import com.github.wal_bos.moreskills.configs.MoreSkillsConfigs;
import com.github.wal_bos.moreskills.net.msg.WitchOfEnvySatellaVfxPayload;
import com.github.wal_bos.moreskills.registry.skill.AllSkills;
import dev.architectury.event.EventResult;
import dev.architectury.event.events.common.EntityEvent.LivingDeath;
import io.github.manasmods.manascore.skill.api.EntityEvents;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import io.github.manasmods.manascore.skill.api.EntityEvents.LivingDamageEvent;
import io.github.manasmods.tensura.effect.template.TensuraMobEffect;
import io.github.manasmods.tensura.registry.attribute.TensuraAttributes;
import io.github.manasmods.tensura.registry.effect.TensuraMobEffects;
import io.github.manasmods.tensura.storage.TensuraStorages;
import io.github.manasmods.tensura.storage.ep.IExistence;
import io.github.manasmods.tensura.util.EnergyHelper;
import io.github.manasmods.tensura.util.ObjectSelectionHelper;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.Map.Entry;
import java.util.function.Predicate;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.tick.LevelTickEvent.Post;

public final class WitchOfEnvySatellaHandler {
   private static final Map<UUID, WitchOfEnvySatellaHandler.HeldShadowHand> HELD_HANDS = new HashMap<>();
   private static final Map<UUID, WitchOfEnvySatellaHandler.ShadowHands> HAND_SWARMS = new HashMap<>();
   private static final Map<UUID, WitchOfEnvySatellaHandler.Authority> AUTHORITIES = new HashMap<>();
   private static boolean initialized;

   private WitchOfEnvySatellaHandler() {
   }

   private static MoreSkillsConfigs.WitchOfEnvySatellaConfig cfg() {
      return MoreSkillsConfigs.GRAND.witchOfEnvySatella;
   }

   public static void init() {
      if (!initialized) {
         initialized = true;
         WitchOfEnvyDevouringShadowsHandler.init();
         NeoForge.EVENT_BUS.addListener(WitchOfEnvySatellaHandler::onLevelTick);
         EntityEvents.LIVING_DAMAGE.register((LivingDamageEvent)(entity, source, amount) -> {
            if (entity instanceof LivingEntity living && !living.level().isClientSide()) {
               Optional<ManasSkillInstance> optional = SkillAPI.getSkillsFrom(living).getSkill((ManasSkill)AllSkills.WITCH_OF_ENVY_SATELLA.get());
               if (optional.isEmpty()) {
                  return EventResult.pass();
               }

               if (!isPhysicalDamage(source)) {
                  return EventResult.pass();
               }

               amount.set((Float)amount.get() * ((Double)cfg().physicalDamageTakenMultiplier.get()).floatValue());
               return EventResult.pass();
            } else {
               return EventResult.pass();
            }
         });
         EntityEvents.DEATH_EVENT_FIRST
            .register(
               (LivingDeath)(entity, source) -> {
                  if (entity instanceof LivingEntity living && !living.level().isClientSide()) {
                     Optional<ManasSkillInstance> self = SkillAPI.getSkillsFrom(living).getSkill((ManasSkill)AllSkills.WITCH_OF_ENVY_SATELLA.get());
                     if (self.isPresent() && trySelfRevive(living, self.get(), source)) {
                        return EventResult.interruptFalse();
                     }

                     if (tryAuthorityRevive(living)) {
                        return EventResult.interruptFalse();
                     }

                     clearOwner(living);
                     AUTHORITIES.entrySet()
                        .removeIf(
                           entry -> entry.getKey().equals(living.getUUID())
                              || entry.getValue().ally.equals(living.getUUID()) && entry.getValue().checkpoint == null
                        );
                     return EventResult.pass();
                  } else {
                     return EventResult.pass();
                  }
               }
            );
      }
   }

   public static void tickOwner(WitchOfEnvySatellaSkill skill, ManasSkillInstance instance, LivingEntity owner) {
      init();
      applyOwnerTick(skill, instance, owner);
   }

   private static void applyOwnerTick(WitchOfEnvySatellaSkill skill, ManasSkillInstance instance, LivingEntity owner) {
      skill.onSkillMastered(instance, owner);
      if (owner.tickCount % 80 == 0) {
         skill.onSkillMastered(instance, owner);
      }

      if (owner.tickCount % 20 == 0 && instance.isToggled()) {
         applyFearAura(owner, instance);
      }

      ensureReviveWindow(instance, owner);
      if (owner.tickCount % Math.max(1, (Integer)cfg().autoCheckpointIntervalTicks.get()) == 0) {
         setCheckpoint(owner, owner, instance, false);

         for (WitchOfEnvySatellaHandler.Authority authority : AUTHORITIES.values()) {
            if (authority.owner.equals(owner.getUUID())) {
               LivingEntity ally = findLiving(owner.level(), authority.ally);
               if (ally != null && ally.isAlive()) {
                  setCheckpoint(owner, ally, instance, false);
               }
            }
         }
      }
   }

   private static void tickPlayerFearAuras(ServerLevel level) {
      for (ServerPlayer player : level.players()) {
         Optional<ManasSkillInstance> optional = SkillAPI.getSkillsFrom(player).getSkill((ManasSkill)AllSkills.WITCH_OF_ENVY_SATELLA.get());
         if (!optional.isEmpty() && optional.get().isToggled()) {
            applyFearAura(player, optional.get());
         }
      }
   }

   public static void applyFearAura(LivingEntity owner, ManasSkillInstance instance) {
      if (owner != null && instance != null && !owner.level().isClientSide()) {
         if (owner.level() instanceof ServerLevel level) {
            double ratio = chance01((Double)cfg().fearMagiculeRatio.get());
            if (ratio <= 0.0) {
               ratio = 0.3;
            }

            double threshold = EnergyHelper.getMaxMagicule(owner) * ratio;
            if (!(threshold <= 0.0)) {
               int duration = Math.max(40, (Integer)cfg().fearDurationTicks.get());
               AABB box = owner.getBoundingBox().inflate(5.0, 5.0, 5.0);

               for (LivingEntity target : level.getEntitiesOfClass(LivingEntity.class, box, targetx -> shouldFearAffect(owner, targetx))) {
                  if (!(EnergyHelper.getMaxMagicule(target) >= threshold)) {
                     target.forceAddEffect(new MobEffectInstance(TensuraMobEffects.getReference(TensuraMobEffects.FEAR), duration, 2, false, true, true), owner);
                  }
               }
            }
         }
      }
   }

   private static boolean shouldFearAffect(LivingEntity owner, LivingEntity target) {
      return target != null && target.isAlive() && target != owner && !target.isAlliedTo(owner)
         ? !(target instanceof Player player && (player.isCreative() || player.isSpectator()))
         : false;
   }

   private static double chance01(double value) {
      return value > 1.0 ? value / 100.0 : value;
   }

   public static void ensureReviveWindow(ManasSkillInstance instance, LivingEntity owner) {
      CompoundTag tag = instance.getOrCreateTag();
      long now = owner.level().getGameTime();
      long reset = tag.getLong("WitchEnvyReviveReset");
      int max = instance.isMastered(owner) ? (Integer)cfg().revivesMastered.get() : (Integer)cfg().revives.get();
      if (reset > 0L && now < reset) {
         if (!tag.contains("WitchEnvyRevives") || tag.getInt("WitchEnvyRevives") > max) {
            tag.putInt("WitchEnvyRevives", Math.min(max, Math.max(0, tag.getInt("WitchEnvyRevives"))));
            instance.markDirty();
         }
      } else {
         tag.putLong("WitchEnvyReviveReset", now + ((Integer)cfg().reviveWindowTicks.get()).intValue());
         tag.putInt("WitchEnvyRevives", max);
         instance.markDirty();
      }
   }

   public static boolean trySelfRevive(LivingEntity entity, ManasSkillInstance instance, DamageSource source) {
      if (entity != null && !entity.level().isClientSide()) {
         if (source == null || !source.is(DamageTypes.FELL_OUT_OF_WORLD) && !source.is(DamageTypes.GENERIC_KILL)) {
            ensureReviveWindow(instance, entity);
            CompoundTag tag = instance.getOrCreateTag();
            int charges = tag.getInt("WitchEnvyRevives");
            if (charges <= 0) {
               return false;
            }

            double maxMagicule = EnergyHelper.getMaxMagicule(entity);
            double cost = maxMagicule
               * instance.isMastered(entity) ? (Double)cfg().reviveMagiculeCostRatioMastered.get() : (Double)cfg().reviveMagiculeCostRatio.get();
            IExistence existence = TensuraStorages.getExistenceFrom(entity);
            if (!entity.hasInfiniteMaterials() && existence.getMagicule() < cost) {
               return false;
            }

            tag.putInt("WitchEnvyRevives", charges - 1);
            instance.markDirty();
            tunerRevive(
               entity,
               instance,
               source,
               (Double)cfg().reviveHealthRatio.get(),
               (Double)cfg().reviveSpiritualHealthRatio.get(),
               (Double)cfg().reviveAuraRatio.get(),
               (Double)cfg().reviveMagiculeRatio.get(),
               cost,
               false
            );

            for (int i = 0; i < instance.getModes(); i++) {
               instance.setCoolDown(60, i);
            }

            if (entity instanceof Player player) {
               player.displayClientMessage(
                  Component.literal("Return by Death consumed one Envy revive. " + (charges - 1) + " left.").withStyle(ChatFormatting.DARK_PURPLE), true
               );
            }

            return true;
         } else {
            return false;
         }
      } else {
         return false;
      }
   }

   private static boolean tryAuthorityRevive(LivingEntity ally) {
      WitchOfEnvySatellaHandler.Authority authority = AUTHORITIES.get(ally.getUUID());
      if (authority != null && authority.checkpoint != null) {
         if (ally.level() instanceof ServerLevel level) {
            LivingEntity owner = findLiving(level, authority.owner);
            if (owner != null && owner.isAlive()) {
               Optional<ManasSkillInstance> optional = SkillAPI.getSkillsFrom(owner).getSkill((ManasSkill)AllSkills.WITCH_OF_ENVY_SATELLA.get());
               if (optional.isEmpty()) {
                  return false;
               }

               ManasSkillInstance instance = optional.get();
               IExistence ownerExistence = TensuraStorages.getExistenceFrom(owner);
               IExistence allyExistence = TensuraStorages.getExistenceFrom(ally);
               double allyCurrentMagicule = Math.max(0.0, allyExistence.getMagicule());
               if (instance.isMastered(owner)) {
                  double drained = allyCurrentMagicule * 0.5;
                  allyExistence.setMagicule(Math.max(0.0, allyCurrentMagicule - drained));
                  ownerExistence.setMagicule(Math.min(EnergyHelper.getMaxMagicule(owner), ownerExistence.getMagicule() + drained));
                  allyExistence.markDirty();
                  ownerExistence.markDirty();
               } else {
                  if (!owner.hasInfiniteMaterials() && ownerExistence.getMagicule() < allyCurrentMagicule) {
                     return false;
                  }

                  ownerExistence.setMagicule(Math.max(0.0, ownerExistence.getMagicule() - allyCurrentMagicule));
                  ownerExistence.markDirty();
               }

               authority.checkpoint.restore(ally);
               ally.invulnerableTime = Math.max(80, ally.invulnerableTime);
               ally.level().playSound(null, ally.getX(), ally.getY(), ally.getZ(), SoundEvents.TOTEM_USE, SoundSource.PLAYERS, 1.0F, 0.55F);
               if (ally instanceof ServerPlayer player) {
                  player.displayClientMessage(
                     Component.literal("Authority of Envy returned you to the checkpoint.").withStyle(ChatFormatting.DARK_PURPLE), false
                  );
               }

               if (owner instanceof ServerPlayer player) {
                  player.displayClientMessage(
                     Component.literal("Authority of Envy revived " + ally.getName().getString() + ".").withStyle(ChatFormatting.DARK_PURPLE), true
                  );
               }

               WitchOfEnvySatellaVfxPayload.sendAuthority(level, owner, ally, 90, instance.isMastered(owner));
               return true;
            } else {
               return false;
            }
         } else {
            return false;
         }
      } else {
         return false;
      }
   }

   public static void tunerRevive(
      LivingEntity entity,
      ManasSkillInstance instance,
      DamageSource source,
      double hpRatio,
      double shpRatio,
      double auraRatio,
      double magiculeRatio,
      double magiculeCost,
      boolean minimumOnly
   ) {
      entity.setHealth(Math.max(entity.getHealth(), entity.getMaxHealth() * (float)hpRatio));
      entity.invulnerableTime = Math.max(80, entity.invulnerableTime);
      Predicate<Holder<MobEffect>> predicate = effect -> ((MobEffect)effect.value()).getCategory() == MobEffectCategory.HARMFUL;
      TensuraMobEffect.removePredicateEffect(entity, predicate);
      TensuraStorages.resetEffect(entity);
      IExistence existence = TensuraStorages.getExistenceFrom(entity);
      double shp = entity.getAttributeValue(TensuraAttributes.MAX_SPIRITUAL_HEALTH) * shpRatio;
      double aura = EnergyHelper.getMaxAura(entity) * auraRatio;
      double magicule = EnergyHelper.getMaxMagicule(entity) * magiculeRatio;
      existence.setSpiritualHealth(minimumOnly ? Math.max(existence.getSpiritualHealth(), shp) : shp);
      existence.setAura(minimumOnly ? Math.max(existence.getAura(), aura) : aura);
      existence.setMagicule(Math.max(0.0, (minimumOnly ? Math.max(existence.getMagicule(), magicule) : magicule) - magiculeCost));
      existence.markDirty();
      entity.addEffect(new MobEffectInstance(TensuraMobEffects.getReference(TensuraMobEffects.SELF_REGENERATION), 160, 1, true, false, true));
      entity.level().playSound(null, entity.getX(), entity.getY(), entity.getZ(), SoundEvents.TOTEM_USE, SoundSource.PLAYERS, 1.0F, 0.8F);
      if (entity.level() instanceof ServerLevel level) {
         level.sendParticles(ParticleTypes.FLASH, entity.getX(), entity.getY() + entity.getBbHeight() * 0.5, entity.getZ(), 1, 0.0, 0.0, 0.0, 0.0);
         level.sendParticles(
            ParticleTypes.SOUL_FIRE_FLAME, entity.getX(), entity.getY() + entity.getBbHeight() * 0.5, entity.getZ(), 36, 0.65, 0.75, 0.65, 0.05
         );
      }

      instance.addMasteryPoint(entity);
   }

   public static void bindAuthority(LivingEntity owner, LivingEntity ally, ManasSkillInstance instance) {
      WitchOfEnvySatellaHandler.Authority authority = new WitchOfEnvySatellaHandler.Authority(owner.getUUID(), ally.getUUID(), instance.isMastered(owner));
      authority.checkpoint = WitchOfEnvySatellaHandler.Snapshot.of(ally);
      AUTHORITIES.put(ally.getUUID(), authority);
      if (owner.level() instanceof ServerLevel level) {
         WitchOfEnvySatellaVfxPayload.sendAuthority(level, owner, ally, 80, instance.isMastered(owner));
      }
   }

   public static boolean hasAuthority(LivingEntity owner, LivingEntity ally) {
      WitchOfEnvySatellaHandler.Authority authority = AUTHORITIES.get(ally.getUUID());
      return authority != null && authority.owner.equals(owner.getUUID());
   }

   public static boolean removeAuthority(LivingEntity owner, LivingEntity ally) {
      WitchOfEnvySatellaHandler.Authority authority = AUTHORITIES.get(ally.getUUID());
      if (authority != null && authority.owner.equals(owner.getUUID())) {
         AUTHORITIES.remove(ally.getUUID());
         return true;
      } else {
         return false;
      }
   }

   public static void setCheckpoint(LivingEntity owner, LivingEntity target, ManasSkillInstance instance, boolean manual) {
      if (target == owner) {
         instance.getOrCreateTag().put("WitchEnvySelfCheckpoint", WitchOfEnvySatellaHandler.Snapshot.of(target).save());
         instance.markDirty();
      } else {
         WitchOfEnvySatellaHandler.Authority authority = AUTHORITIES.computeIfAbsent(
            target.getUUID(), uuid -> new WitchOfEnvySatellaHandler.Authority(owner.getUUID(), target.getUUID(), instance.isMastered(owner))
         );
         if (!authority.owner.equals(owner.getUUID())) {
            return;
         }

         authority.mastered = instance.isMastered(owner);
         authority.checkpoint = WitchOfEnvySatellaHandler.Snapshot.of(target);
      }

      if (manual && owner instanceof Player player) {
         player.displayClientMessage(
            Component.literal("Checkpoint saved for " + target.getName().getString() + ".").withStyle(ChatFormatting.DARK_PURPLE), true
         );
      }

      if (owner.level() instanceof ServerLevel level) {
         WitchOfEnvySatellaVfxPayload.sendCheckpoint(level, target, 70, instance.isMastered(owner));
      }
   }

   public static void startShadowHand(WitchOfEnvySatellaSkill skill, ManasSkillInstance instance, LivingEntity owner, LivingEntity target) {
      HELD_HANDS.put(
         owner.getUUID(),
         new WitchOfEnvySatellaHandler.HeldShadowHand(
            owner.getUUID(), target.getUUID(), owner.level().dimension(), owner.level().getGameTime(), instance.isMastered(owner)
         )
      );
      if (owner.level() instanceof ServerLevel level) {
         WitchOfEnvySatellaVfxPayload.sendShadowHand(level, owner, target, 40, instance.isMastered(owner));
      }
   }

   public static void holdShadowHand(WitchOfEnvySatellaSkill skill, ManasSkillInstance instance, LivingEntity owner, int heldTicks) {
      WitchOfEnvySatellaHandler.HeldShadowHand state = HELD_HANDS.get(owner.getUUID());
      if (state == null) {
         LivingEntity target = ObjectSelectionHelper.getTargetingEntity(owner, (Double)cfg().shadowHandRange.get(), false);
         if (target != null) {
            startShadowHand(skill, instance, owner, target);
         }
      } else {
         LivingEntity target = findLiving(owner.level(), state.target);
         if (target != null
            && target.isAlive()
            && !(target.distanceToSqr(owner) > (Double)cfg().shadowHandBreakRange.get() * (Double)cfg().shadowHandBreakRange.get())) {
            if (owner.level().getGameTime() % 20L == 0L) {
               float physical = state.mastered
                  ? ((Double)cfg().shadowHandPhysicalDamageMastered.get()).floatValue()
                  : ((Double)cfg().shadowHandPhysicalDamage.get()).floatValue();
               float spiritual = state.mastered
                  ? ((Double)cfg().shadowHandSpiritualDamageMastered.get()).floatValue()
                  : ((Double)cfg().shadowHandSpiritualDamage.get()).floatValue();
               skill.forceMixedDamage(instance, owner, target, skill.envyDamageSource(owner, instance, 1), physical, spiritual);
               target.addEffect(
                  new MobEffectInstance(
                     TensuraMobEffects.getReference(TensuraMobEffects.MOVEMENT_INTERFERENCE),
                     (Integer)cfg().shadowHandSlowTicks.get(),
                     (Integer)cfg().shadowHandSlowAmplifier.get(),
                     false,
                     false,
                     true
                  )
               );
            }

            if (owner.level() instanceof ServerLevel level && owner.tickCount % 10 == 0) {
               WitchOfEnvySatellaVfxPayload.sendShadowHand(level, owner, target, 24, state.mastered);
            }
         } else {
            HELD_HANDS.remove(owner.getUUID());
         }
      }
   }

   public static void stopShadowHand(UUID owner) {
      HELD_HANDS.remove(owner);
   }

   public static void startShadowHands(WitchOfEnvySatellaSkill skill, ManasSkillInstance instance, LivingEntity owner, LivingEntity target) {
      int count = instance.isMastered(owner) ? (Integer)cfg().shadowHandsCountMastered.get() : (Integer)cfg().shadowHandsCount.get();
      HAND_SWARMS.put(
         owner.getUUID(),
         new WitchOfEnvySatellaHandler.ShadowHands(
            owner.getUUID(),
            target.getUUID(),
            owner.level().dimension(),
            owner.level().getGameTime(),
            owner.level().getGameTime() + ((Integer)cfg().shadowHandsDurationTicks.get()).intValue(),
            instance.isMastered(owner),
            count
         )
      );
      if (owner.level() instanceof ServerLevel level) {
         WitchOfEnvySatellaVfxPayload.sendShadowHands(level, owner, target, count, (Integer)cfg().shadowHandsDurationTicks.get(), instance.isMastered(owner));
      }
   }

   private static void onLevelTick(Post event) {
      if (event.getLevel() instanceof ServerLevel level) {
         if (level.getGameTime() % 20L == 0L) {
            tickPlayerFearAuras(level);
         }

         tickHeldHands(level);
         tickHandSwarms(level);
      }
   }

   private static void tickHeldHands(ServerLevel level) {
      Iterator<Entry<UUID, WitchOfEnvySatellaHandler.HeldShadowHand>> iterator = HELD_HANDS.entrySet().iterator();

      while (iterator.hasNext()) {
         WitchOfEnvySatellaHandler.HeldShadowHand state = iterator.next().getValue();
         if (state.dimension == level.dimension()) {
            LivingEntity owner = findLiving(level, state.owner);
            LivingEntity target = findLiving(level, state.target);
            if (owner != null
               && target != null
               && owner.isAlive()
               && target.isAlive()
               && !(owner.distanceToSqr(target) > (Double)cfg().shadowHandBreakRange.get() * (Double)cfg().shadowHandBreakRange.get())) {
               Optional<ManasSkillInstance> optional = SkillAPI.getSkillsFrom(owner).getSkill((ManasSkill)AllSkills.WITCH_OF_ENVY_SATELLA.get());
               if (!(!optional.isEmpty() && optional.get().getSkill() instanceof WitchOfEnvySatellaSkill skill)) {
                  iterator.remove();
               } else if (level.getGameTime() % 20L == 0L) {
                  skill.forceMixedDamage(
                     optional.get(),
                     owner,
                     target,
                     skill.envyDamageSource(owner, optional.get(), 1),
                     state.mastered
                        ? ((Double)cfg().shadowHandPhysicalDamageMastered.get()).floatValue()
                        : ((Double)cfg().shadowHandPhysicalDamage.get()).floatValue(),
                     state.mastered
                        ? ((Double)cfg().shadowHandSpiritualDamageMastered.get()).floatValue()
                        : ((Double)cfg().shadowHandSpiritualDamage.get()).floatValue()
                  );
               }
            } else {
               iterator.remove();
            }
         }
      }
   }

   private static void tickHandSwarms(ServerLevel level) {
      Iterator<Entry<UUID, WitchOfEnvySatellaHandler.ShadowHands>> iterator = HAND_SWARMS.entrySet().iterator();

      while (iterator.hasNext()) {
         WitchOfEnvySatellaHandler.ShadowHands state = iterator.next().getValue();
         if (state.dimension == level.dimension()) {
            if (level.getGameTime() > state.endTick) {
               iterator.remove();
            } else {
               LivingEntity owner = findLiving(level, state.owner);
               LivingEntity target = findLiving(level, state.target);
               if (owner != null && target != null && owner.isAlive() && target.isAlive()) {
                  Optional<ManasSkillInstance> optional = SkillAPI.getSkillsFrom(owner).getSkill((ManasSkill)AllSkills.WITCH_OF_ENVY_SATELLA.get());
                  if (!optional.isEmpty() && optional.get().getSkill() instanceof WitchOfEnvySatellaSkill skill) {
                     if (level.getGameTime() % 20L == 0L) {
                        skill.forceMixedDamage(
                           optional.get(),
                           owner,
                           target,
                           skill.envyDamageSource(owner, optional.get(), 2),
                           state.mastered
                              ? ((Double)cfg().shadowHandsPhysicalDamageMastered.get()).floatValue()
                              : ((Double)cfg().shadowHandsPhysicalDamage.get()).floatValue(),
                           state.mastered
                              ? ((Double)cfg().shadowHandsSpiritualDamageMastered.get()).floatValue()
                              : ((Double)cfg().shadowHandsSpiritualDamage.get()).floatValue()
                        );
                        target.addEffect(
                           new MobEffectInstance(
                              TensuraMobEffects.getReference(TensuraMobEffects.MOVEMENT_INTERFERENCE),
                              (Integer)cfg().shadowHandsSlowTicks.get(),
                              state.mastered ? (Integer)cfg().shadowHandsSlowAmplifierMastered.get() : (Integer)cfg().shadowHandsSlowAmplifier.get(),
                              false,
                              false,
                              true
                           )
                        );
                     }

                     if (level.getGameTime() % 10L == 0L) {
                        WitchOfEnvySatellaVfxPayload.sendShadowHands(level, owner, target, state.count, 24, state.mastered);
                     }
                  } else {
                     iterator.remove();
                  }
               } else {
                  iterator.remove();
               }
            }
         }
      }
   }

   private static boolean isPhysicalDamage(DamageSource source) {
      if (source == null) {
         return false;
      }

      String id = source.getMsgId();
      if (id == null) {
         return true;
      }

      String lower = id.toLowerCase();
      return !lower.contains("magic")
         && !lower.contains("spell")
         && !lower.contains("spiritual")
         && !lower.contains("elemental")
         && !lower.contains("holy")
         && !lower.contains("dark")
         && !lower.contains("fire")
         && !lower.contains("ice")
         && !lower.contains("lightning")
         && !lower.contains("water")
         && !lower.contains("wind")
         && !lower.contains("space");
   }

   public static void clearOwner(LivingEntity entity) {
      UUID id = entity.getUUID();
      HELD_HANDS.remove(id);
      HAND_SWARMS.remove(id);
      WitchOfEnvyDevouringShadowsHandler.stop(id);
      AUTHORITIES.entrySet().removeIf(entry -> entry.getValue().owner.equals(id) || entry.getValue().ally.equals(id));
   }

   private static LivingEntity findLiving(Level level, UUID uuid) {
      if (uuid == null) {
         return null;
      }

      if (level instanceof ServerLevel serverLevel) {
         if (serverLevel.getEntity(uuid) instanceof LivingEntity living) {
            return living;
         }

         ServerPlayer player = serverLevel.getServer().getPlayerList().getPlayer(uuid);
         if (player != null) {
            return player;
         }

         for (ServerLevel other : serverLevel.getServer().getAllLevels()) {
            if (other.getEntity(uuid) instanceof LivingEntity living) {
               return living;
            }
         }
      }

      return null;
   }

   private static final class Authority {
      private final UUID owner;
      private final UUID ally;
      private boolean mastered;
      private WitchOfEnvySatellaHandler.Snapshot checkpoint;

      private Authority(UUID owner, UUID ally, boolean mastered) {
         this.owner = owner;
         this.ally = ally;
         this.mastered = mastered;
      }
   }

   private static final class HeldShadowHand {
      private final UUID owner;
      private final UUID target;
      private final ResourceKey<Level> dimension;
      private final long startTick;
      private final boolean mastered;

      private HeldShadowHand(UUID owner, UUID target, ResourceKey<Level> dimension, long startTick, boolean mastered) {
         this.owner = owner;
         this.target = target;
         this.dimension = dimension;
         this.startTick = startTick;
         this.mastered = mastered;
      }
   }

   private static final class ShadowHands {
      private final UUID owner;
      private final UUID target;
      private final ResourceKey<Level> dimension;
      private final long startTick;
      private final long endTick;
      private final boolean mastered;
      private final int count;

      private ShadowHands(UUID owner, UUID target, ResourceKey<Level> dimension, long startTick, long endTick, boolean mastered, int count) {
         this.owner = owner;
         this.target = target;
         this.dimension = dimension;
         this.startTick = startTick;
         this.endTick = endTick;
         this.mastered = mastered;
         this.count = count;
      }
   }

   private record Snapshot(
      ResourceKey<Level> dimension,
      double x,
      double y,
      double z,
      float yRot,
      float xRot,
      float health,
      double magicule,
      double aura,
      double spiritual,
      double ep
   ) {
      private static WitchOfEnvySatellaHandler.Snapshot of(LivingEntity entity) {
         IExistence existence = TensuraStorages.getExistenceFrom(entity);
         return new WitchOfEnvySatellaHandler.Snapshot(
            entity.level().dimension(),
            entity.getX(),
            entity.getY(),
            entity.getZ(),
            entity.getYRot(),
            entity.getXRot(),
            Math.max(1.0F, entity.getHealth()),
            existence.getMagicule(),
            existence.getAura(),
            existence.getSpiritualHealth(),
            EnergyHelper.getMaxEP(entity)
         );
      }

      private CompoundTag save() {
         CompoundTag tag = new CompoundTag();
         tag.putString("Dimension", this.dimension.location().toString());
         tag.putDouble("X", this.x);
         tag.putDouble("Y", this.y);
         tag.putDouble("Z", this.z);
         tag.putFloat("YRot", this.yRot);
         tag.putFloat("XRot", this.xRot);
         tag.putFloat("Health", this.health);
         tag.putDouble("Magicule", this.magicule);
         tag.putDouble("Aura", this.aura);
         tag.putDouble("Spiritual", this.spiritual);
         tag.putDouble("EP", this.ep);
         return tag;
      }

      private void restore(LivingEntity entity) {
         if (entity.level() instanceof ServerLevel level) {
            ServerLevel target = level.getServer().getLevel(this.dimension);
            if (target != null && entity instanceof ServerPlayer player) {
               player.teleportTo(target, this.x, this.y, this.z, this.yRot, this.xRot);
            } else {
               entity.teleportTo(this.x, this.y, this.z);
            }
         } else {
            entity.teleportTo(this.x, this.y, this.z);
         }

         entity.setYRot(this.yRot);
         entity.setXRot(this.xRot);
         entity.setHealth(Math.min(entity.getMaxHealth(), Math.max(1.0F, this.health)));
         IExistence existence = TensuraStorages.getExistenceFrom(entity);
         existence.setMagicule(Math.min(EnergyHelper.getMaxMagicule(entity), this.magicule));
         existence.setAura(Math.min(EnergyHelper.getMaxAura(entity), this.aura));
         existence.setSpiritualHealth(Math.min(entity.getAttributeValue(TensuraAttributes.MAX_SPIRITUAL_HEALTH), this.spiritual));
         existence.markDirty();
         entity.setDeltaMovement(Vec3.ZERO);
         entity.hurtMarked = true;
      }
   }
}
