package com.mortal_cultivation.entity;

import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.network.PacketDistributor;

public final class QilinEffects {
   private QilinEffects() {
   }

   public static void sweep(AzureMossQilinEntity boss) {
      if (boss.level() instanceof ServerLevel) {
         send(boss, 3, boss.position());
         sound(boss, SoundEvents.PLAYER_ATTACK_SWEEP, 1.5F, 0.65F);
      }
   }

   public static void stomp(AzureMossQilinEntity boss) {
      if (boss.level() instanceof ServerLevel level) {
         Vec3 var3 = boss.getStompOrigin();
         send(boss, 0, var3);
         level.sendParticles(
            new BlockParticleOption(ParticleTypes.BLOCK, Blocks.AZALEA_LEAVES.defaultBlockState()), var3.x, var3.y + 0.3, var3.z, 6, 1.8, 0.15, 1.8, 0.06
         );
         sound(boss, SoundEvents.WARDEN_ATTACK_IMPACT, 2.0F, 0.65F);
         sound(boss, SoundEvents.AZALEA_LEAVES_BREAK, 1.4F, 0.6F);
      }
   }

   public static void rootWarning(AzureMossQilinEntity boss, Vec3 target, int age) {
      if (boss.level() instanceof ServerLevel) {
         if (age == 1) {
            send(boss, 2, target);
            sound(boss, SoundEvents.ROOTS_PLACE, 1.0F, 0.6F);
         }
      }
   }

   public static void roots(AzureMossQilinEntity boss, Vec3 target) {
      if (boss.level() instanceof ServerLevel level) {
         send(boss, 1, target);
         level.sendParticles(
            new BlockParticleOption(ParticleTypes.BLOCK, Blocks.MANGROVE_ROOTS.defaultBlockState()),
            target.x,
            target.y + 0.15,
            target.z,
            4,
            0.85,
            0.1,
            0.85,
            0.04
         );
         level.playSound(null, target.x, target.y, target.z, SoundEvents.MANGROVE_ROOTS_PLACE, SoundSource.HOSTILE, 1.7F, 0.55F);
      }
   }

   public static void chargeWarning(AzureMossQilinEntity boss, int age) {
      if (boss.level() instanceof ServerLevel level) {
         if (age == 1) {
            send(boss, 4, boss.position());
            sound(boss, SoundEvents.GOAT_PREPARE_RAM, 1.5F, 0.6F);
         }

         if (age == 12 || age == 20) {
            BlockState state = level.getBlockState(boss.blockPosition().below());
            if (!state.isAir()) {
               level.sendParticles(new BlockParticleOption(ParticleTypes.BLOCK, state), boss.getX(), boss.getY() + 0.1, boss.getZ(), 2, 0.4, 0.03, 0.4, 0.04);
            }
         }
      }
   }

   public static void chargeTrail(AzureMossQilinEntity boss) {
      if (boss.level() instanceof ServerLevel && boss.tickCount % 3 == 0) {
         send(boss, 5, boss.position());
      }
   }

   public static void enrage(AzureMossQilinEntity boss, int age) {
      if (boss.level() instanceof ServerLevel && age == 1) {
         send(boss, 6, boss.position());
         sound(boss, SoundEvents.RAVAGER_ROAR, 2.0F, 0.7F);
      }
   }

   private static void send(AzureMossQilinEntity boss, int effect, Vec3 center) {
      PacketDistributor.sendToPlayersTrackingEntity(
         boss,
         new QilinEffectPayload(
            boss.level().dimension().location(), effect, center, boss.level().getGameTime(), boss.getRandom().nextInt(), boss.getYRot(), boss.getId()
         ),
         new CustomPacketPayload[0]
      );
   }

   private static void sound(AzureMossQilinEntity boss, SoundEvent sound, float volume, float pitch) {
      boss.level().playSound(null, boss.getX(), boss.getY(), boss.getZ(), sound, SoundSource.HOSTILE, volume, pitch);
   }
}
