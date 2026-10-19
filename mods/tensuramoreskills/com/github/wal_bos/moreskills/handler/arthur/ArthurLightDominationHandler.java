package com.github.wal_bos.moreskills.handler.arthur;

import com.github.wal_bos.moreskills.configs.MoreSkillsConfigs;
import com.github.wal_bos.moreskills.net.msg.ArthurLightDominationVfxPayload;
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

public final class ArthurLightDominationHandler implements ArthurModeHandler {
   private static final Map<UUID, ArthurLightDominationHandler.BeamState> ACTIVE = new HashMap<>();
   private static final int AIM_INTERVAL = 2;
   private static final int DAMAGE_INTERVAL = 6;
   private static final int MASTERY_INTERVAL = 40;
   private static final int MAX_TRAIL_POINTS = 72;
   private static final int MAX_INSERTED_POINTS_PER_SAMPLE = 6;
   private static final double TRAIL_POINT_SPACING = 1.35;
   private static final double BEAM_RADIUS = 3.4;
   private static final double ERUPTION_RADIUS = 4.8;
   private static final double ERUPTION_HEIGHT = 18.0;
   private static final float BEAM_DAMAGE_FACTOR = 0.3F;
   private static final float ERUPTION_DAMAGE_FACTOR = 2.0F;

   @Override
   public String modeId() {
      return "arthur_once_and_future_king.light_domination";
   }

   @Override
   public double magiculeCost(LivingEntity entity, ManasSkillInstance instance) {
      return (Double)MoreSkillsConfigs.GRAND.arthur.lightDominationCost.get();
   }

   @Override
   public void activate(ManasSkillInstance instance, LivingEntity entity, int keyNumber, int mode) {
      if (entity.level() instanceof ServerLevel level) {
         if (instance.getCoolDown(mode) <= 0) {
            if (!EnergyHelper.isOutOfEnergy(entity, instance, mode)) {
               ArthurLightDominationHandler.BeamState state = new ArthurLightDominationHandler.BeamState(mode, entity.tickCount);
               ACTIVE.put(entity.getUUID(), state);
               ArthurLightDominationHandler.BeamRay ray = trace(level, entity, (Double)MoreSkillsConfigs.GRAND.arthur.lightDominationRange.get());
               state.lastRay = ray;
               ArthurLightDominationVfxPayload.send(level, entity, null, ray.start, ray.end, 6.6F, 12, 0);
               sampleTrail(level, entity, state, ray);
               level.playSound(null, entity.blockPosition(), SoundEvents.BEACON_ACTIVATE, SoundSource.PLAYERS, 3.8F, 1.72F);
               level.playSound(null, entity.blockPosition(), SoundEvents.WARDEN_SONIC_BOOM, SoundSource.PLAYERS, 2.8F, 1.62F);
               entity.swing(InteractionHand.MAIN_HAND, true);
            }
         }
      }
   }

