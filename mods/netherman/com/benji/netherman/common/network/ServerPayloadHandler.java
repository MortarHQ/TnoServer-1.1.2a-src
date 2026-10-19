package com.benji.netherman.common.network;

import com.benji.netherman.QuotaManager;
import com.benji.netherman.common.entity.AzazelHumanEntity;
import com.benji.netherman.init.ModEffects;
import com.benji.netherman.init.ModItems;
import com.benji.netherman.init.ModSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BarrelBlockEntity;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.joml.Vector3f;

public class ServerPayloadHandler {
   public static void handleAzazelCutscene(AzazelCutscenePayload payload, IPayloadContext context) {
      context.enqueueWork(
         () -> {
            if (context.player() instanceof ServerPlayer player
               && player.level().getEntity(payload.entityId()) instanceof AzazelHumanEntity boss
               && (Integer)boss.getEntityData().get(AzazelHumanEntity.BOSS_STATE) == 3) {
               if (payload.actionId() == 0) {
                  ServerLevel currentLevel = (ServerLevel)boss.level();
                  ServerLevel respawnLevel = currentLevel.getServer().getLevel(player.getRespawnDimension());
                  if (respawnLevel == null) {
                     respawnLevel = currentLevel.getServer().overworld();
                  }

                  BlockPos respawnPos = player.getRespawnPosition();
                  float respawnAngle = player.getRespawnAngle();
                  if (respawnPos != null) {
                     player.teleportTo(respawnLevel, respawnPos.getX() + 0.5, respawnPos.getY() + 1.0, respawnPos.getZ() + 0.5, respawnAngle, 0.0F);
                  } else {
                     BlockPos sharedSpawn = respawnLevel.getSharedSpawnPos();
                     player.teleportTo(respawnLevel, sharedSpawn.getX() + 0.5, sharedSpawn.getY() + 1.0, sharedSpawn.getZ() + 0.5, respawnAngle, 0.0F);
                  }

                  respawnLevel.playSound(null, player.blockPosition(), (SoundEvent)ModSounds.BELL_BEAST_LAUGH.get(), SoundSource.PLAYERS, 1.5F, 1.0F);
                  DustParticleOptions redMagic = new DustParticleOptions(new Vector3f(1.0F, 0.0F, 0.0F), 1.5F);
                  respawnLevel.sendParticles(player, redMagic, true, player.getX(), player.getY() + 1.0, player.getZ(), 80, 0.8, 1.0, 0.8, 0.1);
                  BlockPos barrelPos = player.blockPosition();

                  for (int[] offset : new int[][]{{1, 0, 0}, {-1, 0, 0}, {0, 0, 1}, {0, 0, -1}, {0, 1, 0}}) {
                     BlockPos checkPos = player.blockPosition().offset(offset[0], offset[1], offset[2]);
                     if (respawnLevel.getBlockState(checkPos).canBeReplaced()) {
                        barrelPos = checkPos;
                        break;
                     }
                  }

                  respawnLevel.setBlockAndUpdate(barrelPos, Blocks.BARREL.defaultBlockState());
                  respawnLevel.playSound(null, barrelPos, SoundEvents.TOTEM_USE, SoundSource.BLOCKS, 1.0F, 1.5F);
                  respawnLevel.sendParticles(
                     player,
                     ParticleTypes.TOTEM_OF_UNDYING,
                     true,
                     barrelPos.getX() + 0.5,
                     barrelPos.getY() + 1.0,
                     barrelPos.getZ() + 0.5,
                     60,
                     0.4,
                     0.5,
                     0.4,
                     0.2
                  );
                  if (respawnLevel.getBlockEntity(barrelPos) instanceof BarrelBlockEntity barrel) {
                     barrel.setItem(0, new ItemStack((ItemLike)ModItems.AZAZEL_HELMET.get(), 1));
                     barrel.setItem(1, new ItemStack((ItemLike)ModItems.AZAZEL_CHESTPLATE.get(), 1));
                     barrel.setItem(2, new ItemStack((ItemLike)ModItems.AZAZEL_LEGGINGS.get(), 1));
                     barrel.setItem(3, new ItemStack((ItemLike)ModItems.AZAZEL_BOOTS.get(), 1));
                     barrel.setItem(4, new ItemStack((ItemLike)ModItems.QUOTA.get(), 1));
                     barrel.setItem(5, new ItemStack((ItemLike)ModItems.FAITH_PART.get(), 5));
                     barrel.setItem(6, new ItemStack((ItemLike)ModItems.FAITH_ESSENCE.get(), 1));
                  }

                  player.getPersistentData().putBoolean("AzazelCultist", true);
                  QuotaManager.generateNewQuota(player);
                  boss.discard();
               } else if (payload.actionId() == 1) {
                  boss.getEntityData().set(AzazelHumanEntity.BOSS_STATE, 4);
                  boss.getEntityData().set(AzazelHumanEntity.DIALOGUE_TICK, 0);
                  boss.playSound((SoundEvent)ModSounds.LAUGH.get(), 2.0F, 1.0F);
                  Vec3 forward = Vec3.directionFromRotation(0.0F, boss.getYRot()).normalize();
                  boss.setPos(boss.getX() + forward.x * 2.0, boss.getY(), boss.getZ() + forward.z * 2.0);
                  if (boss.level() instanceof ServerLevel sl) {
                     for (ServerPlayer p : sl.getEntitiesOfClass(ServerPlayer.class, boss.getBoundingBox().inflate(64.0))) {
                        p.addEffect(new MobEffectInstance(ModEffects.PRAEMIUM, -1, 0, false, false, true));
                     }
                  }
               }
            }
         }
      );
   }
}
