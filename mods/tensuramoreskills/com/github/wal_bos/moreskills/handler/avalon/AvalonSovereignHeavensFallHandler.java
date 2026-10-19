package com.github.wal_bos.moreskills.handler.avalon;

import com.github.wal_bos.moreskills.configs.MoreSkillsConfigs;
import com.github.wal_bos.moreskills.net.msg.AvalonSovereignHeavensFallVfxPayload;
import com.github.wal_bos.moreskills.support.RoyalSkillSupport;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.tensura.damage.TensuraDamageTypes;
import io.github.manasmods.tensura.util.EnergyHelper;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;

final class AvalonSovereignHeavensFallHandler implements AvalonSovereignModeHandler {
   private static final Map<UUID, AvalonSovereignHeavensFallHandler.VolleyState> ACTIVE = new HashMap<>();
   private static final int FIRE_START = 3;
   private static final int SERVER_BEAM_LIFE = 86;
   private static final int CLIENT_DURATION = 104;
   private static final int MAX_TARGETS = 96;
   private static final double HIT_RADIUS = 1.1;

   @Override
   public String name() {
      return "Heaven's Fall";
   }

   @Override
   public double magiculeCost(LivingEntity entity, ManasSkillInstance instance) {
      return (Double)MoreSkillsConfigs.GRAND.avalon.heavensFallCost.get();
   }

   @Override
   public void activate(ManasSkillInstance instance, LivingEntity entity, int topMode) {
      if (entity.level() instanceof ServerLevel level) {
         if (instance.getCoolDown(topMode) <= 0) {
            if (!EnergyHelper.isOutOfEnergy(entity, instance, topMode)) {
               double radius = (Double)MoreSkillsConfigs.GRAND.avalon.heavensFallRadius.get();
               List<LivingEntity> targets = new ArrayList<>(RoyalSkillSupport.enemiesInRange(entity, radius));
               targets.removeIf(targetx -> !RoyalSkillSupport.validEnemy(entity, targetx));
               targets.sort(Comparator.comparingDouble(entity::distanceToSqr));
               if (targets.size() > 96) {
                  targets = new ArrayList<>(targets.subList(0, 96));
               }

               if (!targets.isEmpty()) {
                  float damage = ((Double)MoreSkillsConfigs.GRAND.avalon.heavensFallDamage.get()).floatValue();
                  Vec3 casterCenter = entity.position().add(0.0, entity.getBbHeight() * 0.64, 0.0);
                  AvalonSovereignHeavensFallHandler.VolleyState state = new AvalonSovereignHeavensFallHandler.VolleyState(topMode, damage);
                  int index = 0;

                  for (LivingEntity target : targets) {
                     Vec3 targetCenter = target.position().add(0.0, target.getBbHeight() * 0.55, 0.0);
                     state.beams.add(new AvalonSovereignHeavensFallHandler.TargetBeam(target.getUUID(), target.getId(), index));
                     AvalonSovereignHeavensFallVfxPayload.send(level, entity, target, casterCenter, targetCenter, 3.6F, 104, index);
                     index++;
                  }

                  ACTIVE.put(entity.getUUID(), state);
                  AvalonSovereignHeavensFallVfxPayload.send(level, entity, null, casterCenter, casterCenter, (float)radius, 58, -1);
                  level.playSound(null, entity.blockPosition(), SoundEvents.BEACON_POWER_SELECT, SoundSource.PLAYERS, 4.4F, 1.72F);
                  level.playSound(null, entity.blockPosition(), (SoundEvent)SoundEvents.TRIDENT_THROW.value(), SoundSource.PLAYERS, 3.2F, 1.78F);
                  entity.swing(InteractionHand.MAIN_HAND, true);
                  instance.setCoolDown(320, topMode);
                  instance.addMasteryPoint(entity);
                  instance.markDirty();
               }
            }
         }
      }
   }

   @Override
   public void tick(ManasSkillInstance instance, LivingEntity entity) {
      if (entity.level() instanceof ServerLevel level) {
         AvalonSovereignHeavensFallHandler.VolleyState state = ACTIVE.get(entity.getUUID());
         if (state != null) {
            if (entity.isAlive() && !entity.isRemoved()) {
               state.tick(level, entity, instance);
               if (state.done()) {
                  ACTIVE.remove(entity.getUUID());
               }
            } else {
               ACTIVE.remove(entity.getUUID());
            }
         }
      }
   }

