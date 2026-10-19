package com.github.wal_bos.moreskills.handler;

import com.github.wal_bos.moreskills.ability.skill.ultimate.WitchOfEnvySatellaSkill;
import com.github.wal_bos.moreskills.configs.MoreSkillsConfigs;
import com.github.wal_bos.moreskills.net.msg.SatellaVfxPayload;
import com.github.wal_bos.moreskills.registry.skill.AllSkills;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.tensura.ability.SkillUtils;
import io.github.manasmods.tensura.damage.TensuraDamageHelper;
import io.github.manasmods.tensura.registry.effect.TensuraMobEffects;
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

public final class WitchOfEnvyDevouringShadowsHandler {
   private static final Map<UUID, WitchOfEnvyDevouringShadowsHandler.ActiveDevouringShadows> ACTIVE = new ConcurrentHashMap<>();
   private static final double START_RADIUS = 2.5;
   private static final double END_RADIUS = 25.0;
   private static final double DAMAGE_Y_DOWN = 20.0;
   private static final double DAMAGE_Y_UP = 20.0;
   private static boolean initialized;

   private WitchOfEnvyDevouringShadowsHandler() {
   }

   public static void init() {
      if (!initialized) {
         initialized = true;
         NeoForge.EVENT_BUS.addListener(WitchOfEnvyDevouringShadowsHandler::onLevelTick);
      }
   }

   public static void start(ServerLevel level, LivingEntity caster, ManasSkillInstance instance) {
      init();
      MoreSkillsConfigs.WitchOfEnvySatellaConfig cfg = MoreSkillsConfigs.GRAND.witchOfEnvySatella;
      UUID casterId = caster.getUUID();
      long now = level.getGameTime();
      int duration = Math.max(20, (Integer)cfg.devouringShadowsDurationTicks.get());
      Vec3 center = caster.position();
      boolean mastered = instance != null && instance.isMastered(caster);
      ACTIVE.put(
         casterId,
         new WitchOfEnvyDevouringShadowsHandler.ActiveDevouringShadows(
            casterId, level.dimension().location().toString(), center, now, now + duration, now, mastered
         )
      );
      if (instance != null) {
         CompoundTag tag = instance.getOrCreateTag();
         tag.putBoolean("WitchEnvyDevouringActive", true);
         tag.putLong("WitchEnvyDevouringStart", now);
         tag.putLong("WitchEnvyDevouringEnd", now + duration);
         tag.putDouble("WitchEnvyDevouringX", center.x);
         tag.putDouble("WitchEnvyDevouringY", center.y);
         tag.putDouble("WitchEnvyDevouringZ", center.z);
         instance.markDirty();
      }

      SatellaVfxPayload.sendSpread(level, center, 25.0F, duration, mastered);
   }

   public static void stop(UUID casterId) {
      ACTIVE.remove(casterId);
   }

   private static void onLevelTick(Post event) {
      if (event.getLevel() instanceof ServerLevel level && !ACTIVE.isEmpty()) {
         long now = level.getGameTime();
         Iterator<Entry<UUID, WitchOfEnvyDevouringShadowsHandler.ActiveDevouringShadows>> iterator = ACTIVE.entrySet().iterator();

         while (iterator.hasNext()) {
            WitchOfEnvyDevouringShadowsHandler.ActiveDevouringShadows active = iterator.next().getValue();
            if (active.dimensionId.equals(level.dimension().location().toString())) {
               LivingEntity caster = findCaster(level, active.casterId);
               if (caster != null && caster.isAlive() && now < active.endTick) {
                  int interval = Math.max(1, (Integer)MoreSkillsConfigs.GRAND.witchOfEnvySatella.devouringShadowsDamageIntervalTicks.get());
                  if (now >= active.nextDamageTick) {
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

   private static ManasSkillInstance getInstance(LivingEntity caster) {
      return SkillUtils.getSkillOrNull(caster, (ManasSkill)AllSkills.WITCH_OF_ENVY_SATELLA.get());
   }

   private static void clearSkillTag(LivingEntity caster) {
      if (caster != null) {
         ManasSkillInstance instance = getInstance(caster);
         if (instance != null) {
            CompoundTag tag = instance.getOrCreateTag();
            tag.putBoolean("WitchEnvyDevouringActive", false);
            tag.remove("WitchEnvyDevouringStart");
            tag.remove("WitchEnvyDevouringEnd");
            tag.remove("WitchEnvyDevouringX");
            tag.remove("WitchEnvyDevouringY");
            tag.remove("WitchEnvyDevouringZ");
            instance.markDirty();
         }
      }
   }

   private static void damageArea(ServerLevel level, LivingEntity caster, WitchOfEnvyDevouringShadowsHandler.ActiveDevouringShadows active, long now) {
      MoreSkillsConfigs.WitchOfEnvySatellaConfig cfg = MoreSkillsConfigs.GRAND.witchOfEnvySatella;
      double radius = currentRadius(active, now);
      Vec3 center = active.center;
      AABB box = new AABB(center.x - radius, center.y - 20.0, center.z - radius, center.x + radius, center.y + 20.0, center.z + radius);
      ManasSkillInstance instance = getInstance(caster);
      DamageSource source = instance != null
         ? ((WitchOfEnvySatellaSkill)AllSkills.WITCH_OF_ENVY_SATELLA.get()).envyDamageSource(caster, instance, 4)
         : caster.damageSources().magic();
      float physical = Math.max(
         0.0F,
         active.mastered
            ? ((Double)cfg.devouringShadowsPhysicalDamageMastered.get()).floatValue()
            : ((Double)cfg.devouringShadowsPhysicalDamage.get()).floatValue()
      );
      float spiritual = Math.max(
         0.0F,
         active.mastered
            ? ((Double)cfg.devouringShadowsSpiritualDamageMastered.get()).floatValue()
            : ((Double)cfg.devouringShadowsSpiritualDamage.get()).floatValue()
      );
      int slowDuration = Math.max(20, (Integer)cfg.devouringShadowsSlowDurationTicks.get());

      for (LivingEntity target : level.getEntitiesOfClass(LivingEntity.class, box, targetx -> shouldAffect(caster, targetx))) {
         double dx = target.getX() - center.x;
         double dz = target.getZ() - center.z;
         if (!(dx * dx + dz * dz > radius * radius)) {
            forceHurt(caster, target, source, physical, spiritual);
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

   private static void forceHurt(LivingEntity caster, LivingEntity target, DamageSource source, float physical, float spiritual) {
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

   private static double currentRadius(WitchOfEnvyDevouringShadowsHandler.ActiveDevouringShadows active, long now) {
      double total = Math.max(1.0, active.endTick - active.startTick);
      double progress = Math.max(0.0, Math.min(1.0, (now - active.startTick) / total));
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
   }
}
