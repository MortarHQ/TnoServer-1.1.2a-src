package com.foxablazeultimate.ability.skill.azathoth;

import com.foxablazeultimate.config.AzathothConfig;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.tensura.ability.SkillUtils;
import io.github.manasmods.tensura.ability.subclass.ISpatialMovement;
import io.github.manasmods.tensura.entity.human.CloneEntity;
import io.github.manasmods.tensura.util.EnergyHelper;
import io.github.manasmods.tensura.util.ObjectSelectionHelper;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ClipContext.Fluid;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

public final class AzathothSpatial {
   private static final String MODE = "AzathothSpatialMode";
   private static final String DESTINATION = "AzathothSpatialClone";

   private AzathothSpatial() {
   }

   public static boolean isPortalMode(ManasSkillInstance instance) {
      return instance.getOrCreateTag().getInt("AzathothSpatialMode") == 1;
   }

   public static List<CloneEntity> ownedClones(LivingEntity owner) {
      List<CloneEntity> result = new ArrayList<>();
      if (owner.getServer() == null) {
         return result;
      }

      for (ServerLevel level : owner.getServer().getAllLevels()) {
         for (Entity entity : level.getAllEntities()) {
            if (entity instanceof CloneEntity clone && clone.isAlive() && owner.getUUID().equals(clone.getOwnerUUID())) {
               result.add(clone);
            }
         }
      }

      result.sort(Comparator.comparing(Entity::getUUID));
      return result;
   }

   public static void onSpatialPressed(ManasSkillInstance instance, LivingEntity owner, int key, int mode) {
      if (owner instanceof ServerPlayer player) {
         CompoundTag tag = instance.getOrCreateTag();
         if (owner.isShiftKeyDown()) {
            List<CloneEntity> clones = ownedClones(owner);
            int current = tag.getInt("AzathothSpatialMode");
            int index = -1;
            if (current == 2 && tag.hasUUID("AzathothSpatialClone")) {
               for (int i = 0; i < clones.size(); i++) {
                  if (clones.get(i).getUUID().equals(tag.getUUID("AzathothSpatialClone"))) {
                     index = i;
                     break;
                  }
               }
            }

            int next = current == 0 ? 1 : (current == 1 && !clones.isEmpty() ? 2 : (current == 2 && index >= 0 && index + 1 < clones.size() ? 2 : 0));
            tag.putInt("AzathothSpatialMode", next);
            tag.remove("AzathothSpatialClone");
            Component label;
            if (next == 2) {
               int nextIndex = current == 1 ? 0 : index + 1;
               CloneEntity clone = clones.get(nextIndex);
               tag.putUUID("AzathothSpatialClone", clone.getUUID());
               label = Component.translatable(
                  "foxablazeultimate.skill.azathoth.spatial.clone",
                  new Object[]{nextIndex + 1, clone.blockPosition().getX(), clone.blockPosition().getY(), clone.blockPosition().getZ()}
               );
            } else {
               label = Component.translatable("foxablazeultimate.skill.azathoth.spatial." + (next == 0 ? "teleport" : "portal"));
            }

            announceSubMode(player, label);
            instance.markDirty();
         } else if (!instance.onCoolDown(mode)) {
            if (SkillUtils.shouldCancelTeleportation(owner)) {
               player.displayClientMessage(Component.translatable("tensura.skill.spatial_blockade"), true);
            } else if (isPortalMode(instance)) {
               ((ISpatialMovement)instance.getSkill()).openSpatialMovementMenu(player, instance.getSkill());
            } else {
               ServerLevel destinationLevel = player.serverLevel();
               Vec3 destination;
               if (tag.getInt("AzathothSpatialMode") == 2 && tag.hasUUID("AzathothSpatialClone")) {
                  CloneEntity clone = null;
                  Iterator beforeDimension = player.server.getAllLevels().iterator();

                  while (true) {
                     if (beforeDimension.hasNext()) {
                        ServerLevel level = (ServerLevel)beforeDimension.next();
                        if (!(level.getEntity(tag.getUUID("AzathothSpatialClone")) instanceof CloneEntity c)
                           || !c.isAlive()
                           || !owner.getUUID().equals(c.getOwnerUUID())) {
                           continue;
                        }

                        clone = c;
                     }

                     if (clone == null || SkillUtils.shouldCancelTeleportation(clone)) {
                        fail(player);
                        return;
                     }

                     destinationLevel = (ServerLevel)clone.level();
                     ISpatialMovement movement = (ISpatialMovement)instance.getSkill();
                     if (movement.getBlacklistDimensions().contains(destinationLevel.dimension().location().toString())
                        || movement.getBlacklistDimensions().contains(player.level().dimension().location().toString())) {
                        fail(player);
                        return;
                     }

                     destination = safePosition(owner, destinationLevel, clone.position());
                     break;
                  }
               } else {
                  BlockHitResult hit = ObjectSelectionHelper.getPlayerPOVHitResult(
                     player.level(), owner, Fluid.NONE, Math.clamp(AzathothConfig.settings().teleportRange, 1.0, 256.0)
                  );
                  destination = safePosition(owner, destinationLevel, hit.getLocation().subtract(owner.getLookAngle().scale(0.6)));
               }

               if (destination == null) {
                  fail(player);
               } else if (!EnergyHelper.isOutOfEnergy(owner, instance, mode)) {
                  Vec3 before = owner.position();
                  ResourceKey<Level> beforeDimension = owner.level().dimension();
                  ISpatialMovement.warp(owner, owner, destination.x, destination.y, destination.z, destinationLevel.dimension());
                  if (owner.level().dimension() != beforeDimension || owner.position().distanceToSqr(before) > 0.01) {
                     instance.setCoolDown(Math.max(0, AzathothConfig.settings().teleportCooldownSeconds), mode);
                     instance.addMasteryPoint(owner);
                  }
               }
            }
         }
      }
   }

   public static Vec3 safePosition(LivingEntity owner, ServerLevel level, Vec3 requested) {
      BlockPos center = BlockPos.containing(requested);

      for (int radius = 0; radius <= 2; radius++) {
         for (int dy = 2; dy >= -4; dy--) {
            for (int dx = -radius; dx <= radius; dx++) {
               for (int dz = -radius; dz <= radius; dz++) {
                  if (Math.max(Math.abs(dx), Math.abs(dz)) == radius) {
                     BlockPos feet = center.offset(dx, dy, dz);
                     if (level.hasChunkAt(feet) && level.isInWorldBounds(feet) && level.getWorldBorder().isWithinBounds(feet)) {
                        Vec3 result = Vec3.atBottomCenterOf(feet);
                        AABB box = owner.getBoundingBox().move(result.subtract(owner.position()));
                        if (level.getBlockState(feet.below()).isCollisionShapeFullBlock(level, feet.below())
                           && level.noCollision(owner, box)
                           && !level.containsAnyLiquid(box)) {
                           return result;
                        }
                     }
                  }
               }
            }
         }
      }

      return null;
   }

   private static void fail(ServerPlayer player) {
      player.displayClientMessage(Component.translatable("tensura.ability.activation_failed"), true);
   }

   public static void announceSubMode(Player player, Component label) {
      player.displayClientMessage(
         Component.translatable("foxablazeultimate.skill.azathoth.submode.switched", new Object[]{label}).withStyle(ChatFormatting.GOLD), true
      );
   }
}
