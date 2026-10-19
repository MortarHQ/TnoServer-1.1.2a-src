package com.github.wal_bos.moreskills.handler;

import com.github.wal_bos.moreskills.ability.skill.unique.MitamaFaithSkill;
import com.github.wal_bos.moreskills.configs.MoreSkillsConfigs;
import com.github.wal_bos.moreskills.net.msg.MitamaDivineBeamsPayload;
import dev.architectury.networking.NetworkManager;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.tensura.damage.TensuraDamageTypes;
import io.github.manasmods.tensura.storage.TensuraStorages;
import io.github.manasmods.tensura.storage.ep.IExistence;
import io.github.manasmods.tensura.util.EnergyHelper;
import io.github.manasmods.tensura.util.ObjectSelectionHelper;
import io.github.manasmods.tensura.util.SubordinateHelper;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.tick.ServerTickEvent.Post;

public final class MitamaDivineBeamsHandler {
   private static final Map<Integer, MitamaDivineBeamsHandler.VolleyState> ACTIVE = new ConcurrentHashMap<>();
   private static boolean initialized;
   private static final int CLIENT_DURATION = 132;
   private static final int SERVER_BEAM_LIFE = 82;
   private static final int FIRE_START = 3;
   private static final int FIRE_SPACING = 0;
   private static final double WATCH_RANGE = 200.0;
   private static final double HIT_RADIUS = 0.88;

   private MitamaDivineBeamsHandler() {
   }

   public static void init() {
      if (!initialized) {
         initialized = true;
         NeoForge.EVENT_BUS.addListener(MitamaDivineBeamsHandler::serverTick);
      }
   }

   public static void cast(ManasSkillInstance instance, LivingEntity caster) {
      init();
      if (!EnergyHelper.isOutOfEnergy(caster, instance, 2)) {
         if (caster.level() instanceof ServerLevel level) {
            double var25 = MitamaFaithSkill.getWorship(instance);
            int logicalBeams = Math.max(1, 1 + (int)(var25 / Math.max(1.0, (Double)MoreSkillsConfigs.GRAND.mitamaFaith.worshipPerExtraBeam.get())));
            int visualBeams = Math.min((Integer)MoreSkillsConfigs.GRAND.mitamaFaith.beamVisualCap.get(), logicalBeams);
            double radius = Math.min(
               (Double)MoreSkillsConfigs.GRAND.mitamaFaith.beamsRadiusCap.get(),
               (Double)MoreSkillsConfigs.GRAND.mitamaFaith.beamsBaseRadius.get()
                  + Math.sqrt(var25) / Math.max(1.0, (Double)MoreSkillsConfigs.GRAND.mitamaFaith.beamsRadiusSqrtDivisor.get())
            );
            Entity picked = ObjectSelectionHelper.getTargetingEntity(caster, radius, false, true);
            if (picked instanceof LivingEntity) {
               LivingEntity target = (LivingEntity)picked;
               if (target != caster && target.isAlive() && !isOwnedBy(caster, target)) {
                  int seed = caster.getRandom().nextInt();
                  int packetId = caster.getId() * 65536 + caster.tickCount * 64 + target.getId();
                  Vec3 casterPos = caster.position().add(0.0, caster.getBbHeight() * 0.64, 0.0);
                  Vec3 targetPos = target.position().add(0.0, target.getBbHeight() * 0.55, 0.0);
                  double baseDamage = (Double)MoreSkillsConfigs.GRAND.mitamaFaith.beamsBaseDamage.get() * MitamaFaithSkill.getDamageMultiplier(var25);
                  double logicalDamageBoost = Math.max(1.0, logicalBeams / Math.max(1.0, visualBeams));
                  float damagePerBeam = (float)Math.min(Float.MAX_VALUE, baseDamage * logicalDamageBoost);
                  MitamaDivineBeamsHandler.VolleyState state = new MitamaDivineBeamsHandler.VolleyState(
                     level, caster.getUUID(), target.getUUID(), caster.getId(), target.getId(), casterPos, targetPos, visualBeams, seed, damagePerBeam
                  );
                  ACTIVE.put(packetId, state);
                  MitamaDivineBeamsPayload payload = new MitamaDivineBeamsPayload(
                     packetId,
                     caster.getId(),
                     target.getId(),
                     casterPos.x,
                     casterPos.y,
                     casterPos.z,
                     targetPos.x,
                     targetPos.y,
                     targetPos.z,
                     visualBeams,
                     seed,
                     132
                  );
                  AABB watch = caster.getBoundingBox().inflate(200.0);

                  for (ServerPlayer player : level.getEntitiesOfClass(ServerPlayer.class, watch, p -> p != null && !p.isSpectator())) {
                     NetworkManager.sendToPlayer(player, payload);
                  }

                  if (caster instanceof Player player) {
                     player.displayClientMessage(
                        Component.literal("Mitama Divine Beams: " + logicalBeams + " beams locked on " + target.getName().getString() + ".")
                           .withStyle(ChatFormatting.GOLD),
                        true
                     );
                  }

                  instance.addMasteryPoint(caster);
                  instance.setCoolDown((Integer)MoreSkillsConfigs.GRAND.mitamaFaith.beamsCooldownTicks.get(), 2);
                  caster.swing(InteractionHand.MAIN_HAND, true);
                  level.playSound(null, caster.getX(), caster.getY(), caster.getZ(), SoundEvents.BEACON_POWER_SELECT, SoundSource.PLAYERS, 2.4F, 1.85F);
                  level.playSound(
                     null, target.getX(), target.getY(), target.getZ(), (SoundEvent)SoundEvents.TRIDENT_THROW.value(), SoundSource.PLAYERS, 1.15F, 1.95F
                  );
                  return;
               }
            }

            fail(caster);
         }
      }
   }