   @Override
   public void cleanup(LivingEntity entity) {
      ACTIVE.remove(entity.getUUID());
   }

   private static LivingEntity resolve(ServerLevel level, UUID uuid, int id) {
      if (uuid != null && level.getEntity(uuid) instanceof LivingEntity living) {
         return living;
      } else {
         return level.getEntity(id) instanceof LivingEntity living ? living : null;
      }
   }

   private static Vec3 socket(Vec3 caster, AvalonSovereignHeavensFallHandler.Basis basis, double t, int index, long seed) {
      double angle = index * 2.399963229728653 + seed * 3.1E-4 + t * 0.03;
      double ring = 1.95 + index % 7 * 0.3 + hash(seed, index + 4) * 0.42;
      double wave = Math.sin(t * 0.12 + index) * 0.3;
      return caster.add(basis.right.scale(Math.cos(angle) * ring)).add(basis.up.scale(Math.sin(angle) * ring + wave)).subtract(basis.forward.scale(0.26));
   }

   private static Vec3 radialOut(AvalonSovereignHeavensFallHandler.Basis basis, int index, double t, long seed) {
      double angle = index * 2.399963229728653 + seed * 3.1E-4 + t * 0.014;
      return basis.right.scale(Math.cos(angle)).add(basis.up.scale(Math.sin(angle))).normalize();
   }

   private static AvalonSovereignHeavensFallHandler.Basis basis(Vec3 caster, Vec3 target) {
      Vec3 forward = target.subtract(caster);
      if (forward.lengthSqr() < 1.0E-4) {
         forward = new Vec3(0.0, 0.0, 1.0);
      } else {
         forward = forward.normalize();
      }

      Vec3 right = forward.cross(new Vec3(0.0, 1.0, 0.0));
      if (right.lengthSqr() < 1.0E-4) {
         right = new Vec3(1.0, 0.0, 0.0);
      } else {
         right = right.normalize();
      }

      Vec3 up = right.cross(forward).normalize();
      return new AvalonSovereignHeavensFallHandler.Basis(forward, right, up);
   }

   private static long trajectorySeed(int sourceId, int targetId) {
      return (long)sourceId << 32 ^ targetId & 4294967295L ^ 7640891576956012809L;
   }

   private static float hash(long seed, int value) {
      long x = seed ^ value * -7046029254386353131L;
      x ^= x >>> 30;
      x *= -4658895280553007687L;
      x ^= x >>> 27;
      x *= -7723592293110705685L;
      x ^= x >>> 31;
      return (float)(x & 16777215L) / 1.6777215E7F;
   }

   private record Basis(Vec3 forward, Vec3 right, Vec3 up) {
   }

   private static final class TargetBeam {
      private final UUID targetUuid;
      private final int targetId;
      private final int index;
      private boolean active;
      private boolean finished;
      private int age;
      private Vec3 pos = Vec3.ZERO;
      private Vec3 vel = Vec3.ZERO;

      private TargetBeam(UUID targetUuid, int targetId, int index) {
         this.targetUuid = targetUuid;
         this.targetId = targetId;
         this.index = index;
      }
   }

   private static final class VolleyState {
      private final int mode;
      private final float damage;
      private final List<AvalonSovereignHeavensFallHandler.TargetBeam> beams = new ArrayList<>();
      private int age;

      private VolleyState(int mode, float damage) {
         this.mode = mode;
         this.damage = damage;
      }

