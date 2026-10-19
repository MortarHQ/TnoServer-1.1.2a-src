package com.github.wal_bos.moreskills.handler.Istaroth;

import com.github.wal_bos.moreskills.configs.MoreSkillsConfigs;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.tensura.damage.TensuraDamageTypes;
import io.github.manasmods.tensura.entity.variant.MagicCircleVariant;
import io.github.manasmods.tensura.registry.attribute.TensuraAttributes;
import io.github.manasmods.tensura.storage.TensuraStorages;
import io.github.manasmods.tensura.storage.ep.IExistence;
import io.github.manasmods.tensura.util.EnergyHelper;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.Map.Entry;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ClipContext.Block;
import net.minecraft.world.level.ClipContext.Fluid;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public final class IstarothCommon {
   private static final MoreSkillsConfigs.IstarothConfig CONFIG = MoreSkillsConfigs.GRAND.istaroth;
   private static final Map<UUID, ArrayDeque<IstarothCommon.Snapshot>> SNAPSHOTS = new HashMap<>();
   private static final Map<UUID, IstarothCommon.Snapshot> MANUAL_ANCHORS = new HashMap<>();
   private static final Map<UUID, Integer> MANUAL_ANCHOR_TICKS = new HashMap<>();
   private static final Map<UUID, Float> TEMPORAL_DEBT = new HashMap<>();
   private static final Map<UUID, UUID> DEBT_OWNER = new HashMap<>();

   private IstarothCommon() {
   }

   public static void init() {
   }

   public static void tick(LivingEntity entity) {
      recordSnapshot(entity);
      tickManualAnchors();
   }

   public static void manualAnchor(ManasSkillInstance instance, LivingEntity entity) {
      IstarothCommon.Snapshot snapshot = IstarothCommon.Snapshot.of(entity);
      MANUAL_ANCHORS.put(entity.getUUID(), snapshot);
      MANUAL_ANCHOR_TICKS.put(entity.getUUID(), instance.isMastered(entity) ? 600 : 360);
      entity.sendSystemMessage(Component.literal("Manual Anchor stored.").withStyle(ChatFormatting.AQUA));
      IstarothFx.clockCircle(entity, 3.5F, 60, MagicCircleVariant.SPACE);
      IstarothFx.afterimage(entity, entity.position());
      IstarothFx.grayPulse(entity, 3.0F);
      IstarothFx.sound(entity, SoundEvents.RESPAWN_ANCHOR_CHARGE, 1.0F, 0.55F);
   }

   public static void rewindSelf(ManasSkillInstance instance, LivingEntity entity) {
      IstarothCommon.Snapshot snapshot = MANUAL_ANCHORS.remove(entity.getUUID());
      MANUAL_ANCHOR_TICKS.remove(entity.getUUID());
      if (snapshot == null) {
         snapshot = getOldSnapshot(entity, instance.isMastered(entity) ? 100 : 70);
      }

      if (snapshot == null) {
         entity.sendSystemMessage(Component.literal("No recorded moment exists.").withStyle(ChatFormatting.RED));
      } else {
         Vec3 old = entity.position();
         snapshot.restore(entity, true);
         removeRecentHarmfulEffects(entity, instance.isMastered(entity) ? 4 : 2);
         entity.invulnerableTime = instance.isMastered(entity) ? 60 : 40;
         IstarothFx.timeSkipTrail(entity.level(), old.add(0.0, entity.getBbHeight() * 0.5, 0.0), entity.position().add(0.0, entity.getBbHeight() * 0.5, 0.0));
         IstarothFx.afterimage(entity, old);
         IstarothFx.grayPulse(entity, 4.0F);
         IstarothFx.timeStopScreen(entity, 16.0, 25, 1);
         IstarothFx.sound(entity, SoundEvents.RESPAWN_ANCHOR_CHARGE, 1.1F, 0.45F);
         IstarothFx.cameraShake(entity, 14.0, 0.045F, 10);
      }
   }

   public static boolean shouldChronologyDeny(LivingEntity entity, DamageSource source, float damage) {
      if (!source.is(DamageTypeTags.BYPASSES_INVULNERABILITY) && !source.isCreativePlayer()) {
         if (damage != Float.MAX_VALUE && entity.getHealth() - damage > 0.0F) {
            return false;
         }

         CompoundTag persistent = entity.getPersistentData();
         long now = entity.level().getGameTime();
         long last = persistent.getLong("IstarothChronologyDenial");
         if (now - last < ((Integer)CONFIG.chronologyDenialCooldownTicks.get()).intValue()) {
            return false;
         }

         IstarothCommon.Snapshot snapshot = getOldSnapshot(entity, (Integer)CONFIG.chronologyDenialSnapshotTicks.get());
         if (snapshot == null) {
            return false;
         }

         persistent.putLong("IstarothChronologyDenial", now);
         Vec3 death = entity.position();
         snapshot.restore(entity, true);
         removeRecentHarmfulEffects(entity, 8);
         entity.setHealth(Math.max(1.0F, Math.min(entity.getMaxHealth(), snapshot.health)));
         entity.invulnerableTime = (Integer)CONFIG.chronologyDenialInvulnerableTicks.get();
         IstarothFx.timeSkipTrail(entity.level(), death.add(0.0, entity.getBbHeight() * 0.5, 0.0), entity.position().add(0.0, entity.getBbHeight() * 0.5, 0.0));
         IstarothFx.clockShatter(entity, 5.0F);
         if (entity.level() instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(ParticleTypes.EXPLOSION, death.x, death.y + entity.getBbHeight() * 0.5, death.z, 2, 0.0, 0.0, 0.0, 0.0);
            serverLevel.sendParticles(ParticleTypes.WHITE_ASH, death.x, death.y + entity.getBbHeight() * 0.5, death.z, 130, 2.2, 1.3, 2.2, 0.07);
         }

         entity.level().playSound(null, death.x, death.y, death.z, SoundEvents.GLASS_BREAK, SoundSource.PLAYERS, 1.8F, 0.22F);
         entity.sendSystemMessage(Component.literal("Chronology Denial rejected your death.").withStyle(ChatFormatting.GRAY));
         return true;
      } else {
         return false;
      }
   }

   public static void addTemporalDebt(LivingEntity target, float amount, UUID owner) {
      if (!target.level().isClientSide() && !(amount <= 0.0F)) {
         UUID id = target.getUUID();
         float cap = Math.max(160.0F, target.getMaxHealth() * 2.25F);
         TEMPORAL_DEBT.put(id, Math.min(cap, TEMPORAL_DEBT.getOrDefault(id, 0.0F) + amount));
         DEBT_OWNER.put(id, owner);
         if (target.tickCount % 6 == 0) {
            IstarothFx.clockDust(target, 1.0F, 8);
         }
      }
   }

   public static float consumeDebt(LivingEntity target, UUID owner) {
      UUID id = target.getUUID();
      UUID storedOwner = DEBT_OWNER.get(id);
      if (storedOwner != null && !storedOwner.equals(owner)) {
         return 0.0F;
      }

      float debt = TEMPORAL_DEBT.getOrDefault(id, 0.0F);
      TEMPORAL_DEBT.remove(id);
      DEBT_OWNER.remove(id);
      return debt;
   }

   public static float getDebt(LivingEntity target) {
      return TEMPORAL_DEBT.getOrDefault(target.getUUID(), 0.0F);
   }

   public static boolean hasDebt(LivingEntity target) {
      return TEMPORAL_DEBT.getOrDefault(target.getUUID(), 0.0F) > 0.0F;
   }

   public static IstarothCommon.Snapshot getOldSnapshot(LivingEntity entity, int preferredTicks) {
      ArrayDeque<IstarothCommon.Snapshot> list = SNAPSHOTS.get(entity.getUUID());
      if (list != null && !list.isEmpty()) {
         int targetIndex = Math.max(0, list.size() - preferredTicks / 2);
         int i = 0;
         IstarothCommon.Snapshot chosen = null;

         for (IstarothCommon.Snapshot snapshot : list) {
            if (i++ >= targetIndex) {
               chosen = snapshot;
               break;
            }
         }

         return chosen == null ? list.peekFirst() : chosen;
      } else {
         return null;
      }
   }

   public static DamageSource damageSource(LivingEntity entity) {
      return TensuraDamageTypes.getEntityDamageSource(entity.level(), TensuraDamageTypes.MAGIC_GENERIC, entity);
   }

   public static List<LivingEntity> nearbyLiving(Entity center, double radius) {
      return center.level()
         .getEntitiesOfClass(LivingEntity.class, center.getBoundingBox().inflate(radius), e -> e != center && e.isAlive() && !e.isSpectator());
   }

   public static boolean validEnemy(LivingEntity owner, LivingEntity target) {
      if (target == owner) {
         return false;
      } else {
         return target.isAlive() && !target.isSpectator() ? !target.isAlliedTo(owner) : false;
      }
   }

   public static double resistanceFactor(LivingEntity target, LivingEntity owner) {
      double targetEP = EnergyHelper.getMaxEP(target);
      double ownerEP = Math.max(1.0, EnergyHelper.getMaxEP(owner));
      return Mth.clamp(targetEP / ownerEP, 0.0, 1.0);
   }

   public static Entity entity(Level level, UUID uuid) {
      return level instanceof ServerLevel serverLevel ? serverLevel.getEntity(uuid) : null;
   }

   public static Vec3 findSafeTeleport(Level level, LivingEntity entity, Vec3 start, Vec3 target) {
      ClipContext context = new ClipContext(entity.getEyePosition(), target.add(0.0, entity.getEyeHeight(), 0.0), Block.COLLIDER, Fluid.NONE, entity);
      Vec3 hit = level.clip(context).getLocation().subtract(entity.getLookAngle().normalize().scale(1.2));
      Vec3 pos = new Vec3(hit.x, hit.y - entity.getEyeHeight(), hit.z);

      for (int i = 0; i < 6; i++) {
         AABB box = entity.getBoundingBox().move(pos.subtract(entity.position())).inflate(-0.05);
         if (level.noCollision(entity, box)) {
            return pos;
         }

         pos = pos.subtract(entity.getLookAngle().normalize().scale(1.0));
      }

      return start;
   }

   public static void decayBeneficialEffects(LivingEntity entity, int amount) {
      for (MobEffectInstance effect : entity.getActiveEffects().stream().toList()) {
         if (((MobEffect)effect.getEffect().value()).getCategory() == MobEffectCategory.BENEFICIAL) {
            int duration = Math.max(1, effect.getDuration() - amount);
            entity.removeEffect(effect.getEffect());
            entity.addEffect(
               new MobEffectInstance(effect.getEffect(), duration, effect.getAmplifier(), effect.isAmbient(), effect.isVisible(), effect.showIcon())
            );
         }
      }
   }

   public static void extendBeneficialEffects(LivingEntity entity, int amount) {
      for (MobEffectInstance effect : entity.getActiveEffects().stream().toList()) {
         if (((MobEffect)effect.getEffect().value()).getCategory() == MobEffectCategory.BENEFICIAL) {
            entity.removeEffect(effect.getEffect());
            entity.addEffect(
               new MobEffectInstance(
                  effect.getEffect(), effect.getDuration() + amount, effect.getAmplifier(), effect.isAmbient(), effect.isVisible(), effect.showIcon()
               )
            );
         }
      }
   }

   public static void extendHarmfulEffects(LivingEntity entity, int amount) {
      for (MobEffectInstance effect : entity.getActiveEffects().stream().toList()) {
         if (((MobEffect)effect.getEffect().value()).getCategory() == MobEffectCategory.HARMFUL) {
            entity.removeEffect(effect.getEffect());
            entity.addEffect(
               new MobEffectInstance(
                  effect.getEffect(), effect.getDuration() + amount, effect.getAmplifier(), effect.isAmbient(), effect.isVisible(), effect.showIcon()
               )
            );
         }
      }
   }

   public static void removeRecentHarmfulEffects(LivingEntity entity, int max) {
      int removed = 0;

      for (MobEffectInstance effect : entity.getActiveEffects().stream().toList()) {
         if (removed >= max) {
            break;
         }

         if (((MobEffect)effect.getEffect().value()).getCategory() == MobEffectCategory.HARMFUL) {
            entity.removeEffect(effect.getEffect());
            removed++;
         }
      }
   }

   public static void reduceCooldowns(ManasSkillInstance instance, int seconds) {
      for (int i = 0; i < instance.getModes(); i++) {
         int cd = instance.getCoolDown(i);
         if (cd > 0) {
            instance.setCoolDown(Math.max(0, cd - seconds), i);
         }
      }
   }

   private static void recordSnapshot(LivingEntity entity) {
      ArrayDeque<IstarothCommon.Snapshot> list = SNAPSHOTS.computeIfAbsent(entity.getUUID(), id -> new ArrayDeque<>());
      if (entity.tickCount % 2 == 0) {
         list.addLast(IstarothCommon.Snapshot.of(entity));

         while (list.size() > 160) {
            list.removeFirst();
         }
      }
   }

   private static void tickManualAnchors() {
      Iterator<Entry<UUID, Integer>> iterator = MANUAL_ANCHOR_TICKS.entrySet().iterator();

      while (iterator.hasNext()) {
         Entry<UUID, Integer> entry = iterator.next();
         int ticks = entry.getValue() - 1;
         if (ticks <= 0) {
            MANUAL_ANCHORS.remove(entry.getKey());
            iterator.remove();
         } else {
            entry.setValue(ticks);
         }
      }
   }

   public static final class Snapshot {
      public final Vec3 pos;
      public final float yRot;
      public final float xRot;
      public final Vec3 motion;
      public final float health;
      public final double magicule;
      public final double aura;
      public final double spiritualHealth;

      private Snapshot(Vec3 pos, float yRot, float xRot, Vec3 motion, float health, double magicule, double aura, double spiritualHealth) {
         this.pos = pos;
         this.yRot = yRot;
         this.xRot = xRot;
         this.motion = motion;
         this.health = health;
         this.magicule = magicule;
         this.aura = aura;
         this.spiritualHealth = spiritualHealth;
      }

      public static IstarothCommon.Snapshot of(LivingEntity entity) {
         IExistence existence = TensuraStorages.getExistenceFrom(entity);
         return new IstarothCommon.Snapshot(
            entity.position(),
            entity.getYRot(),
            entity.getXRot(),
            entity.getDeltaMovement(),
            entity.getHealth(),
            existence.getMagicule(),
            existence.getAura(),
            existence.getSpiritualHealth()
         );
      }

      public void restore(LivingEntity entity, boolean restoreVitals) {
         this.restorePositionOnly(entity);
         if (restoreVitals) {
            IExistence existence = TensuraStorages.getExistenceFrom(entity);
            entity.setHealth(Mth.clamp(this.health, 1.0F, entity.getMaxHealth()));
            existence.setMagicule(Math.min(this.magicule, EnergyHelper.getMaxMagicule(entity)));
            existence.setAura(Math.min(this.aura, EnergyHelper.getMaxAura(entity)));
            existence.setSpiritualHealth(Math.min(this.spiritualHealth, entity.getAttributeValue(TensuraAttributes.MAX_SPIRITUAL_HEALTH)));
            existence.markDirty();
         }
      }

      public void restorePositionOnly(LivingEntity entity) {
         entity.teleportTo(this.pos.x, this.pos.y, this.pos.z);
         entity.setYRot(this.yRot);
         entity.setXRot(this.xRot);
         entity.setDeltaMovement(this.motion);
         entity.hurtMarked = true;
      }
   }
}
