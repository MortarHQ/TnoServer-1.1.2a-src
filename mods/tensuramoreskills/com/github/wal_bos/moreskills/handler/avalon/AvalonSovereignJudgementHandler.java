package com.github.wal_bos.moreskills.handler.avalon;

import com.github.wal_bos.moreskills.configs.MoreSkillsConfigs;
import com.github.wal_bos.moreskills.net.msg.AvalonSovereignJudgementVfxPayload;
import com.github.wal_bos.moreskills.support.RoyalSkillSupport;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.tensura.damage.TensuraDamageTypes;
import io.github.manasmods.tensura.util.EnergyHelper;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.ClipContext.Block;
import net.minecraft.world.level.ClipContext.Fluid;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.HitResult.Type;

final class AvalonSovereignJudgementHandler implements AvalonSovereignModeHandler {
   private static final Map<UUID, AvalonSovereignJudgementHandler.BeamState> ACTIVE = new HashMap<>();
   private static final int AIM_INTERVAL = 2;
   private static final int DAMAGE_INTERVAL = 8;
   private static final int MASTERY_INTERVAL = 60;
   private static final int MAX_TRAIL_POINTS = 56;
   private static final int MAX_INSERTED_POINTS_PER_SAMPLE = 4;
   private static final int COOLDOWN_TICKS = 2400;
   private static final double TRAIL_POINT_SPACING = 2.0;
   private static final double BEAM_RADIUS = 8.5;
   private static final double ERUPTION_RADIUS = 12.0;
   private static final double ERUPTION_HEIGHT = 42.0;
   private static final double TERMINAL_RADIUS = 24.0;
   private static final float BEAM_DAMAGE_FACTOR = 0.9F;
   private static final float ERUPTION_DAMAGE_FACTOR = 6.0F;

   @Override
   public String name() {
      return "Judgement";
   }

   @Override
   public double magiculeCost(LivingEntity entity, ManasSkillInstance instance) {
      return (Double)MoreSkillsConfigs.GRAND.avalon.sovereignJudgementCost.get();
   }

   @Override
   public void activate(ManasSkillInstance instance, LivingEntity entity, int topMode) {
      if (entity.level() instanceof ServerLevel level) {
         if (instance.getCoolDown(topMode) <= 0) {
            if (!EnergyHelper.isOutOfEnergy(entity, instance, topMode)) {
               AvalonSovereignJudgementHandler.BeamState previous = ACTIVE.remove(entity.getUUID());
               if (previous != null) {
                  stopVisual(level, entity, previous);
               }

               AvalonSovereignJudgementHandler.BeamState state = new AvalonSovereignJudgementHandler.BeamState(topMode, entity.tickCount);
               ACTIVE.put(entity.getUUID(), state);
               AvalonSovereignJudgementHandler.BeamRay ray = trace(level, entity, (Double)MoreSkillsConfigs.GRAND.avalon.sovereignJudgementRange.get());
               state.lastRay = ray;
               AvalonSovereignJudgementVfxPayload.send(level, entity, null, ray.start, ray.end, 12.0F, 14, 0);
               sampleTrail(level, entity, state, ray);
               level.playSound(null, entity.blockPosition(), SoundEvents.BEACON_ACTIVATE, SoundSource.PLAYERS, 6.2F, 1.36F);
               level.playSound(null, entity.blockPosition(), SoundEvents.WARDEN_SONIC_BOOM, SoundSource.PLAYERS, 5.8F, 1.24F);
               level.playSound(null, entity.blockPosition(), (SoundEvent)SoundEvents.TRIDENT_THUNDER.value(), SoundSource.PLAYERS, 4.2F, 1.68F);
               entity.swing(InteractionHand.MAIN_HAND, true);
            }
         }
      }
   }

