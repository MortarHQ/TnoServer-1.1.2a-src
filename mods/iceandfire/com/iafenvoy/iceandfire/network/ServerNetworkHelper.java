package com.iafenvoy.iceandfire.network;

import com.iafenvoy.iceandfire.entity.AmphithereEntity;
import com.iafenvoy.iceandfire.entity.DeathWormEntity;
import com.iafenvoy.iceandfire.entity.DragonBaseEntity;
import com.iafenvoy.iceandfire.entity.HippocampusEntity;
import com.iafenvoy.iceandfire.entity.HippogryphEntity;
import com.iafenvoy.iceandfire.entity.HydraEntity;
import com.iafenvoy.iceandfire.entity.util.ISyncMount;
import com.iafenvoy.iceandfire.event.ServerEvents;
import com.iafenvoy.iceandfire.network.payload.DragonControlC2SPayload;
import com.iafenvoy.iceandfire.network.payload.DragonSetBurnBlockS2CPayload;
import com.iafenvoy.iceandfire.network.payload.LightningBoltS2CPayload;
import com.iafenvoy.iceandfire.network.payload.MultipartInteractC2SPayload;
import com.iafenvoy.iceandfire.network.payload.PlayerHitMultipartC2SPayload;
import com.iafenvoy.iceandfire.network.payload.StartRidingMobC2SPayload;
import com.iafenvoy.iceandfire.network.payload.StartRidingMobS2CPayload;
import com.iafenvoy.iceandfire.network.payload.UpdatePixieHouseS2CPayload;
import com.iafenvoy.iceandfire.network.payload.UpdatePixieJarS2CPayload;
import com.iafenvoy.iceandfire.network.payload.UpdatePodiumS2CPayload;
import dev.architectury.networking.NetworkManager;
import dev.architectury.networking.NetworkManager.Side;
import dev.architectury.platform.Platform;
import dev.architectury.utils.Env;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.player.Player;

public class ServerNetworkHelper {
   public static void registerReceivers() {
      if (Platform.getEnvironment() == Env.SERVER) {
         NetworkManager.registerS2CPayloadType(DragonSetBurnBlockS2CPayload.ID, DragonSetBurnBlockS2CPayload.CODEC);
         NetworkManager.registerS2CPayloadType(LightningBoltS2CPayload.ID, LightningBoltS2CPayload.CODEC);
         NetworkManager.registerS2CPayloadType(StartRidingMobS2CPayload.ID, StartRidingMobS2CPayload.CODEC);
         NetworkManager.registerS2CPayloadType(UpdatePixieHouseS2CPayload.ID, UpdatePixieHouseS2CPayload.CODEC);
         NetworkManager.registerS2CPayloadType(UpdatePixieJarS2CPayload.ID, UpdatePixieJarS2CPayload.CODEC);
         NetworkManager.registerS2CPayloadType(UpdatePodiumS2CPayload.ID, UpdatePodiumS2CPayload.CODEC);
      }

      NetworkManager.registerReceiver(Side.C2S, DragonControlC2SPayload.ID, DragonControlC2SPayload.CODEC, (payload, ctx) -> {
         Player player = ctx.getPlayer();
         if (player != null) {
            Entity entity = player.level().getEntity(payload.dragonId());
            if (ServerEvents.isRidingOrBeingRiddenBy(entity, player)) {
               BlockPos pos = payload.pos();
               switch (entity) {
                  case DragonBaseEntity dragon:
                     if (dragon.isOwnedBy(player)) {
                        dragon.setControlState(payload.controlState());
                     }
                     break;
                  case HippogryphEntity hippogryph:
                     if (hippogryph.isOwnedBy(player)) {
                        hippogryph.setControlState(payload.controlState());
                     }
                     break;
                  case HippocampusEntity hippo:
                     if (hippo.isOwnedBy(player)) {
                        hippo.setControlState(payload.controlState());
                     }

                     hippo.setPosRaw(pos.getX(), pos.getY(), pos.getZ());
                     break;
                  case DeathWormEntity deathWorm:
                     deathWorm.setControlState(payload.controlState());
                     deathWorm.setPosRaw(pos.getX(), pos.getY(), pos.getZ());
                     break;
                  case AmphithereEntity amphithere:
                     if (amphithere.isOwnedBy(player)) {
                        amphithere.setControlState(payload.controlState());
                     }

                     amphithere.setPosRaw(pos.getX(), pos.getY(), pos.getZ());
                     return;
                  default:
               }
            }
         }
      });
      NetworkManager.registerReceiver(
         Side.C2S,
         MultipartInteractC2SPayload.ID,
         MultipartInteractC2SPayload.CODEC,
         (payload, ctx) -> {
            Player player = ctx.getPlayer();
            ctx.queue(
               () -> {
                  if (player != null
                     && player.level() instanceof ServerLevel serverWorld
                     && serverWorld.getEntity(payload.creatureID()) instanceof LivingEntity livingEntity) {
                     double dist = player.distanceTo(livingEntity);
                     if (dist < 100.0) {
                        float dmg = payload.dmg();
                        if (dmg > 0.0F) {
                           livingEntity.hurt(player.level().damageSources.mobAttack(player), dmg);
                        } else {
                           livingEntity.interact(player, InteractionHand.MAIN_HAND);
                        }
                     }
                  }
               }
            );
         }
      );
      NetworkManager.registerReceiver(Side.C2S, PlayerHitMultipartC2SPayload.ID, PlayerHitMultipartC2SPayload.CODEC, (payload, ctx) -> {
         Player player = ctx.getPlayer();
         if (player != null && player.level().getEntity(payload.entityId()) instanceof LivingEntity livingEntity) {
            double dist = player.distanceTo(livingEntity);
            if (dist < 100.0) {
               player.attack(livingEntity);
               if (livingEntity instanceof HydraEntity hydra) {
                  hydra.triggerHeadFlags(payload.index());
               }
            }
         }
      });
      NetworkManager.registerReceiver(Side.C2S, StartRidingMobC2SPayload.ID, StartRidingMobC2SPayload.CODEC, (payload, ctx) -> {
         Player player = ctx.getPlayer();
         if (player != null) {
            Entity entity = player.level().getEntity(payload.dragonId());
            if (entity instanceof ISyncMount && entity instanceof TamableAnimal tamable && tamable.isOwnedBy(player) && tamable.distanceTo(player) < 14.0F) {
               if (payload.ride()) {
                  if (payload.baby()) {
                     tamable.startRiding(player, true);
                  } else {
                     player.startRiding(tamable, true);
                  }
               } else if (payload.baby()) {
                  tamable.stopRiding();
               } else {
                  player.stopRiding();
               }
            }
         }
      });
   }
}