      private void tick(ServerLevel level, LivingEntity owner, ManasSkillInstance instance) {
         this.age++;
         Vec3 casterPos = owner.position().add(0.0, owner.getBbHeight() * 0.64, 0.0);

         for (AvalonSovereignHeavensFallHandler.TargetBeam beam : this.beams) {
            if (!beam.finished) {
               LivingEntity target = AvalonSovereignHeavensFallHandler.resolve(level, beam.targetUuid, beam.targetId);
               if (target != null && RoyalSkillSupport.validEnemy(owner, target)) {
                  Vec3 targetPos = target.position().add(0.0, target.getBbHeight() * 0.55, 0.0);
                  AvalonSovereignHeavensFallHandler.Basis basis = AvalonSovereignHeavensFallHandler.basis(casterPos, targetPos);
                  if (this.age >= 3) {
                     long trajectorySeed = AvalonSovereignHeavensFallHandler.trajectorySeed(owner.getId(), beam.targetId);
                     Vec3 socket = AvalonSovereignHeavensFallHandler.socket(casterPos, basis, this.age, beam.index, trajectorySeed);
                     if (!beam.active) {
                        beam.active = true;
                        beam.age = 0;
                        beam.pos = socket;
                        Vec3 outward = AvalonSovereignHeavensFallHandler.radialOut(basis, beam.index, this.age, trajectorySeed)
                           .scale(2.35 + beam.index % 5 * 0.045);
                        beam.vel = outward.add(basis.forward.scale(0.38));
                     } else {
                        beam.age++;
                        double hitRadius = 1.1 + target.getBbWidth() * 0.55;
                        Vec3 desired = targetPos.subtract(beam.pos);
                        double dist = desired.length();
                        if (dist <= hitRadius) {
                           this.hit(level, owner, instance, target, targetPos);
                           beam.finished = true;
                        } else if (beam.age > 86) {
                           this.hit(level, owner, instance, target, targetPos);
                           beam.finished = true;
                        } else {
                           double curve = Mth.clamp((beam.age - 4.0) / 24.0, 0.0, 1.0);
                           double hardTrack = Mth.clamp((beam.age - 22.0) / 22.0, 0.0, 1.0);
                           Vec3 desiredDir = desired.normalize();
                           Vec3 currentDir = beam.vel.lengthSqr() > 1.0E-4 ? beam.vel.normalize() : desiredDir;
                           Vec3 steerDir = currentDir.lerp(desiredDir, 0.18 + hardTrack * 0.42).normalize();
                           Vec3 accel = steerDir.scale(0.07 + curve * 0.135 + hardTrack * 0.08);
                           beam.vel = beam.vel.scale(0.905).add(accel);
                           double speed = beam.vel.length();
                           double maxSpeed = 1.52 + Math.min(0.95, beam.age * 0.014);
                           if (speed > maxSpeed) {
                              beam.vel = beam.vel.normalize().scale(maxSpeed);
                           }

                           Vec3 previous = beam.pos;
                           Vec3 next = previous.add(beam.vel);
                           beam.pos = next;
                           if (RoyalSkillSupport.distanceToSegment(targetPos, previous, next) <= hitRadius) {
                              this.hit(level, owner, instance, target, targetPos);
                              beam.finished = true;
                           }
                        }
                     }
                  }
               } else {
                  beam.finished = true;
               }
            }
         }
      }

      private void hit(ServerLevel level, LivingEntity owner, ManasSkillInstance instance, LivingEntity target, Vec3 position) {
         DamageSource holy = TensuraDamageTypes.getEntityDamageSource(level, TensuraDamageTypes.HOLY_DAMAGE, owner);
         DamageSource light = TensuraDamageTypes.getEntityDamageSource(level, TensuraDamageTypes.LIGHT_ELEMENTAL, owner);
         target.invulnerableTime = 0;
         boolean holyHit = target.hurt(holy, this.damage * 0.6F);
         if (!holyHit && target.isAlive()) {
            RoyalSkillSupport.unavoidableDamage(target, holy, this.damage * 0.6F);
         }

         if (target.isAlive()) {
            target.invulnerableTime = 0;
            boolean lightHit = target.hurt(light, this.damage * 0.4F);
            if (!lightHit && target.isAlive()) {
               RoyalSkillSupport.unavoidableDamage(target, light, this.damage * 0.4F);
            }
         }

         RoyalSkillSupport.markCombat(owner, target);
         if ((target.getId() & 3) == 0) {
            level.playSound(null, position.x, position.y, position.z, SoundEvents.AMETHYST_CLUSTER_BREAK, SoundSource.PLAYERS, 1.1F, 1.85F);
         }
      }

      private boolean done() {
         if (this.age < 7) {
            return false;
         }

         for (AvalonSovereignHeavensFallHandler.TargetBeam beam : this.beams) {
            if (!beam.finished) {
               return false;
            }
         }

         return true;
      }
   }
}
