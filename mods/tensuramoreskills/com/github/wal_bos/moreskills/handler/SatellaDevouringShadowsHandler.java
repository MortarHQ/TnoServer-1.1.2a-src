package com.github.wal_bos.moreskills.handler;

import com.github.wal_bos.moreskills.ability.skill.unique.SatellaSkill;
import com.github.wal_bos.moreskills.configs.MoreSkillsConfigs;
import com.github.wal_bos.moreskills.net.msg.SatellaVfxPayload;
import com.github.wal_bos.moreskills.registry.skill.AllSkills;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.tensura.ability.SkillUtils;
import io.github.manasmods.tensura.damage.TensuraDamageHelper;
import io.github.manasmods.tensura.registry.effect.TensuraMobEffects;
import io.github.manasmods.tensura.util.EnergyHelper;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.tick.LevelTickEvent.Post;

public final class SatellaDevouringShadowsHandler {
   private static final Map<UUID, SatellaDevouringShadowsHandler.ActiveDevouringShadows> ACTIVE = new ConcurrentHashMap<>();
   private static boolean initialized;
   private static final double START_RADIUS = 2.5;
   private static final double END_RADIUS = 25.0;
   private static final double DAMAGE_Y_DOWN = 20.0;
   private static final double DAMAGE_Y_UP = 20.0;

   private SatellaDevouringShadowsHandler() {
   }

   public static void init() {
      if (!initialized) {
         initialized = true;
         NeoForge.EVENT_BUS.addListener(SatellaDevouringShadowsHandler::onLevelTick);
      }
   }

   public static void start(ServerLevel level, LivingEntity caster, ManasSkillInstance instance) {
      init();
      MoreSkillsConfigs.SatellaConfig cfg = MoreSkillsConfigs.GRAND.satella;
      UUID casterId = caster.getUUID();
      long now = level.getGameTime();
      int duration = Math.max(20, (Integer)cfg.devouringShadowsDurationTicks.get());
      Vec3 center = caster.position();
      boolean mastered = instance != null && instance.isMastered(caster);
      ACTIVE.put(
         casterId,
         new SatellaDevouringShadowsHandler.ActiveDevouringShadows(
            casterId, level.dimension().location().toString(), center, now, now + duration, now, mastered
         )
      );
      if (instance != null) {
         CompoundTag tag = instance.getOrCreateTag();
         tag.putBoolean("SatellaDevouringActive", true);
         tag.putLong("SatellaDevouringStart", now);
         tag.putLong("SatellaDevouringEnd", now + duration);
         tag.putDouble("SatellaDevouringX", center.x);
         tag.putDouble("SatellaDevouringY", center.y);
         tag.putDouble("SatellaDevouringZ", center.z);
         instance.markDirty();
      }

      SatellaVfxPayload.sendSpread(level, center, 25.0F, duration, mastered);
   }

   public static void stop(UUID casterId) {
      ACTIVE.remove(casterId);
   }

   private static void onLevelTick(Post event) {
      if (event.getLevel() instanceof ServerLevel level) {
         long now = level.getGameTime();
         if (now % 20L == 0L) {
            tickPlayerFearAuras(level);
         }

         if (!ACTIVE.isEmpty()) {
            Iterator<Entry<UUID, SatellaDevouringShadowsHandler.ActiveDevouringShadows>> iterator = ACTIVE.entrySet().iterator();

            while (iterator.hasNext()) {
               SatellaDevouringShadowsHandler.ActiveDevouringShadows active = iterator.next().getValue();
               if (active.dimensionId().equals(level.dimension().location().toString())) {
                  LivingEntity caster = findCaster(level, active.casterId());
                  if (caster != null && caster.isAlive() && now < active.endTick()) {
                     int interval = Math.max(1, (Integer)MoreSkillsConfigs.GRAND.satella.devouringShadowsDamageIntervalTicks.get());
                     if (now >= active.nextDamageTick()) {
                        active.nextDamageTick = now + interval;
                        damageArea(level, caster, active, now);
                     }
                  } else {
                     iterator.remove();
                     clearSkillTag(caster);
                  }
               }
            }
         }
      }
   }