   private static void serverTick(Post event) {
      if (!ACTIVE.isEmpty()) {
         Iterator<Entry<Integer, MitamaDivineBeamsHandler.VolleyState>> iterator = ACTIVE.entrySet().iterator();

         while (iterator.hasNext()) {
            MitamaDivineBeamsHandler.VolleyState state = iterator.next().getValue();
            state.tick();
            if (state.done()) {
               iterator.remove();
            }
         }
      }
   }

   private static boolean isOwnedBy(LivingEntity owner, LivingEntity target) {
      if (target == owner) {
         return true;
      } else {
         LivingEntity subordinateOwner = SubordinateHelper.getSubordinateOwner(target);
         if (subordinateOwner != null && Objects.equals(subordinateOwner.getUUID(), owner.getUUID())) {
            return true;
         } else if (target instanceof TamableAnimal tamable) {
            return tamable.isOwnedBy(owner);
         } else {
            IExistence existence = TensuraStorages.getExistenceFrom(target);
            return Objects.equals(existence.getPermanentOwner(), owner.getUUID());
         }
      }
   }

   private static void fail(LivingEntity entity) {
      if (entity instanceof Player player) {
         player.displayClientMessage(Component.literal("Mitama Divine Beams needs a looked target.").withStyle(ChatFormatting.RED), true);
      }
   }

   private static final class VolleyState {
      private final ServerLevel level;
      private final UUID casterUuid;
      private final UUID targetUuid;
      private final int casterId;
      private final int targetId;
      private final Vec3 fallbackCaster;
      private final Vec3 fallbackTarget;
      private final int beams;
      private final int seed;
      private final float damagePerBeam;
      private final MitamaDivineBeamsHandler.VolleyState.BeamState[] beamArray;
      private int age;

      private VolleyState(
         ServerLevel level,
         UUID casterUuid,
         UUID targetUuid,
         int casterId,
         int targetId,
         Vec3 fallbackCaster,
         Vec3 fallbackTarget,
         int beams,
         int seed,
         float damagePerBeam
      ) {
         this.level = level;
         this.casterUuid = casterUuid;
         this.targetUuid = targetUuid;
         this.casterId = casterId;
         this.targetId = targetId;
         this.fallbackCaster = fallbackCaster;
         this.fallbackTarget = fallbackTarget;
         this.beams = Mth.clamp(beams, 1, 128);
         this.seed = seed;
         this.damagePerBeam = damagePerBeam;
         this.beamArray = new MitamaDivineBeamsHandler.VolleyState.BeamState[this.beams];

         for (int i = 0; i < this.beams; i++) {
            this.beamArray[i] = new MitamaDivineBeamsHandler.VolleyState.BeamState();
         }
      }

      private void tick() {
         this.age++;
         LivingEntity caster = this.resolve(this.casterUuid, this.casterId);
         LivingEntity target = this.resolve(this.targetUuid, this.targetId);
         if (caster != null && target != null && caster.isAlive() && target.isAlive()) {
            Vec3 casterPos = caster.position().add(0.0, caster.getBbHeight() * 0.64, 0.0);
            Vec3 targetPos = target.position().add(0.0, target.getBbHeight() * 0.55, 0.0);
            MitamaDivineBeamsHandler.VolleyState.Basis basis = this.basis(casterPos, targetPos);

            for (int i = 0; i < this.beams; i++) {
               MitamaDivineBeamsHandler.VolleyState.BeamState beam = this.beamArray[i];
               if (!beam.finished) {
                  int fireStart = 3 + i * 0;
                  if (this.age >= fireStart) {
                     Vec3 socket = this.socket(casterPos, basis, this.age, i);
                     if (!beam.active) {
                        beam.active = true;
                        beam.age = 0;
                        beam.pos = socket;
                        double spreadPower = Math.sqrt(Math.max(1.0, this.beams));
                        Vec3 outward = this.radialOut(basis, i, this.age).scale(2.2 + spreadPower * 0.24);
                        beam.vel = outward.add(basis.forward.scale(0.34));
                     } else {
                        beam.age++;
                        if (beam.age > 82) {
                           beam.finished = true;
                        } else {
                           Vec3 liveTarget = target.position().add(0.0, target.getBbHeight() * 0.55, 0.0);
                           Vec3 desired = liveTarget.subtract(beam.pos);
                           double dist = desired.length();
                           if (dist <= 0.88 + target.getBbWidth() * 0.45) {
                              this.hit(caster, target, beam.pos);
                              beam.finished = true;
                           } else {
                              double curve = Mth.clamp(beam.age / 22.0, 0.0, 1.0);
                              Vec3 accel = desired.normalize().scale(0.045 + curve * 0.145);
                              beam.vel = beam.vel.scale(0.918).add(accel);
                              double speed = beam.vel.length();
                              double maxSpeed = 1.45 + Math.min(0.75, beam.age * 0.012);
                              if (speed > maxSpeed) {
                                 beam.vel = beam.vel.normalize().scale(maxSpeed);
                              }

                              beam.pos = beam.pos.add(beam.vel);
                           }
                        }
                     }
                  }
               }
            }
         } else {
            for (MitamaDivineBeamsHandler.VolleyState.BeamState beam : this.beamArray) {
               beam.finished = true;
            }
         }
      }

