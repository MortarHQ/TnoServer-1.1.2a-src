package com.iafenvoy.iceandfire.entity.util.dragon;

import com.google.common.base.Predicate;
import com.iafenvoy.iceandfire.config.IafCommonConfig;
import com.iafenvoy.iceandfire.entity.CockatriceEntity;
import com.iafenvoy.iceandfire.entity.DragonBaseEntity;
import com.iafenvoy.iceandfire.entity.GhostEntity;
import com.iafenvoy.iceandfire.entity.GorgonEntity;
import com.iafenvoy.iceandfire.entity.MultipartPartEntity;
import com.iafenvoy.iceandfire.entity.SeaSerpentEntity;
import com.iafenvoy.iceandfire.entity.StymphalianBirdEntity;
import com.iafenvoy.iceandfire.entity.util.IDeadMob;
import com.iafenvoy.iceandfire.registry.IafBlocks;
import com.iafenvoy.iceandfire.registry.tag.IafBlockTags;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.entity.animal.AbstractGolem;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class DragonUtils {
   public static BlockPos getBlockInViewEscort(DragonBaseEntity dragon) {
      BlockPos escortPos = dragon.getEscortPosition();
      BlockPos ground = dragon.level().getHeightmapPos(Types.MOTION_BLOCKING_NO_LEAVES, escortPos);
      int distFromGround = escortPos.getY() - ground.getY();

      for (int i = 0; i < 10; i++) {
         BlockPos pos = new BlockPos(
            escortPos.getX()
               + dragon.getRandom().nextInt((Integer)IafCommonConfig.INSTANCE.dragon.wanderFromHomeDistance.getValue())
               - (Integer)IafCommonConfig.INSTANCE.dragon.wanderFromHomeDistance.getValue() / 2,
            distFromGround > 16 ? escortPos.getY() : escortPos.getY() + 8 + dragon.getRandom().nextInt(16),
            escortPos.getZ()
               + dragon.getRandom().nextInt((Integer)IafCommonConfig.INSTANCE.dragon.wanderFromHomeDistance.getValue())
               - (Integer)IafCommonConfig.INSTANCE.dragon.wanderFromHomeDistance.getValue() / 2
         );
         if (dragon.getDistanceSquared(Vec3.atCenterOf(pos)) > 6.0F && !dragon.isTargetBlocked(Vec3.atCenterOf(pos))) {
            return pos;
         }
      }

      return null;
   }

   public static BlockPos getWaterBlockInViewEscort(DragonBaseEntity dragon) {
      BlockPos inWaterEscortPos = dragon.getEscortPosition();
      if (Math.abs(dragon.getX() - inWaterEscortPos.getX()) < dragon.getBoundingBox().getXsize()
         && Math.abs(dragon.getZ() - inWaterEscortPos.getZ()) < dragon.getBoundingBox().getZsize()) {
         return dragon.blockPosition();
      }

      if (inWaterEscortPos.getY() - dragon.getY() > 8 + dragon.getYNavSize() && !dragon.level().getFluidState(inWaterEscortPos.below()).is(FluidTags.WATER)) {
         dragon.setHovering(true);
      }

      return inWaterEscortPos;
   }

   public static BlockPos getBlockInView(DragonBaseEntity dragon) {
      float radius = 12.0F * (0.7F * dragon.getRenderSize() / 3.0F);
      float neg = dragon.getRandom().nextBoolean() ? 1.0F : -1.0F;
      float renderYawOffset = dragon.yBodyRot;
      if (dragon.hasHomePosition && dragon.homePos != null) {
         BlockPos dragonPos = dragon.blockPosition();
         BlockPos ground = dragon.level().getHeightmapPos(Types.MOTION_BLOCKING_NO_LEAVES, dragonPos);
         int distFromGround = (int)dragon.getY() - ground.getY();

         for (int i = 0; i < 10; i++) {
            BlockPos homePos = dragon.homePos.getPosition();
            BlockPos pos = new BlockPos(
               homePos.getX()
                  + dragon.getRandom().nextInt((Integer)IafCommonConfig.INSTANCE.dragon.wanderFromHomeDistance.getValue() * 2)
                  - (Integer)IafCommonConfig.INSTANCE.dragon.wanderFromHomeDistance.getValue(),
               distFromGround > 16
                  ? (int)Math.min(
                     ((Integer)IafCommonConfig.INSTANCE.dragon.maxFlight.getValue()).intValue(), dragon.getY() + dragon.getRandom().nextInt(16) - 8.0
                  )
                  : (int)dragon.getY() + dragon.getRandom().nextInt(16) + 1,
               homePos.getZ()
                  + dragon.getRandom().nextInt((Integer)IafCommonConfig.INSTANCE.dragon.wanderFromHomeDistance.getValue() * 2)
                  - (Integer)IafCommonConfig.INSTANCE.dragon.wanderFromHomeDistance.getValue()
            );
            if (dragon.getDistanceSquared(Vec3.atCenterOf(pos)) > 6.0F && !dragon.isTargetBlocked(Vec3.atCenterOf(pos))) {
               return pos;
            }
         }
      }

      float angle = (float) (Math.PI / 180.0) * renderYawOffset + 3.15F + dragon.getRandom().nextFloat() * neg;
      double extraX = radius * Mth.sin((float)(Math.PI + angle));
      double extraZ = radius * Mth.cos(angle);
      BlockPos radialPos = BlockPos.containing(dragon.getX() + extraX, 0.0, dragon.getZ() + extraZ);
      BlockPos ground = dragon.level().getHeightmapPos(Types.MOTION_BLOCKING_NO_LEAVES, radialPos);
      int distFromGround = (int)dragon.getY() - ground.getY();
      BlockPos newPos = radialPos.above(
         distFromGround > 16
            ? (int)Math.min(((Integer)IafCommonConfig.INSTANCE.dragon.maxFlight.getValue()).intValue(), dragon.getY() + dragon.getRandom().nextInt(16) - 8.0)
            : (int)dragon.getY() + dragon.getRandom().nextInt(16) + 1
      );
      BlockPos pos = dragon.doesWantToLand() ? ground : newPos;
      return dragon.getDistanceSquared(Vec3.atCenterOf(newPos)) > 6.0F && !dragon.isTargetBlocked(Vec3.atCenterOf(newPos)) ? pos : null;
   }

   public static BlockPos getWaterBlockInView(DragonBaseEntity dragon) {
      float radius = 0.75F * (0.7F * dragon.getRenderSize() / 3.0F) * -7.0F - dragon.getRandom().nextInt(dragon.getDragonStage() * 6);
      float neg = dragon.getRandom().nextBoolean() ? 1.0F : -1.0F;
      float angle = (float) (Math.PI / 180.0) * dragon.yBodyRot + 3.15F + dragon.getRandom().nextFloat() * neg;
      double extraX = radius * Mth.sin((float)(Math.PI + angle));
      double extraZ = radius * Mth.cos(angle);
      BlockPos radialPos = BlockPos.containing(dragon.getX() + extraX, 0.0, dragon.getZ() + extraZ);
      BlockPos ground = dragon.level().getHeightmapPos(Types.MOTION_BLOCKING_NO_LEAVES, radialPos);
      int distFromGround = (int)dragon.getY() - ground.getY();
      BlockPos newPos = radialPos.above(
         distFromGround > 16
            ? (int)Math.min(((Integer)IafCommonConfig.INSTANCE.dragon.maxFlight.getValue()).intValue(), dragon.getY() + dragon.getRandom().nextInt(16) - 8.0)
            : (int)dragon.getY() + dragon.getRandom().nextInt(16) + 1
      );
      BlockPos pos = dragon.doesWantToLand() ? ground : newPos;
      BlockPos surface = dragon.level().getFluidState(newPos.below(2)).is(FluidTags.WATER) ? newPos.below(dragon.getRandom().nextInt(10) + 1) : newPos;
      return dragon.getDistanceSquared(Vec3.atCenterOf(surface)) > 6.0F && dragon.level().getFluidState(surface).is(FluidTags.WATER) ? surface : null;
   }

   public static LivingEntity riderLookingAtEntity(LivingEntity dragon, LivingEntity rider, double dist) {
      Vec3 Vector3d = rider.getEyePosition(1.0F);
      Vec3 Vector3d1 = rider.getViewVector(1.0F);
      Vec3 Vector3d2 = Vector3d.add(Vector3d1.x * dist, Vector3d1.y * dist, Vector3d1.z * dist);
      Entity pointedEntity = null;
      List<Entity> list = rider.level()
         .getEntities(
            rider,
            rider.getBoundingBox().expandTowards(Vector3d1.x * dist, Vector3d1.y * dist, Vector3d1.z * dist).inflate(1.0, 1.0, 1.0),
            (Predicate)entity -> onSameTeam(dragon, entity)
               ? false
               : entity != null
                  && entity.isPickable()
                  && entity instanceof LivingEntity
                  && !entity.is(dragon)
                  && !entity.isAlliedTo(dragon)
                  && (!(entity instanceof IDeadMob) || !((IDeadMob)entity).isMobDead())
         );
      double d2 = dist;

      for (Entity entity1 : list) {
         AABB axisalignedbb = entity1.getBoundingBox().inflate(entity1.getPickRadius() + 2.0);
         Vec3 raytraceresult = axisalignedbb.clip(Vector3d, Vector3d2).orElse(Vec3.ZERO);
         if (axisalignedbb.contains(Vector3d)) {
            if (d2 >= 0.0) {
               pointedEntity = entity1;
               d2 = 0.0;
            }
         } else {
            double d3 = Vector3d.distanceTo(raytraceresult);
            if (d3 < d2 || d2 == 0.0) {
               if (entity1.getRootVehicle() == rider.getRootVehicle()) {
                  if (d2 == 0.0) {
                     pointedEntity = entity1;
                  }
               } else {
                  pointedEntity = entity1;
                  d2 = d3;
               }
            }
         }
      }

      return (LivingEntity)pointedEntity;
   }

   public static BlockPos getBlockInViewStymphalian(StymphalianBirdEntity bird) {
      float radius = -9.45F - bird.getRandom().nextInt(24);
      float neg = bird.getRandom().nextBoolean() ? 1.0F : -1.0F;
      float renderYawOffset = bird.flock != null && !bird.flock.isLeader(bird) ? getStymphalianFlockDirection(bird) : bird.yBodyRot;
      float angle = (float) (Math.PI / 180.0) * renderYawOffset + 3.15F + bird.getRandom().nextFloat() * neg;
      double extraX = radius * Mth.sin((float)(Math.PI + angle));
      double extraZ = radius * Mth.cos(angle);
      BlockPos radialPos = getStymphalianFearPos(bird, BlockPos.containing(bird.getX() + extraX, 0.0, bird.getZ() + extraZ));
      BlockPos ground = bird.level().getHeightmapPos(Types.MOTION_BLOCKING_NO_LEAVES, radialPos);
      int distFromGround = (int)bird.getY() - ground.getY();
      int flightHeight = Math.min((Integer)IafCommonConfig.INSTANCE.stymphalianBird.flightHeight.getValue(), ground.getY() + bird.getRandom().nextInt(16));
      BlockPos newPos = radialPos.above(distFromGround > 16 ? flightHeight : (int)bird.getY() + bird.getRandom().nextInt(16) + 1);
      return bird.getDistanceSquared(Vec3.atCenterOf(newPos)) > 6.0F && !bird.isTargetBlocked(Vec3.atCenterOf(newPos)) ? newPos : null;
   }

   private static BlockPos getStymphalianFearPos(StymphalianBirdEntity bird, BlockPos fallback) {
      if (bird.getVictor() != null && bird.getVictor() instanceof PathfinderMob pathAware) {
         Vec3 Vector3d = DefaultRandomPos.getPosAway(
            pathAware,
            16,
            (Integer)IafCommonConfig.INSTANCE.stymphalianBird.flightHeight.getValue(),
            new Vec3(bird.getVictor().getX(), bird.getVictor().getY(), bird.getVictor().getZ())
         );
         if (Vector3d != null) {
            BlockPos pos = BlockPos.containing(Vector3d);
            return new BlockPos(pos.getX(), 0, pos.getZ());
         }
      }

      return fallback;
   }

   private static float getStymphalianFlockDirection(StymphalianBirdEntity bird) {
      StymphalianBirdEntity leader = bird.flock.getLeader();
      if (bird.distanceToSqr(leader) > 2.0) {
         double d0 = leader.getX() - bird.getX();
         double d2 = leader.getZ() - bird.getZ();
         float f = (float)(Mth.atan2(d2, d0) * (180.0 / Math.PI)) - 90.0F;
         float degrees = Mth.wrapDegrees(f - bird.getYRot());
         return bird.getYRot() + degrees;
      } else {
         return leader.yBodyRot;
      }
   }

   public static BlockPos getBlockInTargetsViewCockatrice(CockatriceEntity cockatrice, LivingEntity target) {
      float radius = 10 + cockatrice.getRandom().nextInt(10);
      float angle = (float) (Math.PI / 180.0) * target.yHeadRot;
      double extraX = radius * Mth.sin((float)(Math.PI + angle));
      double extraZ = radius * Mth.cos(angle);
      BlockPos radialPos = BlockPos.containing(target.getX() + extraX, 0.0, target.getZ() + extraZ);
      BlockPos ground = target.level().getHeightmapPos(Types.MOTION_BLOCKING_NO_LEAVES, radialPos);
      return cockatrice.distanceToSqr(Vec3.atCenterOf(ground)) > 30.0 && !cockatrice.isTargetBlocked(Vec3.atCenterOf(ground)) ? ground : target.blockPosition();
   }

   public static BlockPos getBlockInTargetsViewGhost(GhostEntity ghost, LivingEntity target) {
      float radius = 4 + ghost.getRandom().nextInt(5);
      float angle = (float) (Math.PI / 180.0) * (target.yHeadRot + 90.0F + ghost.getRandom().nextInt(180));
      double extraX = radius * Mth.sin((float)(Math.PI + angle));
      double extraZ = radius * Mth.cos(angle);
      return ghost.distanceToSqr(Vec3.atCenterOf(BlockPos.containing(target.getX() + extraX, target.getY(), target.getZ() + extraZ))) > 30.0
         ? BlockPos.containing(target.getX() + extraX, target.getY(), target.getZ() + extraZ)
         : ghost.blockPosition();
   }

   public static BlockPos getBlockInTargetsViewGorgon(GorgonEntity cockatrice, LivingEntity target) {
      float radius = 6.0F;
      float angle = (float) (Math.PI / 180.0) * target.yHeadRot;
      double extraX = radius * Mth.sin((float)(Math.PI + angle));
      double extraZ = radius * Mth.cos(angle);
      BlockPos radialPos = BlockPos.containing(target.getX() + extraX, target.getY(), target.getZ() + extraZ);
      return cockatrice.distanceToSqr(Vec3.atCenterOf(radialPos)) < 300.0 && !cockatrice.isTargetBlocked(Vec3.atCenterOf(radialPos).add(0.0, 0.75, 0.0))
         ? radialPos
         : target.blockPosition();
   }

   public static BlockPos getBlockInTargetsViewSeaSerpent(SeaSerpentEntity serpent, LivingEntity target) {
      float radius = 10.0F * serpent.getSeaSerpentScale() + serpent.getRandom().nextInt(10);
      float angle = (float) (Math.PI / 180.0) * target.yHeadRot;
      double extraX = radius * Mth.sin((float)(Math.PI + angle));
      double extraZ = radius * Mth.cos(angle);
      BlockPos radialPos = BlockPos.containing(target.getX() + extraX, 0.0, target.getZ() + extraZ);
      BlockPos ground = target.level().getHeightmapPos(Types.MOTION_BLOCKING_NO_LEAVES, radialPos);
      return serpent.distanceToSqr(Vec3.atCenterOf(ground)) > 30.0 ? ground : target.blockPosition();
   }

   public static boolean canTameDragonAttack(TamableAnimal dragon, Entity entity) {
      if (isVillager(entity)) {
         return false;
      } else if (entity instanceof AbstractVillager || entity instanceof AbstractGolem || entity instanceof Player) {
         return false;
      } else {
         return entity instanceof TamableAnimal tameable ? !tameable.isTame() : true;
      }
   }

   public static boolean isVillager(Entity entity) {
      return entity instanceof Villager;
   }

   public static boolean isAnimaniaMob(Entity entity) {
      return false;
   }

   public static boolean isDragonTargetable(Entity entity, TagKey<EntityType<?>> tag) {
      return entity.getType().is(tag);
   }

   public static String getDimensionName(Level world) {
      return world.dimension().location().toString();
   }

   public static boolean isInHomeDimension(DragonBaseEntity dragonBase) {
      return dragonBase.getHomeDimensionName() == null || getDimensionName(dragonBase.level()).equals(dragonBase.getHomeDimensionName());
   }

   public static boolean canDragonBreak(BlockState state, Entity entity) {
      if (!entity.level().getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING)) {
         return false;
      }

      Block block = state.getBlock();
      return block.getExplosionResistance() < 1200.0F && !state.is(IafBlockTags.DRAGON_BLOCK_BREAK_BLACKLIST);
   }

   public static boolean hasSameOwner(TamableAnimal cockatrice, Entity entity) {
      return !(entity instanceof TamableAnimal tameable)
         ? false
         : tameable.getOwnerUUID() != null && cockatrice.getOwnerUUID() != null && tameable.getOwnerUUID().equals(cockatrice.getOwnerUUID());
   }

   public static boolean isAlive(LivingEntity entity) {
      return entity instanceof DragonBaseEntity dragon && dragon.isMobDead()
         ? false
         : !(entity instanceof IDeadMob deadMob && deadMob.isMobDead()) && !GorgonEntity.isStoneMob(entity);
   }

   public static boolean canGrief(DragonBaseEntity dragon) {
      return dragon.isTame() && !IafCommonConfig.INSTANCE.dragon.tamedGriefing.getValue()
         ? false
         : (Boolean)IafCommonConfig.INSTANCE.dragon.griefing.getValue();
   }

   public static boolean canHostilesTarget(Entity entity) {
      if (!(entity instanceof Player) || entity.level().getDifficulty() != Difficulty.PEACEFUL && !((Player)entity).isCreative()) {
         return entity instanceof DragonBaseEntity dragonBase && dragonBase.isMobDead()
            ? false
            : entity instanceof LivingEntity livingEntity && isAlive(livingEntity);
      } else {
         return false;
      }
   }

   public static boolean onSameTeam(Entity entity1, Entity entity2) {
      Entity owner1 = null;
      Entity owner2 = null;
      boolean def = entity1.isAlliedTo(entity2);
      if (entity1 instanceof TamableAnimal tameable) {
         owner1 = tameable.getOwner();
      }

      if (entity2 instanceof TamableAnimal tameable) {
         owner2 = tameable.getOwner();
      }

      if (entity1 instanceof MultipartPartEntity mutlipartPart && mutlipartPart.getParent() instanceof TamableAnimal tameable) {
         owner1 = tameable.getOwner();
      }

      if (entity2 instanceof MultipartPartEntity mutlipartPart && mutlipartPart.getParent() instanceof TamableAnimal tameable) {
         owner2 = tameable.getOwner();
      }

      return owner1 != null && owner2 != null ? owner1.is(owner2) : def;
   }

   public static boolean isDreadBlock(BlockState state) {
      Block block = state.getBlock();
      return block == IafBlocks.DREAD_STONE.get()
         || block == IafBlocks.DREAD_STONE_BRICKS.get()
         || block == IafBlocks.DREAD_STONE_BRICKS_CHISELED.get()
         || block == IafBlocks.DREAD_STONE_BRICKS_CRACKED.get()
         || block == IafBlocks.DREAD_STONE_BRICKS_MOSSY.get()
         || block == IafBlocks.DREAD_STONE_TILE.get()
         || block == IafBlocks.DREAD_STONE_FACE.get()
         || block == IafBlocks.DREAD_TORCH.get()
         || block == IafBlocks.DREAD_STONE_BRICKS_STAIRS.get()
         || block == IafBlocks.DREAD_STONE_BRICKS_SLAB.get()
         || block == IafBlocks.DREADWOOD_LOG.get()
         || block == IafBlocks.DREADWOOD_PLANKS.get()
         || block == IafBlocks.DREADWOOD_PLANKS_LOCK.get()
         || block == IafBlocks.DREAD_PORTAL.get()
         || block == IafBlocks.DREAD_SPAWNER.get();
   }
}