   private static void tickPlayerFearAuras(ServerLevel level) {
      for (ServerPlayer player : level.players()) {
         ManasSkillInstance instance = getSatellaInstance(player);
         if (instance != null && instance.isToggled()) {
            applyFearAura(player, instance);
         }
      }
   }

   public static void applyFearAura(LivingEntity owner, ManasSkillInstance instance) {
      if (owner != null && instance != null && !owner.level().isClientSide()) {
         if (owner.level() instanceof ServerLevel level) {
            double ratio = chance01((Double)MoreSkillsConfigs.GRAND.satella.fearMagiculeRatio.get());
            if (ratio <= 0.0) {
               ratio = 0.05;
            }

            double threshold = EnergyHelper.getMaxMagicule(owner) * ratio;
            if (!(threshold <= 0.0)) {
               int duration = Math.max(40, (Integer)MoreSkillsConfigs.GRAND.satella.fearDurationTicks.get());
               AABB box = owner.getBoundingBox().inflate(5.0, 5.0, 5.0);

               for (LivingEntity target : level.getEntitiesOfClass(LivingEntity.class, box, targetx -> shouldFearAffect(owner, targetx))) {
                  if (!(EnergyHelper.getMaxMagicule(target) >= threshold)) {
                     target.forceAddEffect(new MobEffectInstance(TensuraMobEffects.getReference(TensuraMobEffects.FEAR), duration, 0, false, true, true), owner);
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

   private static LivingEntity findCaster(ServerLevel level, UUID casterId) {
      if (level.getEntity(casterId) instanceof LivingEntity living) {
         return living;
      } else {
         ServerPlayer player = level.getServer().getPlayerList().getPlayer(casterId);
         if (player != null) {
            return player;
         }

         for (ServerPlayer p : level.players()) {
            if (p.getUUID().equals(casterId)) {
               return p;
            }
         }

         return null;
      }
   }

   private static ManasSkillInstance getSatellaInstance(LivingEntity caster) {
      return SkillUtils.getSkillOrNull(caster, (ManasSkill)AllSkills.SATELLA.get());
   }

   private static void clearSkillTag(LivingEntity caster) {
      if (caster != null) {
         ManasSkillInstance instance = getSatellaInstance(caster);
         if (instance != null) {
            CompoundTag tag = instance.getOrCreateTag();
            tag.putBoolean("SatellaDevouringActive", false);
            tag.remove("SatellaDevouringStart");
            tag.remove("SatellaDevouringEnd");
            tag.remove("SatellaDevouringX");
            tag.remove("SatellaDevouringY");
            tag.remove("SatellaDevouringZ");
            instance.markDirty();
         }
      }
   }

   private static void damageArea(ServerLevel level, LivingEntity caster, SatellaDevouringShadowsHandler.ActiveDevouringShadows active, long now) {
      MoreSkillsConfigs.SatellaConfig cfg = MoreSkillsConfigs.GRAND.satella;
      double radius = currentRadius(active, now);
      Vec3 center = active.center();
      AABB box = new AABB(center.x - radius, center.y - 20.0, center.z - radius, center.x + radius, center.y + 20.0, center.z + radius);
      ManasSkillInstance instance = getSatellaInstance(caster);
      DamageSource source = instance != null ? ((SatellaSkill)AllSkills.SATELLA.get()).damageSource(caster, instance, 1) : caster.damageSources().magic();
      float physical = Math.max(
         0.0F,
         active.mastered()
            ? ((Double)cfg.devouringShadowsPhysicalDamageMastered.get()).floatValue()
            : ((Double)cfg.devouringShadowsPhysicalDamage.get()).floatValue()
      );
      float spiritual = Math.max(
         0.0F,
         active.mastered()
            ? ((Double)cfg.devouringShadowsSpiritualDamageMastered.get()).floatValue()
            : ((Double)cfg.devouringShadowsSpiritualDamage.get()).floatValue()
      );
      int slowDuration = Math.max(20, (Integer)cfg.devouringShadowsSlowDurationTicks.get());

      for (LivingEntity target : level.getEntitiesOfClass(LivingEntity.class, box, targetx -> shouldAffect(caster, targetx))) {
         double dx = target.getX() - center.x;
         double dz = target.getZ() - center.z;
         if (!(dx * dx + dz * dz > radius * radius)) {
            forceSatellaHurt(caster, target, source, physical, spiritual);
            target.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, slowDuration, 0, false, true, true), caster);
            target.addEffect(
               new MobEffectInstance(TensuraMobEffects.getReference(TensuraMobEffects.MOVEMENT_INTERFERENCE), slowDuration, 0, false, true, true), caster
            );
         }
      }
   }

   private static boolean shouldAffect(LivingEntity caster, LivingEntity target) {
      return target != null && target.isAlive() && target != caster && !target.isAlliedTo(caster)
         ? !(target instanceof Player player && (player.isCreative() || player.isSpectator()))
         : false;
   }

   private static void forceSatellaHurt(LivingEntity caster, LivingEntity target, DamageSource source, float physical, float spiritual) {
      if (target != null && target.isAlive()) {
         float beforeHealth = target.getHealth();
         float beforeAbsorb = target.getAbsorptionAmount();
         if (physical > 0.0F) {
            target.invulnerableTime = 0;
            target.hurt(source, physical);
            target.invulnerableTime = 0;
         }

         if (spiritual > 0.0F) {
            target.invulnerableTime = 0;
            TensuraDamageHelper.directSpiritualHurt(target, caster, source, spiritual);
            target.invulnerableTime = 0;
         }

         float total = physical + spiritual;
         if (total > 0.0F
            && target.isAlive()
            && target.getHealth() >= beforeHealth
            && target.getAbsorptionAmount() >= beforeAbsorb
            && !target.hasInfiniteMaterials()) {
            float remaining = total;
            float absorption = target.getAbsorptionAmount();
            if (absorption > 0.0F) {
               float used = Math.min(absorption, remaining);
               target.setAbsorptionAmount(absorption - used);
               remaining -= used;
            }

            if (remaining > 0.0F) {
               target.setHealth(Math.max(0.0F, beforeHealth - remaining));
            }

            if (target.getHealth() <= 0.0F) {
               target.getCombatTracker().recordDamage(source, Math.max(1.0F, beforeHealth));
               target.die(source);
            }
         }

         TensuraDamageHelper.markHurt(target, caster);
         target.invulnerableTime = 0;
         target.hasImpulse = true;
         target.hurtMarked = true;
      }
   }

   private static double currentRadius(SatellaDevouringShadowsHandler.ActiveDevouringShadows active, long now) {
      double total = Math.max(1.0, active.endTick() - active.startTick());
      double progress = Math.max(0.0, Math.min(1.0, (now - active.startTick()) / total));
      return 2.5 + 22.5 * progress;
   }

   private static final class ActiveDevouringShadows {
      private final UUID casterId;
      private final String dimensionId;
      private final Vec3 center;
      private final long startTick;
      private final long endTick;
      private long nextDamageTick;
      private final boolean mastered;

      private ActiveDevouringShadows(UUID casterId, String dimensionId, Vec3 center, long startTick, long endTick, long nextDamageTick, boolean mastered) {
         this.casterId = casterId;
         this.dimensionId = dimensionId;
         this.center = center;
         this.startTick = startTick;
         this.endTick = endTick;
         this.nextDamageTick = nextDamageTick;
         this.mastered = mastered;
      }

      private UUID casterId() {
         return this.casterId;
      }

      private String dimensionId() {
         return this.dimensionId;
      }

      private Vec3 center() {
         return this.center;
      }

      private long startTick() {
         return this.startTick;
      }

      private long endTick() {
         return this.endTick;
      }

      private long nextDamageTick() {
         return this.nextDamageTick;
      }

      private boolean mastered() {
         return this.mastered;
      }
   }
}