   @Override
   public boolean onHeld(ManasSkillInstance instance, LivingEntity entity, int heldTicks, int topMode) {
      if (entity.level().isClientSide()) {
         return true;
      }

      if (entity.level() instanceof ServerLevel level) {
         AvalonSovereignJudgementHandler.BeamState state = ACTIVE.get(entity.getUUID());
         if (state != null && state.mode == topMode) {
            state.lastHeldTick = entity.tickCount;
            if (heldTicks % 2 == 0) {
               AvalonSovereignJudgementHandler.BeamRay ray = trace(level, entity, (Double)MoreSkillsConfigs.GRAND.avalon.sovereignJudgementRange.get());
               state.lastRay = ray;
               AvalonSovereignJudgementVfxPayload.send(level, entity, null, ray.start, ray.end, 12.0F, 14, 0);
               sampleTrail(level, entity, state, ray);
            }

            if (heldTicks > 0 && heldTicks % 8 == 0 && state.lastRay != null) {
               damageBeam(level, entity, instance, state, state.lastRay);
            }

            if (heldTicks > 0 && heldTicks % 60 == 0) {
               instance.addMasteryPoint(entity);
            }

            return true;
         } else {
            return false;
         }
      } else {
         return false;
      }
   }

   @Override
   public void onRelease(ManasSkillInstance instance, LivingEntity entity, int heldTicks, int keyNumber, int topMode) {
      if (entity.level() instanceof ServerLevel level) {
         AvalonSovereignJudgementHandler.BeamState state = ACTIVE.remove(entity.getUUID());
         if (state != null) {
            finish(level, entity, instance, state);
         }
      }
   }

   @Override
   public void tick(ManasSkillInstance instance, LivingEntity entity) {
      if (entity.level() instanceof ServerLevel level) {
         AvalonSovereignJudgementHandler.BeamState state = ACTIVE.get(entity.getUUID());
         if (state != null) {
            if (entity.isAlive() && !entity.isRemoved()) {
               if (entity.tickCount - state.lastHeldTick > 3) {
                  if (ACTIVE.remove(entity.getUUID(), state)) {
                     finish(level, entity, instance, state);
                  }
               }
            } else {
               ACTIVE.remove(entity.getUUID());
            }
         }
      }
   }

   @Override
   public boolean shouldTriggerReleaseOnHeldInterrupt(ManasSkillInstance instance, LivingEntity entity, int keyNumber, int topMode) {
      AvalonSovereignJudgementHandler.BeamState state = ACTIVE.get(entity.getUUID());
      return state != null && state.mode == topMode;
   }

   @Override
   public void cleanup(LivingEntity entity) {
      ACTIVE.remove(entity.getUUID());
   }

   private static void finish(ServerLevel level, LivingEntity entity, ManasSkillInstance instance, AvalonSovereignJudgementHandler.BeamState state) {
      AvalonSovereignJudgementHandler.BeamRay ray = state.lastRay;
      Vec3 stop = ray == null ? RoyalSkillSupport.eye(entity) : ray.end;
      stopVisual(level, entity, state);
      if (state.trail.size() >= 2) {
         eruptTrail(level, entity, instance, state);
      }

      AvalonSovereignJudgementVfxPayload.send(level, entity, null, stop, stop, 18.0F, 90, 4);
      level.playSound(null, stop.x, stop.y, stop.z, (SoundEvent)SoundEvents.GENERIC_EXPLODE.value(), SoundSource.PLAYERS, 12.0F, 0.52F);
      level.playSound(null, stop.x, stop.y, stop.z, (SoundEvent)SoundEvents.TRIDENT_THUNDER.value(), SoundSource.PLAYERS, 11.0F, 0.66F);
      level.playSound(null, stop.x, stop.y, stop.z, SoundEvents.END_PORTAL_SPAWN, SoundSource.PLAYERS, 8.0F, 1.18F);
      instance.setCoolDown(2400, state.mode);
      instance.addMasteryPoint(entity);
      instance.markDirty();
   }

   private static void stopVisual(ServerLevel level, LivingEntity entity, AvalonSovereignJudgementHandler.BeamState state) {
      AvalonSovereignJudgementHandler.BeamRay ray = state.lastRay;
      Vec3 stop = ray == null ? RoyalSkillSupport.eye(entity) : ray.end;
      AvalonSovereignJudgementVfxPayload.send(level, entity, null, stop, stop, 1.0F, 4, 3);
   }

