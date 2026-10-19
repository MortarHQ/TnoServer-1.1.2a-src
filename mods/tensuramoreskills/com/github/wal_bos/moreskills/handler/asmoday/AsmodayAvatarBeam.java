package com.github.wal_bos.moreskills.handler.asmoday;

import com.github.wal_bos.moreskills.configs.MoreSkillsConfigs;
import com.github.wal_bos.moreskills.net.msg.FeldwayAvatarBeamPayload;
import dev.architectury.networking.NetworkManager;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.tensura.damage.TensuraDamageHelper;
import io.github.manasmods.tensura.damage.TensuraDamageTypes;
import io.github.manasmods.tensura.registry.attribute.TensuraAttributes;
import io.github.manasmods.tensura.util.AttributeHelper;
import io.github.manasmods.tensura.util.EnergyHelper;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.OwnableEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public final class AsmodayAvatarBeam implements AsmodayAttack {
   private static final Map<UUID, AsmodayAvatarBeam.BeamState> STATES = new ConcurrentHashMap<>();
   private static final ResourceLocation ASMODAY_BEAM_DEGRADATION = ResourceLocation.fromNamespaceAndPath(
      "tensuramoreskills", "asmoday_avatar_beam_degradation"
   );
   private static final String TAG_HOLDING = "AsmodayBeamHolding";
   private static final String TAG_HELD_TICKS = "AsmodayBeamHeldTicks";
   private static final String TAG_RELEASE_ARMED = "AsmodayBeamReleaseArmed";

   @Override
   public String modeId() {
      return "asmoday.avatar_beam";
   }

   @Override
   public double magiculeCost(LivingEntity entity, ManasSkillInstance instance) {
      return (Double)cfg().avatarBeamCost.get();
   }

   @Override
   public void activate(ManasSkillInstance instance, LivingEntity entity, int keyNumber, int mode) {
      if (!entity.level().isClientSide()) {
         if (!STATES.containsKey(entity.getUUID())) {
            CompoundTag tag = instance.getOrCreateTag();
            tag.putBoolean("AsmodayBeamHolding", true);
            tag.putInt("AsmodayBeamHeldTicks", 0);
            tag.putBoolean("AsmodayBeamReleaseArmed", false);
            instance.markDirty();
            if (entity instanceof ServerPlayer player) {
               player.displayClientMessage(Component.literal("Destiny Beam charging: hold to arm").withStyle(ChatFormatting.DARK_PURPLE), true);
            }
         }
      }
   }

   @Override
   public boolean onHeld(ManasSkillInstance instance, LivingEntity entity, int heldTicks, int mode) {
      if (entity.level().isClientSide()) {
         return true;
      }

      if (STATES.containsKey(entity.getUUID())) {
         clearTags(instance);
         return false;
      }

      CompoundTag tag = instance.getOrCreateTag();
      if (!tag.getBoolean("AsmodayBeamHolding")) {
         return false;
      }

      if (heldTicks == 0 && EnergyHelper.isOutOfEnergy(entity, instance, mode)) {
         clearTags(instance);
         return false;
      }

      int realHeld = Math.max(heldTicks, tag.getInt("AsmodayBeamHeldTicks") + 1);
      tag.putInt("AsmodayBeamHeldTicks", realHeld);
      if (realHeld >= (Integer)cfg().avatarBeamArmReleaseTicks.get()) {
         tag.putBoolean("AsmodayBeamReleaseArmed", true);
      }

      int maxHold = (Integer)cfg().avatarBeamMaxHoldTicks.get();
      int clamped = Math.min(realHeld, maxHold);
      float charge = (float)clamped / maxHold;
      int percent = Math.round(charge * 100.0F);
      if (entity instanceof ServerPlayer player && realHeld % (Integer)cfg().avatarBeamChargeMessageIntervalTicks.get() == 0) {
         ChatFormatting color = percent >= 100 ? ChatFormatting.LIGHT_PURPLE : (percent >= 65 ? ChatFormatting.DARK_PURPLE : ChatFormatting.GRAY);
         player.displayClientMessage(Component.literal("Destiny Beam charging: " + percent + "%").withStyle(color), true);
      }

      if (realHeld % (Integer)cfg().avatarBeamChargeSoundIntervalTicks.get() == 0) {
         float pitch = 0.3F + charge * 0.75F;
         entity.level()
            .playSound(null, entity.getX(), entity.getY(), entity.getZ(), SoundEvents.BEACON_AMBIENT, SoundSource.PLAYERS, 1.2F + charge * 1.6F, pitch);
      }

      if (realHeld % (Integer)cfg().avatarBeamMasteryIntervalTicks.get() == 0) {
         instance.addMasteryPoint(entity);
      }

      lookAt(entity, getAimEnd(entity, getBeamStart(entity)));
      if (realHeld >= maxHold) {
         if (entity.level() instanceof ServerLevel level && !EnergyHelper.isOutOfEnergy(entity, instance, mode)) {
            beginBeam(level, entity, instance, mode, maxHold);
         }

         clearTags(instance);
         return false;
      } else {
         instance.markDirty();
         return true;
      }
   }

   @Override
   public void onRelease(ManasSkillInstance instance, LivingEntity entity, int heldTicks, int keyNumber, int mode) {
      if (entity.level() instanceof ServerLevel level) {
         CompoundTag tag = instance.getOrCreateTag();
         boolean holding = tag.getBoolean("AsmodayBeamHolding");
         boolean armed = tag.getBoolean("AsmodayBeamReleaseArmed");
         int realHeld = Math.max(heldTicks, tag.getInt("AsmodayBeamHeldTicks"));
         clearTags(instance);
         if (holding && armed && realHeld >= (Integer)cfg().avatarBeamArmReleaseTicks.get()) {
            if (!EnergyHelper.isOutOfEnergy(entity, instance, mode)) {
               if (!STATES.containsKey(entity.getUUID())) {
                  beginBeam(level, entity, instance, mode, realHeld);
               }
            }
         }
      } else {
         clearTags(instance);
      }
   }

   @Override
   public void cleanup(LivingEntity entity) {
      stopFor(entity);
   }

   public static void stopFor(LivingEntity entity) {
      if (entity != null) {
         STATES.remove(entity.getUUID());
         removeDegradation(entity);
      }
   }

   public static void tick(ServerLevel level) {
      if (!STATES.isEmpty()) {
         Iterator<AsmodayAvatarBeam.BeamState> iterator = STATES.values().iterator();

         while (iterator.hasNext()) {
            AsmodayAvatarBeam.BeamState state = iterator.next();
            LivingEntity owner = getLiving(level, state.owner);
            if (owner != null && owner.isAlive()) {
               state.age++;
               Vec3 start = getBeamStart(owner);
               Vec3 end = getAimEnd(owner, start);
               state.start = start;
               state.end = end;
               lookAt(owner, end);
               applyDegradation(owner);
               if (state.age % (Integer)cfg().avatarBeamPayloadRefreshTicks.get() == 0) {
                  sendBeamPayload(level, owner, start, end, state.duration - state.age, state.seed);
               }

               if (state.age == 1 || state.age == 20 || state.age == 40) {
                  level.playSound(null, owner.blockPosition(), SoundEvents.BEACON_ACTIVATE, SoundSource.PLAYERS, 5.8F, 0.28F);
                  level.playSound(null, owner.blockPosition(), SoundEvents.AMETHYST_BLOCK_CHIME, SoundSource.PLAYERS, 5.4F, 0.18F);
               }

               if (state.age == 50) {
                  level.playSound(null, owner.blockPosition(), SoundEvents.BEACON_POWER_SELECT, SoundSource.PLAYERS, 8.0F, 0.25F);
                  level.playSound(null, owner.blockPosition(), SoundEvents.WARDEN_SONIC_CHARGE, SoundSource.PLAYERS, 8.0F, 0.38F);
               }

               if (state.age >= 8 && state.age % (Integer)cfg().avatarBeamDamageIntervalTicks.get() == 0) {
                  damageBeam(
                     level,
                     owner,
                     start,
                     end,
                     (Double)cfg().avatarBeamRadius.get() * state.radiusMultiplier,
                     (float)((Double)cfg().avatarBeamTickDamage.get() * state.damageMultiplier),
                     false
                  );
               }

               if (state.age % (Integer)cfg().avatarBeamPulseIntervalTicks.get() == 0) {
                  firePulse(level, owner, start, end, false, state.damageMultiplier, state.radiusMultiplier);
               }

               if (state.age % (Integer)cfg().avatarBeamFinisherPulseIntervalTicks.get() == 0) {
                  firePulse(level, owner, start, end, true, state.damageMultiplier, state.radiusMultiplier);
               }

               if (state.age >= state.duration) {
                  firePulse(level, owner, start, end, true, state.damageMultiplier * 1.75F, state.radiusMultiplier * 1.35F);
                  removeDegradation(owner);
                  iterator.remove();
               }
            } else {
               iterator.remove();
            }
         }
      }
   }

   private static void beginBeam(ServerLevel level, LivingEntity entity, ManasSkillInstance instance, int mode, int heldTicks) {
      int maxHold = (Integer)cfg().avatarBeamMaxHoldTicks.get();
      int clamped = Math.max((Integer)cfg().avatarBeamArmReleaseTicks.get(), Math.min(heldTicks, maxHold));
      float charge = (float)clamped / maxHold;
      int duration = (Integer)cfg().avatarBeamMinDurationTicks.get()
         + Math.round(((Integer)cfg().avatarBeamMaxDurationTicks.get() - (Integer)cfg().avatarBeamMinDurationTicks.get()) * charge);
      float damageMultiplier = (float)((Double)cfg().avatarBeamDamageMultiplierBase.get() + charge * (Double)cfg().avatarBeamDamageMultiplierBonus.get());
      float radiusMultiplier = (float)((Double)cfg().avatarBeamRadiusMultiplierBase.get() + charge * (Double)cfg().avatarBeamRadiusMultiplierBonus.get());
      Vec3 start = getBeamStart(entity);
      Vec3 end = getAimEnd(entity, start);
      int seed = entity.getRandom().nextInt();
      STATES.put(entity.getUUID(), new AsmodayAvatarBeam.BeamState(entity.getUUID(), start, end, seed, duration, damageMultiplier, radiusMultiplier));
      sendBeamPayload(level, entity, start, end, duration, seed);
      applyDegradation(entity);
      damageBeam(
         level,
         entity,
         start,
         end,
         (Double)cfg().avatarBeamFinalRadius.get() * radiusMultiplier,
         (float)((Double)cfg().avatarBeamFinalDamage.get() * damageMultiplier),
         true
      );
      level.playSound(null, entity.getX(), entity.getY(), entity.getZ(), SoundEvents.END_PORTAL_SPAWN, SoundSource.PLAYERS, 9.5F, 0.2F);
      level.playSound(null, entity.getX(), entity.getY(), entity.getZ(), SoundEvents.WARDEN_ROAR, SoundSource.PLAYERS, 8.5F, 0.2F);
      level.playSound(null, entity.getX(), entity.getY(), entity.getZ(), SoundEvents.BEACON_ACTIVATE, SoundSource.PLAYERS, 7.5F, 0.28F);
      if (entity instanceof ServerPlayer player) {
         player.displayClientMessage(
            Component.literal("Destiny Beam released: " + Math.round(charge * 100.0F) + "% | " + duration / 20 + "s").withStyle(ChatFormatting.LIGHT_PURPLE),
            true
         );
      }

      entity.swing(InteractionHand.MAIN_HAND, true);
      instance.addMasteryPoint(entity);
      instance.setCoolDown((Integer)cfg().avatarBeamCooldownTicks.get(), mode);
      instance.markDirty();
   }

   private static void sendBeamPayload(ServerLevel level, LivingEntity entity, Vec3 start, Vec3 end, int duration, int seed) {
      LivingEntity target = getLookTarget(entity);
      int targetId = target == null ? -1 : target.getId();
      FeldwayAvatarBeamPayload payload = new FeldwayAvatarBeamPayload(
         entity.getId(), targetId, start.x, start.y, start.z, end.x, end.y, end.z, Math.max(12, duration), seed
      );
      AABB area = new AABB(start, end).inflate((Double)cfg().avatarBeamSendRange.get());

      for (ServerPlayer player : level.getEntitiesOfClass(ServerPlayer.class, area)) {
         NetworkManager.sendToPlayer(player, payload);
      }
   }

   private static void firePulse(ServerLevel level, LivingEntity owner, Vec3 start, Vec3 end, boolean finisher, float damageMultiplier, float radiusMultiplier) {
      applyDegradation(owner);
      double radius = finisher ? (Double)cfg().avatarBeamFinalRadius.get() : (Double)cfg().avatarBeamRadius.get() * radiusMultiplier;
      float damage = (float)(finisher ? (Double)cfg().avatarBeamFinalDamage.get() : (Double)cfg().avatarBeamTickDamage.get() * damageMultiplier);
      damageBeam(level, owner, start, end, radius, damage, finisher);
      level.playSound(null, start.x, start.y, start.z, SoundEvents.WARDEN_SONIC_BOOM, SoundSource.PLAYERS, finisher ? 10.0F : 8.0F, finisher ? 0.28F : 0.48F);
      level.playSound(
         null, start.x, start.y, start.z, SoundEvents.LIGHTNING_BOLT_THUNDER, SoundSource.PLAYERS, finisher ? 10.0F : 8.0F, finisher ? 0.48F : 0.85F
      );
      level.playSound(
         null, end.x, end.y, end.z, (SoundEvent)SoundEvents.GENERIC_EXPLODE.value(), SoundSource.PLAYERS, finisher ? 8.5F : 6.5F, finisher ? 0.52F : 0.82F
      );
   }

   private static void damageBeam(ServerLevel level, LivingEntity owner, Vec3 start, Vec3 end, double radius, float baseDamage, boolean finisher) {
      AABB box = new AABB(start, end).inflate(radius + 16.0);
      DamageSource source = source(level, owner);

      for (LivingEntity living : level.getEntitiesOfClass(LivingEntity.class, box, target -> isValidTarget(owner, target))) {
         Vec3 center = living.position().add(0.0, living.getBbHeight() * 0.5, 0.0);
         double distance = distanceToSegment(center, start, end);
         if (!(distance > radius + living.getBbWidth())) {
            double close = 1.0 - Math.min(1.0, distance / radius);
            double core = 1.0 - Math.min(1.0, distance / (Double)cfg().avatarBeamCoreRadius.get());
            float damage = baseDamage
               + (float)((Double)cfg().avatarBeamCoreDamage.get() * Math.max(0.0, core) * (finisher ? 1.35 : 0.85))
               + (float)(baseDamage * close * (finisher ? 1.25 : 0.95));
            living.invulnerableTime = 0;
            living.hurt(source, damage);
            living.invulnerableTime = 0;

            try {
               TensuraDamageHelper.directSpiritualHurt(
                  living, owner, source, (float)((Double)cfg().avatarBeamSpiritualDamage.get() * (0.65F + (float)close * 1.35F) * (finisher ? 2.0F : 1.0F))
               );
            } catch (Throwable var21) {
            }

            Vec3 push = end.subtract(start);
            if (push.lengthSqr() > 0.001) {
               living.setDeltaMovement(living.getDeltaMovement().add(push.normalize().scale(finisher ? 0.42 : 0.22)).add(0.0, finisher ? 0.14 : 0.07, 0.0));
               living.hurtMarked = true;
               living.fallDistance = 0.0F;
            }
         }
      }
   }

   private static Vec3 getBeamStart(LivingEntity owner) {
      Vec3 eye = owner.getEyePosition();
      Vec3 look = owner.getLookAngle();
      if (look.lengthSqr() < 0.001) {
         look = new Vec3(0.0, 0.0, 1.0);
      }

      return eye.add(look.normalize().scale(4.0)).add(0.0, 1.25, 0.0);
   }

   private static Vec3 getAimEnd(LivingEntity owner, Vec3 start) {
      Vec3 look = owner.getLookAngle();
      if (look.lengthSqr() < 0.001) {
         look = new Vec3(0.0, 0.0, 1.0);
      }

      return start.add(look.normalize().scale((Double)cfg().avatarBeamRange.get()));
   }

   private static LivingEntity getLookTarget(LivingEntity entity) {
      Vec3 eye = entity.getEyePosition();
      Vec3 look = entity.getLookAngle();
      if (look.lengthSqr() < 0.001) {
         look = new Vec3(0.0, 0.0, 1.0);
      }

      look = look.normalize();
      Vec3 end = eye.add(look.scale((Double)cfg().avatarBeamTargetRange.get()));
      AABB area = entity.getBoundingBox().expandTowards(look.scale((Double)cfg().avatarBeamTargetRange.get())).inflate(5.0);
      LivingEntity best = null;
      double bestDistance = (Double)cfg().avatarBeamTargetRange.get();

      for (LivingEntity living : entity.level().getEntitiesOfClass(LivingEntity.class, area, target -> isValidTarget(entity, target))) {
         Optional<Vec3> optional = living.getBoundingBox().inflate(1.4).clip(eye, end);
         if (!optional.isEmpty()) {
            double distance = eye.distanceTo(optional.get());
            if (distance < bestDistance) {
               bestDistance = distance;
               best = living;
            }
         }
      }

      return best;
   }

   private static void lookAt(LivingEntity entity, Vec3 pos) {
      Vec3 eye = entity.getEyePosition();
      double dx = pos.x - eye.x;
      double dy = pos.y - eye.y;
      double dz = pos.z - eye.z;
      double horizontal = Math.sqrt(dx * dx + dz * dz);
      if (!(horizontal < 1.0E-4)) {
         float yaw = (float)(Mth.atan2(dz, dx) * 180.0F / (float)Math.PI) - 90.0F;
         float pitch = (float)(-(Mth.atan2(dy, horizontal) * 180.0F / (float)Math.PI));
         entity.setYRot(yaw);
         entity.setXRot(pitch);
         entity.yBodyRot = yaw;
         entity.yHeadRot = yaw;
         entity.yRotO = yaw;
         entity.xRotO = pitch;
      }
   }

   private static boolean isValidTarget(LivingEntity owner, LivingEntity target) {
      if (target != owner && target.isAlive() && !target.isSpectator()) {
         if (target instanceof Player player && (player.isCreative() || player.isSpectator())) {
            return false;
         } else {
            return target.isAlliedTo(owner) || owner.isAlliedTo(target)
               ? false
               : !(target instanceof OwnableEntity ownable && ownable.getOwnerUUID() != null && ownable.getOwnerUUID().equals(owner.getUUID()));
         }
      } else {
         return false;
      }
   }

   private static LivingEntity getLiving(ServerLevel level, UUID id) {
      if (id == null) {
         return null;
      } else {
         return level.getEntity(id) instanceof LivingEntity living ? living : null;
      }
   }

   private static DamageSource source(ServerLevel level, LivingEntity owner) {
      return TensuraDamageTypes.getEntityDamageSource(level, TensuraDamageTypes.SPACE_ELEMENTAL, owner);
   }

   private static void applyDegradation(LivingEntity entity) {
      AttributeHelper.addPermanentAttributeIfHigher(
         entity, TensuraAttributes.RESISTANCE_DEGRADATION, ASMODAY_BEAM_DEGRADATION, (Double)cfg().avatarBeamResistanceDegradation.get(), Operation.ADD_VALUE
      );
      AttributeHelper.addPermanentAttributeIfHigher(
         entity,
         TensuraAttributes.SPACE_RESIST_DEGRADATION,
         ASMODAY_BEAM_DEGRADATION,
         (Double)cfg().avatarBeamSpaceResistanceDegradation.get(),
         Operation.ADD_VALUE
      );
   }

   private static void removeDegradation(LivingEntity entity) {
      AttributeHelper.removeAttribute(entity, TensuraAttributes.RESISTANCE_DEGRADATION, ASMODAY_BEAM_DEGRADATION);
      AttributeHelper.removeAttribute(entity, TensuraAttributes.SPACE_RESIST_DEGRADATION, ASMODAY_BEAM_DEGRADATION);
   }

   private static void clearTags(ManasSkillInstance instance) {
      CompoundTag tag = instance.getOrCreateTag();
      tag.remove("AsmodayBeamHolding");
      tag.remove("AsmodayBeamHeldTicks");
      tag.remove("AsmodayBeamReleaseArmed");
      instance.markDirty();
   }

   private static double distanceToSegment(Vec3 point, Vec3 start, Vec3 end) {
      Vec3 line = end.subtract(start);
      double len = line.lengthSqr();
      if (len <= 1.0E-6) {
         return point.distanceTo(start);
      }

      double t = point.subtract(start).dot(line) / len;
      t = Math.max(0.0, Math.min(1.0, t));
      Vec3 closest = start.add(line.scale(t));
      return point.distanceTo(closest);
   }

   private static MoreSkillsConfigs.AsmodayConfig cfg() {
      return MoreSkillsConfigs.GRAND.asmoday;
   }

   private static final class BeamState {
      private final UUID owner;
      private Vec3 start;
      private Vec3 end;
      private final int seed;
      private final int duration;
      private final float damageMultiplier;
      private final float radiusMultiplier;
      private int age;

      private BeamState(UUID owner, Vec3 start, Vec3 end, int seed, int duration, float damageMultiplier, float radiusMultiplier) {
         this.owner = owner;
         this.start = start;
         this.end = end;
         this.seed = seed;
         this.duration = duration;
         this.damageMultiplier = damageMultiplier;
         this.radiusMultiplier = radiusMultiplier;
      }
   }
}
