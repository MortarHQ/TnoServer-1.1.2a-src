package com.iafenvoy.iceandfire.entity.util.dragon;

import com.iafenvoy.iceandfire.config.IafCommonConfig;
import com.iafenvoy.iceandfire.entity.DragonBaseEntity;
import com.iafenvoy.iceandfire.entity.util.BlockLaunchExplosion;
import com.iafenvoy.iceandfire.event.IafEvents;
import com.iafenvoy.iceandfire.item.block.CharedPathBlock;
import com.iafenvoy.iceandfire.item.block.FallingReturningStateBlock;
import com.iafenvoy.iceandfire.item.block.ReturningStateBlock;
import com.iafenvoy.iceandfire.item.block.entity.DragonForgeInputBlockEntity;
import com.iafenvoy.iceandfire.item.block.util.DragonProof;
import com.iafenvoy.iceandfire.registry.IafBlocks;
import com.iafenvoy.iceandfire.registry.IafDamageTypes;
import com.iafenvoy.iceandfire.registry.IafDragonTypes;
import com.iafenvoy.iceandfire.registry.IafStatusEffects;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.Explosion.BlockInteraction;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SpreadingSnowyDirtBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

public class IafDragonDestructionManager {
   public static void destroyAreaBreath(Level level, BlockPos center, DragonBaseEntity dragon) {
      if (!((IafEvents.DragonFireDamageWorld)IafEvents.ON_DRAGON_DAMAGE_BLOCK.invoker()).onDamageBlock(dragon, center.getX(), center.getY(), center.getZ())) {
         int statusDuration;
         float damageScale;
         if (dragon.dragonType == IafDragonTypes.FIRE) {
            statusDuration = 5 + dragon.getDragonStage() * 5;
            damageScale = ((Double)IafCommonConfig.INSTANCE.dragon.attackDamageFire.getValue()).floatValue();
         } else if (dragon.dragonType == IafDragonTypes.ICE) {
            statusDuration = 50 * dragon.getDragonStage();
            damageScale = ((Double)IafCommonConfig.INSTANCE.dragon.attackDamageIce.getValue()).floatValue();
         } else {
            if (dragon.dragonType != IafDragonTypes.LIGHTNING) {
               return;
            }

            statusDuration = 3;
            damageScale = ((Double)IafCommonConfig.INSTANCE.dragon.attackDamageLightning.getValue()).floatValue();
         }

         double damageRadius = 3.5;
         boolean canBreakBlocks = level.getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING);
         if (dragon.getDragonStage() <= 3) {
            BlockPos.betweenClosedStream(center.offset(-1, -1, -1), center.offset(1, 1, 1)).forEach(position -> {
               if (level.getBlockEntity(position) instanceof DragonForgeInputBlockEntity forge) {
                  forge.onHitWithFlame();
               } else {
                  if (canBreakBlocks && DragonUtils.canGrief(dragon) && dragon.getRandom().nextBoolean()) {
                     attackBlock(level, dragon, position);
                  }
               }
            });
         } else {
            int radius = dragon.getDragonStage() == 4 ? 2 : 3;
            int x = radius + level.random.nextInt(1);
            int y = radius + level.random.nextInt(1);
            int z = radius + level.random.nextInt(1);
            float f = (x + y + z) * 0.333F + 0.5F;
            float ff = f * f;
            damageRadius = 2.5F + f * 1.2F;
            BlockPos.betweenClosedStream(center.offset(-x, -y, -z), center.offset(x, y, z))
               .forEach(
                  position -> {
                     if (level.getBlockEntity(position) instanceof DragonForgeInputBlockEntity forge) {
                        forge.onHitWithFlame();
                     } else {
                        if (canBreakBlocks
                           && center.distSqr(position) <= ff
                           && DragonUtils.canGrief(dragon)
                           && level.random.nextFloat() > (float)center.distSqr(position) / ff) {
                           attackBlock(level, dragon, position);
                        }
                     }
                  }
               );
         }

         DamageSource damageSource = getDamageSource(dragon);
         float stageDamage = dragon.getDragonStage() * damageScale;
         level.getEntitiesOfClass(
               LivingEntity.class,
               new AABB(
                  center.getX() - damageRadius,
                  center.getY() - damageRadius,
                  center.getZ() - damageRadius,
                  center.getX() + damageRadius,
                  center.getY() + damageRadius,
                  center.getZ() + damageRadius
               )
            )
            .forEach(target -> {
               if (!DragonUtils.onSameTeam(dragon, target) && !dragon.is(target) && dragon.hasLineOfSight(target)) {
                  target.hurt(damageSource, stageDamage);
                  applyDragonEffect(target, dragon, statusDuration);
               }
            });
      }
   }

   public static void destroyAreaCharge(Level level, BlockPos center, DragonBaseEntity dragon) {
      if (dragon != null) {
         if (!((IafEvents.DragonFireDamageWorld)IafEvents.ON_DRAGON_DAMAGE_BLOCK.invoker()).onDamageBlock(dragon, center.getX(), center.getY(), center.getZ())) {
            int x = 2;
            int y = 2;
            int z = 2;
            boolean canBreakBlocks = DragonUtils.canGrief(dragon) && level.getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING);
            if (canBreakBlocks) {
               if (dragon.getDragonStage() <= 3) {
                  BlockPos.betweenClosedStream(center.offset(-x, -y, -z), center.offset(x, y, z)).forEach(position -> {
                     BlockState state = level.getBlockState(position);
                     if (!(state.getBlock() instanceof DragonProof)) {
                        if (dragon.getRandom().nextFloat() * 3.0F > center.distSqr(position) && DragonUtils.canDragonBreak(state, dragon)) {
                           level.destroyBlock(position, false);
                        }

                        if (dragon.getRandom().nextBoolean()) {
                           attackBlock(level, dragon, position, state);
                        }
                     }
                  });
               } else {
                  int radius = dragon.getDragonStage() == 4 ? 2 : 3;
                  x = radius + level.random.nextInt(2);
                  y = radius + level.random.nextInt(2);
                  z = radius + level.random.nextInt(2);
                  float f = (x + y + z) * 0.333F + 0.5F;
                  float ff = f * f;
                  destroyBlocks(level, center, x, y, z, ff, dragon);
                  BlockPos.betweenClosedStream(center.offset(-(++x), -(++y), -(++z)), center.offset(x, y, z)).forEach(position -> {
                     if (center.distSqr(position) <= ff) {
                        attackBlock(level, dragon, position);
                     }
                  });
               }
            }

            int statusDuration;
            if (dragon.dragonType == IafDragonTypes.FIRE) {
               statusDuration = 15;
            } else if (dragon.dragonType == IafDragonTypes.ICE) {
               statusDuration = 400;
            } else {
               if (dragon.dragonType != IafDragonTypes.LIGHTNING) {
                  return;
               }

               statusDuration = 9;
            }

            float stageDamage = Math.max(1, dragon.getDragonStage() - 1) * 2.0F;
            DamageSource damageSource = getDamageSource(dragon);
            level.getEntitiesOfClass(
                  LivingEntity.class,
                  new AABB(
                     (double)center.getX() - x,
                     (double)center.getY() - y,
                     (double)center.getZ() - z,
                     (double)center.getX() + x,
                     (double)center.getY() + y,
                     (double)center.getZ() + z
                  )
               )
               .forEach(target -> {
                  if (!dragon.isAlliedTo(target) && !dragon.is(target) && dragon.hasLineOfSight(target)) {
                     target.hurt(damageSource, stageDamage);
                     applyDragonEffect(target, dragon, statusDuration);
                  }
               });
            if ((Boolean)IafCommonConfig.INSTANCE.dragon.explosiveBreath.getValue()) {
               causeExplosion(level, center, dragon, damageSource, dragon.getDragonStage());
            }
         }
      }
   }

   private static DamageSource getDamageSource(DragonBaseEntity dragon) {
      Player player = dragon.getRidingPlayer();
      if (dragon.dragonType == IafDragonTypes.FIRE) {
         return (DamageSource)(player != null ? IafDamageTypes.causeIndirectDragonFireDamage(dragon, player) : IafDamageTypes.causeDragonFireDamage(dragon));
      } else if (dragon.dragonType == IafDragonTypes.ICE) {
         return (DamageSource)(player != null ? IafDamageTypes.causeIndirectDragonIceDamage(dragon, player) : IafDamageTypes.causeDragonIceDamage(dragon));
      } else if (dragon.dragonType == IafDragonTypes.LIGHTNING) {
         return (DamageSource)(player != null
            ? IafDamageTypes.causeIndirectDragonLightningDamage(dragon, player)
            : IafDamageTypes.causeDragonLightningDamage(dragon));
      } else {
         return dragon.level().damageSources().mobAttack(dragon);
      }
   }

   private static void attackBlock(Level level, DragonBaseEntity dragon, BlockPos position, BlockState state) {
      if (!(state.getBlock() instanceof DragonProof) && DragonUtils.canDragonBreak(state, dragon)) {
         BlockState transformed;
         if (dragon.dragonType == IafDragonTypes.FIRE) {
            transformed = transformBlockFire(state);
         } else if (dragon.dragonType == IafDragonTypes.ICE) {
            transformed = transformBlockIce(state);
         } else {
            if (dragon.dragonType != IafDragonTypes.LIGHTNING) {
               return;
            }

            transformed = transformBlockLightning(state);
         }

         if (!transformed.is(state.getBlock())) {
            level.setBlockAndUpdate(position, transformed);
         }

         Block elementalBlock;
         boolean doPlaceBlock;
         if (dragon.dragonType == IafDragonTypes.FIRE) {
            elementalBlock = Blocks.FIRE;
            doPlaceBlock = dragon.getRandom().nextBoolean();
         } else {
            if (dragon.dragonType != IafDragonTypes.ICE) {
               return;
            }

            elementalBlock = (Block)IafBlocks.DRAGON_ICE_SPIKES.get();
            doPlaceBlock = dragon.getRandom().nextInt(9) == 0;
         }

         BlockState stateAbove = level.getBlockState(position.above());
         if (doPlaceBlock
            && transformed.isSolid()
            && stateAbove.getFluidState().isEmpty()
            && !stateAbove.canOcclude()
            && state.canOcclude()
            && DragonUtils.canDragonBreak(stateAbove, dragon)) {
            level.setBlockAndUpdate(position.above(), elementalBlock.defaultBlockState());
         }
      }
   }

   private static void attackBlock(Level level, DragonBaseEntity dragon, BlockPos position) {
      attackBlock(level, dragon, position, level.getBlockState(position));
   }

   private static void applyDragonEffect(LivingEntity target, DragonBaseEntity dragon, int statusDuration) {
      if (dragon.dragonType == IafDragonTypes.FIRE) {
         target.igniteForSeconds(statusDuration);
      } else if (dragon.dragonType == IafDragonTypes.ICE) {
         target.addEffect(new MobEffectInstance(BuiltInRegistries.MOB_EFFECT.wrapAsHolder((MobEffect)IafStatusEffects.FROZEN.get()), statusDuration));
      } else if (dragon.dragonType == IafDragonTypes.LIGHTNING) {
         double x = dragon.getX() - target.getX();
         double y = dragon.getZ() - target.getZ();
         target.knockback(statusDuration / 10.0, x, y);
      }
   }

   private static void causeExplosion(Level world, BlockPos center, DragonBaseEntity destroyer, DamageSource source, int stage) {
      BlockInteraction mode = world.getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING) ? BlockInteraction.DESTROY : BlockInteraction.KEEP;
      BlockLaunchExplosion explosion = new BlockLaunchExplosion(
         world, destroyer, source, center.getX(), center.getY(), center.getZ(), Math.min(2, stage - 2), mode
      );
      explosion.explode();
      explosion.finalizeExplosion(true);
   }

   private static void destroyBlocks(Level world, BlockPos center, int x, int y, int z, double radius2, Entity destroyer) {
      BlockPos.betweenClosedStream(center.offset(-x, -y, -z), center.offset(x, y, z)).forEach(pos -> {
         if (center.distSqr(pos) <= radius2) {
            BlockState state = world.getBlockState(pos);
            if (state.getBlock() instanceof DragonProof) {
               return;
            }

            if (world.random.nextFloat() * 3.0F > (float)center.distSqr(pos) / radius2 && DragonUtils.canDragonBreak(state, destroyer)) {
               world.destroyBlock(pos, false);
            }
         }
      });
   }

   public static BlockState transformBlockFire(BlockState in) {
      if (in.getBlock() instanceof SpreadingSnowyDirtBlock) {
         return (BlockState)((Block)IafBlocks.CHARRED_GRASS.get()).defaultBlockState().setValue(ReturningStateBlock.REVERTS, true);
      }

      if (in.is(Blocks.DIRT)) {
         return (BlockState)((Block)IafBlocks.CHARRED_DIRT.get()).defaultBlockState().setValue(ReturningStateBlock.REVERTS, true);
      }

      if (in.is(BlockTags.SAND) && in.getBlock() == Blocks.GRAVEL) {
         return (BlockState)((Block)IafBlocks.CHARRED_GRAVEL.get()).defaultBlockState().setValue(FallingReturningStateBlock.REVERTS, true);
      }

      if (!in.is(BlockTags.BASE_STONE_OVERWORLD) || in.getBlock() != Blocks.COBBLESTONE && !in.getBlock().getDescriptionId().contains("cobblestone")) {
         if (in.is(BlockTags.BASE_STONE_OVERWORLD) && in.getBlock() != IafBlocks.CHARRED_COBBLESTONE.get()) {
            return (BlockState)((Block)IafBlocks.CHARRED_STONE.get()).defaultBlockState().setValue(ReturningStateBlock.REVERTS, true);
         } else if (in.getBlock() == Blocks.DIRT_PATH) {
            return (BlockState)((Block)IafBlocks.CHARRED_DIRT_PATH.get()).defaultBlockState().setValue(CharedPathBlock.REVERTS, true);
         } else if (in.is(BlockTags.LOGS) || in.is(BlockTags.PLANKS)) {
            return ((Block)IafBlocks.ASH.get()).defaultBlockState();
         } else {
            return !in.is(BlockTags.LEAVES) && !in.is(BlockTags.FLOWERS) && !in.is(BlockTags.CROPS) && in.getBlock() != Blocks.SNOW
               ? in
               : Blocks.AIR.defaultBlockState();
         }
      } else {
         return (BlockState)((Block)IafBlocks.CHARRED_COBBLESTONE.get()).defaultBlockState().setValue(ReturningStateBlock.REVERTS, true);
      }
   }

   public static BlockState transformBlockIce(BlockState in) {
      if (in.getBlock() instanceof SpreadingSnowyDirtBlock) {
         return (BlockState)((Block)IafBlocks.FROZEN_GRASS.get()).defaultBlockState().setValue(ReturningStateBlock.REVERTS, true);
      }

      if ((!in.is(BlockTags.DIRT) || in.getBlock() != Blocks.DIRT) && !in.is(BlockTags.SNOW)) {
         if (in.is(BlockTags.SAND) && in.getBlock() == Blocks.GRAVEL) {
            return (BlockState)((Block)IafBlocks.FROZEN_GRAVEL.get()).defaultBlockState().setValue(FallingReturningStateBlock.REVERTS, true);
         }

         if (in.is(BlockTags.SAND) && in.getBlock() != Blocks.GRAVEL) {
            return in;
         }

         if (!in.is(BlockTags.BASE_STONE_OVERWORLD) || in.getBlock() != Blocks.COBBLESTONE && !in.getBlock().getDescriptionId().contains("cobblestone")) {
            if (in.is(BlockTags.BASE_STONE_OVERWORLD) && in.getBlock() != IafBlocks.FROZEN_COBBLESTONE.get()) {
               return (BlockState)((Block)IafBlocks.FROZEN_STONE.get()).defaultBlockState().setValue(ReturningStateBlock.REVERTS, true);
            } else if (in.getBlock() == Blocks.DIRT_PATH) {
               return (BlockState)((Block)IafBlocks.FROZEN_DIRT_PATH.get()).defaultBlockState().setValue(CharedPathBlock.REVERTS, true);
            } else if (in.is(BlockTags.LOGS) || in.is(BlockTags.PLANKS)) {
               return ((Block)IafBlocks.FROZEN_SPLINTERS.get()).defaultBlockState();
            } else if (in.is(Blocks.WATER)) {
               return Blocks.ICE.defaultBlockState();
            } else {
               return !in.is(BlockTags.LEAVES) && !in.is(BlockTags.FLOWERS) && !in.is(BlockTags.CROPS) && in.getBlock() != Blocks.SNOW
                  ? in
                  : Blocks.AIR.defaultBlockState();
            }
         } else {
            return (BlockState)((Block)IafBlocks.FROZEN_COBBLESTONE.get()).defaultBlockState().setValue(ReturningStateBlock.REVERTS, true);
         }
      } else {
         return (BlockState)((Block)IafBlocks.FROZEN_DIRT.get()).defaultBlockState().setValue(ReturningStateBlock.REVERTS, true);
      }
   }

   public static BlockState transformBlockLightning(BlockState in) {
      if (in.getBlock() instanceof SpreadingSnowyDirtBlock) {
         return (BlockState)((Block)IafBlocks.CRACKLED_GRASS.get()).defaultBlockState().setValue(ReturningStateBlock.REVERTS, true);
      }

      if (in.is(BlockTags.DIRT) && in.getBlock() == Blocks.DIRT) {
         return (BlockState)((Block)IafBlocks.CRACKLED_DIRT.get()).defaultBlockState().setValue(ReturningStateBlock.REVERTS, true);
      }

      if (in.is(BlockTags.SAND) && in.getBlock() == Blocks.GRAVEL) {
         return (BlockState)((Block)IafBlocks.CRACKLED_GRAVEL.get()).defaultBlockState().setValue(FallingReturningStateBlock.REVERTS, true);
      }

      if (!in.is(BlockTags.BASE_STONE_OVERWORLD) || in.getBlock() != Blocks.COBBLESTONE && !in.getBlock().getDescriptionId().contains("cobblestone")) {
         if (in.is(BlockTags.BASE_STONE_OVERWORLD) && in.getBlock() != IafBlocks.CRACKLED_COBBLESTONE.get()) {
            return (BlockState)((Block)IafBlocks.CRACKLED_STONE.get()).defaultBlockState().setValue(ReturningStateBlock.REVERTS, true);
         } else if (in.getBlock() == Blocks.DIRT_PATH) {
            return (BlockState)((Block)IafBlocks.CRACKLED_DIRT_PATH.get()).defaultBlockState().setValue(CharedPathBlock.REVERTS, true);
         } else if (in.is(BlockTags.LOGS) || in.is(BlockTags.PLANKS)) {
            return ((Block)IafBlocks.ASH.get()).defaultBlockState();
         } else {
            return !in.is(BlockTags.LEAVES) && !in.is(BlockTags.FLOWERS) && !in.is(BlockTags.CROPS) && in.getBlock() != Blocks.SNOW
               ? in
               : Blocks.AIR.defaultBlockState();
         }
      } else {
         return (BlockState)((Block)IafBlocks.CRACKLED_COBBLESTONE.get()).defaultBlockState().setValue(ReturningStateBlock.REVERTS, true);
      }
   }
}