   private static AvalonSovereignJudgementHandler.BeamRay trace(ServerLevel level, LivingEntity entity, double range) {
      Vec3 look = entity.getLookAngle();
      if (look.lengthSqr() < 1.0E-8) {
         look = new Vec3(0.0, 0.0, 1.0);
      }

      look = look.normalize();
      Vec3 start = RoyalSkillSupport.eye(entity).add(look.scale(1.35));
      Vec3 rawEnd = start.add(look.scale(range));
      BlockHitResult hit = level.clip(new ClipContext(start, rawEnd, Block.COLLIDER, Fluid.NONE, entity));
      Vec3 end = hit.getType() == Type.BLOCK ? hit.getLocation() : rawEnd;
      Vec3 surface = hit.getType() == Type.BLOCK ? hit.getLocation().add(0.0, 0.055, 0.0) : null;
      return new AvalonSovereignJudgementHandler.BeamRay(start, end, surface);
   }

   private static void sampleTrail(
      ServerLevel level, LivingEntity entity, AvalonSovereignJudgementHandler.BeamState state, AvalonSovereignJudgementHandler.BeamRay ray
   ) {
      if (ray.surface != null) {
         Vec3 point = ray.surface;
         if (state.trail.isEmpty()) {
            state.trail.add(point);
            state.lastTrailPoint = point;
            AvalonSovereignJudgementVfxPayload.send(level, entity, null, point, point, 4.8F, 180, 1);
         } else {
            Vec3 last = state.lastTrailPoint;
            double distance = last.distanceTo(point);
            if (!(distance < 2.0)) {
               int inserts = Math.min(4, Math.max(1, (int)Math.ceil(distance / 2.0)));

               for (int i = 1; i <= inserts && state.trail.size() < 56; i++) {
                  Vec3 next = last.lerp(point, (double)i / inserts);
                  Vec3 previous = state.trail.get(state.trail.size() - 1);
                  state.trail.add(next);
                  AvalonSovereignJudgementVfxPayload.send(level, entity, null, previous, next, 4.8F, 180, 1);
               }

               state.lastTrailPoint = point;
            }
         }
      }
   }

   private static void damageBeam(
      ServerLevel level,
      LivingEntity owner,
      ManasSkillInstance instance,
      AvalonSovereignJudgementHandler.BeamState state,
      AvalonSovereignJudgementHandler.BeamRay ray
   ) {
      AABB box = new AABB(ray.start, ray.end).inflate(10.0);
      float configured = ((Double)MoreSkillsConfigs.GRAND.avalon.sovereignJudgementDamage.get()).floatValue();
      float pulseDamage = configured * 0.9F;
      DamageSource holy = RoyalSkillSupport.authoritySource(owner, instance, state.mode, TensuraDamageTypes.HOLY_DAMAGE);
      DamageSource light = RoyalSkillSupport.authoritySource(owner, instance, state.mode, TensuraDamageTypes.LIGHT_ELEMENTAL);

      for (LivingEntity target : level.getEntitiesOfClass(LivingEntity.class, box, living -> RoyalSkillSupport.validEnemy(owner, living))) {
         Vec3 center = target.position().add(0.0, target.getBbHeight() * 0.5, 0.0);
         if (!(RoyalSkillSupport.distanceToSegment(center, ray.start, ray.end) > 8.5 + target.getBbWidth() * 0.5)) {
            RoyalSkillSupport.unavoidableDamage(target, holy, pulseDamage * 0.6F);
            if (target.isAlive()) {
               RoyalSkillSupport.unavoidableDamage(target, light, pulseDamage * 0.4F);
            }

            RoyalSkillSupport.markCombat(owner, target);
         }
      }
   }

