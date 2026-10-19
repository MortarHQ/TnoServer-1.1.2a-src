package com.github.wal_bos.moreskills.handler.phainon;

import com.github.wal_bos.moreskills.net.msg.PhainonAcquirementVfxPayload;
import dev.architectury.networking.NetworkManager;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;

public final class PhainonAcquirementAnimation {
   private static final String ACTIVE = "PhainonAcquirementAnimation";
   private static final String TICKS = "PhainonAcquirementTicks";
   private static final String SNAPPED = "PhainonAcquirementSnapped";
   private static final String OLD_GRAVITY = "PhainonAcquirementOldNoGravity";
   private static final String OLD_GLOWING = "PhainonAcquirementOldGlowing";
   private static final String START_X = "PhainonAcquirementStartX";
   private static final String START_Y = "PhainonAcquirementStartY";
   private static final String START_Z = "PhainonAcquirementStartZ";
   private static final String HOLD_X = "PhainonAcquirementHoldX";
   private static final String HOLD_Y = "PhainonAcquirementHoldY";
   private static final String HOLD_Z = "PhainonAcquirementHoldZ";
   private static final int DURATION = 620;
   private static final int SNAP_UP_TICK = 12;
   private static final double ASCENSION_HEIGHT = 20.0;

   private PhainonAcquirementAnimation() {
   }

   public static void start(LivingEntity entity, ManasSkillInstance instance) {
      if (!entity.level().isClientSide()) {
         CompoundTag tag = instance.getOrCreateTag();
         if (!tag.getBoolean("PhainonAcquirementAnimation")) {
            double holdX = entity.getX();
            double holdY = entity.getY() + 20.0;
            double holdZ = entity.getZ();
            tag.putBoolean("PhainonAcquirementAnimation", true);
            tag.putBoolean("PhainonAcquirementSnapped", false);
            tag.putInt("PhainonAcquirementTicks", 0);
            tag.putBoolean("PhainonAcquirementOldNoGravity", entity.isNoGravity());
            tag.putBoolean("PhainonAcquirementOldGlowing", entity.isCurrentlyGlowing());
            tag.putDouble("PhainonAcquirementStartX", entity.getX());
            tag.putDouble("PhainonAcquirementStartY", entity.getY());
            tag.putDouble("PhainonAcquirementStartZ", entity.getZ());
            tag.putDouble("PhainonAcquirementHoldX", holdX);
            tag.putDouble("PhainonAcquirementHoldY", holdY);
            tag.putDouble("PhainonAcquirementHoldZ", holdZ);
            entity.setNoGravity(true);
            entity.setGlowingTag(true);
            entity.setDeltaMovement(0.0, 0.0, 0.0);
            entity.resetFallDistance();
            entity.invulnerableTime = 700;
            entity.hurtMarked = true;
            entity.hasImpulse = true;
            send(entity, true);
            entity.level().playSound(null, entity.getX(), entity.getY(), entity.getZ(), SoundEvents.BEACON_ACTIVATE, SoundSource.PLAYERS, 5.0F, 0.18F);
            entity.level().playSound(null, entity.getX(), entity.getY(), entity.getZ(), SoundEvents.END_PORTAL_SPAWN, SoundSource.PLAYERS, 4.5F, 0.42F);
            entity.level().playSound(null, entity.getX(), entity.getY(), entity.getZ(), SoundEvents.RESPAWN_ANCHOR_CHARGE, SoundSource.PLAYERS, 4.0F, 0.28F);
            entity.level().playSound(null, entity.getX(), entity.getY(), entity.getZ(), SoundEvents.WITHER_AMBIENT, SoundSource.PLAYERS, 3.0F, 0.2F);
         }
      }
   }

   public static boolean isActive(ManasSkillInstance instance) {
      return instance.getOrCreateTag().getBoolean("PhainonAcquirementAnimation");
   }

