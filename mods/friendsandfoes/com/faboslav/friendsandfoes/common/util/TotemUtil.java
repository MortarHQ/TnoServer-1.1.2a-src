package com.faboslav.friendsandfoes.common.util;

import com.faboslav.friendsandfoes.common.entity.PlayerIllusionEntity;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesEntityTypes;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesSoundEvents;
import com.faboslav.friendsandfoes.common.util.particle.ParticleSpawner;
import com.faboslav.friendsandfoes.common.versions.VersionedMobEffects;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public final class TotemUtil {
   private static final int MAX_ILLUSIONS_COUNT = 9;
   private static final int ILLUSION_LIFETIME_TICKS = 600;
   private static final int NEGATIVE_EFFECT_TICKS = 400;
   public static final int POSITIVE_EFFECT_TICKS = 200;
   private static final TargetingConditions FREEZE_TARGET_PREDICATE = TargetingConditions.forNonCombat()
      .ignoreInvisibilityTesting()
      .ignoreLineOfSight()
      .selector(livingEntity -> !(livingEntity instanceof Player) || !((Player)livingEntity).isCreative());
   private static final TargetingConditions ATTACK_TARGET_PREDICATE = TargetingConditions.forNonCombat().ignoreInvisibilityTesting().ignoreLineOfSight();

   public static void playActivateAnimation(ItemStack itemStack, Entity entity, ParticleType<?> particleType) {
      Minecraft minecraftClient = Minecraft.getInstance();
      minecraftClient.particleEngine.createTrackingEmitter(entity, (ParticleOptions)particleType, 30);
      ClientLevel clientWorld = minecraftClient.level;
      if (clientWorld != null) {
         clientWorld.playLocalSound(entity.getX(), entity.getY(), entity.getZ(), SoundEvents.TOTEM_USE, entity.getSoundSource(), 1.0F, 1.0F, false);
      }

      if (entity == minecraftClient.player) {
         minecraftClient.gameRenderer.displayItemActivation(itemStack);
      }
   }

   public static void freezeEntities(Player player, ServerLevel level) {
      List<LivingEntity> nearbyEntities = level.getEntitiesOfClass(
         LivingEntity.class, player.getBoundingBox().inflate(9.0), livingEntity -> FREEZE_TARGET_PREDICATE.test(player, livingEntity)
      );
      nearbyEntities.forEach(nearbyEntity -> {
         nearbyEntity.setTicksFrozen(400);
         nearbyEntity.addEffect(new MobEffectInstance(VersionedMobEffects.MOVEMENT_SLOWNESS, 400, 1));
      });
   }

   public static void createIllusions(Player player, ServerLevel level) {
      player.playSound((SoundEvent)FriendsAndFoesSoundEvents.ENTITY_PLAYER_MIRROR_MOVE.get(), 1.0F, player.getVoicePitch());
      Vec3 illusionerPosition = player.position();
      float slice = (float) Math.PI * 2.0F / 9.0F;
      int radius = 9;
      ArrayList<LivingEntity> createdPlayerIllusions = new ArrayList<>();

      for (int point = 0; point < 9; point++) {
         float angle = slice * point;
         int x = (int)(illusionerPosition.x() + radius * Mth.cos(angle));
         int y = (int)illusionerPosition.y();
         int z = (int)(illusionerPosition.z() + radius * Mth.sin(angle));
         LivingEntity createdPlayerIllusion = createIllusion(player, level, x, y, z);
         if (createdPlayerIllusion != null) {
            createdPlayerIllusions.add(createdPlayerIllusion);
         }
      }

      List<Mob> nearbyEntities = level.getEntitiesOfClass(
         Mob.class, player.getBoundingBox().inflate(18.0), mobEntity -> ATTACK_TARGET_PREDICATE.test(player, mobEntity)
      );
      nearbyEntities.forEach(nearbyEntity -> {
         if (nearbyEntity.getTarget() == player) {
            if (!createdPlayerIllusions.isEmpty()) {
               nearbyEntity.setAggressive(true);
               nearbyEntity.setLastHurtByMob(createdPlayerIllusions.get(player.getRandom().nextInt(createdPlayerIllusions.size())));
               nearbyEntity.setLastHurtMob((Entity)createdPlayerIllusions.get(player.getRandom().nextInt(createdPlayerIllusions.size())));
            }

            nearbyEntity.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 400, 1));
         }
      });
      if (!createdPlayerIllusions.isEmpty()) {
         LivingEntity illusionToReplace = createdPlayerIllusions.get(player.getRandom().nextInt(createdPlayerIllusions.size()));
         boolean teleportResult = tryToTeleport(player, level, illusionToReplace.getBlockX(), illusionToReplace.getBlockY(), illusionToReplace.getBlockZ());
         if (teleportResult) {
            spawnCloudParticles(player, level);
         }

         illusionToReplace.discard();
      }

      player.addEffect(new MobEffectInstance(MobEffects.INVISIBILITY, 200));
   }

   @Nullable
   private static LivingEntity createIllusion(Player player, ServerLevel serverLevel, int x, int y, int z) {
      PlayerIllusionEntity playerIllusion = (PlayerIllusionEntity)((EntityType)FriendsAndFoesEntityTypes.PLAYER_ILLUSION.get()).create(serverLevel);
      if (playerIllusion == null) {
         return null;
      }

      playerIllusion.prevCapeX = player.xCloakO;
      playerIllusion.prevCapeY = player.yCloakO;
      playerIllusion.prevCapeZ = player.zCloakO;
      playerIllusion.capeX = player.xCloak;
      playerIllusion.capeY = player.yCloak;
      playerIllusion.capeZ = player.zCloak;
      playerIllusion.prevStrideDistance = player.oBob;
      playerIllusion.strideDistance = player.bob;
      playerIllusion.setItemSlot(EquipmentSlot.MAINHAND, player.getMainHandItem().copy());
      playerIllusion.setItemSlot(EquipmentSlot.OFFHAND, player.getOffhandItem().copy());

      for (EquipmentSlot slot : EquipmentSlot.values()) {
         ItemStack item = player.getItemBySlot(slot);
         if (!item.isEmpty()) {
            playerIllusion.setItemSlot(slot, item.copy());
         }
      }

      playerIllusion.setHealth(player.getMaxHealth());
      playerIllusion.copyPosition(player);
      float randomYaw = 360.0F * player.getRandom().nextFloat();
      playerIllusion.yRotO = randomYaw;
      playerIllusion.setYRot(randomYaw);
      playerIllusion.yBodyRotO = randomYaw;
      playerIllusion.setYBodyRot(randomYaw);
      playerIllusion.yHeadRotO = randomYaw;
      playerIllusion.setYHeadRot(randomYaw);
      playerIllusion.setPlayerUuid(player.getUUID());
      playerIllusion.setPlayer(player);
      playerIllusion.setTicksUntilDespawn(600);
      boolean teleportResult = PlayerIllusionUtil.tryToTeleport(serverLevel, playerIllusion, x, y, z);
      if (teleportResult) {
         serverLevel.addFreshEntity(playerIllusion);
         ParticleSpawner.spawnParticles(playerIllusion, ParticleTypes.CLOUD, 16, 0.1);
      }

      return playerIllusion;
   }

   private static boolean tryToTeleport(Player player, ServerLevel level, int x, int y, int z) {
      y -= 8;
      int worldBottomY = level.getMinBuildHeight();
      double bottomY = Math.max(y, worldBottomY);
      double topY = Math.min(bottomY + 16.0, level.getLogicalHeight() - 1);

      for (int i = 0; i < 16; i++) {
         y = (int)Mth.clamp(y + 1, bottomY, topY);
         boolean teleportResult = player.randomTeleport(x, y, z, false);
         if (teleportResult) {
            return true;
         }
      }

      return false;
   }

   private static void spawnCloudParticles(Player player, ServerLevel level) {
      spawnParticles(player, level, ParticleTypes.CLOUD, 16);
   }

   private static void spawnParticles(Player player, ServerLevel level, SimpleParticleType particleType, int amount) {
      for (int i = 0; i < amount; i++) {
         level.sendParticles(particleType, player.getRandomX(0.5), player.getRandomY() + 0.5, player.getRandomZ(0.5), 1, 0.0, 0.0, 0.0, 0.0);
      }
   }
}
