package com.b1n_ry.yigd.events;

import com.b1n_ry.yigd.DeathHandler;
import com.b1n_ry.yigd.Yigd;
import com.b1n_ry.yigd.components.GraveComponent;
import com.b1n_ry.yigd.components.RespawnComponent;
import com.b1n_ry.yigd.config.GraveConfig;
import com.b1n_ry.yigd.config.YigdConfig;
import com.b1n_ry.yigd.data.DeathInfoManager;
import com.b1n_ry.yigd.data.GraveStatus;
import com.b1n_ry.yigd.networking.packets.SyncConfigS2CPacket;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.component.ResolvableProfile;
import net.minecraft.world.level.GameRules;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.living.LivingDropsEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent.Clone;
import net.neoforged.neoforge.event.entity.player.PlayerEvent.PlayerLoggedInEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent.PlayerLoggedOutEvent;
import net.neoforged.neoforge.event.server.ServerStartedEvent;
import net.neoforged.neoforge.event.tick.ServerTickEvent.Post;
import net.neoforged.neoforge.network.PacketDistributor;

public class ServerEventHandler {
   @SubscribeEvent
   public void onEndOfTick(Post event) {
      List<Runnable> methodsToRun = new ArrayList<>(Yigd.END_OF_TICK);
      Yigd.END_OF_TICK.clear();

      for (Runnable runnable : methodsToRun) {
         runnable.run();
      }
   }

   @SubscribeEvent
   public void endPlayerTick(net.neoforged.neoforge.event.tick.PlayerTickEvent.Post event) {
      Player player = event.getEntity();
      if (player.onGround()) {
         player.setData(Yigd.LAST_GROUND_POS, player.position());
      }
   }

   @SubscribeEvent
   public void serverStarted(ServerStartedEvent event) {
      MinecraftServer server = event.getServer();
      DeathInfoManager.INSTANCE.clear();
      ServerLevel overworld = server.overworld();
      DeathInfoManager.INSTANCE = (DeathInfoManager)overworld.getDataStorage().computeIfAbsent(DeathInfoManager.getPersistentStateType(server), "yigd_data");
      DeathInfoManager.INSTANCE.setDirty();
   }

   @SubscribeEvent(priority = EventPriority.LOWEST)
   public void onPlayerDeathDropItems(LivingDropsEvent event) {
      if (event.getEntity() instanceof ServerPlayer player) {
         UUID var8 = player.getUUID();
         DeathHandler unfinished = Yigd.UNFINISHED_DEATHS.remove(var8);
         if (unfinished != null) {
            Collection<ItemEntity> drops = event.getDrops();

            for (ItemEntity itemEntity : drops) {
               unfinished.addItem(itemEntity.getItem());
            }

            drops.clear();
            unfinished.finalizeDeath();
         } else {
            Yigd.LOGGER.error("Did not find cached death handler for {}. Can't generate player loot", player.getGameProfile().getName());
         }
      }
   }

   @SubscribeEvent(priority = EventPriority.LOWEST)
   public void onLivingDeath(LivingDeathEvent event) {
      if (event.getEntity() instanceof ServerPlayer player) {
         if (Yigd.UNFINISHED_DEATHS.containsKey(player.getUUID())) {
            Yigd.LOGGER.warn("Player '{}' already has a registered, unhandled death. Ignoring this one", player.getGameProfile().getName());
         } else if (!player.isDeadOrDying()) {
            Yigd.LOGGER
               .error(
                  "Player '{}' is not dead, but LivingDeathEvent was called. This should not happen. Ignoring this event", player.getGameProfile().getName()
               );
         } else if (!player.isSpectator()) {
            ServerLevel level = player.serverLevel();
            DamageSource damageSource = event.getSource();
            if (!level.getGameRules().getBoolean(GameRules.RULE_KEEPINVENTORY)) {
               Yigd.UNFINISHED_DEATHS.put(player.getUUID(), new DeathHandler(player, level, player.position(), damageSource));
            }
         }
      }
   }

