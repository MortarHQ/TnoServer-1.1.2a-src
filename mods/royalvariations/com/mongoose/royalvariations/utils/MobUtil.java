package com.mongoose.royalvariations.utils;

import com.mongoose.royalvariations.common.network.server.SInstaLookPacket;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.game.ClientboundTeleportEntityPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.OwnableEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ClipContext.Block;
import net.minecraft.world.level.ClipContext.Fluid;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.SlabType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.HitResult.Type;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.neoforged.neoforge.network.PacketDistributor;

public class MobUtil {
   public static boolean areAllies(@Nullable Entity entity, @Nullable Entity entity1) {
      return entity != null && entity1 != null ? entity.isAlliedTo(entity1) || entity1.isAlliedTo(entity) || entity == entity1 : false;
   }

   public static boolean inLineOfSight(Level level, Vec3 vec3, Vec3 vec32) {
      BlockHitResult blockHitResult = level.clip(new ClipContext(vec32, vec3, Block.VISUAL, Fluid.NONE, CollisionContext.empty()));
      return blockHitResult.getBlockPos().equals(BlockPos.containing(vec3)) || blockHitResult.getType() == Type.MISS;
   }

   public static void instaLook(Mob looker, Entity target) {
      instaLook(looker, target, false);
   }

   public static void instaLook(Mob looker, Entity target, boolean clientSent) {
      looker.lookAt(target, 100.0F, 100.0F);
      instaLook(looker, target.position());
      if (clientSent && !looker.level().isClientSide) {
         PacketDistributor.sendToAllPlayers(new SInstaLookPacket(looker, target), new CustomPacketPayload[0]);
      }
   }

   public static void instaLook(Mob mob, Vec3 vec3) {
      mob.getLookControl().setLookAt(vec3.x, vec3.y, vec3.z, 200.0F, mob.getMaxHeadXRot());
      double d2 = vec3.x - mob.getX();
      double d1 = vec3.z - mob.getZ();
      float rotate = -((float)Mth.atan2(d2, d1)) * (180.0F / (float)Math.PI);
      mob.setYRot(rotate);
      mob.yBodyRot = rotate;
      mob.yHeadRot = rotate;
   }

   public static boolean notMonsterOrAnimal(Entity entity) {
      return entity instanceof Player || entity instanceof IronGolem || entity instanceof OwnableEntity ownable && ownable.getOwner() instanceof Player;
   }

   private static HitResult rayTrace(Entity entity) {
      Vec3 startPos = new Vec3(entity.getX(), entity.getY(), entity.getZ());
      Vec3 endPos = new Vec3(entity.getX(), entity.level().getMinBuildHeight(), entity.getZ());
      return entity.level().clip(new ClipContext(startPos, endPos, Block.COLLIDER, Fluid.NONE, entity));
   }

   public static void moveDownToGround(Entity entity) {
      HitResult rayTrace = rayTrace(entity);
      if (rayTrace.getType() == Type.BLOCK) {
         BlockHitResult hitResult = (BlockHitResult)rayTrace;
         if (hitResult.getDirection() == Direction.UP) {
            BlockState hitBlock = entity.level().getBlockState(hitResult.getBlockPos());
            if (hitBlock.getBlock() instanceof SlabBlock && hitBlock.getValue(BlockStateProperties.SLAB_TYPE) == SlabType.BOTTOM) {
               entity.setPos(entity.getX(), hitResult.getBlockPos().getY() + 1.0625F - 0.5F, entity.getZ());
            } else {
               entity.setPos(entity.getX(), hitResult.getBlockPos().getY() + 1.0625F, entity.getZ());
            }

            if (entity.level() instanceof ServerLevel serverLevel) {
               serverLevel.getChunkSource().broadcastAndSend(entity, new ClientboundTeleportEntityPacket(entity));
            }
         }
      }
   }

   public static void push(Entity pEntity, double pX, double pY, double pZ) {
      push(pEntity, pX, pY, pZ, 1.0);
   }

   public static void push(Entity pEntity, double pX, double pY, double pZ, double reduction) {
      if (pEntity instanceof Player player && EntitySelector.NO_CREATIVE_OR_SPECTATOR.test(player)) {
         player.hurtMarked = true;
         if (!player.level().isClientSide) {
            player.setOnGround(false);
         }
      }

      double resist = 0.0;
      if (pEntity instanceof LivingEntity living && living.getAttribute(Attributes.KNOCKBACK_RESISTANCE) != null) {
         resist = living.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE) * reduction;
      }

      double resist1 = Math.max(0.0, 1.0 - resist);
      pEntity.setDeltaMovement(pEntity.getDeltaMovement().add(pX, pY, pZ).scale(resist1));
      pEntity.hasImpulse = true;
   }
}
