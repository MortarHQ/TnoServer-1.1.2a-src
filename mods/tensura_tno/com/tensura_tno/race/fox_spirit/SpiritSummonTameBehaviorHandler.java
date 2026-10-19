package com.tensura_tno.race.fox_spirit;

import com.tensura_tno.ai.SubordinateSkillAiController;
import java.util.Objects;
import java.util.UUID;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent.EntityInteract;
import net.neoforged.neoforge.event.tick.EntityTickEvent.Post;

@EventBusSubscriber(modid = "tensura_tno")
public final class SpiritSummonTameBehaviorHandler {
   private static final String TAG_COMMAND = "tensura_tno_spirit_summon_command";
   private static final int FOLLOW = 0;
   private static final int WANDER = 1;
   private static final int STAY = 2;
   private static final double START_FOLLOW_DISTANCE_SQR = 64.0;
   private static final double STOP_FOLLOW_DISTANCE_SQR = 9.0;
   private static final double TELEPORT_DISTANCE_SQR = 2304.0;
   private static final double MAX_GUARD_DISTANCE_SQR = 4096.0;
   private static final int UNIVERSAL_ATTACK_INTERVAL = 20;

   private SpiritSummonTameBehaviorHandler() {
   }

   @SubscribeEvent
   public static void onEntityTick(Post event) {
      if (event.getEntity() instanceof Mob summon) {
         if (!summon.level().isClientSide()) {
            if (SpiritSummonEntityHelper.isSpiritSummon(summon)) {
               if (!SpiritSummonLifecycle.isSummoning(summon)) {
                  UUID ownerId = SpiritSummonEntityHelper.getSpiritSummonOwnerUUID(summon);
                  ServerPlayer owner = findOwner(summon);
                  if (ownerId != null && owner != null && !owner.isSpectator() && owner.level() == summon.level()) {
                     LivingEntity target = SubordinateSkillAiController.resolveTarget(summon);
                     if (target != null
                        && (
                           !target.isAlive() || target.level() != summon.level() || summon.distanceToSqr(target) > 4096.0 || isOwnerOrOwnersAlly(target, owner)
                        )) {
                        clearTarget(summon);
                        SubordinateSkillAiController.cancel(summon);
                        target = null;
                     }

                     int command = summon.getPersistentData().getInt("tensura_tno_spirit_summon_command");
                     if (command == 2) {
                        SubordinateSkillAiController.cancel(summon);
                        clearTarget(summon);
                        summon.getNavigation().stop();
                     } else if (target == null) {
                        SubordinateSkillAiController.cancel(summon);
                        if (command != 1) {
                           double distance = summon.distanceToSqr(owner);
                           if (distance > 2304.0) {
                              if (teleportNearOwner(summon, owner)) {
                                 summon.getNavigation().stop();
                              } else {
                                 summon.getNavigation().moveTo(owner, 1.2);
                              }
                           } else if (distance > 64.0) {
                              summon.getNavigation().moveTo(owner, 1.2);
                           } else if (distance < 9.0) {
                              summon.getNavigation().stop();
                           }
                        }
                     } else if (!SubordinateSkillAiController.tick(summon, target, ownerId, !summon.isVehicle())) {
                        guardOwner(summon, target);
                     }
                  } else {
                     SubordinateSkillAiController.cancel(summon);
                  }
               }
            }
         }
      }
   }

   @SubscribeEvent(priority = EventPriority.HIGHEST)
   public static void onLivingDamage(LivingIncomingDamageEvent event) {
      LivingEntity victim = event.getEntity();
      if (event.getSource().getEntity() instanceof LivingEntity attacker) {
         if (SpiritSummonEntityHelper.isSpiritSummon(victim) && isFriendlyWithSubordinate(victim, attacker)
            || SpiritSummonEntityHelper.isSpiritSummon(attacker) && isFriendlyWithSubordinate(attacker, victim)) {
            event.setCanceled(true);
         }
      }
   }

   @SubscribeEvent(priority = EventPriority.HIGH)
   public static void onEntityInteract(EntityInteract event) {
      if (event.getEntity() instanceof ServerPlayer player) {
         if (event.getHand() == InteractionHand.MAIN_HAND) {
            if (player.isSecondaryUseActive() && player.getMainHandItem().isEmpty()) {
               if (event.getTarget() instanceof Mob summon) {
                  if (SpiritSummonEntityHelper.isSpiritSummonOf(summon, player)) {
                     int next = (summon.getPersistentData().getInt("tensura_tno_spirit_summon_command") + 1) % 3;
                     summon.getPersistentData().putInt("tensura_tno_spirit_summon_command", next);
                     SubordinateSkillAiController.cancel(summon);
                     clearTarget(summon);
                     summon.getNavigation().stop();

                     Component message = switch (next) {
                        case 1 -> Component.translatable("tensura.message.pet.wander", new Object[]{summon.getDisplayName()});
                        case 2 -> Component.translatable("tensura.message.pet.stay", new Object[]{summon.getDisplayName()});
                        default -> Component.translatable("tensura.message.pet.follow", new Object[]{summon.getDisplayName()});
                     };
                     player.displayClientMessage(message.copy().withStyle(ChatFormatting.AQUA), true);
                     event.setCancellationResult(InteractionResult.SUCCESS);
                     event.setCanceled(true);
                  }
               }
            }
         }
      }
   }