   @SubscribeEvent(priority = EventPriority.LOWEST)
   public void afterRespawn(Clone event) {
      if (event.isWasDeath()) {
         Player newPlayer = event.getEntity();
         Player oldPlayer = event.getOriginal();
         if (!newPlayer.level().isClientSide && !oldPlayer.level().isClientSide) {
            NeoForge.EVENT_BUS.post(new YigdEvents.BeforeSoulboundEvent((ServerPlayer)oldPlayer, (ServerPlayer)newPlayer));
            ResolvableProfile newProfile = new ResolvableProfile(newPlayer.getGameProfile());
            Optional<RespawnComponent> respawnComponent = DeathInfoManager.INSTANCE.getRespawnComponent(newProfile);
            respawnComponent.ifPresent(component -> component.apply((ServerPlayer)newPlayer));
            if (YigdConfig.getConfig().graveConfig.informGraveLocation && respawnComponent.isPresent() && respawnComponent.get().wasGraveGenerated()) {
               List<GraveComponent> graves = new ArrayList<>(DeathInfoManager.INSTANCE.getBackupData(newProfile));
               graves.removeIf(grave -> grave.getStatus() != GraveStatus.UNCLAIMED);
               if (!graves.isEmpty()) {
                  GraveComponent latest = graves.getLast();
                  BlockPos gravePos = latest.getPos();
                  newPlayer.sendSystemMessage(
                     Component.translatable(
                        "text.yigd.message.grave_location",
                        new Object[]{gravePos.getX(), gravePos.getY(), gravePos.getZ(), latest.getWorldRegistryKey().location().toString()}
                     )
                  );
               }
            }
         }
      }
   }

   @SubscribeEvent
   public void playerJoin(PlayerLoggedInEvent event) {
      if (event.getEntity() instanceof ServerPlayer player) {
         YigdConfig var9 = YigdConfig.getConfig();
         PacketDistributor.sendToPlayer(
            player,
            new SyncConfigS2CPacket(
               var9.graveConfig.retrieveMethods.onBreak,
               var9.graveRendering.useGlowingEffect,
               var9.graveRendering.glowingDistance,
               var9.extraFeatures.deathSightEnchant.range
            ),
            new CustomPacketPayload[0]
         );
         GraveConfig.GraveRobbing robConfig = var9.graveConfig.graveRobbing;
         UUID joiningId = player.getUUID();
         if (Yigd.NOT_NOTIFIED_ROBBERIES.containsKey(joiningId)) {
            if (robConfig.tellWhoRobbed) {
               for (String robber : Yigd.NOT_NOTIFIED_ROBBERIES.remove(joiningId)) {
                  player.sendSystemMessage(Component.translatable("text.yigd.message.inform_robbery.with_details", new Object[]{robber}));
               }
            } else {
               Yigd.NOT_NOTIFIED_ROBBERIES.remove(joiningId);
               player.sendSystemMessage(Component.translatable("text.yigd.message.inform_robbery"));
            }
         }
      }
   }

   @SubscribeEvent
   public void playerLeave(PlayerLoggedOutEvent event) {
      if (event.getEntity() instanceof ServerPlayer player) {
         YigdConfig config = YigdConfig.getConfig();
         if (config.graveConfig.sellOutOfflinePeople) {
            ResolvableProfile loggedOffProfile = new ResolvableProfile(player.getGameProfile());
            List<GraveComponent> loggedOffGraves = DeathInfoManager.INSTANCE.getBackupData(loggedOffProfile);
            List<GraveComponent> loggedOffUnclaimed = new ArrayList<>(loggedOffGraves);
            loggedOffGraves.removeIf(c -> c.getStatus() == GraveStatus.UNCLAIMED);
            if (!loggedOffUnclaimed.isEmpty()) {
               GraveComponent component = loggedOffUnclaimed.getFirst();
               BlockPos lastGravePos = component.getPos();
               player.server
                  .sendSystemMessage(
                     Component.translatable(
                        "text.yigd.message.sellout_player",
                        new Object[]{
                           loggedOffProfile.name().orElse("PLAYER_NOT_FOUND"),
                           lastGravePos.getX(),
                           lastGravePos.getY(),
                           lastGravePos.getZ(),
                           component.getWorldRegistryKey().location().toString()
                        }
                     )
                  );
            }
         }
      }
   }
}
