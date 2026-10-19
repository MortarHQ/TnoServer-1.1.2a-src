package twilightforest.events;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.Unit;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent.PlayerLoggedInEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent.PlayerRespawnEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent.Post;
import twilightforest.beanification.Component;
import twilightforest.beanification.PostConstruct;
import twilightforest.components.entity.FortificationShieldAttachment;
import twilightforest.components.entity.TFPortalAttachment;
import twilightforest.components.entity.YetiThrowAttachment;
import twilightforest.config.TFConfig;
import twilightforest.init.TFDataAttachments;
import twilightforest.init.TFDimension;
import twilightforest.world.NoReturnTeleporter;
import twilightforest.world.TFTeleporter;

@Component
public class CapabilityEvents {
   @PostConstruct
   private void setup() {
      NeoForge.EVENT_BUS.addListener(this::updateShields);
      NeoForge.EVENT_BUS.addListener(this::updatePlayerCaps);
      NeoForge.EVENT_BUS.addListener(this::absorbShieldHits);
      NeoForge.EVENT_BUS.addListener(this::spawnInTFIfNecessary);
      NeoForge.EVENT_BUS.addListener(this::playerLogsIn);
   }

   private void updateShields(Post event) {
      if (event.getEntity() instanceof LivingEntity living && !living.level().isClientSide() && living.hasData(TFDataAttachments.FORTIFICATION_SHIELDS)) {
         ((FortificationShieldAttachment)event.getEntity().getData(TFDataAttachments.FORTIFICATION_SHIELDS)).tick(living);
      }
   }

   private void updatePlayerCaps(net.neoforged.neoforge.event.tick.PlayerTickEvent.Post event) {
      if ((Boolean)event.getEntity().getData(TFDataAttachments.FEATHER_FAN)) {
         event.getEntity().setIgnoreFallDamageFromCurrentImpulse(true);
         event.getEntity().currentImpulseImpactPos = event.getEntity().position();
         if (event.getEntity().onGround() || event.getEntity().isSwimming() || event.getEntity().isInWater()) {
            event.getEntity().setData(TFDataAttachments.FEATHER_FAN, false);
         }
      }

      ((YetiThrowAttachment)event.getEntity().getData(TFDataAttachments.YETI_THROWING)).tick(event.getEntity());
      ((TFPortalAttachment)event.getEntity().getData(TFDataAttachments.TF_PORTAL_COOLDOWN)).tick(event.getEntity());
   }

   private void absorbShieldHits(LivingIncomingDamageEvent event) {
      LivingEntity living = event.getEntity();
      if (!living.level().isClientSide() && !event.getSource().is(DamageTypeTags.BYPASSES_ARMOR)) {
         FortificationShieldAttachment attachment = (FortificationShieldAttachment)living.getData(TFDataAttachments.FORTIFICATION_SHIELDS);
         if (attachment.shieldsLeft() > 0) {
            if (living.invulnerableTime <= 0) {
               attachment.breakShield(living, false);
               FortificationShieldAttachment.addShieldBreakParticles(event.getSource(), living);
               living.invulnerableTime = 20;
            }

            event.setCanceled(true);
         }
      }
   }

   private void spawnInTFIfNecessary(PlayerRespawnEvent event) {
      if (event.getEntity() instanceof ServerPlayer serverPlayer) {
         if (serverPlayer.getRespawnPosition() == null) {
            newSpawnInTwilightForest(serverPlayer);
         }
      }
   }

   public void playerLogsIn(PlayerLoggedInEvent event) {
      if (!event.getEntity().level().isClientSide() && event.getEntity() instanceof ServerPlayer player) {
         dataFixLegacyBanish(player);
         if (!player.hasData(TFDataAttachments.BANISHED_TO_TWILIGHT_FOREST)) {
            newSpawnInTwilightForest(player);
         }
      }
   }

   private static void newSpawnInTwilightForest(ServerPlayer player) {
      if (TFConfig.newPlayersSpawnInTF) {
         ServerLevel level = player.getServer().getLevel(TFDimension.DIMENSION_KEY);
         if (level != null) {
            BlockPos newDefaultSpawn = level.getHeightmapPos(Types.MOTION_BLOCKING_NO_LEAVES, player.blockPosition());
            player.changeDimension(
               TFConfig.portalForNewPlayerSpawn
                  ? TFTeleporter.createTransition(player, level, newDefaultSpawn, true)
                  : NoReturnTeleporter.createNoPortalTransition(level, player, newDefaultSpawn)
            );
            player.setRespawnPosition(TFDimension.DIMENSION_KEY, newDefaultSpawn, player.getYRot(), true, false);
            player.setData(TFDataAttachments.BANISHED_TO_TWILIGHT_FOREST, Unit.INSTANCE);
         }
      }
   }

   private static void dataFixLegacyBanish(ServerPlayer player) {
      CompoundTag tagCompound = player.getPersistentData();
      if (tagCompound.contains("PlayerPersisted")) {
         CompoundTag playerData = tagCompound.getCompound("PlayerPersisted");
         if (playerData.contains("twilightforest_banished")) {
            playerData.remove("twilightforest_banished");
            tagCompound.put("PlayerPersisted", playerData);
            if (!player.hasData(TFDataAttachments.BANISHED_TO_TWILIGHT_FOREST)) {
               player.setData(TFDataAttachments.BANISHED_TO_TWILIGHT_FOREST, Unit.INSTANCE);
            }
         }
      }
   }
}