   private static ServerPlayer findOwner(Mob summon) {
      if (summon.level() instanceof ServerLevel level) {
         UUID ownerId = SpiritSummonEntityHelper.getSpiritSummonOwnerUUID(summon);
         return ownerId == null ? null : level.getServer().getPlayerList().getPlayer(ownerId);
      } else {
         return null;
      }
   }

   private static boolean isOwnerOrOwnersAlly(LivingEntity entity, ServerPlayer owner) {
      if (entity.getUUID().equals(owner.getUUID())) {
         return true;
      }

      UUID entityOwner = SubordinateSkillAiController.findOwnerId(entity);
      if (entityOwner == null) {
         entityOwner = SpiritSummonEntityHelper.getSpiritSummonOwnerUUID(entity);
      }

      return Objects.equals(owner.getUUID(), entityOwner) || owner.isAlliedTo(entity);
   }

   private static boolean isFriendlyWithSubordinate(LivingEntity subordinate, LivingEntity other) {
      UUID ownerId = SubordinateSkillAiController.findOwnerId(subordinate);
      if (ownerId == null) {
         ownerId = SpiritSummonEntityHelper.getSpiritSummonOwnerUUID(subordinate);
      }

      if (ownerId == null) {
         return false;
      }

      if (ownerId.equals(other.getUUID())) {
         return true;
      }

      UUID otherOwner = SubordinateSkillAiController.findOwnerId(other);
      if (otherOwner == null) {
         otherOwner = SpiritSummonEntityHelper.getSpiritSummonOwnerUUID(other);
      }

      return ownerId.equals(otherOwner) || isAlliedWithOwner(subordinate, other, ownerId);
   }

   private static boolean isAlliedWithOwner(LivingEntity summon, LivingEntity other, UUID ownerId) {
      if (!(summon.level() instanceof ServerLevel level)) {
         return false;
      } else {
         ServerPlayer owner = level.getServer().getPlayerList().getPlayer(ownerId);
         return owner != null && owner.level() == other.level() && owner.isAlliedTo(other);
      }
   }

   private static void guardOwner(Mob summon, LivingEntity target) {
      summon.getLookControl().setLookAt(target, 30.0F, 30.0F);
      summon.getNavigation().moveTo(target, 1.25);
      double reach = Math.max(2.25, (double)summon.getBbWidth() * summon.getBbWidth() + (double)target.getBbWidth() * target.getBbWidth() + 1.0);
      if (summon.tickCount % 20 == 0 && !(summon.distanceToSqr(target) > reach) && summon.hasLineOfSight(target)) {
         float oldHealth = target.getHealth();
         boolean attacked = summon.doHurtTarget(target);
         if ((!attacked || target.getHealth() >= oldHealth) && target.isAlive()) {
            target.hurt(summon.damageSources().mobAttack(summon), 2.0F);
         }

         summon.swing(InteractionHand.MAIN_HAND);
      }
   }

   private static boolean teleportNearOwner(Mob summon, ServerPlayer owner) {
      ServerLevel level = (ServerLevel)summon.level();
      BlockPos center = owner.blockPosition();

      for (int yOffset = 1; yOffset >= -1; yOffset--) {
         for (int radius = 2; radius <= 4; radius++) {
            for (int xOffset = -radius; xOffset <= radius; xOffset++) {
               for (int zOffset = -radius; zOffset <= radius; zOffset++) {
                  if (Math.abs(xOffset) == radius || Math.abs(zOffset) == radius) {
                     BlockPos pos = center.offset(xOffset, yOffset, zOffset);
                     if (level.getFluidState(pos).isEmpty() && level.getBlockState(pos.below()).isFaceSturdy(level, pos.below(), Direction.UP)) {
                        double x = pos.getX() + 0.5;
                        double y = pos.getY();
                        double z = pos.getZ() + 0.5;
                        if (level.noCollision(summon, summon.getBoundingBox().move(x - summon.getX(), y - summon.getY(), z - summon.getZ()))) {
                           summon.teleportTo(x, y, z);
                           return true;
                        }
                     }
                  }
               }
            }
         }
      }

      return false;
   }

   private static void clearTarget(Mob summon) {
      summon.setTarget(null);
      summon.setLastHurtMob(null);
      summon.setLastHurtByMob(null);

      try {
         summon.getBrain().eraseMemory(MemoryModuleType.ATTACK_TARGET);
         summon.getBrain().eraseMemory(MemoryModuleType.HURT_BY_ENTITY);
         summon.getBrain().eraseMemory(MemoryModuleType.ANGRY_AT);
      } catch (Exception var2) {
      }
   }
}
