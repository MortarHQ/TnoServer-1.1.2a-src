package it.hurts.octostudios.reliquified_lenders_cataclysm.utils.relics;

import it.hurts.octostudios.reliquified_lenders_cataclysm.init.RECDataComponentRegistry;
import it.hurts.octostudios.reliquified_lenders_cataclysm.utils.ItemUtils;
import it.hurts.sskirillss.relics.init.DataComponentRegistry;
import it.hurts.sskirillss.relics.network.NetworkHandler;
import it.hurts.sskirillss.relics.network.packets.PacketPlayerMotion;
import java.util.UUID;
import net.minecraft.commands.arguments.EntityAnchorArgument.Anchor;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class ScouringEyeUtils {
   public static final String ABILITY_ID = "glowing_scour";

   public static void resetData(ItemStack stack) {
      setGlowingTime(stack, getGlowingTimeStat(stack));
      setTargetUUID(stack, "");
      setTeleportSafe(stack, false);
   }

   public static void teleportToTarget(Player player, LivingEntity target, BlockPos pos, Vec3 motion) {
      Vec3 posCenter = pos.getBottomCenter();
      player.teleportTo(posCenter.x, pos.getY() + 1.0, posCenter.z);
      player.lookAt(Anchor.EYES, target.getEyePosition().add(0.0, 0.5, 0.0));
      NetworkHandler.sendToClient(new PacketPlayerMotion(motion.x, motion.y, motion.z), (ServerPlayer)player);
      target.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 30), player);
      player.getCommandSenderWorld().playSound(null, pos, SoundEvents.ENDERMAN_TELEPORT, SoundSource.PLAYERS, 1.0F, 1.0F);
   }

   @Nullable
   public static BlockPos getTeleportPos(LivingEntity entity, LivingEntity target) {
      Level level = entity.getCommandSenderWorld();
      Vec3 pos = target.position().add(target.getViewVector(1.0F).scale(-3.0));
      BlockPos blockPos = BlockPos.containing(pos.x, pos.y, pos.z);
      return !isBlockSafe(level, blockPos) ? getSafePos(level, blockPos) : blockPos;
   }

   @Nullable
   public static BlockPos getSafePos(Level level, BlockPos initialPos) {
      for (int i = 1; i <= 4; i++) {
         for (int j = 1; j <= 4; j++) {
            double initialY = initialPos.getY();
            BlockPos pos = BlockPos.containing(initialPos.getX() + i, initialY, initialPos.getZ() + i);
            BlockPos newPos = level.getHeightmapPos(Types.MOTION_BLOCKING_NO_LEAVES, pos);
            if (isBlockSafe(level, newPos) && Math.abs(initialY - newPos.getY()) <= 3.0) {
               return newPos;
            }
         }
      }

      return null;
   }

   public static Vec3 getMovementOnTeleport(BlockPos teleportPos, BlockPos targetPos) {
      return new Vec3(targetPos.getX(), 0.0, targetPos.getZ()).subtract(teleportPos.getX(), 0.0, teleportPos.getZ());
   }

   public static boolean isBlockSafe(Level level, BlockPos pos) {
      BlockState blockState = level.getBlockState(pos);
      BlockState blockStateBelow = level.getBlockState(pos.below());
      BlockState blockStateAbove = level.getBlockState(pos.above());
      return blockStateAbove.isAir()
         && (blockState.isAir() || !blockState.isCollisionShapeFullBlock(level, pos))
         && blockStateBelow.isSolid()
         && !(blockStateBelow.getBlock() instanceof LiquidBlock);
   }

   @Nullable
   public static LivingEntity getEntityFromStack(Level level, ItemStack stack) {
      String uuid = getTargetUUID(stack);
      if (!uuid.isEmpty() && !level.isClientSide) {
         return ((ServerLevel)level).getEntity(UUID.fromString(uuid)) instanceof LivingEntity livingEntity ? livingEntity : null;
      } else {
         return null;
      }
   }

   public static boolean isGlowingTimeInBounds(ItemStack stack) {
      return getGlowingTime(stack) > 0 && getGlowingTime(stack) <= getGlowingTimeStat(stack);
   }

   public static boolean isGlowingTimeTicking(ItemStack stack, Level level) {
      return isGlowingTimeInBounds(stack) && getStackTime(stack) >= level.getGameTime() - 1L;
   }

   public static boolean isTeleportAllowed(ItemStack stack) {
      return (Boolean)stack.getOrDefault(RECDataComponentRegistry.TP_SAFE, false)
         && isGlowingTimeInBounds(stack)
         && !(Boolean)stack.getOrDefault(RECDataComponentRegistry.PLAYER_DIED, false);
   }

   public static void setTeleportSafe(ItemStack stack, boolean value) {
      stack.set(RECDataComponentRegistry.TP_SAFE, value);
   }

   public static void setPlayerDied(ItemStack stack, boolean value) {
      stack.set(RECDataComponentRegistry.PLAYER_DIED, value);
   }

   public static String getTargetUUID(ItemStack stack) {
      return (String)stack.getOrDefault(RECDataComponentRegistry.TARGET_UUID, "");
   }

   public static void setTargetUUID(ItemStack stack, String value) {
      stack.set(RECDataComponentRegistry.TARGET_UUID, value);
   }

   public static int getStackTime(ItemStack stack) {
      return (Integer)stack.getOrDefault(DataComponentRegistry.TIME, 0);
   }

   public static void setStackTime(ItemStack stack, int value) {
      stack.set(DataComponentRegistry.TIME, value);
   }

   public static int getGlowingTime(ItemStack stack) {
      return (Integer)stack.getOrDefault(RECDataComponentRegistry.GLOWING_TIME, 0);
   }

   public static void setGlowingTime(ItemStack stack, int value) {
      stack.set(RECDataComponentRegistry.GLOWING_TIME, value);
   }

   public static int getGlowingTimeStat(ItemStack stack) {
      return ItemUtils.getTickStat(stack, "glowing_scour", "glowing_time");
   }
}
