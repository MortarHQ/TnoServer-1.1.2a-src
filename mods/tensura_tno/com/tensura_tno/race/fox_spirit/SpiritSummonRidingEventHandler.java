package com.tensura_tno.race.fox_spirit;

import io.github.manasmods.tensura.entity.template.TensuraRideableEntity;
import io.github.manasmods.tensura.entity.template.subclass.ILivingPartEntity;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityMountEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent.EntityInteract;
import net.neoforged.neoforge.event.tick.EntityTickEvent.Post;

@EventBusSubscriber(modid = "tensura_tno")
public final class SpiritSummonRidingEventHandler {
   private static final TagKey<EntityType<?>> SPIRIT_SUMMON_RIDEABLE = TagKey.create(
      Registries.ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath("tensura_tno", "spirit_summon_rideable")
   );
   private static final TagKey<EntityType<?>> SPIRIT_SUMMON_FLYING_MOUNT = TagKey.create(
      Registries.ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath("tensura_tno", "spirit_summon_flying_mount")
   );
   private static final Map<UUID, Boolean> PREVIOUS_NO_GRAVITY = new HashMap<>();

   private SpiritSummonRidingEventHandler() {
   }

   @SubscribeEvent
   public static void onEntityInteract(EntityInteract event) {
      if (event.getEntity() instanceof ServerPlayer player) {
         if (event.getHand() == InteractionHand.MAIN_HAND) {
            if (!player.isPassenger()) {
               if (!player.isSecondaryUseActive()) {
                  if (player.getItemInHand(event.getHand()).isEmpty()) {
                     if (normalizeTarget(event.getTarget()) instanceof Mob mob) {
                        if (canPlayerMount(player, mob)) {
                           mob.getNavigation().stop();
                           player.setYRot(mob.getYRot());
                           player.setXRot(mob.getXRot());
                           if (player.startRiding(mob, true)) {
                              event.setCancellationResult(InteractionResult.sidedSuccess(false));
                              event.setCanceled(true);
                           }
                        }
                     }
                  }
               }
            }
         }
      }
   }

   @SubscribeEvent
   public static void onMountChange(EntityMountEvent event) {
      if (!event.getLevel().isClientSide()) {
         if (normalizeTarget(event.getEntityBeingMounted()) instanceof Mob mob) {
            if (mob.getType().is(SPIRIT_SUMMON_FLYING_MOUNT)) {
               if (event.isMounting()) {
                  PREVIOUS_NO_GRAVITY.putIfAbsent(mob.getUUID(), mob.isNoGravity());
               } else {
                  Boolean previous = PREVIOUS_NO_GRAVITY.remove(mob.getUUID());
                  if (previous != null) {
                     mob.setNoGravity(previous);
                  }
               }
            }
         }
      }
   }

   @SubscribeEvent
   public static void onEntityTick(Post event) {
      if (event.getEntity() instanceof Mob mount) {
         if (!mount.level().isClientSide()) {
            if (!(mount instanceof TensuraRideableEntity)) {
               if (mount.getFirstPassenger() instanceof ServerPlayer rider) {
                  if (SpiritSummonEntityHelper.isSpiritSummonOf(mount, rider)) {
                     boolean flying = mount.getType().is(SPIRIT_SUMMON_FLYING_MOUNT);
                     boolean genericRideable = flying || mount.getType().is(SPIRIT_SUMMON_RIDEABLE);
                     if (genericRideable) {
                        applyGenericControl(mount, rider, flying);
                     }
                  }
               }
            }
         }
      }
   }

   private static Entity normalizeTarget(Entity target) {
      if (target instanceof ILivingPartEntity part) {
         Entity var3 = part.getHead();
         if (var3 instanceof Entity) {
            return var3;
         }
      }

      return target;
   }

   private static boolean canPlayerMount(ServerPlayer player, Mob mob) {
      if (!mob.isAlive()) {
         return false;
      } else if (mob.isBaby()) {
         return false;
      } else if (mob.level() != player.level()) {
         return false;
      } else if (!SpiritSummonEntityHelper.isSpiritSummonOf(mob, player)) {
         return false;
      } else {
         return mob instanceof TensuraRideableEntity ? true : mob.getType().is(SPIRIT_SUMMON_RIDEABLE) || mob.getType().is(SPIRIT_SUMMON_FLYING_MOUNT);
      }
   }

   private static void applyGenericControl(Mob mount, ServerPlayer rider, boolean flying) {
      if (!rider.isSpectator()) {
         mount.getNavigation().stop();
         mount.setYRot(rider.getYRot());
         mount.setYHeadRot(rider.getYRot());
         mount.setYBodyRot(rider.getYRot());
         if (flying) {
            PREVIOUS_NO_GRAVITY.putIfAbsent(mount.getUUID(), mount.isNoGravity());
            mount.setNoGravity(true);
            mount.fallDistance = 0.0F;
         }

         float forwardInput = rider.zza;
         float strafeInput = rider.xxa;
         boolean hasHorizontalInput = Math.abs(forwardInput) > 0.01F || Math.abs(strafeInput) > 0.01F;
         if (!hasHorizontalInput) {
            if (flying) {
               mount.setDeltaMovement(mount.getDeltaMovement().multiply(0.55, 0.55, 0.55));
            }
         } else {
            double speed = getControlledSpeed(mount, rider, flying, forwardInput);
            double yaw = Math.toRadians(rider.getYRot());
            Vec3 forward = new Vec3(-Math.sin(yaw), 0.0, Math.cos(yaw));
            Vec3 strafe = new Vec3(Math.cos(yaw), 0.0, Math.sin(yaw));
            Vec3 horizontal = forward.scale(forwardInput * speed).add(strafe.scale(strafeInput * speed * 0.65));
            double vertical = 0.0;
            if (flying) {
               double pitch = Math.toRadians(rider.getXRot());
               vertical = -Math.sin(pitch) * Math.max(0.0F, forwardInput) * speed;
               vertical = Mth.clamp(vertical, -0.7, 0.7);
            }

            Vec3 movement = new Vec3(horizontal.x, flying ? vertical : 0.0, horizontal.z);
            mount.move(MoverType.SELF, movement);
            mount.setDeltaMovement(mount.getDeltaMovement().multiply(0.35, flying ? 0.35 : 1.0, 0.35));
            mount.hasImpulse = true;
         }
      }
   }

   private static double getControlledSpeed(Mob mount, ServerPlayer rider, boolean flying, float forwardInput) {
      double speed = mount.getAttributeValue(Attributes.MOVEMENT_SPEED) * (rider.isSprinting() ? 1.8 : 1.25);
      if (forwardInput < 0.0F) {
         speed *= 0.5;
      }

      return Mth.clamp(speed, 0.12, flying ? 1.0 : 0.55);
   }
}