   private static void eruptTrail(ServerLevel level, LivingEntity owner, ManasSkillInstance instance, AvalonSovereignJudgementHandler.BeamState state) {
      List<Vec3> trail = state.trail;
      if (trail.size() >= 2) {
         float configured = ((Double)MoreSkillsConfigs.GRAND.avalon.sovereignJudgementDamage.get()).floatValue();
         float totalDamage = configured * 6.0F;
         DamageSource holy = RoyalSkillSupport.authoritySource(owner, instance, state.mode, TensuraDamageTypes.HOLY_DAMAGE);
         DamageSource light = RoyalSkillSupport.authoritySource(owner, instance, state.mode, TensuraDamageTypes.LIGHT_ELEMENTAL);
         double minX = Double.POSITIVE_INFINITY;
         double minY = Double.POSITIVE_INFINITY;
         double minZ = Double.POSITIVE_INFINITY;
         double maxX = Double.NEGATIVE_INFINITY;
         double maxY = Double.NEGATIVE_INFINITY;
         double maxZ = Double.NEGATIVE_INFINITY;

         for (Vec3 point : trail) {
            minX = Math.min(minX, point.x);
            minY = Math.min(minY, point.y);
            minZ = Math.min(minZ, point.z);
            maxX = Math.max(maxX, point.x);
            maxY = Math.max(maxY, point.y + 42.0);
            maxZ = Math.max(maxZ, point.z);
         }

         AABB area = new AABB(minX, minY - 3.0, minZ, maxX, maxY, maxZ).inflate(14.0);
         Vec3 terminal = trail.get(trail.size() - 1);

         for (LivingEntity target : level.getEntitiesOfClass(LivingEntity.class, area, living -> RoyalSkillSupport.validEnemy(owner, living))) {
            Vec3 center = target.position().add(0.0, target.getBbHeight() * 0.5, 0.0);
            double nearest = distanceToTrailXZ(center, trail);
            if (!(nearest > 12.0 + target.getBbWidth() * 0.5)) {
               float edge = (float)Math.max(0.55, 1.0 - nearest / Math.max(12.0, 0.001));
               float terminalBonus = center.distanceToSqr(terminal) <= 576.0 ? 1.75F : 1.0F;
               float damage = totalDamage * (0.7F + edge * 0.45F) * terminalBonus;
               RoyalSkillSupport.unavoidableDamage(target, holy, damage * 0.6F);
               if (target.isAlive()) {
                  RoyalSkillSupport.unavoidableDamage(target, light, damage * 0.4F);
               }

               RoyalSkillSupport.markCombat(owner, target);
            }
         }

         int last = trail.size() - 1;

         for (int i = 0; i < last; i += 2) {
            Vec3 a = trail.get(i);
            Vec3 b = trail.get(Math.min(i + 2, last));
            if (!(a.distanceToSqr(b) < 0.04)) {
               AvalonSovereignJudgementVfxPayload.send(level, owner, null, a, b, 10.0F, 92, 2);
            }
         }
      }
   }

   private static double distanceToTrailXZ(Vec3 point, List<Vec3> trail) {
      double best = Double.MAX_VALUE;

      for (int i = 0; i < trail.size() - 1; i++) {
         Vec3 a = trail.get(i);
         Vec3 b = trail.get(i + 1);
         double dx = b.x - a.x;
         double dz = b.z - a.z;
         double len = dx * dx + dz * dz;
         double t = len <= 1.0E-8 ? 0.0 : ((point.x - a.x) * dx + (point.z - a.z) * dz) / len;
         t = Math.max(0.0, Math.min(1.0, t));
         double px = a.x + dx * t;
         double pz = a.z + dz * t;
         double ox = point.x - px;
         double oz = point.z - pz;
         best = Math.min(best, Math.sqrt(ox * ox + oz * oz));
      }

      return best;
   }

   private static final class BeamRay {
      private final Vec3 start;
      private final Vec3 end;
      private final Vec3 surface;

      private BeamRay(Vec3 start, Vec3 end, Vec3 surface) {
         this.start = start;
         this.end = end;
         this.surface = surface;
      }
   }

   private static final class BeamState {
      private final int mode;
      private final List<Vec3> trail = new ArrayList<>();
      private AvalonSovereignJudgementHandler.BeamRay lastRay;
      private Vec3 lastTrailPoint;
      private int lastHeldTick;

      private BeamState(int mode, int lastHeldTick) {
         this.mode = mode;
         this.lastHeldTick = lastHeldTick;
      }
   }
}