   public static void tick(LivingEntity entity, ManasSkillInstance instance) {
      if (!entity.level().isClientSide()) {
         CompoundTag tag = instance.getOrCreateTag();
         if (tag.getBoolean("PhainonAcquirementAnimation")) {
            int ticks = tag.getInt("PhainonAcquirementTicks");
            if (ticks < 620 && entity.isAlive()) {
               double holdX = tag.getDouble("PhainonAcquirementHoldX");
               double holdY = tag.getDouble("PhainonAcquirementHoldY");
               double holdZ = tag.getDouble("PhainonAcquirementHoldZ");
               entity.setNoGravity(true);
               entity.setGlowingTag(true);
               entity.invulnerableTime = Math.max(entity.invulnerableTime, 60);
               entity.resetFallDistance();
               if (!tag.getBoolean("PhainonAcquirementSnapped") && ticks >= 12) {
                  teleport(entity, holdX, holdY, holdZ);
                  tag.putBoolean("PhainonAcquirementSnapped", true);
                  entity.level().playSound(null, holdX, holdY, holdZ, SoundEvents.ENDERMAN_TELEPORT, SoundSource.PLAYERS, 5.0F, 0.18F);
                  entity.level().playSound(null, holdX, holdY, holdZ, (SoundEvent)SoundEvents.GENERIC_EXPLODE.value(), SoundSource.PLAYERS, 4.0F, 0.34F);
                  entity.level().playSound(null, holdX, holdY, holdZ, SoundEvents.BEACON_POWER_SELECT, SoundSource.PLAYERS, 5.0F, 0.28F);
               }

               if (tag.getBoolean("PhainonAcquirementSnapped")) {
                  lock(entity, holdX, holdY, holdZ);
               } else {
                  entity.setDeltaMovement(0.0, 0.72, 0.0);
               }

               if (ticks == 70) {
                  entity.level().playSound(null, holdX, holdY, holdZ, SoundEvents.WITHER_SPAWN, SoundSource.PLAYERS, 5.0F, 0.16F);
               }

               if (ticks == 190) {
                  entity.level().playSound(null, holdX, holdY, holdZ, (SoundEvent)SoundEvents.RESPAWN_ANCHOR_DEPLETE.value(), SoundSource.PLAYERS, 5.0F, 0.18F);
                  entity.level().playSound(null, holdX, holdY, holdZ, SoundEvents.END_PORTAL_SPAWN, SoundSource.PLAYERS, 4.0F, 0.55F);
               }

               if (ticks == 325) {
                  entity.level().playSound(null, holdX, holdY, holdZ, (SoundEvent)SoundEvents.GENERIC_EXPLODE.value(), SoundSource.PLAYERS, 5.0F, 0.13F);
                  entity.level().playSound(null, holdX, holdY, holdZ, SoundEvents.TOTEM_USE, SoundSource.PLAYERS, 5.0F, 0.42F);
                  entity.level().playSound(null, holdX, holdY, holdZ, SoundEvents.BEACON_ACTIVATE, SoundSource.PLAYERS, 5.0F, 0.32F);
               }

               if (ticks == 470) {
                  entity.level().playSound(null, holdX, holdY, holdZ, SoundEvents.LIGHTNING_BOLT_THUNDER, SoundSource.PLAYERS, 5.0F, 0.55F);
               }

               tag.putInt("PhainonAcquirementTicks", ticks + 1);
            } else {
               stop(entity, instance, entity.isAlive());
            }
         }
      }
   }

   public static void stop(LivingEntity entity, ManasSkillInstance instance, boolean finish) {
      CompoundTag tag = instance.getOrCreateTag();
      if (tag.getBoolean("PhainonAcquirementAnimation")) {
         boolean oldGravity = tag.getBoolean("PhainonAcquirementOldNoGravity");
         boolean oldGlowing = tag.getBoolean("PhainonAcquirementOldGlowing");
         tag.remove("PhainonAcquirementAnimation");
         tag.remove("PhainonAcquirementTicks");
         tag.remove("PhainonAcquirementSnapped");
         tag.remove("PhainonAcquirementOldNoGravity");
         tag.remove("PhainonAcquirementOldGlowing");
         tag.remove("PhainonAcquirementStartX");
         tag.remove("PhainonAcquirementStartY");
         tag.remove("PhainonAcquirementStartZ");
         tag.remove("PhainonAcquirementHoldX");
         tag.remove("PhainonAcquirementHoldY");
         tag.remove("PhainonAcquirementHoldZ");
         entity.setNoGravity(oldGravity);
         entity.setGlowingTag(oldGlowing);
         entity.setDeltaMovement(0.0, 0.0, 0.0);
         entity.resetFallDistance();
         entity.hurtMarked = true;
         entity.hasImpulse = true;
         entity.invulnerableTime = Math.max(entity.invulnerableTime, 30);
         send(entity, false);
         if (finish) {
            entity.level().playSound(null, entity.getX(), entity.getY(), entity.getZ(), SoundEvents.BEACON_DEACTIVATE, SoundSource.PLAYERS, 3.5F, 0.7F);
         }
      }
   }

   private static void lock(LivingEntity entity, double x, double y, double z) {
      entity.setDeltaMovement(0.0, 0.0, 0.0);
      double dx = entity.getX() - x;
      double dy = entity.getY() - y;
      double dz = entity.getZ() - z;
      if (dx * dx + dy * dy + dz * dz > 0.0036) {
         teleport(entity, x, y, z);
      } else {
         entity.hurtMarked = true;
         entity.hasImpulse = true;
      }

      entity.resetFallDistance();
   }

   private static void teleport(LivingEntity entity, double x, double y, double z) {
      if (entity instanceof ServerPlayer player) {
         player.connection.teleport(x, y, z, player.getYRot(), player.getXRot());
      } else {
         entity.teleportTo(x, y, z);
      }

      entity.setDeltaMovement(0.0, 0.0, 0.0);
      entity.resetFallDistance();
      entity.hurtMarked = true;
      entity.hasImpulse = true;
   }

   private static void send(LivingEntity entity, boolean start) {
      if (entity instanceof ServerPlayer self) {
         PhainonAcquirementVfxPayload payload = new PhainonAcquirementVfxPayload(entity.getId(), entity.getUUID(), start);

         for (ServerPlayer player : self.serverLevel().players()) {
            if (player.distanceToSqr(entity) <= 9216.0 || player == self) {
               NetworkManager.sendToPlayer(player, payload);
            }
         }
      }
   }
}
