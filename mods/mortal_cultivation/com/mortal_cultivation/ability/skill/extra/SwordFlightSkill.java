package com.mortal_cultivation.ability.skill.extra;

import com.mortal_cultivation.ability.skill.base.LegacySkill;
import com.mortal_cultivation.entity.FlyingSwordEntity;
import com.mortal_cultivation.flight.FlyingSwordDeploymentData;
import com.mortal_cultivation.registry.CultivationEntities;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.tensura.ability.skill.Skill.SkillType;
import io.github.manasmods.tensura.storage.TensuraStorages;
import io.github.manasmods.tensura.util.EnergyHelper;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class SwordFlightSkill extends LegacySkill {
   private static final double MAGICULE_COST = 2000.0;
   private static final double DIRECT_RETURN_DISTANCE = 64.0;
   private static final double[] STAGING_RADII = new double[]{48.0, 50.0, 46.0, 44.0, 40.0, 36.0, 32.0};
   private static final double[] STAGING_HEIGHTS = new double[]{8.0, 16.0, 24.0, 4.0, 32.0, 0.0};
   private static final float[] STAGING_ANGLES = new float[]{0.0F, 30.0F, -30.0F, 60.0F, -60.0F, 90.0F, -90.0F, 120.0F, -120.0F, 150.0F, -150.0F, 180.0F};

   public SwordFlightSkill() {
      super(SkillType.EXTRA);
   }

   public ResourceLocation getSkillIcon() {
      return ResourceLocation.fromNamespaceAndPath("mortal_cultivation", "textures/skill/extra/sword_flight.png");
   }

   @Override
   public boolean meetEPRequirement(Player entity, double curEP) {
      return false;
   }

   @Override
   public double magiculeCost(LivingEntity entity, ManasSkillInstance instance) {
      return 2000.0;
   }

   public boolean canBeToggled(ManasSkillInstance instance, LivingEntity living) {
      return false;
   }

   @Override
   public void onPressed(ManasSkillInstance instance, LivingEntity entity) {
      if (entity instanceof ServerPlayer player) {
         SwordFlightSkill.OwnedSwordLookup lookup = findOrRecoverOwnedSword(player);
         if (lookup.deploymentExists()) {
            FlyingSwordEntity deployedSword = lookup.sword();
            if (deployedSword != null && player.getVehicle() != deployedSword) {
               summonDeployedSword(player, deployedSword);
            }
         } else {
            InteractionHand swordHand = findSwordHand(player);
            if (swordHand == null) {
               player.displayClientMessage(Component.translatable("skill.mortal_cultivation.sword_flight.need_sword"), true);
            } else {
               ItemStack heldSword = player.getItemInHand(swordHand);
               if (!hasEnoughMagicule(player)) {
                  player.displayClientMessage(Component.translatable("skill.mortal_cultivation.sword_flight.out_of_magicule"), true);
               } else {
                  FlyingSwordEntity flyingSword = createSwordInFrontOf(player, heldSword);
                  if (player.serverLevel().addFreshEntity(flyingSword)) {
                     if (EnergyHelper.isOutOfMagiculeConsuming(player, 2000.0) > 0.0) {
                        flyingSword.discardWithoutDroppingSword();
                     } else {
                        player.setItemInHand(swordHand, ItemStack.EMPTY);
                        FlyingSwordDeploymentData.track(player, flyingSword);
                     }
                  }
               }
            }
         }
      }
   }

   private static boolean hasEnoughMagicule(ServerPlayer player) {
      return player.hasInfiniteMaterials() || TensuraStorages.getExistenceFrom(player).getMagicule() >= 2000.0;
   }

   private static void summonDeployedSword(ServerPlayer player, FlyingSwordEntity deployedSword) {
      if (deployedSword.level() == player.level()) {
         if (deployedSword.distanceToSqr(player) > 4096.0) {
            Vec3 stagingPosition = findRecallStagingPosition(player, deployedSword);
            if (stagingPosition != null) {
               deployedSword.teleportTo(stagingPosition.x, stagingPosition.y, stagingPosition.z);
               deployedSword.setDeltaMovement(Vec3.ZERO);
            }
         }

         deployedSword.startReturningToOwner(player);
      } else {
         ItemStack swordStack = deployedSword.takeSwordStack();
         if (swordStack.isEmpty()) {
            deployedSword.ejectPassengers();
            deployedSword.discard();
         } else {
            FlyingSwordEntity replacement = createSword(player, swordStack);
            replacement.setDeploymentId(deployedSword.getOrCreateDeploymentId());
            Vec3 stagingPosition = findRecallStagingPosition(player, replacement);
            if (stagingPosition == null) {
               deployedSword.setSwordStack(swordStack);
            } else {
               replacement.moveTo(stagingPosition.x, stagingPosition.y, stagingPosition.z, player.getYRot(), 0.0F);
               replacement.startReturningToOwner(player);
               if (!player.serverLevel().addFreshEntity(replacement)) {
                  deployedSword.setSwordStack(swordStack);
               } else {
                  FlyingSwordDeploymentData.track(player, replacement);
                  deployedSword.discardWithoutDroppingSword();
               }
            }
         }
      }
   }

   private static FlyingSwordEntity createSwordInFrontOf(ServerPlayer player, ItemStack swordStack) {
      FlyingSwordEntity flyingSword = createSword(player, swordStack);
      Vec3 horizontalLook = Vec3.directionFromRotation(0.0F, player.getYRot());
      Vec3 spawnPosition = player.position().add(horizontalLook.scale(1.2)).add(0.0, 0.12, 0.0);
      flyingSword.moveTo(spawnPosition.x, spawnPosition.y, spawnPosition.z, player.getYRot(), 0.0F);
      return flyingSword;
   }

   private static FlyingSwordEntity createSword(ServerPlayer player, ItemStack swordStack) {
      FlyingSwordEntity flyingSword = new FlyingSwordEntity((EntityType<? extends FlyingSwordEntity>)CultivationEntities.FLYING_SWORD.get(), player.level());
      flyingSword.setOwnerUUID(player.getUUID());
      flyingSword.setSwordStack(swordStack);
      flyingSword.setYRot(player.getYRot());
      flyingSword.setXRot(0.0F);
      return flyingSword;
   }

   @Nullable
   private static Vec3 findRecallStagingPosition(ServerPlayer player, FlyingSwordEntity sword) {
      ServerLevel level = player.serverLevel();
      Vec3 forward = Vec3.directionFromRotation(0.0F, player.getYRot()).normalize();

      for (double radius : STAGING_RADII) {
         for (float angle : STAGING_ANGLES) {
            Vec3 horizontal = forward.yRot(angle * (float) (Math.PI / 180.0)).scale(radius);

            for (double height : STAGING_HEIGHTS) {
               double y = Mth.clamp(player.getY() + height, level.getMinBuildHeight() + 1.0, level.getMaxBuildHeight() - 2.0);
               Vec3 candidate = new Vec3(player.getX() + horizontal.x, y, player.getZ() + horizontal.z);
               if (isSafeStagingPosition(level, sword, candidate)) {
                  return candidate;
               }
            }

            int surfaceY = level.getHeight(Types.MOTION_BLOCKING_NO_LEAVES, Mth.floor(player.getX() + horizontal.x), Mth.floor(player.getZ() + horizontal.z));
            Vec3 surfaceCandidate = new Vec3(
               player.getX() + horizontal.x,
               Mth.clamp(surfaceY + 0.25, level.getMinBuildHeight() + 1.0, level.getMaxBuildHeight() - 2.0),
               player.getZ() + horizontal.z
            );
            if (isSafeStagingPosition(level, sword, surfaceCandidate)) {
               return surfaceCandidate;
            }
         }
      }

      return null;
   }

   private static boolean isSafeStagingPosition(ServerLevel level, FlyingSwordEntity sword, Vec3 candidate) {
      AABB bounds = sword.getSwordBoundingBoxAt(candidate);
      return level.isInWorldBounds(BlockPos.containing(candidate)) && level.getWorldBorder().isWithinBounds(bounds) && level.noCollision(sword, bounds);
   }

   @Nullable
   private static InteractionHand findSwordHand(Player player) {
      if (player.getMainHandItem().is(ItemTags.SWORDS)) {
         return InteractionHand.MAIN_HAND;
      } else {
         return player.getOffhandItem().is(ItemTags.SWORDS) ? InteractionHand.OFF_HAND : null;
      }
   }

   private static SwordFlightSkill.OwnedSwordLookup findOrRecoverOwnedSword(ServerPlayer player) {
      MinecraftServer server = player.getServer();
      if (server == null) {
         return new SwordFlightSkill.OwnedSwordLookup(null, false);
      }

      FlyingSwordDeploymentData.ActiveDeployment active = FlyingSwordDeploymentData.get(player);
      FlyingSwordEntity riddenSword = player.getVehicle() instanceof FlyingSwordEntity sword
            && sword.isOwnedBy(player)
            && !sword.isRemoved()
            && !sword.getSwordStack().isEmpty()
         ? sword
         : null;
      List<FlyingSwordEntity> ownedSwords = new ArrayList<>();
      List<FlyingSwordEntity> discardWithoutDrop = new ArrayList<>();
      FlyingSwordEntity activeSword = null;
      FlyingSwordEntity otherDimensionSword = null;
      FlyingSwordEntity nearestSword = null;
      double nearestDistance = Double.MAX_VALUE;

      for (ServerLevel level : server.getAllLevels()) {
         for (Entity loadedEntity : level.getAllEntities()) {
            if (loadedEntity instanceof FlyingSwordEntity sword && !sword.isRemoved() && sword.isOwnedBy(player)) {
               if (sword.getSwordStack().isEmpty() && active != null && active.matchesEntity(sword)) {
                  sword.setSwordStack(active.copySwordStack());
               }

               ownedSwords.add(sword);
               if (sword.getSwordStack().isEmpty()) {
                  discardWithoutDrop.add(sword);
               } else if (active != null) {
                  if (active.matchesEntity(sword)) {
                     activeSword = sword;
                  } else {
                     discardWithoutDrop.add(sword);
                  }
               } else if (level == player.serverLevel()) {
                  double distance = sword.distanceToSqr(player);
                  if (distance < nearestDistance) {
                     nearestDistance = distance;
                     nearestSword = sword;
                  }
               } else if (otherDimensionSword == null) {
                  otherDimensionSword = sword;
               }
            }
         }
      }

      for (FlyingSwordEntity sword : discardWithoutDrop) {
         sword.discardWithoutDroppingSword();
      }

      if (active == null) {
         FlyingSwordEntity primarySword = riddenSword != null ? riddenSword : (nearestSword != null ? nearestSword : otherDimensionSword);
         if (primarySword == null) {
            return new SwordFlightSkill.OwnedSwordLookup(null, false);
         }

         for (FlyingSwordEntity sword : ownedSwords) {
            if (sword != primarySword && !sword.isRemoved()) {
               sword.recallTo(player);
            }
         }

         FlyingSwordDeploymentData.track(player, primarySword);
         return new SwordFlightSkill.OwnedSwordLookup(primarySword, true);
      } else {
         if (activeSword != null && !activeSword.isRemoved()) {
            FlyingSwordDeploymentData.track(player, activeSword);
            return new SwordFlightSkill.OwnedSwordLookup(activeSword, true);
         }

         FlyingSwordEntity recoveredSword = createSword(player, active.copySwordStack());
         recoveredSword.setDeploymentId(active.deploymentId());
         Vec3 stagingPosition = findRecallStagingPosition(player, recoveredSword);
         if (stagingPosition == null) {
            return new SwordFlightSkill.OwnedSwordLookup(null, true);
         }

         recoveredSword.moveTo(stagingPosition.x, stagingPosition.y, stagingPosition.z, player.getYRot(), 0.0F);
         if (!player.serverLevel().addFreshEntity(recoveredSword)) {
            return new SwordFlightSkill.OwnedSwordLookup(null, true);
         }

         FlyingSwordDeploymentData.track(player, recoveredSword);
         return new SwordFlightSkill.OwnedSwordLookup(recoveredSword, true);
      }
   }

   private record OwnedSwordLookup(@Nullable FlyingSwordEntity sword, boolean deploymentExists) {
   }
}