      private void hit(LivingEntity caster, LivingEntity target, Vec3 pos) {
         target.hurt(TensuraDamageTypes.getEntityDamageSource(this.level, TensuraDamageTypes.HOLY_DAMAGE, caster), this.damagePerBeam);
         this.level.sendParticles(ParticleTypes.END_ROD, pos.x, pos.y, pos.z, 34, 0.34, 0.34, 0.34, 0.035);
         this.level.sendParticles(ParticleTypes.FLASH, pos.x, pos.y, pos.z, 1, 0.0, 0.0, 0.0, 0.0);
         this.level.playSound(null, target.getX(), target.getY(), target.getZ(), SoundEvents.AMETHYST_CLUSTER_BREAK, SoundSource.PLAYERS, 1.25F, 2.0F);
      }

      private boolean done() {
         if (this.age < 132) {
            return false;
         }

         for (MitamaDivineBeamsHandler.VolleyState.BeamState beam : this.beamArray) {
            if (!beam.finished) {
               return false;
            }
         }

         return true;
      }

      private LivingEntity resolve(UUID uuid, int id) {
         if (uuid != null && this.level.getEntity(uuid) instanceof LivingEntity living) {
            return living;
         } else {
            return this.level.getEntity(id) instanceof LivingEntity living ? living : null;
         }
      }

      private Vec3 socket(Vec3 caster, MitamaDivineBeamsHandler.VolleyState.Basis basis, double t, int index) {
         double spreadPower = Math.sqrt(Math.max(1.0, this.beams));
         double angle = index * 2.399963229728653 + this.seed * 3.1E-4 + t * 0.04;
         double ring = 1.75 + spreadPower * 0.2 + index % 7 * 0.32 + this.hash(this.seed, index + 4) * 0.4;
         double wave = Math.sin(t * 0.14 + index) * (0.25 + spreadPower * 0.018);
         return caster.add(basis.right.scale(Math.cos(angle) * ring)).add(basis.up.scale(Math.sin(angle) * ring + wave)).subtract(basis.forward.scale(0.24));
      }

      private Vec3 radialOut(MitamaDivineBeamsHandler.VolleyState.Basis basis, int index, double t) {
         double angle = index * 2.399963229728653 + this.seed * 3.1E-4 + t * 0.018;
         return basis.right.scale(Math.cos(angle)).add(basis.up.scale(Math.sin(angle))).normalize();
      }

      private MitamaDivineBeamsHandler.VolleyState.Basis basis(Vec3 caster, Vec3 target) {
         Vec3 forward = target.subtract(caster);
         if (forward.lengthSqr() < 1.0E-4) {
            forward = new Vec3(0.0, 0.0, 1.0);
         } else {
            forward = forward.normalize();
         }

         Vec3 worldUp = new Vec3(0.0, 1.0, 0.0);
         Vec3 right = forward.cross(worldUp);
         if (right.lengthSqr() < 1.0E-4) {
            right = new Vec3(1.0, 0.0, 0.0);
         } else {
            right = right.normalize();
         }

         Vec3 up = right.cross(forward).normalize();
         return new MitamaDivineBeamsHandler.VolleyState.Basis(forward, right, up);
      }

      private float hash(int seed, int value) {
         int x = seed ^ value * 73244475;
         x = (x ^ x >>> 16) * 73244475;
         x = (x ^ x >>> 16) * 73244475;
         x ^= x >>> 16;
         return (x & 65535) / 65535.0F;
      }

      private record Basis(Vec3 forward, Vec3 right, Vec3 up) {
      }

      private static final class BeamState {
         private boolean active;
         private boolean finished;
         private int age;
         private Vec3 pos = Vec3.ZERO;
         private Vec3 vel = Vec3.ZERO;
      }
   }
}
