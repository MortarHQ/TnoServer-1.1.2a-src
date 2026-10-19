package com.github.wal_bos.moreskills.handler.ronova;

import com.github.wal_bos.moreskills.ability.skill.shades.ronova.RonovaConfigAccess;
import com.github.wal_bos.moreskills.configs.MoreSkillsConfigs;
import com.github.wal_bos.moreskills.net.msg.RonovaFinalityVfxPayload;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public final class RonovaWorldEraseHandler {
   private RonovaWorldEraseHandler() {
   }

   public static void finality(ManasSkillInstance instance, LivingEntity caster) {
      MoreSkillsConfigs.RonovaConfig cfg = RonovaConfigAccess.cfg();
      if (caster.level() instanceof ServerLevel level) {
         RonovaFinalityVfxPayload.send(level, caster.position(), ((Double)cfg.finalityRadius.get()).floatValue(), false);
         int var6 = burst(
            level,
            caster,
            caster.position(),
            (Double)cfg.finalityRadius.get(),
            (Boolean)cfg.finalityInstantKill.get(),
            (Double)cfg.finalityDamage.get(),
            true,
            (Boolean)cfg.finalityEraseBlocks.get()
         );
         level.sendParticles(
            ParticleTypes.SOUL_FIRE_FLAME,
            caster.getX(),
            caster.getY() + caster.getBbHeight() * 0.5,
            caster.getZ(),
            80,
            (Double)cfg.finalityRadius.get() * 0.12,
            1.2,
            (Double)cfg.finalityRadius.get() * 0.12,
            0.04
         );
         level.playSound(null, caster.getX(), caster.getY(), caster.getZ(), SoundEvents.WITHER_DEATH, SoundSource.PLAYERS, 2.5F, 0.45F);
         level.playSound(null, caster.getX(), caster.getY(), caster.getZ(), (SoundEvent)SoundEvents.GENERIC_EXPLODE.value(), SoundSource.PLAYERS, 2.0F, 0.55F);
         level.playSound(
            null, caster.getX(), caster.getY(), caster.getZ(), (SoundEvent)SoundEvents.RESPAWN_ANCHOR_DEPLETE.value(), SoundSource.PLAYERS, 2.0F, 0.45F
         );
         if (caster instanceof Player player) {
            player.displayClientMessage(
               Component.translatable("skill.tensuramoreskills.ronova.finality_cast", new Object[]{var6}).withStyle(ChatFormatting.DARK_RED), true
            );
         }

         instance.setCoolDown((Integer)cfg.finalityCooldown.get(), 4);
         instance.addMasteryPoint(caster);
      }
   }

   public static void trueDeathPassive(LivingEntity center, double radius, boolean blocks, boolean instantKill, double damage) {
      if (center.level() instanceof ServerLevel level) {
         RonovaFinalityVfxPayload.send(level, center.position(), (float)radius, true);
         burst(level, center, center.position(), radius, instantKill, damage, false, blocks);
         level.sendParticles(
            ParticleTypes.SOUL_FIRE_FLAME,
            center.getX(),
            center.getY() + center.getBbHeight() * 0.5,
            center.getZ(),
            60,
            radius * 0.12,
            1.2,
            radius * 0.12,
            0.04
         );
         level.playSound(null, center.getX(), center.getY(), center.getZ(), SoundEvents.WITHER_DEATH, SoundSource.PLAYERS, 2.0F, 0.35F);
         level.playSound(
            null, center.getX(), center.getY(), center.getZ(), (SoundEvent)SoundEvents.RESPAWN_ANCHOR_DEPLETE.value(), SoundSource.PLAYERS, 1.8F, 0.35F
         );
      }
   }

   public static void worldErase(LivingEntity center, double radius, boolean blocks) {
      if (center.level() instanceof ServerLevel level) {
         RonovaFinalityVfxPayload.send(level, center.position(), (float)radius, true);
         burst(level, center, center.position(), radius, true, Float.MAX_VALUE, false, blocks);
      }
   }

   private static int burst(
      ServerLevel level, LivingEntity source, Vec3 center, double radius, boolean instantKill, double damage, boolean includeSource, boolean blocks
   ) {
      AABB box = new AABB(center.x - radius, center.y - radius, center.z - radius, center.x + radius, center.y + radius, center.z + radius);
      int affected = 0;

      for (Entity entity : level.getEntities(source, box, entityx -> entityx.isAlive() && entityx.position().distanceToSqr(center) <= radius * radius)) {
         if (includeSource || entity != source) {
            if (entity instanceof LivingEntity living) {
               if (instantKill) {
                  eraseLiving(living);
               } else {
                  damageLiving(living, source, damage);
               }

               affected++;
            } else if (instantKill && (Boolean)RonovaConfigAccess.cfg().finalityDiscardEntities.get()) {
               entity.discard();
               affected++;
            } else if (entity instanceof ItemEntity item && (Boolean)RonovaConfigAccess.cfg().finalityBalanceDeleteDrops.get()) {
               item.discard();
               affected++;
            }
         }
      }

      if (includeSource) {
         if (instantKill) {
            eraseLiving(source);
         } else {
            damageLiving(source, source, damage);
         }

         affected++;
      }

      if (blocks) {
         eraseBlocks(level, center, radius);
      }

      return affected;
   }

   private static void eraseLiving(LivingEntity living) {
      living.invulnerableTime = 0;
      living.hurt(living.damageSources().fellOutOfWorld(), Float.MAX_VALUE);
      if (living.isAlive()) {
         living.setHealth(0.0F);
      }

      if (living.isAlive()) {
         living.kill();
      }

      if (!(living instanceof Player) && (Boolean)RonovaConfigAccess.cfg().finalityDiscardEntities.get()) {
         living.discard();
      }
   }

   private static void damageLiving(LivingEntity living, LivingEntity source, double damage) {
      living.invulnerableTime = 0;
      living.hurt(source.damageSources().magic(), Math.max(1.0F, (float)damage));
   }

   private static void eraseBlocks(ServerLevel level, Vec3 center, double radius) {
      int r = (int)Math.ceil(radius);
      BlockPos origin = BlockPos.containing(center);
      int changed = 0;
      int max = (Integer)RonovaConfigAccess.cfg().finalityBalanceMaxBlocksPerDeath.get();

      for (BlockPos pos : BlockPos.betweenClosed(origin.offset(-r, -r, -r), origin.offset(r, r, r))) {
         if (changed >= max) {
            return;
         }

         if (!(pos.distSqr(origin) > radius * radius) && level.isLoaded(pos) && !level.getBlockState(pos).isAir() && !level.getBlockState(pos).hasBlockEntity()
            )
          {
            level.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
            changed++;
         }
      }
   }
}