   @Override
   public boolean onHeld(ManasSkillInstance instance, LivingEntity entity, int heldTicks, int mode) {
      if (entity.level().isClientSide()) {
         return true;
      }

      if (entity.level() instanceof ServerLevel level) {
         ArthurLightDominationHandler.BeamState state = ACTIVE.get(entity.getUUID());
         if (state != null && state.mode == mode) {
            state.lastHeldTick = entity.tickCount;
            if (heldTicks % 2 == 0) {
               ArthurLightDominationHandler.BeamRay ray = trace(level, entity, (Double)MoreSkillsConfigs.GRAND.arthur.lightDominationRange.get());
               state.lastRay = ray;
               ArthurLightDominationVfxPayload.send(level, entity, null, ray.start, ray.end, 6.6F, 12, 0);
               sampleTrail(level, entity, state, ray);
            }

            if (heldTicks > 0 && heldTicks % 6 == 0 && state.lastRay != null) {
               damageBeam(level, entity, instance, state, state.lastRay);
            }

            if (heldTicks > 0 && heldTicks % 40 == 0) {
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
   public void onRelease(ManasSkillInstance instance, LivingEntity entity, int heldTicks, int keyNumber, int mode) {
      if (entity.level() instanceof ServerLevel level) {
         ArthurLightDominationHandler.BeamState state = ACTIVE.remove(entity.getUUID());
         if (state != null) {
            finish(level, entity, instance, state);
         }
      }
   }

   @Override
   public void tick(ManasSkillInstance instance, LivingEntity entity) {
      if (entity.level() instanceof ServerLevel level) {
         ArthurLightDominationHandler.BeamState state = ACTIVE.get(entity.getUUID());
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

   private static void finish(ServerLevel level, LivingEntity entity, ManasSkillInstance instance, ArthurLightDominationHandler.BeamState state) {
      ArthurLightDominationHandler.BeamRay ray = state.lastRay;
      Vec3 stop = ray == null ? RoyalSkillSupport.eye(entity) : ray.end;
      ArthurLightDominationVfxPayload.send(level, entity, null, stop, stop, 1.0F, 4, 3);
      if (state.trail.size() >= 2) {
         eruptTrail(level, entity, instance, state);
         instance.addMasteryPoint(entity);
      }

      level.playSound(null, stop.x, stop.y, stop.z, (SoundEvent)SoundEvents.GENERIC_EXPLODE.value(), SoundSource.PLAYERS, 5.0F, 1.28F);
      level.playSound(null, stop.x, stop.y, stop.z, (SoundEvent)SoundEvents.TRIDENT_THUNDER.value(), SoundSource.PLAYERS, 4.2F, 1.52F);
      instance.setCoolDown((Integer)MoreSkillsConfigs.GRAND.arthur.lightDominationCooldown.get(), state.mode);
      instance.markDirty();
   }

   @Override
   public boolean shouldTriggerReleaseOnHeldInterrupt(ManasSkillInstance instance, LivingEntity entity, int keyNumber, int mode) {
      return true;
   }

   @Override
   public void cleanup(LivingEntity entity) {
      ACTIVE.remove(entity.getUUID());
   }

   private static ArthurLightDominationHandler.BeamRay trace(ServerLevel level, LivingEntity entity, double range) {
      Vec3 look = entity.getLookAngle();
      if (look.lengthSqr() < 1.0E-8) {
         look = new Vec3(0.0, 0.0, 1.0);
      }

      look = look.normalize();
      Vec3 start = RoyalSkillSupport.eye(entity).add(look.scale(1.15));
      Vec3 rawEnd = start.add(look.scale(range));
      BlockHitResult hit = level.clip(new ClipContext(start, rawEnd, Block.COLLIDER, Fluid.NONE, entity));
      Vec3 end = hit.getType() == Type.BLOCK ? hit.getLocation() : rawEnd;
      Vec3 surface = hit.getType() == Type.BLOCK ? hit.getLocation().add(0.0, 0.045, 0.0) : null;
      return new ArthurLightDominationHandler.BeamRay(start, end, look, surface);
   }

   private static void sampleTrail(
      ServerLevel level, LivingEntity entity, ArthurLightDominationHandler.BeamState state, ArthurLightDominationHandler.BeamRay ray
   ) {
      if (ray.surface != null) {
         Vec3 point = ray.surface;
         if (state.trail.isEmpty()) {
            state.trail.add(point);
            state.lastTrailPoint = point;
            ArthurLightDominationVfxPayload.send(level, entity, null, point, point, 3.2F, 160, 1);
         } else {
            Vec3 last = state.lastTrailPoint;
            double distance = last.distanceTo(point);
            if (!(distance < 1.35)) {
               int inserts = Math.min(6, Math.max(1, (int)Math.ceil(distance / 1.35)));

               for (int i = 1; i <= inserts && state.trail.size() < 72; i++) {
                  Vec3 next = last.lerp(point, (double)i / inserts);
                  Vec3 previous = state.trail.get(state.trail.size() - 1);
                  state.trail.add(next);
                  ArthurLightDominationVfxPayload.send(level, entity, null, previous, next, 3.2F, 160, 1);
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
      ArthurLightDominationHandler.BeamState state,
      ArthurLightDominationHandler.BeamRay ray
   ) {
      AABB box = new AABB(ray.start, ray.end).inflate(4.9);
      float configured = ((Double)MoreSkillsConfigs.GRAND.arthur.lightDominationDamage.get()).floatValue();
      float pulseDamage = configured * 0.3F;
      DamageSource source = RoyalSkillSupport.authoritySource(owner, instance, state.mode, TensuraDamageTypes.LIGHT_ELEMENTAL);

      for (LivingEntity target : level.getEntitiesOfClass(LivingEntity.class, box, living -> RoyalSkillSupport.validEnemy(owner, living))) {
         Vec3 center = target.position().add(0.0, target.getBbHeight() * 0.5, 0.0);
         if (!(RoyalSkillSupport.distanceToSegment(center, ray.start, ray.end) > 3.4 + target.getBbWidth() * 0.5)) {
            target.invulnerableTime = 0;
            target.hurt(source, pulseDamage);
            RoyalSkillSupport.markCombat(owner, target);
         }
      }
   }

   private static void eruptTrail(ServerLevel level, LivingEntity owner, ManasSkillInstance instance, ArthurLightDominationHandler.BeamState state) {
      List<Vec3> trail = state.trail;
      if (trail.size() >= 2) {
         float configured = ((Double)MoreSkillsConfigs.GRAND.arthur.lightDominationDamage.get()).floatValue();
         float totalDamage = configured * 2.0F;
         DamageSource source = RoyalSkillSupport.authoritySource(owner, instance, state.mode, TensuraDamageTypes.HOLY_DAMAGE);
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
            maxY = Math.max(maxY, point.y + 18.0);
            maxZ = Math.max(maxZ, point.z);
         }

         AABB area = new AABB(minX, minY - 2.0, minZ, maxX, maxY, maxZ).inflate(5.8);

         for (LivingEntity target : level.getEntitiesOfClass(LivingEntity.class, area, living -> RoyalSkillSupport.validEnemy(owner, living))) {
            Vec3 center = target.position().add(0.0, target.getBbHeight() * 0.5, 0.0);
            double nearest = distanceToTrailXZ(center, trail);
            if (!(nearest > 4.8 + target.getBbWidth() * 0.5)) {
               target.invulnerableTime = 0;
               target.hurt(source, totalDamage * 0.5F);
               RoyalSkillSupport.markCombat(owner, target);
            }
         }

         int last = trail.size() - 1;

         for (int i = 0; i < last; i++) {
            Vec3 a = trail.get(i);
            Vec3 b = trail.get(i + 1);
            if (!(a.distanceToSqr(b) < 0.04)) {
               ArthurLightDominationVfxPayload.send(level, owner, null, a, b, 5.2F, 72, 2);
            }
         }
      }
   }

   private static double distanceToTrailXZ(Vec3 point, List<Vec3> trail) {
      double best = Double.MAX_VALUE;

      for (int i = 0; i < trail.size() - 1; i++) {
         Vec3 a = trail.get(i);
         Vec3 b = trail.get(i + 1);
         double ax = a.x;
         double az = a.z;
         double bx = b.x;
         double bz = b.z;
         double dx = bx - ax;
         double dz = bz - az;
         double len = dx * dx + dz * dz;
         double t = len <= 1.0E-8 ? 0.0 : ((point.x - ax) * dx + (point.z - az) * dz) / len;
         t = Math.max(0.0, Math.min(1.0, t));
         double px = ax + dx * t;
         double pz = az + dz * t;
         double ox = point.x - px;
         double oz = point.z - pz;
         best = Math.min(best, Math.sqrt(ox * ox + oz * oz));
      }

      return best;
   }

   private static final class BeamRay {
      private final Vec3 start;
      private final Vec3 end;
      private final Vec3 direction;
      private final Vec3 surface;

      private BeamRay(Vec3 start, Vec3 end, Vec3 direction, Vec3 surface) {
         this.start = start;
         this.end = end;
         this.direction = direction;
         this.surface = surface;
      }
   }

   private static final class BeamState {
      private final int mode;
      private final List<Vec3> trail = new ArrayList<>();
      private ArthurLightDominationHandler.BeamRay lastRay;
      private Vec3 lastTrailPoint;
      private int lastHeldTick;

      private BeamState(int mode, int lastHeldTick) {
         this.mode = mode;
         this.lastHeldTick = lastHeldTick;
      }
   }
}
